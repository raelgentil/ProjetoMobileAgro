package br.com.alisonrodrigo_rafaelgentil.agro.model.entidades;

import java.io.Serializable;

public class Usuario implements Serializable {


    private String UId;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String nome;
    private String Cpf;
    private String Email;
    private String telefone;
    private String login;
    private String senha;
    private String dataNascimento;
    private String fotoFileURL;

    public Usuario() {}

    public Usuario(String UId, String nome, String cpf, String email, String telefone, String login, String senha, String dataNascimento, String fotoFileURL) {
        this.UId = UId;
        this.nome = nome;
        Cpf = cpf;
        Email = email;
        this.telefone = telefone;
        this.login = login;
        this.senha = senha;
        this.dataNascimento = dataNascimento;
        this.fotoFileURL = fotoFileURL;
    }

    public Usuario(int id, String nome, String cpf, String email, String telefone, String login, String senha, String dataNascimento) {
        this.id = id;
        this.nome = nome;
        Cpf = cpf;
        Email = email;
        this.telefone = telefone;
        this.login = login;
        this.senha = senha;
        this.dataNascimento = dataNascimento;
    }

    public void setFotoFileURL(String fotoFileURL) {
        this.fotoFileURL = fotoFileURL;
    }

    public String getFotoFileURL() {
        return fotoFileURL;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getUId() { return UId; }

    public void setUId(String UId) { this.UId = UId; }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return Cpf;
    }

    public void setCpf(String cpf) {
        Cpf = cpf;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }



    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
