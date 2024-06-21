package com.github.lucitacastello.proposta_app.repository;

import com.github.lucitacastello.proposta_app.entities.Proposta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropostaRepository extends CrudRepository<Proposta, Long> {
}
