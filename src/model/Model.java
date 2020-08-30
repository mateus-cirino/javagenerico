package model;

import utils.Campo;

import java.util.List;

public abstract class Model {
    private int id;

    public void setId (int id) {
        this.id = id;
    }

    public int getId () {
        return id;
    }
}
