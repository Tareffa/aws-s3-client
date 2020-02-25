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
@Table(name = "buckets")
public class Bucket implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "buckets_sequence", sequenceName = "buckets_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "buckets_sequence")
    private BigInteger id;

    @Column
    private String aplicationId;

    @Column
    private String bucketName;

    @Column
    private String accessKeyId;

    @Column
    private String secretAccessKey;

    @Column
    private String region;
    
    @Column
    private LocalDateTime dataCriacao;

    @Column
    private LocalDateTime dataAtualizacao;


}