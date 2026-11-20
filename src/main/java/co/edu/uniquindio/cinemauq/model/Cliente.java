package co.edu.uniquindio.cinemauq.model;

import co.edu.uniquindio.cinemauq.model.enums.Rol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cliente extends Usuario {
    private final TarjetaVirtual tarjeta;
    private final CuentaPuntos cuentaPuntos;
    private final List<Compra> compras = new ArrayList<>();

    public Cliente(String nombre, String correo, String contrasena) {
        super("CLI", nombre, correo, contrasena);
        this.tarjeta = new TarjetaVirtual();
        this.cuentaPuntos = new CuentaPuntos();
    }

    @Override
    public Rol getRol() {
        return Rol.CLIENTE;
    }

    public void agregarCompra(Compra compra) {
        compras.add(compra);
    }

    public TarjetaVirtual getTarjeta() {
        return tarjeta; }
    public CuentaPuntos getCuentaPuntos() {
        return cuentaPuntos; }
    public List<Compra> getCompras() {
        return Collections.unmodifiableList(compras); }
}
