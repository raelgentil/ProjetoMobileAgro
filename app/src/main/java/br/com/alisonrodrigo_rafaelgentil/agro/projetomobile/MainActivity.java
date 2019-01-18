package br.com.alisonrodrigo_rafaelgentil.agro.projetomobile;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import br.com.alisonrodrigo_rafaelgentil.agro.model.dao.ClienteDao;
import br.com.alisonrodrigo_rafaelgentil.agro.model.dao.PessoaDAO;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Pessoa;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Usuario;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.interfaces.ComunicadorInterface;


public class MainActivity extends AppCompatActivity implements ComunicadorInterface {

    private PessoaDAO pessoaDAO;
    private Pessoa pessoa;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main_activy);
        verificarUserLogado();



//        ChatFragment  chatFragment = new ChatFragment();
//        FragmentManager fragmentManager2 = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction ();
//        fragmentTransaction2.replace (R.id.layoutMainPrincipal, chatFragment, "mainFrag");
//        fragmentTransaction2.commit ();

        // Write a message to the database


    }

    public void abrirTelaLogin(){
        LoginFragment loginFragment = new LoginFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
        fragmentTransaction.replace (R.id.layoutMainPrincipal, loginFragment);
//        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit ();
    }

    public void verificarUserLogado(){
        pessoa = new Pessoa();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
//            Toast.makeText(getApplicationContext(), "Bem vindo de volta "  + "!", Toast.LENGTH_LONG).show();
            Usuario u = new Usuario();
            u.setUId(user.getUid());
            u.setEmail(user.getEmail());
            pessoa.setUsuario(u);
            pegarDadosPessoa();

        } else {
            abrirTelaLogin();
        }
    }

    private void pegarDadosPessoa() {

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
                                abrirTeladeUsuario();
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


    public void abrirTeladeUsuario(){
        Bundle args = null;
        if(pessoa != null){
            Toast.makeText(getApplicationContext(), "Bem vindo " + pessoa.getNome(), Toast.LENGTH_LONG).show();
            args = new Bundle();
            args.putSerializable("pessoa", pessoa);
            Intent intent = new Intent(MainActivity.this, PerfilActivy.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("args", args);
            startActivity(intent);
        }else
            Toast.makeText(getApplicationContext(), "Usuario não existe", Toast.LENGTH_LONG).show();
    }

    @Override
    public void responde(Map<String, Object> map) {
        pessoa = (Pessoa) map.get("pessoa");
        abrirTeladeUsuario();
    }

//    @Override
//    public void responde(Bundle bundle) {
//        pessoa = (Pessoa) bundle.getSerializable("pessoa");
//        abrirTeladeUsuario();
//    }




}
