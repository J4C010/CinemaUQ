package co.edu.uniquindio.cinemauq.patrones.comportamiento;

public class ReembolsoCompleto implements PoliticaReembolso {
    @Override
    public double calcular(double valorPagado) {
        return valorPagado;
    }
}
