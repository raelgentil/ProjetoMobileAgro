package br.com.alisonrodrigo_rafaelgentil.agro.projetomobile;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.xwray.groupie.ViewHolder;

import java.util.List;
import java.util.Map;

import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Pessoa;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.interfaces.ComunicadorInterface;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContatoFragment extends Fragment implements ComunicadorInterface {

private GroupAdapter adapter;
private Pessoa pessoa;
    public ContatoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contato, container, false);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.contatosRecyclerView);
        adapter = new GroupAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Log.i("Teste Agora Bora", pessoa.getNome());
//        buscarContatos();
        adapter.add(new ContatoItem(pessoa));
        adapter.add(new ContatoItem(pessoa));
        adapter.add(new ContatoItem(pessoa));
        adapter.add(new ContatoItem(pessoa));
        adapter.add(new ContatoItem(pessoa));
        adapter.add(new ContatoItem(pessoa));
        adapter.add(new ContatoItem(pessoa));
        adapter.add(new ContatoItem(pessoa));

        return view;

        
    }



    @Override
    public void responde(Map<String, Object> map) {
        pessoa =(Pessoa) map.get("pessoa");
    }

    public class ContatoItem extends Item<ViewHolder>{
        private  final Pessoa pessoa1;
        CircleImageView fotoImgView;
        TextView nomeUserTView;

        public ContatoItem(Pessoa pessoa) {
            this.pessoa1 = pessoa;
        }
        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {
            Log.i("TesteContato", "Deu ceto tb");
            fotoImgView =(CircleImageView)  viewHolder.itemView.findViewById(R.id.fotoImgView);
            nomeUserTView = (TextView) viewHolder.itemView.findViewById(R.id.mensagemTView);
            if (this.pessoa1.getNome()!=null) {
                nomeUserTView.setText(this.pessoa1.getNome());
                Picasso.get().load(this.pessoa1.getFotoFileURL()).resize(350, 350).centerCrop().into(fotoImgView);
            }
        }

        @Override
        public int getLayout() {
            return R.layout.item_contato;
        }
    }

}
