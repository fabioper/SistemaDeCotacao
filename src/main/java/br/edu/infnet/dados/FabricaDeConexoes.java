package br.edu.infnet.dados;

import br.edu.infnet.LeitorDePropriedades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaDeConexoes {
    private static String DRIVER;
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    private static void setPropriedades() {
        var props = new LeitorDePropriedades("/conexao.properties");
        DRIVER = props.getValor("driver");
        URL = props.getValor("url");
        USER = props.getValor("user");
        PASSWORD = props.getValor("password");
    }

    public static Connection conectar() {
        setPropriedades();

        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
