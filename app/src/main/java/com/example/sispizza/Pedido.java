package com.example.sispizza;

public class Pedido {
    private int id;
    private String cliente;
    private String descripcion;
    private int cantidad;
    private double precioTotal;
    private String salsa;

    // Constructor, getters y setters...

    // Por ejemplo:
    public Pedido(int id, String cliente, String descripcion, int cantidad, double precioTotal, String salsa) {
        this.id = id;
        this.cliente = cliente;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precioTotal = precioTotal;
        this.salsa = salsa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public String getSalsa() {
        return salsa;
    }

    public void setSalsa(String salsa) {
        this.salsa = salsa;
    }
}
