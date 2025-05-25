package com.apso.app.repository;

import com.apso.app.model.Sorteo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SorteoRepository extends JpaRepository<Sorteo, Long> {
}

