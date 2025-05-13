package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class ReceitasView {

    private final ObservableList<String> listaReceitas = FXCollections.observableArrayList();

    public ReceitasView() {
        carregarReceitasFicticias();
    }

    private void carregarReceitasFicticias() {
        listaReceitas.addAll("Receita #1: Dipirona 500mg, 1 comprimido a cada 6 horas.",
                "Receita #2: Amoxicilina 500mg, 1 cápsula de 8 em 8 horas por 7 dias.",
                "Receita #3: Paracetamol 750mg, 1 comprimido se necessário para dor ou febre.");
        // Em uma implementação real, você buscaria as receitas do paciente.
    }

    public void start(Stage stage) {
        Label title = new Label("Receitas");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-padding: 10px;");

        Label receitasLabel = new Label("Receitas Prescritas:");
        ListView<String> listaDeReceitasView = new ListView<>(listaReceitas);

        Button voltarBtn = new Button("Voltar");
        voltarBtn.setOnAction(e -> {
            new MainMenuView(null).start(stage); // Você pode precisar passar o UserService
        });

        VBox layout = new VBox(10, title, receitasLabel, listaDeReceitasView, voltarBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 500, 300); // Aumentei a largura para melhor visualização
        stage.setTitle("Receitas");
        stage.setScene(scene);
        stage.show();
    }
}