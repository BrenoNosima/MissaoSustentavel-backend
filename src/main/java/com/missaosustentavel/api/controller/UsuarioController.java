package com.missaosustentavel.api.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.missaosustentavel.api.model.Usuario;
import com.missaosustentavel.api.service.UsuarioService;

/**
 * Controller para gerenciar usuários (alunos e professores)
 * 
 * Aqui tratamos:
 * - Cadastro de usuários
 * - Login no sistema
 * - Atualização de dados
 * - Consulta de perfil e pontos
 * - Ranking de alunos
 * 
 * Matérias relacionadas:
 * - Programação Web (endpoints REST)
 * - Banco de Dados (CRUD básico)
 * - Engenharia de Software (padrão MVC)
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Cadastra um novo usuário (aluno ou professor)
     * POST /api/usuarios
     * 
     * Exemplo:
     * {
     *   "nome": "João Silva",
     *   "email": "joao@email.com",
     *   "senha": "123456",
     *   "tipo": "ALUNO"
     * }
     */
    @PostMapping
    public Usuario cadastrar(@RequestBody CadastroRequest dados) {
        Usuario usuario = new Usuario();
        usuario.setNome(dados.nome);
        usuario.setEmail(dados.email);
        usuario.setSenha(dados.senha);
        usuario.setTipo(dados.tipo);
        
        return usuarioService.createUsuario(usuario);
    }

    /**
     * Faz login no sistema
     * POST /api/usuarios/login
     * 
     * Exemplo:
     * {
     *   "email": "joao@email.com",
     *   "senha": "123456"
     * }
     */
    @PostMapping("/login")
    public Usuario login(@RequestBody LoginRequest dados) {
        return usuarioService.login(dados.email, dados.senha)
                .orElseThrow(() -> new RuntimeException("Email ou senha incorretos"));
    }

    /**
     * Busca o perfil do usuário logado
     * GET /api/usuarios/perfil?id=123
     */
    @GetMapping("/perfil")
    public PerfilResponse buscarPerfil(@RequestParam Long id) {
        Usuario usuario = usuarioService.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return new PerfilResponse(usuario);
    }

    /**
     * Retorna o ranking dos alunos por pontuação
     * GET /api/usuarios/ranking
     */
    @GetMapping("/ranking")
    public List<RankingResponse> listarRanking() {
        return usuarioService.getRanking().stream()
                .map(RankingResponse::new)
                .toList();
    }

    // Classes auxiliares para as requisições

    public static class CadastroRequest {
        public String nome;
        public String email;
        public String senha;
        public Usuario.TipoUsuario tipo;
    }

    public static class LoginRequest {
        public String email;
        public String senha;
    }

    // Classes auxiliares para as respostas

    public static class PerfilResponse {
        public Long id;
        public String nome;
        public String email;
        public String tipo;
        public Integer pontos;
        public String nivel;

        public PerfilResponse(Usuario u) {
            this.id = u.getId();
            this.nome = u.getNome();
            this.email = u.getEmail();
            this.tipo = u.getTipo().name();
            this.pontos = u.getPontos();
            this.nivel = u.getNivel();
        }
    }

    public static class RankingResponse {
        public String nome;
        public Integer pontos;
        public String nivel;

        public RankingResponse(Usuario u) {
            this.nome = u.getNome();
            this.pontos = u.getPontos();
            this.nivel = u.getNivel();
        }
    }
}
