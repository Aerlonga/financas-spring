package dev.financas.FinancasSpring.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.financas.FinancasSpring.model.entities.*;
import dev.financas.FinancasSpring.model.repository.UsuarioRepository;
import dev.financas.FinancasSpring.rest.dto.*;
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

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    private UsuarioRepository usuarioRepository; // Adicionado para o EmailUnicoValidator

    @Test
    @WithMockUser
    public void deveCriarUsuarioComSucesso() throws Exception {
        // Comportamento do validador
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.empty());

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

    @Test
    @WithMockUser
    public void deveBuscarDetalhesDoUsuarioComSucesso() throws Exception {
        Long usuarioId = 1L;
        UsuarioDetalhes detalhes = UsuarioDetalhes.builder().id(1L).cpf("12345678901").build();
        UsuarioDetalhesResponseDTO responseDTO = UsuarioDetalhesResponseDTO.builder().cpf("12345678901").build();

        when(usuarioDetalhesService.findByUsuarioId(usuarioId)).thenReturn(detalhes);
        when(usuarioDetalhesMapper.toResponseDTO(detalhes)).thenReturn(responseDTO);

        mockMvc.perform(get("/usuarios/{id}/detalhes", usuarioId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpf").value("12345678901"));
    }

    @Test
    @WithMockUser
    public void deveAtualizarDetalhesDoUsuarioComSucesso() throws Exception {
        Long usuarioId = 1L;
        UsuarioDetalhesUpdateDTO updateDTO = new UsuarioDetalhesUpdateDTO();
        updateDTO.setCpf("09876543210");

        UsuarioDetalhes detalhesAtualizados = UsuarioDetalhes.builder().id(1L).cpf("09876543210").build();
        UsuarioDetalhesResponseDTO responseDTO = UsuarioDetalhesResponseDTO.builder().cpf("09876543210").build();

        when(usuarioDetalhesService.createOrUpdate(eq(usuarioId), any(UsuarioDetalhesUpdateDTO.class))).thenReturn(detalhesAtualizados);
        when(usuarioDetalhesMapper.toResponseDTO(detalhesAtualizados)).thenReturn(responseDTO);

        mockMvc.perform(put("/usuarios/{id}/detalhes", usuarioId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpf").value("09876543210"));
    }

    @Test
    @WithMockUser
    public void deveBuscarFinanceiroDoUsuarioComSucesso() throws Exception {
        Long usuarioId = 1L;
        UsuarioFinanceiro financeiro = UsuarioFinanceiro.builder().id(1L).profissao("Engenheiro").build();
        UsuarioFinanceiroResponseDTO responseDTO = UsuarioFinanceiroResponseDTO.builder().profissao("Engenheiro").build();

        when(usuarioFinanceiroService.findByUsuarioId(usuarioId)).thenReturn(financeiro);
        when(usuarioFinanceiroMapper.toResponseDTO(financeiro)).thenReturn(responseDTO);

        mockMvc.perform(get("/usuarios/{id}/financeiro", usuarioId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.profissao").value("Engenheiro"));
    }

    @Test
    @WithMockUser
    public void deveAtualizarFinanceiroDoUsuarioComSucesso() throws Exception {
        Long usuarioId = 1L;
        UsuarioFinanceiroUpdateDTO updateDTO = new UsuarioFinanceiroUpdateDTO();
        updateDTO.setProfissao("Desenvolvedor");

        UsuarioFinanceiro financeiroAtualizado = UsuarioFinanceiro.builder().id(1L).profissao("Desenvolvedor").build();
        UsuarioFinanceiroResponseDTO responseDTO = UsuarioFinanceiroResponseDTO.builder().profissao("Desenvolvedor").build();

        when(usuarioFinanceiroService.createOrUpdate(eq(usuarioId), any(UsuarioFinanceiroUpdateDTO.class))).thenReturn(financeiroAtualizado);
        when(usuarioFinanceiroMapper.toResponseDTO(financeiroAtualizado)).thenReturn(responseDTO);

        mockMvc.perform(put("/usuarios/{id}/financeiro", usuarioId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.profissao").value("Desenvolvedor"));
    }

    @Test
    @WithMockUser
    public void deveBuscarPreferenciasDoUsuarioComSucesso() throws Exception {
        Long usuarioId = 1L;
        UsuarioPreferencias preferencias = UsuarioPreferencias.builder().id(1L).moedaPreferida("USD").build();
        UsuarioPreferenciasResponseDTO responseDTO = UsuarioPreferenciasResponseDTO.builder().moedaPreferida("USD").build();

        when(usuarioPreferenciasService.findByUsuarioId(usuarioId)).thenReturn(preferencias);
        when(usuarioPreferenciasMapper.toResponseDTO(preferencias)).thenReturn(responseDTO);

        mockMvc.perform(get("/usuarios/{id}/preferencias", usuarioId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.moedaPreferida").value("USD"));
    }

    @Test
    @WithMockUser
    public void deveAtualizarPreferenciasDoUsuarioComSucesso() throws Exception {
        Long usuarioId = 1L;
        UsuarioPreferenciasUpdateDTO updateDTO = new UsuarioPreferenciasUpdateDTO();
        updateDTO.setMoedaPreferida("EUR");

        UsuarioPreferencias preferenciasAtualizadas = UsuarioPreferencias.builder().id(1L).moedaPreferida("EUR").build();
        UsuarioPreferenciasResponseDTO responseDTO = UsuarioPreferenciasResponseDTO.builder().moedaPreferida("EUR").build();

        when(usuarioPreferenciasService.createOrUpdate(eq(usuarioId), any(UsuarioPreferenciasUpdateDTO.class))).thenReturn(preferenciasAtualizadas);
        when(usuarioPreferenciasMapper.toResponseDTO(preferenciasAtualizadas)).thenReturn(responseDTO);

        mockMvc.perform(put("/usuarios/{id}/preferencias", usuarioId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.moedaPreferida").value("EUR"));
    }
}
