package co.edu.uniquindio.cinemauq.model;

import java.time.LocalDate;

public class LoteDePuntos {
    private final int puntosIniciales;
    private int puntosRestantes;
    private final LocalDate fechaObtencion;
    private final LocalDate fechaVencimiento;

    public LoteDePuntos(int puntos, LocalDate fechaObtencion) {
        this.puntosIniciales = puntos;
        this.puntosRestantes = puntos;
        this.fechaObtencion = fechaObtencion;
        this.fechaVencimiento = fechaObtencion.plusYears(1);
    }

    public boolean estaVigente(LocalDate hoy) {
        return !hoy.isAfter(fechaVencimiento);
    }

    public int consumir(int solicitados) {
        int tomados = Math.min(solicitados, puntosRestantes);
        puntosRestantes -= tomados;
        return tomados;
    }

    public int getPuntosIniciales() {
        return puntosIniciales; }
    public int getPuntosRestantes() {
        return puntosRestantes; }
    public LocalDate getFechaObtencion() {
        return fechaObtencion; }
    public LocalDate getFechaVencimiento() {
        return fechaVencimiento; }
}
