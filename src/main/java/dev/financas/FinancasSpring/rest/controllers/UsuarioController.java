package dev.financas.FinancasSpring.rest.controllers;

import dev.financas.FinancasSpring.rest.dto.UsuarioCreateDTO;
import dev.financas.FinancasSpring.rest.dto.UsuarioResponseDTO;
import dev.financas.FinancasSpring.rest.dto.UsuarioUpdateDTO;
import dev.financas.FinancasSpring.rest.mapper.UsuarioMapper;
import dev.financas.FinancasSpring.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    public UsuarioController(UsuarioService usuarioService, UsuarioMapper usuarioMapper) {
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
    }

    @GetMapping("/teste")
    public String primeiraRota() {
        return "Testando rota usuário!";
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
        var usuarios = usuarioService.findAll();
        var response = usuarios.stream()
                .map(usuarioMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> criar(@Valid @RequestBody UsuarioCreateDTO dto) {
        var usuario = usuarioMapper.toEntity(dto);
        var salvo = usuarioService.save(usuario);
        var response = usuarioMapper.toResponseDTO(salvo);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioUpdateDTO dto) {

        dto.setId(id);

        return usuarioService.atualizar(id, dto)
                .map(usuario -> ResponseEntity.ok(usuarioMapper.toResponseDTO(usuario)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (usuarioService.findById(id).isPresent()) {
            usuarioService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}