package br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.interfaces;



public interface ISubject {
    public void addObserver(IObserver observer);
    public void removeObserver(IObserver observer);
    public void notifyObservers();


}
