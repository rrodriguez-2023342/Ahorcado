package com.robertorodriguez.ahorcadoAPI.service;

import com.robertorodriguez.ahorcadoAPI.model.Palabras;
import com.robertorodriguez.ahorcadoAPI.repository.PalabraRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PalabraServiceImplements implements PalabraService{
    private final PalabraRepository palabraRepository;

    public PalabraServiceImplements(PalabraRepository palabraRepository) {
        this.palabraRepository = palabraRepository;
    }

    @Override
    public List<Palabras> getAllPalabras() {
        return palabraRepository.findAll();
    }

    @Override
    public Palabras getPalabraById(Integer id) {
        return palabraRepository.findById(id).orElse(null);
    }

    @Override
    public Palabras savePalabras(Palabras palabras) {
        if (palabras.getPalabra() == null || palabras.getPalabra().trim().isEmpty()) {
            palabras.setPalabra("ERROR_VACIO");
            return palabras;
        }
        if (palabras.getPista() == null || palabras.getPista().trim().isEmpty()) {
            palabras.setPista("ERROR_VACIO");
            return palabras;
        }

        List<Palabras> lista = palabraRepository.findAll();
        for (Palabras p : lista) {
            if (p.getPalabra().equalsIgnoreCase(palabras.getPalabra())) {
                palabras.setPalabra("ERROR_PALABRA_REPETIDO");
                return palabras;
            }
            if(p.getPista().equalsIgnoreCase(palabras.getPista())) {
                palabras.setPista("ERROR_PISTA_REPETIDO");
                return palabras;
            }
        }
        return palabraRepository.save(palabras);
    }

    @Override
    public Palabras updatePalabras(Integer id, Palabras palabras) {
        if (palabras.getPalabra() == null || palabras.getPalabra().trim().isEmpty()) {
            palabras.setPalabra("ERROR_VACIO");
            return palabras;
        }
        if (palabras.getPista() == null || palabras.getPista().trim().isEmpty()) {
            palabras.setPista("ERROR_VACIO");
            return palabras;
        }

        Palabras existingPalabra = palabraRepository.findById(id).orElse(null);
        if (existingPalabra != null) {
            List<Palabras> lista = palabraRepository.findAll();
            for (Palabras p : lista) {
                if (!p.getCodigoPalabra().equals(id)) {
                    if (p.getPalabra().equalsIgnoreCase(palabras.getPalabra())) {
                        palabras.setPalabra("ERROR_PALABRA_REPETIDO");
                        return palabras;
                    }
                    if (p.getPista().equalsIgnoreCase(palabras.getPista())) {
                        palabras.setPista("ERROR_PISTA_REPETIDO");
                        return palabras;
                    }
                }
            }
            existingPalabra.setPalabra(palabras.getPalabra());
            existingPalabra.setPista(palabras.getPista());
            return palabraRepository.save(existingPalabra);
        }
        return null;
    }

    @Override
    public void deletePalabras(Integer id) {
        palabraRepository.deleteById(id);
    }
}
