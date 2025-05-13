package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.UserService;
import view.LoginView;

public class ConsultController {
    private final UserService userService;
    private final ObservableList<String> consultas;

    public ConsultController(UserService userService) {
        this.userService = userService;
        this.consultas = FXCollections.observableArrayList();
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
        new LoginView(userService).start(stage);
    }
}

