package com.github.lucitacastello.proposta_app.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_proposta")
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valor")
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
