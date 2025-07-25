package com.LiterAlura.repository;

import com.LiterAlura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombreIgnoreCase(String nombre);
    List<Autor> findByAnioNacimientoLessThanEqualAndAnioFallecimientoGreaterThanOrAnioFallecimientoIsNull(
            Integer anioVivo, Integer anioVivo2
    );

    List<Autor> findByNombreContainingIgnoreCase(String nombre);
    List<Autor> findByAnioNacimientoGreaterThanEqual(Integer anioNacimiento);
    List<Autor> findByAnioFallecimientoLessThanEqual(Integer anioFallecimiento);

}
