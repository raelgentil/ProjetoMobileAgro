package br.com.alisonrodrigo_rafaelgentil.agro.model.dao.classes;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Pessoa;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.PerfilFragment;

public class ConversaDAO {
    FirebaseAuth auth;

    public ConversaDAO(FirebaseAuth auth) {
        this.auth = auth;
    }

//    public void salvar(final Conver pessoa, final PerfilFragment fragment) {
//        final Map<String, Object> map = new HashMap<>();
//        FirebaseFirestore.getInstance().collection("pessoa").add(pessoa.getMap())
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.i("TesteUser", documentReference.getId());
//                        pessoa.setUId(documentReference.getId());
////                        Toast.makeText(getContext(),"Usuário salvo com sucesso!", Toast.LENGTH_LONG).show();
//                        map.put("mensagem", "Usuário salvo com sucesso!");
//                        fragment.responde(map);
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.i("Teste", e.getMessage());
////                Toast.makeText(getContext(),"Erro ao salvar Usuario!", Toast.LENGTH_LONG).show();
//                map.put("mensagem", "Erro ao salvar Usuario!");
//                fragment.responde(map);
//            }
//        });
//    }

}
