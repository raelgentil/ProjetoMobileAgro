package br.com.alisonrodrigo_rafaelgentil.agro.projetomobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Contato;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Conversa;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Pessoa;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.interfaces.IObserver;
import br.com.alisonrodrigo_rafaelgentil.agro.model.fachada.Fachada;
import br.com.alisonrodrigo_rafaelgentil.agro.model.fachada.IFachada;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.interfaces.IComunicadorInterface;
import de.hdodenhof.circleimageview.CircleImageView;


public class PerfilActivy extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IObserver, IComunicadorInterface {


    private Pessoa pessoa;
    private PublicacoesFragment publicacaoFragment;
    private PerfilFragment perfilFragment;
    private TextView emailTViewHeader;
    private TextView nomeTViewHeader;
    private Button selectImgButtonHeader;
    private CircleImageView fotoImgViewHeader;
    private  DrawerLayout drawer;
//    Toolbar toolbar;
    private ChatFragment chatFragment;
    private IFachada fachada;
//    private List<Conversa> conversas;

    public PerfilActivy() {
//        this.conversas = new ArrayList<>();
        fachada = new Fachada();
        chatFragment = new ChatFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        s(true);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();

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
        perfilFragment = new PerfilFragment(drawer, fachada);
        Map<String, Object> map = new HashMap<>();

        map.put("pessoa", pessoa);
        map.put("nomeButton", MaskEditUtil.ATUALIZAR);
//        map.put("drawer_layout", drawer);
        Log.i("TesteButton", (String)(map.get("nomeButton")));
        perfilFragment.responde(map);
//        perfilFragment.setDrawer(drawer);
        fragmentTransaction.replace(R.id.layout_principal, perfilFragment);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.perfil_activy, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
//        if (id == R.id.menuItem_Perfil) {
//            perfilFragment = new PerfilFragment();
//            Bundle args = new Bundle();
//            args.putSerializable("pessoa", pessoa);
//            args.putSerializable("nomeButton", MaskEditUtil.EDITAR);
//            perfilFragment.setArguments(args);
//            fragmentTransaction.replace(R.id.layout_principal, perfilFragment);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit ();
//        } else if(id == R.id.menuItem_publicacoes){
//            publicacaoFragment = new PublicacoesFragment();
//            fragmentTransaction.replace(R.id.layout_principal, publicacaoFragment);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit ();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
        Map<String, Object> map;
        switch (item.getItemId()){
            case R.id.nav_perfil:
                perfilFragment = new PerfilFragment(drawer, fachada);
                map = new HashMap<>();
                map.put("nomeButton", MaskEditUtil.ATUALIZAR);
                map.put("pessoa", pessoa);
//                map.put("fachada", fachada);
//                map.put("drawer_layout", drawer);
//                perfilFragment.setDrawer(drawer);
//                perfilFragment.setFachada(fachada);
                perfilFragment.responde(map);
                fragmentTransaction.replace(R.id.layout_principal, perfilFragment);
                fragmentTransaction.commit ();
                break;

            case R.id.nav_publicacoes:
                publicacaoFragment = new PublicacoesFragment();
                fragmentTransaction.replace(R.id.layout_principal, publicacaoFragment);
                fragmentTransaction.commit ();
                break;

            case R.id.nav_manage:

                break;

            case R.id.nav_share:
                break;
            case R.id.nav_chat:
                map = new HashMap<>();
                map.put("pessoa", pessoa);
                map.put("fachada", fachada);
                map.put("drawer_layout", drawer);
                chatFragment.responde(map);
                fragmentTransaction.replace(R.id.layout_principal, chatFragment);
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

//    public void addConversa(Conversa conversa) {
//        for (Conversa conversa1: conversas) {
//            if (!(conversa.getUId().equals(conversa1.getUId()))){
//                conversas.add( conversa );
//            }
//        }
//    }
//
//    public void removeConversa(Conversa conversa) {
//        int index = conversas.indexOf( conversa );
//        if( index > -1 ){
//            conversas.remove( conversa );
//        }
//    }

    @Override
    public void responde(Map<String, Object> map) {

    }
}
