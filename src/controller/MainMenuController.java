package controller;

import javafx.stage.Stage;
import model.User;
import model.UserService;
import view.ConsultView;
import view.ConvenioView;
import view.ExamesView;
import view.LoginView;
import view.MinhasInformacoesView;
import view.ReceitasView;

public class MainMenuController {

    private final UserService userService;
    private final User loggedInUser;

    public MainMenuController(User user, UserService userService) {
        this.loggedInUser = user;
        this.userService = userService;
    }

    public void goToConsultas(Stage stage) {
        ConsultController consultController = new ConsultController(loggedInUser, userService);
        new ConsultView(consultController).start(stage);
    }

    public void goToExames(Stage stage) {
        new ExamesView(loggedInUser, userService).start(stage);
    }

    public void goToReceitas(Stage stage) {
        new ReceitasView(loggedInUser, userService).start(stage);
    }

    public void goToConvenio(Stage stage) {
        new ConvenioView(loggedInUser, userService).start(stage);
    }

    public void goToMinhasInformacoes(Stage stage) {
        new MinhasInformacoesView(this.loggedInUser, this.userService).start(stage);
    }

    public void logout(Stage stage) {
        new LoginView().start(stage);
    }

} // <- CERTIFIQUE-SE DE QUE NADA FOI ESCRITO DEPOIS DESTA CHAVE