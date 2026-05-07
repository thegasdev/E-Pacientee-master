package controller;

import javafx.stage.Stage;
import model.User;
import model.UserService;
import view.DentistMainView;
import view.LoginView;
import view.MainMenuView;
import view.RegisterView;
// Importaremos a nova view do funcionário, mesmo que ela ainda não exista
import view.FuncionarioMainView;

public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    public void login(String cpf, String password, Stage stage) {
        User loggedInUser = userService.authenticate(cpf, password);

        if (loggedInUser != null) {
            String role = loggedInUser.getRole();

            if ("DENTISTA".equals(role)) {
                new DentistMainView(loggedInUser, userService).start(stage);

            } else if ("FUNCIONARIO".equals(role)) { // <-- NOVO CAMINHO
                new FuncionarioMainView(loggedInUser, userService).start(stage);

            } else { // Paciente
                new MainMenuView(loggedInUser, userService).start(stage);
            }
        } else {
            LoginView.showError("CPF ou senha inválidos.");
        }
    }

    public void goToRegister(Stage stage) {
        new RegisterView(userService).start(stage);
    }
}