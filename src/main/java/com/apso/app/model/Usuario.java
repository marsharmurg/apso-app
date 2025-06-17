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

    private String sub;

    private String nombre;

    private String email;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SorteoGrupal> sorteos;

}
