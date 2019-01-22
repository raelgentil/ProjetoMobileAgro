package br.com.alisonrodrigo_rafaelgentil.agro.projetomobile;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.Map;

import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Pessoa;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Usuario;
import br.com.alisonrodrigo_rafaelgentil.agro.model.fachada.Fachada;
import br.com.alisonrodrigo_rafaelgentil.agro.model.fachada.IFachada;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.interfaces.IComunicadorInterface;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements IComunicadorInterface {

    private TextView cadastroTView;
    private Button okButton;
    private PerfilFragment perfilFragment;
    private IComunicadorInterface iComunicadorInterface;
    private EditText loginText;
    private EditText senhaText;
    private View progressView;
    private DrawerLayout drawer;
   private IFachada fachada;

    public LoginFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        cadastroTView = (TextView) view.findViewById(R.id.registrarTView);
        loginText = (EditText) view.findViewById(R.id.loginText);
        senhaText = (EditText) view.findViewById(R.id.senhaText);
        okButton = (Button) view.findViewById(R.id.entrarButton);
        progressView = view.findViewById(R.id.login_progress);
        cadastroTView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCadastro(v);
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgress(true);
                okButton.setEnabled(false);
                cadastroTView.setEnabled(false);
                Usuario u = new Usuario();
                u.setEmail(loginText.getText().toString());
                u.setSenha(senhaText.getText().toString());
//                autenticar(u);
//                PessoaDAO pessoaDAO = new PessoaDAO();
//                pessoaDAO.autenticar(u, );

                fachada.autenticarUsuario(u,LoginFragment.this);
                }
        });
        return view;
    }


    public void abrirCadastro(View v){
        perfilFragment = new PerfilFragment(drawer,fachada);
        Map<String, Object> map = new HashMap<>();
        map.put("nomeButton", MaskEditUtil.SALVAR);
        map.put("pessoa", new Pessoa());
        map.put("fachada", fachada);
//        map.put("drawer_layout", drawer);
        perfilFragment.responde(map);
//        perfilFragment.setFachada(fachada);
//        perfilFragment.setDrawer(drawer);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
        fragmentTransaction.replace (R.id.layoutMainPrincipal, perfilFragment);
//        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit ();

    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IComunicadorInterface) {
            iComunicadorInterface = (IComunicadorInterface)context;
        } else {
            throw new ClassCastException();
        }

    }

    public void setFachada(IFachada fachada) {
        this.fachada = fachada;
    }

    public void setDrawer(DrawerLayout drawer) {
        this.drawer = drawer;
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isSenhaValid(String senha) {
        //TODO: Replace this with your own logic
        return senha.length() > 5;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);

        }
    }

    @Override
    public void responde(Map<String, Object> map) {
        boolean show = (boolean) map.get("show");
        String mensagem = (String) map.get("mensagem");
        Pessoa pessoa = (Pessoa) map.get("pessoa");
//        fachada = (Fachada)map.get("fachada");
//        drawer = (DrawerLayout) map.get("drawer_layout");
        if ( mensagem != null && mensagem !="" ){
            Toast.makeText(getContext().getApplicationContext(), mensagem , Toast.LENGTH_LONG).show();
        }
        if ( pessoa!=null ){
            map = new HashMap<>();
            map.put("pessoa", pessoa);
            iComunicadorInterface.responde(map);
        }
            showProgress(!show);
            okButton.setEnabled(show);
            cadastroTView.setEnabled(show);


    }


}
