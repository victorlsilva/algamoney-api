package com.algamoney.algamoneyapi.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name="pessoa")
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @NotNull
    @Size(min = 2, max = 45)
    private String nome;

    @NotNull
    private Boolean ativo;

    @Embedded
    private Endereco endereco;
}
