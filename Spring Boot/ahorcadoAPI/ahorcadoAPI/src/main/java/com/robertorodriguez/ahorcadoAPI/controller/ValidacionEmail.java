package com.robertorodriguez.ahorcadoAPI.controller;

public class ValidacionEmail {
    private static  String gmail = "@gmail.com";
    private static  String edu = "@edu.gt";
    private static  String emailmin;

    public static void validarEmail (String email) {

        if (email == null || email.trim().isEmpty()){
            throw new InvalidEmail("El correo no puede estar vacio");
        }
        emailmin = email.toLowerCase().trim();

        if (!emailmin.endsWith(gmail) && !emailmin.endsWith(edu)) {
            throw new InvalidEmail("El correo debe terminar en @gmail.com o @edu.gt");
        }

        if (!emailmin.matches("^[A-Za-z0-9+_.-]+@(gmail\\.com|edu\\.gt)$")) {
            throw new InvalidEmail("El formato del correo no es valido");
        }
    }

    public static boolean emailValido(String email) {
        try {
            validarEmail(email);
            return true;
        } catch (InvalidEmail e) {
            return false;
        }
    }
}
