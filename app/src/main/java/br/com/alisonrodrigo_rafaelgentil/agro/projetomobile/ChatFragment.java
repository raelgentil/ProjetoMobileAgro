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

import java.util.HashMap;
import java.util.Map;

import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Chat;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Contato;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Conversa;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Pessoa;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.interfaces.IObserver;
import br.com.alisonrodrigo_rafaelgentil.agro.model.fachada.IFachada;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.interfaces.IComunicadorInterface;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class ChatFragment extends Fragment implements IComunicadorInterface, IObserver {
    private GroupAdapter adapter;
    private Contato meuContato;
    private ContatoFragment contatoFragment;
    private ConversaFragment conversaFragment;
    private  DrawerLayout drawer;
    private IFachada fachada;
    private Chat chat;
    public ChatFragment(DrawerLayout drawer, IFachada fachada) {
        this.drawer = drawer;
        this.fachada = fachada;
        this.adapter = new GroupAdapter();
        this.fachada.pegarConversas(this);
        this.chat = new Chat();
        this.chat.addObserver(this);


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
        this.chat.setMeu_contato(meuContato);
        fachada.listarConversas(chat);
//
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        fachada.
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {
//                conversaFragment = new ConversaFragment();
                Map<String, Object> map = new HashMap<>();
                ConversarItem conversarItem = (ConversarItem) item;
                Conversa conversa = conversarItem.conversa;
                conversaFragment = new ConversaFragment(conversa,drawer, fachada);
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


    }

    public void addItemList(Conversa conversa){
        for (int i = 0; i < adapter.getItemCount(); i++) {
            ConversarItem conversarItem = (ConversarItem) adapter.getItem(i);
            Conversa conversa11 = conversarItem.conversa;
            if (!(conversa.getUId().equals(conversa11.getUId()))){
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

    @Override
    public void update(Object observado) {
        if (observado instanceof Chat){
            this.chat = (Chat) observado;
            addItemList(chat.getConversas().get(chat.getConversas().size()-1));
        }
    }

    public class ConversarItem extends Item<ViewHolder> {
//        private  final Contato contato;
//        private  final Contato contato;
        private final Conversa conversa;

        public ConversarItem(Conversa conversa) {
            this.conversa = conversa;
        }
//        private  final Contato meuContato;


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
