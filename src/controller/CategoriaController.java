package controller;

import dao.Dao;
import model.Categoria;
import model.Model;

public class CategoriaController extends Controller{
    public CategoriaController(Model model, Dao dao) {
        super(model, dao);
    }

    protected Model criarModelAPartirDeUmArrayDeStrings(String[] dados) {
        Categoria categoria = new Categoria();
        categoria.setNome(dados[0]);
        categoria.setDescricao(dados[1]);
        return categoria;
    }
}
