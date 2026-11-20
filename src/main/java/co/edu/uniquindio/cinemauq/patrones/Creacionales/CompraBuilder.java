package co.edu.uniquindio.cinemauq.patrones.Creacionales;

import co.edu.uniquindio.cinemauq.model.*;
import co.edu.uniquindio.cinemauq.model.enums.EstadoFuncion;
import co.edu.uniquindio.cinemauq.model.excepciones.AsientoNoDisponibleException;
import co.edu.uniquindio.cinemauq.model.excepciones.CinemaUQException;
import co.edu.uniquindio.cinemauq.model.excepciones.FuncionNoDisponibleException;
import co.edu.uniquindio.cinemauq.model.excepciones.ValorInvalidoException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CompraBuilder {
    private final Cliente cliente;
    private final LocalDateTime fecha;
    private Funcion funcion;
    private final List<Asiento> asientos = new ArrayList<>();
    private final List<ItemCompra> items = new ArrayList<>();

    public CompraBuilder(Cliente cliente, LocalDateTime fecha) {
        this.cliente = cliente;
        this.fecha = fecha;
    }

    public CompraBuilder funcion(Funcion funcion) {
        this.funcion = funcion;
        return this;
    }

    public CompraBuilder agregarAsiento(Asiento asiento) {
        asientos.add(asiento);
        return this;
    }

    public CompraBuilder agregarItem(ItemConfiteria item, int cantidad) {
        items.add(new ItemCompra(item, cantidad));
        return this;
    }

    public Compra build() throws CinemaUQException {
        if (asientos.isEmpty() && items.isEmpty()) {
            throw new ValorInvalidoException("La compra debe incluir al menos una entrada o un producto.");
        }
        for (ItemCompra item : items) {
            if (item.getCantidad() <= 0) {
                throw new ValorInvalidoException("La cantidad de " + item.getItem().getNombre() + " debe ser mayor que cero.");
            }
        }
        if (!asientos.isEmpty()) {
            validarEntradas();
        }

        Compra compra = new Compra(cliente, fecha);
        for (Asiento asiento : asientos) {
            compra.agregarEntrada(FabricaEntrada.para(asiento.tipo()).crear(funcion, asiento));
        }
        items.forEach(compra::agregarItem);
        return compra;
    }

    private void validarEntradas() throws CinemaUQException {
        if (funcion == null) {
            throw new ValorInvalidoException("Debe seleccionar una función para comprar entradas.");
        }
        if (funcion.getEstado() != EstadoFuncion.PROGRAMADA || !funcion.getFechaHora().isAfter(fecha)) {
            throw new FuncionNoDisponibleException("La función ya empezó o no admite ventas.");
        }
        int max = ConfiguracionCine.getInstancia().getMaxEntradasPorCompra();
        if (asientos.size() > max) {
            throw new ValorInvalidoException("Máximo " + max + " entradas por compra.");
        }
        Set<Asiento> vistos = new HashSet<>();
        for (Asiento asiento : asientos) {
            if (!vistos.add(asiento) || !funcion.estaDisponible(asiento)) {
                throw new AsientoNoDisponibleException("El asiento " + asiento.codigo() + " no está disponible.");
            }
        }
    }
}
