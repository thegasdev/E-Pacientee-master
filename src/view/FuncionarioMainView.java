package view;

import controller.FuncionarioMainController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;
import model.UserService;

public class FuncionarioMainView {

    private final FuncionarioMainController controller;
    private final UserService userService;
    private final User loggedInFuncionario;

    public FuncionarioMainView(User user, UserService userService) {
        this.loggedInFuncionario = user;
        this.userService = userService;
        this.controller = new FuncionarioMainController(user, userService);
    }

    public void start(Stage stage) {
        Label title = new Label("Painel do Funcionário");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-padding: 20px;");

        // Botões para as funcionalidades do funcionário
        Button agendaBtn = new Button("Agenda / Marcar Consulta"); // <-- O novo botão
        Button cadastrarBtn = new Button("Cadastrar Novo Paciente");
        Button buscarBtn = new Button("Buscar Paciente");
        Button logoutBtn = new Button("Sair (Logout)");

        // Configurando ações
        agendaBtn.setOnAction(e -> controller.goToAgenda(stage)); // <-- A nova ação
        cadastrarBtn.setOnAction(e -> controller.goToCadastroPaciente(stage));
        buscarBtn.setOnAction(e -> controller.goToBuscarPaciente(stage));
        logoutBtn.setOnAction(e -> controller.logout(stage));

        // Estilizando os botões
        agendaBtn.setPrefWidth(200);
        cadastrarBtn.setPrefWidth(200);
        buscarBtn.setPrefWidth(200);
        logoutBtn.setPrefWidth(200);

        // O VBox corrigido, com todos os botões na ordem certa
        VBox layout = new VBox(20, title, agendaBtn, cadastrarBtn, buscarBtn, logoutBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        layout.setStyle("-fx-background-color: linear-gradient(to bottom, #f0f4f8, #ffffff);");

        Scene scene = new Scene(layout, 400, 450);
        stage.setTitle("Menu Principal - Funcionário");
        stage.setScene(scene);
        stage.show();
    }
}