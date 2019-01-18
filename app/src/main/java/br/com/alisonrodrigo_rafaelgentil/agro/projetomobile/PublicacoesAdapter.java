package br.com.alisonrodrigo_rafaelgentil.agro.projetomobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Publicacao;

public class PublicacoesAdapter extends ArrayAdapter<Publicacao> {

    private ArrayList<Publicacao> publicacao;
    private  Context context;

    public PublicacoesAdapter(Context context, ArrayList<Publicacao> objects) {

        super(context, 0,objects);
        this.context = context;
        this.publicacao = objects;
    }


    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {

        View view = null;

        if(publicacao != null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.lista_publicacoes, parent, false);

            TextView textView_descricao = (TextView) view.findViewById(R.id.textview_descricao);
            TextView textView_data = (TextView) view.findViewById(R.id.textview_data);

            Publicacao publicacao1 = publicacao.get(position);

            textView_descricao.setText(publicacao1.getDescricao().toString());
            textView_data.setText(publicacao1.getData_publicacao().toString());
        }

       return  view;
    }
}
