package br.com.alisonrodrigo_rafaelgentil.agro.projetomobile;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.com.alisonrodrigo_rafaelgentil.agro.model.dao.PublicacaoDao;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Pessoa;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Publicacao;


/**
 * A simple {@link Fragment} subclass.
 */
public class PublicacoesUser extends Fragment {

    private Button postarButton;
    private EditText edir_descricao;
    private Publicacao publicacao;
    private PublicacaoDao publicacaoDao;
    private SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");
    private Date data;
    public PublicacoesUser() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    View view =  inflater.inflate(R.layout.fragment_publicacao_user, container, false);

    postarButton = (Button) view.findViewById(R.id.postar_button);

    edir_descricao = (EditText) view.findViewById(R.id.edit_descricao);

    publicacaoDao = new PublicacaoDao();

    postarButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            publicacao = new Publicacao();
            data = new Date();
            String dataFormatada = formataData.format(data);

            publicacao.setDescricao(edir_descricao.getText().toString());
            publicacao.setData_publicacao(dataFormatada);

            publicacaoDao.salvarPublicao(publicacao);
        }
    });


        return  view;
    }


}
