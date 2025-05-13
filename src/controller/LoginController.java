package controller;

import javafx.stage.Stage;
import model.UserService;
import view.ConsultView;
import view.LoginView;
import view.MainMenuView;
import view.RegisterView;

public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    // Método de login
    public void login(String cpf, String password, Stage stage) { // Alterado para receber CPF
        if (userService.authenticate(cpf, password)) { // Usando CPF na autenticação
            new MainMenuView(userService).start(stage);
        } else {
            LoginView.showError("CPF ou senha inválidos."); // Mensagem de erro atualizada
        }
    }

    // Método para redirecionar para a tela de cadastro
    public void goToRegister(Stage stage) {
        new RegisterView(userService).start(stage);
    }
}