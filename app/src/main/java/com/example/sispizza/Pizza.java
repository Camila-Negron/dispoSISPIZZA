package com.example.sispizza;

public class Pizza {
    private String nombre;
    private int imagen;
    private String descripcion;
    private double precio; // Agrega el campo de precio

    private int cantidad;

    public Pizza(String nombre, int imagen, String descripcion, double precio) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.precio = precio;
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

    public double getPrecio() {
        return precio;
    }



}

