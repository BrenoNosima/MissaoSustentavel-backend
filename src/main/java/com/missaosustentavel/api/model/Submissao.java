package com.missaosustentavel.api.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Representa uma submissão de desafio feita por um aluno
 * 
 * Esta classe relaciona:
 * - Qual usuário (aluno) fez a submissão
 * - Qual desafio ele completou
 * - Qual a evidência apresentada
 * - Se foi aprovada ou não pelo professor
 */
@Entity
@Table(name = "submissoes")
public class Submissao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne  // Muitas submissões podem ser feitas por um usuário
    private Usuario usuario;

    @ManyToOne  // Muitas submissões podem ser do mesmo desafio
    private Desafio desafio;

    private String evidencia;        // Texto descrevendo como completou o desafio
    private LocalDateTime dataEnvio = LocalDateTime.now();
    
    @Enumerated(EnumType.STRING)
    private StatusSubmissao status = StatusSubmissao.PENDENTE;

    // Possíveis estados de uma submissão
    public enum StatusSubmissao {
        PENDENTE,    // Acabou de ser enviada
        APROVADA,    // Professor aprovou
        REJEITADA    // Professor rejeitou
    }

    // Construtor padrão
    public Submissao() {
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Desafio getDesafio() {
        return desafio;
    }

    public void setDesafio(Desafio desafio) {
        this.desafio = desafio;
    }

    public String getEvidencia() {
        return evidencia;
    }

    public void setEvidencia(String evidencia) {
        this.evidencia = evidencia;
    }

    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDateTime dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public StatusSubmissao getStatus() {
        return status;
    }

    public void setStatus(StatusSubmissao status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Submissao{" +
                "id=" + id +
                ", usuario=" + usuario.getNome() +
                ", desafio=" + desafio.getTitulo() +
                ", status=" + status +
                '}';
    }
}