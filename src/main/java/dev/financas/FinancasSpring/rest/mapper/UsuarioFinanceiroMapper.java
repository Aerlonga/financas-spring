package dev.financas.FinancasSpring.rest.mapper;

import dev.financas.FinancasSpring.model.entities.UsuarioFinanceiro;
import dev.financas.FinancasSpring.rest.dto.UsuarioFinanceiroCreateDTO;
import dev.financas.FinancasSpring.rest.dto.UsuarioFinanceiroResponseDTO;
import dev.financas.FinancasSpring.rest.dto.UsuarioFinanceiroUpdateDTO;
import org.springframework.stereotype.Component;

@Component
public class UsuarioFinanceiroMapper {

    public UsuarioFinanceiro toEntity(UsuarioFinanceiroCreateDTO dto) {
        if (dto == null)
            return null;

        return UsuarioFinanceiro.builder()
                .profissao(dto.getProfissao())
                .rendaMensal(dto.getRendaMensal())
                .tipoRenda(dto.getTipoRenda())
                .objetivoFinanceiro(dto.getObjetivoFinanceiro())
                .metaPoupancaMensal(dto.getMetaPoupancaMensal())
                .build();
    }

    public UsuarioFinanceiro toEntity(UsuarioFinanceiroUpdateDTO dto) {
        if (dto == null)
            return null;

        return UsuarioFinanceiro.builder()
                .profissao(dto.getProfissao())
                .rendaMensal(dto.getRendaMensal())
                .tipoRenda(dto.getTipoRenda())
                .objetivoFinanceiro(dto.getObjetivoFinanceiro())
                .metaPoupancaMensal(dto.getMetaPoupancaMensal())
                .build();
    }

    public UsuarioFinanceiroResponseDTO toResponseDTO(UsuarioFinanceiro entity) {
        if (entity == null)
            return null;

        return UsuarioFinanceiroResponseDTO.builder()
                .id(entity.getId())
                .profissao(entity.getProfissao())
                .rendaMensal(entity.getRendaMensal())
                .tipoRenda(entity.getTipoRenda())
                .objetivoFinanceiro(entity.getObjetivoFinanceiro())
                .metaPoupancaMensal(entity.getMetaPoupancaMensal())
                .build();
    }

    public void updateEntity(UsuarioFinanceiro entity, UsuarioFinanceiroUpdateDTO dto) {
        if (dto == null || entity == null)
            return;

        if (dto.getProfissao() != null)
            entity.setProfissao(dto.getProfissao());
        if (dto.getRendaMensal() != null)
            entity.setRendaMensal(dto.getRendaMensal());
        if (dto.getTipoRenda() != null)
            entity.setTipoRenda(dto.getTipoRenda());
        if (dto.getObjetivoFinanceiro() != null)
            entity.setObjetivoFinanceiro(dto.getObjetivoFinanceiro());
        if (dto.getMetaPoupancaMensal() != null)
            entity.setMetaPoupancaMensal(dto.getMetaPoupancaMensal());
    }
}