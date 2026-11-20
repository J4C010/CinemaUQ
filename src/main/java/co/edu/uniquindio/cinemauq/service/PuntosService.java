package co.edu.uniquindio.cinemauq.service;

import co.edu.uniquindio.cinemauq.model.*;
import co.edu.uniquindio.cinemauq.model.enums.EstadoFuncion;
import co.edu.uniquindio.cinemauq.model.enums.TipoAsiento;
import co.edu.uniquindio.cinemauq.model.enums.TipoRedencion;
import co.edu.uniquindio.cinemauq.model.excepciones.CinemaUQException;
import co.edu.uniquindio.cinemauq.model.excepciones.FuncionNoDisponibleException;
import co.edu.uniquindio.cinemauq.model.excepciones.PuntosInsuficientesException;
import co.edu.uniquindio.cinemauq.model.excepciones.ValorInvalidoException;
import co.edu.uniquindio.cinemauq.patrones.Creacionales.ConfiguracionCine;
import co.edu.uniquindio.cinemauq.repository.Repositorio;

import java.time.LocalDateTime;
import java.util.List;

public class PuntosService {
    private final Repositorio<Redencion> redenciones;

    public PuntosService(Repositorio<Redencion> redenciones) {
        this.redenciones = redenciones;
    }

    public int puntosDisponibles(Cliente cliente, LocalDateTime ahora) {
        return cliente.getCuentaPuntos().disponibles(ahora.toLocalDate());
    }

    public Redencion redimirEntrada(Cliente cliente, Funcion funcion, Asiento asiento, LocalDateTime ahora)
            throws CinemaUQException {
        if (funcion.getEstado() != EstadoFuncion.PROGRAMADA || !funcion.getFechaHora().isAfter(ahora)) {
            throw new FuncionNoDisponibleException("La función ya empezó o no admite ventas.");
        }
        ConfiguracionCine config = ConfiguracionCine.getInstancia();
        int costo = asiento.tipo() == TipoAsiento.VIP
                ? config.getPuntosEntradaVip() : config.getPuntosEntradaGeneral();

        verificarPuntos(cliente, costo, ahora);
        funcion.reservarAsiento(asiento);
        cliente.getCuentaPuntos().redimir(costo, ahora.toLocalDate());

        Entrada entrada = new Entrada(funcion, asiento, 0);
        return registrar(cliente, TipoRedencion.ENTRADA,
                "Entrada " + asiento.codigo() + " - " + funcion.getPelicula().getTitulo(),
                costo, ahora, entrada);
    }

    public Redencion redimirProducto(Cliente cliente, Producto producto, int cantidad, LocalDateTime ahora)
            throws CinemaUQException {
        int costo = ConfiguracionCine.getInstancia().getPuntosProducto() * validarCantidad(cantidad);
        return canjearConfiteria(cliente, TipoRedencion.PRODUCTO, producto.getNombre(), cantidad, costo, ahora);
    }

    public Redencion redimirCombo(Cliente cliente, Combo combo, int cantidad, LocalDateTime ahora)
            throws CinemaUQException {
        int costo = ConfiguracionCine.getInstancia().getPuntosCombo() * validarCantidad(cantidad);
        return canjearConfiteria(cliente, TipoRedencion.COMBO, combo.getNombre(), cantidad, costo, ahora);
    }

    public List<Redencion> historial(Cliente cliente) {
        return redenciones.listar().stream()
                .filter(r -> r.getCliente() == cliente)
                .toList();
    }

    private Redencion canjearConfiteria(Cliente cliente, TipoRedencion tipo, String nombre,
                                        int cantidad, int costo, LocalDateTime ahora) throws CinemaUQException {
        verificarPuntos(cliente, costo, ahora);
        cliente.getCuentaPuntos().redimir(costo, ahora.toLocalDate());
        return registrar(cliente, tipo, cantidad + " x " + nombre, costo, ahora, null);
    }

    private void verificarPuntos(Cliente cliente, int costo, LocalDateTime ahora) throws PuntosInsuficientesException {
        int disponibles = puntosDisponibles(cliente, ahora);
        if (disponibles < costo) {
            throw new PuntosInsuficientesException(
                    "Puntos vigentes: " + disponibles + ", requeridos: " + costo + ".");
        }
    }

    private int validarCantidad(int cantidad) throws ValorInvalidoException {
        if (cantidad <= 0) {
            throw new ValorInvalidoException("La cantidad debe ser mayor que cero.");
        }
        return cantidad;
    }

    private Redencion registrar(Cliente cliente, TipoRedencion tipo, String descripcion,
                                int puntos, LocalDateTime ahora, Entrada entrada) {
        Redencion redencion = new Redencion(cliente, tipo, descripcion, puntos, ahora, entrada);
        redenciones.guardar(redencion);
        return redencion;
    }
}

