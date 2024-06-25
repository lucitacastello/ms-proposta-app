package com.github.lucitacastello.proposta_app.service;

import com.github.lucitacastello.proposta_app.dto.PropostaRequestDTO;
import com.github.lucitacastello.proposta_app.dto.PropostaResponseDTO;
import com.github.lucitacastello.proposta_app.entities.Proposta;
import com.github.lucitacastello.proposta_app.mapper.PropostaMapper;
import com.github.lucitacastello.proposta_app.repository.PropostaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PropostaService {

    private PropostaRepository propostaRepository;

    private NotificacaoService notificacaoService;

    public PropostaResponseDTO criar(PropostaRequestDTO requestDTO) {

        Proposta proposta = PropostaMapper.INSTANCE.convertDtoToProposta(requestDTO);
        proposta = propostaRepository.save(proposta); //cadastro com sucesso

        //criar response -> objeto de resposta - response
        PropostaResponseDTO response = PropostaMapper.INSTANCE.convertEntityToDto(proposta);
        // "proposta-pendente.ex" criada em RabbitMQConfiguration.java
        //notifica exchange
        notificacaoService.notificar(response, "proposta-pendente.ex");

       // return PropostaMapper.INSTANCE.convertEntityToDto(proposta);
        return response;
    }

    public List<PropostaResponseDTO> obterProposta() {

//        Iterable<Proposta> propostas =  propostaRepository.findAll();
//        PropostaMapper.INSTANCE.convertListEntityToListDto(propostas);
//        ou
        return PropostaMapper.INSTANCE.convertListEntityToListDto(propostaRepository.findAll());

    }
}
