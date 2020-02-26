package br.com.tareffa.awss3client.repositories;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.tareffa.awss3client.domain.models.Arquivo;

public interface ArquivosRepository extends JpaRepository<Arquivo, BigInteger> {
   
}
