package dev.financas.FinancasSpring.rest.mapper;

import dev.financas.FinancasSpring.model.entities.UsuarioPreferencias;
import dev.financas.FinancasSpring.rest.dto.UsuarioPreferenciasCreateDTO;
import dev.financas.FinancasSpring.rest.dto.UsuarioPreferenciasResponseDTO;
import dev.financas.FinancasSpring.rest.dto.UsuarioPreferenciasUpdateDTO;
import org.springframework.stereotype.Component;

@Component
public class UsuarioPreferenciasMapper {

    public UsuarioPreferencias toEntity(UsuarioPreferenciasCreateDTO dto) {
        if (dto == null)
            return null;

        UsuarioPreferencias entity = new UsuarioPreferencias();
        updateEntity(entity, dto);
        return entity;
    }

    public UsuarioPreferencias toEntity(UsuarioPreferenciasUpdateDTO dto) {
        if (dto == null)
            return null;

        UsuarioPreferencias entity = new UsuarioPreferencias();
        updateEntity(entity, dto);
        return entity;
    }

    public UsuarioPreferenciasResponseDTO toResponseDTO(UsuarioPreferencias entity) {
        if (entity == null)
            return null;

        return UsuarioPreferenciasResponseDTO.builder()
                .id(entity.getId())
                .temaInterface(entity.getTemaInterface())
                .notificacoesAtivadas(entity.getNotificacoesAtivadas())
                .moedaPreferida(entity.getMoedaPreferida())
                .avatarUrl(entity.getAvatarUrl())
                .build();
    }

    public void updateEntity(UsuarioPreferencias entity, UsuarioPreferenciasUpdateDTO dto) {
        if (dto == null || entity == null)
            return;

        if (dto.getTemaInterface() != null)
            entity.setTemaInterface(dto.getTemaInterface());
        if (dto.getNotificacoesAtivadas() != null)
            entity.setNotificacoesAtivadas(dto.getNotificacoesAtivadas());
        if (dto.getMoedaPreferida() != null)
            entity.setMoedaPreferida(dto.getMoedaPreferida());
        if (dto.getAvatarUrl() != null)
            entity.setAvatarUrl(dto.getAvatarUrl());
    }

    private void updateEntity(UsuarioPreferencias entity, UsuarioPreferenciasCreateDTO dto) {
        if (dto.getTemaInterface() != null)
            entity.setTemaInterface(dto.getTemaInterface());
        if (dto.getNotificacoesAtivadas() != null)
            entity.setNotificacoesAtivadas(dto.getNotificacoesAtivadas());
        if (dto.getMoedaPreferida() != null)
            entity.setMoedaPreferida(dto.getMoedaPreferida());
        if (dto.getAvatarUrl() != null)
            entity.setAvatarUrl(dto.getAvatarUrl());
    }
}