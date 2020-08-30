package controller;

import dao.CategoriaDao;
import model.Categoria;
import model.Model;

public class CategoriaController extends Controller{
    protected void inicializaController() {
        this.model = new Categoria();
        this.dao = new CategoriaDao();
    }

    protected Model criarModelAPartirDeUmArrayDeStrings(String[] dados) {
        Categoria categoria = new Categoria();
        categoria.setNome(dados[0]);
        categoria.setDescricao(dados[1]);
        return categoria;
    }
}
