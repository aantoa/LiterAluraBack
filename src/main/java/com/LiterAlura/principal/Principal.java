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
                    
                    ------------- 📈 Estadísticas 📈 -------------
                    
                    6 - 📈 Ver estadísticas de libros por idioma
                    
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
                case 6:
                    libroService.mostrarEstadisticasIdiomas();
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
            System.out.println("\u001B[33m**********************************************\u001B[0m");
            System.out.println("📚 Libros registrados: ");
            System.out.println("\u001B[33m**********************************************\u001B[0m");
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
            System.out.println("\u001B[33m**********************************************\u001B[0m");
            System.out.printf("📚 Libros registrados en: %s%n", idioma.toUpperCase());
            System.out.println("\u001B[33m**********************************************\u001B[0m");
            librosFiltrados.forEach(System.out::println);
        }
    }

    private void listarAutores() {
        List<AutorDTO> autores = autorService.listarTodos();
        if (autores.isEmpty()) {
            System.out.println("No hay autores guardados.");
        } else {
            System.out.println("\u001B[33m**********************************************\u001B[0m");
            System.out.println("👩‍🎓 Lista de autores: ");
            System.out.println("\u001B[33m**********************************************\u001B[0m");
            autores.forEach(System.out::println);
        }
    }

    private void listarAutoresVivosEnAno() {
        int ano = -1;
        while (true) {
            System.out.print("Ingrese el año a consultar: ");
            String input = teclado.nextLine();
            try {
                ano = Integer.parseInt(input);
                if (ano < 0 || ano > 2100) {
                    System.out.println("Por favor, ingrese un año válido entre 0 y 2100.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("El año debe ser un número entero. Intente de nuevo.");
            }
        }

        List<AutorDTO> vivos = autorService.listarVivosEnAno(ano);
        if (vivos.isEmpty()) {
            System.out.println("No hay autores vivos en ese año.");
        } else {
            System.out.println("\u001B[33m**********************************************\u001B[0m");
            System.out.println("👩‍🎓 Lista de autores: ");
            System.out.println("\u001B[33m**********************************************\u001B[0m");
            vivos.forEach(System.out::println);
        }
    }


}
