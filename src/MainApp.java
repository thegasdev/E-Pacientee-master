import javafx.application.Application;
import javafx.stage.Stage;
import model.DatabaseManager; // Importe a nova classe
import view.LoginView;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        new LoginView().start(primaryStage);
    }

    public static void main(String[] args) {
        // Inicializa o banco de dados e cria a tabela se não existir
        DatabaseManager.createNewTable();

        launch(args);
    }
}