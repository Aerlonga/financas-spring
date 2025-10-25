package dev.financas.FinancasSpring.rest.controllers;

import dev.financas.FinancasSpring.rest.dto.UsuarioCreateDTO;
import dev.financas.FinancasSpring.rest.dto.UsuarioDetalhesResponseDTO;
import dev.financas.FinancasSpring.rest.dto.UsuarioDetalhesUpdateDTO;
import dev.financas.FinancasSpring.rest.dto.UsuarioFinanceiroResponseDTO;
import dev.financas.FinancasSpring.rest.dto.UsuarioFinanceiroUpdateDTO;
import dev.financas.FinancasSpring.rest.dto.UsuarioPreferenciasResponseDTO;
import dev.financas.FinancasSpring.rest.dto.UsuarioPreferenciasUpdateDTO;
import dev.financas.FinancasSpring.rest.dto.UsuarioResponseDTO;
import dev.financas.FinancasSpring.rest.dto.UsuarioUpdateDTO;
import dev.financas.FinancasSpring.rest.mapper.UsuarioDetalhesMapper;
import dev.financas.FinancasSpring.rest.mapper.UsuarioFinanceiroMapper;
import dev.financas.FinancasSpring.rest.mapper.UsuarioPreferenciasMapper;
import dev.financas.FinancasSpring.rest.mapper.UsuarioMapper;
import dev.financas.FinancasSpring.services.UsuarioService;
import dev.financas.FinancasSpring.services.UsuarioDetalhesService;
import dev.financas.FinancasSpring.services.UsuarioFinanceiroService;
import dev.financas.FinancasSpring.services.UsuarioPreferenciasService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;
    private final UsuarioDetalhesService usuarioDetalhesService;
    private final UsuarioDetalhesMapper usuarioDetalhesMapper;
    private final UsuarioFinanceiroService usuarioFinanceiroService;
    private final UsuarioFinanceiroMapper usuarioFinanceiroMapper;
    private final UsuarioPreferenciasService usuarioPreferenciasService;
    private final UsuarioPreferenciasMapper usuarioPreferenciasMapper;

    public UsuarioController(UsuarioService usuarioService,
            UsuarioMapper usuarioMapper,
            UsuarioDetalhesService usuarioDetalhesService,
            UsuarioDetalhesMapper usuarioDetalhesMapper,
            UsuarioFinanceiroService usuarioFinanceiroService,
            UsuarioFinanceiroMapper usuarioFinanceiroMapper,
            UsuarioPreferenciasService usuarioPreferenciasService,
            UsuarioPreferenciasMapper usuarioPreferenciasMapper) {
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
        this.usuarioDetalhesService = usuarioDetalhesService;
        this.usuarioDetalhesMapper = usuarioDetalhesMapper;
        this.usuarioFinanceiroService = usuarioFinanceiroService;
        this.usuarioFinanceiroMapper = usuarioFinanceiroMapper;
        this.usuarioPreferenciasService = usuarioPreferenciasService;
        this.usuarioPreferenciasMapper = usuarioPreferenciasMapper;
    }

    @GetMapping
    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista com todos os usuários cadastrados")
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
        var usuarios = usuarioService.findAll();
        var response = usuarios.stream()
                .map(usuarioMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna um usuário específico a partir do seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    // @PreAuthorize("@securityUtil.isOwnerOrAdmin(#id) or hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        var usuario = usuarioService.findById(id);
        return ResponseEntity.ok(usuarioMapper.toResponseDTO(usuario));
    }

    @PostMapping
    @Operation(summary = "Criar um novo usuário", description = "Cadastra um novo usuário no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos ou e-mail já existente")
    })
    public ResponseEntity<UsuarioResponseDTO> criar(@Valid @RequestBody UsuarioCreateDTO dto) {
        var usuario = usuarioMapper.toEntity(dto);
        var salvo = usuarioService.save(usuario);
        var response = usuarioMapper.toResponseDTO(salvo);

        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(salvo.getId())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um usuário", description = "Atualiza os dados de um usuário existente a partir do seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    // @PreAuthorize("@securityUtil.isOwnerOrAdmin(#id)")
    public ResponseEntity<UsuarioResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioUpdateDTO dto) {

        dto.setId(id);
        var usuarioAtualizado = usuarioService.atualizar(id, dto);
        return ResponseEntity.ok(usuarioMapper.toResponseDTO(usuarioAtualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um usuário (Apenas ADMIN)", description = "Exclui um usuário do sistema a partir do seu ID. Requer permissão de ADMIN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado. Requer permissão de ADMIN."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        usuarioService.deleteById(id);
    }

    // Endpoints para UsuarioDetalhes

    @GetMapping("/{id}/detalhes")
    @Operation(summary = "Buscar detalhes de um usuário", description = "Retorna os detalhes de um usuário específico a partir do seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalhes do usuário encontrados"),
            @ApiResponse(responseCode = "404", description = "Usuário ou detalhes não encontrados")
    })
    // @PreAuthorize("@securityUtil.isOwnerOrAdmin(#id)")
    public ResponseEntity<UsuarioDetalhesResponseDTO> buscarDetalhesPorUsuarioId(@PathVariable Long id) {
        var detalhes = usuarioDetalhesService.findByUsuarioId(id);
        return ResponseEntity.ok(usuarioDetalhesMapper.toResponseDTO(detalhes));
    }

    @PutMapping("/{id}/detalhes")
    @Operation(summary = "Criar ou atualizar detalhes de um usuário", description = "Cria ou atualiza os detalhes de um usuário existente a partir do seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalhes do usuário atualizados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    // @PreAuthorize("@securityUtil.isOwnerOrAdmin(#id)")
    public ResponseEntity<UsuarioDetalhesResponseDTO> criarOuAtualizarDetalhes(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioDetalhesUpdateDTO dto) {
        var detalhes = usuarioDetalhesService.createOrUpdate(id, dto);
        return ResponseEntity.ok(usuarioDetalhesMapper.toResponseDTO(detalhes));
    }

    // Endpoints para UsuarioFinanceiro

    @GetMapping("/{id}/financeiro")
    @Operation(summary = "Buscar dados financeiros de um usuário", description = "Retorna os dados financeiros de um usuário específico a partir do seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados financeiros do usuário encontrados"),
            @ApiResponse(responseCode = "404", description = "Usuário ou dados financeiros não encontrados")
    })
    // @PreAuthorize("@securityUtil.isOwnerOrAdmin(#id)")
    public ResponseEntity<UsuarioFinanceiroResponseDTO> buscarFinanceiroPorUsuarioId(@PathVariable Long id) {
        var financeiro = usuarioFinanceiroService.findByUsuarioId(id);
        return ResponseEntity.ok(usuarioFinanceiroMapper.toResponseDTO(financeiro));
    }

    @PutMapping("/{id}/financeiro")
    @Operation(summary = "Criar ou atualizar dados financeiros de um usuário", description = "Cria ou atualiza os dados financeiros de um usuário existente a partir do seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados financeiros do usuário atualizados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    // @PreAuthorize("@securityUtil.isOwnerOrAdmin(#id)")
    public ResponseEntity<UsuarioFinanceiroResponseDTO> criarOuAtualizarFinanceiro(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioFinanceiroUpdateDTO dto) {
        var financeiro = usuarioFinanceiroService.createOrUpdate(id, dto);
        return ResponseEntity.ok(usuarioFinanceiroMapper.toResponseDTO(financeiro));
    }

    // Endpoints para UsuarioPreferencias

    @GetMapping("/{id}/preferencias")
    @Operation(summary = "Buscar preferências de um usuário", description = "Retorna as preferências de um usuário específico a partir do seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Preferências do usuário encontradas"),
            @ApiResponse(responseCode = "404", description = "Usuário ou preferências não encontradas")
    })
    // @PreAuthorize("@securityUtil.isOwnerOrAdmin(#id)")
    public ResponseEntity<UsuarioPreferenciasResponseDTO> buscarPreferenciasPorUsuarioId(@PathVariable Long id) {
        var preferencias = usuarioPreferenciasService.findByUsuarioId(id);
        return ResponseEntity.ok(usuarioPreferenciasMapper.toResponseDTO(preferencias));
    }

    @PutMapping("/{id}/preferencias")
    @Operation(summary = "Criar ou atualizar preferências de um usuário", description = "Cria ou atualiza as preferências de um usuário existente a partir do seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Preferências do usuário atualizadas com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    // @PreAuthorize("@securityUtil.isOwnerOrAdmin(#id)")
    public ResponseEntity<UsuarioPreferenciasResponseDTO> criarOuAtualizarPreferencias(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioPreferenciasUpdateDTO dto) {
        var preferencias = usuarioPreferenciasService.createOrUpdate(id, dto);
        return ResponseEntity.ok(usuarioPreferenciasMapper.toResponseDTO(preferencias));
    }
}