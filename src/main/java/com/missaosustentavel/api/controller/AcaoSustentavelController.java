package com.missaosustentavel.api.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.missaosustentavel.api.model.AcaoSustentavel;
import com.missaosustentavel.api.model.Usuario;
import com.missaosustentavel.api.service.AcaoSustentavelService;
import com.missaosustentavel.api.service.UsuarioService;

/**
 * Controlador para gerenciar as ações sustentáveis do sistema
 * 
 * Esse controlador é responsável por receber as requisições HTTP relacionadas
 * às ações sustentáveis que os usuários registram. Ele usa o padrão MVC onde:
 * - Controller (este arquivo): recebe as requisições web
 * - Service: contém as regras de negócio
 * - Repository: faz o acesso ao banco de dados
 */
@RestController // Marca esta classe como um controlador REST
@RequestMapping("/api/acoes") // Todas as URLs deste controller começam com /api/acoes
public class AcaoSustentavelController {

    // Declaração dos serviços que vamos usar
    private AcaoSustentavelService acaoService;
    private UsuarioService usuarioService;

    // O Spring vai injetar os serviços automaticamente
    public AcaoSustentavelController(AcaoSustentavelService acaoService, UsuarioService usuarioService) {
        this.acaoService = acaoService;
        this.usuarioService = usuarioService;
    }

    /**
     * Endpoint que lista todas as ações sustentáveis cadastradas
     * GET /api/acoes
     */
    /**
     * Lista todas as ações cadastradas
     * GET /api/acoes
     */
    @GetMapping
    public List<AcaoSustentavel> listarTodas() {
        return acaoService.listByUsuario(null); // lista todas
    }

    /**
     * Busca uma ação específica pelo ID
     * GET /api/acoes/123
     */
    @GetMapping("/{id}")
    public AcaoSustentavel buscarPorId(@PathVariable Long id) {
        return acaoService.findById(id)
                .orElseThrow(() -> new RuntimeException("Ação não encontrada"));
    }

    /**
     * Registra uma nova ação sustentável
     * POST /api/acoes
     * 
     * Exemplo:
     * {
     *   "usuarioId": 1,
     *   "titulo": "Reciclagem",
     *   "descricao": "Reciclei 5kg de plástico",
     *   "pontos": 10
     * }
     */
    @PostMapping
    public AcaoSustentavel criar(@RequestBody DadosDaAcao dados) {
        // Busca o usuário que está registrando a ação
        Usuario usuario = usuarioService.findById(dados.usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Cria a ação
        AcaoSustentavel acao = new AcaoSustentavel();
        acao.setUsuario(usuario);
        acao.setTitulo(dados.titulo);
        acao.setDescricao(dados.descricao);
        acao.setPontos(dados.pontos);

        return acaoService.createAcao(acao);
    }

    /**
     * Lista ações pendentes de validação
     * GET /api/acoes/pendentes
     */
    @GetMapping("/pendentes")
    public List<AcaoSustentavel> listarPendentes() {
        return acaoService.listPendentes();
    }

    /**
     * Valida uma ação e atribui pontos ao usuário
     * PUT /api/acoes/123/validar
     */
    @PutMapping("/{id}/validar")
    public AcaoSustentavel validar(@PathVariable Long id) {
        return acaoService.validarAcao(id);
    }

    /**
     * Lista ações de um usuário específico
     * GET /api/acoes/usuario/123
     */
    @GetMapping("/usuario/{id}")
    public List<AcaoSustentavel> listarPorUsuario(@PathVariable Long id) {
        return acaoService.listByUsuario(id);
    }

    // Classe auxiliar para receber os dados da requisição POST
    public static class DadosDaAcao {
        public Long usuarioId;     // ID do usuário que fez a ação
        public String titulo;      // Título curto da ação
        public String descricao;   // Descrição detalhada
        public Integer pontos;     // Quantidade de pontos da ação
    }
}
