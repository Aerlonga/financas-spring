package dev.financas.FinancasSpring.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.financas.FinancasSpring.model.entities.Usuario;
import dev.financas.FinancasSpring.rest.dto.UsuarioCreateDTO;
import dev.financas.FinancasSpring.rest.dto.UsuarioResponseDTO;
import dev.financas.FinancasSpring.rest.mapper.UsuarioDetalhesMapper;
import dev.financas.FinancasSpring.rest.mapper.UsuarioFinanceiroMapper;
import dev.financas.FinancasSpring.rest.mapper.UsuarioMapper;
import dev.financas.FinancasSpring.rest.mapper.UsuarioPreferenciasMapper;
import dev.financas.FinancasSpring.security.JwtUtil;
import dev.financas.FinancasSpring.services.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import java.util.Optional;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private UsuarioMapper usuarioMapper;

    @MockBean
    private UsuarioDetalhesService usuarioDetalhesService;

    @MockBean
    private UsuarioDetalhesMapper usuarioDetalhesMapper;

    @MockBean
    private UsuarioFinanceiroService usuarioFinanceiroService;

    @MockBean
    private UsuarioFinanceiroMapper usuarioFinanceiroMapper;

    @MockBean
    private UsuarioPreferenciasService usuarioPreferenciasService;

    @MockBean
    private UsuarioPreferenciasMapper usuarioPreferenciasMapper;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private dev.financas.FinancasSpring.model.repository.UsuarioRepository usuarioRepository; // Adicionado para o EmailUnicoValidator

    @Test
    @WithMockUser
    public void deveCriarUsuarioComSucesso() throws Exception {
        // Comportamento do validador
        when(usuarioRepository.findByEmail(anyString())).thenReturn(java.util.Optional.empty());

        // Dados de entrada
        UsuarioCreateDTO createDTO = UsuarioCreateDTO.builder()
                .nomeCompleto("Nome Teste")
                .email("teste@email.com")
                .senha("senha123")
                .build();

        // Mock do mapper e service
        Usuario usuario = Usuario.builder()
                .id(1L)
                .nomeCompleto("Nome Teste")
                .email("teste@email.com")
                .build();

        UsuarioResponseDTO responseDTO = UsuarioResponseDTO.builder()
                .id(1L)
                .nomeCompleto("Nome Teste")
                .email("teste@email.com")
                .build();

        when(usuarioMapper.toEntity(any(UsuarioCreateDTO.class))).thenReturn(usuario);
        when(usuarioService.save(any(Usuario.class))).thenReturn(usuario);
        when(usuarioMapper.toResponseDTO(any(Usuario.class))).thenReturn(responseDTO);

        // Execução da requisição e verificação
        mockMvc.perform(post("/usuarios")
                .with(csrf()) // Adiciona o token CSRF para o teste
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nomeCompleto").value("Nome Teste"))
                .andExpect(jsonPath("$.email").value("teste@email.com"));
    }

    @Test
    @WithMockUser
    public void deveBuscarUsuarioPorIdComSucesso() throws Exception {
        // Dados de entrada
        Long usuarioId = 1L;
        Usuario usuario = Usuario.builder()
                .id(usuarioId)
                .nomeCompleto("Nome Teste")
                .email("teste@email.com")
                .build();

        UsuarioResponseDTO responseDTO = UsuarioResponseDTO.builder()
                .id(usuarioId)
                .nomeCompleto("Nome Teste")
                .email("teste@email.com")
                .build();

        // Mock do service e mapper
        when(usuarioService.findById(usuarioId)).thenReturn(usuario);
        when(usuarioMapper.toResponseDTO(usuario)).thenReturn(responseDTO);

        // Execução da requisição e verificação
        mockMvc.perform(get("/usuarios/{id}", usuarioId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(usuarioId))
                .andExpect(jsonPath("$.nomeCompleto").value("Nome Teste"))
                .andExpect(jsonPath("$.email").value("teste@email.com"));
    }
}
