package com.apso.app.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "sorteos_grupales")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SorteoGrupal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", length = 50, nullable = false)
    private String titulo;

    @Column(name = "fecha_sorteo", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "cantidad_grupos", nullable = false)
    private Integer cantidadGrupos;

    @Column(name = "resultado", columnDefinition = "TEXT", nullable = false)
    private String resultado; // Guarda los grupos formados como texto plano o JSON

    // Relación muchos a muchos con Estudiante
    @ManyToMany
    @JoinTable(
        name = "estudiante_sorteo",
        joinColumns = @JoinColumn(name = "sorteo_id"),
        inverseJoinColumns = @JoinColumn(name = "estudiante_id")
    )
    private List<Estudiante> estudiantes;

    // Relación con el usuario que creó el sorteo
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Método Ref. Error GrupoController.java
    public void setUsuario(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setTitulo(String titulo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setCantidadGrupos(int cantidadGruposTemporal) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setFechaHora(LocalDateTime now) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setResultado(String toString) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setEstudiantes(List<Estudiante> todosLosEstudiantes) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
