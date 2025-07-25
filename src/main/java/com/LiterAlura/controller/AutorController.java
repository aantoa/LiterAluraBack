package com.LiterAlura.controller;

import com.LiterAlura.dto.AutorDTO;
import com.LiterAlura.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("/api/autores")
public class AutorController {
    @Autowired
    private AutorService autorService;

    // 4. Listar todos los autores
    @GetMapping
    public ResponseEntity<List<AutorDTO>> listarTodos() {
        return ResponseEntity.ok(autorService.listarTodos());
    }

    // 5. Autores vivos en determinado año
    @GetMapping("/vivos")
    public ResponseEntity<List<AutorDTO>> vivosEnAno(@RequestParam int ano) {
        return ResponseEntity.ok(autorService.listarVivosEnAno(ano));
    }

    // 9. Buscar autor por nombre
    @GetMapping("/search")
    public ResponseEntity<List<AutorDTO>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(autorService.buscarPorNombre(nombre));
    }

    // 10. Nacidos desde año
    @GetMapping("/nacidos-desde")
    public ResponseEntity<List<AutorDTO>> nacidosDesde(@RequestParam int anio) {
        return ResponseEntity.ok(autorService.buscarNacidosDesde(anio));
    }

    // 11. Fallecidos hasta año
    @GetMapping("/fallecidos-hasta")
    public ResponseEntity<List<AutorDTO>> fallecidosHasta(@RequestParam int anio) {
        return ResponseEntity.ok(autorService.buscarFallecidosHasta(anio));
    }
}
