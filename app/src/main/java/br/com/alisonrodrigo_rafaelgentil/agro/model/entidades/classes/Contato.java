package br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.interfaces.IObserver;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.interfaces.ISubject;


public class Contato implements Serializable, ISubject {
    private String fotoFileURL;
    private String nome;
    private String UId;
    private List<Mensagem> mensagens;
    private List<String> UIdchats;
    private List<IObserver> observers;
    public Contato() {
        this.mensagens = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.UIdchats = new ArrayList<>();
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

    public List<Mensagem> getMensagens() {
        return mensagens;
    }

    public void addMensagem(Mensagem mensagem){
        this.mensagens.add(mensagem);
        notifyObservers();
    }

    public List<String> getUIdchats() {
        return UIdchats;
    }

    public void setUIdchats(List<String> UIdchats) {
        this.UIdchats = UIdchats;
    }

    public Map<String, Object> getMapMensagens(){
        Map<String, Object> map = new HashMap<>();
        map.put("" + getUId(), getMensagensMap());
        return map;
    }

    public Map<String, Object> getMapContato(){
        Map<String, Object> map = new HashMap<>();
        map.put("nome", getNome());
        map.put("fotoFileURL", getFotoFileURL());
        map.put("UId", getUId());


        return map;
    }
    private List<Map<String, Object>> getMensagensMap() {
        List<Map<String, Object>> mensagens_maps = new ArrayList<>();
        for (Mensagem mensagem:mensagens) {
            mensagens_maps.add(mensagem.getMap());
        }
        return mensagens_maps;
    }

    public List<IObserver> getObservers() {
        return observers;
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
}
