package co.edu.uniquindio.cinemauq.patrones.Creacionales;

import co.edu.uniquindio.cinemauq.model.Asiento;
import co.edu.uniquindio.cinemauq.model.Entrada;
import co.edu.uniquindio.cinemauq.model.Funcion;

public class FabricaEntradaGeneral implements FabricaEntrada {
    @Override
    public Entrada crear(Funcion funcion, Asiento asiento) {
        return new Entrada(funcion, asiento, funcion.getPrecioBase());
    }
}
