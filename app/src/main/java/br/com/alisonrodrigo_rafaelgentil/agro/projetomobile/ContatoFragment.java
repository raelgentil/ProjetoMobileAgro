package br.com.alisonrodrigo_rafaelgentil.agro.projetomobile;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.xwray.groupie.OnItemClickListener;
import com.xwray.groupie.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Contato;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Conversa;
import br.com.alisonrodrigo_rafaelgentil.agro.model.fachada.Fachada;
import br.com.alisonrodrigo_rafaelgentil.agro.model.fachada.IFachada;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.interfaces.IComunicadorInterface;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContatoFragment extends Fragment implements IComunicadorInterface {

private GroupAdapter adapter;
    private Contato meuContato;
    private ConversaFragment conversaFragment;
    private DrawerLayout drawer;
    private IFachada fachada;
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
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {

                ContatoItem contatoItem = (ContatoItem) item;
                Contato contato = contatoItem.contato;
                Conversa conversa = new Conversa();
//                if (meuContato.getUIdchats()!=null && !(meuContato.getUIdchats().isEmpty())){
//                    conversa.setUId(meuContato.getUIdchats().get(0));
//                }
                conversa.setContato(contato);
                conversa.setMeuContato(meuContato);

                conversaFragment = new ConversaFragment(conversa, drawer, fachada);
//                Map<String, Object> map = new HashMap<>();
//                map.put("fachada", fachada);
//                map.put("drawer_layout", drawer);
//                map.put("conversa", conversa);
//                conversaFragment.responde(map);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
                fragmentTransaction.replace (R.id.layout_principal, conversaFragment);
//        fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit ();
            }
        });
        return view;

    }

    @SuppressLint("LongLogTag")
    public void addItemList(Contato contato){
        if (!(this.meuContato.getUId().equals(contato.getUId()))){
            Log.i("TesteVeficiar Quantidade no adapter",  "Procurando contato" + adapter.getItemCount());
            if (adapter.getItemCount() >= 1){
                for (int i = 0; i < adapter.getItemCount(); i++) {
                    ContatoItem contatoItem = (ContatoItem) adapter.getItem(i);
                    Contato contato1 = contatoItem.contato;
                    if (!(contato1.getUId().equals(contato.getUId()))){
                        adapter.add(new ContatoItem(contato));
                        adapter.notifyDataSetChanged();
                    }
                }
            }else{
                adapter.add(new ContatoItem(contato));
                adapter.notifyDataSetChanged();
            }
        }
    }



    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.contato_activy, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.menuItem_contato_buscar).getActionView();
        searchView.setOnQueryTextListener(new SearchFiltro());
//        SearchView searchView = new SearchView(getActivity());
//        searchView.setOnQueryTextListener(new SearchFiltro());
        super.onCreateOptionsMenu(menu, inflater);

    }


    @Override
    public void responde(Map<String, Object> map) {
        meuContato =(Contato) map.get("meuContato");
        fachada =(Fachada) map.get("fachada");
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
            nomeUserTView = (TextView) viewHolder.itemView.findViewById(R.id.nomeTView);
            Button b = (Button)viewHolder.itemView.findViewById(R.id.fotoButton);
            if (this.contato.getNome()!=null) {
                nomeUserTView.setText(this.contato.getNome());
                if (this.contato.getFotoFileURL() == null || this.contato.getFotoFileURL() ==""){
                    b.setAlpha(1);
                }else{
                    b.setAlpha(0);
                    Picasso.get().load(this.contato.getFotoFileURL()).resize(50, 50).centerCrop().into(fotoImgView);
                }
            }
        }

        @Override
        public int getLayout() {
            return R.layout.item_contato;
        }
    }

    private class SearchFiltro implements SearchView.OnQueryTextListener {
//        ContatoFragment contatoFragment;

        public SearchFiltro() {
        }

//        public SearchFiltro(ContatoFragment contatoFragment) {
//            this.contatoFragment = contatoFragment;
//        }

        @Override
        public boolean onQueryTextSubmit(String s) {
                Log.i("TesteSearchFiltro", " onQueryTextSubmit:    " + s);
//                PessoaDAO pessoaDAO = new PessoaDAO();
                fachada.buscarContatos(s,ContatoFragment.this);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {
//            Log.i("TesteSearchFiltro", " onQueryTextChange:    " + s);
            return false;
        }
    }
}
