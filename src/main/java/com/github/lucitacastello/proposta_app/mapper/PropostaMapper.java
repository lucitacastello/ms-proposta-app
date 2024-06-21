package com.github.lucitacastello.proposta_app.mapper;

import com.github.lucitacastello.proposta_app.dto.PropostaRequestDTO;
import com.github.lucitacastello.proposta_app.dto.PropostaResponseDTO;
import com.github.lucitacastello.proposta_app.entities.Proposta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

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
    @Mapping(target = "integrada",ignore = true)
    @Mapping(target = "observacao",ignore = true)

    Proposta convertDtoToProposta(PropostaRequestDTO propostaRequestDTO);


    @Mapping(target = "nome", source = "usuario.nome")
    @Mapping(target = "sobrenome", source = "usuario.sobrenome")
    @Mapping(target = "cpf", source = "usuario.cpf")
    @Mapping(target = "telefone", source = "usuario.telefone")
    @Mapping(target = "renda", source = "usuario.renda")
    PropostaResponseDTO convertEntityToDto(Proposta proposta);

}
