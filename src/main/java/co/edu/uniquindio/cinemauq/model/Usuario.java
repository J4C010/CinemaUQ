package co.edu.uniquindio.cinemauq.model;

import co.edu.uniquindio.cinemauq.model.enums.Rol;
import co.edu.uniquindio.cinemauq.util.Ids;

public abstract class Usuario {
    private final String id;
    private String nombre;
    private String correo;
    private String contrasena;

    protected Usuario(String prefijoId, String nombre, String correo, String contrasena) {
        this.id = Ids.nuevo(prefijoId);
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
    }

    public abstract Rol getRol();

    public boolean validarContrasena(String intento) {
        return contrasena.equals(intento);
    }

    public String getId() {
        return id; }
    public String getNombre() {
        return nombre; }
    public String getCorreo() {
        return correo; }
    public void setNombre(String nombre) {
        this.nombre = nombre; }
    public void setCorreo(String correo) {
        this.correo = correo; }
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena; }
}
