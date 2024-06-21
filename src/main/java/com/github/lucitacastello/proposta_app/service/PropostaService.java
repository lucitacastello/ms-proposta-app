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


    public PropostaResponseDTO criar(PropostaRequestDTO requestDTO) {

        Proposta proposta = PropostaMapper.INSTANCE.convertDtoToProposta(requestDTO);
        proposta = propostaRepository.save(proposta);

        return PropostaMapper.INSTANCE.convertEntityToDto(proposta);
    }

    public List<PropostaResponseDTO> obterProposta() {

//        Iterable<Proposta> propostas =  propostaRepository.findAll();
//        PropostaMapper.INSTANCE.convertListEntityToListDto(propostas);
//        ou
        return PropostaMapper.INSTANCE.convertListEntityToListDto(propostaRepository.findAll());

    }
}
