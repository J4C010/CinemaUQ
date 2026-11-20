package co.edu.uniquindio.cinemauq.model;

import co.edu.uniquindio.cinemauq.model.enums.CategoriaProducto;
import co.edu.uniquindio.cinemauq.util.Ids;

public class Producto implements ItemConfiteria {
    private final String id = Ids.nuevo("PRO");
    private String nombre;
    private double precio;
    private final CategoriaProducto categoria;

    public Producto(String nombre, double precio, CategoriaProducto categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
    }

    @Override public String getId() {
        return id; }
    @Override public String getNombre() {
        return nombre; }
    @Override public double getPrecio() {
        return precio; }
    public CategoriaProducto getCategoria() {
        return categoria; }
    public void setNombre(String nombre) {
        this.nombre = nombre; }
    public void setPrecio(double precio) {
        this.precio = precio; }

    @Override
    public String toString() {
        return nombre; }
}
