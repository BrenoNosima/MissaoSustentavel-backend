package com.missaosustentavel.api.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.missaosustentavel.api.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Busca usuário pelo email (usado no login)
    Optional<Usuario> findByEmail(String email);
    
    // Busca alunos ordenados por pontos (ranking)
    @Query("SELECT u FROM Usuario u WHERE u.tipo = 'ALUNO' ORDER BY u.pontos DESC")
    List<Usuario> buscarRanking();
    
    // Verifica se email já está cadastrado
    boolean existsByEmail(String email);
}