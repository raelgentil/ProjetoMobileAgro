package br.com.alisonrodrigo_rafaelgentil.agro.model.business.classes;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import br.com.alisonrodrigo_rafaelgentil.agro.model.business.interfaces.IPessoaBusiness;
import br.com.alisonrodrigo_rafaelgentil.agro.model.dao.classes.PessoaDAO;
import br.com.alisonrodrigo_rafaelgentil.agro.model.dao.interfaces.IPessoaDAO;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Pessoa;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Usuario;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.ContatoFragment;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.LoginFragment;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.PerfilFragment;

public class PessoaBusiness implements IPessoaBusiness {

    private IPessoaDAO pessoaDAO;
    public PessoaBusiness(FirebaseAuth auth, FirebaseFirestore firestore) {
        this.pessoaDAO = new PessoaDAO(auth, firestore);
    }

    @Override
    public boolean autenticar(final Pessoa pessoa) {

        if (isEmailValid(pessoa.getUsuario().getEmail()) && isSenhaValid(pessoa.getUsuario().getSenha())){
            return pessoaDAO.autenticar(pessoa);
        }else{
//            Map<String, Object> map = new HashMap<>();
//            map.put("show", true);
//            map.put("mensagem", "E-mail ou senha incorreto");
//            fragment.responde(map);
//            Toast.makeText(fragment.getContext().getApplicationContext(), "E-mail ou senha incorreto" , Toast.LENGTH_LONG).show();
//            showProgress(false);
//            okButton.setEnabled(true);
//            cadastroTView.setEnabled(true);
            return false;
        }
    }
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isSenhaValid(String senha) {
        //TODO: Replace this with your own logic
        return senha.length() > 5;
    }

    @Override
    public void salvar(Pessoa pessoa, PerfilFragment fragment) {
        if (pessoa.getUsuario().getUId()!=null & pessoa.getUsuario().getUId()!=""){
            pessoaDAO.atualizar(pessoa, fragment);
        }else{

            pessoaDAO.salvar(pessoa,fragment);
        }

    }

    @Override
    public boolean verificarUserLogado(Pessoa pessoa) {
        pessoa = pessoaDAO.verificarUserLogado(pessoa);
        Log.i("TesteAutenticacao   :", "" + pessoa.getUsuario());
        if (pessoa.getUsuario().getUId()!=null){
            pegarDadosPessoa(pessoa);
            return true;
        }else{
            Log.i("TesteAutenticacao   :", "Vou entrar aq");
            pessoa.notifyObservers();
            return false;
        }

    }

    private void pegarDadosPessoa(final Pessoa pessoa){
        pessoaDAO.pegarDadosPessoa(pessoa);
    }

    //Lembrar de criptografar a senha por aq
}
