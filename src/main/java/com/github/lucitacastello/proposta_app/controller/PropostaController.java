package com.github.lucitacastello.proposta_app.controller;

import com.github.lucitacastello.proposta_app.dto.PropostaRequestDTO;
import com.github.lucitacastello.proposta_app.dto.PropostaResponseDTO;
import com.github.lucitacastello.proposta_app.service.PropostaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@AllArgsConstructor

@RestController
@RequestMapping("/proposta")
public class PropostaController {

//    @Autowired
    private PropostaService propostaService;

    @PostMapping()
    public ResponseEntity<PropostaResponseDTO> criar(@RequestBody PropostaRequestDTO requestDTO){
        // retornando código 201 e o cabeçalho
        PropostaResponseDTO response = propostaService.criar(requestDTO);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri()).body(response);
    }

    @GetMapping
    public ResponseEntity<List<PropostaResponseDTO>> obterPropostas(){

//        List<PropostaResponseDTO> response = propostaService.obterProposta();
//        return ResponseEntity.ok(response);
//        ou
        return ResponseEntity.ok(propostaService.obterProposta());
    }

}
