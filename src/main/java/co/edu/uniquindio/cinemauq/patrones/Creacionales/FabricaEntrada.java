package co.edu.uniquindio.cinemauq.patrones.Creacionales;

import co.edu.uniquindio.cinemauq.model.Asiento;
import co.edu.uniquindio.cinemauq.model.Entrada;
import co.edu.uniquindio.cinemauq.model.Funcion;
import co.edu.uniquindio.cinemauq.model.enums.TipoAsiento;

import static co.edu.uniquindio.cinemauq.model.enums.TipoAsiento.VIP;

public interface FabricaEntrada {
    Entrada crear(Funcion funcion, Asiento asiento);

    static FabricaEntrada para(TipoAsiento tipo) {
        return switch (tipo) {
            case GENERAL -> new FabricaEntradaGeneral();
            case VIP -> new FabricaEntradaVip();
        };
    }
}
