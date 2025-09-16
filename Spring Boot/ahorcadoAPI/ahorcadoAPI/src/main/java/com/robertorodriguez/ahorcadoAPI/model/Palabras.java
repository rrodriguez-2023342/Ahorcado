package com.robertorodriguez.ahorcadoAPI.model;

import jakarta.persistence.*;

@Entity
@Table(name="Palabras")
public class Palabras {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigoPalabra;

    @Column(name = "palabra")
    private String palabra;

    @Column(name = "pista")
    private String pista;

    //Getter y Setter

    public Integer getCodigoPalabra() {
        return codigoPalabra;
    }

    public void setCodigoPalabra(Integer codigoPalabra) {
        this.codigoPalabra = codigoPalabra;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public String getPista() {
        return pista;
    }

    public void setPista(String pista) {
        this.pista = pista;
    }
}
