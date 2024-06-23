package com.github.lucitacastello.proposta_app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PropostaResponseDTO {

    private Long id;
    private String nome;
    private String sobrenome;
    private String cpf;
    private String telefone;
    private Double renda;

    //modificado para String para solucionar o problema
    //apresentar o valor no front
    private String valorSolicitadoFmt;

    private int prazoPagamento;
    private Boolean aprovada;
    private String observacao;

}
