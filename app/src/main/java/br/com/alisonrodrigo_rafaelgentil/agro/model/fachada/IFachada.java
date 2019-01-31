package br.com.alisonrodrigo_rafaelgentil.agro.model.fachada;

import java.io.Serializable;

import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Chat;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Conversa;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Mensagem;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Pessoa;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.ChatFragment;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.ContatoFragment;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.PerfilFragment;

public interface IFachada extends Serializable {
    public boolean verificarUserLogado(Pessoa pessoa);
    public boolean autenticarUsuario(final Pessoa pessoa);
    public void salvarPessoa(final Pessoa pessoa, final PerfilFragment fragment);
    public void buscarContatos(String busca, final ContatoFragment fragment);
    public void receberMensagens(final Conversa conversa);
    public void pegarConversa(final Conversa conversa);

    public void salvarConersa(Conversa conversa, Mensagem mensagem);

    public void pegarConversas(ChatFragment chatFragment);

    public void listarConversas(Chat chat);
}
