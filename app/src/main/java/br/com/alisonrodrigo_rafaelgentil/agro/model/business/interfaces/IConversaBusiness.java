package br.com.alisonrodrigo_rafaelgentil.agro.model.business.interfaces;

import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Conversa;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Mensagem;

public interface IConversaBusiness {
    public void salvarOuAtualizar(final Conversa conversa, Mensagem mensagem);
    public void receberDados(final Conversa conversa);
}
