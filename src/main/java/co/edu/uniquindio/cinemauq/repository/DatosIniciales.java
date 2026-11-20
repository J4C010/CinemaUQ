package co.edu.uniquindio.cinemauq.repository;

import co.edu.uniquindio.cinemauq.model.*;
import co.edu.uniquindio.cinemauq.model.enums.CategoriaProducto;
import co.edu.uniquindio.cinemauq.model.enums.TipoMovimiento;
import co.edu.uniquindio.cinemauq.model.excepciones.ValorInvalidoException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public final class DatosIniciales {
    private DatosIniciales() {}

    public static void cargar(RepositorioUsuarios usuarios,
                              Repositorio<Pelicula> peliculas,
                              Repositorio<Sala> salas,
                              Repositorio<Funcion> funciones,
                              Repositorio<Producto> productos,
                              Repositorio<Combo> combos) throws ValorInvalidoException {

        // ---------- Usuarios ----------
        usuarios.guardar(new Administrador("Administrador", "admin@cinemauq.com", "admin123"));

        Cliente ana = new Cliente("Ana Gómez", "ana@cinemauq.com", "1234");
        ana.getTarjeta().acreditar(TipoMovimiento.RECARGA, 200000, "Recarga inicial", null);
        ana.getCuentaPuntos().acumular(300, LocalDate.now().minusMonths(2));
        ana.getCuentaPuntos().acumular(500, LocalDate.now().minusDays(10));
        usuarios.guardar(ana);

        Cliente luis = new Cliente("Luis Pérez", "luis@cinemauq.com", "1234");
        luis.getTarjeta().acreditar(TipoMovimiento.RECARGA, 50000, "Recarga inicial", null);
        luis.getCuentaPuntos().acumular(400, LocalDate.now().minusMonths(14)); // ya vencido
        usuarios.guardar(luis);

        // ---------- Películas ----------
        Pelicula p1 = new Pelicula("Horizonte Rojo", "Ciencia ficción", 128, "+12",
                "Una tripulación viaja a un planeta que no aparece en ningún mapa.");
        Pelicula p2 = new Pelicula("La Última Función", "Drama", 105, "+7",
                "Un proyeccionista lucha por salvar el cine de su pueblo.");
        Pelicula p3 = new Pelicula("Risas al Volante", "Comedia", 98, "Todo público",
                "Dos primos cruzan el país en un taxi prestado.");
        peliculas.guardar(p1);
        peliculas.guardar(p2);
        peliculas.guardar(p3);

        // ---------- Salas ----------
        Sala sala1 = new Sala("Sala 1", 6, 8);
        Sala sala2 = new Sala("Sala 2 Premium", 5, 6);
        salas.guardar(sala1);
        salas.guardar(sala2);

        // ---------- Funciones (horarios relativos a "ahora") ----------
        LocalDateTime ahora = LocalDateTime.now();
        funciones.guardar(new Funcion(p1, sala1, ahora.plusHours(1), 16000));   // sin cancelación
        funciones.guardar(new Funcion(p2, sala2, ahora.plusHours(10), 18000));  // reembolso 80 %
        funciones.guardar(new Funcion(p3, sala1, ahora.plusDays(3), 14000));    // reembolso 100 %
        funciones.guardar(new Funcion(p1, sala2, ahora.plusDays(4), 18000));

        // ---------- Productos y combos ----------
        Producto crispetas = new Producto("Crispetas grandes", 12000, CategoriaProducto.COMIDA);
        Producto perro = new Producto("Perro caliente", 10000, CategoriaProducto.COMIDA);
        Producto gaseosa = new Producto("Gaseosa", 7000, CategoriaProducto.BEBIDA);
        Producto agua = new Producto("Agua", 4000, CategoriaProducto.BEBIDA);
        Producto chocolatina = new Producto("Chocolatina", 5000, CategoriaProducto.DULCE);
        for (Producto p : List.of(crispetas, perro, gaseosa, agua, chocolatina)) {
            productos.guardar(p);
        }

        combos.guardar(new Combo("Combo Clásico", 16000, List.of(crispetas, gaseosa)));
        combos.guardar(new Combo("Combo Pareja", 26000, List.of(crispetas, perro, gaseosa, gaseosa)));
    }
}
