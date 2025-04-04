package com.apso.app.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numero;

    @ManyToOne
    @JoinColumn(name = "sorteo_id")
    private SorteoGrupal sorteo;

    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EstudianteGrupo> estudianteGrupos;
}
