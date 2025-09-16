package com.robertorodriguez.ahorcadoAPI.service;

import com.robertorodriguez.ahorcadoAPI.model.Usuarios;

import java.util.List;

public interface UsuarioService {
    List<Usuarios> getAllUsuarios();
    Usuarios getUsuarioById(Integer id);
    Usuarios saveUsuario(Usuarios usuario);
    Usuarios updateUsuario(Integer id, Usuarios usuario);
    void  deleteUsuario(Integer id);
}
