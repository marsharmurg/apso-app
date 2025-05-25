package com.apso.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;

@Controller
public class InicioController {

    @GetMapping("/")
    public String mostrarInicio(@AuthenticationPrincipal OidcUser oidcUser, Model model) {
        if (oidcUser != null) {
            model.addAttribute("userName", oidcUser.getFullName());
            model.addAttribute("userEmail", oidcUser.getEmail());
        }
        return "inicio";
    }
}
