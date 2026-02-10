package controller;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.User;
import model.UserService;

public class ProfileController {

    private final UserService userService;
    private final User loggedInUser;

    public ProfileController(User user, UserService userService) {
        this.loggedInUser = user;
        this.userService = userService;
    }

    public void saveProfile(Stage stage, String nome, String sobrenome, String telefone, String gmail,
                            String cro, String clinicaNome, String clinicaCep, String clinicaLocal) {

        // 1. Atualiza o objeto User que está em memória
        loggedInUser.setNome(nome);
        loggedInUser.setSobrenome(sobrenome);
        loggedInUser.setTelefone(telefone);
        loggedInUser.setGmail(gmail);
        loggedInUser.setCro(cro);
        loggedInUser.setClinicaNome(clinicaNome);
        loggedInUser.setClinicaCep(clinicaCep);
        loggedInUser.setClinicaLocalizacao(clinicaLocal);

        // 2. Tenta salvar as mudanças no banco de dados
        boolean sucesso = userService.updateProfissionalProfile(loggedInUser);

        if (sucesso) {
            showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Perfil atualizado com sucesso!");
            stage.close(); // Fecha a janela do perfil
        } else {
            showAlert(Alert.AlertType.ERROR, "Erro", "Não foi possível atualizar o perfil no banco de dados.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}