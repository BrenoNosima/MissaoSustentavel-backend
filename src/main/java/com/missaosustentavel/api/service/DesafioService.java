package com.missaosustentavel.api.service;

import org.springframework.stereotype.Service;
import com.missaosustentavel.api.model.Desafio;
import com.missaosustentavel.api.repository.DesafioRepository;
import java.util.List;
import java.util.Optional;

/**
 * Service com as regras de negócio relacionadas aos desafios
 */
@Service
public class DesafioService {

    private DesafioRepository desafioRepository;

    public DesafioService(DesafioRepository desafioRepository) {
        this.desafioRepository = desafioRepository;
    }

    public List<Desafio> listarAtivos() {
        return desafioRepository.findAtivos();
    }

    public Optional<Desafio> findById(Long id) {
        return desafioRepository.findById(id);
    }

    public Desafio criar(Desafio desafio) {
        return desafioRepository.save(desafio);
    }

    public void desativar(Long id) {
        desafioRepository.findById(id).ifPresent(desafio -> {
            desafio.setAtivo(false);
            desafioRepository.save(desafio);
        });
    }
}