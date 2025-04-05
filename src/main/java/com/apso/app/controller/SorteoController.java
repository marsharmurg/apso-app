package com.apso.app.controller;

import com.apso.app.model.Sorteo;
import com.apso.app.service.SorteoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sorteos")
public class SorteoController {

    @Autowired
    private SorteoService sorteoService;

    // Obtener todos los sorteos
    @GetMapping
    public List<Sorteo> obtenerTodosLosSorteos() {
        return sorteoService.obtenerTodosLosSorteos();
    }

    // Obtener un sorteo por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Sorteo> obtenerSorteoPorId(@PathVariable Long id) {
        Optional<Sorteo> sorteo = sorteoService.obtenerSorteoPorId(id);
        return sorteo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo sorteo
    @PostMapping
    public ResponseEntity<Sorteo> crearSorteo(@RequestBody Sorteo sorteo) {
        Sorteo nuevoSorteo = sorteoService.crearSorteo(sorteo);
        return new ResponseEntity<>(nuevoSorteo, HttpStatus.CREATED);
    }

    // Eliminar un sorteo por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSorteo(@PathVariable Long id) {
        sorteoService.eliminarSorteo(id);
        return ResponseEntity.noContent().build();
    }

    // Actualizar un sorteo
    @PutMapping("/{id}")
    public ResponseEntity<Sorteo> actualizarSorteo(@PathVariable Long id, @RequestBody Sorteo sorteo) {
        Sorteo sorteoActualizado = sorteoService.actualizarSorteo(id, sorteo);
        return sorteoActualizado != null ? ResponseEntity.ok(sorteoActualizado) : ResponseEntity.notFound().build();
    }
}

