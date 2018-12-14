package br.com.alisonrodrigo_rafaelgentil.agro.projetomobile;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.firestore.Query;
//import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.Usuario;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.interfaces.ComunicadorInterface;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private TextView cadastroTView;
    private Button okButton;
    PerfilFragment perfilFragment;
    ComunicadorInterface comunicadorInterface;
    private  Usuario usuario;


//    private UserLogin userLogin = null;

    private EditText loginText;
    private EditText senhaText;
    private View progressView;
    private FirebaseUser user;
    private FirebaseAuth mAuth;







    public LoginFragment() {
        // Required empty public constructor
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
//    }

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

                attemptLoginOrRegister(false);





            }
        });

        return view;
    }


    public void abrirCadastro(View v){

        perfilFragment = new PerfilFragment();


        perfilFragment.setNomeButton(MaskEditUtil.SALVAR);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
        fragmentTransaction.replace (R.id.layoutMainPrincipal, perfilFragment);
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


    private void attemptLoginOrRegister(boolean isNewUser) {
//        if (userLogin != null) {
//            return;
//        }

        // Reset errors.
        loginText.setError(null);
        senhaText.setError(null);

        // Store values at the time of the login attempt.
        String email = loginText.getText().toString();
        String senha = senhaText.getText().toString();
        String[] uId = new String[1];


        boolean cancel = false;
        View focusView = null;

        // Check for a valid senha, if the user entered one.
        if (!TextUtils.isEmpty(senha) && !isSenhaValid(senha)) {
            senhaText.setError(getString(R.string.error_invalid_password));
            focusView = senhaText;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            loginText.setError(getString(R.string.error_field_required));
            focusView = loginText;
            cancel = true;
        } else if (!isEmailValid(email)) {
            loginText.setError(getString(R.string.error_invalid_email));
            focusView = loginText;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            if(isNewUser) {
                mAuth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        showProgress(false);
                        String uIdd =""+ task.getResult().getUser().getUid();

                        Toast.makeText(getContext().getApplicationContext(), "Usuário cadastrado com sucesso. Agora você pode se autenticar com suas credenciais!", Toast.LENGTH_LONG).show();
                    }
                })
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Log.i("TesteAutenticado", task.getResult().getUser().getUid());
                                    user = task.getResult().getUser();
//                                    LibraClass
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i("TesteFalhaAoAutenticar", e.getMessage());
                            }
                        });
            }
            else {
//                userLogin = new UserLogin(email, senha);
//                userLogin.execute((Void) null);
                mAuth.signInWithEmailAndPassword(email, senha)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Log.i("TesteAutenticado", task.getResult().getUser().getUid());
                                    DatabaseReference raiz = FirebaseDatabase.getInstance().getReference();
//                                    DatabaseReference raiz = mAuth.getUid()

                                    Query querry1 = FirebaseFirestore.getInstance().collection("user").whereEqualTo("email", loginText.getText().toString());
                                            querry1.get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                            Log.i("TestePegarDados", document.getId()+ " =>>>>>>>>> " + document.get("nome").toString()+ " => " + document.getData());

                                                            usuario = new Usuario(document.getId(), document.get("nome").toString(), document.get("cpf").toString(),
                                                                    document.get("email").toString(), document.get("telefone").toString(), document.get("login").toString(),
                                                                    document.get("senha").toString(), document.get("dataNascimento").toString(), document.get("fotoFileURL").toString());
                                                            Bundle bundle = new Bundle();
                                                            bundle.putSerializable("usuario", usuario);
                                                            comunicadorInterface.responde(bundle);
                                                            Log.i("TesteDeuCertoPegarDados", usuario.toString());


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

//                                    Query query2 = raiz.child("user").orderByChild("email").equalTo(loginText.getText().toString());
//                                    query2.addListenerForSingleValueEvent(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(DataSnapshot dataSnapshot) {
//
//
//                                        }
//
//                                        @Override
//                                        public void onCancelled(DatabaseError databaseError) {
//
//                                        }
//                                    });

//                                    Usuario usuario = new Usuario();
//                                    usuario.setLogin(loginText.getText().toString());
//                                    usuario.setSenha(senhaText.getText().toString());
//                                    Bundle bundle = new Bundle();
//                                    bundle.putSerializable("usuario", usuario);
//                                    comunicadorInterface.responde(bundle);
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i("TesteFalhaAoAutenticar", e.getMessage());
                            }
                        });
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("usuario", usuario);
//                comunicadorInterface.responde(bundle);
//                Log.i("TesteDeuCerto", usuario.toString());
            }
        }
    }
//    private void writeNewUser(String name, String email) {
//        User user = new User(name, email);
//
//        mDatabase.child("users").child(userId).setValue(user);
//    }
//
//    public class UserLogin extends AsyncTask<Void, Void, Boolean> {
//
//        private final String email;
//        private final String senha;
//
//        UserLogin(String email, String senha) {
//            this.email = email;
//            this.senha = senha;
//        }
//
//
//        @Override
//        protected Boolean doInBackground(Void... voids) {
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(final Boolean success) {
//            userLogin = null;
//            showProgress(false);
//
//            if (success) {
//                Usuario usuario = new Usuario();
//                usuario.setLogin(loginText.getText().toString());
//                usuario.setSenha(senhaText.getText().toString());
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("usuario", usuario);
//                comunicadorInterface.responde(bundle);
//
//
//
//
//            } else {
//                senhaText.setError(getString(R.string.error_incorrect_password));
//                senhaText.requestFocus();
//            }
//        }
//
//        @Override
//        protected void onCancelled() {
//            userLogin = null;
//            showProgress(false);
//        }
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

//            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
////                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//                }
//            });

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

}
