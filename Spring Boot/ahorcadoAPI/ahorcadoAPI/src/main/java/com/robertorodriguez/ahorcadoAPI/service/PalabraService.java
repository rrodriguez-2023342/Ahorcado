package com.robertorodriguez.ahorcadoAPI.service;

import com.robertorodriguez.ahorcadoAPI.model.Palabras;

import java.util.List;

public interface PalabraService {
    List<Palabras> getAllPalabras();
    Palabras getPalabraById(Integer id);
    Palabras savePalabras(Palabras palabras);
    Palabras updatePalabras(Integer id, Palabras palabras);
    void deletePalabras(Integer id);
}
