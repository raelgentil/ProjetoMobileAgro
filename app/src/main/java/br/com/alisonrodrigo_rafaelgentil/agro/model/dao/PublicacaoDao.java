package br.com.alisonrodrigo_rafaelgentil.agro.model.dao;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Publicacao;

public class PublicacaoDao {


    public PublicacaoDao() {
    }

    public void salvarPublicao(Publicacao publicacao) {

        final Map<String, Object> map = new HashMap<>();

        FirebaseFirestore.getInstance().collection("publicacao").add(publicacao.getMap())
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.i("TesteUser", documentReference.getId());
//                        Toast.makeText(getContext(),"Usuário salvo com sucesso!", Toast.LENGTH_LONG).show();
                        map.put("mensagem", "Usuário salvo com sucesso!");


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.i("Teste", e.getMessage());

//                Toast.makeText(getContext(),"Erro ao salvarOuAtualizar Usuario!", Toast.LENGTH_LONG).show();
                map.put("mensagem", "Erro ao salvarOuAtualizar Usuario!");

            }
        });


    }


}