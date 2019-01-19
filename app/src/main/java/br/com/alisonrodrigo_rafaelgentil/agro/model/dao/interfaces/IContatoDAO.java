package br.com.alisonrodrigo_rafaelgentil.agro.model.dao.interfaces;

import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.ContatoFragment;

public interface IContatoDAO {
    public void buscar(String busca, final ContatoFragment fragment);
}
