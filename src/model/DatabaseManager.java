package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    // Define o caminho e o nome do arquivo do banco de dados
    private static final String DATABASE_URL = "jdbc:sqlite:ePacient.db";

    // Método para conectar ao banco de dados
    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DATABASE_URL);
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
        return conn;
    }

    // Método para criar a tabela de usuários se ela não existir
    public static void createNewTable() {
        // SQL para criar a tabela de usuários
        String sql = "CREATE TABLE IF NOT EXISTS users (\n"
                + "    cpf TEXT PRIMARY KEY,\n"
                + "    password_hash TEXT NOT NULL,\n"
                + "    nome TEXT NOT NULL,\n"
                + "    sobrenome TEXT NOT NULL,\n"
                + "    gmail TEXT\n"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            // Cria a tabela
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Erro ao criar a tabela: " + e.getMessage());
        }
    }
}