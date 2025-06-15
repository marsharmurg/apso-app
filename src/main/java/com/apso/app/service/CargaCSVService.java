package com.apso.app.service;

import com.apso.app.model.Estudiante;
import com.apso.app.model.Usuario;
import com.apso.app.model.Usuario;
import com.apso.app.repository.EstudianteRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CargaCSVService {

    private final EstudianteRepository estudianteRepository = null;
    private final UsuarioService usuarioService = null;
    private final HttpServletRequest request = null;

    public void cargarEstudiantesDesdeCSV(MultipartFile archivoCSV) throws Exception {
        List<Estudiante> estudiantes = new ArrayList<>();

        Usuario usuarioActual = usuarioService.obtenerUsuarioDesdeRequest(request);

        Usuario usuarioActual = usuarioService.obtenerUsuarioDesdeRequest(request);

        try (
            BufferedReader reader = new BufferedReader(new InputStreamReader(archivoCSV.getInputStream(), StandardCharsets.UTF_8));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())
        ) {
            for (CSVRecord record : csvParser) {
                Estudiante estudiante = new Estudiante();
                estudiante.setNombre(record.get("nombre"));
                estudiante.setEmail(record.get("email"));
                estudiante.setGrupoTeorico(record.get("grupo_teorico"));
                estudiante.setAsignatura(record.get("asignatura"));
                estudiante.setCargaId(Long.parseLong(record.get("carga_id")));
                estudiante.setUsuario(usuarioActual); // vínculo directo al usuario

                estudiantes.add(estudiante);
            }

            estudianteRepository.saveAll(estudiantes);
        }
    }
}
