package br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes;

import android.icu.util.LocaleData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.interfaces.IObserver;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.interfaces.ISubject;

public class Mensagem implements ISubject {
    private List<IObserver> observers;
//    private String UId;
    private String texto;
    private boolean visualizada;
    private boolean recebida;
    private LocaleData data;

    public Map<String, Object> getMap(){
        Map<String, Object> map = new HashMap<>();
//        map.put("UId", getUId());
        map.put("texto", getTexto());
        map.put("visualizada", isVisualizada());
        map.put("recebida", isRecebida());

        return map;
    }
    public Mensagem() {
        this.observers = new ArrayList<>();
    }

//    public void setUId(String UId) {
//        this.UId = UId;
//    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setVisualizada(boolean visualizada) {
        this.visualizada = visualizada;
    }

    public void setRecebida(boolean recebida) {
        this.recebida = recebida;
    }

    public void setData(LocaleData data) {
        this.data = data;
    }

//    public String getUId() {
//        return UId;
//    }

    public String getTexto() {
        return texto;
    }

    public boolean isVisualizada() {
        return visualizada;
    }

    public boolean isRecebida() {
        return recebida;
    }

    public LocaleData getData() {
        return data;
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
