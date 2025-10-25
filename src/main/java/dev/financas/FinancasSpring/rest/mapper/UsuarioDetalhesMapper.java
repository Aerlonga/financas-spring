package dev.financas.FinancasSpring.rest.mapper;

import dev.financas.FinancasSpring.model.entities.UsuarioDetalhes;
import dev.financas.FinancasSpring.rest.dto.UsuarioDetalhesCreateDTO;
import dev.financas.FinancasSpring.rest.dto.UsuarioDetalhesResponseDTO;
import dev.financas.FinancasSpring.rest.dto.UsuarioDetalhesUpdateDTO;
import org.springframework.stereotype.Component;

@Component
public class UsuarioDetalhesMapper {

    public UsuarioDetalhes toEntity(UsuarioDetalhesCreateDTO dto) {
        if (dto == null)
            return null;

        return UsuarioDetalhes.builder()
                .dataNascimento(dto.getDataNascimento())
                .genero(dto.getGenero())
                .telefone(dto.getTelefone())
                .cpf(dto.getCpf())
                .cep(dto.getCep())
                .endereco(dto.getEndereco())
                .numero(dto.getNumero())
                .bairro(dto.getBairro())
                .cidade(dto.getCidade())
                .estado(dto.getEstado())
                .build();
    }

    public UsuarioDetalhes toEntity(UsuarioDetalhesUpdateDTO dto) {
        if (dto == null)
            return null;

        return UsuarioDetalhes.builder()
                .dataNascimento(dto.getDataNascimento())
                .genero(dto.getGenero())
                .telefone(dto.getTelefone())
                .cpf(dto.getCpf())
                .cep(dto.getCep())
                .endereco(dto.getEndereco())
                .numero(dto.getNumero())
                .bairro(dto.getBairro())
                .cidade(dto.getCidade())
                .estado(dto.getEstado())
                .build();
    }

    public UsuarioDetalhesResponseDTO toResponseDTO(UsuarioDetalhes entity) {
        if (entity == null)
            return null;

        return UsuarioDetalhesResponseDTO.builder()
                .dataNascimento(entity.getDataNascimento())
                .genero(entity.getGenero())
                .telefone(entity.getTelefone())
                .cpf(entity.getCpf())
                .cep(entity.getCep())
                .endereco(entity.getEndereco())
                .numero(entity.getNumero())
                .bairro(entity.getBairro())
                .cidade(entity.getCidade())
                .estado(entity.getEstado())
                .build();
    }

    public void updateEntity(UsuarioDetalhes entity, UsuarioDetalhesUpdateDTO dto) {
        if (dto == null || entity == null)
            return;

        if (dto.getDataNascimento() != null)
            entity.setDataNascimento(dto.getDataNascimento());
        if (dto.getGenero() != null)
            entity.setGenero(dto.getGenero());
        if (dto.getTelefone() != null)
            entity.setTelefone(dto.getTelefone());
        if (dto.getCpf() != null)
            entity.setCpf(dto.getCpf());
        if (dto.getCep() != null)
            entity.setCep(dto.getCep());
        if (dto.getEndereco() != null)
            entity.setEndereco(dto.getEndereco());
        if (dto.getNumero() != null)
            entity.setNumero(dto.getNumero());
        if (dto.getBairro() != null)
            entity.setBairro(dto.getBairro());
        if (dto.getCidade() != null)
            entity.setCidade(dto.getCidade());
        if (dto.getEstado() != null)
            entity.setEstado(dto.getEstado());
    }
}