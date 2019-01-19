package br.com.alisonrodrigo_rafaelgentil.agro.projetomobile;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Map;

import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Contato;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Conversa;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Mensagem;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Pessoa;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.interfaces.IObserver;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.interfaces.IComunicadorInterface;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConversaFragment extends Fragment implements IObserver, IComunicadorInterface {
    private GroupAdapter adapter;
    private Contato contato;
    private Contato meuContato;

    private RecyclerView recyclerView;
    private EditText mensagemText;
    private Button okButton;
    private Conversa conversa;

    public ConversaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversa, container, false);

        mensagemText = view.findViewById(R.id.mensagemText);
        okButton = view.findViewById(R.id.okButton);
        mensagemText.setMovementMethod(new ScrollingMovementMethod());
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        adapter = new GroupAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String texto = mensagemText.getText().toString();
                if (texto!= null && texto !=""){
                    Mensagem mensagem = new Mensagem();
                    mensagem.setTexto(texto);
                    mensagem.setRecebida(false);
                    mensagem.setVisualizada(false);

                    conversa.addMensagem(mensagem);
                }
            }
        });
//        adapter
//        Log.i("Teste Agora Bora", pessoa.getNome());
//        buscar();
//        adapter.add(new ContatoItem(pessoa));
        return view;

    }

    public Conversa getConversa() {
        return conversa;
    }

    @Override
    public void update(Object observado) {
        if (observado instanceof Mensagem){
            Mensagem mensagem = (Mensagem) observado;
            addMensagem(mensagem);
        }
    }

    private void addMensagem(Mensagem mensagem){
        adapter.add(new MensagemItem(mensagem));
    }

    @Override
    public void responde(Map<String, Object> map) {
        conversa = (Conversa) map.get("conversa");
        conversa.addObserver(this);
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
