package br.com.alisonrodrigo_rafaelgentil.agro.projetomobile;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.Map;

import br.com.alisonrodrigo_rafaelgentil.agro.model.dao.classes.PessoaDAO;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Pessoa;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.interfaces.IObserver;
import br.com.alisonrodrigo_rafaelgentil.agro.model.fachada.Fachada;
import br.com.alisonrodrigo_rafaelgentil.agro.model.fachada.IFachada;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.interfaces.IComunicadorInterface;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class PerfilFragment extends Fragment implements IObserver, IComunicadorInterface {

    private Pessoa pessoa;
    private EditText nomeText;
    private EditText cpfText;
    private EditText emailText;
    private EditText telefText;
    private EditText data_nascText;
    private EditText loginText;
    private EditText senhaText;
    private Button okButton;
    private Button cancelarButton;
    private Button selectImgButton;
    private CircleImageView fotoImgView;
    private String nomeButton;
    private Activity activity;
    private View progressView;
    private  DrawerLayout drawer;
    private IFachada fachada;
//    private String

//    public PerfilFragment() {
//        // Required empty public constructor
//    }

    @SuppressLint("ValidFragment")
    public PerfilFragment(DrawerLayout drawer, IFachada fachada) {
        this.drawer = drawer;
        this.fachada = fachada;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);



        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
//        getContext()setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
//        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        nomeText = (EditText) view.findViewById(R.id.nomeText);
        cpfText = (EditText) view.findViewById(R.id.cpfText);
        emailText = (EditText) view.findViewById(R.id.emailText);
        telefText = (EditText) view.findViewById(R.id.telefText);
        data_nascText = (EditText) view.findViewById(R.id.data_nascText);
        loginText = (EditText) view.findViewById(R.id.loginText);
        senhaText = (EditText) view.findViewById(R.id.senhaText);
        okButton = (Button) view.findViewById(R.id.okButton);
        cancelarButton = (Button) view.findViewById(R.id.cancelarButton);
        selectImgButton = (Button) view.findViewById(R.id.selectImgButton);
        fotoImgView = (CircleImageView)view.findViewById(R.id.fotoImgView);
        progressView = view.findViewById(R.id.progressBar);
        okButton.setText(nomeButton);


        cpfText.addTextChangedListener(MaskEditUtil.mask(cpfText,MaskEditUtil.FORMAT_CPF));
        data_nascText.addTextChangedListener(MaskEditUtil.mask(data_nascText, MaskEditUtil.FORMAT_DATE));
        telefText.addTextChangedListener(MaskEditUtil.mask(telefText, MaskEditUtil.FORMAT_FONE));
//        atualizarCampos();
        showProgress(false);
        if (pessoa.getNome() != null){
            pessoa.addObserver(this);
            pessoa.notifyObservers();
        }

        selectImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecionarFoto();
            }
        });

        cancelarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (okButton.getText().toString().equals(MaskEditUtil.SALVAR)){
                    voltarTelaLogin();
                }
                if (okButton.getText().toString().equals(MaskEditUtil.ATUALIZAR)){
                    abilitarCampos(false);
                    okButton.setText(MaskEditUtil.EDITAR);
                }
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (okButton.getText().toString().equals(MaskEditUtil.SALVAR)){
                    showProgress(true);
                    pegarDadosTela();
                    abilitarCampos(false);
//                    PessoaDAO p = new PessoaDAO();
//                    p.salvar(pessoa, PerfilFragment.this);
                    fachada.salvarPessoa(pessoa, PerfilFragment.this);
                }
                if (okButton.getText().toString().equals(MaskEditUtil.ATUALIZAR)){
                    atualizar();
                }
                if (okButton.getText().toString().equals(MaskEditUtil.EDITAR)){

                    okButton.setText(MaskEditUtil.ATUALIZAR);
                }
            }
        });

//        esconderTecladoHideKeyboard(view);
        return view;
    }

    private void selecionarFoto() {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("Vou Imprimir o ResultCode: " + resultCode);
        if (requestCode == 0 && data != null){
//            mSelectUri = data.getData();
            pessoa.setmSelectUri(data.getData());
            selectImgButton.setAlpha(0);
            Picasso.get().load(pessoa.getmSelectUri()).resize(350, 350).centerCrop().into(fotoImgView);
        }
    }

    private void atualizar(){
        pegarDadosTela();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        currentUser.updateEmail(pessoa.getUsuario().getEmail()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                fachada.salvarPessoa(pessoa, PerfilFragment.this);
                Toast.makeText(getContext(),"Usuario atualizado com sucesso!", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"Erro ao atualizar Usuario!", Toast.LENGTH_LONG).show();
            }
        });
        currentUser.updatePassword(pessoa.getUsuario().getSenha());


    }

    private Pessoa pegarDadosTela(){
        pessoa.setNome(nomeText.getText().toString());
        pessoa.setCpf(cpfText.getText().toString());
        pessoa.setTelefone(telefText.getText().toString());
        pessoa.setDataNascimento(data_nascText.getText().toString());
        pessoa.setLogin(loginText.getText().toString());
        pessoa.getUsuario().setSenha(senhaText.getText().toString());
        pessoa.getUsuario().setEmail(emailText.getText().toString());
//        if (pessoa.getmSelectUri()==null)
        return pessoa;
    }

    private void abilitarCampos(boolean ativar){
        nomeText.setEnabled(ativar);
        cpfText.setEnabled(ativar);
        emailText.setEnabled(ativar);
        telefText.setEnabled(ativar);
        data_nascText.setEnabled(ativar);
        loginText.setEnabled(ativar);
        senhaText.setEnabled(ativar);
        selectImgButton.setEnabled(ativar);
        okButton.setEnabled(ativar);
        cancelarButton.setEnabled(ativar);
    }

    public void setFachada(IFachada fachada) {
        this.fachada = fachada;
    }

    public void setDrawer(DrawerLayout drawer) {
        this.drawer = drawer;
    }

    private void voltarTelaLogin(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        LoginFragment loginFragment = new LoginFragment();
        loginFragment.setFachada(fachada);
        loginFragment.setDrawer(drawer);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
        fragmentTransaction.replace (R.id.layoutMainPrincipal, loginFragment);
//        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit ();
    }

    @Override
    public void update(Object observado) {
        pessoa = (Pessoa) observado;
        if (pessoa != null){
            if (okButton.getText().toString().equals(MaskEditUtil.EDITAR)){
                abilitarCampos(false);
            }
            nomeText.setText(pessoa.getNome());
            cpfText.setText(pessoa.getCpf());
            emailText.setText(pessoa.getUsuario().getEmail());
            telefText.setText(pessoa.getTelefone());
            data_nascText.setText(pessoa.getDataNascimento());
            loginText.setText(pessoa.getLogin());
            senhaText.setText(pessoa.getUsuario().getSenha());
            if (pessoa.getFotoFileURL()==null){
                selectImgButton.setAlpha(1);
            }else{

                selectImgButton.setAlpha(0);
                Picasso.get().load(pessoa.getFotoFileURL()).resize(350, 350).centerCrop().into(fotoImgView);
            }

        }
    }
//    private void esconderTecladoHideKeyboard(View v) {
//        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.perfil_activy, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void responde(Map<String, Object> map) {
//        okButton.setText((String)map.get("nomeButton"));
        nomeButton = (String)map.get("nomeButton");
        pessoa = (Pessoa) map.get("pessoa");
        fachada = (Fachada) map.get("fachada");
//        drawer = (DrawerLayout) map.get("drawer_layout");
        String mensagem = (String)map.get("mensagem");

            if (mensagem!= null && mensagem !="") {
                if (okButton.getText().toString().equals(MaskEditUtil.SALVAR)){
                Toast.makeText(getContext(), mensagem, Toast.LENGTH_LONG).show();
                    showProgress(false);
                    abilitarCampos(false);
                    voltarTelaLogin();
                    FirebaseAuth.getInstance().signOut();
            }
        }
    }


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

}
