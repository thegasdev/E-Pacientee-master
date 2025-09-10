package view;

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

public class ConvenioView {

    private final ObservableList<String> farmaciasParceiras = FXCollections.observableArrayList();
    private final User loggedInUser; // <- Adicionado
    private final UserService userService; // <- Adicionado

    // Construtor atualizado para receber o usuário e o serviço
    public ConvenioView(User user, UserService service) {
        this.loggedInUser = user;
        this.userService = service;
        carregarFarmaciasParceiras();
    }

    private void carregarFarmaciasParceiras() {
        farmaciasParceiras.addAll("Drogaria São Paulo",
                "Farma Conde",
                "Droga Raia",
                "Ultrafarma",
                "Pacheco");
    }

    public void start(Stage stage) {
        Label title = new Label("Convênio");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-padding: 10px;");

        Label farmaciasLabel = new Label("Farmácias Parceiras:");
        ListView<String> listaDeFarmacias = new ListView<>(farmaciasParceiras);

        Button voltarBtn = new Button("Voltar");
        // Ação do botão "Voltar" CORRIGIDA
        voltarBtn.setOnAction(e -> {
            new MainMenuView(loggedInUser, userService).start(stage);
        });

        VBox layout = new VBox(10, title, farmaciasLabel, listaDeFarmacias, voltarBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 300, 300);
        stage.setTitle("Convênio");
        stage.setScene(scene);
        stage.show();
    }
}