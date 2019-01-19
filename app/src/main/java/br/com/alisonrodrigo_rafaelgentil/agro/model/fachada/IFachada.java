package br.com.alisonrodrigo_rafaelgentil.agro.model.fachada;

import java.io.Serializable;

import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Pessoa;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Usuario;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.ContatoFragment;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.LoginFragment;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.PerfilFragment;

public interface IFachada extends Serializable {
    public boolean verificarUserLogado(Pessoa pessoa);
    public boolean autenticarUsuario(Usuario usuario, final LoginFragment fragment);
    public void salvarPessoa(final Pessoa pessoa, final PerfilFragment fragment);
    public void buscarContatos(String busca, final ContatoFragment fragment);
}
