package br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes;

import java.io.Serializable;

public class Contato implements Serializable {
    private String fotoFileURL;
    private String nome;

    public Contato() {
    }

    public void setFotoFileURL(String fotoFileURL) {
        this.fotoFileURL = fotoFileURL;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }



    public String getFotoFileURL() {
        return fotoFileURL;
    }
    public String getNome() {
        return nome;
    }
}
