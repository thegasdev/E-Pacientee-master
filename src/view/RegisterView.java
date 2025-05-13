package view;

import controller.RegisterController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.UserService;

public class RegisterView {
    private final RegisterController controller;

    public RegisterView(UserService userService) {
        this.controller = new RegisterController(userService);
    }

    public void start(Stage stage) {
        Label title = new Label("Cadastro");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TextField nomeField = new TextField();
        nomeField.setPromptText("Nome");

        TextField sobrenomeField = new TextField();
        sobrenomeField.setPromptText("Sobrenome");

        TextField cpfField = new TextField();
        cpfField.setPromptText("CPF");

        TextField gmailField = new TextField();
        gmailField.setPromptText("Gmail");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Senha");

        TextArea termsTextArea = new TextArea("Bla bla bla... Estes são os termos de privacidade que você precisa aceitar para se registrar.");
        termsTextArea.setEditable(false); // Para não permitir edição
        termsTextArea.setWrapText(true); // Para quebrar o texto em várias linhas
        termsTextArea.setMaxHeight(70); // Defina uma altura máxima para a área de texto

        CheckBox acceptTermsCheckBox = new CheckBox("Eu aceito os termos de privacidade");

        Button registerBtn = new Button("Registrar");
        registerBtn.setDisable(true); // Desabilita o botão de registro inicialmente
        Button backBtn = new Button("Voltar");

        acceptTermsCheckBox.setOnAction(e -> {
            registerBtn.setDisable(!acceptTermsCheckBox.isSelected()); // Habilita o botão se o CheckBox estiver selecionado
        });

        registerBtn.setOnAction(e -> {
            controller.register(
                    cpfField.getText(),
                    passwordField.getText(),
                    nomeField.getText(),
                    sobrenomeField.getText(),
                    cpfField.getText(),
                    gmailField.getText(),
                    stage
            );
        });
        backBtn.setOnAction(e -> controller.goBack(stage));

        VBox box = new VBox(10, title, nomeField, sobrenomeField, cpfField, gmailField, passwordField, termsTextArea, acceptTermsCheckBox, registerBtn, backBtn);
        box.setPadding(new Insets(30));
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-background-color: linear-gradient(to bottom, #fff8dc, #ffffff);");

        stage.setScene(new Scene(box, 350, 500)); // Aumentei a altura
        stage.setTitle("Cadastro");
        stage.show();
    }

    public static void showError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg).showAndWait();
    }
}