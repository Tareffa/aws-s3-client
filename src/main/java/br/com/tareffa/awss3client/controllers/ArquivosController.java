package br.com.tareffa.awss3client.controllers;

import java.math.BigInteger;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.tareffa.awss3client.client.OAuthClient;
import br.com.tareffa.awss3client.domain.commands.SalvarArquivoRequest;
import br.com.tareffa.awss3client.domain.dtos.ArquivoDTO;
import br.com.tareffa.awss3client.domain.dtos.UserDTO;
import br.com.tareffa.awss3client.services.ArquivosService;
import br.com.tareffa.awss3client.utils.FileUtils;

@RestController
@RequestMapping("/api/v1/arquivos")
public class ArquivosController { // @formatter:off 

    @Inject
    ArquivosService service;
    
    @Inject
    OAuthClient oauthClient;

    @PostMapping(path = "", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> upload(
            @Valid SalvarArquivoRequest detalhes,
            @RequestParam("file") MultipartFile file, 
            OAuth2Authentication authentication) throws Exception {

        return ResponseEntity.ok(service.store(file, detalhes, authentication));
    }
    
    @GetMapping
    public ResponseEntity<?> findAll(@Valid ArquivoDTO arquivo, 
    								 @RequestHeader("Authorization") String authorization){
    	UserDTO userInfo = oauthClient.getUserInfo(authorization).getBody().getRecord();
    	return ResponseEntity.ok(service.findAll(arquivo, userInfo));
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> download(@PathVariable("id") BigInteger id, 
                                                     HttpServletRequest request, 
                                                     OAuth2Authentication authentication)  throws Exception {
        Resource resource = service.download(id, authentication);

        String contentDisposition = FileUtils.getContentDisposition(resource, "attachment");
        String contentType = FileUtils.getContentType(resource, request);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }

    @GetMapping("/{id}/view")
    public ResponseEntity<Resource> view(@PathVariable("id") BigInteger id, 
                                         HttpServletRequest request, 
                                         OAuth2Authentication authentication)  throws Exception {
        Resource resource = service.download(id, authentication);

        String contentDisposition = FileUtils.getContentDisposition(resource);
        String contentType = FileUtils.getContentType(resource, request);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }

}
