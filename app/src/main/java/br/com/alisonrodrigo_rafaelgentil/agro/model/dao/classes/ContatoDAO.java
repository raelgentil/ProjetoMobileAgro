package br.com.alisonrodrigo_rafaelgentil.agro.model.dao.classes;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import br.com.alisonrodrigo_rafaelgentil.agro.model.dao.interfaces.IContatoDAO;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Contato;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Pessoa;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.ContatoFragment;

public class ContatoDAO implements IContatoDAO {
    FirebaseFirestore firestore;

    public ContatoDAO(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

//    public void buscar(String busca, final ContatoFragment fragment) {
////        final Pessoa pessoa = null; //Modificar pra contato
//        final String logTag = "Teste_contatoDAO.buscar()";
//        firestore.collection("pessoa").document().collection("dados").whereEqualTo("login", busca)
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @SuppressLint("LongLogTag")
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//                        if (e!=null){
//                            Log.i(logTag, e.getMessage(),e);
//                            return;
//                        }
//                        Log.i(logTag, "Deu Certo");
//                        List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
////                        Log.i(logTag, "" + (docs.get(0)));
//                        for (DocumentSnapshot doc: docs) {
//                            Log.i(logTag, "Procurando contato");
//                            Contato contato = (Contato) doc.toObject(Contato.class);
//                            Log.i(logTag, contato.getNome());
//                            fragment.addItemList(contato);
//                        }
//
//                    }
//                });
//    }

    public void buscar(String busca, final ContatoFragment fragment) {
        final String logTag = "Teste_contatoDAO.buscar()";
        Query querry1 = firestore.collection("pessoa").whereEqualTo("login", busca);
//        Query querry1 = firestore.collection("pessoa").document(pessoa.getUsuario().getUId()).collection("dados").whereEqualTo("UId", pessoa.getUsuario().getUId());
        querry1.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.i(logTag, "Deu certo");
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.i("TestePegarDados", document.getId()+ " =>>>>>>>>> " + document.get("nome").toString()+ " => " + document.getData());
                                Log.i(logTag, "Procurando contato");
                                Contato contato = (Contato) document.toObject(Contato.class);
//                                Contato contato = (Contato) doc.toObject(Contato.class);
                                Log.i(logTag, contato.getNome());
                                fragment.addItemList(contato);
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("TesteFalhaPegarDados", e.getMessage());
//                                                        Log.w(TAG, "Error getting documents.", task.getException());
            }
        });
    }
}
