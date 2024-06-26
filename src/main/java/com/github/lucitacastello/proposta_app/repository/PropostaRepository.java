package com.github.lucitacastello.proposta_app.repository;

import com.github.lucitacastello.proposta_app.entities.Proposta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropostaRepository extends CrudRepository<Proposta, Long> {

    //listar todas propostas não estão integradas - integrada = false
    //query derivada
    List<Proposta> findAllByIntegradaIsFalse();
}
