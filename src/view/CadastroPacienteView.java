package view;

import controller.CadastroPacienteController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;
import model.UserService;

import java.time.LocalDate;

public class CadastroPacienteView {

    private final CadastroPacienteController controller;

    public CadastroPacienteView(User funcionario, UserService service) {
        this.controller = new CadastroPacienteController(funcionario, service);
    }

    public void start(Stage stage) {
        stage.setTitle("Cadastrar Novo Paciente");

        Label title = new Label("Cadastro de Paciente");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TextField nomeField = new TextField();
        nomeField.setPromptText("Nome");

        TextField sobrenomeField = new TextField();
        sobrenomeField.setPromptText("Sobrenome");

        TextField cpfField = new TextField();
        cpfField.setPromptText("CPF");

        TextField gmailField = new TextField();
        gmailField.setPromptText("Gmail (Opcional)");

        // O novo campo de telefone
        TextField telefoneField = new TextField();
        telefoneField.setPromptText("Telefone");

        DatePicker dataNascimentoPicker = new DatePicker();
        dataNascimentoPicker.setPromptText("Data de Nascimento");
        dataNascimentoPicker.setPrefWidth(Double.MAX_VALUE);

        Button registerBtn = new Button("Cadastrar Paciente");
        Button backBtn = new Button("Voltar");

        registerBtn.setOnAction(e -> {
            String dataNascimento = "";
            if (dataNascimentoPicker.getValue() != null) {
                dataNascimento = dataNascimentoPicker.getValue().toString();
            }

            // Passa o telefone para o controller
            controller.registerPatient(
                    stage,
                    cpfField.getText(),
                    nomeField.getText(),
                    sobrenomeField.getText(),
                    gmailField.getText(),
                    dataNascimento,
                    telefoneField.getText()
            );
        });

        backBtn.setOnAction(e -> controller.goBack(stage));

        // A LINHA 80 (PROVAVELMENTE) CORRIGIDA:
        // Garante que o telefoneField está na lista com as vírgulas corretas.
        VBox box = new VBox(10, title, nomeField, sobrenomeField, cpfField, gmailField, telefoneField, dataNascimentoPicker, registerBtn, backBtn);
        box.setPadding(new Insets(30));
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-background-color: linear-gradient(to bottom, #e6f7ff, #ffffff);");

        stage.setScene(new Scene(box, 350, 450)); // Altura ajustada
        stage.show();
    }
}