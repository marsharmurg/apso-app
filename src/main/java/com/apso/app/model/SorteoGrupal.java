package com.apso.app.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SorteoGrupal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreSorteo;

    private Integer cantidadGrupos;

    private LocalDateTime fecha;

    @OneToMany(mappedBy = "sorteo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Grupo> grupos;
}
