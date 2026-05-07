package utils;

// Removidas importações de SwingFXUtils e Image
import model.User;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject; // Pode ser removido se não for usar

// Imports de AWT (para abrir o arquivo) e IO
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
// Imports de tempo (para o timestamp)
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GeradorPDF {

    // Assinatura do método ATUALIZADA (removemos Image, adicionamos permitiuUso)
    public static void gerarTCLE(User paciente, User dentista, String textoDoTermo, boolean permitiuUso) throws IOException {
        String fileName = "TCLE_" + paciente.getCpf() + "_" + paciente.getNome() + ".pdf";

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 9);
            contentStream.setLeading(14.5f);

            float margin = 50;
            float y = page.getMediaBox().getUpperRightY() - margin;
            float width = page.getMediaBox().getWidth() - 2 * margin;

            // --- Escreve o texto principal do termo ---
            String[] lines = textoDoTermo.split("\n");
            contentStream.beginText();
            contentStream.newLineAtOffset(margin, y);

            for (String line : lines) {
                // (Lógica simples de quebra de linha)
                if (line.isEmpty()) {
                    contentStream.newLine();
                    y -= 14.5f;
                    continue;
                }
                String[] words = line.split(" ");
                StringBuilder wrappedLine = new StringBuilder();
                for (String word : words) {
                    float wordWidth = new PDType1Font(Standard14Fonts.FontName.HELVETICA).getStringWidth(wrappedLine + " " + word) / 1000 * 9;
                    if (wordWidth > width) {
                        contentStream.showText(wrappedLine.toString());
                        contentStream.newLine();
                        y -= 14.5f;
                        wrappedLine = new StringBuilder(word + " ");
                    } else {
                        wrappedLine.append(word).append(" ");
                    }
                }
                contentStream.showText(wrappedLine.toString());
                contentStream.newLine();
                y -= 14.5f;
            }

            // --- NOVO RODAPÉ COM ESCOLHAS E REGISTRO ---

            // 1. Escolha de Uso de Dados
            y -= 30; // Espaço
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 10);
            contentStream.newLineAtOffset(0, -30); // Move para baixo
            contentStream.showText("AUTORIZAÇÃO PARA USO DE DADOS (Fins Acadêmicos/Científicos)");
            contentStream.newLine();
            y -= 15;
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 10);
            String escolha = permitiuUso
                    ? "[X] PERMITO a utilização para fins acadêmicos/científicos, desde que meu anonimato seja preservado."
                    : "[X] NÃO PERMITO a utilização para fins acadêmicos/científicos.";
            contentStream.showText(escolha);
            contentStream.newLine();
            y -= 15;

            // 2. Declaração de Consentimento
            y -= 15;
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 10);
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("[X] DECLARAÇÃO DE CONSENTIMENTO DIGITAL");
            contentStream.newLine();
            y -= 15;
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 9);
            contentStream.showText("Declaro que li e compreendi o termo, tive minhas dúvidas sanadas e concordo voluntariamente com o tratamento.");
            contentStream.newLine();
            y -= 15;

            // 3. Registro do Sistema
            y -= 30;
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_OBLIQUE), 8);
            contentStream.newLineAtOffset(0, -30);
            contentStream.showText("(ÁREA DE REGISTRO DO SISTEMA)");
            contentStream.newLine(); y -= 12;
            contentStream.showText("Documento Aceito por: " + paciente.getNome() + " " + paciente.getSobrenome() + " (CPF: " + paciente.getCpf() + ")");
            contentStream.newLine(); y -= 12;
            contentStream.showText("Data e Hora do Aceite: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            contentStream.newLine(); y -= 12;
            contentStream.showText("Versão do Documento: TCLE-v1.2-Digital");

            contentStream.endText();
            contentStream.close();

            // Salva o documento
            document.save(fileName);

            // Tenta abrir o PDF gerado
            File file = new File(fileName);
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().open(file);
                } catch (Exception e) {
                    System.err.println("PDF salvo, mas não foi possível abri-lo automaticamente: " + e.getMessage());
                }
            }
        }
    }
}