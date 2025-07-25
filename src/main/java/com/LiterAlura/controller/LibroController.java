package com.LiterAlura.controller;

import com.LiterAlura.dto.LibroDTO;
import com.LiterAlura.service.LibroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class LibroController {
    @Autowired
    private LibroService libroService;

    // 1. Buscar libro por título
    @GetMapping("/search")
    public ResponseEntity<List<LibroDTO>> buscarPorTitulo(@RequestParam String titulo) {
        return ResponseEntity.ok(libroService.buscarPorTitulo(titulo));
    }

    // 2 y 3. Listar todos o por idioma
    @GetMapping
    public ResponseEntity<List<LibroDTO>> listarTodos(@RequestParam(required = false) String idioma) {
        if (idioma != null) {
            return ResponseEntity.ok(libroService.listarPorIdioma(idioma));
        } else {
            return ResponseEntity.ok(libroService.listarTodos());
        }
    }

    // 8. Top 10 libros más descargados
    @GetMapping("/top-descargas")
    public ResponseEntity<List<LibroDTO>> topDescargas() {
        return ResponseEntity.ok(libroService.top10MasDescragados());
    }

}
