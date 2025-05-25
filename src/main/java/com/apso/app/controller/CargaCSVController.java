package com.apso.app.controller;

import java.util.List;

import com.apso.app.repository.EstudianteRepository;
import com.apso.app.service.CargaCSVService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequiredArgsConstructor
public class CargaCSVController {

    private final EstudianteRepository estudianteRepository;
    private final CargaCSVService cargaCSVService;

    /**
     * Este método se ejecuta antes de cada handler y asegura que la variable
     * "esAdmin" esté disponible en el modelo para todas las vistas de este controlador.
     */
    @ModelAttribute("esAdmin")
    public boolean esAdmin(@AuthenticationPrincipal OidcUser oidcUser) {
        if (oidcUser == null) return false;
        List<String> roles = oidcUser.getClaimAsStringList("https://apso.app/claims/roles");
        return roles != null && roles.contains("admin");
        //return oidcUser.getAuthorities().stream()
        //    .anyMatch(auth -> auth.getAuthority().equals("ROLE_admin"));
    }

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
    public String cargarArchivo(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttrs) {
        try {
            cargaCSVService.cargarEstudiantesDesdeCSV(file);
            redirectAttrs.addFlashAttribute("mensaje", "¡Carga exitosa!");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("mensaje", "Error al procesar el archivo: " + e.getMessage());
        }
        return "redirect:/cargacsv";
    }

    @PostMapping("/cargacsv/eliminar")
    public String eliminarTodosLosEstudiantes(RedirectAttributes redirectAttrs) {
        estudianteRepository.deleteAll();
        redirectAttrs.addFlashAttribute("mensaje", "Todos los estudiantes han sido eliminados exitosamente.");
        return "redirect:/cargacsv";
    }
}
