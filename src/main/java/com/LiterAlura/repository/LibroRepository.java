package com.LiterAlura.repository;

import com.LiterAlura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    long countByIdiomaIgnoreCase(String idioma);
    List<Libro> findTop10ByOrderByDescargasDesc();

    Optional<Libro> findByTituloIgnoreCase(String titulo);

    List<Libro> findByIdiomaIgnoreCase(String idioma);
}
