package co.edu.uniquindio.cinemauq.service;

import co.edu.uniquindio.cinemauq.model.Cliente;
import co.edu.uniquindio.cinemauq.model.Usuario;
import co.edu.uniquindio.cinemauq.model.enums.Rol;
import co.edu.uniquindio.cinemauq.model.enums.TipoMovimiento;
import co.edu.uniquindio.cinemauq.model.excepciones.AccionNoPermitidaException;
import co.edu.uniquindio.cinemauq.model.excepciones.ValorInvalidoException;

public class TarjetaService {

    public void recargar(Usuario solicitante, Cliente cliente, double valor)
            throws AccionNoPermitidaException, ValorInvalidoException {
        if (solicitante.getRol() != Rol.ADMINISTRADOR) {
            throw new AccionNoPermitidaException("Solo el administrador puede recargar tarjetas.");
        }
        cliente.getTarjeta().acreditar(
                TipoMovimiento.RECARGA,
                valor,
                "Recarga realizada por " + solicitante.getNombre(),
                null);
    }
}
