package com.apso.app.controller;

import com.apso.app.service.CargaCSVService;
import com.apso.app.repository.EstudianteRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class CargaCSVController {

    private final EstudianteRepository estudianteRepository;
    private final CargaCSVService cargaCSVService;

    @GetMapping("/cargacsv")
    public String mostrarFormularioCarga(@AuthenticationPrincipal OidcUser oidcUser, Model model) {
        if (oidcUser != null) {
            model.addAttribute("userName", oidcUser.getFullName());
            model.addAttribute("userEmail", oidcUser.getEmail());
        }
        model.addAttribute("estudiantes", estudianteRepository.findAll());
        return "cargacsv";
    }

    @PostMapping("/cargacsv")
    public String cargarArchivo(@RequestParam("file") MultipartFile file, Model model) {
        model.addAttribute("estudiantes", estudianteRepository.findAll());
        try {
            cargaCSVService.cargarEstudiantesDesdeCSV(file);
            model.addAttribute("mensaje", "¡Carga exitosa!");
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al procesar el archivo: " + e.getMessage());
        }
        return "redirect:/cargacsv";
    }

    @PostMapping("/cargacsv/eliminar")
    public String eliminarTodosLosEstudiantes(@AuthenticationPrincipal OidcUser oidcUser, Model model) {
        model.addAttribute("estudiantes", estudianteRepository.findAll());
        estudianteRepository.deleteAll(); // Borra todos los registros
        model.addAttribute("mensaje", "Todos los estudiantes han sido eliminados exitosamente.");
        return "redirect:/cargacsv";
    }

}
