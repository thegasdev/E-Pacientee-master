package controller;

import javafx.stage.Stage;
import model.UserService;
import view.LoginView;

public class RegisterController {
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    // Assinatura do método atualizada para incluir 'adminCode'
    // ...
    // ...
    // Assinatura atualizada
    public void register(String username, String password, String nome, String sobrenome, String gmail, String adminCode, String telefone, Stage stage) {

        // Passa 'null' para os novos campos de clínica, pois eles não são preenchidos no auto-cadastro
        if (userService.register(username, password, nome, sobrenome, gmail, adminCode, telefone, null, null, null, null)) {
            LoginView.showSuccess("Usuário registrado com sucesso!");
            new LoginView().start(stage);
        } else {
            view.RegisterView.showError("Usuário já existe.");
        }
    }
// ...

    public void goBack(Stage stage) {
        new LoginView().start(stage);
    }
}
