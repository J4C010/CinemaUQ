package co.edu.uniquindio.cinemauq.service;

import co.edu.uniquindio.cinemauq.model.*;
import co.edu.uniquindio.cinemauq.model.enums.EstadoCompra;
import co.edu.uniquindio.cinemauq.model.enums.EstadoFuncion;
import co.edu.uniquindio.cinemauq.model.enums.Rol;
import co.edu.uniquindio.cinemauq.model.enums.TipoMovimiento;
import co.edu.uniquindio.cinemauq.model.excepciones.*;
import co.edu.uniquindio.cinemauq.patrones.Creacionales.ConfiguracionCine;
import co.edu.uniquindio.cinemauq.patrones.comportamiento.PoliticaReembolso;
import co.edu.uniquindio.cinemauq.repository.Repositorio;

import java.time.LocalDateTime;

public class CancelacionService {
    private final Repositorio<Compra> compras;

    public CancelacionService(Repositorio<Compra> compras) {
        this.compras = compras;
    }

    public double cancelarCompra(Cliente cliente, Compra compra, LocalDateTime ahora) throws CinemaUQException {
        if (compra.getCliente() != cliente) {
            throw new AccionNoPermitidaException("La compra no pertenece al cliente.");
        }
        if (compra.isReembolsada()) {
            throw new ReembolsoDuplicadoException("La compra " + compra.getId() + " ya fue reembolsada.");
        }
        if (compra.getEntradas().isEmpty()) {
            throw new CancelacionNoPermitidaException("Las compras solo de confitería no admiten cancelación.");
        }

        Funcion funcion = compra.getEntradas().get(0).getFuncion();
        PoliticaReembolso politica = PoliticaReembolso.segunTiempo(funcion.horasRestantes(ahora));
        double valor = politica.calcular(compra.getTotal());

        reembolsar(compra, valor, EstadoCompra.CANCELADA_CLIENTE, ahora);
        for (Entrada entrada : compra.getEntradas()) {
            entrada.getFuncion().liberarAsiento(entrada.getAsiento());
        }
        return valor;
    }

    public int cancelarFuncion(Usuario solicitante, Funcion funcion, LocalDateTime ahora) throws CinemaUQException {
        if (solicitante.getRol() != Rol.ADMINISTRADOR) {
            throw new AccionNoPermitidaException("Solo el administrador puede cancelar funciones.");
        }
        if (funcion.getEstado() == EstadoFuncion.CANCELADA) {
            throw new FuncionNoDisponibleException("La función ya está cancelada.");
        }
        funcion.cancelar();

        int reembolsadas = 0;
        for (Compra compra : compras.listar()) {
            boolean esDeLaFuncion = compra.getEntradas().stream().anyMatch(e -> e.getFuncion() == funcion);
            if (esDeLaFuncion && !compra.isReembolsada()) {
                reembolsar(compra, compra.getTotal(), EstadoCompra.CANCELADA_POR_CINE, ahora);
                reembolsadas++;
            }
        }
        return reembolsadas;
    }

    private void reembolsar(Compra compra, double valor, EstadoCompra nuevoEstado, LocalDateTime ahora)
            throws CinemaUQException {
        compra.marcarReembolsada(valor, nuevoEstado);
        compra.getTarjetaUsada().acreditar(TipoMovimiento.REEMBOLSO, valor,
                "Reembolso de la compra " + compra.getId(), compra.getId());
        int puntos = ConfiguracionCine.getInstancia().puntosGanados(compra.getTotal());
        compra.getCliente().getCuentaPuntos().revertir(puntos, ahora.toLocalDate());
    }
}
