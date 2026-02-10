package controller;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.User;
import model.UserService;
import view.FuncionarioMainView;

public class CadastroPacienteController {

    private final UserService userService;
    private final User loggedInFuncionario;

    public CadastroPacienteController(User user, UserService userService) {
        this.loggedInFuncionario = user;
        this.userService = userService;
    }

    // Tenta cadastrar o paciente
    // ...
    // Assinatura atualizada
    public void registerPatient(Stage stage, String cpf, String nome, String sobrenome, String gmail, String dataNascimento, String telefone) {
        // Validação
        if (cpf.isEmpty() || nome.isEmpty() || sobrenome.isEmpty() || dataNascimento.isEmpty() || telefone.isEmpty()) { // Telefone agora é obrigatório
            showAlert(Alert.AlertType.ERROR, "Erro no Cadastro", "Todos os campos, exceto Gmail, são obrigatórios.");
            return;
        }

        // Chama o service com o telefone
        boolean sucesso = userService.createPatient(cpf, nome, sobrenome, gmail, dataNascimento, telefone);

        if (sucesso) {
            showAlert(Alert.AlertType.INFORMATION, "Sucesso!", "Paciente cadastrado com sucesso. A senha inicial é o CPF.");
            goBack(stage);
        } else {
            showAlert(Alert.AlertType.ERROR, "Erro no Cadastro", "Um paciente com este CPF já existe no sistema.");
        }
    }
// ...

    // Volta para o menu principal do funcionário
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