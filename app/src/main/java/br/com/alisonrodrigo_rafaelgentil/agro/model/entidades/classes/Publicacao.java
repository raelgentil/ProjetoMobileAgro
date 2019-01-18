package br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes;

import java.util.HashMap;
import java.util.Map;

public class Publicacao {

    public String descricao;
    public String data_publicacao;

    public Publicacao() {

    }

    public Map<String, Object> getMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("descricao", getDescricao());
        map.put("data_publicacao", getData_publicacao());
        return map;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData_publicacao() {
        return data_publicacao;
    }

    public void setData_publicacao(String data_publicacao) {
        this.data_publicacao = data_publicacao;
    }
}
