package com.algamoney.algamoneyapi.service;

import com.algamoney.algamoneyapi.model.Lancamento;
import com.algamoney.algamoneyapi.model.Pessoa;
import com.algamoney.algamoneyapi.repository.LancamentoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LancamentoService {

    private LancamentoRepository lancamentoRepository;

    public LancamentoService(LancamentoRepository lancamentoRepository) {
        this.lancamentoRepository = lancamentoRepository;
    }

    public Lancamento atualizar(Long codigo, Lancamento lancamento){
        Lancamento lancamentoSalvo = buscarLancamentoPeloCodigo(codigo);
        BeanUtils.copyProperties(lancamento, lancamentoSalvo, "codigo");
        return lancamentoRepository.save(lancamentoSalvo);
    }

    private Lancamento buscarLancamentoPeloCodigo(Long codigo) {
        Optional<Lancamento> lancamentoSalvo = lancamentoRepository.findById(codigo);
        if (lancamentoSalvo.isEmpty()){
            throw new EmptyResultDataAccessException(1);
        }
        return lancamentoSalvo.get();
    }
}
