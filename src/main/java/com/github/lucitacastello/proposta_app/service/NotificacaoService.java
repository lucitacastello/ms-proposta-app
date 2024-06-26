package com.github.lucitacastello.proposta_app.service;

import com.github.lucitacastello.proposta_app.dto.PropostaResponseDTO;
import com.github.lucitacastello.proposta_app.entities.Proposta;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NotificacaoService {

    private RabbitTemplate rabbitTemplate; //injeção pelo constructor

    //alterado pq se RbbitMQ estiver fora
    public void notificar(Proposta proposta, String exchange){
         rabbitTemplate.convertAndSend(exchange, "", proposta);
    }


}
