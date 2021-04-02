package com.algamoney.algamoneyapi.repository;

import com.algamoney.algamoneyapi.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    List<Pessoa> findByNomeStartingWith(String nome);
}
