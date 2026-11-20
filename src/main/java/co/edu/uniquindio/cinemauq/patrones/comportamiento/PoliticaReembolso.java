package co.edu.uniquindio.cinemauq.patrones.comportamiento;

import co.edu.uniquindio.cinemauq.model.excepciones.CancelacionNoPermitidaException;

public interface PoliticaReembolso {
    double calcular(double valorPagado) throws CancelacionNoPermitidaException;

    static PoliticaReembolso segunTiempo(double horasRestantes) {
        if (horasRestantes > 24) return new ReembolsoCompleto();
        if (horasRestantes >= 2) return new ReembolsoParcial();
        return new SinReembolso();
    }
}