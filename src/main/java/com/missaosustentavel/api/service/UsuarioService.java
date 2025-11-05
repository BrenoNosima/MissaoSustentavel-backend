package com.missaosustentavel.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.missaosustentavel.api.model.Usuario;
import com.missaosustentavel.api.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario createUsuario(Usuario usuario) {
        Optional<Usuario> existing = usuarioRepository.findByEmail(usuario.getEmail());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
        if (usuario.getPontos() == null) {
            usuario.setPontos(0);
        }
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> login(String email, String senha) {
        Optional<Usuario> u = usuarioRepository.findByEmail(email);
        if (u.isPresent() && u.get().getSenha() != null && u.get().getSenha().equals(senha)) {
            return u;
        }
        return Optional.empty();
    }

    public List<Usuario> getRanking() {
        return usuarioRepository.buscarRanking();
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
}
