package co.edu.uniquindio.cinemauq.model;

import co.edu.uniquindio.cinemauq.util.Ids;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Combo implements ItemConfiteria {
    private final String id = Ids.nuevo("CMB");
    private String nombre;
    private double precio;
    private final List<Producto> productos = new ArrayList<>();

    public Combo(String nombre, double precio, List<Producto> productos) {
        this.nombre = nombre;
        this.precio = precio;
        this.productos.addAll(productos);
    }

    public double precioSinDescuento() {
        return productos.stream().mapToDouble(Producto::getPrecio).sum();
    }

    @Override public String getId() {
        return id; }
    @Override public String getNombre() {
        return nombre; }
    @Override public double getPrecio() {
        return precio; }
    public List<Producto> getProductos() {
        return Collections.unmodifiableList(productos); }
    public void setNombre(String nombre) {
        this.nombre = nombre; }
    public void setPrecio(double precio) {
        this.precio = precio; }

    @Override
    public String toString() {
        return nombre; }
}
