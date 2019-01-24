package br.com.alisonrodrigo_rafaelgentil.agro.model.dao.interfaces;

import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Conversa;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Mensagem;

public interface IConversaDAO {
    public void salvar(final Conversa conversa, Mensagem mensagem);
    public void atualizar(final Conversa conversa, Mensagem mensagem);
    public void receberDados(final Conversa conversa);
    public void pegarDadosConversa(final Conversa conversa);

}
