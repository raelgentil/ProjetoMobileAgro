package br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.interfaces;



public interface Subject {
    public void addObserver(Observer observer);
    public void removeObserver(Observer observer);
    public void notifyObservers();
}
