package com.github.lucitacastello.proposta_app.mapper;

import com.github.lucitacastello.proposta_app.dto.PropostaRequestDTO;
import com.github.lucitacastello.proposta_app.dto.PropostaResponseDTO;
import com.github.lucitacastello.proposta_app.entities.Proposta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.text.NumberFormat;
import java.util.List;

@Mapper
public interface PropostaMapper {

    //para interagir com essa interface
    //para chamar
    //vai ser usada na service
    PropostaMapper INSTANCE = Mappers.getMapper(PropostaMapper.class);

    //target -> saída  --  source -> entrada
    @Mapping(target = "usuario.nome", source = "nome")
    @Mapping(target = "usuario.sobrenome", source = "sobrenome")
    @Mapping(target = "usuario.cpf", source = "cpf")
    @Mapping(target = "usuario.telefone", source = "telefone")
    @Mapping(target = "usuario.renda", source = "renda")
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "aprovada",ignore = true)
    @Mapping(target = "integrada",constant = "true") //colocar como padrão true pq se Rabbit fora, fica false
    @Mapping(target = "observacao",ignore = true)

    Proposta convertDtoToProposta(PropostaRequestDTO propostaRequestDTO);


    @Mapping(target = "nome", source = "usuario.nome")
    @Mapping(target = "sobrenome", source = "usuario.sobrenome")
    @Mapping(target = "cpf", source = "usuario.cpf")
    @Mapping(target = "telefone", source = "usuario.telefone")
    @Mapping(target = "renda", source = "usuario.renda")
        //mapeando String para Double
    @Mapping(target = "valorSolicitadoFmt", expression = "java(setValorSolicitadoFmt(proposta))")
    PropostaResponseDTO convertEntityToDto(Proposta proposta);


    //método para retornar List de Propostas
    List<PropostaResponseDTO> convertListEntityToListDto(Iterable<Proposta> propostas);

    default String setValorSolicitadoFmt(Proposta proposta){
        return NumberFormat.getCurrencyInstance().format(proposta.getValorSolicitado());
    }
}
