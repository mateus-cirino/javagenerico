package banco;

import java.sql.Connection;
import java.sql.DriverManager;

public class FabricaDeConexao {
    private static Connection conexao = null;

    public static Connection getConnection() {
        if(conexao == null) {
            //utilizando o padrão factory, que encapsula a criacao de um objeto
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conexao = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/javaprojetogenerico?useTimezone=true&serverTimezone=UTC","root",""
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return conexao;

    }

    public static void closeConnection() {
        try {
            conexao.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
