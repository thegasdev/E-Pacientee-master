package controller;

import javafx.stage.Stage;
import model.User;
import model.UserService;
import view.ConsultView;
import view.ConvenioView;
import view.ExamesView;
import view.LoginView;
import view.MinhasInformacoesView; // Importe a nova View
import view.ReceitasView;
import java.util.List;

public class MainMenuController {

    private final UserService userService;

    public MainMenuController(UserService userService) {
        this.userService = userService;
    }

    public void goToConsultas(Stage stage) {
        ConsultController consultController = new ConsultController(userService);
        new ConsultView(consultController).start(stage);
    }

    public void goToExames(Stage stage) {
        new ExamesView().start(stage);
    }

    public void goToReceitas(Stage stage) {
        new ReceitasView().start(stage);
    }

    public void goToConvenio(Stage stage) {
        new ConvenioView().start(stage);
    }

    public void goToMinhasInformacoes(Stage stage) {
        List<String> users = userService.getAllUsers();
        if (!users.isEmpty()) {
            User userDetails = userService.getUserDetails(users.get(0)); // Pega os detalhes do primeiro usuário
            if (userDetails != null) {
                new MinhasInformacoesView(userDetails).start(stage);
            } else {
                // Lógica caso os detalhes do usuário não sejam encontrados
                LoginView.showError("Detalhes do usuário não encontrados.");
            }
        } else {
            LoginView.showError("Nenhum usuário registrado.");
        }
    }

    public void logout(Stage stage) {
        new LoginView(userService).start(stage);
    }
}
