package com.LiterAlura.service;

import com.LiterAlura.dto.AutorDTO;
import com.LiterAlura.model.Autor;
import com.LiterAlura.model.DatosAutor;
import com.LiterAlura.repository.AutorRepository;
import com.LiterAlura.utils.ListConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutorService {
    @Autowired
    private AutorRepository autorRepository;

    public Autor buscarOCrearAutor(DatosAutor datosAutor) {
        return autorRepository.findByNombreIgnoreCase(datosAutor.nombre())
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
        return ListConverter.convertirLista(autorRepository.findAll(), this::toDTO);
    }

    public List<AutorDTO> buscarPorNombre(String nombre) {
        return ListConverter.convertirLista(autorRepository.findByNombreContainingIgnoreCase(nombre), this::toDTO);
    }

    public List<AutorDTO> listarVivosEnAno(int anio) {
        return ListConverter.convertirLista(autorRepository
                .findByAnioNacimientoLessThanEqualAndAnioFallecimientoGreaterThanOrAnioFallecimientoIsNull(anio,anio), this::toDTO);
    }

    public List<AutorDTO> buscarNacidosDesde(int anio) {
        return ListConverter.convertirLista(autorRepository.findByAnioNacimientoGreaterThanEqual(anio),this::toDTO);
    }

    public List<AutorDTO> buscarFallecidosHasta(int anio) {
        return ListConverter.convertirLista(autorRepository.findByAnioFallecimientoLessThanEqual(anio), this::toDTO);
    }

}
