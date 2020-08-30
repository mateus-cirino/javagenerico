package dao;

import banco.FabricaDeConexao;
import model.Model;
import utils.Campo;
import utils.Tipo;

import java.sql.*;
import java.util.Collections;
import java.util.List;

public abstract class Dao {
    private Connection conexao;
    protected Model model;
    private String nomeDaTabela;
    private String sqlInicialInserir;
    private String sqlInicialAtualizar;
    private String sqlInicialDeletar;
    private String sqlInicialRecuperar;

    public Dao(String nomeDaTabela) {
        this.conexao = FabricaDeConexao.getConnection();
        this.model = model;
        this.nomeDaTabela = nomeDaTabela;
        this.sqlInicialInserir = "insert into " + this.nomeDaTabela + "(";
        this.sqlInicialAtualizar = "update " + this.nomeDaTabela + " set ";
        this.sqlInicialDeletar = "delete from " + this.nomeDaTabela + " ";
        this.sqlInicialRecuperar = "select * from " + this.nomeDaTabela + " ";
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public abstract List<Campo> pegarListaDeCamposDoModel();

    public abstract void atualizarCamposDoModel(List<Campo> campos);

    public String criarTrechoDeSqlComOsNomesDosCamposNoInserir(List<Campo> campos) {
        // nome, descricao, dataInsercao
        String trechoDeSqlComOsNomesDosCampos = "";
        for (Campo campo : campos) {
            trechoDeSqlComOsNomesDosCampos += campo.getNome() + ",";
        }
        String trechoDeSqlComOsNomesDosCamposSemAUltimaVirgula = trechoDeSqlComOsNomesDosCampos.substring(0, trechoDeSqlComOsNomesDosCampos.length() - 1);
        return trechoDeSqlComOsNomesDosCamposSemAUltimaVirgula;
    }

    public String criarTrechoDeSqlComAsInterrogacoesNoInserir(List<Campo> campos) {
        // ?, ?, ?
        String trechoDeSqlComAsInterrogacoes = "";
        int numeroDeCampos = campos.size();
        for (int i = 0; i < numeroDeCampos; i++) {
            trechoDeSqlComAsInterrogacoes += "? ,";
        }
        String trechoDeSqlComAsInterrogacoesSemAUltimaVirgula = trechoDeSqlComAsInterrogacoes.substring(0, trechoDeSqlComAsInterrogacoes.length() - 2);
        return trechoDeSqlComAsInterrogacoesSemAUltimaVirgula;
    }

    public String criarTrechoDeSqlComOsNomesDosCamposNoAtualizar(List<Campo> campos) {
        // nome = ?, descricao = ?, dataInsercao = ?
        String trechoDeSqlComOsNomesDosCamposEInterrogacoes = "";
        for (Campo campo : campos) {
            trechoDeSqlComOsNomesDosCamposEInterrogacoes += campo.getNome() + " = ? ,";
        }
        String trechoDeSqlComOsNomesDosCamposEInterrogacoesSemAUltimaVirgula = trechoDeSqlComOsNomesDosCamposEInterrogacoes.substring(0, trechoDeSqlComOsNomesDosCamposEInterrogacoes.length() - 1);
        return trechoDeSqlComOsNomesDosCamposEInterrogacoesSemAUltimaVirgula;
    }

    public String criarTrechoDeSqlComOWhere() {
        // where id = ?
        return " where id = ? ";
    }

    public void setarValoresNoSql(List<Campo> campos, PreparedStatement preparedStatement) throws SQLException {
        // "mateus", 2, "2020/10/23"
        for (Campo campo : campos) {
            if (campo.getTipo().equals(Tipo.Int)) {
                preparedStatement.setInt(campos.indexOf(campo) + 1, (int) campo.getValor());
            } else {
                preparedStatement.setString(campos.indexOf(campo) + 1, (String) campo.getValor());
            }
        }
    }

    public void inserir() {
        // insert into categoria (nome, descricao) values ("mateus", "desc")
        List<Campo> campos = pegarListaDeCamposDoModel();
        String sqlInserir = sqlInicialInserir
                + criarTrechoDeSqlComOsNomesDosCamposNoInserir(campos)
                + ")values("
                + criarTrechoDeSqlComAsInterrogacoesNoInserir(campos)
                + ")";
        try {
            PreparedStatement preparedStatement = conexao.prepareStatement(sqlInserir, Statement.RETURN_GENERATED_KEYS);
            setarValoresNoSql(campos, preparedStatement);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            model.setId(resultSet.getInt(1));
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    public void atualizar() {
        // update categoria set nome = "mateus", descricao = "descricao" where id = 10
        List<Campo> campos = pegarListaDeCamposDoModel();
        String sqlAtualizar = sqlInicialAtualizar
                + criarTrechoDeSqlComOsNomesDosCamposNoAtualizar(campos)
                + criarTrechoDeSqlComOWhere();
        try {
            PreparedStatement preparedStatement = conexao.prepareStatement(sqlAtualizar);
            // adicionando o campo id como sendo o último da lista de campos por causa do where id = 10
            campos.add(new Campo("id", Tipo.Int, model.getId()));
            setarValoresNoSql(campos, preparedStatement);
            preparedStatement.executeUpdate();
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    public void deletar() {
        // deletar from categoria where id = 10
        String sqlDeletar = sqlInicialDeletar
                + criarTrechoDeSqlComOWhere();
        try {
            PreparedStatement preparedStatement = conexao.prepareStatement(sqlDeletar);
            // mandando uma lista de um único elemento pq no delete vc só precisa do valor do id
            setarValoresNoSql(Collections.singletonList(
                    new Campo("id", Tipo.Int, model.getId())
            ), preparedStatement);
            preparedStatement.executeUpdate();
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    public void recuperar() {
        // select * from categoria where id = 10
        String sqlRecuperar = sqlInicialRecuperar
                + criarTrechoDeSqlComOWhere();
        try {
            PreparedStatement preparedStatement = conexao.prepareStatement(sqlRecuperar);
            // mandando uma lista de um único elemento pq no select vc só precisa do valor do id
            setarValoresNoSql(Collections.singletonList(
                    new Campo("id", Tipo.Int, model.getId())
            ), preparedStatement);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                // recuperando o id
                model.setId(resultSet.getInt("id"));
                // recuperando os valores dos campos restantes
                List<Campo> campos = pegarListaDeCamposDoModel();
                for (Campo campo : campos) {
                    if (campo.getTipo() == Tipo.Int) {
                        campo.setValor(resultSet.getInt(campo.getNome()));
                    } else {
                        campo.setValor(resultSet.getString(campo.getNome()));
                    }
                }
                atualizarCamposDoModel(campos);
            }
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }
}
