package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;
import model.UserService; // <- Importe o UserService

public class MinhasInformacoesView {

    private final User user;
    private final UserService userService; // <- Adicionado

    // Construtor atualizado para receber também o UserService
    public MinhasInformacoesView(User user, UserService userService) {
        this.user = user;
        this.userService = userService; // <- Adicionado
    }

    public void start(Stage stage) {
        Label title = new Label("Minhas Informações");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-padding: 10px;");

        Label nomeLabel = new Label("Nome: " + user.getNome());
        Label sobrenomeLabel = new Label("Sobrenome: " + user.getSobrenome());
        Label cpfLabel = new Label("CPF: " + user.getCpf());
        Label gmailLabel = new Label("Gmail: " + user.getGmail());
        Label usuarioLabel = new Label("Usuário: " + user.getUsername());

        Button voltarBtn = new Button("Voltar");
        // Ação do botão "Voltar" CORRIGIDA
        voltarBtn.setOnAction(e -> {
            new MainMenuView(user, userService).start(stage);
        });

        VBox layout = new VBox(10, title, nomeLabel, sobrenomeLabel, cpfLabel, gmailLabel, usuarioLabel, voltarBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 300, 300);
        stage.setTitle("Minhas Informações");
        stage.setScene(scene);
        stage.show();
    }
}