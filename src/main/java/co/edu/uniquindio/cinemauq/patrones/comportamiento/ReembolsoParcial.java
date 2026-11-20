package co.edu.uniquindio.cinemauq.patrones.comportamiento;

public class ReembolsoParcial implements PoliticaReembolso {
    @Override
    public double calcular(double valorPagado) {
        return Math.round(valorPagado * 0.80);
    }
}
