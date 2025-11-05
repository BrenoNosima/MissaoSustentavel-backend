package com.missaosustentavel.api.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.missaosustentavel.api.model.*;
import com.missaosustentavel.api.service.*;

/**
 * Controller para gerenciar as submissões de desafios
 * 
 * Aqui tratamos:
 * - Alunos enviando evidências de desafios concluídos
 * - Professores validando as submissões
 * - Listagem de submissões pendentes
 * 
 * Matérias relacionadas:
 * - Banco de Dados (relacionamentos)
 * - Programação Web (REST)
 * - Engenharia de Software (regras de negócio)
 */
@RestController
@RequestMapping("/api/submissoes")
public class SubmissaoController {

    private SubmissaoService submissaoService;
    private UsuarioService usuarioService;
    private DesafioService desafioService;

    public SubmissaoController(
            SubmissaoService submissaoService,
            UsuarioService usuarioService,
            DesafioService desafioService) {
        this.submissaoService = submissaoService;
        this.usuarioService = usuarioService;
        this.desafioService = desafioService;
    }

    /**
     * Aluno envia evidência de um desafio completado
     * POST /api/submissoes
     * 
     * Exemplo:
     * {
     *   "usuarioId": 1,
     *   "desafioId": 2,
     *   "evidencia": "Reciclei 6kg de plástico, foto anexa: [link]"
     * }
     */
    @PostMapping
    public Submissao enviar(@RequestBody EnvioRequest dados) {
        // Busca usuário e desafio
        Usuario usuario = usuarioService.findById(dados.usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        Desafio desafio = desafioService.findById(dados.desafioId)
                .orElseThrow(() -> new RuntimeException("Desafio não encontrado"));

        // Cria e salva a submissão
        Submissao submissao = new Submissao();
        submissao.setUsuario(usuario);
        submissao.setDesafio(desafio);
        submissao.setEvidencia(dados.evidencia);

        return submissaoService.criar(submissao);
    }

    /**
     * Lista submissões pendentes de validação
     * GET /api/submissoes/pendentes
     */
    @GetMapping("/pendentes")
    public List<SubmissaoResponse> listarPendentes() {
        return submissaoService.listarPendentes().stream()
                .map(SubmissaoResponse::new)
                .toList();
    }

    /**
     * Professor valida uma submissão
     * POST /api/submissoes/123/validar
     * 
     * Exemplo:
     * {
     *   "professorId": 1,
     *   "aprovado": true
     * }
     */
    @PostMapping("/{id}/validar")
    public SubmissaoResponse validar(
            @PathVariable Long id,
            @RequestBody ValidacaoRequest dados) {
        
        // Verifica se é um professor
        Usuario professor = usuarioService.findById(dados.professorId)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        if (!professor.isProfessor()) {
            throw new RuntimeException("Apenas professores podem validar submissões");
        }

        // Valida a submissão
        Submissao submissao = submissaoService.validar(id, dados.aprovado);
        return new SubmissaoResponse(submissao);
    }

    // Classes auxiliares

    public static class EnvioRequest {
        public Long usuarioId;
        public Long desafioId;
        public String evidencia;
    }

    public static class ValidacaoRequest {
        public Long professorId;
        public Boolean aprovado;
    }

    public static class SubmissaoResponse {
        public Long id;
        public String aluno;
        public String desafio;
        public String evidencia;
        public String status;
        public Integer pontos;

        public SubmissaoResponse(Submissao s) {
            this.id = s.getId();
            this.aluno = s.getUsuario().getNome();
            this.desafio = s.getDesafio().getTitulo();
            this.evidencia = s.getEvidencia();
            this.status = s.getStatus().name();
            this.pontos = s.getDesafio().getPontos();
        }
    }
}