package br.com.alisonrodrigo_rafaelgentil.agro.projetomobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Pessoa;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.interfaces.Observer;
import de.hdodenhof.circleimageview.CircleImageView;


public class PerfilActivy extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Observer {


    private Pessoa pessoa;
    private PublicacoesFragment publicacaoFragment;
    private PerfilFragment perfilFragment;
    private TextView emailTViewHeader;
    private TextView nomeTViewHeader;
    private Button selectImgButtonHeader;
    private CircleImageView fotoImgViewHeader;
    private  DrawerLayout drawer;
    Toolbar toolbar;
    ChatFragment chatFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        nomeTViewHeader = (TextView) headerView.findViewById(R.id.nomeUserTView);
        emailTViewHeader = (TextView) headerView.findViewById(R.id.emailUserTView);
        selectImgButtonHeader = (Button) headerView.findViewById(R.id.selectImgButton);
        fotoImgViewHeader = (CircleImageView)headerView.findViewById(R.id.fotoImgView);
        Bundle args = getIntent().getBundleExtra("args");
        pessoa = (Pessoa) args.getSerializable("pessoa");
        if (pessoa.getNome() != null){
            pessoa.addObserver(this);
            pessoa.notifyObservers();
        }





        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
//        perfilFragment = new PerfilFragment();
        Map<String, Object> map = new HashMap<>();
//        map.put("nomeButton", MaskEditUtil.EDITAR);
//        map.put("pessoa", pessoa);
//        map.put("drawer_layout", drawer);
//        perfilFragment.responde(map);
//        fragmentTransaction.replace(R.id.layoutPrincipal, perfilFragment);
//        fragmentTransaction.commit ();



//        chatFragment = new ChatFragment();
//        map = new HashMap<>();
//        map.put("pessoa", pessoa);
//        map.put("drawer_layout", drawer);
//        chatFragment.responde(map);
//        fragmentTransaction.replace(R.id.layoutPrincipal, chatFragment);
//        fragmentTransaction.commit ();


//        FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
                ContatoFragment contatoFragment = new ContatoFragment();
                Map<String, Object> map1 = new HashMap<>();
                map1.put("pessoa", pessoa);
                contatoFragment.responde(map1);
                fragmentTransaction.replace(R.id.layoutPrincipal, contatoFragment);
                fragmentTransaction.commit ();


    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.perfil_activy, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
        if (id == R.id.menuItem_Perfil) {
            perfilFragment = new PerfilFragment();
            Bundle args = new Bundle();
            args.putSerializable("pessoa", pessoa);
            args.putSerializable("nomeButton", MaskEditUtil.EDITAR);
            perfilFragment.setArguments(args);
            fragmentTransaction.replace(R.id.layoutPrincipal, perfilFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit ();
        } else if(id == R.id.menuItem_publicacoes){
            publicacaoFragment = new PublicacoesFragment();
            fragmentTransaction.replace(R.id.layoutPrincipal, publicacaoFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit ();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
        Map<String, Object> map;
        switch (item.getItemId()){
            case R.id.nav_perfil:
                perfilFragment = new PerfilFragment();
                map = new HashMap<>();
                map.put("nomeButton", MaskEditUtil.EDITAR);
                map.put("pessoa", pessoa);
                map.put("drawer_layout", drawer);
                perfilFragment.responde(map);
                fragmentTransaction.replace(R.id.layoutPrincipal, perfilFragment);
                fragmentTransaction.commit ();
                break;

            case R.id.nav_publicacoes:
                publicacaoFragment = new PublicacoesFragment();
                fragmentTransaction.replace(R.id.layoutPrincipal, publicacaoFragment);
                fragmentTransaction.commit ();
                break;

            case R.id.nav_manage:

                break;

            case R.id.nav_share:
                break;
            case R.id.nav_chat:
                chatFragment = new ChatFragment();
                map = new HashMap<>();
                map.put("pessoa", pessoa);
                map.put("drawer_layout", drawer);
                chatFragment.responde(map);
                fragmentTransaction.replace(R.id.layoutPrincipal, chatFragment);
                fragmentTransaction.commit ();


                break;
            case R.id.nav_sair:
                pessoa = null;
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(PerfilActivy.this, MainActivity.class);
                startActivity(intent);
                break;

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void voltarTelaLogin(){
//        startActivity(new Intent(this, LoginActivity.class));
//        finish();
    }


    @Override
    public void update(Object observado) {
        if (pessoa != null){
            nomeTViewHeader.setText(pessoa.getNome());

            emailTViewHeader.setText(pessoa.getUsuario().getEmail());
            selectImgButtonHeader.setEnabled(true);
            if (pessoa.getFotoFileURL()==null){
                selectImgButtonHeader.setAlpha(1);
            }else{
                selectImgButtonHeader.setAlpha(0);
                Picasso.get().load(pessoa.getFotoFileURL()).resize(100, 100).centerCrop().into(fotoImgViewHeader);
            }
        }
    }
}
