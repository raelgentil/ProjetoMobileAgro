package br.com.alisonrodrigo_rafaelgentil.agro.projetomobile;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import java.util.Map;

import br.com.alisonrodrigo_rafaelgentil.agro.model.dao.PessoaDAO;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Contato;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Pessoa;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.interfaces.ComunicadorInterface;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContatoFragment extends Fragment implements ComunicadorInterface {

private GroupAdapter adapter;
    private Pessoa pessoa;
    private DrawerLayout drawer;
    public ContatoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contato, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
//        getContext()setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
//        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        adapter = new GroupAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Log.i("Teste Agora Bora", pessoa.getNome());

        return view;

    }

    public void addItemList(Contato contato){
        adapter.add(new ContatoItem(contato));
        adapter.notifyDataSetChanged();
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();

        getActivity().getMenuInflater().inflate(R.menu.contato_activy, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.menuItem_contato_buscar).getActionView();
        searchView.setOnQueryTextListener(new SearchFiltro(this));
//        SearchView searchView = new SearchView(getActivity());
//        searchView.setOnQueryTextListener(new SearchFiltro());
        super.onCreateOptionsMenu(menu, inflater);

    }


    @Override
    public void responde(Map<String, Object> map) {
        pessoa =(Pessoa) map.get("pessoa");
        drawer = (DrawerLayout) map.get("drawer_layout");
    }

    public class ContatoItem extends Item<ViewHolder>{
        private  final Contato contato;
        CircleImageView fotoImgView;
        TextView nomeUserTView;

        public ContatoItem(Contato contato) {
            this.contato = contato;
        }
        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {
            Log.i("TesteContato", "Deu ceto tb");
            fotoImgView =(CircleImageView)  viewHolder.itemView.findViewById(R.id.fotoImgView);
            nomeUserTView = (TextView) viewHolder.itemView.findViewById(R.id.mensagemTView);
            Button b = (Button)viewHolder.itemView.findViewById(R.id.fotoButton);
            if (this.contato.getNome()!=null) {
                nomeUserTView.setText(this.contato.getNome());
                if (this.contato.getFotoFileURL() == null || this.contato.getFotoFileURL() ==""){
                    b.setAlpha(1);
                }else{
                    b.setAlpha(0);
                    Picasso.get().load(this.contato.getFotoFileURL()).resize(350, 350).centerCrop().into(fotoImgView);
                }
            }
        }

        @Override
        public int getLayout() {
            return R.layout.item_contato;
        }
    }

    private class SearchFiltro implements SearchView.OnQueryTextListener {
        ContatoFragment contatoFragment;

        public SearchFiltro(ContatoFragment contatoFragment) {
            this.contatoFragment = contatoFragment;
        }

        @Override
        public boolean onQueryTextSubmit(String s) {
            Log.i("TesteSearchFiltro", " onQueryTextSubmit:    " + s);
            PessoaDAO pessoaDAO = new PessoaDAO();
            pessoaDAO.buscarContatos(s,contatoFragment);

            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {
//            Log.i("TesteSearchFiltro", " onQueryTextChange:    " + s);
            return false;
        }
    }
}
