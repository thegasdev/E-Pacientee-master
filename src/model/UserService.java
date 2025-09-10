package model;

import org.mindrot.jbcrypt.BCrypt; // Importa a biblioteca de hashing

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {

    // Não usamos mais os HashMaps!

    // Construtor vazio, pois não precisamos mais inicializar os mapas
    public UserService() {}

    public boolean register(String cpf, String password, String nome, String sobrenome, String gmail) {
        // SQL para inserir um novo usuário
        String sql = "INSERT INTO users(cpf, password_hash, nome, sobrenome, gmail) VALUES(?,?,?,?,?)";

        // Gera o hash da senha
        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cpf);
            pstmt.setString(2, passwordHash); // Salva o hash, não a senha!
            pstmt.setString(3, nome);
            pstmt.setString(4, sobrenome);
            pstmt.setString(5, gmail);
            pstmt.executeUpdate();
            return true; // Retorna true se a inserção for bem-sucedida

        } catch (SQLException e) {
            // Um erro aqui provavelmente significa que o CPF (PRIMARY KEY) já existe
            System.out.println("Erro ao registrar usuário: " + e.getMessage());
            return false;
        }
    }

    public boolean authenticate(String cpf, String password) {
        String sql = "SELECT password_hash FROM users WHERE cpf = ?";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cpf);
            ResultSet rs = pstmt.executeQuery();

            // Verifica se um usuário com o CPF foi encontrado
            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                // Compara a senha fornecida com o hash armazenado
                return BCrypt.checkpw(password, storedHash);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao autenticar: " + e.getMessage());
        }
        return false; // Retorna false se o usuário não for encontrado ou a senha estiver errada
    }

    public User getUserDetails(String cpf) {
        String sql = "SELECT cpf, nome, sobrenome, gmail FROM users WHERE cpf = ?";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cpf);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Como não armazenamos mais o username separadamente, passamos cpf como username
                // A senha não é necessária no objeto User após o login, então passamos null ou uma string vazia
                return new User(
                        rs.getString("cpf"), // Usando cpf como username
                        null, // Não precisamos da senha aqui
                        rs.getString("cpf"),
                        rs.getString("nome"),
                        rs.getString("sobrenome"),
                        rs.getString("gmail")
                );
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar detalhes do usuário: " + e.getMessage());
        }
        return null; // Retorna null se não encontrar o usuário
    }
}