package com.apso.app.config;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final String ROLE_CLAIM = "https://apso.app/claims/roles";

// Version1
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//            .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/", "/css/**", "/js/**", "/images/**", "/public/**").permitAll()
//                .requestMatchers("/admin/**").hasAuthority("ROLE_admin")
//                .requestMatchers("/user/**").hasAuthority("ROLE_user")
//                .anyRequest().authenticated()
//            )
//            .oauth2Login()  // Login con Auth0
//            .and()
//            .logout(logout -> logout.logoutSuccessUrl("/")) // Redirigir a home tras logout
//            .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt
//              .jwtAuthenticationConverter(jwtAuthenticationConverter()))); // Para validar tokens JWT
//
//        return http.build();
//    }

// Version2
//@Bean
//public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    http
//        .authorizeHttpRequests(authz -> authz
//            .requestMatchers("/", "/cargacsv", "/sorteogrupos", "/css/**", "/js/**", "/images/**").permitAll()  // acceso libre (guest)
//            .anyRequest().authenticated()  // todo lo demás requiere login
//        )
//        //.oauth2Login(Customizer.withDefaults())  // habilita login con Auth0
//        .oauth2Login(oauth -> oauth.loginPage("/oauth2/authorization/auth0")) // Habilita login con Auth0 con redirección a: Login with OAuth 2.0
//        .logout(logout -> logout
//            .logoutSuccessUrl("/")
//            .invalidateHttpSession(true)
//            .clearAuthentication(true)
//            .deleteCookies("JSESSIONID")
//        )
//        .oauth2ResourceServer(oauth2 -> oauth2
//            .jwt(jwt -> jwt
//                .jwtAuthenticationConverter(jwtAuthenticationConverter())  // mapea los roles
//            )
//        );
//    return http.build();
//}

// Version3
//@Bean
//public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    http
//        .authorizeHttpRequests(authz -> authz
//            .requestMatchers("/", "/cargacsv", "/css/**", "/js/**", "/images/**").permitAll()  // acceso libre (guest)
//            .requestMatchers(HttpMethod.POST, "/cargacsv/eliminar", "/sorteogrupos").authenticated()  // acceso a eliminar CSV requiere login
//            .anyRequest().authenticated()  // todo lo demás requiere login
//        )
//        .oauth2Login(oauth -> oauth
//            .loginPage("/oauth2/authorization/auth0")
//            .defaultSuccessUrl("/", true)  // Redirige al home tras login
//        )
//        .logout(logout -> logout
//            .logoutSuccessHandler((request, response, authentication) -> {
//                String returnTo = "http://localhost:8080/";
//                String clientId = "spdpLLdmgrGBxpDqRm0PoCQRxnlJEw4t"; 
//                String logoutUrl = "https://dev-e7bxsuqtgxjngf5d.us.auth0.com/v2/logout?client_id=" + clientId + "&returnTo=" + returnTo;
//                response.sendRedirect(logoutUrl);
//            })
//        )
//        .oauth2ResourceServer(oauth2 -> oauth2
//            .jwt(jwt -> jwt
//                .jwtAuthenticationConverter(jwtAuthenticationConverter())  // mapea los roles
//            )
//        );
//    return http.build();
//}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/", "/cargacsv", "/css/**", "/js/**", "/images/**").permitAll()  // acceso libre (guest)
                .requestMatchers(HttpMethod.POST, "/cargacsv/eliminar").hasRole("admin")  // solo admin 
                .requestMatchers(HttpMethod.POST, "/sorteogrupos").authenticated()        // cualquier usuario autenticado
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth -> oauth
                .loginPage("/oauth2/authorization/auth0")
                .defaultSuccessUrl("/", true)
            )
            .logout(logout -> logout
                .logoutSuccessHandler((request, response, authentication) -> {
                    String returnTo = "http://localhost:8080/";
                    String clientId = "spdpLLdmgrGBxpDqRm0PoCQRxnlJEw4t"; 
                    String logoutUrl = "https://dev-e7bxsuqtgxjngf5d.us.auth0.com/v2/logout?client_id=" + clientId + "&returnTo=" + returnTo;
                    response.sendRedirect(logoutUrl);
                })
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                    .jwtAuthenticationConverter(jwtAuthenticationConverter())
                    
                )
            );

        return http.build();
    }

    /**
     * Convierte los claims del token JWT en roles Spring Security.
     */
    private Converter<Jwt, Collection<GrantedAuthority>> authoritiesConverter() {
        return jwt -> {
            List<String> roles = jwt.getClaimAsStringList("https://apso.app/claims/roles");
            System.out.println("🎯 Roles extraídos del JWT: " + roles);
            if (roles == null) return List.of();
            return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
        };
    }

    /**
     * Combinamos el convertidor de authorities personalizado con el convertidor por defecto.
     
    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter());
        return converter;
    }*/

    /**
     * Combinamos el convertidor de authorities personalizado con el convertidor por defecto
     * e imprimimos el JWT para validación en jwt.io.
     */
    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            // ✅ Imprime el token JWT completo en consola
            System.out.println("🛡️ JWT recibido por Spring: " + jwt.getTokenValue());

            // ✅ También imprime los claims completos (opcional, muy útil para debug)
            System.out.println("📦 Todos los claims: " + jwt.getClaims());

            // Sigue con tu conversión de roles personalizada
            return authoritiesConverter().convert(jwt);
        });

        return converter;
    }

}
