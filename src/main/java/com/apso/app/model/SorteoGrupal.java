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

    private String nombreSorteo;

    private Integer cantidadGrupos;

    //private String fechaSorteo;

    // Mejor usar tipo LocalDateTime para fecha/hora en vez de String
    private LocalDateTime fechaSorteo;

    // Relación muchos a muchos con Estudiante
    //@ManyToMany(mappedBy = "sorteos")
    //private List<Estudiante> estudiantes;

    //@ManyToOne
    //@JoinColumn(mappedBy = "sorteoGrupal", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<Grupo> grupos;
     @OneToMany(mappedBy = "sorteo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Grupo> grupos;

}
