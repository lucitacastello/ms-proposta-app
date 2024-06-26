package com.github.lucitacastello.proposta_app.service;

import com.github.lucitacastello.proposta_app.dto.PropostaRequestDTO;
import com.github.lucitacastello.proposta_app.dto.PropostaResponseDTO;
import com.github.lucitacastello.proposta_app.entities.Proposta;
import com.github.lucitacastello.proposta_app.mapper.PropostaMapper;
import com.github.lucitacastello.proposta_app.repository.PropostaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropostaService {

    //recupera valores setados em application.properties
    private String exchange;

    private PropostaRepository propostaRepository;

    private NotificacaoRabbitService notificacaoRabbitService;

    public PropostaService(@Value("${rabbitmq.propostapendente.exchange}") String exchange,
                           PropostaRepository propostaRepository,
                           NotificacaoRabbitService notificacaoRabbitService) {
        this.exchange = exchange; // pega do application.properties
        this.propostaRepository = propostaRepository;
        this.notificacaoRabbitService = notificacaoRabbitService;
    }

    public PropostaResponseDTO criar(PropostaRequestDTO requestDTO) {

        Proposta proposta = PropostaMapper.INSTANCE.convertDtoToProposta(requestDTO);
        proposta = propostaRepository.save(proposta); //cadastro com sucesso

        //criar response -> objeto de resposta - response
//        PropostaResponseDTO response = PropostaMapper.INSTANCE.convertEntityToDto(proposta);
        // "proposta-pendente.ex" criada em RabbitMQConfiguration.java
        //notifica exchange
//        notificacaoRabbitService.notificar(proposta, exchange);
        notificarRabbitMQ(proposta);
        return PropostaMapper.INSTANCE.convertEntityToDto(proposta);
//        return response;
    }

    public List<PropostaResponseDTO> obterProposta() {

//        Iterable<Proposta> propostas =  propostaRepository.findAll();
//        PropostaMapper.INSTANCE.convertListEntityToListDto(propostas);
//        ou
        return PropostaMapper.INSTANCE.convertListEntityToListDto(propostaRepository.findAll());

    }
    //recebe response - se RabbitMQ estiver fora
    private void notificarRabbitMQ(Proposta proposta){

        try {
            notificacaoRabbitService.notificar(proposta, exchange);
        } catch (RuntimeException ex){
            //Rabbit fora do ar
            //atualizar registro DB
            // coluna integrada
            //pegar registros com integrada FALSE para atualizar depois
            proposta.setIntegrada(false);
            propostaRepository.save(proposta);

        }



    }
}
