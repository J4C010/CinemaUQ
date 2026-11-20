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
        CompraService servicio = new CompraService(new RepositorioEnMemoria<>(Compra::getId));

        Cliente ana = (Cliente) usuarios.buscarPorCorreo("ana@cinemauq.com").orElseThrow();
        Cliente luis = (Cliente) usuarios.buscarPorCorreo("luis@cinemauq.com").orElseThrow();
        Funcion f = funciones.listar().get(2);
        List<Asiento> asientos = f.getSala().getAsientos();

        // Compra exitosa de Ana: A1 y A2 a 14000 cada una
        Compra compra = servicio.comprar(new CompraBuilder(ana, LocalDateTime.now())
                .funcion(f).agregarAsiento(asientos.get(0)).agregarAsiento(asientos.get(1)));
        System.out.println(compra.getTotal());                                      // 28000.0
        System.out.println(ana.getTarjeta().getSaldo());                            // 172000.0
        System.out.println(f.asientosVendidos());                                   // 2
        System.out.println(ana.getCuentaPuntos().disponibles(LocalDate.now()));     // 828

        // Luis intenta el mismo asiento: no se cobra nada
        try {
            servicio.comprar(new CompraBuilder(luis, LocalDateTime.now())
                    .funcion(f).agregarAsiento(asientos.get(0)));
        } catch (CinemaUQException e) { System.out.println("Bloqueado: " + e.getMessage()); }
        System.out.println(luis.getTarjeta().getSaldo());                           // 50000.0

        // Luis sin saldo: 4 entradas de 18000 = 72000 en otra función
        Funcion f2 = funciones.listar().get(3);
        try {
            CompraBuilder b = new CompraBuilder(luis, LocalDateTime.now()).funcion(f2);
            f2.getSala().getAsientos().subList(0, 4).forEach(b::agregarAsiento);
            servicio.comprar(b);
        } catch (CinemaUQException e) { System.out.println("Bloqueado: " + e.getMessage()); }
        System.out.println(f2.asientosVendidos());                                  // 0
    }
}