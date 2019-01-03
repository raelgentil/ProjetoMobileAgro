package br.com.alisonrodrigo_rafaelgentil.agro.projetomobile;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import br.com.alisonrodrigo_rafaelgentil.agro.model.dao.ClienteDao;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Pessoa;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.interfaces.ComunicadorInterface;


public class MainActivity extends AppCompatActivity implements ComunicadorInterface {

    private ClienteDao clienteDao;
    private Pessoa pessoa;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main_activy);
        abrirTelaLogin();



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

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Toast.makeText(getApplicationContext(), "Bem vindo de volta " + user.getEmail() + "!", Toast.LENGTH_LONG).show();
        } else {
//            Intent intent = new Intent(this, LoginActivity.class);
//            startActivity(intent);
//            finish();
        }
    }

    public void abrirTeladeUsuario(){
        Bundle args = null;
        if(pessoa != null){
            Toast.makeText(getApplicationContext(), "Usuario existe", Toast.LENGTH_LONG).show();
            args = new Bundle();
            args.putSerializable("pessoa", pessoa);
            Intent intent = new Intent(MainActivity.this, PerfilActivy.class);
            intent.putExtra("args", args);
            startActivity(intent);
        }else
            Toast.makeText(getApplicationContext(), "Usuario não existe", Toast.LENGTH_LONG).show();
    }

    @Override
    public void responde(Bundle bundle) {
        pessoa = (Pessoa) bundle.getSerializable("pessoa");
        abrirTeladeUsuario();
    }




}
