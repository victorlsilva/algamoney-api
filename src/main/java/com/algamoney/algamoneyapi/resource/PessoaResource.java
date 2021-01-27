package com.algamoney.algamoneyapi.resource;

import com.algamoney.algamoneyapi.model.Pessoa;
import com.algamoney.algamoneyapi.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

    private PessoaRepository pessoaRepository;

    public PessoaResource(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @GetMapping
    public List<Pessoa> listar(){return pessoaRepository.findAll();}

    @GetMapping("/{codigo}")
    public ResponseEntity<Optional<Pessoa>> buscaPeloCodigo(@PathVariable Long codigo){

        Optional<Pessoa> pessoaAchada = pessoaRepository.findById(codigo);

        if (pessoaAchada.isEmpty()){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(pessoaAchada);
        }
    }

    @PostMapping
    public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse res){

        Pessoa pessoaSalva = pessoaRepository.save(pessoa);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
                .buildAndExpand(pessoaSalva.getCodigo()).toUri();

        res.setHeader("Location", uri.toASCIIString());

        return ResponseEntity.created(uri).body(pessoaSalva);
    }

}
