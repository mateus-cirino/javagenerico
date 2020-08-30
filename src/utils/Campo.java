package utils;

public class Campo {
    private String nome;
    private Tipo tipo;
    private Object valor;

    public Campo (String nome, Tipo tipo, Object valor) {
        this.nome = nome;
        this.tipo = tipo;
        this.valor = valor;
    }

    public void setNome (String nome) {
        this.nome = nome;
    }

    public void setTipo (Tipo tipo) {
        this.tipo = tipo;
    }

    public void setValor (Object valor) {
        this.valor = valor;
    }

    public String getNome () {
        return nome;
    }

    public Tipo getTipo () {
        return tipo;
    }

    public Object getValor () {
        return valor;
    }
}
