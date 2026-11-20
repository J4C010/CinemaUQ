package co.edu.uniquindio.cinemauq;

import co.edu.uniquindio.cinemauq.model.*;
import co.edu.uniquindio.cinemauq.model.enums.CategoriaProducto;
import co.edu.uniquindio.cinemauq.model.enums.EstadoCompra;
import co.edu.uniquindio.cinemauq.model.enums.TipoMovimiento;
import co.edu.uniquindio.cinemauq.model.excepciones.*;
import co.edu.uniquindio.cinemauq.model.excepciones.CinemaUQException;
import co.edu.uniquindio.cinemauq.model.excepciones.FuncionNoDisponibleException;
import co.edu.uniquindio.cinemauq.model.excepciones.PuntosInsuficientesException;
import co.edu.uniquindio.cinemauq.patrones.Creacionales.CompraBuilder;
import co.edu.uniquindio.cinemauq.patrones.Creacionales.ConfiguracionCine;
import co.edu.uniquindio.cinemauq.repository.DatosIniciales;
import co.edu.uniquindio.cinemauq.repository.Repositorio;
import co.edu.uniquindio.cinemauq.repository.RepositorioEnMemoria;
import co.edu.uniquindio.cinemauq.repository.RepositorioUsuarios;
import co.edu.uniquindio.cinemauq.service.AuthService;
import co.edu.uniquindio.cinemauq.service.CancelacionService;
import co.edu.uniquindio.cinemauq.service.CompraService;
import co.edu.uniquindio.cinemauq.service.TarjetaService;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public class Launcher {
    public static void main(String[] args) throws Exception {
        RepositorioUsuarios usuarios = new RepositorioUsuarios();
        Repositorio<Funcion> funciones = new RepositorioEnMemoria<>(Funcion::getId);
        DatosIniciales.cargar(usuarios,
                new RepositorioEnMemoria<>(Pelicula::getId),
                new RepositorioEnMemoria<>(Sala::getId),
                funciones,
                new RepositorioEnMemoria<>(Producto::getId),
                new RepositorioEnMemoria<>(Combo::getId));
        Repositorio<Compra> repoCompras = new RepositorioEnMemoria<>(Compra::getId);
        CompraService compras = new CompraService(repoCompras);
        CancelacionService cancelaciones = new CancelacionService(repoCompras);

        Cliente ana = (Cliente) usuarios.buscarPorCorreo("ana@cinemauq.com").orElseThrow();
        Usuario admin = usuarios.buscarPorCorreo("admin@cinemauq.com").orElseThrow();
        Funcion f0 = funciones.listar().get(0), f1 = funciones.listar().get(1), f2 = funciones.listar().get(2);
        LocalDateTime ahora = LocalDateTime.now();

        // A: función en 3 días -> 100 %
        Compra a = compras.comprar(new CompraBuilder(ana, ahora).funcion(f2)
                .agregarAsiento(f2.getSala().getAsientos().get(0)));
        System.out.println(ana.getTarjeta().getSaldo());                      // 186000.0
        System.out.println(cancelaciones.cancelarCompra(ana, a, ahora));      // 14000.0
        System.out.println(ana.getTarjeta().getSaldo());                      // 200000.0
        System.out.println(f2.asientosVendidos());                            // 0
        System.out.println(ana.getCuentaPuntos().disponibles(LocalDate.now())); // 800
        try {
            cancelaciones.cancelarCompra(ana, a, ahora);
        } catch (CinemaUQException e) { System.out.println("Bloqueado: " + e.getMessage()); }

        // B: función en 10 horas -> 80 %
        Compra b = compras.comprar(new CompraBuilder(ana, ahora).funcion(f1)
                .agregarAsiento(f1.getSala().getAsientos().get(0)));
        System.out.println(cancelaciones.cancelarCompra(ana, b, ahora));      // 14400.0
        System.out.println(ana.getTarjeta().getSaldo());                      // 196400.0

        // C: función en 1 hora -> no se permite; luego la cancela el cine -> 100 %
        Compra c = compras.comprar(new CompraBuilder(ana, ahora).funcion(f0)
                .agregarAsiento(f0.getSala().getAsientos().get(0)));
        try {
            cancelaciones.cancelarCompra(ana, c, ahora);
        } catch (CinemaUQException e) { System.out.println("Bloqueado: " + e.getMessage()); }
        System.out.println(cancelaciones.cancelarFuncion(admin, f0, ahora));  // 1
        System.out.println(ana.getTarjeta().getSaldo());                      // 196400.0
        System.out.println(c.getEstado());                                    // CANCELADA_POR_CINE
    }
}