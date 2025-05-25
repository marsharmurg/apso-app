package com.apso.app.controller;

import com.apso.app.model.Estudiante;
import com.apso.app.repository.EstudianteRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequiredArgsConstructor
public class GrupoController {

    private final EstudianteRepository estudianteRepository;

    // ✅ Muestra la vista con el formulario para realizar el sorteo
    @GetMapping("/sorteogrupos")
    public String mostrarFormularioSorteo(@AuthenticationPrincipal OidcUser oidcUser, Model model) {
        if (oidcUser != null) {
            model.addAttribute("userName", oidcUser.getFullName());
            model.addAttribute("userEmail", oidcUser.getEmail());
        }
        return "sorteogrupos"; // Este archivo HTML debe estar en src/main/resources/templates
    }

    // ✅ Procesa el sorteo al enviar el formulario
    @PostMapping("/sorteogrupos")
    public String procesarSorteo(@AuthenticationPrincipal OidcUser oidcUser, @RequestParam("cantidadGrupos") int cantidadGrupos, Model model) {
        if (oidcUser != null) {
            model.addAttribute("userName", oidcUser.getFullName());
            model.addAttribute("userEmail", oidcUser.getEmail());
        }

        List<Estudiante> estudiantes = estudianteRepository.findAll();

        // Validación del número de grupos
        if (cantidadGrupos < 1 || cantidadGrupos > estudiantes.size() / 2) {
            model.addAttribute("error", "La cantidad de grupos debe estar entre 1 y " + (estudiantes.size() / 2));
            return "sorteogrupos";
        }

        // Mezclar los estudiantes
        Collections.shuffle(estudiantes);

        // Crear listas para cada grupo
        List<List<Estudiante>> grupos = new ArrayList<>();
        for (int i = 0; i < cantidadGrupos; i++) {
            grupos.add(new ArrayList<>());
        }

        // Asignar estudiantes a grupos en orden
        int indexGrupo = 0;
        for (Estudiante estudiante : estudiantes) {
            grupos.get(indexGrupo).add(estudiante);
            indexGrupo = (indexGrupo + 1) % cantidadGrupos;
        }

        // Enviar los datos a la vista
        model.addAttribute("grupos", grupos);
        model.addAttribute("cantidadGrupos", cantidadGrupos);
        return "sorteogrupos";
    }
}
