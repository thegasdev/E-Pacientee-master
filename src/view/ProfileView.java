package view;

import controller.ProfileController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.User;
import model.UserService;

public class ProfileView {

    private final ProfileController controller;
    private final User loggedInUser;

    public ProfileView(User user, UserService service) {
        this.loggedInUser = user;
        this.controller = new ProfileController(user, service);
    }

    public void display(Stage ownerStage) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initOwner(ownerStage);
        window.setTitle("Meu Perfil Profissional");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        // --- Campos de Informações Pessoais ---
        Label infoHeader = new Label("Informações Pessoais");
        infoHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        grid.add(infoHeader, 0, 0, 2, 1);

        grid.add(new Label("Nome:"), 0, 1);
        TextField nomeField = new TextField(loggedInUser.getNome());
        grid.add(nomeField, 1, 1);

        grid.add(new Label("Sobrenome:"), 0, 2);
        TextField sobrenomeField = new TextField(loggedInUser.getSobrenome());
        grid.add(sobrenomeField, 1, 2);

        grid.add(new Label("CPF:"), 0, 3);
        TextField cpfField = new TextField(loggedInUser.getCpf());
        cpfField.setEditable(false); // CPF não pode ser mudado
        grid.add(cpfField, 1, 3);

        grid.add(new Label("Telefone:"), 0, 4);
        TextField telefoneField = new TextField(loggedInUser.getTelefone());
        grid.add(telefoneField, 1, 4);

        grid.add(new Label("Gmail:"), 0, 5);
        TextField gmailField = new TextField(loggedInUser.getGmail());
        grid.add(gmailField, 1, 5);

        // --- Campos de Informações da Clínica ---
        Label clinicaHeader = new Label("Informações da Clínica/Profissional");
        clinicaHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        grid.add(clinicaHeader, 0, 6, 2, 1);

        grid.add(new Label("CRO:"), 0, 7);
        TextField croField = new TextField(loggedInUser.getCro());
        croField.setPromptText("Ex: SP-12345");
        grid.add(croField, 1, 7);

        grid.add(new Label("Nome da Clínica:"), 0, 8);
        TextField clinicaNomeField = new TextField(loggedInUser.getClinicaNome());
        grid.add(clinicaNomeField, 1, 8);

        grid.add(new Label("Localização (Endereço):"), 0, 9);
        TextField clinicaLocalField = new TextField(loggedInUser.getClinicaLocalizacao());
        grid.add(clinicaLocalField, 1, 9);

        grid.add(new Label("CEP da Clínica:"), 0, 10);
        TextField clinicaCepField = new TextField(loggedInUser.getClinicaCep());
        grid.add(clinicaCepField, 1, 10);

        // Botões
        Button saveButton = new Button("Salvar Alterações");
        saveButton.setOnAction(e -> {
            controller.saveProfile(window,
                    nomeField.getText(),
                    sobrenomeField.getText(),
                    telefoneField.getText(),
                    gmailField.getText(),
                    croField.getText(),
                    clinicaNomeField.getText(),
                    clinicaCepField.getText(),
                    clinicaLocalField.getText()
            );
        });

        Button cancelButton = new Button("Cancelar");
        cancelButton.setOnAction(e -> window.close());

        HBox buttonBox = new HBox(10, cancelButton, saveButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        grid.add(buttonBox, 1, 11);

        Scene scene = new Scene(grid, 500, 500);
        window.setScene(scene);
        window.showAndWait();
    }
}