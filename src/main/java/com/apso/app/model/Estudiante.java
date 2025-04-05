package com.apso.app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "estudiantes")
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Cambié 'log' a 'Long'

    @Column(nullable = false)
    private String nombre;

    // Constructor vacío (necesario para JPA)
    public Estudiante() {
    }

    // Constructor con parámetros
    public Estudiante(String nombre) {
        this.nombre = nombre;
    }

    // Getters y Setters
    public Long getId() { // Cambié 'log' a 'Long'
        return id;
    }

    public void setId(Long id) { // Cambié 'log' a 'Long'
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

