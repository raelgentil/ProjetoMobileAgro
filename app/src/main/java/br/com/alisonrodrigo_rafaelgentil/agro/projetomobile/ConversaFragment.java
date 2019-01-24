package br.com.alisonrodrigo_rafaelgentil.agro.projetomobile;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import java.util.Map;

import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Contato;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Conversa;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Mensagem;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.interfaces.IObserver;
import br.com.alisonrodrigo_rafaelgentil.agro.model.fachada.Fachada;
import br.com.alisonrodrigo_rafaelgentil.agro.model.fachada.IFachada;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.interfaces.IComunicadorInterface;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class ConversaFragment extends Fragment implements IObserver, IComunicadorInterface {
    private GroupAdapter adapter;
    private RecyclerView recyclerView;
    private EditText mensagemText;
    private Button okButton;
    private Conversa conversa;
    private DrawerLayout drawer;
    private IFachada fachada;

//    public ConversaFragment() {
//    }

    @SuppressLint("ValidFragment")
    public ConversaFragment(Conversa conversa, DrawerLayout drawer, IFachada fachada) {
        this.conversa = conversa;
        this.conversa.addObserver(this);

        this.drawer = drawer;
        this.fachada = fachada;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversa, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
//        getContext()setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle(conversa.getContato().getNome());
//        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mensagemText = view.findViewById(R.id.mensagemText);
        okButton = view.findViewById(R.id.okButton);
        mensagemText.setMovementMethod(new ScrollingMovementMethod());
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        adapter = new GroupAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        fachada.receberMensagens(conversa);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String texto = mensagemText.getText().toString();
                if (texto!= null && texto !=""){
                    Mensagem mensagem = new Mensagem();
                    mensagem.setTexto(texto);
                    mensagem.setRecebida(false);
                    mensagem.setVisualizada(false);
                    conversa.getMeuContato().addMensagem(mensagem);
                    mensagemText.setText("");
                    drawer.setFocusable(true);
                    fachada.salvarConersa(conversa, mensagem);

                }
            }
        });

        return view;

    }

    public Conversa getConversa() {
        return conversa;
    }

    @Override
    public void update(Object observado) {
        if (observado instanceof Contato){
            Log.i("TesteMensagem", "Vou criar a mensagem");
            Contato contato = (Contato) observado;
            addMensagem(contato.getMensagens().get((contato.getMensagens().size()-1)));
        }if (observado instanceof Conversa){
            this.conversa = (Conversa)observado;
        }
    }

    private void addMensagem(Mensagem mensagem){
        adapter.add(new MensagemItem(mensagem));
    }


    @Override
    public void responde(Map<String, Object> map) {
//        conversa = (Conversa) map.get("conversa");
//
//        fachada =(Fachada) map.get("fachada");
//        drawer = (DrawerLayout) map.get("drawer_layout");
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
            mensagemTView = (TextView) viewHolder.itemView.findViewById(R.id.nomeTView);
            dataHoraTView = (TextView) viewHolder.itemView.findViewById(R.id.dataHoraTView);
            if (mensagem.isRecebida()){
                fotoVisualizaImgView =(CircleImageView)  viewHolder.itemView.findViewById(R.id.fotoVisualizaImgView);
//                fotoVisualizaImgView
//                Picasso.get().load(this.pessoa.getFotoFileURL()).resize(350, 350).centerCrop().into(fotoImgView);
//Atualizar a imagem
            }
            if (this.mensagem != null) {
                mensagemTView.setText(mensagem.getTexto());
//                dataHoraTView.setText(mensagem.getData().toString());
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
