package com.example.sispizza;

public class Pizza {
    private String nombre;
    private int imagen;
    private String descripcion;

    public Pizza(String nombre, int imagen, String descripcion) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public int getImagen() {
        return imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
