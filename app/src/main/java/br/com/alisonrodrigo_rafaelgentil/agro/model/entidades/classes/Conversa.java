package br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.interfaces.IObserver;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.interfaces.ISubject;

public class Conversa implements ISubject {
    Contato contato;
    Contato meuContato;
    private String UId;


    private List<IObserver> observers;
    List<Mensagem> mensagens;

    public Conversa() {
        this.observers = new ArrayList<>();
    }

    public Map<String, Object> getMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("mensagens", getMensagens());
        return map;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }

    public void setMeuContato(Contato meuContato) {
        this.meuContato = meuContato;
    }

    public void setUId(String UId) {
        this.UId = UId;
    }

    public Contato getContato() {
        return contato;
    }

    public Contato getMeuContato() {
        return meuContato;
    }

    public String getUId() {
        return UId;
    }

    public void addMensagem(Mensagem mensagem){
        this.mensagens.add(mensagem);
        notifyObservers();
    }

    public List<Map<String, Object>> getMensagens() {
        List<Map<String, Object>> mensagens_maps = new ArrayList<>();
        for (Mensagem mensagem:mensagens) {
            mensagens_maps.add(mensagem.getMap());
        }
        return mensagens_maps;
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
    //criar uma forma que na hora de colocar o id da conversa na pessoa acrecentar um id numerico nele pra identificar mais facil na conversa
//    private class Cliente{
//        Contato contato;
//        int quantMsn;
//        List<Mensagem> mensagens;
//
//        public Cliente() {
//            this.mensagens = new ArrayList<>();
//        }
//    }
}
