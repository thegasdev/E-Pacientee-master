package view;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;
import model.UserService;

import java.util.Optional; // Importar para o diálogo

public class GerenciarPacientesView {

    private final User loggedInDentista;
    private final UserService userService;

    public GerenciarPacientesView(User user, UserService service) {
        this.loggedInDentista = user;
        this.userService = service;
    }

    public void start(Stage stage) {
        stage.setTitle("Gerenciar Pacientes");

        Label title = new Label("Buscar Pacientes");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TextField searchField = new TextField();
        searchField.setPromptText("Buscar por nome ou CPF...");
        searchField.setMaxWidth(Double.MAX_VALUE);

        ObservableList<User> todosPacientes = userService.getTodosPacientes();
        FilteredList<User> filteredPacientes = new FilteredList<>(todosPacientes, p -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredPacientes.setPredicate(paciente -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (paciente.getNome().toLowerCase().contains(lowerCaseFilter)) return true;
                if (paciente.getSobrenome().toLowerCase().contains(lowerCaseFilter)) return true;
                if (paciente.getCpf().contains(lowerCaseFilter)) return true;
                return false;
            });
        });

        ListView<User> listaPacientesView = new ListView<>(filteredPacientes);

        Button verProntuarioBtn = new Button("Abrir Prontuário");
        verProntuarioBtn.setDisable(true);

        Button voltarBtn = new Button("Voltar");
        voltarBtn.setOnAction(e -> {
            new DentistMainView(loggedInDentista, userService).start(stage);
        });

        listaPacientesView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            verProntuarioBtn.setDisable(newVal == null);
        });

        // --- AÇÃO DO BOTÃO "VER PRONTUÁRIO" ATUALIZADA ---
        verProntuarioBtn.setOnAction(e -> {
            User pacienteSelecionado = listaPacientesView.getSelectionModel().getSelectedItem();
            if (pacienteSelecionado != null) {

                // 1. Pergunta qual é o tratamento
                TextInputDialog dialog = new TextInputDialog("Ex: Restauração Dente 18");
                dialog.setTitle("Plano de Tratamento");
                dialog.setHeaderText("Qual é o procedimento principal para este consentimento?");
                dialog.setContentText("Procedimento:");
                Optional<String> result = dialog.showAndWait();

                // 2. Verifica se o dentista preencheu
                if (result.isPresent() && !result.get().isEmpty()) {
                    String tratamentoProposto = result.get();

                    // 3. CHAMA A TELA DE CONSENTIMENTO (passando o tratamento)
                    TermoConsentimentoView termoView = new TermoConsentimentoView(pacienteSelecionado, loggedInDentista, tratamentoProposto);

                    boolean aceito = termoView.display(stage);

                    // 4. SE FOI ACEITO, ABRE O PRONTUÁRIO
                    if (aceito) {
                        ProntuarioView prontuarioView = new ProntuarioView(pacienteSelecionado, loggedInDentista, userService);
                        prontuarioView.display();
                    } else {
                        new Alert(Alert.AlertType.WARNING, "O termo de consentimento deve ser aceito para acessar o prontuário.").show();
                    }
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "O nome do procedimento é obrigatório para gerar o TCLE.").show();
                }
            }
        });

        VBox layout = new VBox(10, title, searchField, listaPacientesView, verProntuarioBtn, voltarBtn);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 500, 600);
        stage.setScene(scene);
        stage.show();
    }
}