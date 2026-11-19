package co.edu.uniquindio.cinemauq.model;

import co.edu.uniquindio.cinemauq.model.enums.TipoMovimiento;
import co.edu.uniquindio.cinemauq.util.Ids;

import java.time.LocalDateTime;

public class MovimientoTarjeta {
    private final String id = Ids.nuevo("MOV");
    private final TipoMovimiento tipo;
    private final double valor;
    private final LocalDateTime fecha;
    private final String descripcion;
    private final String compraId;

    public MovimientoTarjeta(TipoMovimiento tipo,double valor, String descripcion, String compraId) {
        this.tipo = tipo;
        this.valor = valor;
        this.fecha = LocalDateTime.now();
        this.descripcion = descripcion;
        this.compraId = compraId;
    }
    public String getId() { return id; }
    public TipoMovimiento getTipo() { return tipo; }
    public double getValor() { return valor; }
    public LocalDateTime getFecha() { return fecha; }
    public String getDescripcion() { return descripcion; }
    public String getCompraId() { return compraId; }
}
