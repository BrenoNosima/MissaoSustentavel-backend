package com.missaosustentavel.api.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.missaosustentavel.api.model.*;
import com.missaosustentavel.api.service.*;

/**
 * Controller para gerenciar os desafios sustentáveis
 *
 * Este controller permite:
 * - Listar todos os desafios disponíveis
 * - Buscar detalhes de um desafio específico
 * - Criar novos desafios (apenas professores)
 * - Enviar evidências de conclusão
 *
 * Matérias relacionadas:
 * - Banco de Dados (CRUD)
 * - Programação Web (REST)
 */
@RestController
@RequestMapping("/api/desafios")
public class DesafioController {

    private DesafioService desafioService;
    private SubmissaoService submissaoService;
    private UsuarioService usuarioService;

    public DesafioController(
            DesafioService desafioService,
            SubmissaoService submissaoService,
            UsuarioService usuarioService) {
        this.desafioService = desafioService;
        this.submissaoService = submissaoService;
        this.usuarioService = usuarioService;
    }

    /**
     * Lista desafios ativos da semana atual
     * GET /api/desafios
     */
    @GetMapping
    public List<Desafio> listarAtuais() {
        return desafioService.listarAtivos();
    }

    /**
     * Lista desafios fixos (sempre disponíveis)
     * GET /api/desafios/fixed
     */
    @GetMapping("/fixed")
    public List<Desafio> listarFixos() {
        return desafioService.listarFixos();
    }

    /**
     * Lista desafios concluídos pelo usuário
     * GET /api/desafios/completed
     *
     * Nota: Por enquanto retorna todos os desafios.
     * TODO: Implementar filtro por usuário autenticado
     */
    @GetMapping("/completed")
    public List<Desafio> listarConcluidos() {
        // TODO: Implementar lógica para buscar desafios concluídos pelo usuário
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
     * Envia evidência de conclusão de um desafio
     * POST /api/desafios/123/submit
     *
     * Exemplo:
     * {
     *   "userId": 1,
     *   "evidenceUrl": "https://exemplo.com/foto.jpg"
     * }
     */
    @PostMapping("/{id}/submit")
    public Submissao enviarEvidencia(
            @PathVariable Long id,
            @RequestBody SubmitRequest payload) {

        // Valida os dados
        if (payload.userId == null) {
            throw new RuntimeException("ID do usuário é obrigatório");
        }
        if (payload.evidenceUrl == null || payload.evidenceUrl.isEmpty()) {
            throw new RuntimeException("URL da evidência é obrigatória");
        }

        // Busca usuário e desafio
        Usuario usuario = usuarioService.findById(payload.userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Desafio desafio = desafioService.findById(id)
                .orElseThrow(() -> new RuntimeException("Desafio não encontrado"));

        // Cria e salva a submissão
        Submissao submissao = new Submissao();
        submissao.setUsuario(usuario);
        submissao.setDesafio(desafio);
        submissao.setEvidencia(payload.evidenceUrl);

        return submissaoService.criar(submissao);
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
     *   "prazoFinal": "2025-12-31",
     *   "fixo": false
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

    // Classe auxiliar para receber dados da submissão
    public static class SubmitRequest {
        public Long userId;
        public String evidenceUrl;
    }
}