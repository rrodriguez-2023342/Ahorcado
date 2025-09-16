package com.robertorodriguez.ahorcadoAPI.controller;

import com.robertorodriguez.ahorcadoAPI.model.Palabras;
import com.robertorodriguez.ahorcadoAPI.model.Usuarios;
import com.robertorodriguez.ahorcadoAPI.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuarios> listarUsuarios(){
        return usuarioService.getAllUsuarios();
    }

    @GetMapping("/{id}")
    public Usuarios getUsuarioPorId(@PathVariable Integer id){
        return usuarioService.getUsuarioById(id);
    }

    @PostMapping
    public String createUsuario(@RequestBody Usuarios usuarios){
        try {
            Usuarios result = usuarioService.saveUsuario(usuarios);
            if("ERROR_CORREO_REPETIDO".equals(result.getCorreoUsuario())){
                return "El correo ya existe en la base de datos!";
            }
            if("ERROR_CONTRA_REPETIDO".equals(result.getContraseña())){
                return "La contraseña ya existe en la base de datos!";
            }
            return "Usuario agregado correctamente!";
        } catch (InvalidEmail invalid) {
            return invalid.getMessage();
        }
    }

    @PutMapping("/{id}")
    public String updateUsuario(@PathVariable Integer id, @RequestBody Usuarios usuarios){
        try {
            Usuarios result = usuarioService.updateUsuario(id, usuarios);
            if (result == null) {
                return "Usuario no encontrado";
            }
            if("ERROR_CORREO_REPETIDO".equals(result.getCorreoUsuario())){
                return "El correo ya existe en la base de datos!";
            }
            return "Usuario actualizado correctamente!";
        }  catch (InvalidEmail invalid) {
            return invalid.getMessage();
        }
    }

    @DeleteMapping("/{id}")
    public String deleteUsuario(@PathVariable Integer id){
        usuarioService.deleteUsuario(id);
        return "Usuario eliminado correctamente!";
    }
}
