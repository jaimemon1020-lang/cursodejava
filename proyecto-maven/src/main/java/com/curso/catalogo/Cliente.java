package com.curso.catalogo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
    private String direccionEnvio;

    protected Cliente() {
    }

    public Cliente(String nombre, String email, String direccionEnvio) {
        this.nombre = nombre;
        this.email = email;
        this.direccionEnvio = direccionEnvio;
    }

    public Long getId() {
        return this.id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getEmail() {
        return this.email;
    }

    public String getDireccionEnvio() {
        return this.direccionEnvio;
    }
}
