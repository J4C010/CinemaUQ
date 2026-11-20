package co.edu.uniquindio.cinemauq.model;

import co.edu.uniquindio.cinemauq.model.enums.TipoAsiento;

public record Asiento(int fila, int columna, TipoAsiento tipo) {

    public String codigo() {
        return (char) ('A' + fila) + String.valueOf(columna + 1);
    }

    @Override
    public String toString() {
        return codigo(); }
}
