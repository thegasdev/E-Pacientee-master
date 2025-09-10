package view;

import controller.ExamesController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User; // <- Importações necessárias
import model.UserService; // <- Importações necessárias

public class ExamesView {

    private final ExamesController controller;
    private final ObservableList<String> listaExames = FXCollections.observableArrayList();
    private final User loggedInUser; // <- Adicionado
    private final UserService userService; // <- Adicionado

    // Construtor atualizado para receber o usuário e o serviço
    public ExamesView(User user, UserService service) {
        this.loggedInUser = user;
        this.userService = service;
        this.controller = new ExamesController();
        carregarExamesFicticios();
    }

    private void carregarExamesFicticios() {
        listaExames.addAll("Hemograma Completo - 10/05/2025",
                "Eletrocardiograma - 15/05/2025",
                "Raio-X do Tórax - 20/05/2025");
    }

    public void start(Stage stage) {
        Label title = new Label("Resultados de Exames");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-padding: 10px;");

        Label examesLabel = new Label("Exames Realizados:");
        ListView<String> listaDeExamesView = new ListView<>(listaExames);

        Button voltarBtn = new Button("Voltar");
        // Ação do botão "Voltar" CORRIGIDA
        voltarBtn.setOnAction(e -> {
            new MainMenuView(loggedInUser, userService).start(stage);
        });

        VBox layout = new VBox(10, title, examesLabel, listaDeExamesView, voltarBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 400, 300);
        stage.setTitle("Resultados de Exames");
        stage.setScene(scene);
        stage.show();
    }
}