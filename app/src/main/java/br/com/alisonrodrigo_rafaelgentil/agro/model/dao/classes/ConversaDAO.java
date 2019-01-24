package br.com.alisonrodrigo_rafaelgentil.agro.model.dao.classes;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.alisonrodrigo_rafaelgentil.agro.model.dao.interfaces.IConversaDAO;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Contato;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Conversa;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Mensagem;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Pessoa;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Publicacao;
import br.com.alisonrodrigo_rafaelgentil.agro.model.util.Utill;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.PerfilFragment;

public class ConversaDAO implements IConversaDAO {
    FirebaseAuth auth;
    FirebaseFirestore firestore;


    public ConversaDAO(FirebaseAuth auth, FirebaseFirestore firestore) {
        this.auth = auth;
        this.firestore = firestore;
    }

    public void salvar(final Conversa conversa, final Mensagem mensagem) {

        final Map<String, Object> map = new HashMap<>();
        map.put("teste", "teste");
        firestore.collection("conversas").add(map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @SuppressLint("LongLogTag")
                    @Override

                    public void onSuccess(DocumentReference documentReference) {
                        conversa.setUId(documentReference.getId());
//                        atualizarConversaPessoa(conversa);
                        Log.i("Teste_conversaDAO.salvar()", conversa.getUId());
                        salvaDados(conversa);
//                        salvarMensagem(conversa, mensagem);

                        atualizar(conversa, mensagem);
                        atualizarConversaPessoa(conversa);
                        conversa.notifyObservers();
//                        firestore.collection("pessoa").document(user.getUid()).collection("conversas").add(conversa);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Teste", e.getMessage());

//                Toast.makeText(getContext(),"Erro ao salvarOuAtualizar Usuario!", Toast.LENGTH_LONG).show();
//                map.put("mensagem", "Erro ao salvarOuAtualizar Usuario!");
//                fragment.responde(map);
            }
        });
    }

    @SuppressLint("LongLogTag")
    private void salvarMensagem(final Conversa conversa, Mensagem mensagem) {
        Log.i("Teste_conversaDAO.salvarMensagem()", conversa.getUId());
//        List<Mensagem> m = new ArrayList<>();
//        m.add(mensagem);
        firestore.collection("conversas")
                .document(conversa.getUId())
                .collection(conversa.getMeuContato().getUId()).add(mensagem.getMap())
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.i("Teste_conversaDAO.salvarMensagem()", conversa.getUId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Teste", e.getMessage());

                    }
                });

    }

    @SuppressLint("LongLogTag")
    private void salvaDados(final Conversa conversa) {
        Log.i("Teste_conversaDAO.salvaDados()", conversa.getUId());
        firestore.collection("conversas")
                .document(conversa.getUId())
                .collection("dados").document().set(conversa.getMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("Teste_conversaDAO.salvaDados()", conversa.getUId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Teste", e.getMessage());

                    }
                });

    }

    private void atualizarConversaPessoa(final Conversa conversa){
//        firestore.collection("pessoa").document(conversa.getContato().getUId()).collection("conversas").document(conversa.getUId());
//        firestore.collection("pessoa").document(conversa.getMeuContato().getUId()).collection("conversas").document(conversa.getUId());
                Query querry1 = firestore.collection("pessoa").document(conversa.getContato().getUId()).getParent();
//        Query querry1 = firestore.collection("pessoa").document(pessoa.getUsuario().getUId()).collection("dados").whereEqualTo("UId", pessoa.getUsuario().getUId());
        querry1.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.i("TestePegarDados", document.getId()+ " =>>>>>>>>> " + document.get("nome").toString()+ " => " + document.getData());
                                Pessoa pessoa = new Pessoa();
                                pessoa =(Pessoa) document.toObject(Pessoa.class);
                                pessoa.addchats(conversa.getUId());
                                firestore.collection("pessoa").document(conversa.getContato().getUId()).set(pessoa.getMap())
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                            }
                                        });
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


        Query querry = firestore.collection("pessoa").document(conversa.getMeuContato().getUId()).getParent();
//        Query querry1 = firestore.collection("pessoa").document(pessoa.getUsuario().getUId()).collection("dados").whereEqualTo("UId", pessoa.getUsuario().getUId());
        querry.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.i("TestePegarDados", document.getId()+ " =>>>>>>>>> " + document.get("nome").toString()+ " => " + document.getData());
                                Pessoa pessoa = new Pessoa();
                                pessoa =(Pessoa) document.toObject(Pessoa.class);
                                pessoa.addchats(conversa.getUId());
                                firestore.collection("pessoa").document(conversa.getMeuContato().getUId()).set(pessoa.getMap())
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                            }
                                        });
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

    public  void pegarMensagens(Conversa conversa){
        Query q = firestore.collection("conversas").document(conversa.getUId()).collection("mensagens").document(conversa.getMeuContato().getUId()).getParent();
//        Query querry1 = firestore.collection("pessoa").document(pessoa.getUsuario().getUId()).collection("dados").whereEqualTo("UId", pessoa.getUsuario().getUId());
        q.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.i("TestePegarDados", document.getId()+ " =>>>>>>>>> " + document.get("nome").toString()+ " => " + document.getData());
                                List<Mensagem> mensagems = (List<Mensagem>) document.toObject(Mensagem.class);
                                if (mensagems.isEmpty()){
                                    Log.i("TesteFalhaPegarDados", "vazio");
                                }
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

    @SuppressLint("LongLogTag")
    public void atualizar(final Conversa conversa, final Mensagem mensagem) {
        Log.i("Teste_conversaDAO.salvarMensagem()", conversa.getUId());
//        List<Mensagem> m = new ArrayList<>();
//        m.add(mensagem);
        firestore.collection("conversas")
                .document(conversa.getUId())
                .collection(conversa.getMeuContato().getUId()).add(mensagem.getMap())
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.i("Teste_conversaDAO.salvarMensagem()", conversa.getUId());
                        conversa.getMeuContato().addMensagem(mensagem);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Teste", e.getMessage());

                    }
                });

    }

    public void receberDados(final Conversa conversa){
        if (conversa.getUId()!=null){

            firestore.collection("conversas").document(conversa.getUId()).collection(conversa.getContato().getUId()).orderBy("timestamp",Query.Direction.ASCENDING)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(QuerySnapshot DocumentSnapshots, FirebaseFirestoreException e) {
                            List<DocumentChange> documentChanges = DocumentSnapshots.getDocumentChanges();
                            if (documentChanges != null){
                                for(DocumentChange dc: documentChanges){

                                    if(dc.getType() == DocumentChange.Type.ADDED){
                                        Mensagem mensagem = dc.getDocument().toObject(Mensagem.class);
                                        mensagem.setRecebida(true);
                                        conversa.getContato().addMensagem(mensagem);

                                    }
                                }
                            }

                        }
                    });

        }
    }

    public void pegarDadosPessoa(final Conversa conversa) {
        Query querry1 = firestore.collection("conversas").document(conversa.getUId()).collection("dados").document().getParent();
//        Query querry1 = firestore.collection("pessoa").document(pessoa.getUsuario().getUId()).collection("dados").whereEqualTo("UId", pessoa.getUsuario().getUId());
        querry1.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.i("TestePegarDados", document.getId()+ " =>>>>>>>>> " + document.get("nome").toString()+ " => " + document.getData());
                                List<Contato> contatoes = (List<Contato>) document.get("contatos");
                                if (contatoes!=null){
//                                    contatoes.get(0).equals()
                                }
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
