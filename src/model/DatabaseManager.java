package model;

import java.sql.*;

public class DatabaseManager {

    private static final String DATABASE_URL = "jdbc:sqlite:ePacient.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DATABASE_URL);
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
        return conn;
    }

    public static void initializeDatabase() {
        // --- Tabela USERS atualizada ---
        String sqlCreateTableUsers = "CREATE TABLE IF NOT EXISTS users (\n"
                + "    cpf TEXT PRIMARY KEY,\n"
                + "    password_hash TEXT NOT NULL,\n"
                + "    nome TEXT NOT NULL,\n"
                + "    sobrenome TEXT NOT NULL,\n"
                + "    gmail TEXT,\n"
                + "    data_nascimento TEXT,\n"
                + "    telefone TEXT,\n"
                + "    foto_path TEXT,\n"
                + "    role TEXT NOT NULL DEFAULT 'PACIENTE',\n"
                // --- NOVOS CAMPOS PARA DENTISTA/FUNCIONARIO ---
                + "    cro TEXT,\n"
                + "    clinica_nome TEXT,\n"
                + "    clinica_cep TEXT,\n"
                + "    clinica_localizacao TEXT\n"
                + ");";

        // Tabela ANAMNESE (sem mudança)
        String sqlCreateTableAnamnese = "CREATE TABLE IF NOT EXISTS anamnese (\n"
                + "    cpf_paciente TEXT PRIMARY KEY,\n"
                + "    alergias TEXT,\n"
                + "    medicamentos_uso TEXT,\n"
                + "    doencas_previas TEXT,\n"
                + "    fumante TEXT,\n"
                + "    gravida TEXT,\n"
                + "    observacoes TEXT,\n"
                + "    FOREIGN KEY (cpf_paciente) REFERENCES users (cpf)\n"
                + ");";

        // --- Tabela TRATAMENTOS atualizada ---
        String sqlCreateTableTratamentos = "CREATE TABLE IF NOT EXISTS tratamentos (\n"
                + "    tratamento_id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    cpf_paciente TEXT NOT NULL,\n"
                + "    cpf_profissional TEXT NOT NULL,\n" // <-- QUEM FEZ O TRATAMENTO
                + "    data TEXT NOT NULL,\n"
                + "    dente_regiao TEXT,\n"
                + "    descricao TEXT NOT NULL,\n"
                + "    FOREIGN KEY (cpf_paciente) REFERENCES users (cpf),\n"
                + "    FOREIGN KEY (cpf_profissional) REFERENCES users (cpf)\n"
                + ");";

        // Tabela CONSULTAS (sem mudança)
        String sqlCreateTableConsultas = "CREATE TABLE IF NOT EXISTS consultas (\n"
                // ... (seu código da tabela consultas) ...
                + "    FOREIGN KEY (cpf_paciente) REFERENCES users (cpf)\n"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sqlCreateTableUsers);
            stmt.execute(sqlCreateTableAnamnese);
            stmt.execute(sqlCreateTableTratamentos);
            stmt.execute(sqlCreateTableConsultas);
        } catch (SQLException e) {
            System.out.println("Erro ao criar as tabelas: " + e.getMessage());
        }

        // Garante que as colunas existam
        addColumnIfNotExists("role", "TEXT NOT NULL DEFAULT 'PACIENTE'");
        addColumnIfNotExists("data_nascimento", "TEXT");
        addColumnIfNotExists("telefone", "TEXT");
        addColumnIfNotExists("foto_path", "TEXT");
        addColumnIfNotExists("cro", "TEXT"); // <-- NOVA VERIFICAÇÃO
        addColumnIfNotExists("clinica_nome", "TEXT"); // <-- NOVA VERIFICAÇÃO
        addColumnIfNotExists("clinica_cep", "TEXT"); // <-- NOVA VERIFICAÇÃO
        addColumnIfNotExists("clinica_localizacao", "TEXT"); // <-- NOVA VERIFICAÇÃO
    }

    private static void addColumnIfNotExists(String columnName, String columnType) {
        try (Connection conn = connect()) {
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet columns = meta.getColumns(null, null, "users", columnName);
            if (!columns.next()) {
                System.out.println("Coluna '" + columnName + "' não encontrada. Adicionando...");
                try (Statement stmt = conn.createStatement()) {
                    String sqlAlterTable = "ALTER TABLE users ADD COLUMN " + columnName + " " + columnType;
                    stmt.execute(sqlAlterTable);
                    System.out.println("Coluna '" + columnName + "' adicionada com sucesso.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar/adicionar a coluna '" + columnName + "': " + e.getMessage());
        }
    }
}