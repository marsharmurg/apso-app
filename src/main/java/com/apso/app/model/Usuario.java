package com.apso.app.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID único del usuario en Auth0 (claim 'sub')
    @Column(name = "auth0_id", unique = true, nullable = false)
    private String auth0Id;

    private String nombre;

    private String email;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SorteoGrupal> sorteos;

    // Resuelve errores de compilación al no tener implementaciones: UsuarioService
    public void setAuth0Id(String auth0Id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setNombre(String fullName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setEmail(String email) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setSub(String sub) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setSub'");
    }
}
