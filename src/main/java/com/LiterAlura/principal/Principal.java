package com.LiterAlura.principal;

import com.LiterAlura.model.DatosAutor;
import com.LiterAlura.model.DatosCatalogoLibros;
import com.LiterAlura.model.DatosLibro;
import com.LiterAlura.service.ConsumoAPI;
import com.LiterAlura.service.ConvierteDatos;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);
    private List<DatosLibro> datosLibros = new ArrayList<>();
    private List<DatosAutor> autoresGuardados = new ArrayList<>();

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

    private List<DatosLibro> getDatosLibro(){
        System.out.print("Escribe el título del libro que deseas buscar: ");
        String busqueda = teclado.nextLine();

        String url = URL_BASE+ "?search=" + busqueda.replace(" ", "+");
        String json = consumoApi.obtenerDatos(url);

        DatosCatalogoLibros catalogo = conversor.obtenerDatos(json, DatosCatalogoLibros.class);
        return catalogo.libros();
    }

    private void buscarLibros() {
        List<DatosLibro> libros = getDatosLibro();
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros.");
        } else {
            DatosLibro libro = libros.get(0);
            boolean yaExiste = datosLibros.stream()
                    .anyMatch(l -> l.id() == libro.id());
            if (yaExiste) {
                System.out.println("⚠️  Ese libro ya está registrado en tu catálogo.");
            } else {
                datosLibros.add(libro);
                System.out.println("Libro guardado: ");
                System.out.println(libro);

                DatosAutor autor = libro.autores() != null && !libro.autores().isEmpty()
                        ? libro.autores().get(0)
                        : null;

                if (autor != null && autoresGuardados.stream().
                        noneMatch(a -> a.nombre().equalsIgnoreCase(autor.nombre()))) {
                    autoresGuardados.add(autor);
                }
            }
        }
    }


    private void listarTodosLosLibros() {
        if (datosLibros.isEmpty()) {
            System.out.println("No hay libros en el catálogo.");
        } else {
            datosLibros.stream()
                    .sorted(Comparator.comparing(DatosLibro::titulo))
                    .forEach(System.out::println);
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.print("Ingrese el idioma a filtrar (ej: en, es): ");
        String idioma = teclado.nextLine().toLowerCase();

        List<DatosLibro> librosFiltrados = datosLibros.stream()
                .filter(libro -> libro.idiomas() != null && !libro.idiomas().isEmpty())
                .filter(libro -> libro.idiomas().get(0).equalsIgnoreCase(idioma))
                .toList();

        if (librosFiltrados.isEmpty()) {
            System.out.println("No hay libros en ese idioma.");
        } else {
            librosFiltrados.forEach(System.out::println);
        }
    }

    private void listarAutores() {
        if (autoresGuardados.isEmpty()) {
            System.out.println("No hay autores guardados.");
        } else {
            autoresGuardados.stream()
                    .sorted(Comparator.comparing(DatosAutor::nombre, String.CASE_INSENSITIVE_ORDER))
                    .forEach(System.out::println);
        }
    }

    private void listarAutoresVivosEnAno() {
        System.out.print("Ingrese el año a consultar: ");
        int ano = Integer.parseInt(teclado.nextLine());

        List<DatosAutor> vivos = autoresGuardados.stream()
                .filter(autor ->
                        autor.anioNacimiento() != null && autor.anioNacimiento() <= ano &&
                                (autor.anioFallecimiento() == null || autor.anioFallecimiento() > ano)
                )
                .sorted(Comparator.comparing(DatosAutor::nombre, String.CASE_INSENSITIVE_ORDER))
                .toList();

        if (vivos.isEmpty()) {
            System.out.println("No hay autores vivos en ese año.");
        } else {
            vivos.forEach(System.out::println);
        }
    }


}
