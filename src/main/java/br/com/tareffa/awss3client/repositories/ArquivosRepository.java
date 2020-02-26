package br.com.tareffa.awss3client.repositories;

import java.math.BigInteger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.tareffa.awss3client.domain.dtos.ArquivoDTO;
import br.com.tareffa.awss3client.domain.models.Arquivo;

public interface ArquivosRepository extends JpaRepository<Arquivo, BigInteger> {

	Page<Arquivo> findAll(ArquivoDTO arquivo, PageRequest of);
    
}
