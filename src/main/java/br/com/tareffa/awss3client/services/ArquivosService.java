package br.com.tareffa.awss3client.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.MessageFormat;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.tareffa.awss3client.domain.commands.SalvarArquivoRequest;
import br.com.tareffa.awss3client.domain.dtos.ArquivoDTO;
import br.com.tareffa.awss3client.domain.dtos.UserDTO;
import br.com.tareffa.awss3client.domain.models.Arquivo;
import br.com.tareffa.awss3client.domain.models.Bucket;
import br.com.tareffa.awss3client.repositories.ArquivosRepository;
import br.com.tareffa.awss3client.repositories.BucketsRepository;

@Service
public class ArquivosService {

    @Inject
    BucketsRepository bucketRepository;

    @Inject
    ArquivosRepository arquivosRepository;

    @Inject
    AwsService awsService;

    public String store(MultipartFile file, SalvarArquivoRequest detalhes, OAuth2Authentication authentication)
            throws Exception {

        Bucket bucket = this.bucketRepository.findByAplicationId(detalhes.getApplicationId())
                .orElseThrow(() -> new NoResultException("Bucket não encontrado!"));

        File tmp = createTemporaryFile(file);

        String filename = MessageFormat.format(
            "{0}__{1}", UUID.randomUUID().toString(), file.getOriginalFilename()
        );

        awsService.upload(tmp, filename, bucket);

        return "";
    }

    private File createTemporaryFile(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();

        File tmp = File.createTempFile(UUID.randomUUID().toString(), getFileExtension(originalFilename));
        tmp.deleteOnExit();

        Files.copy(file.getInputStream(), Paths.get(tmp.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);

        return tmp;
    }

    private String getFileExtension(String filename) {
        return filename.contains(".") ? filename.substring(filename.lastIndexOf(".") + 1) : ".tmp";
    }

	public Page<Arquivo> findAll(ArquivoDTO arquivo, UserDTO userInfo) {
		if(arquivo.getContabilidadeId() != null) {
			arquivo.setContabilidadeId(userInfo.getOrganizationId());
		}
		return null;
	}

}