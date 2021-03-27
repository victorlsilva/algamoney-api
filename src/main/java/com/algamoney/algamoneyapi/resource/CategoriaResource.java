package com.algamoney.algamoneyapi.resource;

import com.algamoney.algamoneyapi.event.RecursoCriadoEvent;
import com.algamoney.algamoneyapi.model.Categoria;
import com.algamoney.algamoneyapi.repository.CategoriaRepository;
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
@RequestMapping("/categorias")
public class CategoriaResource {

    private CategoriaRepository categoriaRepository;
    private ApplicationEventPublisher publisher;

    public CategoriaResource(CategoriaRepository categoriaRepository, ApplicationEventPublisher publisher) {
        this.categoriaRepository = categoriaRepository;
        this.publisher = publisher;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public List<Categoria> listar(){
        return categoriaRepository.findAll();
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
    public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse res){

        Categoria categoriaSalva = categoriaRepository.save(categoria);
        publisher.publishEvent(new RecursoCriadoEvent(this, res, categoriaSalva.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
    }

    @GetMapping("/{codigo}")
    @PreAuthorize("hasAnyAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    public ResponseEntity<Categoria> buscaPeloCodigo(@PathVariable Long codigo){

        Optional<Categoria> categoriaAchada = categoriaRepository.findById(codigo);

        if (categoriaAchada.isEmpty()){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(categoriaAchada.get());
        }
    }
}
