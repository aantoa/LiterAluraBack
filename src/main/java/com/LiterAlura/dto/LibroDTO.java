package com.LiterAlura.dto;

public record LibroDTO(
        Long id,
        String titulo,
        String autor,
        String idioma,
        Integer descargas,
        String imagenUrl
) {
    @Override
    public String toString() {
        return "Título: " + titulo +
                "\nAutor: " + (autor != null ? autor : "Desconocido") +
                "\nIdioma: " + (idioma != null ? idioma : "Desconocido") +
                "\nDescargas: " + (descargas != null ? descargas : "N/A") +
                "\n----------------------------------";
    }
}
