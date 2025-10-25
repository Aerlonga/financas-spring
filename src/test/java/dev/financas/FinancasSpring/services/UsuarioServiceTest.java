package dev.financas.FinancasSpring.services;

import dev.financas.FinancasSpring.exceptions.BusinessException;
import dev.financas.FinancasSpring.exceptions.ResourceNotFoundException;
import dev.financas.FinancasSpring.model.entities.Usuario;
import dev.financas.FinancasSpring.model.repository.UsuarioRepository;
import dev.financas.FinancasSpring.rest.mapper.UsuarioMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void deveSalvarNovoUsuarioComSucesso() {
        // Cenário
        Usuario novoUsuario = Usuario.builder().email("novo@email.com").build();
        when(usuarioRepository.existsByEmail(anyString())).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(novoUsuario);

        // Execução
        Usuario usuarioSalvo = usuarioService.save(novoUsuario);

        // Verificação
        assertThat(usuarioSalvo).isNotNull();
        assertThat(usuarioSalvo.getCriadoPor()).isEqualTo("novo@email.com");
        verify(usuarioRepository).save(novoUsuario); // Verifica se o método save foi chamado
    }

    @Test
    void naoDeveSalvarUsuarioComEmailDuplicado() {
        // Cenário
        Usuario usuarioExistente = Usuario.builder().email("existente@email.com").build();
        when(usuarioRepository.existsByEmail("existente@email.com")).thenReturn(true);

        // Execução e Verificação
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            usuarioService.save(usuarioExistente);
        });

        assertThat(exception.getMessage()).isEqualTo("O e-mail informado já está em uso.");
        verify(usuarioRepository, never()).save(any(Usuario.class)); // Verifica que o save NUNCA foi chamado
    }

    @Test
    void deveEncontrarUsuarioPorIdComSucesso() {
        // Cenário
        Long id = 1L;
        Usuario usuario = Usuario.builder().id(id).build();
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        // Execução
        Usuario usuarioEncontrado = usuarioService.findById(id);

        // Verificação
        assertThat(usuarioEncontrado).isNotNull();
        assertThat(usuarioEncontrado.getId()).isEqualTo(id);
    }

    @Test
    void deveLancarExcecaoAoNaoEncontrarUsuarioPorId() {
        // Cenário
        Long id = 99L;
        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        // Execução e Verificação
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            usuarioService.findById(id);
        });

        assertThat(exception.getMessage()).isEqualTo("Usuário não encontrado com o ID: " + id);
    }
}
