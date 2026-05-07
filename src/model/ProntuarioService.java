package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class ProntuarioService {

    // updatePaciente (sem mudança, continua igual)
    public boolean updatePaciente(String cpf, String nome, String sobrenome, String gmail, String dataNascimento, String telefone) {
        String sql = "UPDATE users SET nome = ?, sobrenome = ?, gmail = ?, data_nascimento = ?, telefone = ? " +
                "WHERE cpf = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setString(2, sobrenome);
            pstmt.setString(3, gmail);
            pstmt.setString(4, dataNascimento);
            pstmt.setString(5, telefone);
            pstmt.setString(6, cpf);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar paciente: " + e.getMessage());
            return false;
        }
    }

    // updateFotoPath (sem mudança, continua igual)
    public boolean updateFotoPath(String cpf, String fotoPath) {
        String sql = "UPDATE users SET foto_path = ? WHERE cpf = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, fotoPath);
            pstmt.setString(2, cpf);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar caminho da foto: " + e.getMessage());
            return false;
        }
    }

    // getAnamnese (sem mudança, continua igual)
    public Anamnese getAnamnese(String cpfPaciente) {
        String sql = "SELECT * FROM anamnese WHERE cpf_paciente = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cpfPaciente);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Anamnese(
                        cpfPaciente,
                        rs.getString("alergias"),
                        rs.getString("medicamentos_uso"),
                        rs.getString("doencas_previas"),
                        "Sim".equals(rs.getString("fumante")),
                        "Sim".equals(rs.getString("gravida")),
                        rs.getString("observacoes")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar anamnese: " + e.getMessage());
        }
        return new Anamnese(cpfPaciente);
    }

    // saveAnamnese (sem mudança, continua igual)
    public boolean saveAnamnese(Anamnese anamnese) {
        String sql = "INSERT OR REPLACE INTO anamnese " +
                "(cpf_paciente, alergias, medicamentos_uso, doencas_previas, fumante, gravida, observacoes) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, anamnese.getCpfPaciente());
            pstmt.setString(2, anamnese.getAlergias());
            pstmt.setString(3, anamnese.getMedicamentosUso());
            pstmt.setString(4, anamnese.getDoencasPrevias());
            pstmt.setString(5, anamnese.isFumante() ? "Sim" : "Não");
            pstmt.setString(6, anamnese.isGravida() ? "Sim" : "Não");
            pstmt.setString(7, anamnese.getObservacoes());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao salvar anamnese: " + e.getMessage());
            return false;
        }
    }

    // getTratamentos (atualizado para carregar o cpf_profissional)
    public ObservableList<Tratamento> getTratamentos(String cpfPaciente) {
        ObservableList<Tratamento> tratamentos = FXCollections.observableArrayList();
        String sql = "SELECT * FROM tratamentos WHERE cpf_paciente = ? ORDER BY data DESC";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cpfPaciente);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                tratamentos.add(new Tratamento(
                        rs.getInt("tratamento_id"),
                        rs.getString("cpf_paciente"),
                        rs.getString("cpf_profissional"), // <-- Carregado
                        rs.getString("data"),
                        rs.getString("dente_regiao"),
                        rs.getString("descricao")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar tratamentos: " + e.getMessage());
        }
        return tratamentos;
    }

    // addTratamento (atualizado para salvar o cpf_profissional)
    public Tratamento addTratamento(Tratamento tratamento) {
        String sql = "INSERT INTO tratamentos(cpf_paciente, cpf_profissional, data, dente_regiao, descricao) VALUES(?,?,?,?,?)";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, tratamento.getCpfPaciente());
            pstmt.setString(2, tratamento.getCpfProfissional()); // <-- Salvo
            pstmt.setString(3, tratamento.getData());
            pstmt.setString(4, tratamento.getDenteRegiao());
            pstmt.setString(5, tratamento.getDescricao());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return new Tratamento(
                                rs.getInt(1),
                                tratamento.getCpfPaciente(),
                                tratamento.getCpfProfissional(),
                                tratamento.getData(),
                                tratamento.getDenteRegiao(),
                                tratamento.getDescricao()
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao salvar tratamento: " + e.getMessage());
        }
        return null;
    }
}