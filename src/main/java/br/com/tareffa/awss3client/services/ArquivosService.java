package br.com.tareffa.awss3client.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.tareffa.awss3client.domain.commands.SalvarArquivoRequest;
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

    public String store(MultipartFile file, SalvarArquivoRequest detalhes, OAuth2Authentication authentication) throws Exception {

        Bucket bucket = this.bucketRepository.findByAplicationId(detalhes.getApplicationId())
                             .orElseThrow(() -> new NoResultException("Bucket não encontrado!"));

        File tmp = createTemporaryFile(file);

        awsService.upload(tmp, bucket);

        return "";
    }

    File createTemporaryFile(MultipartFile file) throws IOException {

        System.out.println("" + file.getOriginalFilename());
        System.out.println("" + file.getOriginalFilename());

        String originalFilename = file.getOriginalFilename();
        File tmp = File.createTempFile("", originalFilename);

        Files.copy(
            file.getInputStream(), 
            Paths.get(tmp.getAbsolutePath()), 
            StandardCopyOption.REPLACE_EXISTING
        );

        return tmp;
    }

}