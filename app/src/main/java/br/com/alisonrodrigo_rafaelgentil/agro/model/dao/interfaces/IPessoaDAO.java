package br.com.alisonrodrigo_rafaelgentil.agro.model.dao.interfaces;

import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Pessoa;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Usuario;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.ContatoFragment;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.LoginFragment;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.PerfilFragment;

public interface IPessoaDAO {
    public boolean autenticar(final Pessoa pessoa);
//    public boolean autenticar(Usuario usuario, final LoginFragment fragment);
    public void salvar(final Pessoa pessoa, final PerfilFragment fragment);
    public Pessoa verificarUserLogado(Pessoa pessoa);
    public void pegarDadosPessoa(final Pessoa pessoa);
    public void atualizar(final Pessoa pessoa, final PerfilFragment fragment);

}
