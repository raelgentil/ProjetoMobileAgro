package br.com.alisonrodrigo_rafaelgentil.agro.model.dao;

import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Pessoa;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Usuario;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.ContatoFragment;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.LoginFragment;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.PerfilFragment;

public interface IPessoaDAO {
    public boolean autenticarUsuario(Usuario usuario, final LoginFragment fragment);
    public void salvar(final Pessoa pessoa, final PerfilFragment fragment);
    public void buscarContatos(String busca, final ContatoFragment fragment);
}
