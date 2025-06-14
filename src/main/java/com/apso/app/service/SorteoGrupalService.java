package com.apso.app.service;

import com.apso.app.dto.SorteoGrupalRequestDTO;
import com.apso.app.dto.SorteoGrupalResponseDTO;
import com.apso.app.model.*;
import com.apso.app.repository.EstudianteRepository;
import com.apso.app.repository.SorteoGrupalRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SorteoGrupalService {

    private final SorteoGrupalRepository sorteoGrupalRepository;
    private final EstudianteRepository estudianteRepository;

    public SorteoGrupalService(SorteoGrupalRepository sorteoGrupalRepository, EstudianteRepository estudianteRepository) {
        this.sorteoGrupalRepository = sorteoGrupalRepository;
        this.estudianteRepository = estudianteRepository;
    }

    // Método para obtener historial
    public List<SorteoGrupal> obtenerHistorialSorteos() {
        return sorteoGrupalRepository.findAll();
    }

    // Método para crear un sorteo grupal y devolver el DTO
    public SorteoGrupalResponseDTO crearSorteo(SorteoGrupalRequestDTO dto) {
        List<Estudiante> estudiantes = estudianteRepository.findAllById(dto.getEstudiantesIds());

        SorteoGrupal sorteo = new SorteoGrupal();
        sorteo.setNombreSorteo(dto.getNombreSorteo());
        sorteo.setCantidadGrupos(dto.getCantidadGrupos());
        sorteo.setFechaSorteo(LocalDateTime.now());

        List<Grupo> grupos = new ArrayList<>();

        int totalEstudiantes = estudiantes.size();
        int cantidadGrupos = dto.getCantidadGrupos();
        int basePorGrupo = totalEstudiantes / cantidadGrupos;
        int restantes = totalEstudiantes % cantidadGrupos;

        int indexEstudiante = 0;

        for (int i = 1; i <= cantidadGrupos; i++) {
            Grupo grupo = new Grupo();
            grupo.setNumero(i);
            grupo.setSorteo(sorteo);

            int tamañoGrupo = basePorGrupo + (restantes > 0 ? 1 : 0);
            if (restantes > 0) restantes--;

            List<EstudianteGrupo> estudiantesGrupo = new ArrayList<>();

            for (int j = 0; j < tamañoGrupo; j++) {
                Estudiante estudiante = estudiantes.get(indexEstudiante++);
                EstudianteGrupo eg = new EstudianteGrupo();
                eg.setEstudiante(estudiante);
                eg.setGrupo(grupo);
                estudiantesGrupo.add(eg);
            }

            grupo.setEstudianteGrupos(estudiantesGrupo);
            grupos.add(grupo);
        }

        sorteo.setGrupos(grupos);

        SorteoGrupal sorteoGuardado = sorteoGrupalRepository.save(sorteo);

        return mapToResponseDTO(sorteoGuardado);
    }

    // -------------------- MÉTODO NUEVO --------------------
    private SorteoGrupalResponseDTO mapToResponseDTO(SorteoGrupal sorteo) {
        SorteoGrupalResponseDTO dto = new SorteoGrupalResponseDTO();
        dto.setId(sorteo.getId());
        dto.setNombreSorteo(sorteo.getNombreSorteo());
        dto.setCantidadGrupos(sorteo.getCantidadGrupos());
        dto.setFechaSorteo(sorteo.getFechaSorteo());

        List<SorteoGrupalResponseDTO.GrupoDTO> grupoDTOs = new ArrayList<>();

        for (Grupo grupo : sorteo.getGrupos()) {
            SorteoGrupalResponseDTO.GrupoDTO grupoDTO = new SorteoGrupalResponseDTO.GrupoDTO();
            grupoDTO.setNumero(grupo.getNumero());

            List<String> nombres = grupo.getEstudianteGrupos().stream()
                    .map(eg -> eg.getEstudiante().getNombre())
                    .toList();

            grupoDTO.setNombresEstudiantes(nombres);
            grupoDTOs.add(grupoDTO);
        }

        dto.setGrupos(grupoDTOs);
        return dto;
    }
}


