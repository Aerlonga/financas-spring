package dev.financas.FinancasSpring.model.repository;

import dev.financas.FinancasSpring.model.entities.UsuarioDetalhes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioDetalhesRepository extends JpaRepository<UsuarioDetalhes, Long> {
    Optional<UsuarioDetalhes> findByCpf(String cpf);
    boolean existsByCpf(String cpf);
    Optional<UsuarioDetalhes> findByUsuarioId(Long usuarioId);
}