package br.com.alisonrodrigo_rafaelgentil.agro.model.dao.classes;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import br.com.alisonrodrigo_rafaelgentil.agro.model.dao.interfaces.IContatoDAO;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Contato;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.ContatoFragment;

public class ContatoDAO implements IContatoDAO {
    FirebaseFirestore firestore;

    public ContatoDAO(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    public void buscar(String busca, final ContatoFragment fragment) {
//        final Pessoa pessoa = null; //Modificar pra contato
        final String logTag = "Teste_contatoDAO.buscar()";
        firestore.collection("pessoa").whereEqualTo("login", busca)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e!=null){
                            Log.i(logTag, e.getMessage(),e);
                            return;
                        }
                        Log.i(logTag, "Deu Certo");
                        List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot doc: docs) {
                            Contato contato = (Contato) doc.toObject(Contato.class);
                            Log.i(logTag, contato.getNome());
                            fragment.addItemList(contato);
                        }

                    }
                });
    }
}
