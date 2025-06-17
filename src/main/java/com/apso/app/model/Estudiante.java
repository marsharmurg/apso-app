package com.apso.app.model;

import jakarta.persistence.*;
import lombok.*;

import com.apso.app.model.Usuario;

import java.util.List;

@Entity
@Table(name = "estudiantes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
    private String asignatura;
    
    @Column(name = "carga_id")
    private Long cargaId;

    @Column(name = "grupo_teorico")
    private String grupoTeorico;

    // Usuarios creador
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;


    // Relación muchos a muchos con SorteoGrupal
    @ManyToMany
    @JoinTable(
        name = "estudiante_sorteo",
        joinColumns = @JoinColumn(name = "estudiante_id"),
        inverseJoinColumns = @JoinColumn(name = "sorteo_id")
    )
    private List<SorteoGrupal> sorteos;
}
