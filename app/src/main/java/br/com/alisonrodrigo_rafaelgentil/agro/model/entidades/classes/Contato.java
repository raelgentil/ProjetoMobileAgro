package br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes;

import java.io.Serializable;

public class Contato implements Serializable {
    private String fotoFileURL;
    private String nome;
    private String UId;

    public Contato() {
    }

    public void setFotoFileURL(String fotoFileURL) {
        this.fotoFileURL = fotoFileURL;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setUId(String UId) {
        this.UId = UId;
    }

    public String getUId() {
        return UId;
    }
    public String getFotoFileURL() {
        return fotoFileURL;
    }
    public String getNome() {
        return nome;
    }
}
