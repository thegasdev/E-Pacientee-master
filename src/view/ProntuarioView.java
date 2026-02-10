package view;

// Imports de Modelo
import model.Anamnese;
import model.ProntuarioService;
import model.Tratamento;
import model.User;
import model.UserService;

// Imports do JavaFX (Layout)
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

// Imports do JavaFX (Controles)
import javafx.scene.Scene;
import javafx.scene.control.*;

// Imports do JavaFX (Canvas/Imagem)
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

// Imports do JavaFX (Stage/Window)
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

// Imports do Java (tempo/util/IO)
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import javafx.collections.ObservableList;


public class ProntuarioView {

    private final User paciente;
    private final User dentista; // Agora também representa o "profissional logado"
    private final UserService userService;
    private final ProntuarioService prontuarioService;
    private Stage window;

    // Campos Aba 1
    private TextField nomeField, sobrenomeField, cpfField, gmailField, telefoneField;
    private DatePicker dataNascimentoPicker;
    private ImageView fotoPacienteView;

    // Campos Aba 2
    private TextField alergiasField, medicamentosField;
    private TextArea doencasArea, obsArea;
    private CheckBox fumanteCheck, gravidaCheck;

    // Campos Aba 3
    private ListView<Tratamento> tratamentosListView;
    private ObservableList<Tratamento> listaDeTratamentos;
    private DatePicker dataTratamentoPicker;
    private TextField denteField;
    private TextArea descricaoTratamentoArea;

    public ProntuarioView(User paciente, User dentista, UserService userService) {
        this.paciente = paciente;
        this.dentista = dentista;
        this.userService = userService;
        this.prontuarioService = new ProntuarioService();
    }

    public void display() {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Prontuário - " + paciente.getNome() + " " + paciente.getSobrenome());

        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(
                new Tab("Visão Geral", createVisaoGeralTab()),
                new Tab("Anamnese", createAnamneseTab()),
                new Tab("Evolução e Plano", createTratamentosTab())
        );
        for (Tab tab : tabPane.getTabs()) tab.setClosable(false);

        Scene scene = new Scene(tabPane, 800, 600);
        window.setScene(scene);
        window.showAndWait();
    }

    // --- ABA 1: VISÃO GERAL (Atualizada) ---
    private BorderPane createVisaoGeralTab() {
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(20));

        // LADO ESQUERDO: FOTO
        VBox fotoBox = new VBox(10);
        fotoBox.setAlignment(Pos.CENTER);
        fotoBox.setPadding(new Insets(10, 20, 10, 10));
        fotoPacienteView = new ImageView();
        fotoPacienteView.setFitHeight(150);
        fotoPacienteView.setFitWidth(150);
        Circle clip = new Circle(75, 75, 75);
        fotoPacienteView.setClip(clip);
        fotoPacienteView.setStyle("-fx-background-color: #EEEEEE;");
        loadPacienteImage();
        Button alterarFotoBtn = new Button("Anexar/Alterar Foto");
        alterarFotoBtn.setOnAction(e -> anexarFoto());
        fotoBox.getChildren().addAll(fotoPacienteView, alterarFotoBtn);
        layout.setLeft(fotoBox);

        // LADO DIREITO: DADOS
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Nome:"), 0, 0);
        nomeField = new TextField(paciente.getNome());
        grid.add(nomeField, 1, 0);

        grid.add(new Label("Sobrenome:"), 0, 1);
        sobrenomeField = new TextField(paciente.getSobrenome());
        grid.add(sobrenomeField, 1, 1);

        grid.add(new Label("CPF:"), 0, 2);
        cpfField = new TextField(paciente.getCpf());
        cpfField.setEditable(false);
        grid.add(cpfField, 1, 2);

        grid.add(new Label("Telefone:"), 0, 3);
        telefoneField = new TextField(paciente.getTelefone());
        grid.add(telefoneField, 1, 3);

        grid.add(new Label("Gmail:"), 0, 4);
        gmailField = new TextField(paciente.getGmail());
        grid.add(gmailField, 1, 4);

        grid.add(new Label("Data de Nascimento:"), 0, 5);
        dataNascimentoPicker = new DatePicker();
        if (paciente.getDataNascimento() != null && !paciente.getDataNascimento().isEmpty()) {
            dataNascimentoPicker.setValue(LocalDate.parse(paciente.getDataNascimento()));
        }
        grid.add(dataNascimentoPicker, 1, 5);

        Button salvarButton = new Button("Salvar Alterações");
        salvarButton.setOnAction(e -> salvarVisaoGeral());

        VBox dadosBox = new VBox(20, grid, salvarButton);
        dadosBox.setAlignment(Pos.CENTER);

        layout.setCenter(dadosBox);
        return layout;
    }

    // Ação "Salvar" da Aba 1 (Atualizada)
    private void salvarVisaoGeral() {
        String dataNasc = (dataNascimentoPicker.getValue() != null) ? dataNascimentoPicker.getValue().toString() : "";
        boolean sucesso = prontuarioService.updatePaciente(
                cpfField.getText(),
                nomeField.getText(),
                sobrenomeField.getText(),
                gmailField.getText(),
                dataNasc,
                telefoneField.getText()
        );
        Alert alert = new Alert(sucesso ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
        alert.setTitle(sucesso ? "Sucesso" : "Erro");
        alert.setContentText(sucesso ? "Informações do paciente atualizadas." : "Não foi possível atualizar as informações.");
        alert.showAndWait();
        if (sucesso) {
            paciente.setNome(nomeField.getText());
            paciente.setSobrenome(sobrenomeField.getText());
            paciente.setGmail(gmailField.getText());
            paciente.setDataNascimento(dataNasc);
            paciente.setTelefone(telefoneField.getText());
        }
    }

    // Métodos da Foto (Sem mudança)
    private void loadPacienteImage() {
        String path = paciente.getFotoPath();
        Image image;
        try {
            if (path != null && !path.isEmpty() && new File(path).exists()) {
                image = new Image(new File(path).toURI().toString());
            } else {
                image = new Image(getClass().getResourceAsStream("/images/placeholder_paciente.png"));
            }
        } catch (Exception e) {
            image = new Image(getClass().getResourceAsStream("/images/placeholder_paciente.png"));
            System.err.println("Erro ao carregar foto do paciente: " + e.getMessage());
        }
        fotoPacienteView.setImage(image);
    }

    private void anexarFoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar Foto do Paciente");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(window);
        if (selectedFile != null) {
            try {
                File destDir = new File("fotos_pacientes");
                if (!destDir.exists()) destDir.mkdir();
                String fileExtension = selectedFile.getName().substring(selectedFile.getName().lastIndexOf("."));
                String newFileName = paciente.getCpf() + fileExtension;
                Path destPath = new File(destDir.getName() + File.separator + newFileName).toPath();
                Path sourcePath = selectedFile.toPath();
                Files.copy(sourcePath, destPath, StandardCopyOption.REPLACE_EXISTING);
                String pathParaSalvar = destPath.toAbsolutePath().toString();
                boolean sucesso = prontuarioService.updateFotoPath(paciente.getCpf(), pathParaSalvar);
                if (sucesso) {
                    paciente.setFotoPath(pathParaSalvar);
                    loadPacienteImage();
                    new Alert(Alert.AlertType.INFORMATION, "Foto atualizada com sucesso!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Erro ao salvar o caminho da foto no banco de dados.").show();
                }
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Erro ao salvar a imagem: " + e.getMessage()).show();
            }
        }
    }

    // --- ABA 2: ANAMNESE (Sem mudança) ---
    private VBox createAnamneseTab() {
        Anamnese anamnese = prontuarioService.getAnamnese(paciente.getCpf());
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Alergias:"), 0, 0);
        alergiasField = new TextField(anamnese.getAlergias());
        grid.add(alergiasField, 1, 0);
        grid.add(new Label("Medicamentos em uso:"), 0, 1);
        medicamentosField = new TextField(anamnese.getMedicamentosUso());
        grid.add(medicamentosField, 1, 1);
        grid.add(new Label("Doenças prévias:"), 0, 2);
        doencasArea = new TextArea(anamnese.getDoencasPrevias());
        doencasArea.setPrefRowCount(3);
        grid.add(doencasArea, 1, 2);
        fumanteCheck = new CheckBox("É fumante?");
        fumanteCheck.setSelected(anamnese.isFumante());
        grid.add(fumanteCheck, 1, 3);
        gravidaCheck = new CheckBox("Está grávida / gestante?");
        gravidaCheck.setSelected(anamnese.isGravida());
        grid.add(gravidaCheck, 1, 4);
        grid.add(new Label("Observações:"), 0, 5);
        obsArea = new TextArea(anamnese.getObservacoes());
        obsArea.setPrefRowCount(3);
        grid.add(obsArea, 1, 5);
        Button salvarButton = new Button("Salvar Anamnese");
        salvarButton.setOnAction(e -> salvarAnamnese());
        VBox layout = new VBox(20, grid, salvarButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        return layout;
    }

    // Ação "Salvar" da Aba 2 (Sem mudança)
    private void salvarAnamnese() {
        Anamnese anamnese = new Anamnese(
                paciente.getCpf(),
                alergiasField.getText(),
                medicamentosField.getText(),
                doencasArea.getText(),
                fumanteCheck.isSelected(),
                gravidaCheck.isSelected(),
                obsArea.getText()
        );
        boolean sucesso = prontuarioService.saveAnamnese(anamnese);
        Alert alert = new Alert(sucesso ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
        alert.setTitle(sucesso ? "Sucesso" : "Erro");
        alert.setContentText(sucesso ? "Anamnese salva com sucesso." : "Não foi possível salvar a anamnese.");
        alert.showAndWait();
    }

    // --- ABA 3: EVOLUÇÃO E PLANO (Sem mudança) ---
    private VBox createTratamentosTab() {
        TabPane tratamentosTabPane = new TabPane();
        Tab evolucaoTab = new Tab("Evolução (Descrição Dente a Dente)", createEvolucaoLog());
        evolucaoTab.setClosable(false);
        Tab odontogramaTab = new Tab("Odontograma Gráfico", createOdontogramaVisual());
        odontogramaTab.setClosable(false);
        tratamentosTabPane.getTabs().addAll(evolucaoTab, odontogramaTab);
        VBox layout = new VBox(tratamentosTabPane);
        VBox.setVgrow(tratamentosTabPane, Priority.ALWAYS);
        return layout;
    }

    // Sub-Aba "Evolução (Log)" (Sem mudança)
    private BorderPane createEvolucaoLog() {
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(20));
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.add(new Label("Data:"), 0, 0);
        dataTratamentoPicker = new DatePicker(LocalDate.now());
        formGrid.add(dataTratamentoPicker, 1, 0);
        formGrid.add(new Label("Dente/Região:"), 0, 1);
        denteField = new TextField();
        denteField.setPromptText("Ex: 18, 21-23, Região...");
        formGrid.add(denteField, 1, 1);
        formGrid.add(new Label("Descrição (por extenso):"), 0, 2);
        descricaoTratamentoArea = new TextArea();
        descricaoTratamentoArea.setPromptText("Ex: Restauração em resina composta na face oclusal...");
        descricaoTratamentoArea.setPrefRowCount(4);
        formGrid.add(descricaoTratamentoArea, 0, 3, 2, 1);
        Button adicionarBtn = new Button("Adicionar Lançamento");
        adicionarBtn.setOnAction(e -> salvarNovoTratamento());
        HBox buttonBox = new HBox(adicionarBtn);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        formGrid.add(buttonBox, 0, 4, 2, 1);
        layout.setTop(formGrid);
        tratamentosListView = new ListView<>();
        listaDeTratamentos = prontuarioService.getTratamentos(paciente.getCpf());
        tratamentosListView.setItems(listaDeTratamentos);
        layout.setCenter(tratamentosListView);
        BorderPane.setMargin(tratamentosListView, new Insets(20, 0, 0, 0));
        return layout;
    }

    // Ação "Salvar Lançamento" (Atualizada)
    private void salvarNovoTratamento() {
        String data = dataTratamentoPicker.getValue().toString();
        String dente = denteField.getText();
        String descricao = descricaoTratamentoArea.getText();

        if (descricao.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "A descrição é obrigatória.").show();
            return;
        }

        // --- ATUALIZADO: Passa o CPF do dentista logado ---
        Tratamento novoTratamento = new Tratamento(paciente.getCpf(), dentista.getCpf(), data, dente, descricao);
        Tratamento tratamentoSalvo = prontuarioService.addTratamento(novoTratamento);

        if (tratamentoSalvo != null) {
            listaDeTratamentos.add(0, tratamentoSalvo);
            denteField.clear();
            descricaoTratamentoArea.clear();
        } else {
            new Alert(Alert.AlertType.ERROR, "Não foi possível salvar o lançamento.").show();
        }
    }

    // Sub-Aba "Odontograma" (Sem mudança)
    private VBox createOdontogramaVisual() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.setAlignment(Pos.TOP_CENTER);
        Label labelInfo = new Label("Utilize o cursor para marcar as informações diretamente no odontograma.");
        labelInfo.setWrapText(true);
        labelInfo.setStyle("-fx-font-weight: bold; -fx-padding: 5px;");
        StackPane canvasContainer = new StackPane();
        canvasContainer.setStyle("-fx-border-color: lightgray; -fx-border-width: 1;");
        Image odontogramaBaseImage = null;
        try {
            InputStream is = getClass().getResourceAsStream("/images/odontograma_base.png");
            if (is != null) {
                odontogramaBaseImage = new Image(is);
            } else {
                System.err.println("Imagem odontograma_base.png não encontrada. Usando fallback.");
                odontogramaBaseImage = new Image("https://i.imgur.com/g8oA8Cg.png");
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar imagem do odontograma: " + e.getMessage());
            odontogramaBaseImage = new Image("https://i.imgur.com/g8oA8Cg.png");
        }
        ImageView imageView = new ImageView(odontogramaBaseImage);
        imageView.setFitWidth(750);
        imageView.setPreserveRatio(true);
        Canvas drawingCanvas = new Canvas(imageView.getFitWidth(), imageView.getFitHeight());
        GraphicsContext gc = drawingCanvas.getGraphicsContext2D();
        gc.setStroke(Color.RED);
        gc.setLineWidth(2);
        double[] lastX = {0};
        double[] lastY = {0};
        drawingCanvas.setOnMousePressed(event -> {
            lastX[0] = event.getX();
            lastY[0] = event.getY();
        });
        drawingCanvas.setOnMouseDragged(event -> {
            gc.strokeLine(lastX[0], lastY[0], event.getX(), event.getY());
            lastX[0] = event.getX();
            lastY[0] = event.getY();
        });
        Button clearButton = new Button("Limpar Desenho");
        clearButton.setOnAction(e -> gc.clearRect(0, 0, drawingCanvas.getWidth(), drawingCanvas.getHeight()));
        canvasContainer.getChildren().addAll(imageView, drawingCanvas);
        imageView.fitWidthProperty().addListener((obs, oldVal, newVal) -> {
            drawingCanvas.setWidth(newVal.doubleValue());
            drawingCanvas.setHeight(imageView.getFitHeight());
        });
        imageView.fitHeightProperty().addListener((obs, oldVal, newVal) -> {
            drawingCanvas.setHeight(newVal.doubleValue());
            drawingCanvas.setWidth(imageView.getFitWidth());
        });
        layout.getChildren().addAll(labelInfo, canvasContainer, clearButton);
        VBox.setVgrow(canvasContainer, Priority.ALWAYS);
        return layout;
    }
}