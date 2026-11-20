package co.edu.uniquindio.cinemauq.repository;

import java.util.*;
import java.util.function.Function;

public class RepositorioEnMemoria<T> implements Repositorio<T> {
    private final Map<String, T> datos = new LinkedHashMap<>();
    private final Function<T, String> extractorId;

    public RepositorioEnMemoria(Function<T, String> extractorId) {
        this.extractorId = extractorId;
    }

    @Override
    public void guardar(T entidad) {
        datos.put(extractorId.apply(entidad), entidad);
    }

    @Override
    public Optional<T> buscarPorId(String id) {
        return Optional.ofNullable(datos.get(id));
    }

    @Override
    public List<T> listar() {
        return new ArrayList<>(datos.values());
    }

    @Override
    public boolean eliminar(String id) {
        return datos.remove(id) != null;
    }
}
