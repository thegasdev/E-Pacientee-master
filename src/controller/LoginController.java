package controller;

import javafx.stage.Stage;
import model.User;
import model.UserService;
import view.LoginView;
import view.MainMenuView;
import view.RegisterView;

public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    // Método de login
    public void login(String cpf, String password, Stage stage) {
        if (userService.authenticate(cpf, password)) {
            User loggedInUser = userService.getUserDetails(cpf);

            if (loggedInUser != null) {
                new MainMenuView(loggedInUser, userService).start(stage);
            } else {
                LoginView.showError("Não foi possível carregar os dados do usuário.");
            }
        } else {
            LoginView.showError("CPF ou senha inválidos.");
        }
    }

    // MÉTODO QUE ESTAVA FALTANDO
    // Método para redirecionar para a tela de cadastro
    public void goToRegister(Stage stage) {
        new RegisterView(userService).start(stage);
    }
}