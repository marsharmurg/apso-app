package com.apso.app.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SorteoGrupalResponseDTO {
    private Long id;
    private String nombreSorteo;
    private Integer cantidadGrupos;
    private LocalDateTime fechaSorteo;
    private List<GrupoDTO> grupos;

    @Data
    public static class GrupoDTO {
        private Integer numero;
        private List<String> nombresEstudiantes;
    }
}
