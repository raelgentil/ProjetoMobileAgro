package br.com.alisonrodrigo_rafaelgentil.agro.model.business.classes;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

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
    public boolean autenticar(Usuario usuario, LoginFragment fragment) {
        return pessoaDAO.autenticar(usuario, fragment);
    }

    @Override
    public void salvar(Pessoa pessoa, PerfilFragment fragment) {
        pessoaDAO.salvar(pessoa,fragment);
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
