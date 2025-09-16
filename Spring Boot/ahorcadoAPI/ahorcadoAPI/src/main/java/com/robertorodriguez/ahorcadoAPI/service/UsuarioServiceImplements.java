package com.robertorodriguez.ahorcadoAPI.service;

import com.robertorodriguez.ahorcadoAPI.controller.ValidacionEmail;
import com.robertorodriguez.ahorcadoAPI.model.Palabras;
import com.robertorodriguez.ahorcadoAPI.model.Usuarios;
import com.robertorodriguez.ahorcadoAPI.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImplements implements UsuarioService{
    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImplements(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<Usuarios> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuarios getUsuarioById(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public Usuarios saveUsuario(Usuarios usuarios) {
        ValidacionEmail.validarEmail(usuarios.getCorreoUsuario());
        List<Usuarios> lista = usuarioRepository.findAll();
        for (Usuarios u :  lista) {
            if (u.getCorreoUsuario().equalsIgnoreCase(usuarios.getCorreoUsuario())) {
                usuarios.setCorreoUsuario("ERROR_CORREO_REPETIDO");
                return usuarios;
            }
            if(u.getContraseña().equalsIgnoreCase(usuarios.getContraseña())) {
                usuarios.setContraseña("ERROR_CONTRA_REPETIDO");
                return usuarios;
            }
        }
        return usuarioRepository.save(usuarios);
    }

    @Override
    public Usuarios updateUsuario(Integer id, Usuarios usuario) {
        ValidacionEmail.validarEmail(usuario.getCorreoUsuario());
        Usuarios existingUsuario = usuarioRepository.findById(id).orElse(null);
        if (existingUsuario != null) {
            List<Usuarios> lista = usuarioRepository.findAll();
            for (Usuarios u : lista) {
                if (!u.getCodigoUsuario().equals(id)) {
                    if (u.getCorreoUsuario().equalsIgnoreCase(usuario.getCorreoUsuario())) {
                        usuario.setCorreoUsuario("ERROR_CORREO_REPETIDO");
                        return usuario;
                    }
                }
            }
            existingUsuario.setCorreoUsuario(usuario.getCorreoUsuario());
            existingUsuario.setContraseña(usuario.getContraseña());
            return usuarioRepository.save(existingUsuario);
        }
        return null;
    }

    @Override
    public void deleteUsuario(Integer id) {
        usuarioRepository.deleteById(id);
    }
}
