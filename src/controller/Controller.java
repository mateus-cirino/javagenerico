package controller;

import dao.Dao;
import model.Model;

public abstract class Controller {
    private Model model;
    private Dao dao;

    public Controller (Model model, Dao dao) {
        this.model = model;
        this.dao = dao;
    }

    protected abstract Model criarModelAPartirDeUmArrayDeStrings(String[] dados);

    public void inserir(String[] dados) {
        model = criarModelAPartirDeUmArrayDeStrings(dados);
        dao.setModel(model);
        dao.inserir();
    }

    public void atualizar(String[] dados) {
        model = criarModelAPartirDeUmArrayDeStrings(dados);
        dao.setModel(model);
        dao.atualizar();
    }

    public void deletar(int id) {
        model.setId(id);
        dao.setModel(model);
        dao.deletar();
    }

    public Model recuperar(int id) {
        model.setId(id);
        dao.setModel(model);
        dao.recuperar();
        return model;
    }
}
