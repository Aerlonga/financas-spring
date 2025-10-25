package dev.financas.FinancasSpring.model.repository;

import dev.financas.FinancasSpring.model.entities.UsuarioPreferencias;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioPreferenciasRepository extends JpaRepository<UsuarioPreferencias, Long> {
    Optional<UsuarioPreferencias> findByUsuarioId(Long usuarioId);
    boolean existsByUsuarioId(Long usuarioId);
}
