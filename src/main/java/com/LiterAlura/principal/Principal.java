package com.LiterAlura.principal;

import com.LiterAlura.model.DatosCatalogoLibros;
import com.LiterAlura.model.DatosLibro;
import com.LiterAlura.service.ConsumoAPI;
import com.LiterAlura.service.ConvierteDatos;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosLibro> datosLibros = new ArrayList<>();

    public void mostrarMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libros
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = Integer.parseInt(teclado.nextLine());

            switch (opcion) {
                case 1:
                    buscarLibros();
                    break;
                
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    private List<DatosLibro> getDatosLibro(){
        System.out.print("Escribe el título (o palabra clave) del libro que deseas buscar: ");
        String busqueda = teclado.nextLine();

        String url = "https://gutendex.com/books/?search=" + busqueda.replace(" ", "+");
        String json = consumoApi.obtenerDatos(url);

        DatosCatalogoLibros catalogo = conversor.obtenerDatos(json, DatosCatalogoLibros.class);
        return catalogo.libros();
    }

    private void buscarLibros() {
        List<DatosLibro> libros = getDatosLibro();
        datosLibros.addAll(libros);

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros.");
        } else {
            libros.forEach(System.out::println);
        }
    }

}
