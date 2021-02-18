package com.algamoney.algamoneyapi.repository.lancamento;

import com.algamoney.algamoneyapi.model.Lancamento;
import com.algamoney.algamoneyapi.repository.filter.LancamentoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LancamentoRepositoryQuery {

    Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);

}
