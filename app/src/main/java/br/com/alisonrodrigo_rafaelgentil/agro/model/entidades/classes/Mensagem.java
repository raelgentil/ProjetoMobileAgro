package br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes;

import android.icu.util.LocaleData;

import java.util.ArrayList;
import java.util.List;

import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.interfaces.Observer;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.interfaces.Subject;

public class Mensagem implements Subject {
    private List<Observer> observers;
    private String UId;
    private String texto;
    private boolean visualizada;
    private boolean isRecebida;
    private LocaleData data;

    public Mensagem() {
        this.observers = new ArrayList<>();
    }

    public String getUId() {
        return UId;
    }

    public String getTexto() {
        return texto;
    }

    public boolean isVisualizada() {
        return visualizada;
    }

    public boolean isRecebida() {
        return isRecebida;
    }

    public LocaleData getData() {
        return data;
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
