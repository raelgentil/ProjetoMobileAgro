package br.com.alisonrodrigo_rafaelgentil.agro.projetomobile;


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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;
import com.xwray.groupie.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Contato;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Conversa;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Pessoa;
import br.com.alisonrodrigo_rafaelgentil.agro.model.fachada.Fachada;
import br.com.alisonrodrigo_rafaelgentil.agro.model.fachada.IFachada;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.interfaces.IComunicadorInterface;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment implements IComunicadorInterface {
    private GroupAdapter adapter;
    private Contato meuContato;
    private ContatoFragment contatoFragment;
    private ConversaFragment conversaFragment;
    private  DrawerLayout drawer;
    private IFachada fachada;
    private List<ConversaFragment> conversasFragments;
    public ChatFragment() {
        this.adapter = new GroupAdapter();
        this.conversasFragments = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

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


//
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {
//                conversaFragment = new ConversaFragment();
                Map<String, Object> map = new HashMap<>();

                ConversarItem conversarItem = (ConversarItem) item;
                Conversa conversa = conversarItem.conversa;
//                map.put("conversa", conversa);
//                conversaFragment.responde(map);
                for (ConversaFragment fragment:conversasFragments) {
                    if (fragment.getConversa().getUId().equals(conversa.getUId())){
                        conversaFragment = fragment;
                    }
                }
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
                fragmentTransaction.replace (R.id.layout_principal, conversaFragment);
//        fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit ();
            }
        });
        Log.i("Teste Agora Bora", meuContato.getNome());

//        adapter.add(new ConversarItem(meuContato));adapter.add(new ConversarItem(meuContato));adapter.add(new ConversarItem(meuContato));adapter.add(new ConversarItem(meuContato));adapter.add(new ConversarItem(meuContato));adapter.add(new ConversarItem(meuContato));

        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.chat_activy, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuItem_buscar:
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
                Map<String, Object> map;
                contatoFragment = new ContatoFragment();
                map = new HashMap<>();
                map.put("meuContato", meuContato);
                map.put("fachada", fachada);
                map.put("drawer_layout",drawer);
                contatoFragment.responde(map);
                fragmentTransaction.replace(R.id.layout_principal, contatoFragment);
                fragmentTransaction.commit ();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void responde(Map<String, Object> map) {
        meuContato =(Pessoa) map.get("pessoa");
        drawer = (DrawerLayout) map.get("drawer_layout");
        fachada = (Fachada) map.get("fachada");

    }

    public void addItemList(Conversa conversa){
        for (int i = 0; i < adapter.getItemCount(); i++) {
            ConversarItem conversarItem = (ConversarItem) adapter.getItem(i);
            Conversa conversa1 = conversarItem.conversa;
            if (!(conversa1.getUId().equals(conversa))){
                adapter.add(new ConversarItem(conversa));
                adapter.notifyDataSetChanged();
            }
        }
    }
    public void removeConversa(ConversarItem conversarItem) {
        int index = adapter.getAdapterPosition(conversarItem);
        if( index > -1 ){
            adapter.removeGroup(index);
        }
    }

    public class ConversarItem extends Item<ViewHolder> {
//        private  final Contato contato;
        private  final Conversa conversa;

        public ConversarItem(Conversa conversa) {
            this.conversa = conversa;
        }

        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {
            Log.i("TesteConversa", "Vamos Imprimir ");
            CircleImageView fotoImgView =(CircleImageView)  viewHolder.itemView.findViewById(R.id.fotoImgView);
            TextView nomeUserTView = (TextView) viewHolder.itemView.findViewById(R.id.nomeTView);
            Button b = (Button)viewHolder.itemView.findViewById(R.id.fotoButton);
            if (this.conversa.getContato().getNome()!=null) {
                nomeUserTView.setText(this.conversa.getContato().getNome());
                if (this.conversa.getContato().getFotoFileURL() == null || this.conversa.getContato().getFotoFileURL() ==""){
                    b.setAlpha(1);
                }else{
                    b.setAlpha(0);
                    Picasso.get().load(this.conversa.getContato().getFotoFileURL()).resize(350, 350).centerCrop().into(fotoImgView);
                }
            }
        }

        @Override
        public int getLayout() {
            return R.layout.item_contato;
        }
    }

}
