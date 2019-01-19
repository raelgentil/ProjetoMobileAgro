package br.com.alisonrodrigo_rafaelgentil.agro.model.business.classes;

import com.google.firebase.firestore.FirebaseFirestore;

import br.com.alisonrodrigo_rafaelgentil.agro.model.business.interfaces.IContatoBusiness;
import br.com.alisonrodrigo_rafaelgentil.agro.model.dao.classes.ContatoDAO;
import br.com.alisonrodrigo_rafaelgentil.agro.model.dao.interfaces.IContatoDAO;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.ContatoFragment;

public class ContatoBusiness implements IContatoBusiness {
    IContatoDAO contatoDAO;

    public ContatoBusiness(FirebaseFirestore firestore) {
        this.contatoDAO = new ContatoDAO(firestore);
    }

    @Override
    public void buscar(String busca, ContatoFragment fragment) {
        contatoDAO.buscar(busca,fragment);
    }
}
