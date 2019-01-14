package br.com.alisonrodrigo_rafaelgentil.agro.projetomobile;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;
import com.xwray.groupie.ViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Pessoa;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.interfaces.ComunicadorInterface;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment implements ComunicadorInterface {
    private GroupAdapter adapter;
    private Pessoa pessoa;
    ContatoFragment contatoFragment;
    private  DrawerLayout drawer;
    public ChatFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contato, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        setHasOptionsMenu(true);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
//        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


//
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.contatosRecyclerView);
        adapter = new GroupAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//        buscarButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
//                contatoFragment = new ContatoFragment();
//                Map<String, Object> map1 = new HashMap<>();
//                map1.put("pessoa", pessoa);
//                contatoFragment.responde(map1);
//                fragmentTransaction.replace(R.id.layoutPrincipal, contatoFragment);
//                fragmentTransaction.commit ();
//            }
//        });
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {
                ConversaFragment conversaFragment = new ConversaFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
                fragmentTransaction.replace (R.id.layoutMainPrincipal, conversaFragment);
//        fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit ();
            }
        });
        Log.i("Teste Agora Bora", pessoa.getNome());
//        buscarContatos();
//        adapter.add(new ContatoItem(pessoa));
        adapter.add(new ConversarItem(pessoa));
        adapter.notifyItemInserted(adapter.getItemCount());
        adapter.notifyDataSetChanged();
        adapter.add(new ConversarItem(pessoa));
        adapter.notifyItemInserted(adapter.getItemCount());
        adapter.notifyDataSetChanged();
        adapter.add(new ConversarItem(pessoa));
        adapter.notifyItemInserted(adapter.getItemCount());
        adapter.notifyDataSetChanged();
        adapter.add(new ConversarItem(pessoa));
        adapter.notifyItemInserted(adapter.getItemCount());
        adapter.notifyDataSetChanged();
        adapter.add(new ConversarItem(pessoa));
        adapter.notifyItemInserted(adapter.getItemCount());
        adapter.notifyDataSetChanged();
        adapter.add(new ConversarItem(pessoa));
        adapter.notifyItemInserted(adapter.getItemCount());
        adapter.notifyDataSetChanged();




        return view;


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.chat_activy, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }
//
//    private void buscarContatos() {
////        final Pessoa pessoa = null; //Modificar pra contato
//        FirebaseFirestore.getInstance().collection("pessoa").whereEqualTo("UId", pessoa.getUsuario().getUId())
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//                        if (e!=null){
//                            Log.i("TestContato", e.getMessage(),e);
//                            return;
//                        }
//                        List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
//                        for (DocumentSnapshot doc: docs) {
//                            Pessoa pessoa1 = doc.toObject(Pessoa.class);
//                            adapter.add(new ConversarItem(pessoa1));
//
//                        }
//
//                    }
//                });
//    }

    @Override
    public void responde(Map<String, Object> map) {
        pessoa =(Pessoa) map.get("pessoa");
        drawer = (DrawerLayout) map.get("drawer_layout");

    }

    public class ConversarItem extends Item<ViewHolder> {
        private  final Pessoa pessoa;

        public ConversarItem(Pessoa pessoa) {
            this.pessoa = pessoa;
        }

        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {
            Log.i("TesteConversa", "Vamos Imprimir ");
            CircleImageView fotoImgView =(CircleImageView)  viewHolder.itemView.findViewById(R.id.fotoImgView);
            TextView nomeUserTView = (TextView) viewHolder.itemView.findViewById(R.id.mensagemTView);
            Button b = (Button)viewHolder.itemView.findViewById(R.id.fotoButton);
            if (this.pessoa.getNome()!=null) {
                nomeUserTView.setText(this.pessoa.getNome());
                if (this.pessoa.getFotoFileURL() == null || this.pessoa.getFotoFileURL() ==""){
                    b.setAlpha(1);
                }else{
                    b.setAlpha(0);
                    Picasso.get().load(this.pessoa.getFotoFileURL()).resize(350, 350).centerCrop().into(fotoImgView);
                }
            }
        }

        @Override
        public int getLayout() {
            return R.layout.item_contato;
        }
    }

}
