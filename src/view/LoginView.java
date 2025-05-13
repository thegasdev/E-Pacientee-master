package view;

import controller.LoginController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image; // Importar Image
import javafx.scene.image.ImageView; // Importar ImageView
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.UserService;

public class LoginView {
    private static final UserService userService = new UserService(); // Mantém como estava
    private final LoginController controller = new LoginController(userService); // Mantém como estava

    public LoginView() {}
    // O construtor public LoginView(UserService service) {} pode ser removido se não for usado.
    // A instância estática userService é a que está sendo usada pelo controller.

    public void start(Stage stage) {
        // --- Logo ---
        ImageView logoView = null;
        try {
            // Tenta carregar a logo do classpath (src/resources/images/logo.png)
            Image logoImage = new Image(getClass().getResourceAsStream("/images/logo.png"));
            logoView = new ImageView(logoImage);
            logoView.setFitWidth(120); // Ajuste a largura conforme necessário
            logoView.setPreserveRatio(true); // Mantém a proporção da imagem
            logoView.setSmooth(true); // Melhora a qualidade da imagem redimensionada
        } catch (Exception e) {
            System.err.println("Erro ao carregar a logo: " + e.getMessage());
            // Opcional: pode criar um Label placeholder se a logo não carregar
            logoView = new ImageView(); // ImageView vazio para não quebrar o layout
        }

        Label title = new Label("Bem-vindo!"); // Título um pouco mais amigável
        title.setId("title-label"); // ID para estilização via CSS

        TextField cpfField = new TextField();
        cpfField.setPromptText("CPF");
        cpfField.getStyleClass().add("login-field"); // Classe para estilização

        PasswordField passwordField = new PasswordField(); // Renomeado para clareza
        passwordField.setPromptText("Senha");
        passwordField.getStyleClass().add("login-field"); // Classe para estilização

        Button loginBtn = new Button("Entrar");
        loginBtn.getStyleClass().add("login-button"); // Classe para estilização

        Button registerBtn = new Button("Cadastrar");
        registerBtn.getStyleClass().add("secondary-button");

        Button forgotPasswordBtn = new Button("Esqueci a senha");
        forgotPasswordBtn.getStyleClass().add("link-button");

        loginBtn.setOnAction(e -> controller.login(cpfField.getText(), passwordField.getText(), stage));
        registerBtn.setOnAction(e -> controller.goToRegister(stage));
        forgotPasswordBtn.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Esqueci a senha");
            alert.setHeaderText(null);
            alert.setContentText("Funcionalidade de recuperação de senha não implementada.");
            alert.showAndWait();
        });

        // Adicionando a logo no topo do VBox
        VBox box = new VBox(15, logoView, title, cpfField, passwordField, loginBtn, registerBtn, forgotPasswordBtn);
        box.setPadding(new Insets(40, 30, 40, 30)); // Ajuste no padding
        box.setAlignment(Pos.CENTER);
        // A estilização de fundo será movida para o CSS, mas podemos deixar um fallback
        // box.setStyle("-fx-background-color: linear-gradient(to bottom, #d0eaff, #ffffff);");
        box.getStyleClass().add("login-container"); // Classe para o container principal

        Scene scene = new Scene(box, 350, 450); // Ajustei a altura para acomodar a logo

        // Vinculando o arquivo CSS à cena
        try {
            String cssPath = getClass().getResource("/styles/login-styles.css").toExternalForm();
            scene.getStylesheets().add(cssPath);
        } catch (Exception e) {
            System.err.println("Erro ao carregar o arquivo CSS: " + e.getMessage());
        }

        stage.setScene(scene);
        stage.setTitle("Login - ePacient"); // Título da Janela
        stage.show();
    }

    public static void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg);
        // Adicionar estilo ao Alert também (opcional, mais avançado)
        // DialogPane dialogPane = alert.getDialogPane();
        // dialogPane.getStylesheets().add(LoginView.class.getResource("/styles/login-styles.css").toExternalForm());
        // dialogPane.getStyleClass().add("error-alert");
        alert.showAndWait();
    }

    public static void showSuccess(String msg) { // Mantido conforme seu código anterior
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg);
        alert.showAndWait();
    }
}