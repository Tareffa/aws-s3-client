package br.com.tareffa.awss3client.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.tareffa.awss3client.domain.dtos.OrganizationDTO;
import br.com.tareffa.awss3client.domain.dtos.UserDTO;
import br.com.tareffa.awss3client.domain.responses.GenericResponse;


@FeignClient(name = "${oauth.service.name}", url = "${oauth.service.url}") // @formatter:off
public interface OAuthClient {
	
	@GetMapping("/oauth/userinfo")
	HttpEntity<GenericResponse<UserDTO>> getUserInfo(@RequestHeader("Authorization") String authorization);
	
	@GetMapping("/api/v1/organizations")
	HttpEntity<GenericResponse<OrganizationDTO>> getOrganizationInfo(@RequestHeader("Authorization") String authorization, @RequestParam String cnpj);	
	

}
