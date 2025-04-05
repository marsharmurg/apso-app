package com.apso.app.dto;

import lombok.Data;

@Data
public class EstudianteCSV {
    private String nombre;
    private String email;
    private String grupoTeorico;
    private String asignatura;
    private String cargaId; // Opcional, en archivo CSV está vacío
}
