package view;

import controller.AgendaController;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Consulta;
import model.User;
import model.UserService;

import java.time.LocalDate;

public class AgendaView {

    private final AgendaController controller;
    private ListView<Consulta> listaConsultasView;
    private DatePicker agendaDatePicker;
    private ComboBox<String> horaBox; // <- Mudança de TextField para ComboBox

    public AgendaView(User funcionario, UserService service) {
        this.controller = new AgendaController(funcionario, service);
    }

    public void start(Stage stage) {
        stage.setTitle("Agenda de Consultas");

        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(20));

        // --- TOPO: Seleção de Data e Botão Voltar ---
        agendaDatePicker = new DatePicker(LocalDate.now());
        agendaDatePicker.setOnAction(e -> carregarConsultasDoDia());

        Button voltarBtn = new Button("Voltar ao Menu");
        voltarBtn.setOnAction(e -> controller.goBack(stage));

        HBox topBox = new HBox(10, new Label("Selecionar Data:"), agendaDatePicker, voltarBtn);
        layout.setTop(topBox);

        // --- CENTRO: Lista de Consultas do Dia ---
        listaConsultasView = new ListView<>();
        layout.setCenter(listaConsultasView);
        BorderPane.setMargin(listaConsultasView, new Insets(10, 0, 10, 0));

        // --- INFERIOR: Formulário de Agendamento ---
        GridPane formGrid = new GridPane();
        formGrid.setVgap(10);
        formGrid.setHgap(10);

        TextField cpfField = new TextField();
        cpfField.setPromptText("CPF do Paciente");

        // --- CAMPO DE HORA ATUALIZADO ---
        horaBox = new ComboBox<>();
        horaBox.setPromptText("Hora");
        // Popula o ComboBox com horários (de 8h às 18h, de 30 em 30 min)
        for (int h = 8; h < 18; h++) {
            horaBox.getItems().add(String.format("%02d:00", h));
            horaBox.getItems().add(String.format("%02d:30", h));
        }
        horaBox.setPrefWidth(150);

        TextField descField = new TextField();
        descField.setPromptText("Descrição (Ex: Avaliação, Limpeza)");

        Button agendarBtn = new Button("Agendar");

        formGrid.add(new Label("CPF:"), 0, 0);
        formGrid.add(cpfField, 1, 0);
        formGrid.add(new Label("Hora:"), 0, 1);
        formGrid.add(horaBox, 1, 1); // <- Adicionado o ComboBox
        formGrid.add(new Label("Descrição:"), 0, 2);
        formGrid.add(descField, 1, 2);
        formGrid.add(agendarBtn, 1, 3);

        VBox bottomBox = new VBox(10, new Label("Novo Agendamento:"), formGrid);
        layout.setBottom(bottomBox);

        // Ação do Botão Agendar (atualizada)
        agendarBtn.setOnAction(e -> {
            String horaSelecionada = horaBox.getValue(); // Pega o valor do ComboBox

            boolean sucesso = controller.agendar(
                    cpfField.getText(),
                    agendaDatePicker.getValue().toString(),
                    horaSelecionada, // Passa a hora selecionada
                    descField.getText()
            );
            if (sucesso) {
                carregarConsultasDoDia();
                cpfField.clear();
                horaBox.setValue(null); // Limpa o ComboBox
                descField.clear();
            }
        });

        carregarConsultasDoDia();

        Scene scene = new Scene(layout, 600, 500);
        stage.setScene(scene);
        stage.show();
    }

    private void carregarConsultasDoDia() {
        LocalDate data = agendaDatePicker.getValue();
        if (data != null) {
            ObservableList<Consulta> consultas = controller.getConsultas(data);
            listaConsultasView.setItems(consultas);
        }
    }
}