package com.apso.app.model;

import jakarta.persistence.*;
import lombok.*;

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

    private String nombreSorteo;

    private Integer cantidadGrupos;

    private String fechaSorteo;

    // Relación muchos a muchos con Estudiante
    @ManyToMany(mappedBy = "sorteos")
    private List<Estudiante> estudiantes;
}
