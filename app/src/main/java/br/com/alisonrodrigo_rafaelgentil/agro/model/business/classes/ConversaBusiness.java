package br.com.alisonrodrigo_rafaelgentil.agro.model.business.classes;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import br.com.alisonrodrigo_rafaelgentil.agro.model.business.interfaces.IConversaBusiness;
import br.com.alisonrodrigo_rafaelgentil.agro.model.dao.classes.ConversaDAO;
import br.com.alisonrodrigo_rafaelgentil.agro.model.dao.interfaces.IConversaDAO;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Conversa;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Mensagem;

public class ConversaBusiness implements IConversaBusiness {
    IConversaDAO conversaDAO;

    public ConversaBusiness(FirebaseAuth auth, FirebaseFirestore firestore) {
        this.conversaDAO = new ConversaDAO(auth, firestore);
    }

    @Override
    public void salvarOuAtualizar(Conversa conversa, Mensagem mensagem) {
        if (conversa.getUId()!=null && conversa.getUId() != ""){
            conversaDAO.atualizar(conversa, mensagem);
        }else{
            conversaDAO.salvar(conversa, mensagem);
        }
    }

    @Override
    public void receberDados(Conversa conversa) {
        conversaDAO.receberDados(conversa);
    }


}
