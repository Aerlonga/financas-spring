package dev.financas.FinancasSpring.services;

// import dev.financas.FinancasSpring.exceptions.BusinessException;
import dev.financas.FinancasSpring.exceptions.ResourceNotFoundException;
import dev.financas.FinancasSpring.model.entities.Usuario;
import dev.financas.FinancasSpring.model.entities.UsuarioDetalhes;
import dev.financas.FinancasSpring.model.repository.UsuarioDetalhesRepository;
import dev.financas.FinancasSpring.model.repository.UsuarioRepository;
import dev.financas.FinancasSpring.rest.dto.UsuarioDetalhesUpdateDTO;
import dev.financas.FinancasSpring.rest.mapper.UsuarioDetalhesMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioDetalhesService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioDetalhesRepository usuarioDetalhesRepository;
    private final UsuarioDetalhesMapper usuarioDetalhesMapper;

    public UsuarioDetalhesService(UsuarioRepository usuarioRepository,
            UsuarioDetalhesRepository usuarioDetalhesRepository,
            UsuarioDetalhesMapper usuarioDetalhesMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioDetalhesRepository = usuarioDetalhesRepository;
        this.usuarioDetalhesMapper = usuarioDetalhesMapper;
    }

    @Transactional(readOnly = true)
    public UsuarioDetalhes findByUsuarioId(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new ResourceNotFoundException("Usuário não encontrado com o ID: " + usuarioId);
        }
        return usuarioDetalhesRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Detalhes não encontrados para o usuário com ID: " + usuarioId));
    }

    @Transactional
    public UsuarioDetalhes createOrUpdate(Long usuarioId, UsuarioDetalhesUpdateDTO dto) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o ID: " + usuarioId));

        // Verifica se o usuário já possui detalhes
        UsuarioDetalhes detalhes = usuario.getDetalhes();

        if (detalhes == null) {
            // Se não tiver, cria uma nova entidade de detalhes
            detalhes = usuarioDetalhesMapper.toEntity(dto);
            detalhes.setUsuario(usuario); // Associa ao usuário
            usuario.setDetalhes(detalhes); // Associa no lado do usuário
        } else {
            // Se já tiver, apenas atualiza os campos
            usuarioDetalhesMapper.updateEntity(detalhes, dto);
        }

        // Salva a entidade de detalhes diretamente para garantir que a FK seja
        // definida.
        // O CascadeType.ALL no Usuario garante que o estado permaneça consistente.
        return usuarioDetalhesRepository.save(detalhes);
    }
}