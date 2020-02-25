package br.com.tareffa.awss3client.domain.commands;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class SalvarArquivoRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String cnpjContabilidade;

    @NotBlank
    private String cnpjEmpresa;

    @NotBlank
    private String applicationId;

}
