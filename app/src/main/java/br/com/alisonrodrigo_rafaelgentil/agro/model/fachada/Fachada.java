package br.com.alisonrodrigo_rafaelgentil.agro.model.fachada;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;

import br.com.alisonrodrigo_rafaelgentil.agro.model.business.classes.ContatoBusiness;
import br.com.alisonrodrigo_rafaelgentil.agro.model.business.classes.ConversaBusiness;
import br.com.alisonrodrigo_rafaelgentil.agro.model.business.classes.PessoaBusiness;
import br.com.alisonrodrigo_rafaelgentil.agro.model.business.interfaces.IContatoBusiness;
import br.com.alisonrodrigo_rafaelgentil.agro.model.business.interfaces.IConversaBusiness;
import br.com.alisonrodrigo_rafaelgentil.agro.model.business.interfaces.IPessoaBusiness;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Conversa;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Mensagem;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Pessoa;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Usuario;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.ContatoFragment;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.LoginFragment;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.PerfilFragment;

public class Fachada implements IFachada, Serializable{
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private IPessoaBusiness pessoaBusiness;
    private IContatoBusiness contatoBusiness;
    private IConversaBusiness conversaBusiness;

    public Fachada() {
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        this.pessoaBusiness = new PessoaBusiness(auth, firestore);
        this.contatoBusiness = new ContatoBusiness(firestore);
        this.conversaBusiness = new ConversaBusiness(auth, firestore);

    }

    @Override
    public boolean verificarUserLogado(Pessoa pessoa) {
        return pessoaBusiness.verificarUserLogado(pessoa);
    }

    public boolean autenticarUsuario(final Pessoa pessoa){
        return pessoaBusiness.autenticar(pessoa);
    }
    public void salvarPessoa(final Pessoa pessoa, final PerfilFragment fragment){
        pessoaBusiness.salvar(pessoa, fragment);
    }
    public void buscarContatos(String busca, final ContatoFragment fragment){
        contatoBusiness.buscar(busca, fragment);
    }

    @Override
    public void receberMensagens(Conversa conversa) {
        conversaBusiness.receberDados(conversa);
    }

    @Override
    public void salvarConersa(Conversa conversa, Mensagem mensagem) {
        this.conversaBusiness.salvarOuAtualizar(conversa, mensagem);
    }
}
