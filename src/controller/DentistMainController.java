package controller;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.User;
import model.UserService;
import view.AgendaView; // <-- IMPORTAÇÃO ADICIONADA
import view.GerenciarPacientesView;
import view.LoginView;
import view.ProfileView;

public class DentistMainController {

    private final UserService userService;
    private final User loggedInUser;

    public DentistMainController(User user, UserService userService) {
        this.loggedInUser = user;
        this.userService = userService;
    }

    public void goToMeuPerfil(Stage stage) {
        ProfileView profileView = new ProfileView(loggedInUser, userService);
        profileView.display(stage);
    }

    // --- MÉTODO ATUALIZADO ---
    public void goToAgenda(Stage stage) {
        // Removemos o alerta e chamamos a AgendaView que já existe
        new AgendaView(loggedInUser, userService).start(stage);
    }

    public void goToPacientes(Stage stage) {
        new GerenciarPacientesView(loggedInUser, userService).start(stage);
    }

    public void logout(Stage stage) {
        new LoginView().start(stage);
    }

    // O método showAlert() não é mais necessário aqui, a menos que outras funções o usem
    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}