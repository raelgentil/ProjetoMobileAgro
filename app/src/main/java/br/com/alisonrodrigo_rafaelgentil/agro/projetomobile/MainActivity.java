package br.com.alisonrodrigo_rafaelgentil.agro.projetomobile;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import br.com.alisonrodrigo_rafaelgentil.agro.model.dao.classes.PessoaDAO;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Pessoa;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Usuario;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.interfaces.IObserver;
import br.com.alisonrodrigo_rafaelgentil.agro.model.fachada.Fachada;
import br.com.alisonrodrigo_rafaelgentil.agro.model.fachada.IFachada;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.interfaces.IComunicadorInterface;


public class MainActivity extends AppCompatActivity implements IComunicadorInterface, IObserver {

    private PessoaDAO pessoaDAO;
    private Pessoa pessoa;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private IFachada fachada;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fachada = new Fachada();
        pessoa = new Pessoa();
        pessoa.addObserver(this);
        fachada.verificarUserLogado(pessoa);

    }

    public void abrirTelaLogin(){
        LoginFragment loginFragment = new LoginFragment();
        pessoa.removeObserver(this);
        loginFragment.setFachada(fachada);
        loginFragment.setDrawer(drawer);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
        fragmentTransaction.replace (R.id.layoutMainPrincipal, loginFragment);
//        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit ();
    }

    public void abrirTeladeUsuario(){
        Bundle args = null;
        if(pessoa != null){
            pessoa.removeObserver(this);  // lembrar de verificar se quando usar o botão de voltar
                                            //permanece a mesma instancia da MainActivity ou se é uma nova
//            Toast.makeText(getApplicationContext(), "Bem vindo " + pessoa.getNome(), Toast.LENGTH_LONG).show();
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

    @Override
    public void update(Object observado) {
        if (observado instanceof Pessoa){
            pessoa = (Pessoa) observado;
            Log.i("TesteUpdate", "Atualizando");
            if (pessoa.getUsuario().getUId() != null) {
                Toast.makeText(getApplicationContext(), "Bem vindo de volta " +pessoa.getNome() + "!", Toast.LENGTH_LONG).show();
                abrirTeladeUsuario();
            } else {
                abrirTelaLogin();
            }
        }
    }




}
