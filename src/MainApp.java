import javafx.application.Application;
import javafx.stage.Stage;
import model.DatabaseManager; // Importe a nova classe
import view.LoginView;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        new LoginView().start(primaryStage);
    }

    // ...
    public static void main(String[] args) {
        // Mude para o novo método de inicialização
        DatabaseManager.initializeDatabase();

        launch(args);
    }
}