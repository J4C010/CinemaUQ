package co.edu.uniquindio.cinemauq.model;

import co.edu.uniquindio.cinemauq.model.enums.Rol;

public class Administrador extends Usuario {

    public Administrador(String nombre, String correo, String contrasena) {
        super("ADM", nombre, correo, contrasena);
    }

    @Override
    public Rol getRol() {
        return Rol.ADMINISTRADOR;
    }
}
