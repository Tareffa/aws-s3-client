package br.com.tareffa.awss3client.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.MessageFormat;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.validation.Valid;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.tareffa.awss3client.client.OAuthClient;
import br.com.tareffa.awss3client.domain.commands.SalvarArquivoRequest;
import br.com.tareffa.awss3client.domain.criterias.PageCriteria;
import br.com.tareffa.awss3client.domain.dtos.ArquivoDTO;
import br.com.tareffa.awss3client.domain.mappers.ArquivoMapper;
import br.com.tareffa.awss3client.domain.dtos.UserDTO;
import br.com.tareffa.awss3client.domain.models.Arquivo;
import br.com.tareffa.awss3client.domain.models.Bucket;
import br.com.tareffa.awss3client.repositories.ArquivosRepository;
import br.com.tareffa.awss3client.repositories.BucketsRepository;
import br.com.tareffa.awss3client.utils.FileUtils;

@Service
public class ArquivosService {

    @Inject
    BucketsRepository bucketRepository;

    @Inject
    ArquivosRepository arquivosRepository;

    @Inject
    OAuthClient oauthClient;

    @Inject
    AwsService awsService;

    public ArquivoDTO store(MultipartFile multipart, SalvarArquivoRequest detalhes, OAuth2Authentication authentication) throws Exception {
        // busca o bucket do banco de dados...
        Bucket bucket = this.bucketRepository.findByAplicationId(detalhes.getApplicationId())
                                             .orElseThrow(() -> new NoResultException("Bucket não encontrado!"));

        // cria um arquivo temporario...
        File file = FileUtils.createTemporaryFile(multipart);

        String filename = MessageFormat.format(
            "{0}__{1}", UUID.randomUUID().toString(), multipart.getOriginalFilename()
        );

        Arquivo arquivo = Arquivo.builder()
            .nomeOriginal(filename)
            .nomeAws(filename)
            .applicationId(detalhes.getApplicationId())
            .contabilidadeId(BigInteger.ZERO)
            .empresaId(BigInteger.ZERO)
            .processado(false)
            .build();
        
        awsService.upload(arquivo.getNomeAws(), file, bucket);

        return ArquivoMapper.fromEntity(arquivosRepository.save(arquivo));
    }

    public Resource download(BigInteger id, OAuth2Authentication authentication) throws Exception {
        Arquivo arquivo = arquivosRepository.findById(id)
                                            .orElseThrow(() -> new NoResultException("Arquivo não encontrado!"));

        Bucket bucket = this.bucketRepository.findByAplicationId(arquivo.getApplicationId())
                                             .orElseThrow(() -> new NoResultException("Bucket não encontrado!"));

        return FileUtils.loadFileAsResource(
            awsService.download(arquivo.getNomeAws(), arquivo.getNomeOriginal(), bucket)
        );
    }

	public Page<ArquivoDTO> findAll(ArquivoDTO filter, PageCriteria pageCriteria, String authorization) {
    	UserDTO userInfo = oauthClient.getUserInfo(authorization).getBody().getRecord();

		if(filter.getContabilidadeId() != null) {
			filter.setContabilidadeId(userInfo.getOrganizationId());
		}
		
		ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(StringMatcher.CONTAINING);
		Example<Arquivo> example = Example.of(ArquivoMapper.fromDto(filter), matcher);
		
		return arquivosRepository.findAll(example, PageRequest.of(pageCriteria.getPageIndex(), pageCriteria.getPageSize()))
								 .map(ArquivoMapper::fromEntity);
	}

}
