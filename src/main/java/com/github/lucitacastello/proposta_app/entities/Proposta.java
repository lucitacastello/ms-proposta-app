package com.github.lucitacastello.proposta_app.entities;

import jakarta.persistence.*;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double valorSolicitado;
    private int prazoPagamento;
    private Boolean aprovado;
    private boolean integrada;
    private String observacao;

    //    1:1
    @OneToOne
    @JoinColumn(name = "id_usuario") //FK
    private Usuario usuario;


}
