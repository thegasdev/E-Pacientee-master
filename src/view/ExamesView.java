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

public class ExamesView {

    private final ExamesController controller;
    private final ObservableList<String> listaExames = FXCollections.observableArrayList();

    public ExamesView() {
        this.controller = new ExamesController();
        carregarExamesFicticios();
    }

    private void carregarExamesFicticios() {
        listaExames.addAll("Hemograma Completo - 10/05/2025",
                "Eletrocardiograma - 15/05/2025",
                "Raio-X do Tórax - 20/05/2025");
        // Em uma implementação real, você buscaria os exames do paciente.
    }

    public void start(Stage stage) {
        Label title = new Label("Resultados de Exames");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-padding: 10px;");

        Label examesLabel = new Label("Exames Realizados:");
        ListView<String> listaDeExamesView = new ListView<>(listaExames);

        Button voltarBtn = new Button("Voltar");
        voltarBtn.setOnAction(e -> {
            new MainMenuView(null).start(stage); // Você pode precisar passar o UserService
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