package br.com.alisonrodrigo_rafaelgentil.agro.projetomobile;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.Usuario;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.interfaces.ComunicadorInterface;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private TextView cadastroTView;
    private EditText loginText;
    private EditText senhaText;
    private Button okButton;
    PerfilFragment perfilFragment;
    ComunicadorInterface comunicadorInterface;





    public LoginFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        cadastroTView = (TextView) view.findViewById(R.id.registrarTView);
        loginText = (EditText) view.findViewById(R.id.loginText);
        senhaText = (EditText) view.findViewById(R.id.senhaText);
        okButton = (Button) view.findViewById(R.id.entrarButton);

        cadastroTView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                abrirCadastro(v);
            }
        });



        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Usuario usuario = new Usuario();
                usuario.setLogin(loginText.getText().toString());
                usuario.setSenha(senhaText.getText().toString());
                Bundle bundle = new Bundle();
                bundle.putSerializable("usuario", usuario);
                comunicadorInterface.responde(bundle);


            }
        });

        return view;
    }


    public void abrirCadastro(View v){

        perfilFragment = new PerfilFragment();


        perfilFragment.setNomeButton(MaskEditUtil.SALVAR);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
        fragmentTransaction.replace (R.id.layoutMainPrincipal, perfilFragment);
        fragmentTransaction.commit ();

    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ComunicadorInterface) {
            comunicadorInterface = (ComunicadorInterface)context;
        } else {
            throw new ClassCastException();
        }

    }

}
