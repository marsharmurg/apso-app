package com.apso.app.repository;

import com.apso.app.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByAuth0Id(String auth0Id);
    Optional<Usuario> findBySub(String sub);
}
