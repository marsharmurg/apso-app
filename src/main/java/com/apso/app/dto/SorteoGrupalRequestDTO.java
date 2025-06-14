package com.apso.app.dto;

import lombok.Data;

import java.util.List;

@Data
public class SorteoGrupalRequestDTO {
    private String nombreSorteo;
    private Integer cantidadGrupos;
    private List<Long> estudiantesIds;
}
