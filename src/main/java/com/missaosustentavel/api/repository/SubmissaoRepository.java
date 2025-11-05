package com.missaosustentavel.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.missaosustentavel.api.model.Submissao;
import java.util.List;

/**
 * Repository para acessar as submissões no banco de dados
 */
public interface SubmissaoRepository extends JpaRepository<Submissao, Long> {
    
    // Busca submissões pendentes de validação
    @Query("SELECT s FROM Submissao s WHERE s.status = 'PENDENTE' ORDER BY s.dataEnvio")
    List<Submissao> findPendentes();
}