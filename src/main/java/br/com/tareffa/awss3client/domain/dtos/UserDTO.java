package br.com.tareffa.awss3client.domain.dtos;

import java.io.Serializable;
import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO  implements Serializable {
	
	private static final long serialVersionUID = 1L;

	BigInteger id;
	String username;
	String email;
	String avatar;
	String firstName;
	String lastName;
	Integer type;
	
	BigInteger organizationId;
	OrganizationDTO organization;

}
