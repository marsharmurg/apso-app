package com.apso.app.service;

import com.apso.app.model.Estudiante;
import com.apso.app.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    // Método para obtener todos los estudiantes
    public List<Estudiante> obtenerTodosLosEstudiantes() {
        return estudianteRepository.findAll();
    }

    // Método para obtener un estudiante por su ID
    public Optional<Estudiante> obtenerEstudiantePorId(Long id) {
        return estudianteRepository.findById(id);
    }

    // Método para crear un nuevo estudiante
    public Estudiante crearEstudiante(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    // Método para eliminar un estudiante por su ID
    public void eliminarEstudiante(Long id) {
        estudianteRepository.deleteById(id);
    }

    // Método para actualizar un estudiante
    public Estudiante actualizarEstudiante(Long id, Estudiante estudiante) {
        if (estudianteRepository.existsById(id)) {
            estudiante.setId(id);
            return estudianteRepository.save(estudiante);
        } else {
            return null; // Estudiante no encontrado
        }
    }
}

