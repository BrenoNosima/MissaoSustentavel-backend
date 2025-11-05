package com.missaosustentavel.api.model;

import jakarta.persistence.*;

/**
 * Representa um usuário do sistema (aluno ou professor)
 * 
 * Esta classe guarda as informações básicas dos usuários e seus pontos.
 * Um usuário pode ser do tipo ALUNO ou PROFESSOR, o que define o que
 * ele pode fazer no sistema.
 */
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;            // Nome completo
    private String email;           // Email para login
    private String senha;           // Senha (guarda em texto puro por simplicidade)
    private Integer pontos = 0;     // Pontos acumulados por desafios

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipo = TipoUsuario.ALUNO;  // Tipo do usuário (aluno/professor)

    // Define os tipos possíveis de usuário
    public enum TipoUsuario {
        ALUNO,      // Pode submeter desafios
        PROFESSOR   // Pode validar submissões
    }

    // Retorna o nível do usuário baseado nos pontos
    public String getNivel() {
        if (pontos >= 351) return "OURO";
        if (pontos >= 201) return "PRATA";
        if (pontos >= 100) return "BRONZE";
        return "INICIANTE";
    }

    // Construtor padrão
    public Usuario() {
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getPontos() {
        return pontos;
    }

    public void setPontos(Integer pontos) {
        this.pontos = pontos;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    // Verifica se o usuário é um professor
    public boolean isProfessor() {
        return tipo == TipoUsuario.PROFESSOR;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", tipo=" + tipo +
                ", pontos=" + pontos +
                ", nivel='" + getNivel() + '\'' +
                '}';
    }
}