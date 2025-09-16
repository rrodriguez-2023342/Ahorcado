package com.robertorodriguez.ahorcadoAPI.controller;

import com.robertorodriguez.ahorcadoAPI.model.Palabras;
import com.robertorodriguez.ahorcadoAPI.service.PalabraService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/palabras")
public class PalabraController {
    private final PalabraService palabraService;

    public PalabraController(PalabraService palabraService) {
        this.palabraService = palabraService;
    }

    @GetMapping
    public List<Palabras> listarPalabras(){
        return palabraService.getAllPalabras();
    }

    @GetMapping("/{id}")
    public Palabras getPalabraPorId(@PathVariable Integer id){
        return palabraService.getPalabraById(id);
    }

    @PostMapping
    public String createPalabra(@RequestBody Palabras palabras){
        try {
            Palabras result = palabraService.savePalabras(palabras);
            if("ERROR_PALABRA_REPETIDO".equals(result.getPalabra())){
                return "La palabra ya existe en la base de datos!";
            }
            if("ERROR_PISTA_REPETIDO".equals(result.getPista())){
                return "La pista ya existe en la base de datos!";
            }
            return "Palabra agregada correctamente!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @PutMapping("/{id}")
    public String updatePalabra(@PathVariable Integer id, @RequestBody Palabras palabras){
        try {
            Palabras result = palabraService.updatePalabras(id, palabras);
            if (result == null) {
                return "Palabra no encontrada";
            }
            if("ERROR_PALABRA_REPETIDO".equals(result.getPalabra())){
                return "La palabra ya existe en la base de datos!";
            }
            if("ERROR_PISTA_REPETIDO".equals(result.getPista())){
                return "La pista ya existe en la base de datos!";
            }
            return "Palabra actualizado correctamente!";
        }  catch (Exception e) {
            return e.getMessage();
        }
    }

    @DeleteMapping("/{id}")
    public String deletePalabra(@PathVariable Integer id){
        palabraService.deletePalabras(id);
        return "Palabra eliminado correctamente!";
    }
}
