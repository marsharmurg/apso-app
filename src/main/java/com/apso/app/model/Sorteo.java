package com.apso.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Sorteo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Campo id agregado como Long

    private String tipo;  // Tipo de sorteo, como "aleatorio", "grupo", etc.
    private String resultado;  // Resultado del sorteo, como el nombre del estudiante o grupo

    // Constructor vacío
    public Sorteo() {}

    // Constructor con parámetros
    public Sorteo(Long id, String tipo, String resultado) {
        this.id = id;
        this.tipo = tipo;
        this.resultado = resultado;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    @Override
    public String toString() {
        return "Sorteo [id=" + id + ", tipo=" + tipo + ", resultado=" + resultado + "]";
    }
}

