package br.com.tareffa.awss3client.domain.mappers;

import java.util.List;
import java.util.stream.Collectors;

import br.com.tareffa.awss3client.domain.dtos.ArquivoDTO;
import br.com.tareffa.awss3client.domain.models.Arquivo;

public class ArquivoMapper {

    public static ArquivoDTO fromEntity(Arquivo arquivo) {
        return ArquivoDTO.builder()
                    .id(arquivo.getId())
                    .contabilidadeId(arquivo.getContabilidadeId())
                    .empresaId(arquivo.getEmpresaId())
                    .processado(arquivo.getProcessado())
                .build();
    }
    
    public static List<ArquivoDTO> fromEntities(List<Arquivo> arquivos) {
        return arquivos.stream().map(ArquivoMapper::fromEntity).collect(Collectors.toList());
    }

    public static Arquivo fromDto(ArquivoDTO arquivo) {
        return Arquivo.builder()
                    .id(arquivo.getId())
                    .contabilidadeId(arquivo.getContabilidadeId())
                    .empresaId(arquivo.getEmpresaId())
                    .processado(arquivo.getProcessado())
                .build();
    }

}
