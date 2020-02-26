package br.com.tareffa.awss3client.domain.dtos;

import java.io.Serializable;
import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private BigInteger id;
	private String externalId;
	private String name;
	private Integer type;
	private String cnpj;
	private String codigoERP;
	private String email;
	private String avatar;
	private BigInteger organizationId;
	private BigInteger scriptId; 

}
