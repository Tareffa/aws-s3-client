package br.com.tareffa.awss3client.services;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ArquivosService {

    public String store(MultipartFile file, OAuth2Authentication authentication) {
        return "";
    }

}