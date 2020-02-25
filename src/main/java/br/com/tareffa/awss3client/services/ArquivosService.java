package br.com.tareffa.awss3client.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.MessageFormat;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.tareffa.awss3client.domain.commands.SalvarArquivoRequest;
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
    AwsService awsService;

    public String store(MultipartFile multipart, SalvarArquivoRequest detalhes, OAuth2Authentication authentication)
            throws Exception {
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

        return this.arquivosRepository.save(arquivo).toString();
    }

}