package com.apso.app.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@ControllerAdvice
public class GlobalModelAttributes {
    @ModelAttribute("userName")
    public String userName(Principal principal) {
        return principal != null ? principal.getName() : null;
    }

    @ModelAttribute("userEmail")
    public String userEmail(Principal principal) {
        // Si se una Auth0 con más atributos, colocar acá los OidcUser
        return principal != null ? principal.getName() : null;
    }
}
