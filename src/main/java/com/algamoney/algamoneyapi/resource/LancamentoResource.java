package com.algamoney.algamoneyapi.resource;

import com.algamoney.algamoneyapi.event.RecursoCriadoEvent;
import com.algamoney.algamoneyapi.model.Lancamento;
import com.algamoney.algamoneyapi.repository.LancamentoRepository;
import com.algamoney.algamoneyapi.repository.filter.LancamentoFilter;
import com.algamoney.algamoneyapi.service.LancamentoService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

    private LancamentoRepository lancamentoRepository;
    private ApplicationEventPublisher publisher;
    private LancamentoService lancamentoService;

    public LancamentoResource(LancamentoRepository lancamentoRepository, ApplicationEventPublisher publisher, LancamentoService lancamentoService) {
        this.lancamentoRepository = lancamentoRepository;
        this.publisher = publisher;
        this.lancamentoService = lancamentoService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
    public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable){
        return lancamentoRepository.filtrar(lancamentoFilter, pageable);
    }

    @GetMapping("/{codigo}")
    @PreAuthorize("hasAnyAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
    public ResponseEntity<Lancamento> buscaPorCodigo(@PathVariable Long codigo){
        Optional<Lancamento> lancamento = lancamentoRepository.findById(codigo);

        if(lancamento.isEmpty()){
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok(lancamento.get());
        }
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
    public ResponseEntity<Lancamento> salvar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response){
        Lancamento lancamentoSalvo = lancamentoRepository.save(lancamento);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
    }

    @DeleteMapping("/{codigo}")
    @PreAuthorize("hasAnyAuthority('ROLE_REMOVER_LANCAMENTO') and #oauth2.hasScope('write')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long codigo){
        lancamentoRepository.deleteById(codigo);
    }

    @PutMapping("/{codigo}")
    @PreAuthorize("hasAnyAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
    public ResponseEntity<Lancamento> atualizar(@PathVariable Long codigo, @RequestBody Lancamento lancamento){
        return ResponseEntity.ok(lancamentoService.atualizar(codigo,lancamento));
    }

}
