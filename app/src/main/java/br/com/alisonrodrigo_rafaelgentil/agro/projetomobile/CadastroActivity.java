package br.com.alisonrodrigo_rafaelgentil.agro.projetomobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.alisonrodrigo_rafaelgentil.agro.model.dao.ClienteDao;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.Usuario;

public class CadastroActivity extends AppCompatActivity {

    private Button voltarButton;
    private Button cadastrarButton;
    private EditText editNome;
    private EditText editCpf;
    private EditText dataNascimento;
    private EditText editTelefone;
    private EditText editEmail;
    private EditText editUsuario;
    private EditText editSenha;

    private Usuario usuario;
    private ClienteDao clienteDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

    voltarButton = (Button) findViewById(R.id.voltar_button);
    cadastrarButton = (Button) findViewById(R.id.cadastrar_button);


    editNome = (EditText) findViewById(R.id.edit_Nome);
    editCpf = (EditText) findViewById(R.id.edit_Cpf);
    dataNascimento = (EditText) findViewById(R.id.edit_data);
    editTelefone = (EditText) findViewById(R.id.edit_Telefone);
    editEmail = (EditText) findViewById(R.id.edit_Email);
    editUsuario = (EditText) findViewById(R.id.edit_usuario);
    editSenha = (EditText) findViewById(R.id.edit_senha);

    editCpf.addTextChangedListener(MaskEditUtil.mask(editCpf,MaskEditUtil.FORMAT_CPF));
    dataNascimento.addTextChangedListener(MaskEditUtil.mask(dataNascimento, MaskEditUtil.FORMAT_DATE));
    editTelefone.addTextChangedListener(MaskEditUtil.mask(editTelefone, MaskEditUtil.FORMAT_FONE));

    }
    public void cadastrarUsuario(View v){

        clienteDao = new ClienteDao(getBaseContext());

        usuario = new Usuario();
        usuario.setNome(editNome.getText().toString());
        usuario.setCpf(editCpf.getText().toString());
        usuario.setEmail(editEmail.getText().toString());
        usuario.setTelefone(editTelefone.getText().toString());
        usuario.setDataNascimento(dataNascimento.getText().toString());
        usuario.setLogin(editUsuario.getText().toString());
        usuario.setSenha(editSenha.getText().toString());

        String resultado;


        resultado = clienteDao.insertUsuario(usuario);

        //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        voltarTelaLogin(v);

    }

    public boolean verificarCampos(EditText campo){

        if(campo.getText().length() != 0){

            return true;
        }
        return  false;
    }



    public void voltarTelaLogin(View v){

        Intent intent = new Intent(CadastroActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}
