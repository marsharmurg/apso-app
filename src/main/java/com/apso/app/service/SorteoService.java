package com.apso.app.service;

import com.apso.app.model.Sorteo;
import com.apso.app.repository.SorteoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SorteoService {

    @Autowired
    private SorteoRepository sorteoRepository;

    // Obtener todos los sorteos
    public List<Sorteo> obtenerTodosLosSorteos() {
        return sorteoRepository.findAll();
    }

    // Obtener un sorteo por su ID
    public Optional<Sorteo> obtenerSorteoPorId(Long id) {
        return sorteoRepository.findById(id);
    }

    // Crear un nuevo sorteo
    public Sorteo crearSorteo(Sorteo sorteo) {
        return sorteoRepository.save(sorteo);
    }

    // Eliminar un sorteo por su ID
    public void eliminarSorteo(Long id) {
        sorteoRepository.deleteById(id);
    }

    // Actualizar un sorteo
    public Sorteo actualizarSorteo(Long id, Sorteo sorteo) {
        if (sorteoRepository.existsById(id)) {
            sorteo.setId(id);
            return sorteoRepository.save(sorteo);
        }
        return null;
    }
}
