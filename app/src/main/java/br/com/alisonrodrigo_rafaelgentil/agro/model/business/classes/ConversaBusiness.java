package br.com.alisonrodrigo_rafaelgentil.agro.model.business.classes;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;

import br.com.alisonrodrigo_rafaelgentil.agro.model.business.interfaces.IConversaBusiness;
import br.com.alisonrodrigo_rafaelgentil.agro.model.dao.classes.ConversaDAO;
import br.com.alisonrodrigo_rafaelgentil.agro.model.dao.interfaces.IConversaDAO;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Chat;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Conversa;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Mensagem;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.ChatFragment;

public class ConversaBusiness implements IConversaBusiness {
    IConversaDAO conversaDAO;

    public ConversaBusiness(FirebaseAuth auth, FirebaseFirestore firestore, DatabaseReference databaseReference) {
        this.conversaDAO = new ConversaDAO(auth, firestore, databaseReference);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void salvarOuAtualizar(Conversa conversa, Mensagem mensagem) {
        if (conversa.getUId()==null || conversa.getUId()==""){
            Log.i("Teste_conversaDAO.salvarOuAtualizar()/salvar","");
            conversaDAO.salvar(conversa, mensagem);

        }else{
            Log.i("Teste_conversaDAO.salvarOuAtualizar()/atualizar", conversa.getUId());
            conversaDAO.atualizar(conversa, mensagem);
        }
    }

    @Override
    public void receberDados(Conversa conversa) {
        conversaDAO.receberDados(conversa);
    }

    public void pegarDadosConversa(final Conversa conversa){
        conversaDAO.pegarDadosConversa(conversa);
    }

    @Override
    public void pegarConversa(Conversa conversa) {
        conversaDAO.pegarDadosConversa(conversa);
    }

    @Override
    public void pegarConversas(ChatFragment chatFragment) {
        this.conversaDAO.pegarConversas(chatFragment);
    }

    @Override
    public void listarConversas(Chat chat) {
        conversaDAO.listarConversas(chat);
    }


}
