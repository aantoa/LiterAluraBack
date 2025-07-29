package com.LiterAlura.service;

import com.LiterAlura.dto.LibroDTO;
import com.LiterAlura.model.*;
import com.LiterAlura.repository.LibroRepository;
import com.LiterAlura.utils.ConsumoAPI;
import com.LiterAlura.utils.ConvierteDatos;
import com.LiterAlura.utils.ListConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorService autorService;

    @Autowired
    private ConsumoAPI consumoApi;

    @Autowired
    private ConvierteDatos conversor;

    private static final String GUTENDEX_URL_BASE = "https://gutendex.com/books/";

    public List<LibroDTO> buscarPorTitulo(String busqueda) {
        List<DatosLibro> datosLibros = buscarLibrosEnApi(busqueda);
        if (datosLibros == null || datosLibros.isEmpty()) {
            return Collections.emptyList();
        }
        // Toma solo el primer libro
        DatosLibro primero = datosLibros.get(0);
        Autor autor = autorService.buscarOCrearAutor(
                primero.autores() != null && !primero.autores().isEmpty()
                        ? primero.autores().get(0)
                        : null
        );
        Libro libro = guardarLibroSiNoExiste(primero, autor);
        // Devuelve solo ese en la lista de resultado
        return List.of(toDTO(libro));
    }

    private List<DatosLibro> buscarLibrosEnApi(String busqueda) {
        String url = GUTENDEX_URL_BASE + "?search=" + URLEncoder.encode(busqueda, StandardCharsets.UTF_8);
        String json = consumoApi.obtenerDatos(url);
        DatosCatalogoLibros catalogo = conversor.obtenerDatos(json, DatosCatalogoLibros.class);
        return catalogo != null && catalogo.libros() != null ? catalogo.libros() : Collections.emptyList();
    }

    public Libro guardarLibroSiNoExiste(DatosLibro datosLibro, Autor autor) {
        Optional<Libro> existente = libroRepository.findByTituloIgnoreCase(datosLibro.titulo());
        if (existente.isPresent()) {
            return existente.get();
        }
        Libro libro = new Libro();
        libro.setId(datosLibro.id());
        libro.setTitulo(datosLibro.titulo());
        libro.setAutor(autor);
        libro.setIdioma(datosLibro.idiomas() != null && !datosLibro.idiomas().isEmpty()
                ? datosLibro.idiomas().get(0) : null);
        libro.setDescargas(datosLibro.descargas());
        libro.setTemas(datosLibro.temas() != null ? String.join(",", datosLibro.temas()) : null);
        if (datosLibro.formats() != null) {
            libro.setImagenUrl(datosLibro.formats().get("image/jpeg"));
        }
        return libroRepository.save(libro);
    }

    private LibroDTO toDTO(Libro libro) {
        return new LibroDTO(
                libro.getId(),
                libro.getTitulo(),
                libro.getAutor() != null ? libro.getAutor().getNombre() : null,
                libro.getIdioma(),
                libro.getDescargas(),
                libro.getImagenUrl()
        );
    }

    public List<LibroDTO> listarTodos() {
        return ListConverter.convertirLista(libroRepository.findAll(), this::toDTO);
    }

    public List<LibroDTO> listarPorIdioma(String idioma) {
        return ListConverter.convertirLista(libroRepository.findByIdiomaIgnoreCase(idioma), this::toDTO);
    }

    public List<LibroDTO> top10MasDescragados(){
        return ListConverter.convertirLista(libroRepository.findTop10ByOrderByDescargasDesc(), this::toDTO);
    }

    public Map<String, Object> estadisticasDescargas() {
        List<Libro> libros = libroRepository.findAll();
        DoubleSummaryStatistics stats = libros.stream()
                .mapToDouble(l -> l.getDescargas() != null ? l.getDescargas() : 0)
                .summaryStatistics();

        Map<String, Object> result = new HashMap<>();
        result.put("Total", stats.getSum());
        result.put("Promedio", stats.getAverage());
        result.put("Maximo", stats.getMax());
        result.put("Minimo", stats.getMin());
        return result;
    }

    public Map<String, Long> cantidadLibrosPorIdioma() {
        List<Libro> libros = libroRepository.findAll();
        return libros.stream()
                .filter(l -> l.getIdioma() != null)
                .collect(Collectors.groupingBy(
                        l -> l.getIdioma().toLowerCase(),
                        Collectors.counting()
                ));
    }
}
