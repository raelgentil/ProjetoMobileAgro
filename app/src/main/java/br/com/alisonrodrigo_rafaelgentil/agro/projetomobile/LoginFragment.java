package br.com.alisonrodrigo_rafaelgentil.agro.projetomobile;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.Query;
//import com.google.firebase.database.Query;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import br.com.alisonrodrigo_rafaelgentil.agro.model.dao.PessoaDAO;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Pessoa;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Usuario;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.interfaces.ComunicadorInterface;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements ComunicadorInterface{

    private TextView cadastroTView;
    private Button okButton;
    private PerfilFragment perfilFragment;
    private ComunicadorInterface comunicadorInterface;
    private EditText loginText;
    private EditText senhaText;
    private View progressView;
    private FirebaseUser user;
    private FirebaseAuth mAuth;

    public LoginFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        cadastroTView = (TextView) view.findViewById(R.id.registrarTView);
        loginText = (EditText) view.findViewById(R.id.loginText);
        senhaText = (EditText) view.findViewById(R.id.senhaText);
        okButton = (Button) view.findViewById(R.id.entrarButton);
        progressView = view.findViewById(R.id.login_progress);
        mAuth = FirebaseAuth.getInstance();
        cadastroTView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCadastro(v);
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgress(true);
                okButton.setEnabled(false);
                cadastroTView.setEnabled(false);
                Usuario u = new Usuario();
                u.setEmail(loginText.getText().toString());
                u.setSenha(senhaText.getText().toString());
//                autenticarUsuario(u);
                PessoaDAO pessoaDAO = new PessoaDAO();
                pessoaDAO.autenticarUsuario(u, LoginFragment.this);
                }
        });
        return view;
    }


    public void abrirCadastro(View v){
        perfilFragment = new PerfilFragment();
        Map<String, Object> map = new HashMap<>();
        map.put("nomeButton", MaskEditUtil.SALVAR);
        map.put("pessoa", new Pessoa());
        perfilFragment.responde(map);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
        fragmentTransaction.replace (R.id.layoutMainPrincipal, perfilFragment);
//        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit ();

    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ComunicadorInterface) {
            comunicadorInterface = (ComunicadorInterface)context;
        } else {
            throw new ClassCastException();
        }

    }

//    public Pessoa autenticarUsuario(Usuario usuario){
//        if (isEmailValid(usuario.getEmail()) && isSenhaValid(usuario.getSenha())){
//            final Pessoa pessoa = new Pessoa();
//            pessoa.setUsuario(usuario);
//            Log.i("TesteAutenticado", "Vou entrar no autenticar:   "  + usuario.getEmail() + "    " + usuario.getSenha());
//            FirebaseAuth.getInstance().signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
//                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if(task.isSuccessful()){
//                                Log.i("TesteAutenticado", task.getResult().getUser().getUid());
//                                pessoa.getUsuario().setUId(task.getResult().getUser().getUid());
//                                Query querry1 = FirebaseFirestore.getInstance().collection("pessoa").whereEqualTo("UId", pessoa.getUsuario().getUId());
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
////                                                        Bundle bundle = new Bundle();
////                                                        bundle.putSerializable("pessoa", pessoa);
//                                                        Map<String, Object> map = new HashMap<>();
//                                                        map.put("pessoa", pessoa);
//                                                        comunicadorInterface.responde(map);
//                                                        Log.i("TesteDeuCerto", pessoa.getNome());
//                                                        showProgress(false);
//                                                        okButton.setEnabled(true);
//                                                        cadastroTView.setEnabled(true);
//
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
//                            showProgress(false);
//                            okButton.setEnabled(true);
//                            cadastroTView.setEnabled(true);
//                            e.printStackTrace();
//                            if(e.getMessage().equals("The email address is badly formatted.")){
//                                mensagem += "O e-mail digitado é invalido, digite um e-mail valido";
//                            }
//                            if(e.getMessage().equals("There is no user record corresponding to this identifier. The user may have been deleted.") || e.getMessage().equals("The password is invalid or the user does not have a password.")){
//                                mensagem += "E-mail ou senha incorreto";
//                            }
//                            System.out.println("+"+e.getMessage() + "                          "+e.fillInStackTrace());
//                            Toast.makeText(getContext().getApplicationContext(), "" + mensagem, Toast.LENGTH_LONG).show();
//                        }
//                    });
//            return pessoa;
//        }else{
//            Toast.makeText(getContext().getApplicationContext(), "E-mail ou senha incorreto" , Toast.LENGTH_LONG).show();
//            showProgress(false);
//            okButton.setEnabled(true);
//            cadastroTView.setEnabled(true);
//            return null;
//        }
//
//    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isSenhaValid(String senha) {
        //TODO: Replace this with your own logic
        return senha.length() > 5;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);

        }
    }

    @Override
    public void responde(Map<String, Object> map) {
        boolean show = (boolean) map.get("show");
        String mensagem = (String) map.get("mensagem");
        Toast.makeText(getContext().getApplicationContext(), mensagem , Toast.LENGTH_LONG).show();
        showProgress(!show);
        okButton.setEnabled(show);
        cadastroTView.setEnabled(show);
    }

}
