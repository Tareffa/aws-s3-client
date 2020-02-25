package br.com.tareffa.awss3client.controllers;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.tareffa.awss3client.domain.commands.SalvarArquivoRequest;
import br.com.tareffa.awss3client.services.ArquivosService;

@RestController
@RequestMapping("/api/v1/arquivos")
public class ArquivosController { // @formatter:off

    @Inject
    ArquivosService service;

    @PostMapping(path = "", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> upload(
            @Valid SalvarArquivoRequest detalhes,
            @RequestParam("file") MultipartFile file, 
            OAuth2Authentication authentication) throws Exception {

        return ResponseEntity.ok(service.store(file, detalhes, authentication));
    }

}
