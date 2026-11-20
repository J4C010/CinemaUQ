package co.edu.uniquindio.cinemauq.model;

import co.edu.uniquindio.cinemauq.model.enums.TipoAsiento;
import co.edu.uniquindio.cinemauq.util.Ids;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Sala {
    private final String id = Ids.nuevo("SAL");
    private String nombre;
    private final int filas;
    private final int columnas;
    private final List<Asiento> asientos = new ArrayList<>();

    public Sala(String nombre, int filas, int columnas) {
        this.nombre = nombre;
        this.filas = filas;
        this.columnas = columnas;
        for (int f = 0; f < filas; f++) {
            TipoAsiento tipo = (f == filas - 1) ? TipoAsiento.VIP : TipoAsiento.GENERAL;
            for (int c = 0; c < columnas; c++) {
                asientos.add(new Asiento(f, c, tipo));
            }
        }
    }

    public boolean contiene(Asiento asiento) {
        return asientos.contains(asiento); }
    public int capacidad() {
        return filas * columnas; }

    public String getId() {
        return id; }
    public String getNombre() {
        return nombre; }
    public void setNombre(String nombre) {
        this.nombre = nombre; }
    public int getFilas() {
        return filas; }
    public int getColumnas() {
        return columnas; }
    public List<Asiento> getAsientos() {
        return Collections.unmodifiableList(asientos); }

    @Override
    public String toString() {
        return nombre; }
}
