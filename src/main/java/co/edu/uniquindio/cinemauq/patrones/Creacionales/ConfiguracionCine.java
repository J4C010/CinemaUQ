package co.edu.uniquindio.cinemauq.patrones.Creacionales;

public final class ConfiguracionCine {
    private static final ConfiguracionCine INSTANCIA = new ConfiguracionCine();

    private final double recargoVip = 0.30;
    private final int maxEntradasPorCompra = 8;
    private final double pesosPorPunto = 1000;
    private final int puntosEntradaGeneral = 150;
    private final int puntosEntradaVip = 200;
    private final int puntosProducto = 60;
    private final int puntosCombo = 120;

    private ConfiguracionCine() {}

    public static ConfiguracionCine getInstancia() {
        return INSTANCIA;
    }

    public int puntosGanados(double valorPagado) {
        return (int) (valorPagado / pesosPorPunto);
    }

    public double getRecargoVip() {
        return recargoVip; }
    public int getMaxEntradasPorCompra() {
        return maxEntradasPorCompra; }
    public int getPuntosEntradaGeneral() {
        return puntosEntradaGeneral; }
    public int getPuntosEntradaVip() {
        return puntosEntradaVip; }
    public int getPuntosProducto() {
        return puntosProducto; }
    public int getPuntosCombo() {
        return puntosCombo; }
}
