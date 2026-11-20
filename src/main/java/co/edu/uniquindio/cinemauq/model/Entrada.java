package co.edu.uniquindio.cinemauq.model;

import co.edu.uniquindio.cinemauq.util.Ids;

public class Entrada {
    private final String id = Ids.nuevo("ENT");
    private final Funcion funcion;
    private final Asiento asiento;
    private final double precio;

    public Entrada(Funcion funcion, Asiento asiento, double precio) {
        this.funcion = funcion;
        this.asiento = asiento;
        this.precio = precio;
    }

    public String getId() {
        return id; }
    public Funcion getFuncion() {
        return funcion; }
    public Asiento getAsiento() {
        return asiento; }
    public double getPrecio() {
        return precio; }
}
