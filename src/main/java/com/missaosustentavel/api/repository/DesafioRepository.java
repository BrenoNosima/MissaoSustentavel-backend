package com.missaosustentavel.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.missaosustentavel.api.model.Desafio;
import java.util.List;

/**
 * Repository para acessar os desafios no banco de dados
 * Usa Spring Data JPA para gerar os métodos automaticamente
 */
public interface DesafioRepository extends JpaRepository<Desafio, Long> {

    // Busca apenas desafios ativos
    @Query("SELECT d FROM Desafio d WHERE d.ativo = true")
    List<Desafio> findAtivos();

    // Busca apenas desafios fixos (sempre disponíveis)
    @Query("SELECT d FROM Desafio d WHERE d.ativo = true AND d.fixo = true")
    List<Desafio> findFixos();

    // Busca desafios ativos da semana atual (não fixos)
    @Query("SELECT d FROM Desafio d WHERE d.ativo = true AND d.fixo = false")
    List<Desafio> findAtivosDaSemana();
}