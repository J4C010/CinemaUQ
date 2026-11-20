package co.edu.uniquindio.cinemauq.model;

public class ItemCompra {
    private final ItemConfiteria item;
    private final int cantidad;
    private final double precioUnitario;

    public ItemCompra(ItemConfiteria item, int cantidad) {
        this.item = item;
        this.cantidad = cantidad;
        this.precioUnitario = item.getPrecio();
    }

    public double subtotal() {
        return precioUnitario * cantidad; }

    public ItemConfiteria getItem() {
        return item; }
    public int getCantidad() {
        return cantidad; }
    public double getPrecioUnitario() {
        return precioUnitario; }
}
