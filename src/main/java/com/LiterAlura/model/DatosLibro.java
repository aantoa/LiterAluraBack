package com.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonAlias("id") Long id,
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DatosAutor> autores,
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("download_count") int descargas
) {
    @Override
    public String toString() {
        String autor = (autores != null && !autores.isEmpty())
                ? String.join(", ", autores.stream().map(DatosAutor::nombre).toList())
                : "Desconocido";
        String idioma = (idiomas != null && !idiomas.isEmpty())
                ? idiomas.get(0)
                : "Desconocido";
        return """
            Título: %s
            Autor: %s
            Idioma: %s
            Descargas: %d
            """.formatted(titulo, autores, idioma.toUpperCase(), descargas);
    }
}
