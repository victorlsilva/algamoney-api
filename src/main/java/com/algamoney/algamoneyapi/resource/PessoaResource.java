package com.algamoney.algamoneyapi.resource;

import com.algamoney.algamoneyapi.event.RecursoCriadoEvent;
import com.algamoney.algamoneyapi.model.Pessoa;
import com.algamoney.algamoneyapi.repository.PessoaRepository;
import com.algamoney.algamoneyapi.service.PessoaService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private PessoaService pessoaService;

    public PessoaResource(PessoaRepository pessoaRepository, ApplicationEventPublisher publisher, PessoaService pessoaService) {
        this.pessoaRepository = pessoaRepository;
        this.publisher = publisher;
        this.pessoaService = pessoaService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
    public List<Pessoa> listar(){return pessoaRepository.findAll();}

    @GetMapping("/{codigo}")
    @PreAuthorize("hasAnyAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
    public ResponseEntity<Pessoa> buscaPeloCodigo(@PathVariable Long codigo){

        Optional<Pessoa> pessoaAchada = pessoaRepository.findById(codigo);

        if (pessoaAchada.isEmpty()){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(pessoaAchada.get());
        }
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
    public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse res){

        Pessoa pessoaSalva = pessoaRepository.save(pessoa);
        publisher.publishEvent(new RecursoCriadoEvent(this, res, pessoaSalva.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
    }

    @DeleteMapping("/{codigo}")
    @PreAuthorize("hasAnyAuthority('ROLE_REMOVER_PESSOA') and #oauth2.hasScope('write')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long codigo){
        pessoaRepository.deleteById(codigo);
    }

    @PutMapping("/{codigo}")
    @PreAuthorize("hasAnyAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
    public ResponseEntity<Pessoa> atualizar(@PathVariable Long codigo, @Valid @RequestBody Pessoa pessoa){
        return ResponseEntity.ok(pessoaService.atualizar(codigo, pessoa));
    }

    @PutMapping("/{codigo}/ativo")
    @PreAuthorize("hasAnyAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarPropriedadeAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo){
        pessoaService.atualizarPropriedadeAtivo(codigo, ativo);
    }

}
