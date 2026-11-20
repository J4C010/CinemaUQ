package co.edu.uniquindio.cinemauq.repository;

import co.edu.uniquindio.cinemauq.model.Usuario;

import java.util.Optional;

public class RepositorioUsuarios extends RepositorioEnMemoria<Usuario> {

    public RepositorioUsuarios() {
        super(Usuario::getId);
    }

    public Optional<Usuario> buscarPorCorreo(String correo) {
        return listar().stream()
                .filter(u -> u.getCorreo().equalsIgnoreCase(correo))
                .findFirst();
    }
}
