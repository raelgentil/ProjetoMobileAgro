package br.com.alisonrodrigo_rafaelgentil.agro.projetomobile;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.List;
import java.util.Map;

import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Pessoa;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {
    private GroupAdapter adapter;
    private Pessoa pessoa;

    public ChatFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contato, container, false);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.contatosRecyclerView);
        adapter = new GroupAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

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
        buscarContatos();
//        adapter.add(new ContatoItem(pessoa));
        return view;


    }

    private void buscarContatos() {
//        final Pessoa pessoa = null; //Modificar pra contato
        FirebaseFirestore.getInstance().collection("pessoa").whereEqualTo("UId", pessoa.getUsuario().getUId())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e!=null){
                            Log.i("TestContato", e.getMessage(),e);
                            return;
                        }
                        List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot doc: docs) {
                            Pessoa pessoa1 = doc.toObject(Pessoa.class);
                            adapter.add(new ChatItem(pessoa1));

                        }

                    }
                });
    }

//    @Override
//    public void responde(Map<String, Object> map) {
//        pessoa =(Pessoa) map.get("pessoa");
//    }

    public class ChatItem extends Item<ViewHolder> {
        private  final Pessoa pessoa;

        public ChatItem(Pessoa pessoa) {
            this.pessoa = pessoa;
        }

        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {
            CircleImageView fotoImgView =(CircleImageView)  viewHolder.itemView.findViewById(R.id.fotoImgView);
            TextView nomeUserTView = (TextView) viewHolder.itemView.findViewById(R.id.mensagemTView);
            if (this.pessoa.getNome()!=null) {
                nomeUserTView.setText(this.pessoa.getNome());
                Picasso.get().load(this.pessoa.getFotoFileURL()).resize(350, 350).centerCrop().into(fotoImgView);
            }
        }

        @Override
        public int getLayout() {
            return R.layout.item_contato;
        }
    }

}
