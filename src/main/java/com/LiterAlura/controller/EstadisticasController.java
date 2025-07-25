package com.LiterAlura.controller;

import com.LiterAlura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/estadisticas")
public class EstadisticasController {
    @Autowired
    private LibroService libroService;

    // 6. Estadísticas de libros por idioma
    @GetMapping("/libros-por-idioma")
    public ResponseEntity<Map<String, Long>> librosPorIdioma() {
        return ResponseEntity.ok(libroService.cantidadLibrosPorIdioma());
    }

    // 7. Estadísticas de descargas
    @GetMapping("/descargas")
    public ResponseEntity<Map<String, Object>> estadisticasDescargas() {
        return ResponseEntity.ok(libroService.estadisticasDescargas());
    }
}
