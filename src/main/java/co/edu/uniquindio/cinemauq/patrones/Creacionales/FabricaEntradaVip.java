package co.edu.uniquindio.cinemauq.patrones.Creacionales;

import co.edu.uniquindio.cinemauq.model.Asiento;
import co.edu.uniquindio.cinemauq.model.Entrada;
import co.edu.uniquindio.cinemauq.model.Funcion;

public class FabricaEntradaVip implements FabricaEntrada {
    @Override
    public Entrada crear(Funcion funcion, Asiento asiento) {
        double recargo = ConfiguracionCine.getInstancia().getRecargoVip();
        double precio = Math.round(funcion.getPrecioBase() * (1 + recargo));
        return new Entrada(funcion, asiento, precio);
    }
}

