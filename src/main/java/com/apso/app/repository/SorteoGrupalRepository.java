package com.apso.app.repository;

import com.apso.app.model.SorteoGrupal;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SorteoGrupalRepository extends JpaRepository<SorteoGrupal, Long> {

    @EntityGraph(attributePaths = {
        "grupos",
        "grupos.estudianteGrupos",
        "grupos.estudianteGrupos.estudiante"
    })
    List<SorteoGrupal> findAll();  // trae todo el historial ya listo
    
}
