package s1;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;

public class HtmlToPdf {

    public static void main(String[] args) {
        String inputHtmlFilePath = "C:/htmltopdfusingjsoup/Form1.html";
        String outputPdfFilePath = "C:/htmltopdfusingjsoup/satya.pdf";
        String baseUri = "file:/C:/htmltopdfusingjsoup/";

        try {
            // Read HTML content from file
            String htmlContent = new String(Files.readAllBytes(Paths.get(inputHtmlFilePath)));

            // Convert HTML to PDF and save to file
            convertHtmlToPdf(htmlContent, outputPdfFilePath, baseUri);
            System.out.println("PDF created successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void convertHtmlToPdf(String htmlContent, String outputPdfFilePath, String baseUri) {
        try (FileOutputStream outputStream = new FileOutputStream(outputPdfFilePath)) {
            ConverterProperties converterProperties = new ConverterProperties();
            converterProperties.setBaseUri(baseUri);
            HtmlConverter.convertToPdf(htmlContent, outputStream, converterProperties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
