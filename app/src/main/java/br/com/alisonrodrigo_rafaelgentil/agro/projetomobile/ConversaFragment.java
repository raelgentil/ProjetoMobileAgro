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
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Mensagem;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Pessoa;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.interfaces.Observer;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConversaFragment extends Fragment implements Observer{
    private GroupAdapter adapter;
    private Pessoa pessoa;
    List<Mensagem> mensagens;


    public ConversaFragment() {
        this.mensagens = new ArrayList<>();
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversa, container, false);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        adapter = new GroupAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//        adapter
//        Log.i("Teste Agora Bora", pessoa.getNome());
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


                        }

                    }
                });
    }

    @Override
    public void update(Object observado) {

    }

    public void addMensagem(Mensagem mensagem){
        this.mensagens.add(mensagem);
        adapter.add(new MensagemItem(mensagem));

    }


    public class MensagemItem extends Item<ViewHolder> {
        private  final Mensagem mensagem;
        private CircleImageView fotoVisualizaImgView;
        private  TextView mensagemTView;
        TextView dataHoraTView;
        public MensagemItem(Mensagem mensagem) {
            this.mensagem = mensagem;
        }


        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {
            mensagemTView = (TextView) viewHolder.itemView.findViewById(R.id.mensagemTView);
            dataHoraTView = (TextView) viewHolder.itemView.findViewById(R.id.dataHoraTView);
            if (mensagem.isRecebida()){
                fotoVisualizaImgView =(CircleImageView)  viewHolder.itemView.findViewById(R.id.fotoVisualizaImgView);
//                fotoVisualizaImgView
//                Picasso.get().load(this.pessoa.getFotoFileURL()).resize(350, 350).centerCrop().into(fotoImgView);
//Atualizar a imagem
            }
            if (this.mensagem != null) {
                mensagemTView.setText(mensagem.getTexto());
                dataHoraTView.setText(mensagem.getData().toString());
            }
        }

        @Override
        public int getLayout() {
            if (mensagem.isRecebida()){return R.layout.mensagem_recebida;}
            else{return R.layout.mensagem_enviada;}
        }

        public void atualizarVisualizado() {
            if (mensagem.isRecebida()){
//                atualizar imagem de visualizado
            }
        }
    }

}
