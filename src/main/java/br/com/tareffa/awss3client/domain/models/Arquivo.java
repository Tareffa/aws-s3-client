package br.com.tareffa.awss3client.domain.models;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "arquivos")
public class Arquivo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "arquivos_sequence", sequenceName = "arquivos_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "arquivos_sequence")
    private BigInteger id;

    @Column
    private String nomeOriginal;

    @Column
    private String nomeAws;

    @Column
    private String urlAws;

    @Column
    private BigInteger contabilidadeId;

    @Column
    private BigInteger empresaId;

    @Column
    private String applicationId;

    @Column
    private Boolean processado;

    @Column
    private LocalDateTime dataCriacao;

    @Column
    private LocalDateTime dataAtualizacao;


}