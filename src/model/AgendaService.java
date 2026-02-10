package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AgendaService {

    // Busca todas as consultas para uma data específica
    public ObservableList<Consulta> getConsultasPorData(String data) {
        ObservableList<Consulta> consultas = FXCollections.observableArrayList();

        String sql = "SELECT c.*, u.nome, u.sobrenome " +
                "FROM consultas c " +
                "JOIN users u ON c.cpf_paciente = u.cpf " +
                "WHERE c.data = ? AND c.status = 'Agendada' " + // <-- Mostra só as agendadas
                "ORDER BY c.hora ASC";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, data);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Consulta consulta = new Consulta(
                        rs.getInt("consulta_id"),
                        rs.getString("cpf_paciente"),
                        rs.getString("data"),
                        rs.getString("hora"),
                        rs.getString("descricao"),
                        rs.getString("status")
                );
                consulta.setNomePaciente(rs.getString("nome") + " " + rs.getString("sobrenome"));
                consultas.add(consulta);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar consultas: " + e.getMessage());
        }
        return consultas;
    }

    // Adiciona uma nova consulta ao banco de dados
    public boolean agendarNovaConsulta(Consulta consulta) {
        String sql = "INSERT INTO consultas(cpf_paciente, data, hora, descricao, status) VALUES(?,?,?,?,?)";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, consulta.getCpfPaciente());
            pstmt.setString(2, consulta.getData());
            pstmt.setString(3, consulta.getHora());
            pstmt.setString(4, consulta.getDescricao());
            pstmt.setString(5, consulta.getStatus());
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Erro ao agendar consulta: " + e.getMessage());
            return false;
        }
    }

    // --- NOVO MÉTODO PARA VERIFICAR CONFLITO ---
    public boolean isHorarioDisponivel(String data, String hora) {
        // Verifica se já existe uma consulta agendada para esta data e hora
        String sql = "SELECT COUNT(*) FROM consultas WHERE data = ? AND hora = ? AND status = 'Agendada'";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, data);
            pstmt.setString(2, hora);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // Retorna true (disponível) apenas se a contagem de consultas for 0
                return rs.getInt(1) == 0;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao checar horário: " + e.getMessage());
            return false; // Por segurança, não permite agendar se der erro
        }
        return false;
    }
}