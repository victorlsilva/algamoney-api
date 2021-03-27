package com.algamoney.algamoneyapi.repository;

import com.algamoney.algamoneyapi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    public Optional<Usuario> findByEmail(String Email);

}
