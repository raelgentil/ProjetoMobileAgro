package br.com.alisonrodrigo_rafaelgentil.agro.model.dao.classes;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.support.annotation.NonNull;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.alisonrodrigo_rafaelgentil.agro.model.dao.interfaces.IPessoaDAO;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Pessoa;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Usuario;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.LoginFragment;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.PerfilFragment;

public class PessoaDAO implements IPessoaDAO {
    FirebaseAuth auth;
    FirebaseFirestore firestore;

    public PessoaDAO(FirebaseAuth auth, FirebaseFirestore firestore) {
        this.auth = auth;
        this.firestore = firestore;
    }

    public boolean autenticar(final Pessoa pessoa){
//        final IComunicadorInterface comunicadorInterface = (IComunicadorInterface) fragment.getContext();

            Log.i("TesteAutenticado", "Vou entrar no autenticar:   "  + pessoa.getUsuario().getEmail() + "    " + pessoa.getUsuario().getSenha());
           auth.signInWithEmailAndPassword(pessoa.getUsuario().getEmail(), pessoa.getUsuario().getSenha())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Log.i("TesteAutenticado", task.getResult().getUser().getUid());
                                pessoa.getUsuario().setUId(task.getResult().getUser().getUid());
                                pegarDadosPessoa(pessoa);

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
//                            fragment.responde(map);
                        }
                    });
            return true;
    }

//
//    public boolean autenticar(Usuario usuario, final LoginFragment fragment){
////        final IComunicadorInterface comunicadorInterface = (IComunicadorInterface) fragment.getContext();
//
//            final Pessoa pessoa = new Pessoa();
//            pessoa.setUsuario(usuario);
//            Log.i("TesteAutenticado", "Vou entrar no autenticar:   "  + usuario.getEmail() + "    " + usuario.getSenha());
//           auth.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
//                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if(task.isSuccessful()){
//                                Log.i("TesteAutenticado", task.getResult().getUser().getUid());
//                                pessoa.getUsuario().setUId(task.getResult().getUser().getUid());
////                                Query querry1 = firestore.collection("pessoa").whereEqualTo("UId", pessoa.getUsuario().getUId());
//
//                                Query querry1 = firestore.collection("pessoa").document(pessoa.getUsuario().getUId()).getParent();
//                                querry1.get()
//                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                                if (task.isSuccessful()) {
//                                                    for (QueryDocumentSnapshot document : task.getResult()) {
//                                                        Log.i("TestePegarDados", document.getId()+ " =>>>>>>>>> " + document.get("nome").toString()+ " => " + document.getData());
//                                                        pessoa.setUId(document.getId());
//                                                        pessoa.setNome((String) document.get("nome"));
//                                                        pessoa.setCpf((String) document.get("cpf"));
//                                                        pessoa.setTelefone((String) document.get("telefone"));
//                                                        pessoa.setLogin((String) document.get("login"));
//                                                        pessoa.setDataNascimento((String) document.get("dataNascimento"));
//                                                        pessoa.setFotoFileURL((String) document.get("fotoFileURL"));
//                                                        Map<String, Object> map = new HashMap<>();
//                                                        map.put("pessoa", pessoa);
////                                                        ((IComunicadorInterface)fragment.getContext()).responde(map);
//                                                        Log.i("TesteDeuCerto", pessoa.getNome());
//                                                        map.put("show", true);
//                                                        map.put("mensagem", "");
//                                                        fragment.responde(map);
//                                                    }
//                                                }
//                                            }
//                                        }).addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        Log.i("TesteFalhaPegarDados", e.getMessage());
////                                                        Log.w(TAG, "Error getting documents.", task.getException());
//                                    }
//                                });
//
//                            }
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        String mensagem = "Erro: ";
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
////                            showProgress(false);
////                            okButton.setEnabled(true);
////                            cadastroTView.setEnabled(true);
//                            e.printStackTrace();
//                            if(e.getMessage().equals("The email address is badly formatted.")){
//                                mensagem += "O e-mail digitado é invalido, digite um e-mail valido";
//                            }
//                            if(e.getMessage().equals("There is no user record corresponding to this identifier. The user may have been deleted.") || e.getMessage().equals("The password is invalid or the user does not have a password.")){
//                                mensagem += "E-mail ou senha incorreto";
//                            }
////                            System.out.println("+"+e.getMessage() + "                          "+e.fillInStackTrace());
////                            Toast.makeText(fragment.getContext().getApplicationContext(), "" + mensagem, Toast.LENGTH_LONG).show();
//
//                            Map<String, Object> map = new HashMap<>();
//                            map.put("show", true);
//                            map.put("mensagem", mensagem);
//                            fragment.responde(map);
//                        }
//                    });
//            return true;
//    }

    public void salvar(final Pessoa pessoa, final PerfilFragment fragment){
        auth.createUserWithEmailAndPassword(pessoa.getUsuario().getEmail(), pessoa.getUsuario().getSenha())
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

    @SuppressLint("LongLogTag")
    private void salvarFoto(final Pessoa pessoa, final PerfilFragment fragment) {
        Log.i("Teste_PessoaDAO.salvarFoto()", "" + pessoa.getUsuario().getUId());
        final StorageReference ref = FirebaseStorage.getInstance().getReference("/images/perfil/" + pessoa.getUsuario().getUId());
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
    public void atualizar(final Pessoa pessoa, final PerfilFragment fragment){
        if (pessoa.getmSelectUri()==null){
            salvarPessoa(pessoa, fragment);
        }else{
            salvarFoto(pessoa, fragment);
        }
    }
    private void salvarPessoa(final Pessoa pessoa, final PerfilFragment fragment) {
        final Map<String, Object> map = new HashMap<>();
        firestore.collection("pessoa").document(pessoa.getUsuario().getUId()).set(pessoa.getMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

//                        Toast.makeText(getContext(),"Usuário salvo com sucesso!", Toast.LENGTH_LONG).show();
                        map.put("mensagem", "Usuário salvo com sucesso!");
                        fragment.responde(map);
                    }
                })
               .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Teste", e.getMessage());
//                Toast.makeText(getContext(),"Erro ao salvarOuAtualizar Usuario!", Toast.LENGTH_LONG).show();
                map.put("mensagem", "Erro ao salvarOuAtualizar Usuario!");
                fragment.responde(map);
            }
        });
    }

    public Pessoa verificarUserLogado(Pessoa pessoa){
//        auth = FirebaseAuth.getInstance();
        Log.i("TesteAutenticacao   :", "Entrei no metodo pessoaDAO.verificarUserLogado");
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
//            Toast.makeText(getApplicationContext(), "Bem vindo de volta "  + "!", Toast.LENGTH_LONG).show();
            Usuario u = new Usuario();
            u.setUId(user.getUid());
            u.setEmail(user.getEmail());
            Log.i("TesteAutenticacao   :", user.getEmail());
            pessoa.setUsuario(u);

        }
        Log.i("TesteAutenticacao   :", "Vou retornar");
        return pessoa;
    }

    public void pegarDadosPessoa(final Pessoa pessoa) {
        Query querry1 = firestore.collection("pessoa").document(pessoa.getUsuario().getUId()).getParent();
//        Query querry1 = firestore.collection("pessoa").document(pessoa.getUsuario().getUId()).collection("dados").whereEqualTo("UId", pessoa.getUsuario().getUId());
        querry1.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.i("TestePegarDados", document.getId()+ " =>>>>>>>>> " + document.get("nome").toString()+ " => " + document.getData());
                                document.get(pessoa.getUsuario().getUId());
                                pessoa.setUId(document.getId());
                                pessoa.setNome((String) document.get("nome"));
                                pessoa.setCpf((String) document.get("cpf"));
                                pessoa.setTelefone((String) document.get("telefone"));
                                pessoa.setLogin((String) document.get("login"));
                                pessoa.setDataNascimento((String) document.get("dataNascimento"));
                                pessoa.setFotoFileURL((String) document.get("fotoFileURL"));
                                pessoa.setUIdchats((List<String>) document.get("conversas"));
                                pessoa.notifyObservers();
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
