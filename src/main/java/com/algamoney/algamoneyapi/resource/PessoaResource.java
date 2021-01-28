package com.algamoney.algamoneyapi.resource;

import com.algamoney.algamoneyapi.event.RecursoCriadoEvent;
import com.algamoney.algamoneyapi.model.Pessoa;
import com.algamoney.algamoneyapi.repository.PessoaRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

    private PessoaRepository pessoaRepository;
    private ApplicationEventPublisher publisher;

    public PessoaResource(PessoaRepository pessoaRepository, ApplicationEventPublisher publisher) {
        this.pessoaRepository = pessoaRepository;
        this.publisher = publisher;
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
        publisher.publishEvent(new RecursoCriadoEvent(this, res, pessoaSalva.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
    }

}
