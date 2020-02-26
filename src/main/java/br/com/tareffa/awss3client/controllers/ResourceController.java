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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.tareffa.awss3client.domain.commands.SalvarArquivoRequest;
import br.com.tareffa.awss3client.services.ArquivosService;
import br.com.tareffa.awss3client.utils.FileUtils;

@RestController
@RequestMapping("/resources")
public class ResourceController { // @formatter:off

    @Inject
    ArquivosService service;

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
