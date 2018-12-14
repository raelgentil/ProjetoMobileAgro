package br.com.alisonrodrigo_rafaelgentil.agro.projetomobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.Usuario;


public class PerfilActivy extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private Usuario usuario;
    private TextView nomeHeader;
    private PerfilFragment perfilFragment;
    private TextView emailView;
    private PublicacoesFragment publicacaoFragment;
//    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_activy);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        Bundle args = getIntent().getBundleExtra("args");
        usuario = (Usuario) args.getSerializable("usuario");


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView nomeHeader = (TextView) headerView.findViewById(R.id.text_nomeUser);
//        nomeHeader.setText(usuario.getNome().toString());

                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.menuItem_Perfil) {
            perfilFragment = new PerfilFragment();
            perfilFragment.setNomeButton(MaskEditUtil.EDITAR);
            perfilFragment.receberUsuario(usuario);
            fragmentManager.beginTransaction().replace(R.id.layoutPrincipal, perfilFragment).commit();

        } else if(id == R.id.menuItem_publicacoes){
            publicacaoFragment = new PublicacoesFragment();
            fragmentManager.beginTransaction().replace(R.id.layoutPrincipal, publicacaoFragment).commit();

        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_perfil) {
            perfilFragment = new PerfilFragment();
            perfilFragment.receberUsuario(usuario);
            perfilFragment.setNomeButton(MaskEditUtil.EDITAR);
            fragmentManager.beginTransaction().replace(R.id.layoutPrincipal, perfilFragment).commit();

        } else if(id == R.id.nav_publicacoes){

            publicacaoFragment = new PublicacoesFragment();
            fragmentManager.beginTransaction().replace(R.id.layoutPrincipal, publicacaoFragment).commit();

        }else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        else if (id == R.id.nav_sair) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(PerfilActivy.this, MainActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void voltarTelaLogin(){

        startActivity(new Intent(this, LoginActivity.class));
        finish();

    }



}
