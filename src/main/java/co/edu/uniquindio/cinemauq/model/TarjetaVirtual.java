package co.edu.uniquindio.cinemauq.model;

import co.edu.uniquindio.cinemauq.model.enums.EstadoTarjeta;
import co.edu.uniquindio.cinemauq.model.enums.TipoMovimiento;
import co.edu.uniquindio.cinemauq.model.excepciones.SaldoInsuficienteException;
import co.edu.uniquindio.cinemauq.model.excepciones.TarjetaInactivaException;
import co.edu.uniquindio.cinemauq.model.excepciones.ValorInvalidoException;
import co.edu.uniquindio.cinemauq.util.Ids;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TarjetaVirtual {
        private final String id = Ids.nuevo("TAR");
        private double saldo;
        private EstadoTarjeta estado = EstadoTarjeta.ACTIVA;
        private final List<MovimientoTarjeta> movimientos = new ArrayList<>();

        public void validarPago(double valor) throws TarjetaInactivaException, SaldoInsuficienteException {
            if (estado != EstadoTarjeta.ACTIVA) {
                throw new TarjetaInactivaException("La tarjeta " + id + " está bloqueada.");
            }
            if (saldo < valor) {
                throw new SaldoInsuficienteException(
                        "Saldo insuficiente: disponible " + saldo + ", requerido " + valor + ".");
            }
        }
        public void debitar(double valor, String descripcion, String compraId)
                throws TarjetaInactivaException, SaldoInsuficienteException, ValorInvalidoException {
            exigirPositivo(valor);
            validarPago(valor);
            saldo -= valor;
            movimientos.add(new MovimientoTarjeta(TipoMovimiento.PAGO, valor, descripcion, compraId));
        }

        public void acreditar(TipoMovimiento tipo, double valor, String descripcion, String compraId)
                throws ValorInvalidoException {
            exigirPositivo(valor);
            saldo += valor;
            movimientos.add(new MovimientoTarjeta(tipo, valor, descripcion, compraId));
        }
        public void bloquear() {
            estado = EstadoTarjeta.BLOQUEADA; }
        public void activar() {
            estado = EstadoTarjeta.ACTIVA; }

        private void exigirPositivo(double valor) throws ValorInvalidoException {
            if (valor <= 0) {
                throw new ValorInvalidoException("El valor debe ser mayor que cero.");
            }
        }

        public String getId() {
            return id; }
        public double getSaldo() {
            return saldo; }
        public EstadoTarjeta getEstado() {
            return estado; }
        public List<MovimientoTarjeta> getMovimientos() {
            return Collections.unmodifiableList(movimientos); }

}
