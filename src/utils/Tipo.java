package utils;

public enum Tipo {

    Int("Inteiro"),
    String("String");

    private String descricao;

    private Tipo (String descricao) {
        this.descricao = descricao;
    }
}