package co.edu.uniquindio.cinemauq.service;

import co.edu.uniquindio.cinemauq.model.Cliente;
import co.edu.uniquindio.cinemauq.model.Usuario;
import co.edu.uniquindio.cinemauq.model.excepciones.CinemaUQException;
import co.edu.uniquindio.cinemauq.model.excepciones.CorreoDuplicadoException;
import co.edu.uniquindio.cinemauq.model.excepciones.CredencialesInvalidasException;
import co.edu.uniquindio.cinemauq.model.excepciones.ValorInvalidoException;
import co.edu.uniquindio.cinemauq.repository.RepositorioUsuarios;

public class AuthService {
    private final RepositorioUsuarios usuarios;

    public AuthService(RepositorioUsuarios usuarios) {
        this.usuarios = usuarios;
    }

    public Cliente registrar(String nombre, String correo, String contrasena) throws CinemaUQException {
        if (estaVacio(nombre) || estaVacio(correo) || estaVacio(contrasena)) {
            throw new ValorInvalidoException("Nombre, correo y contraseña son obligatorios.");
        }
        if (!correo.contains("@")) {
            throw new ValorInvalidoException("El correo no tiene un formato válido.");
        }
        if (usuarios.buscarPorCorreo(correo).isPresent()) {
            throw new CorreoDuplicadoException("Ya existe una cuenta con el correo " + correo + ".");
        }
        Cliente cliente = new Cliente(nombre.trim(), correo.trim(), contrasena);
        usuarios.guardar(cliente);
        return cliente;
    }

    public Usuario iniciarSesion(String correo, String contrasena) throws CredencialesInvalidasException {
        Usuario usuario = usuarios.buscarPorCorreo(correo == null ? "" : correo.trim())
                .orElseThrow(() -> new CredencialesInvalidasException("Correo o contraseña incorrectos."));
        if (!usuario.validarContrasena(contrasena)) {
            throw new CredencialesInvalidasException("Correo o contraseña incorrectos.");
        }
        return usuario;
    }

    private boolean estaVacio(String texto) {
        return texto == null || texto.isBlank();
    }
}
