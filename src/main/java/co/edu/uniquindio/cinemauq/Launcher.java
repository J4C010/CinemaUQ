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
import co.edu.uniquindio.cinemauq.service.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public class Launcher {
    public static void main(String[] args) throws Exception {
            RepositorioUsuarios usuarios = new RepositorioUsuarios();
            Repositorio<Funcion> funciones = new RepositorioEnMemoria<>(Funcion::getId);
            Repositorio<Producto> productos = new RepositorioEnMemoria<>(Producto::getId);
            Repositorio<Combo> combos = new RepositorioEnMemoria<>(Combo::getId);
            DatosIniciales.cargar(usuarios, new RepositorioEnMemoria<>(Pelicula::getId),
                    new RepositorioEnMemoria<>(Sala::getId), funciones, productos, combos);
            PuntosService puntos = new PuntosService(new RepositorioEnMemoria<>(Redencion::getId));

            Cliente ana = (Cliente) usuarios.buscarPorCorreo("ana@cinemauq.com").orElseThrow();
            Cliente luis = (Cliente) usuarios.buscarPorCorreo("luis@cinemauq.com").orElseThrow();
            Funcion f = funciones.listar().get(2);
            List<Asiento> asientos = f.getSala().getAsientos();
            LocalDateTime ahora = LocalDateTime.now();

            puntos.redimirEntrada(ana, f, asientos.get(0), ahora);      // general: 150
            puntos.redimirEntrada(ana, f, asientos.get(40), ahora);     // VIP: 200
            puntos.redimirCombo(ana, combos.listar().get(0), 1, ahora); // 120
            puntos.redimirProducto(ana, productos.listar().get(0), 2, ahora); // 2 x 60
            System.out.println(puntos.puntosDisponibles(ana, ahora));   // 210
            System.out.println(f.asientosVendidos());                   // 2
            System.out.println(puntos.historial(ana).size());           // 4

            try { puntos.redimirEntrada(ana, f, asientos.get(0), ahora); }
            catch (CinemaUQException e) { System.out.println("Bloqueado: " + e.getMessage()); }
            try { puntos.redimirCombo(ana, combos.listar().get(0), 5, ahora); }
            catch (CinemaUQException e) { System.out.println("Bloqueado: " + e.getMessage()); }
            try { puntos.redimirProducto(luis, productos.listar().get(0), 1, ahora); }
            catch (CinemaUQException e) { System.out.println("Bloqueado: " + e.getMessage()); }
            System.out.println(puntos.puntosDisponibles(ana, ahora));   // 210 (no se perdió nada)
    }
}