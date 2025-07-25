package com.LiterAlura.repository;

import com.LiterAlura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    long countByIdiomaIgnoreCase(String idioma);
    List<Libro> findTop10ByOrderByDescargasDesc();
}
