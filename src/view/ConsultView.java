package view;

import controller.ConsultController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ConsultView {

    private final ConsultController controller;

    public ConsultView(ConsultController controller) {
        this.controller = controller;
    }

    public void start(Stage stage) {
        stage.setTitle("Agendamento de Consultas"); // Alterei o título

        // Label para a lista de consultas
        Label label = new Label("Consultas Agendadas:");

        // ListView para mostrar consultas
        ListView<String> listaConsultas = new ListView<>();
        listaConsultas.setItems(controller.getConsultas());

        // Botão para marcar a consulta
        Button marcarConsulta = new Button("Marcar Consulta");
        marcarConsulta.setOnAction(e -> {
            // Criando a janela de diálogo para a entrada da data e hora
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Marcar Consulta");
            dialog.setHeaderText("Digite a data e hora da consulta");

            // Campos de entrada
            DatePicker datePicker = new DatePicker();
            datePicker.setValue(LocalDate.now());

            TextField horaField = new TextField();
            horaField.setPromptText("Hora (HH:mm)");

            // Layout do diálogo
            VBox vboxDialog = new VBox(10, new Label("Data:"), datePicker, new Label("Hora:"), horaField);
            dialog.getDialogPane().setContent(vboxDialog);

            // Botões do diálogo
            ButtonType marcarButtonType = new ButtonType("Marcar", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(marcarButtonType, ButtonType.CANCEL);

            // Ação ao clicar em "Marcar"
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == marcarButtonType) {
                    String dataHora = datePicker.getValue() + " " + horaField.getText();
                    controller.marcarConsulta(dataHora);
                    return dataHora;
                }
                return null;
            });

            dialog.showAndWait();
        });

        // Botão para cancelar consulta
        Button cancelarConsulta = new Button("Cancelar Consulta");
        cancelarConsulta.setOnAction(e -> {
            String selecionada = listaConsultas.getSelectionModel().getSelectedItem();
            if (selecionada != null) {
                controller.cancelarConsulta(selecionada);
            } else {
                showAlert("Selecione uma consulta para cancelar.");
            }
        });

        // Botão para voltar ao menu principal
        Button voltarBtn = new Button("Voltar");
        voltarBtn.setOnAction(e -> {
            new MainMenuView(null).start(stage);
        });

        // Botão para sair
        Button sair = new Button("Sair");
        sair.setOnAction(e -> stage.close());

        VBox vbox = new VBox(10, label, listaConsultas, marcarConsulta, cancelarConsulta, voltarBtn, sair); // Adicionei o botão Voltar
        vbox.setPadding(new Insets(20)); // Adicionei um pouco de padding para espaçamento
        Scene scene = new Scene(vbox, 400, 400); // Aumentei um pouco a altura para acomodar o novo botão
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}