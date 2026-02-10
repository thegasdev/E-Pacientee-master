package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {

    private static final String ADMIN_SECRET_CODE = "ADMIN123";
    private static final String FUNC_SECRET_CODE = "FUNC123";

    // Atualizado para incluir campos do profissional (que podem ser nulos)
    public boolean register(String cpf, String password, String nome, String sobrenome, String gmail, String adminCode, String telefone,
                            String cro, String clinicaNome, String clinicaCep, String clinicaLocalizacao) {

        String role;
        if (ADMIN_SECRET_CODE.equals(adminCode)) {
            role = "DENTISTA";
        } else if (FUNC_SECRET_CODE.equals(adminCode)) {
            role = "FUNCIONARIO";
        } else {
            role = "PACIENTE";
        }

        String sql = "INSERT INTO users(cpf, password_hash, nome, sobrenome, gmail, role, telefone, cro, clinica_nome, clinica_cep, clinica_localizacao) " +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cpf);
            pstmt.setString(2, passwordHash);
            pstmt.setString(3, nome);
            pstmt.setString(4, sobrenome);
            pstmt.setString(5, gmail);
            pstmt.setString(6, role);
            pstmt.setString(7, telefone);
            pstmt.setString(8, cro);
            pstmt.setString(9, clinicaNome);
            pstmt.setString(10, clinicaCep);
            pstmt.setString(11, clinicaLocalizacao);

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Erro ao registrar usuário: " + e.getMessage());
            return false;
        }
    }

    // Cadastro de paciente (feito pelo funcionário) - não muda, pois os campos extras são nulos
    public boolean createPatient(String cpf, String nome, String sobrenome, String gmail, String dataNascimento, String telefone) {
        String passwordHash = BCrypt.hashpw(cpf, BCrypt.gensalt());
        String role = "PACIENTE";

        // Os campos do dentista (CRO, etc.) ficarão nulos por padrão no banco
        String sql = "INSERT INTO users(cpf, password_hash, nome, sobrenome, gmail, data_nascimento, telefone, role) VALUES(?,?,?,?,?,?,?,?)";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cpf);
            pstmt.setString(2, passwordHash);
            pstmt.setString(3, nome);
            pstmt.setString(4, sobrenome);
            pstmt.setString(5, gmail);
            pstmt.setString(6, dataNascimento);
            pstmt.setString(7, telefone);
            pstmt.setString(8, role);
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Erro ao criar paciente: " + e.getMessage());
            return false;
        }
    }

    // Atualizado para carregar TODOS os campos
    public User authenticate(String cpf, String password) {
        String sql = "SELECT * FROM users WHERE cpf = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cpf);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                if (BCrypt.checkpw(password, storedHash)) {
                    return new User(
                            rs.getString("cpf"), null, rs.getString("cpf"),
                            rs.getString("nome"), rs.getString("sobrenome"),
                            rs.getString("gmail"), rs.getString("role"),
                            rs.getString("data_nascimento"),
                            rs.getString("telefone"),
                            rs.getString("foto_path"),
                            rs.getString("cro"),
                            rs.getString("clinica_nome"),
                            rs.getString("clinica_cep"),
                            rs.getString("clinica_localizacao")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao autenticar: " + e.getMessage());
        }
        return null;
    }

    // Atualizado para carregar TODOS os campos
    public User getUserDetails(String cpf) {
        String sql = "SELECT * FROM users WHERE cpf = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cpf);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getString("cpf"), null, rs.getString("cpf"),
                        rs.getString("nome"), rs.getString("sobrenome"),
                        rs.getString("gmail"), rs.getString("role"),
                        rs.getString("data_nascimento"),
                        rs.getString("telefone"),
                        rs.getString("foto_path"),
                        rs.getString("cro"),
                        rs.getString("clinica_nome"),
                        rs.getString("clinica_cep"),
                        rs.getString("clinica_localizacao")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar detalhes do usuário: " + e.getMessage());
        }
        return null;
    }

    // Atualizado para carregar TODOS os campos
    public ObservableList<User> getTodosPacientes() {
        ObservableList<User> pacientes = FXCollections.observableArrayList();
        String sql = "SELECT * FROM users WHERE role = 'PACIENTE'";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                pacientes.add(new User(
                        rs.getString("cpf"), null, rs.getString("cpf"),
                        rs.getString("nome"), rs.getString("sobrenome"),
                        rs.getString("gmail"), rs.getString("role"),
                        rs.getString("data_nascimento"),
                        rs.getString("telefone"),
                        rs.getString("foto_path"),
                        rs.getString("cro"),
                        rs.getString("clinica_nome"),
                        rs.getString("clinica_cep"),
                        rs.getString("clinica_localizacao")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar pacientes: " + e.getMessage());
        }
        return pacientes;
    }
    public boolean updateProfissionalProfile(User user) {
        String sql = "UPDATE users SET nome = ?, sobrenome = ?, gmail = ?, telefone = ?, " +
                "cro = ?, clinica_nome = ?, clinica_cep = ?, clinica_localizacao = ? " +
                "WHERE cpf = ?";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getNome());
            pstmt.setString(2, user.getSobrenome());
            pstmt.setString(3, user.getGmail());
            pstmt.setString(4, user.getTelefone());
            pstmt.setString(5, user.getCro());
            pstmt.setString(6, user.getClinicaNome());
            pstmt.setString(7, user.getClinicaCep());
            pstmt.setString(8, user.getClinicaLocalizacao());
            pstmt.setString(9, user.getCpf()); // Cláusula WHERE

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar perfil profissional: " + e.getMessage());
            return false;
        }
    }
}