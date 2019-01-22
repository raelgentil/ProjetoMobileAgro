package br.com.alisonrodrigo_rafaelgentil.agro.model.dao.classes;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Conversa;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Mensagem;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Pessoa;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Publicacao;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.PerfilFragment;

public class ConversaDAO {
    FirebaseAuth auth;
    FirebaseFirestore firestore;


    public ConversaDAO(FirebaseAuth auth, FirebaseFirestore firestore) {
        this.auth = auth;
        this.firestore = firestore;
    }

    public void salvar(final Conversa conversa, final PerfilFragment fragment) {
        final Map<String, Object> map = new HashMap<>();
        firestore.collection("conversas").document().collection(conversa.getMeuContato().getUId()).add(conversa.getMap())
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.i("Teste_conversaDAO.salvar()", documentReference.getId());
//                        pessoa.setUId(documentReference.getId());
//                        Toast.makeText(getContext(),"Usuário salvo com sucesso!", Toast.LENGTH_LONG).show();
                        map.put("mensagem", "Usuário salvo com sucesso!");
                        fragment.responde(map);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Teste", e.getMessage());
//                Toast.makeText(getContext(),"Erro ao salvar Usuario!", Toast.LENGTH_LONG).show();
                map.put("mensagem", "Erro ao salvar Usuario!");
                fragment.responde(map);
            }
        });
    }


//    private void receberDados(final Conversa conversa, String UId){
//        firestore.collection("conversas").document(UId).getParent().addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(QuerySnapshot DocumentSnapshots, FirebaseFirestoreException e) {
//                for(DocumentChange dc: DocumentSnapshots.getDocumentChanges()){
//                    if(dc.getType() == DocumentChange.Type.ADDED){
//                        Mensagem mensagem = dc.getDocument().toObject(Conversa.class);
//                        conversa.addMensagem();
//
//                    }
//                    adapterPublicacoes.notifyDataSetChanged();
//                }
//            }
//        });
//
//    }
}
