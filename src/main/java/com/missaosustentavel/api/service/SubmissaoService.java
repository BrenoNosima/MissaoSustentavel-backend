package com.missaosustentavel.api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.missaosustentavel.api.model.Submissao;
import com.missaosustentavel.api.model.Usuario;
import com.missaosustentavel.api.repository.SubmissaoRepository;
import com.missaosustentavel.api.repository.UsuarioRepository;
import java.util.List;

/**
 * Service com as regras de negócio relacionadas às submissões
 */
@Service
public class SubmissaoService {

    private SubmissaoRepository submissaoRepository;
    private UsuarioRepository usuarioRepository;

    public SubmissaoService(
            SubmissaoRepository submissaoRepository,
            UsuarioRepository usuarioRepository) {
        this.submissaoRepository = submissaoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Submissao> listarPendentes() {
        return submissaoRepository.findPendentes();
    }

    public Submissao criar(Submissao submissao) {
        return submissaoRepository.save(submissao);
    }

    @Transactional
    public Submissao validar(Long id, boolean aprovado) {
        Submissao submissao = submissaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Submissão não encontrada"));

        if (submissao.getStatus() != Submissao.StatusSubmissao.PENDENTE) {
            throw new RuntimeException("Submissão já foi validada");
        }

        // Atualiza o status da submissão
        submissao.setStatus(aprovado ? 
                Submissao.StatusSubmissao.APROVADA : 
                Submissao.StatusSubmissao.REJEITADA);

        // Se aprovada, adiciona os pontos ao usuário
        if (aprovado) {
            Usuario usuario = submissao.getUsuario();
            usuario.setPontos(usuario.getPontos() + submissao.getDesafio().getPontos());
            usuarioRepository.save(usuario);
        }

        return submissaoRepository.save(submissao);
    }
}