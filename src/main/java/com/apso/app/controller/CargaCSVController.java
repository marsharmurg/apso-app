package com.apso.app.controller;

import com.apso.app.service.CargaCSVService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class CargaCSVController {

    private final CargaCSVService cargaCSVService;

    @GetMapping("/cargacsv")
    public String mostrarFormularioCarga() {
        return "cargacsv";
    }

    @PostMapping("/cargacsv")
    public String cargarArchivo(@RequestParam("file") MultipartFile file, Model model) {
        try {
            cargaCSVService.cargarEstudiantesDesdeCSV(file);
            model.addAttribute("mensaje", "¡Carga exitosa!");
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al procesar el archivo: " + e.getMessage());
        }
        return "cargacsv";
    }
}
