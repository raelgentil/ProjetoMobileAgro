package br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes;

import java.util.ArrayList;
import java.util.List;

import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.interfaces.IObserver;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.interfaces.ISubject;

public class Chat implements ISubject {
    private List<Conversa> conversas;
    private List<IObserver> observers;
    private String UId_contato;
    private Contato meu_contato;

    public Chat() {
        this.conversas = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    public List<Conversa> getConversas() {
        return conversas;
    }

    public String getUId_contato() {
        return UId_contato;
    }

    public void setUId_contato(String UId_contato) {
        this.UId_contato = UId_contato;
    }

    public Contato getMeu_contato() {
        return meu_contato;
    }

    public void setMeu_contato(Contato meu_contato) {
        this.meu_contato = meu_contato;
    }

    public void addConversa(Conversa conversa){
        this.conversas.add(conversa);
        notifyObservers();
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
