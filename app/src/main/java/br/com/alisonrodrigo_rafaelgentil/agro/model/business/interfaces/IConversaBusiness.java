package br.com.alisonrodrigo_rafaelgentil.agro.model.business.interfaces;

import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Chat;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Conversa;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Mensagem;
import br.com.alisonrodrigo_rafaelgentil.agro.projetomobile.ChatFragment;

public interface IConversaBusiness {
    public void salvarOuAtualizar(final Conversa conversa, Mensagem mensagem);
    public void receberDados(final Conversa conversa);
    public void pegarDadosConversa(final Conversa conversa);
    public void pegarConversa(final Conversa conversa);
    public void pegarConversas(ChatFragment chatFragment);

    public void listarConversas(Chat chat);
}
