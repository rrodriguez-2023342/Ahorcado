package com.robertorodriguez.ahorcadoAPI.repository;

import com.robertorodriguez.ahorcadoAPI.model.Palabras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PalabraRepository extends JpaRepository<Palabras, Integer> {
}
