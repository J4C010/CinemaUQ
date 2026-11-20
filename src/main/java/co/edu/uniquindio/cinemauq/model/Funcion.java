package co.edu.uniquindio.cinemauq.model;

import co.edu.uniquindio.cinemauq.model.enums.EstadoFuncion;
import co.edu.uniquindio.cinemauq.model.excepciones.AsientoNoDisponibleException;
import co.edu.uniquindio.cinemauq.model.excepciones.FuncionNoDisponibleException;
import co.edu.uniquindio.cinemauq.util.Ids;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Funcion {
    private final String id = Ids.nuevo("FUN");
    private final Pelicula pelicula;
    private final Sala sala;
    private final LocalDateTime fechaHora;
    private final double precioBase;
    private EstadoFuncion estado = EstadoFuncion.PROGRAMADA;
    private final Set<Asiento> asientosOcupados = new HashSet<>();

    public Funcion(Pelicula pelicula, Sala sala, LocalDateTime fechaHora, double precioBase) {
        this.pelicula = pelicula;
        this.sala = sala;
        this.fechaHora = fechaHora;
        this.precioBase = precioBase;
    }

    public boolean estaDisponible(Asiento asiento) {
        return sala.contiene(asiento) && !asientosOcupados.contains(asiento);
    }

    public void reservarAsiento(Asiento asiento) throws AsientoNoDisponibleException, FuncionNoDisponibleException {
        if (estado != EstadoFuncion.PROGRAMADA) {
            throw new FuncionNoDisponibleException("La función " + id + " no admite ventas.");
        }
        if (!estaDisponible(asiento)) {
            throw new AsientoNoDisponibleException("El asiento " + asiento.codigo() + " no está disponible.");
        }
        asientosOcupados.add(asiento);
    }

    public void liberarAsiento(Asiento asiento) {
        asientosOcupados.remove(asiento);
    }

    public void cancelar() {
        estado = EstadoFuncion.CANCELADA; }

    public double horasRestantes(LocalDateTime ahora) {
        return Duration.between(ahora, fechaHora).toMinutes() / 60.0;
    }

    public int asientosVendidos() {
        return asientosOcupados.size(); }
    public double porcentajeOcupacion() {
        return 100.0 * asientosOcupados.size() / sala.capacidad(); }

    public String getId() {
        return id; }
    public Pelicula getPelicula() {
        return pelicula; }
    public Sala getSala() {
        return sala; }
    public LocalDateTime getFechaHora() {
        return fechaHora; }
    public double getPrecioBase() {
        return precioBase; }
    public EstadoFuncion getEstado() {
        return estado; }
    public Set<Asiento> getAsientosOcupados() {
        return Collections.unmodifiableSet(asientosOcupados); }
}
