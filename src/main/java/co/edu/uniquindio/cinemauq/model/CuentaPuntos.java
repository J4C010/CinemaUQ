package co.edu.uniquindio.cinemauq.model;

import co.edu.uniquindio.cinemauq.model.excepciones.PuntosInsuficientesException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CuentaPuntos {
    private final List<LoteDePuntos> lotes = new ArrayList<>();
    private int totalAcumulado = 0;
    private int totalRedimido = 0;

    public void acumular(int puntos, LocalDate fecha) {
        if (puntos <= 0) return;
        lotes.add(new LoteDePuntos(puntos, fecha));
        totalAcumulado += puntos;
    }

    public int disponibles(LocalDate hoy) {
        return lotes.stream()
                .filter(l -> l.estaVigente(hoy))
                .mapToInt(LoteDePuntos::getPuntosRestantes)
                .sum();
    }

    public void redimir(int puntos, LocalDate hoy) throws PuntosInsuficientesException {
        if (disponibles(hoy) < puntos) {
            throw new PuntosInsuficientesException(
                    "Puntos vigentes: " + disponibles(hoy) + ", requeridos: " + puntos + ".");
        }
        int pendientes = puntos;
        List<LoteDePuntos> porVencer = lotes.stream()
                .filter(l -> l.estaVigente(hoy) && l.getPuntosRestantes() > 0)
                .sorted(Comparator.comparing(LoteDePuntos::getFechaVencimiento))
                .toList();
        for (LoteDePuntos lote : porVencer) {
            if (pendientes == 0) break;
            pendientes -= lote.consumir(pendientes);
        }
        totalRedimido += puntos;
    }

    public List<LoteDePuntos> getLotes() {
        return Collections.unmodifiableList(lotes); }
    public int getTotalAcumulado() {
        return totalAcumulado; }
    public int getTotalRedimido() {
        return totalRedimido; }
}
