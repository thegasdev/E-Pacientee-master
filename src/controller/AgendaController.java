package controller;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.AgendaService;
import model.Consulta;
import model.User;
import model.UserService;
import view.FuncionarioMainView;

import java.time.LocalDate;

public class AgendaController {

    private final UserService userService;
    private final AgendaService agendaService;
    private final User loggedInFuncionario;

    public AgendaController(User user, UserService userService) {
        this.loggedInFuncionario = user;
        this.userService = userService;
        this.agendaService = new AgendaService();
    }

    // Busca as consultas para a data selecionada
    public ObservableList<Consulta> getConsultas(LocalDate data) {
        return agendaService.getConsultasPorData(data.toString());
    }

    // Tenta agendar uma nova consulta (MÉTODO ATUALIZADO)
    public boolean agendar(String cpf, String data, String hora, String descricao) {
        // Validação de campos vazios
        if (cpf.isEmpty() || data.isEmpty() || hora == null || hora.isEmpty() || descricao.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Campos Incompletos", "Todos os campos são obrigatórios.");
            return false;
        }

        // Verifica se o paciente existe
        if (userService.getUserDetails(cpf) == null) {
            showAlert(Alert.AlertType.ERROR, "Paciente Não Encontrado", "Não existe um paciente com o CPF informado.");
            return false;
        }

        // --- NOVA VERIFICAÇÃO DE CONFLITO DE HORÁRIO ---
        if (!agendaService.isHorarioDisponivel(data, hora)) {
            showAlert(Alert.AlertType.ERROR, "Horário Indisponível", "Já existe uma consulta marcada para este dia e horário.");
            return false;
        }

        Consulta novaConsulta = new Consulta(cpf, data, hora, descricao);
        boolean sucesso = agendaService.agendarNovaConsulta(novaConsulta);

        if (sucesso) {
            showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Consulta agendada com sucesso!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Erro", "Não foi possível agendar a consulta.");
        }
        return sucesso;
    }

    // Volta para o menu do funcionário
    public void goBack(Stage stage) {
        new FuncionarioMainView(loggedInFuncionario, userService).start(stage);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}