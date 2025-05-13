package view;

import controller.MainMenuController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.UserService;

public class MainMenuView {

    private final MainMenuController controller;
    private final UserService userService;

    public MainMenuView(UserService userService) {
        this.controller = new MainMenuController(userService);
        this.userService = userService;
    }

    public void start(Stage stage) {
        Label title = new Label("Menu Principal");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-padding: 20px;");

        Button minhasInfoBtn = new Button("Minhas Informações"); // Primeiro botão
        Button agendamentoBtn = new Button("Agendamento");
        Button examesBtn = new Button("Resultados de Exames");
        Button receitasBtn = new Button("Receitas");
        Button convenioBtn = new Button("Convênio");
        Button logoutBtn = new Button("Sair");

        minhasInfoBtn.setOnAction(e -> controller.goToMinhasInformacoes(stage));
        agendamentoBtn.setOnAction(e -> controller.goToConsultas(stage));
        examesBtn.setOnAction(e -> controller.goToExames(stage));
        receitasBtn.setOnAction(e -> controller.goToReceitas(stage));
        convenioBtn.setOnAction(e -> controller.goToConvenio(stage));
        logoutBtn.setOnAction(e -> controller.logout(stage));

        VBox layout = new VBox(20, title, minhasInfoBtn, agendamentoBtn, examesBtn, receitasBtn, convenioBtn, logoutBtn); // Nova ordem dos botões
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        layout.setStyle("-fx-background-color: linear-gradient(to bottom, #e6f7ff, #ffffff);");

        Scene scene = new Scene(layout, 400, 450);
        stage.setTitle("Menu Principal");
        stage.setScene(scene);
        stage.show();
    }
}