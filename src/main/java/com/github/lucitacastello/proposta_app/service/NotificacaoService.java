package com.github.lucitacastello.proposta_app.service;

import com.github.lucitacastello.proposta_app.dto.PropostaResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NotificacaoService {

    private RabbitTemplate rabbitTemplate; //injeção pelo constructor

    public void notificar(PropostaResponseDTO proposta, String exchange){
         rabbitTemplate.convertAndSend(exchange, "", proposta);
    }


}
