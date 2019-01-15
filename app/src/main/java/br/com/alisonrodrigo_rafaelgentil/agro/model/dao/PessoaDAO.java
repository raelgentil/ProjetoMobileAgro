package br.com.alisonrodrigo_rafaelgentil.agro.model.dao;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Contato;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Pessoa;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Usuario;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.ContatoFragment;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.LoginFragment;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.PerfilFragment;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.interfaces.ComunicadorInterface;

public class PessoaDAO implements IPessoaDAO{
    FirebaseAuth auth;

    public PessoaDAO() {
        this.auth = FirebaseAuth.getInstance();
    }
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isSenhaValid(String senha) {
        //TODO: Replace this with your own logic
        return senha.length() > 5;
    }

    public boolean autenticarUsuario(Usuario usuario, final LoginFragment fragment){
//        final ComunicadorInterface comunicadorInterface = (ComunicadorInterface) fragment.getContext();
        if (isEmailValid(usuario.getEmail()) && isSenhaValid(usuario.getSenha())){
            final Pessoa pessoa = new Pessoa();
            pessoa.setUsuario(usuario);
            Log.i("TesteAutenticado", "Vou entrar no autenticar:   "  + usuario.getEmail() + "    " + usuario.getSenha());
            FirebaseAuth.getInstance().signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Log.i("TesteAutenticado", task.getResult().getUser().getUid());
                                pessoa.getUsuario().setUId(task.getResult().getUser().getUid());
                                Query querry1 = FirebaseFirestore.getInstance().collection("pessoa").whereEqualTo("UId", pessoa.getUsuario().getUId());
                                querry1.get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        Log.i("TestePegarDados", document.getId()+ " =>>>>>>>>> " + document.get("nome").toString()+ " => " + document.getData());
                                                        pessoa.setUId(document.getId());
                                                        pessoa.setNome((String) document.get("nome"));
                                                        pessoa.setCpf((String) document.get("cpf"));
                                                        pessoa.setTelefone((String) document.get("telefone"));
                                                        pessoa.setLogin((String) document.get("login"));
                                                        pessoa.setDataNascimento((String) document.get("dataNascimento"));
                                                        pessoa.setFotoFileURL((String) document.get("fotoFileURL"));
                                                        Map<String, Object> map = new HashMap<>();
                                                        map.put("pessoa", pessoa);
                                                        ((ComunicadorInterface)fragment.getContext()).responde(map);
                                                        Log.i("TesteDeuCerto", pessoa.getNome());
                                                        map.put("show", true);
                                                        map.put("mensagem", "");
                                                        fragment.responde(map);
// showProgress(false);
//                                                        okButton.setEnabled(true);
//                                                        cadastroTView.setEnabled(true);

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
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        String mensagem = "Erro: ";
                        @Override
                        public void onFailure(@NonNull Exception e) {
//                            showProgress(false);
//                            okButton.setEnabled(true);
//                            cadastroTView.setEnabled(true);
                            e.printStackTrace();
                            if(e.getMessage().equals("The email address is badly formatted.")){
                                mensagem += "O e-mail digitado é invalido, digite um e-mail valido";
                            }
                            if(e.getMessage().equals("There is no user record corresponding to this identifier. The user may have been deleted.") || e.getMessage().equals("The password is invalid or the user does not have a password.")){
                                mensagem += "E-mail ou senha incorreto";
                            }
//                            System.out.println("+"+e.getMessage() + "                          "+e.fillInStackTrace());
//                            Toast.makeText(fragment.getContext().getApplicationContext(), "" + mensagem, Toast.LENGTH_LONG).show();

                            Map<String, Object> map = new HashMap<>();
                            map.put("show", true);
                            map.put("mensagem", mensagem);
                            fragment.responde(map);
                        }
                    });
            return true;
        }else{
            Map<String, Object> map = new HashMap<>();
            map.put("show", true);
            map.put("mensagem", "E-mail ou senha incorreto");
            fragment.responde(map);
//            Toast.makeText(fragment.getContext().getApplicationContext(), "E-mail ou senha incorreto" , Toast.LENGTH_LONG).show();
//            showProgress(false);
//            okButton.setEnabled(true);
//            cadastroTView.setEnabled(true);
            return false;
        }
    }

    public void salvar(final Pessoa pessoa, final PerfilFragment fragment){
//        pessoa = pegarDadosTela();
        final String mesagem = "";
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(pessoa.getUsuario().getEmail(), pessoa.getUsuario().getSenha())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.i("Teste", task.getResult().getUser().getUid());
                            pessoa.getUsuario().setUId(task.getResult().getUser().getUid());
                            Log.i("Teste", pessoa.getUsuario().getUId());
                            if (pessoa.getmSelectUri()==null){
                                salvarPessoa(pessoa, fragment);
                            }else{
                                salvarFoto(pessoa, fragment);
                            }
                        }else{
                            String erroException="";
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                erroException = "Digite uma senha mais forte, contendo no minimo 8 caracter";
                            }catch (FirebaseAuthInvalidCredentialsException e) {
                                erroException = "O e-mail digitado é invalido, digite outro e-mail";
                            }catch (FirebaseAuthUserCollisionException e) {
                                erroException = "Esse e-mail ja foi cadastrado no sistema, digite outro e-mail";
                            }catch (Exception e) {
                                erroException = "Erro ao Cadastrar Usuario" ;
                            }
//                            Toast.makeText(getContext().getApplicationContext(), "Erro: " + erroException, Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Teste", e.getMessage());
                    }
                });
    }

    private void salvarFoto(final Pessoa pessoa, final PerfilFragment fragment) {
        String fileName = UUID.randomUUID().toString();
        final StorageReference ref = FirebaseStorage.getInstance().getReference("/images/perfil" + fileName);
        ref.putFile(pessoa.getmSelectUri())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String uid = FirebaseAuth.getInstance().getUid();
                                String fotoFileURL = uri.toString();

                                pessoa.setFotoFileURL(fotoFileURL);
                                Log.i("TesteFoto", fotoFileURL);
                                salvarPessoa(pessoa, fragment);
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Teste", e.getMessage(), e);
                    }
                });
    }

    private void salvarPessoa(final Pessoa pessoa, final PerfilFragment fragment) {
        final Map<String, Object> map = new HashMap<>();
        FirebaseFirestore.getInstance().collection("pessoa").add(pessoa.getMap())
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.i("TesteUser", documentReference.getId());
                        pessoa.setUId(documentReference.getId());
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

    public void buscarContatos(String busca, final ContatoFragment fragment) {
//        final Pessoa pessoa = null; //Modificar pra contato
        FirebaseFirestore.getInstance().collection("pessoa").whereEqualTo("login", busca)
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
