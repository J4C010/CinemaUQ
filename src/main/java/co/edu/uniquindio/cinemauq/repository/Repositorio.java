package co.edu.uniquindio.cinemauq.repository;

import java.util.List;
import java.util.Optional;

public interface Repositorio<T> {
    void guardar(T entidad);
    Optional<T> buscarPorId(String id);
    List<T> listar();
    boolean eliminar(String id);
}
