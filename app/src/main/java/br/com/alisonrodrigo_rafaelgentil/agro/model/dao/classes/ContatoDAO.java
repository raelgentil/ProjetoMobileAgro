package br.com.alisonrodrigo_rafaelgentil.agro.model.dao.classes;

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
        firestore.collection("pessoa").whereEqualTo("login", busca)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e!=null){
                            Log.i("TesteContato", e.getMessage(),e);
                            return;
                        }
                        Log.i("TesteContato", "Deu Certo");
                        List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot doc: docs) {
                            Contato contato = (Contato) doc.toObject(Contato.class);
                            Log.i("TesteContato", contato.getNome());
                            fragment.addItemList(contato);
                        }

                    }
                });
    }
}
