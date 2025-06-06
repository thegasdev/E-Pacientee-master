import javafx.application.Application;
import javafx.stage.Stage;
import view.LoginView;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        new LoginView().start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
