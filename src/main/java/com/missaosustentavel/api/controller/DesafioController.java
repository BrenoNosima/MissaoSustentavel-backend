package com.missaosustentavel.api.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.missaosustentavel.api.model.Desafio;
import com.missaosustentavel.api.service.DesafioService;

/**
 * Controller para gerenciar os desafios sustentáveis
 * 
 * Este controller permite:
 * - Listar todos os desafios disponíveis
 * - Buscar detalhes de um desafio específico
 * - Criar novos desafios (apenas professores)
 * 
 * Matérias relacionadas:
 * - Banco de Dados (CRUD)
 * - Programação Web (REST)
 */
@RestController
@RequestMapping("/api/desafios")
public class DesafioController {

    private DesafioService desafioService;

    public DesafioController(DesafioService desafioService) {
        this.desafioService = desafioService;
    }

    /**
     * Lista todos os desafios ativos
     * GET /api/desafios
     */
    @GetMapping
    public List<Desafio> listarTodos() {
        return desafioService.listarAtivos();
    }

    /**
     * Busca um desafio específico
     * GET /api/desafios/123
     */
    @GetMapping("/{id}")
    public Desafio buscarPorId(@PathVariable Long id) {
        return desafioService.findById(id)
                .orElseThrow(() -> new RuntimeException("Desafio não encontrado"));
    }

    /**
     * Cria um novo desafio (apenas professores)
     * POST /api/desafios
     * 
     * Exemplo:
     * {
     *   "titulo": "Recicle Plástico",
     *   "descricao": "Recicle 5kg de plástico em uma semana",
     *   "pontos": 50,
     *   "deadline": "2025-12-31"
     * }
     */
    @PostMapping
    public Desafio criar(@RequestBody Desafio desafio) {
        // TODO: verificar se usuário é professor
        return desafioService.criar(desafio);
    }

    /**
     * Desativa um desafio (apenas professores)
     * DELETE /api/desafios/123
     */
    @DeleteMapping("/{id}")
    public void desativar(@PathVariable Long id) {
        // TODO: verificar se usuário é professor
        desafioService.desativar(id);
    }
}