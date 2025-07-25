package com.LiterAlura.service;

import com.LiterAlura.dto.AutorDTO;
import com.LiterAlura.model.Autor;
import com.LiterAlura.model.DatosAutor;
import com.LiterAlura.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutorService {
    @Autowired
    private AutorRepository autorRepository;

    public Autor buscarOCrearAutor(DatosAutor datosAutor) {
        return autorRepository.findByNombre(datosAutor.nombre())
                .orElseGet(() -> {
                    Autor nuevo = new Autor();
                    nuevo.setNombre(datosAutor.nombre());
                    nuevo.setAnioNacimiento(datosAutor.anioNacimiento());
                    nuevo.setAnioFallecimiento(datosAutor.anioFallecimiento());
                    return autorRepository.save(nuevo);
                });
    }

    private AutorDTO toDTO(Autor autor) {
        return new AutorDTO(
                autor.getNombre(),
                autor.getAnioNacimiento(),
                autor.getAnioFallecimiento()
        );
    }

    public List<AutorDTO> listarTodos() {
        return autorRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /*public List<AutorDTO> listarVivosEnAno(int ano) {
        return autorRepository.findAll().stream()
                .filter(autor ->
                        autor.getAnioNacimiento() != null && autor.getAnioNacimiento() <= ano &&
                                (autor.getAnioFallecimiento() == null || autor.getAnioFallecimiento() > ano)
                )
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    */
    // Usando derived queries
    public List<AutorDTO> listarVivosEnAno(int ano) {
        List<Autor> vivos = autorRepository
                .findByAnioNacimientoLessThanEqualAndAnioFallecimientoGreaterThanOrAnioFallecimientoIsNull(ano, ano);
        return vivos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<AutorDTO> buscarPorNombre(String nombre) {
        return autorRepository.findByNombreContainingIgnoreCase(nombre).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<AutorDTO> buscarNacidosDesde(int anio) {
        return autorRepository.findByAnioNacimientoGreaterThanEqual(anio).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<AutorDTO> buscarFallecidosHasta(int anio) {
        return autorRepository.findByAnioFallecimientoLessThanEqual(anio).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

}
