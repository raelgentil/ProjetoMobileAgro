package br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.interfaces.IObserver;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.interfaces.ISubject;

public class Conversa implements ISubject  {
    Contato contato;
    Contato meuContato;
    private String UId;
    private List<IObserver> observers;
    public Conversa() {
        this.observers = new ArrayList<>();
    }


    public Map<String, Object> getMap(){
        List<Map<String, Object>> maps = new ArrayList<>();
        maps.add(contato.getMapContato());
        maps.add(meuContato.getMapContato());
        Map<String, Object> map = new HashMap<>();
        map.put("contatos", maps);
//        List<Map<String, Object>> maps1 = new ArrayList<>();
//        maps1.add(contato.getMapMensagens());
//        maps1.add(meuContato.getMapMensagens());
//        map.put("conversa", maps1);
        return map;
    }
    public Map<String, Object> getMapUId(){

        Map<String, Object> map = new HashMap<>();
        map.put("UId", getUId());
//        List<Map<String, Object>> maps1 = new ArrayList<>();
//        maps1.add(contato.getMapMensagens());
//        maps1.add(meuContato.getMapMensagens());
//        map.put("conversa", maps1);
        return map;
    }

    public void setUId(String UId) {
        this.UId = UId;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }

    public void setMeuContato(Contato meuContato) {
        this.meuContato = meuContato;
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



//    public List<Map<String, Object>> getMensagensMap() {
//        List<Map<String, Object>> mensagens_maps = new ArrayList<>();
//        for (Mensagem mensagem:mensagens) {
//            mensagens_maps.add(mensagem.getMap());
//        }
//        return mensagens_maps;
//    }

    @Override
    public void addObserver(IObserver observer) {
        observers.add( observer );
        contato.addObserver(observer);
        meuContato.addObserver(observer);
    }

    @Override
    public void removeObserver(IObserver observer) {
        int index = observers.indexOf( observer );
        if( index > -1 ){
            observers.remove( observer );
            contato.removeObserver( observer );
            meuContato.removeObserver( observer );
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
