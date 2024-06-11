package com.github.lucitacastello.proposta_app.entities;

import jakarta.persistence.*;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String sobrenome;
    private String cpf;
    private String telefone;
    private Double renda;

//    1:1
    @OneToOne(mappedBy = "usuario")
    private Proposta proposta;
}
