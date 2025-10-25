package dev.financas.FinancasSpring.model.repository;

import dev.financas.FinancasSpring.model.entities.UsuarioFinanceiro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioFinanceiroRepository extends JpaRepository<UsuarioFinanceiro, Long> {
    Optional<UsuarioFinanceiro> findByUsuarioId(Long usuarioId);
    boolean existsByUsuarioId(Long usuarioId);
}