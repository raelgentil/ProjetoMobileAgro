package br.com.alisonrodrigo_rafaelgentil.agro.projetomobile;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import br.com.alisonrodrigo_rafaelgentil.agro.model.dao.ClienteDao;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.Usuario;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.interfaces.ComunicadorInterface;


public class MainActivity extends AppCompatActivity implements ComunicadorInterface {

    private ClienteDao clienteDao;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main_activy);
        LoginFragment loginFragment = new LoginFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
        fragmentTransaction.replace (R.id.layoutMainPrincipal, loginFragment);
        fragmentTransaction.commit ();



//        ChatFragment  chatFragment = new ChatFragment();
//        FragmentManager fragmentManager2 = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction ();
//        fragmentTransaction2.replace (R.id.layoutMainPrincipal, chatFragment, "mainFrag");
//        fragmentTransaction2.commit ();

        // Write a message to the database


    }

    public void abrirTeladeUsuario(){
        clienteDao = new ClienteDao(getBaseContext());
        usuario = clienteDao.verificarLogin(usuario.getLogin(), usuario.getSenha());
        Bundle args = null;
        if(usuario != null){
            FirebaseFirestore.getInstance().collection("user").add(usuario)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.i("Teste", documentReference.getId());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.i("Teste", e.getMessage());
                }
            });
            Toast.makeText(getApplicationContext(), "Usuario existe", Toast.LENGTH_LONG).show();
            args = new Bundle();
            args.putSerializable("usuario", usuario);
            Intent intent = new Intent(MainActivity.this, PerfilActivy.class);
            intent.putExtra("args", args);
            startActivity(intent);

        }else
            Toast.makeText(getApplicationContext(), "Usuario não existe", Toast.LENGTH_LONG).show();
    }


    @Override
    public void responde(Bundle bundle) {

        usuario = (Usuario) bundle.getSerializable("usuario");
        abrirTeladeUsuario();

    }




}