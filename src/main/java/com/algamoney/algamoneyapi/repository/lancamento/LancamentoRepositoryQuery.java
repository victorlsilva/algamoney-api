package com.algamoney.algamoneyapi.repository.lancamento;

import com.algamoney.algamoneyapi.model.Lancamento;
import com.algamoney.algamoneyapi.repository.filter.LancamentoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LancamentoRepositoryQuery {

    public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);

}
