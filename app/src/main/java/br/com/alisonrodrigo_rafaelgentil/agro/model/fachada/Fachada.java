package br.com.alisonrodrigo_rafaelgentil.agro.model.fachada;

import br.com.alisonrodrigo_rafaelgentil.agro.model.dao.PessoaDAO;
import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Pessoa;

public class Fachada {
    PessoaDAO pessoaDAO;

    public Fachada() {
        this.pessoaDAO = new PessoaDAO();
    }

    public Pessoa autenticarUsuario(String email, String senha){
//        return pessoaDAO.autenticarUsuario(email,senha);
        return null;
    }
}
