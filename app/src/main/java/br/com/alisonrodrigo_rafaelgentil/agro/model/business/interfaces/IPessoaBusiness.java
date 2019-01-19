package br.com.alisonrodrigo_rafaelgentil.agro.model.business.interfaces;

import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Pessoa;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Usuario;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.ContatoFragment;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.LoginFragment;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.PerfilFragment;

public interface IPessoaBusiness {

    public boolean autenticar(Usuario usuario, final LoginFragment fragment);
    public void salvar(final Pessoa pessoa, final PerfilFragment fragment);
    public boolean verificarUserLogado(Pessoa pessoa);


}
