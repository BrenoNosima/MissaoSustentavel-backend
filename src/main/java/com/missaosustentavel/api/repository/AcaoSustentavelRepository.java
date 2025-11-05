package com.missaosustentavel.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.missaosustentavel.api.model.AcaoSustentavel;

public interface AcaoSustentavelRepository extends JpaRepository<AcaoSustentavel, Long> {
    
    List<AcaoSustentavel> findByValidadoFalseOrderByDataEnvioAsc();
    
    List<AcaoSustentavel> findByUsuarioIdOrderByDataEnvioDesc(Long usuarioId);
}