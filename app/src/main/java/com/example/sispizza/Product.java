package com.example.sispizza;

public class Product {
    private int id;
    private String name;
    private double price;

    // Constructor para crear un nuevo producto (sin ID, para cuando se inserta un nuevo producto en la DB)
    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    // Constructor completo, incluyendo ID (útil para recuperar productos de la DB)
    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
