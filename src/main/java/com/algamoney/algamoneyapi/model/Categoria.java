package com.algamoney.algamoneyapi.model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;
    private String nome;

}
