package com.robertorodriguez.ahorcadoAPI.controller;

public class InvalidEmail extends RuntimeException {
    public InvalidEmail(String message) {
        super(message);
    }
}
