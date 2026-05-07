package view;

import controller.DentistMainController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;
import model.UserService;

public class DentistMainView {

    private final DentistMainController controller;
    private final UserService userService;
    private final User loggedInUser;

    public DentistMainView(User user, UserService userService) {
        this.loggedInUser = user;
        this.userService = userService;
        this.controller = new DentistMainController(user, userService);
    }

    public void start(Stage stage) {
        Label title = new Label("Painel do Dentista");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-padding: 20px;");

        // Botões para as funcionalidades do dentista
        Button perfilBtn = new Button("Meu Perfil"); // <-- NOVO BOTÃO
        Button agendaBtn = new Button("Agenda Completa");
        Button pacientesBtn = new Button("Gerenciar Pacientes");
        Button logoutBtn = new Button("Sair (Logout)");

        // Configurando ações
        perfilBtn.setOnAction(e -> controller.goToMeuPerfil(stage)); // <-- NOVA AÇÃO
        agendaBtn.setOnAction(e -> controller.goToAgenda(stage));
        pacientesBtn.setOnAction(e -> controller.goToPacientes(stage));
        logoutBtn.setOnAction(e -> controller.logout(stage));

        // Estilizando os botões
        perfilBtn.setPrefWidth(200);
        agendaBtn.setPrefWidth(200);
        pacientesBtn.setPrefWidth(200);
        logoutBtn.setPrefWidth(200);

        // Adiciona o novo botão ao layout
        VBox layout = new VBox(20, title, perfilBtn, agendaBtn, pacientesBtn, logoutBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        layout.setStyle("-fx-background-color: linear-gradient(to bottom, #d0eaff, #ffffff);");

        Scene scene = new Scene(layout, 400, 450);
        stage.setTitle("Menu Principal - Dentista");
        stage.setScene(scene);
        stage.show();
    }
}