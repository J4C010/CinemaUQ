package co.edu.uniquindio.cinemauq.model;

import co.edu.uniquindio.cinemauq.model.enums.TipoRedencion;
import co.edu.uniquindio.cinemauq.util.Ids;
import java.time.LocalDateTime;

public class Redencion {
    private final String id = Ids.nuevo("RED");
    private final Cliente cliente;
    private final TipoRedencion tipo;
    private final String descripcion;
    private final int puntos;
    private final LocalDateTime fecha;
    private final Entrada entrada;

    public Redencion(Cliente cliente, TipoRedencion tipo, String descripcion,
                     int puntos, LocalDateTime fecha, Entrada entrada) {
        this.cliente = cliente;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.puntos = puntos;
        this.fecha = fecha;
        this.entrada = entrada;
    }

    public String getId() {
        return id; }
    public Cliente getCliente() {
        return cliente; }
    public TipoRedencion getTipo() {
        return tipo; }
    public String getDescripcion() {
        return descripcion; }
    public int getPuntos() {
        return puntos; }
    public LocalDateTime getFecha() {
        return fecha; }
    public Entrada getEntrada() {
        return entrada; }
}
