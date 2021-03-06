package com.algamoney.algamoneyapi.service;

import com.algamoney.algamoneyapi.model.Pessoa;
import com.algamoney.algamoneyapi.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PessoaService {

    private PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public Pessoa atualizar(Long codigo, Pessoa pessoa){
        Pessoa pessoaSalva = buscarPessoaPeloCodigo(codigo);

        BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
        return pessoaRepository.save(pessoaSalva);
    }

    public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
        Pessoa pessoaSalva = buscarPessoaPeloCodigo(codigo);
        pessoaSalva.setAtivo(ativo);
        pessoaRepository.save(pessoaSalva);
    }

    private Pessoa buscarPessoaPeloCodigo(Long codigo) {
        Optional<Pessoa> pessoaSalva = pessoaRepository.findById(codigo);
        if (pessoaSalva.isEmpty()){
            throw new EmptyResultDataAccessException(1);
        }
        return pessoaSalva.get();
    }
}
