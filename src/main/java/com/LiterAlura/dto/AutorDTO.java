package com.LiterAlura.dto;

public record AutorDTO(
        String nombre,
        Integer anioNacimiento,
        Integer anioFallecimiento){
    @Override
    public String toString() {
        return nombre + (anioNacimiento != null ? " (" + anioNacimiento : "") +
                (anioFallecimiento != null ? " - " + anioFallecimiento + ")" : "");
    }
}
