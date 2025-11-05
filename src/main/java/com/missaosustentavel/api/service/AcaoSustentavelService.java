package com.missaosustentavel.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.missaosustentavel.api.model.AcaoSustentavel;
import com.missaosustentavel.api.model.Usuario;
import com.missaosustentavel.api.repository.AcaoSustentavelRepository;
import com.missaosustentavel.api.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class AcaoSustentavelService {

    private final AcaoSustentavelRepository acaoRepository;
    private final UsuarioRepository usuarioRepository;

    public AcaoSustentavelService(AcaoSustentavelRepository acaoRepository, UsuarioRepository usuarioRepository) {
        this.acaoRepository = acaoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public AcaoSustentavel createAcao(AcaoSustentavel acao) {
        // dataEnvio, validado e pontos já possuem valores padrão na entidade
        return acaoRepository.save(acao);
    }

    public List<AcaoSustentavel> listPendentes() {
        return acaoRepository.findByValidadoFalseOrderByDataEnvioAsc();
    }

    public List<AcaoSustentavel> listByUsuario(Long usuarioId) {
        return acaoRepository.findByUsuarioIdOrderByDataEnvioDesc(usuarioId);
    }

    @Transactional
    public AcaoSustentavel validarAcao(Long id) {
        AcaoSustentavel acao = acaoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ação não encontrada: " + id));

        if (Boolean.TRUE.equals(acao.getValidado())) {
            return acao; // já validada
        }

        acao.setValidado(true);

        Usuario usuario = acao.getUsuario();
        if (usuario == null) {
            throw new IllegalStateException("Ação não possui usuário associado");
        }

        Integer pontosAtuais = usuario.getPontos() == null ? 0 : usuario.getPontos();
        usuario.setPontos(pontosAtuais + (acao.getPontos() == null ? 0 : acao.getPontos()));

        usuarioRepository.save(usuario);
        acaoRepository.save(acao);

        return acao;
    }

    public Optional<AcaoSustentavel> findById(Long id) {
        return acaoRepository.findById(id);
    }
}
