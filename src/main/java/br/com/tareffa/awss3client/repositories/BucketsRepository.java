package br.com.tareffa.awss3client.repositories;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.tareffa.awss3client.domain.models.Bucket;

public interface BucketsRepository extends JpaRepository<Bucket, BigInteger> {
 
    Optional<Bucket> findByAplicationId(String aplicationId);

}
