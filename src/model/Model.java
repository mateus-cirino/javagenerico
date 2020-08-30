package model;

public abstract class Model {
    private int id;

    public void setId (int id) {
        this.id = id;
    }

    public int getId () {
        return id;
    }
}
