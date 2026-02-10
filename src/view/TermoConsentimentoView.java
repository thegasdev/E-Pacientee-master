package view;

// Removidos os imports do Canvas e SwingFXUtils
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.User;
import utils.GeradorPDF;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicBoolean;

public class TermoConsentimentoView {

    private final User paciente;
    private final User dentista;
    private final String tratamentoProposto;
    private String termoCompleto;

    // Construtor atualizado para receber o tratamento
    public TermoConsentimentoView(User paciente, User dentista, String tratamentoProposto) {
        this.paciente = paciente;
        this.dentista = dentista;
        this.tratamentoProposto = tratamentoProposto;
        this.termoCompleto = preencherTermo(tratamentoProposto);
    }

    /**
     * Exibe a janela modal e retorna true se o usuário aceitou, false caso contrário.
     */
    public boolean display(Stage ownerStage) {
        AtomicBoolean aceito = new AtomicBoolean(false);

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initOwner(ownerStage);
        window.setTitle("TERMO DE CONSENTIMENTO LIVRE E ESCLARECIDO (TCLE) - Versão Digital");
        window.setMinWidth(700);
        window.setMinHeight(600);

        // --- Área do Texto do Termo ---
        TextArea termsArea = new TextArea();
        termsArea.setText(this.termoCompleto);
        termsArea.setEditable(false);
        termsArea.setWrapText(true);
        termsArea.setPrefHeight(400);

        // --- NOVOS CAMPOS DE ESCOLHA ---

        // 1. Autorização de Uso de Dados
        VBox usoDadosBox = new VBox(5);
        usoDadosBox.setPadding(new Insets(10, 0, 10, 0));
        Label usoDadosLabel = new Label("AUTORIZAÇÃO PARA USO DE DADOS (Fins Acadêmicos/Científicos)");
        usoDadosLabel.setStyle("-fx-font-weight: bold;");
        ToggleGroup usoDadosGroup = new ToggleGroup();
        RadioButton permitirSim = new RadioButton("PERMITO a utilização para fins acadêmicos/científicos, desde que meu anonimato seja preservado.");
        RadioButton permitirNao = new RadioButton("NÃO PERMITO a utilização para fins acadêmicos/científicos.");
        permitirSim.setToggleGroup(usoDadosGroup);
        permitirNao.setToggleGroup(usoDadosGroup);
        usoDadosBox.getChildren().addAll(usoDadosLabel, permitirSim, permitirNao);

        // 2. Declaração de Consentimento Digital
        CheckBox declaracaoCheck = new CheckBox("DECLARAÇÃO DE CONSENTIMENTO DIGITAL");
        declaracaoCheck.setStyle("-fx-font-weight: bold;");
        Label declaracaoTexto = new Label(
                "Ao marcar esta caixa, eu declaro que: Li (ou este termo foi lido para mim) e compreendi integralmente o seu conteúdo; " +
                        "Tive a oportunidade de tirar todas as minhas dúvidas; " +
                        "Estou ciente de que posso revogar este consentimento a qualquer momento (antes da realização do procedimento); " +
                        "Concordo voluntariamente com o tratamento proposto e com todos os termos aqui descritos."
        );
        declaracaoTexto.setWrapText(true);
        declaracaoTexto.setPadding(new Insets(0, 0, 0, 25)); // Indentação

        // --- Botões de Ação ---
        Button acceptButton = new Button("Aceitar e Gerar PDF");
        acceptButton.setDisable(true); // Começa desabilitado

        Button closeButton = new Button("Recusar");

        // --- Lógica para habilitar o botão ---
        Runnable checkFields = () -> {
            boolean consentChecked = declaracaoCheck.isSelected();
            boolean usoDadosSelected = usoDadosGroup.getSelectedToggle() != null;
            acceptButton.setDisable(!(consentChecked && usoDadosSelected));
        };
        declaracaoCheck.setOnAction(e -> checkFields.run());
        usoDadosGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> checkFields.run());

        // Ações dos botões
        acceptButton.setOnAction(e -> {
            try {
                boolean permitiuUso = permitirSim.isSelected();

                // Tenta gerar o PDF
                GeradorPDF.gerarTCLE(paciente, dentista, this.termoCompleto, permitiuUso);

                aceito.set(true);
                window.close();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Erro ao gerar PDF: " + ex.getMessage()).show();
            }
        });

        closeButton.setOnAction(e -> {
            aceito.set(false);
            window.close();
        });

        // Layout
        HBox buttonBox = new HBox(10, closeButton, acceptButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        VBox bottomContainer = new VBox(15, usoDadosBox, declaracaoCheck, declaracaoTexto, buttonBox);
        bottomContainer.setPadding(new Insets(10, 0, 10, 0));

        BorderPane mainLayout = new BorderPane();
        mainLayout.setCenter(termsArea);
        mainLayout.setBottom(bottomContainer);
        mainLayout.setPadding(new Insets(15));

        Scene scene = new Scene(mainLayout, 800, 750);
        window.setScene(scene);
        window.showAndWait();

        return aceito.get();
    }


    private String preencherTermo(String tratamentoProposto) {
        // Dados do Paciente
        String nomePaciente = paciente.getNome() + " " + paciente.getSobrenome();
        String cpfPaciente = paciente.getCpf();

        // Dados do Dentista (puxados do objeto 'dentista')
        String nomeDentista = dentista.getNome() + " " + dentista.getSobrenome();
        String croDentista = dentista.getCro() != null ? dentista.getCro() : "_________";
        String localClinica = dentista.getClinicaLocalizacao() != null ? dentista.getClinicaLocalizacao() : "________________";

        // --- SEU NOVO TEXTO COMPLETO ---
        String termoBase = "TERMO DE CONSENTIMENTO LIVRE E ESCLARECIDO (TCLE) - Versão Digital\n" +
                "Atenção: Este é um documento legal. Leia com atenção antes de aceitar.\n\n" +
                "DADOS DO PROFISSIONAL RESPONSÁVEL:\n\n" +
                "Cirurgião-Dentista: " + nomeDentista + "\n" +
                "Inscrição no CRO-SP: " + croDentista + "\n" +
                "Consultório: " + localClinica + "\n\n" +
                "DADOS DO PACIENTE:\n\n" +
                "Paciente: " + nomePaciente + "\n" +
                "CPF: " + cpfPaciente + "\n" +
                "(Se aplicável) Responsável Legal por: ________________________\n\n" +
                "Ao aceitar digitalmente este termo, Eu, " + nomePaciente + ", (ou como responsável legal por ________________________), " +
                "declaro que o(a) Cirurgião(ã)-Dentista acima identificado(a) me informou e esclareceu, em linguagem clara e acessível, sobre os seguintes pontos referentes ao tratamento odontológico proposto:\n\n" +
                "1. Diagnóstico e Tratamento: Fui informado(a) sobre meu diagnóstico e sobre o tratamento específico proposto, que consta em meu prontuário como:\n" +
                "   Tratamento Proposto: " + tratamentoProposto + "\n\n" +
                "2. Objetivos e Benefícios: Compreendi os objetivos do tratamento e os benefícios esperados (ex: alívio da dor, restauração da função, melhoria estética, etc.).\n\n" +
                "3. Riscos e Complicações: Fui cientificado(a) sobre os riscos e complicações potenciais mais frequentes associados ao tratamento " + tratamentoProposto + ", " +
                "que podem incluir, mas não se limitam a: dor pós-operatória, inchaço, infecção, hemorragia, reações alérgicas, parestesia (perda de sensibilidade) e a possível necessidade de tratamentos complementares.\n\n" +
                "4. Alternativas: Foram-me apresentadas as alternativas de tratamento viáveis para o meu caso, bem como as consequências e riscos de não realizar nenhum tratamento.\n\n" +
                "5. Dúvidas: Tive a oportunidade de fazer todas as perguntas necessárias, e todas foram respondidas de forma satisfatória.\n\n" +
                "AUTORIZAÇÃO DE TRATAMENTO\n" +
                "Diante do exposto, sinto-me devidamente esclarecido(a) e AUTORIZO voluntariamente o(a) Dr(a). " + nomeDentista + " e sua equipe a realizar o tratamento descrito acima.\n\n" +
                "CUSTOS\n" +
                "Declaro estar ciente e de acordo com o planejamento de custos (orçamento) referente ao tratamento proposto, que me foi apresentado e aprovado digitalmente através da plataforma ePacient.";

        return termoBase;
    }
}