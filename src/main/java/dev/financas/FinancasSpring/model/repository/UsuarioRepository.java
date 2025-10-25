package dev.financas.FinancasSpring.model.repository;

import dev.financas.FinancasSpring.model.entities.Usuario;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    @EntityGraph(attributePaths = { "detalhes", "financeiro", "preferencias" })
    Optional<Usuario> findWithAllRelationsByEmail(String email);
}
