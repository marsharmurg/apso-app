package com.apso.app.controller;

import com.apso.app.dto.SorteoGrupalRequestDTO;
import com.apso.app.dto.SorteoGrupalResponseDTO;
import com.apso.app.model.SorteoGrupal;
import com.apso.app.service.SorteoGrupalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sorteos")
public class SorteoGrupalController {

    private final SorteoGrupalService sorteoGrupalService;

    public SorteoGrupalController(SorteoGrupalService sorteoGrupalService) {
        this.sorteoGrupalService = sorteoGrupalService;
    }

    // GET: Historial de sorteos
    @GetMapping("/historial")
    public List<SorteoGrupal> obtenerHistorial() {
        return sorteoGrupalService.obtenerHistorialSorteos();
    }

    // POST: Crear sorteo grupal
    @PostMapping("/crear")
    public SorteoGrupalResponseDTO crearSorteo(@RequestBody SorteoGrupalRequestDTO dto) {
        return sorteoGrupalService.crearSorteo(dto);
    }

    // MÉTODO DE PRUEBA —> este es el que debes agregar
    @GetMapping("/test")
    public String test() {
        return "API funcionando";
    }
}


