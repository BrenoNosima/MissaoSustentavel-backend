package com.missaosustentavel.api.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;

/**
 * Classe que representa uma ação sustentável no sistema
 * 
 * Esta classe demonstra um relacionamento Many-to-One com Usuario,
 * ou seja, um usuário pode ter várias ações sustentáveis.
 * 
 * Matérias relacionadas:
 * - Banco de Dados II (relacionamentos entre tabelas)
 * - Programação Web (entidades JPA)
 */
@Entity // Marca como entidade JPA
@Table(name = "acoes_sustentaveis") // Nome da tabela no banco
public class AcaoSustentavel {

    @Id // Chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremento
    private Long id;

    @ManyToOne // Muitas ações podem pertencer a um usuário
    private Usuario usuario;    // Usuário que registrou a ação

    private String titulo;      // Título curto da ação (ex: "Reciclagem")
    private String descricao;   // Descrição detalhada da ação
    private LocalDateTime dataEnvio = LocalDateTime.now();  // Data/hora do registro
    private Boolean validado = false;  // Se a ação foi validada por um admin
    private Integer pontos = 0;        // Pontos que esta ação vale

    // Construtor padrão necessário para o JPA
    public AcaoSustentavel() {
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDateTime dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public Boolean getValidado() {
        return validado;
    }

    public void setValidado(Boolean validado) {
        this.validado = validado;
    }

    public Integer getPontos() {
        return pontos;
    }

    public void setPontos(Integer pontos) {
        this.pontos = pontos;
    }

    // Método útil para debugar
    @Override
    public String toString() {
        return "AcaoSustentavel {" +
                "id=" + id +
                ", usuario=" + usuario.getNome() +
                ", titulo='" + titulo + '\'' +
                ", validado=" + validado +
                ", pontos=" + pontos +
                '}';
    }
}