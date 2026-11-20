package co.edu.uniquindio.cinemauq.patrones.comportamiento;

import co.edu.uniquindio.cinemauq.model.excepciones.CancelacionNoPermitidaException;

public class SinReembolso implements PoliticaReembolso {
    @Override
    public double calcular(double valorPagado) throws CancelacionNoPermitidaException {
        throw new CancelacionNoPermitidaException(
                "Faltan menos de 2 horas para la función: no se permite cancelar.");
    }
}
