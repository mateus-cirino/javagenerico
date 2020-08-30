package dao;

import model.Categoria;
import utils.Campo;
import utils.Tipo;

import java.util.ArrayList;
import java.util.List;

public class CategoriaDao extends Dao {
    public CategoriaDao() {
        super("categoria");
    }

    public List<Campo> pegarListaDeCamposDoModel () {
        List<Campo> campos = new ArrayList<Campo>();
        campos.add(new Campo("nome", Tipo.String, ((Categoria) model).getNome()));
        campos.add(new Campo("descricao", Tipo.String, ((Categoria) model).getDescricao()));
        return campos;
    }

    public void atualizarCamposDoModel(List<Campo> campos) {
        ((Categoria) model).setNome((String) campos.stream().filter(campo -> campo.getNome().equals("nome")).findFirst().get().getValor());
        ((Categoria) model).setDescricao((String) campos.stream().filter(campo -> campo.getNome().equals("descricao")).findFirst().get().getValor());
    }
}
