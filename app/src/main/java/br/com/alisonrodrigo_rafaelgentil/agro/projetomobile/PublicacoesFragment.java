package br.com.alisonrodrigo_rafaelgentil.agro.projetomobile;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class PublicacoesFragment extends Fragment {


    private Button buttonPublicar;
    private PublicacoesUser publicacoesUser;

    public PublicacoesFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_publicacoes, container, false);


        buttonPublicar = (Button) view.findViewById(R.id.button_publicar);

        buttonPublicar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                publicacoesUser = new PublicacoesUser();
                fragmentManager.beginTransaction().replace(R.id.layoutPrincipal, publicacoesUser).commit();
            }

        });

        return  view;
    }

}
