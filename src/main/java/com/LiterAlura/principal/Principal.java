package com.LiterAlura.principal;

import com.LiterAlura.dto.AutorDTO;
import com.LiterAlura.dto.LibroDTO;
import com.LiterAlura.service.AutorService;
import com.LiterAlura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Principal {

    @Autowired
    private LibroService libroService;
    @Autowired
    private AutorService autorService;
    private Scanner teclado = new Scanner(System.in);

    public void mostrarMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    ══════════════════════════════════════════════
                             📚  Menú de LiterAlura  📚
                    ══════════════════════════════════════════════
                    1 - 🔎 Buscar libro por titulo
                    2 - 📖 Listar todos los libros registrados
                    3 - 🌐 Listar libros por idioma
                    4 - 👩‍🎓 Listar autores
                    5 - 🏆 Listar autores vivos en determinado año
                    
                    0 - ❌ Salir
                    ══════════════════════════════════════════════
                    """;
            System.out.println(menu);
            System.out.print("Elige una opción: ");
            try {
                opcion = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    buscarLibros();
                    break;
                case 2:
                    listarTodosLosLibros();
                    break;
                case 3:
                    listarLibrosPorIdioma();
                    break;
                case 4:
                    listarAutores();
                    break;
                case 5:
                    listarAutoresVivosEnAno();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación... 👋");
                    System.exit(0);
                    break;
                default:
                    System.out.println("⚠\uFE0F Opción inválida. Intenta de nuevo.\n");
            }
        }

    }

    private void buscarLibros() {
        System.out.print("Escribe el título del libro que deseas buscar: ");
        String busqueda = teclado.nextLine();
        libroService.buscarYGuardarLibro(busqueda);
    }


    private void listarTodosLosLibros() {
        List<LibroDTO> libros = libroService.listarTodos();
        if (libros.isEmpty()) {
            System.out.println("No hay libros en el catálogo.");
        } else {
            libros.forEach(System.out::println);
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.print("Ingrese el idioma a filtrar (ej: en, es): ");
        String idioma = teclado.nextLine().toLowerCase();
        List<LibroDTO> librosFiltrados = libroService.listarPorIdioma(idioma);
        if (librosFiltrados.isEmpty()) {
            System.out.println("No hay libros en ese idioma.");
        } else {
            librosFiltrados.forEach(System.out::println);
        }
    }

    private void listarAutores() {
        List<AutorDTO> autores = autorService.listarTodos();
        if (autores.isEmpty()) {
            System.out.println("No hay autores guardados.");
        } else {
            autores.forEach(System.out::println);
        }
    }

    private void listarAutoresVivosEnAno() {
        System.out.print("Ingrese el año a consultar: ");
        int ano = Integer.parseInt(teclado.nextLine());

        List<AutorDTO> vivos = autorService.listarVivosEnAno(ano);
        if (vivos.isEmpty()) {
            System.out.println("No hay autores vivos en ese año.");
        } else {
            vivos.forEach(System.out::println);
        }
    }


}
