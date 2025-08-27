package dev.financas.FinancasSpring.model.repository;

import dev.financas.FinancasSpring.model.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository <Usuario, Long> {
    boolean existsByEmail(String email);
}
