package com.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") Integer anioNacimiento,
        @JsonAlias("death_year") Integer anioFallecimiento
) {
    @Override
    public String toString() {
        return nombre + (anioNacimiento != null ? " (" + anioNacimiento : "") +
                (anioFallecimiento != null ? " - " + anioFallecimiento + ")" : "");
    }
}
