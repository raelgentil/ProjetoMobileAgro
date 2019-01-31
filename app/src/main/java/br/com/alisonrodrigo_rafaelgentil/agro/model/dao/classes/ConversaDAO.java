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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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
import java.util.UUID;

import br.com.alisonrodrigo_rafaelgentil.agro.model.dao.interfaces.IConversaDAO;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Chat;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Contato;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Conversa;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Mensagem;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Pessoa;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Publicacao;
import br.com.alisonrodrigo_rafaelgentil.agro.model.util.Utill;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.ChatFragment;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.ContatoFragment;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.PerfilFragment;

public class ConversaDAO implements IConversaDAO {
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    private DatabaseReference databaseReference;
    private final String tagNomeClasse = "" + ConversaDAO.class.getSimpleName();


    public ConversaDAO(FirebaseAuth auth, FirebaseFirestore firestore, DatabaseReference databaseReference) {
        this.auth = auth;
        this.firestore = firestore;
        this.databaseReference = databaseReference;
    }
    @SuppressLint("LongLogTag")
    public void salvar(final Conversa conversa, final Mensagem mensagem) {
        String UId = UUID.randomUUID().toString();
        conversa.setUId(UId);
        Log.i("Teste_conversaDAO.salvar():::::::::", UId);
        salvarMensagem(conversa, mensagem);
//
    }

    @SuppressLint("LongLogTag")
    private void salvarMensagem(final Conversa conversa, final Mensagem mensagem) {

        firestore.collection("conversas")
                .document(conversa.getUId())
                .collection(conversa.getMeuContato().getUId()).add(mensagem.getMap())
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        conversa.addMensagem(mensagem);
                        atualizarConversaPessoa(conversa);
                        Log.i("Teste_conversaDAO.salvarMensagem():::::::::", conversa.getUId());

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
                .collection("dados").document().set(conversa.getContato())
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

        firestore.collection("conversas")
                .document(conversa.getUId())
                .collection("dados").document().set(conversa.getMeuContato())
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
//
//    private void atualizarConversaPessoa(final Conversa conversa){
//
//                Query querry1 = firestore.collection("pessoa").document(conversa.getContato().getUId()).getParent();
//                querry1.get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.i("TestePegarDados", document.getId()+ " =>>>>>>>>> " + document.get("nome").toString()+ " => " + document.getData());
//                                Pessoa pessoa = new Pessoa();
//                                pessoa =(Pessoa) document.toObject(Pessoa.class);
//                                pessoa.addchats(conversa.getUId());
//                                firestore.collection("pessoa").document(conversa.getContato().getUId()).set(pessoa.getMap())
//                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void aVoid) {
//
//                                            }
//                                        })
//                                        .addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception e) {
//
//                                            }
//                                        });
//                            }
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.i("TesteFalhaPegarDados", e.getMessage());
////                                                        Log.w(TAG, "Error getting documents.", task.getException());
//            }
//        });
//
//
//        Query querry = firestore.collection("pessoa").document(conversa.getMeuContato().getUId()).getParent();
////        Query querry1 = firestore.collection("pessoa").document(pessoa.getUsuario().getUId()).collection("dados").whereEqualTo("UId", pessoa.getUsuario().getUId());
//        querry.get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.i("TestePegarDados", document.getId()+ " =>>>>>>>>> " + document.get("nome").toString()+ " => " + document.getData());
//                                Pessoa pessoa = new Pessoa();
//                                pessoa =(Pessoa) document.toObject(Pessoa.class);
//                                pessoa.addchats(conversa.getUId());
//                                firestore.collection("pessoa").document(conversa.getMeuContato().getUId()).set(pessoa.getMap())
//                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void aVoid) {
//
//                                            }
//                                        })
//                                        .addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception e) {
//
//                                            }
//                                        });
//                            }
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.i("TesteFalhaPegarDados", e.getMessage());
////                                                        Log.w(TAG, "Error getting documents.", task.getException());
//            }
//        });
//    }

    @SuppressLint("LongLogTag")
    private void atualizarConversaPessoa(final Conversa conversa){
        Log.i("Teste_conversaDAO.atualizarConversaPessoa():::::::::::", conversa.getUId());
        firestore.collection("conversa_pessoa").document(conversa.getMeuContato().getUId())
                .collection(conversa.getMeuContato().getUId()).add(conversa.getMapUId())
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.i("Teste_conversaDAO.atualizarConversaPessoa()", conversa.getUId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });


        firestore.collection("conversa_pessoa").document(conversa.getContato().getUId())
                .collection(conversa.getContato().getUId()).add(conversa.getMapUId())
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.i("Teste_conversaDAO.atualizarConversaPessoa()", conversa.getUId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }

    @SuppressLint("LongLogTag")
    public void atualizar(final Conversa conversa, final Mensagem mensagem) {
        Log.i("Teste_conversaDAO.atualizar()", conversa.getUId());

        firestore.collection("conversas")
                .document(conversa.getUId())
                .collection(conversa.getMeuContato().getUId()).add(mensagem.getMap())
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.i("Teste_conversaDAO.atualizar()", conversa.getUId());
                        conversa.addMensagem(mensagem);
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
                                        conversa.addMensagem(mensagem);

                                    }
                                }
                            }

                        }
                    });

        }
    }

    public void pegarDadosConversa(final Conversa conversa) {
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
                                    if (contatoes.get(0).getUId().equals(conversa.getMeuContato().getUId())){
                                        conversa.setContato(contatoes.get(0));
                                    }else{
                                        conversa.setContato(contatoes.get(0));
                                    }
                                    conversa.notifyObservers();
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

    @Override
    public void pegarConversas(ChatFragment chatFragment) {

    }

    @Override
    public void listarConversas(final Chat chat) {
        this.databaseReference.child("conversa_pessoa").child(chat.getMeu_contato().getUId()).child(chat.getMeu_contato().getUId())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                           final String UId_conversa = (String) postSnapshot.getValue();

                            final String logTag = "Teste_contatoDAO.listarConversas()";
                            Query querry1 = firestore.collection("conversas").document(UId_conversa).getParent();
                            querry1.get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @SuppressLint("LongLogTag")
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                Log.i(logTag, "Deu certo");
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    final Conversa conversa = new Conversa();
                                                    conversa.setUId(UId_conversa);
                                                    Query querry1 = firestore.collection("pessoa").document(document.getId()).getParent();
                                                    querry1.get()
                                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                    if (task.isSuccessful()) {
                                                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                                            Log.i("TestePegarDados", document.getId()+ " =>>>>>>>>> " + document.get("nome").toString()+ " => " + document.getData());
                                                                            Contato contato = (Contato) document.toObject(Contato.class);
//                                                                            if (!(contato.getUId().equals(chat.getMeu_contato().getUId()))){
//                                                                                conversa.setContato(contato);
//                                                                            }else{
                                                                                conversa.setMeuContato(contato);
//                                                                            }
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





                                                    Query querry2 = firestore.collection("conversas").document(conversa.getUId()).collection(document.getId());
                                                    querry2.get()
                                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                    if (task.isSuccessful()) {
                                                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                                            Log.i("TestePegarDados", document.getId()+ " =>>>>>>>>> " + document.get("nome").toString()+ " => " + document.getData());
                                                                            Mensagem mensagem = (Mensagem) document.toObject(Mensagem.class);
                                                                            conversa.addMensagem(mensagem);
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

                                                    chat.addConversa(conversa);
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
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
    }



    //    @Override
    public void pegarConversa(final Conversa conversa) {
        this.databaseReference.child("conversas").child(conversa.getUId())
        .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    pegarMensagemDosOutrso(conversa, postSnapshot.getKey().toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    private void pegarMensagemDosOutrso(final Conversa conversa, String UId) {
        final List<String> UIds = new ArrayList<>();
        this.databaseReference.child("conversas").child(conversa.getUId()).child(UId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                            postSnapshot.getKey().toString();
                            conversa.addMensagem(postSnapshot.getValue(Mensagem.class));
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
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
