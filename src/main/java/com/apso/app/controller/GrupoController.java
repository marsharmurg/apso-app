package com.apso.app.controller;

import com.apso.app.model.Estudiante;
import com.apso.app.model.SorteoGrupal;
import com.apso.app.model.Usuario;
import com.apso.app.repository.EstudianteRepository;
import com.apso.app.repository.SorteoGrupalRepository;
import com.apso.app.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class GrupoController {

    private final EstudianteRepository estudianteRepository;
    private final SorteoGrupalRepository sorteoGrupalRepository;
    private final UsuarioRepository usuarioRepository;

    private List<List<Estudiante>> resultadoTemporal; // Guarda el sorteo no guardado
    private int cantidadGruposTemporal = 0;

    @GetMapping("/sorteogrupos")
    public String mostrarFormularioSorteo(@AuthenticationPrincipal OidcUser oidcUser, Model model) {
        agregarDatosUsuario(model, oidcUser);
        model.addAttribute("grupos", null); // Sin sorteo al inicio
        return "sorteogrupos";
    }

    @PostMapping("/sorteogrupos")
    public String procesarSorteo(@AuthenticationPrincipal OidcUser oidcUser,
                                 @RequestParam("cantidadGrupos") int cantidadGrupos,
                                 Model model) {
        agregarDatosUsuario(model, oidcUser);

        List<Estudiante> estudiantes = estudianteRepository.findAll();
        if (cantidadGrupos < 1 || cantidadGrupos > estudiantes.size() / 2) {
            model.addAttribute("error", "La cantidad de grupos debe estar entre 1 y " + (estudiantes.size() / 2));
            return "sorteogrupos";
        }

        Collections.shuffle(estudiantes);
        List<List<Estudiante>> grupos = new ArrayList<>();
        for (int i = 0; i < cantidadGrupos; i++) {
            grupos.add(new ArrayList<>());
        }

        int indexGrupo = 0;
        for (Estudiante estudiante : estudiantes) {
            grupos.get(indexGrupo).add(estudiante);
            indexGrupo = (indexGrupo + 1) % cantidadGrupos;
        }

        resultadoTemporal = grupos;
        cantidadGruposTemporal = cantidadGrupos;

        model.addAttribute("grupos", grupos);
        model.addAttribute("cantidadGrupos", cantidadGrupos);
        model.addAttribute("mostrarBotones", true);
        return "sorteogrupos";
    }
    /*
    @PostMapping("/sorteogrupos/guardar")
    public String guardarSorteo(@AuthenticationPrincipal OidcUser oidcUser,
                                @RequestParam("titulo") String titulo,
                                Model model) {
        agregarDatosUsuario(model, oidcUser);

        if (oidcUser == null || resultadoTemporal == null || resultadoTemporal.isEmpty()) {
            model.addAttribute("error", "No hay un sorteo para guardar.");
            return "sorteogrupos";
        }

        // Obtener o crear usuario desde Auth0
        String sub = oidcUser.getSubject();
        Usuario usuario = usuarioRepository.findByAuth0Id(sub)
                .orElseGet(() -> {
                    Usuario nuevo = new Usuario();
                    nuevo.setSub(sub);
                    nuevo.setNombre(oidcUser.getFullName());
                    nuevo.setEmail(oidcUser.getEmail());
                    return usuarioRepository.save(nuevo);
                });

        // Crear sorteo
        SorteoGrupal sorteo = new SorteoGrupal();
        sorteo.setTitulo(titulo);
        sorteo.setCantidadGrupos(cantidadGruposTemporal);
        sorteo.setFechaHora(LocalDateTime.now());
        sorteo.setUsuario(usuario);

        // Guardar el resultado como texto plano
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < resultadoTemporal.size(); i++) {
            sb.append("Grupo ").append(i + 1).append(":\n");
            for (Estudiante est : resultadoTemporal.get(i)) {
                sb.append("- ").append(est.getNombre()).append(" (").append(est.getEmail()).append(")\n");
            }
            sb.append("\n");
        }
        sorteo.setResultado(sb.toString());

        // Agregar estudiantes al sorteo
        List<Estudiante> todosLosEstudiantes = resultadoTemporal.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        sorteo.setEstudiantes(todosLosEstudiantes);

        // Guardar sorteo
        sorteoGrupalRepository.save(sorteo);

        model.addAttribute("mensaje", "Sorteo guardado exitosamente como: " + titulo);
        resultadoTemporal = null;
        cantidadGruposTemporal = 0;

        return "redirect:/sorteogrupos";
    }
        */    @PostMapping("/sorteogrupos/guardar")
    public String guardarSorteo(
            @AuthenticationPrincipal OidcUser oidcUser,
            @RequestParam("titulo") String titulo,
            RedirectAttributes redirectAttributes) {
        try {
            // Verificar autenticación
            if (oidcUser == null) {
                redirectAttributes.addFlashAttribute("error", "Debes iniciar sesión para guardar un sorteo.");
                return "redirect:/sorteogrupos";
            }

            // Validar que haya un sorteo temporal
            if (resultadoTemporal == null || resultadoTemporal.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "No hay un sorteo para guardar.");
                return "redirect:/sorteogrupos";
            }            // Obtener o crear usuario
            String auth0Id = oidcUser.getSubject();
            Usuario usuario = usuarioRepository.findByAuth0Id(auth0Id)
                .orElseGet(() -> {
                    Usuario nuevo = new Usuario();
                    nuevo.setAuth0Id(auth0Id);
                    nuevo.setNombre(oidcUser.getFullName());
                    nuevo.setEmail(oidcUser.getEmail());
                    return usuarioRepository.save(nuevo);
                });

            // Crear sorteo
            SorteoGrupal sorteo = new SorteoGrupal();
            sorteo.setTitulo(titulo);
            sorteo.setCantidadGrupos(cantidadGruposTemporal);
            sorteo.setFechaHora(LocalDateTime.now());
            sorteo.setUsuario(usuario);

            // Guardar el resultado como texto plano
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < resultadoTemporal.size(); i++) {
                sb.append("Grupo ").append(i + 1).append(":\n");
                for (Estudiante est : resultadoTemporal.get(i)) {
                    sb.append("- ").append(est.getNombre())
                      .append(" (").append(est.getEmail()).append(")\n");
                }
                sb.append("\n");
            }
            sorteo.setResultado(sb.toString());

            // Agregar estudiantes al sorteo
            List<Estudiante> todosLosEstudiantes = resultadoTemporal.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
            sorteo.setEstudiantes(todosLosEstudiantes);

            // Guardar sorteo
            sorteoGrupalRepository.save(sorteo);

            // Limpiar el sorteo temporal
            resultadoTemporal = null;
            cantidadGruposTemporal = 0;

            redirectAttributes.addFlashAttribute("mensaje", "Sorteo guardado exitosamente como: " + titulo);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar el sorteo: " + e.getMessage());
        }

        return "redirect:/sorteogrupos";
    }


    @PostMapping("/sorteogrupos/reiniciar")
    public String reiniciarSorteo(@AuthenticationPrincipal OidcUser oidcUser, Model model) {
        agregarDatosUsuario(model, oidcUser);
        resultadoTemporal = null;
        cantidadGruposTemporal = 0;
        model.addAttribute("mensaje", "Sorteo reiniciado.");
        return "sorteogrupos";
    }    @GetMapping("/historialsorteos")
    public String mostrarHistorialSorteos(@AuthenticationPrincipal OidcUser oidcUser, Model model) {
        // Verificar autenticación
        if (oidcUser == null) {
            model.addAttribute("error", "Debes iniciar sesión para ver el historial.");
            return "redirect:/";
        }

        // Buscar usuario
        String auth0Id = oidcUser.getSubject();
        Usuario usuario = usuarioRepository.findByAuth0Id(auth0Id).orElse(null);
        
        if (usuario == null) {
            model.addAttribute("error", "No se encontró el usuario.");
            return "redirect:/";
        }

        // Obtener sorteos ordenados por fecha
        List<SorteoGrupal> sorteos = sorteoGrupalRepository.findByUsuarioOrderByFechaHoraDesc(usuario);
        model.addAttribute("sorteos", sorteos);

        // Agregar datos del usuario para la vista
        agregarDatosUsuario(model, oidcUser);

        return "historialsorteos";
    }


    private void agregarDatosUsuario(Model model, OidcUser oidcUser) {
        if (oidcUser != null) {
            model.addAttribute("userName", oidcUser.getFullName());
            model.addAttribute("userEmail", oidcUser.getEmail());
        }
    }
}
