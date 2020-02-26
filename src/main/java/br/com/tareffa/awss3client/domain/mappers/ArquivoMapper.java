package br.com.tareffa.awss3client.domain.mappers;

import br.com.tareffa.awss3client.domain.dtos.ArquivoDTO;
import br.com.tareffa.awss3client.domain.models.Arquivo;

public class ArquivoMapper {

    public static ArquivoDTO fromEntity(Arquivo arquivo) {
        return ArquivoDTO.builder()
                    .id(arquivo.getId())
                .build();
    }

    public static Arquivo fromDto(ArquivoDTO arquivo) {
        return Arquivo.builder()
                    .id(arquivo.getId())
                .build();
    }

}
