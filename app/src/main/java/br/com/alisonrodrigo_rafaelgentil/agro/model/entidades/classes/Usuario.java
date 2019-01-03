package br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes;

import java.io.Serializable;

public class Usuario implements Serializable {


    private String UId;
    private String Email;
    private String senha;

    public Usuario() {}

    public String getUId() {
        return UId;
    }

    public String getEmail() {
        return Email;
    }

    public String getSenha() {
        return senha;
    }


    public void setUId(String UId) {
        this.UId = UId;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
