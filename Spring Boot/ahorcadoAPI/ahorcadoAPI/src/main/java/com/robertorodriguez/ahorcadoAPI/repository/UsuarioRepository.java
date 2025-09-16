package com.robertorodriguez.ahorcadoAPI.repository;

import com.robertorodriguez.ahorcadoAPI.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository  extends JpaRepository<Usuarios,Integer> {

}
