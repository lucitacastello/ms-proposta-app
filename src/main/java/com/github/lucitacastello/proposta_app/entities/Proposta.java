package com.github.lucitacastello.proposta_app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "tb_proposta")
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valor")
    private Double valorSolicitado;
    private int prazoPagamento;
    private Boolean aprovada;
    private boolean integrada;
    private String observacao;

    //    1:1
    //salva o usuário no DB
    @OneToOne(cascade = CascadeType.PERSIST) //pra não dar erro obj transiente, pq usuário ainda não existe
    @JoinColumn(name = "id_usuario") //FK
    private Usuario usuario;


}
