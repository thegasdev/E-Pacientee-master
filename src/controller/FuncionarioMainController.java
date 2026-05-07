package controller;

import javafx.stage.Stage;
import model.User;
import model.UserService;
import view.AgendaView; // Importação da nova tela de agenda
import view.GerenciarPacientesView;
import view.LoginView;
import view.CadastroPacienteView;

public class FuncionarioMainController {

    private final UserService userService;
    private final User loggedInFuncionario;

    public FuncionarioMainController(User user, UserService userService) {
        this.loggedInFuncionario = user;
        this.userService = userService;
    }

    // Ação para o botão "Agenda / Marcar Consulta"
    public void goToAgenda(Stage stage) {
        new AgendaView(loggedInFuncionario, userService).start(stage);
    }

    // Ação para o botão "Cadastrar Novo Paciente"
    public void goToCadastroPaciente(Stage stage) {
        new CadastroPacienteView(loggedInFuncionario, userService).start(stage);
    }

    // Ação para o botão "Buscar Paciente"
    public void goToBuscarPaciente(Stage stage) {
        // Vamos reutilizar a mesma tela de busca do dentista
        new GerenciarPacientesView(loggedInFuncionario, userService).start(stage);
    }

    // Ação para o botão "Sair"
    public void logout(Stage stage) {
        new LoginView().start(stage);
    }
}