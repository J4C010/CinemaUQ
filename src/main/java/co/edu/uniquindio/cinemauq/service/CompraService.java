package co.edu.uniquindio.cinemauq.service;

import co.edu.uniquindio.cinemauq.model.Cliente;
import co.edu.uniquindio.cinemauq.model.Compra;
import co.edu.uniquindio.cinemauq.model.Entrada;
import co.edu.uniquindio.cinemauq.model.excepciones.CinemaUQException;
import co.edu.uniquindio.cinemauq.patrones.Creacionales.CompraBuilder;
import co.edu.uniquindio.cinemauq.patrones.Creacionales.ConfiguracionCine;
import co.edu.uniquindio.cinemauq.repository.Repositorio;

import java.util.ArrayList;
import java.util.List;

public class CompraService {
    private final Repositorio<Compra> compras;

    public CompraService(Repositorio<Compra> compras) {
        this.compras = compras;
    }

    public Compra comprar(CompraBuilder builder) throws CinemaUQException {
        Compra compra = builder.build();
        Cliente cliente = compra.getCliente();
        double total = compra.getTotal();

        cliente.getTarjeta().validarPago(total);
        reservarAsientos(compra);

        try {
            cliente.getTarjeta().debitar(total, "Compra " + compra.getId(), compra.getId());
        } catch (CinemaUQException e) {
            liberarAsientos(compra.getEntradas());
            throw e;
        }

        int puntos = ConfiguracionCine.getInstancia().puntosGanados(total);
        cliente.getCuentaPuntos().acumular(puntos, compra.getFecha().toLocalDate());
        cliente.agregarCompra(compra);
        compras.guardar(compra);
        return compra;
    }

    private void reservarAsientos(Compra compra) throws CinemaUQException {
        List<Entrada> reservadas = new ArrayList<>();
        try {
            for (Entrada entrada : compra.getEntradas()) {
                entrada.getFuncion().reservarAsiento(entrada.getAsiento());
                reservadas.add(entrada);
            }
        } catch (CinemaUQException e) {
            liberarAsientos(reservadas);
            throw e;
        }
    }

    private void liberarAsientos(List<Entrada> entradas) {
        for (Entrada entrada : entradas) {
            entrada.getFuncion().liberarAsiento(entrada.getAsiento());
        }
    }
}