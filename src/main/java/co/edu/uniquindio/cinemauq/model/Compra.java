package co.edu.uniquindio.cinemauq.model;

import co.edu.uniquindio.cinemauq.model.enums.EstadoCompra;
import co.edu.uniquindio.cinemauq.model.excepciones.ReembolsoDuplicadoException;
import co.edu.uniquindio.cinemauq.util.Ids;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Compra {
    private final String id = Ids.nuevo("COM");
    private final Cliente cliente;
    private final TarjetaVirtual tarjetaUsada;
    private final LocalDateTime fecha;
    private final List<Entrada> entradas = new ArrayList<>();
    private final List<ItemCompra> items = new ArrayList<>();
    private EstadoCompra estado = EstadoCompra.PAGADA;
    private boolean reembolsada = false;
    private double valorReembolsado = 0;

    public Compra(Cliente cliente, LocalDateTime fecha) {
        this.cliente = cliente;
        this.tarjetaUsada = cliente.getTarjeta();
        this.fecha = fecha;
    }

    public void agregarEntrada(Entrada entrada) { entradas.add(entrada); }
    public void agregarItem(ItemCompra item) { items.add(item); }

    public double getTotal() {
        double total = entradas.stream().mapToDouble(Entrada::getPrecio).sum();
        total += items.stream().mapToDouble(ItemCompra::subtotal).sum();
        return total;
    }

    public void marcarReembolsada(double valor, EstadoCompra nuevoEstado) throws ReembolsoDuplicadoException {
        if (reembolsada) {
            throw new ReembolsoDuplicadoException("La compra " + id + " ya fue reembolsada.");
        }
        this.reembolsada = true;
        this.valorReembolsado = valor;
        this.estado = nuevoEstado;
    }

    public String getId() {
        return id; }
    public Cliente getCliente() {
        return cliente; }
    public TarjetaVirtual getTarjetaUsada() {
        return tarjetaUsada; }
    public LocalDateTime getFecha() {
        return fecha; }
    public List<Entrada> getEntradas() {
        return Collections.unmodifiableList(entradas); }
    public List<ItemCompra> getItems() {
        return Collections.unmodifiableList(items); }
    public EstadoCompra getEstado() {
        return estado; }
    public boolean isReembolsada() {
        return reembolsada; }
    public double getValorReembolsado() {
        return valorReembolsado; }
}
