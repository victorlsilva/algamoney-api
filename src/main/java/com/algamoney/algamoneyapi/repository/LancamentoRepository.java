package com.algamoney.algamoneyapi.repository;

import com.algamoney.algamoneyapi.model.Lancamento;
import com.algamoney.algamoneyapi.repository.lancamento.LancamentoRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {

}
