package br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.interfaces.IObserver;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.interfaces.ISubject;


public class Pessoa extends Contato implements ISubject {
    private List<IObserver> observers;

//    private String nome;
    private String Cpf;
    private String telefone;
    private String login;
    private String dataNascimento;
//    private String fotoFileURL;
    private Uri mSelectUri;
    private Usuario usuario;


    public Pessoa() {this.observers = new ArrayList<>(); this.usuario = new Usuario(); }



//    public String getNome() {
//        return nome;
//    }

    public String getCpf() {
        return Cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getLogin() {
        return login;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

//    public String getFotoFileURL() {
//        return fotoFileURL;
//    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Uri getmSelectUri() {
        return mSelectUri;
    }



    public Map<String, Object> getMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("cpf", getCpf());
        map.put("nome", getNome());
        map.put("UId", getUsuario().getUId());
        map.put("telefone", getTelefone());
        map.put("login", getLogin());
        map.put("dataNascimento", getDataNascimento());
        map.put("fotoFileURL", getFotoFileURL());
        map.put("conversas", getUIdchats());
        return map;
    }



//    public void setNome(String nome) {
//        this.nome = nome;
//    }




    public void setCpf(String cpf) {
        Cpf = cpf;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

//    public void setFotoFileURL(String fotoFileURL) {
//        this.fotoFileURL = fotoFileURL;
//    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setmSelectUri(Uri mSelectUri) {
        this.mSelectUri = mSelectUri;
    }

    @Override
    public void addObserver(IObserver observer) {
        observers.add( observer );
    }



    @Override
    public void removeObserver(IObserver observer) {
        int index = observers.indexOf( observer );
        if( index > -1 ){
            observers.remove( observer );
        }
    }

    @Override
    public void notifyObservers() {
        for( IObserver o :observers ){
            o.update(this);
        }
    }

    public void addchats(String UId) {
        getUIdchats().add( UId );
    }

    public void removeChat(String UId) {
        //Lembrar de quando remover a conversa remover do banco
        int index = observers.indexOf( UId );
        if( index > -1 ){
            observers.remove( UId );
        }
    }
}
