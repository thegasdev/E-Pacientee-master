package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.User;
import model.UserService;
import view.LoginView;

public class ConsultController {
    private final UserService userService;
    private final User loggedInUser;
    private final ObservableList<String> consultas;

    public ConsultController(User user, UserService userService) {
        this.loggedInUser = user;
        this.userService = userService;
        this.consultas = FXCollections.observableArrayList();
    }

    // Método getter para o usuário (necessário para o botão "Voltar")
    public User getLoggedInUser() {
        return loggedInUser;
    }

    // MÉTODO GETTER QUE ESTAVA FALTANDO
    public UserService getUserService() {
        return userService;
    }

    public ObservableList<String> getConsultas() {
        return consultas;
    }

    public void marcarConsulta(String dataHora) {
        if (!dataHora.isEmpty()) {
            consultas.add(dataHora);
        }
    }

    public void cancelarConsulta(String consulta) {
        consultas.remove(consulta);
    }

    public void logout(Stage stage) {
        new LoginView().start(stage);
    }
}