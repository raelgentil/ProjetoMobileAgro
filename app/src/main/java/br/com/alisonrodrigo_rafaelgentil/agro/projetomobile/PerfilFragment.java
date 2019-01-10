package br.com.alisonrodrigo_rafaelgentil.agro.projetomobile;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Map;
import java.util.UUID;

import br.com.alisonrodrigo_rafaelgentil.agro.model.dao.PessoaDAO;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Pessoa;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.interfaces.Observer;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.interfaces.ComunicadorInterface;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment implements Observer, ComunicadorInterface {

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
//    private Uri mSelectUri;


    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

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
                    PessoaDAO p = new PessoaDAO();
                    p.salvar(pessoa, PerfilFragment.this);
                }
                if (okButton.getText().toString().equals(MaskEditUtil.ATUALIZAR)){
                    atualizar();
                }
                if (okButton.getText().toString().equals(MaskEditUtil.EDITAR)){
                    abilitarCampos(true);
                    okButton.setText(MaskEditUtil.ATUALIZAR);
                }
            }
        });
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

//    private void salvar(){
//        pessoa = pegarDadosTela();
//        FirebaseAuth.getInstance().createUserWithEmailAndPassword(pessoa.getUsuario().getEmail(), pessoa.getUsuario().getSenha())
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful()){
//                            Log.i("Teste", task.getResult().getUser().getUid());
//                            pessoa.getUsuario().setUId(task.getResult().getUser().getUid());
//                            Log.i("Teste", pessoa.getUsuario().getUId());
//                            salvarFoto();
//                        }else{
//                            String erroException="";
//                            try {
//                                throw task.getException();
//                            } catch (FirebaseAuthWeakPasswordException e) {
//                                erroException = "Digite uma senha mais forte, contendo no minimo 8 caracter";
//                            }catch (FirebaseAuthInvalidCredentialsException e) {
//                                erroException = "O e-mail digitado é invalido, digite outro e-mail";
//                            }catch (FirebaseAuthUserCollisionException e) {
//                                erroException = "Esse e-mail ja foi cadastrado no sistema, digite outro e-mail";
//                            }catch (Exception e) {
//                                erroException = "Erro ao Cadastrar Usuario" ;
//                            }
//                            Toast.makeText(getContext().getApplicationContext(), "Erro: " + erroException, Toast.LENGTH_LONG).show();
//                        }
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.i("Teste", e.getMessage());
//                    }
//                });
//    }

//    private void salvarFoto() {
//        String fileName = UUID.randomUUID().toString();
//        final StorageReference ref = FirebaseStorage.getInstance().getReference("/images/perfil" + fileName);
//        ref.putFile(pessoa.getmSelectUri())
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//                                String uid = FirebaseAuth.getInstance().getUid();
//                                String fotoFileURL = uri.toString();
//
//                                pessoa.setFotoFileURL(fotoFileURL);
//                                Log.i("TesteFoto", fotoFileURL);
//                                salvarPessoa();
//                            }
//                        });
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                    Log.i("Teste", e.getMessage(), e);
//            }
//        });
//    }

//    private void salvarPessoa() {
//        FirebaseFirestore.getInstance().collection("pessoa").add(pessoa.getMap())
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.i("TesteUser", documentReference.getId());
//                        pessoa.setUId(documentReference.getId());
//                        Toast.makeText(getContext(),"Usuário salvo com sucesso!", Toast.LENGTH_LONG).show();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.i("Teste", e.getMessage());
//                Toast.makeText(getContext(),"Erro ao salvar Usuario!", Toast.LENGTH_LONG).show();
//            }
//        });
//    }

    private void atualizar(){
        pegarDadosTela();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        currentUser.updateEmail(pessoa.getUsuario().getEmail()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                FirebaseFirestore.getInstance().collection("pessoa").document(pessoa.getUId()).set(pessoa.getMap());

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
    }

    private void voltarTelaLogin(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        LoginFragment loginFragment = new LoginFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
        fragmentTransaction.replace (R.id.layoutMainPrincipal, loginFragment);
        fragmentTransaction.addToBackStack(null);
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

    @Override
    public void responde(Map<String, Object> map) {
//        okButton.setText((String)map.get("nomeButton"));
        nomeButton = (String)map.get("nomeButton");
        pessoa = (Pessoa) map.get("pessoa");
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
