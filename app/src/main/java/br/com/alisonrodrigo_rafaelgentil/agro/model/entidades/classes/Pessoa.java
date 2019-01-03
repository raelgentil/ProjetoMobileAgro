package br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.interfaces.Observer;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.interfaces.Subject;


public class Pessoa implements Serializable, Subject {
    private List<Observer> observers;
    private String UId;
    private String nome;
    private String Cpf;
    private String email;
    private String telefone;
    private String login;
    private String senha;
    private String dataNascimento;
    private String fotoFileURL;

    public Pessoa() {this.observers = new ArrayList<>();}

    public String getUId() {
        return UId;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return Cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public String getFotoFileURL() {
        return fotoFileURL;
    }

    public Map<String, Object> getMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("cpf", getCpf());
        map.put("nome", getNome());
        map.put("email", getEmail());
        map.put("telefone", getTelefone());
        map.put("login", getLogin());
        map.put("senha", getSenha());
        map.put("dataNascimento", getDataNascimento());
        map.put("fotoFileURL", getFotoFileURL());
        return map;
    }

    public void setUId(String UId) {
        this.UId = UId;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        Cpf = cpf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setFotoFileURL(String fotoFileURL) {
        this.fotoFileURL = fotoFileURL;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add( observer );
    }

    @Override
    public void removeObserver(Observer observer) {
        int index = observers.indexOf( observer );
        if( index > -1 ){
            observers.remove( observer );
        }
    }

    @Override
    public void notifyObservers() {
        for( Observer o :observers ){
            o.update(this);
        }
    }
}
