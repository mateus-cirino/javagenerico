package model;

import utils.Campo;
import utils.Tipo;

import java.util.ArrayList;
import java.util.List;

public class Categoria extends Model {
    private String nome;
    private String descricao;

    public Categoria() {

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
