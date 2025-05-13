package controller;

import javafx.stage.Stage;
import model.UserService;
import view.LoginView;

public class RegisterController {
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    public void register(String username, String password, String nome, String sobrenome, String cpf, String gmail, Stage stage) {
        if (userService.register(username, password, nome, sobrenome, cpf, gmail)) {
            LoginView.showSuccess("Usuário registrado com sucesso!");
            new LoginView(userService).start(stage);
        } else {
            view.RegisterView.showError("Usuário já existe.");
        }
    }

    public void goBack(Stage stage) {
        new LoginView(userService).start(stage);
    }
}