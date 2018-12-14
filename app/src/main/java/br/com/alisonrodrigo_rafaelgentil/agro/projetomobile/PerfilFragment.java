package br.com.alisonrodrigo_rafaelgentil.agro.projetomobile;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
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
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.UUID;

import br.com.alisonrodrigo_rafaelgentil.agro.model.dao.ClienteDao;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.Usuario;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {

    private ClienteDao clienteDao;

    private Usuario usuario;
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
    private Uri mSelectUri;


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
        okButton.setText(nomeButton);

        cpfText.addTextChangedListener(MaskEditUtil.mask(cpfText,MaskEditUtil.FORMAT_CPF));
        data_nascText.addTextChangedListener(MaskEditUtil.mask(data_nascText, MaskEditUtil.FORMAT_DATE));
        telefText.addTextChangedListener(MaskEditUtil.mask(telefText, MaskEditUtil.FORMAT_FONE));


        atualizarCampos();

        clienteDao = new ClienteDao(view.getContext());


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
                    abilitarDesabilitarCampos(false);
                    okButton.setText(MaskEditUtil.EDITAR);
                }

            }
        });


        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (okButton.getText().toString().equals(MaskEditUtil.SALVAR)){
                    salvar();
                    voltarTelaLogin();
                }
                if (okButton.getText().toString().equals(MaskEditUtil.ATUALIZAR)){
                    atualizar();
                }
                if (okButton.getText().toString().equals(MaskEditUtil.EDITAR)){
                    abilitarDesabilitarCampos(true);
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
//        startActivityForResult(intent, 0);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 0);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0){
            mSelectUri = data.getData();
            Bitmap bitmap = null;

            try {
               bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), mSelectUri);
               fotoImgView.setImageDrawable(new BitmapDrawable(bitmap));
               selectImgButton.setAlpha(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void salvar(){


        usuario = new Usuario();
        usuario.setNome(nomeText.getText().toString());
        usuario.setCpf(cpfText.getText().toString());
        usuario.setEmail(emailText.getText().toString());
        usuario.setTelefone(telefText.getText().toString());
        usuario.setDataNascimento(data_nascText.getText().toString());
        usuario.setLogin(loginText.getText().toString());
        usuario.setSenha(senhaText.getText().toString());

        String resultado;
//        resultado = clienteDao.insertUsuario(usuario);

//        FirebaseApp.initializeApp(this);
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.i("Teste", task.getResult().getUser().getUid());
                            clienteDao.insertUsuario(usuario);
                            salvarFoto();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Teste", e.getMessage());
                    }
                });

        Toast.makeText(getContext(),"Usuário salvo com sucesso!", Toast.LENGTH_LONG).show();
//        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

    }

    private void salvarFoto() {
        String fileName = UUID.randomUUID().toString();
        final StorageReference ref = FirebaseStorage.getInstance().getReference("/images/" + fileName);
        ref.putFile(mSelectUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String uid = FirebaseAuth.getInstance().getUid();
                                String fotoFileURL = uri.toString();

                                usuario.setFotoFileURL(fotoFileURL);
                                Log.i("TesteFoto", fotoFileURL);
                                salvarUser();

                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                    Log.i("Teste", e.getMessage(), e);
            }
        });

    }

    private void salvarUser() {
        FirebaseFirestore.getInstance().collection("user").add(usuario)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.i("TesteUser", documentReference.getId());
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Teste", e.getMessage());
            }
        });
    }


    private void atualizar(){
        usuario.setNome(nomeText.getText().toString());
        usuario.setCpf(cpfText.getText().toString());
        usuario.setEmail(emailText.getText().toString());
        usuario.setTelefone(telefText.getText().toString());
        usuario.setDataNascimento(data_nascText.getText().toString());
        usuario.setLogin(loginText.getText().toString());
        usuario.setSenha(senhaText.getText().toString());


        if(clienteDao.updateUsuario(usuario)){

            Toast.makeText(getContext(),"Usuário alterado com sucesso!", Toast.LENGTH_LONG).show();

        }
    }


    public void abilitarDesabilitarCampos(boolean ativar){
        nomeText.setEnabled(ativar);
        cpfText.setEnabled(ativar);
        emailText.setEnabled(ativar);
        telefText.setEnabled(ativar);
        data_nascText.setEnabled(ativar);
        loginText.setEnabled(ativar);
        senhaText.setEnabled(ativar);
    }
    public void voltarTelaLogin(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        LoginFragment loginFragment = new LoginFragment();
//        loginFragment.setActivityListener(getActivity());
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
        fragmentTransaction.replace (R.id.layoutMainPrincipal, loginFragment);
        fragmentTransaction.commit ();

    }

    public void setNomeButton(String nomeButton){
        this.nomeButton = nomeButton;
    }

    public void atualizarCampos(){
        if (usuario != null){
            if (okButton.getText().toString().equals(MaskEditUtil.EDITAR)){
                abilitarDesabilitarCampos(false);
            }
            selectImgButton.setAlpha(0);
//            Picasso.with(v.getContext()).load(txtUrl.getText().toString()).into(imvImagem);
            Picasso.get().load(usuario.getFotoFileURL()).into(fotoImgView);
            nomeText.setText(usuario.getNome());
            cpfText.setText(usuario.getCpf());
            emailText.setText(usuario.getEmail());
            telefText.setText(usuario.getTelefone());
            data_nascText.setText(usuario.getDataNascimento());
            loginText.setText(usuario.getLogin());
            senhaText.setText(usuario.getSenha());

        }

    }
    public Usuario receberUsuario(Usuario usuario){

        this.usuario= usuario;


        return  this.usuario;
    }

}
