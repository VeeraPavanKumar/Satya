package s1;


import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.layout.renderer.DocumentRenderer;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HtmlToPdf {

    public static void main(String[] args) {
        String inputHtmlFilePath = "C:/htmltopdfusingjsoup/satya.html";
        String outputPdfFilePath = "C:/htmltopdfusingjsoup/satya.pdf";
        String baseUri = "file:///C:/htmltopdfusingjsoup/";

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

            FontProvider fontProvider = new FontProvider();
            fontProvider.addStandardPdfFonts();
            fontProvider.addSystemFonts();
            converterProperties.setFontProvider(fontProvider);

            PdfWriter writer = new PdfWriter(outputPdfFilePath);
            PdfDocument pdfDoc = new PdfDocument(writer);
            pdfDoc.setDefaultPageSize(PageSize.A3.rotate());
            Document document = new Document(pdfDoc);
            HtmlConverter.convertToPdf(htmlContent, pdfDoc, converterProperties);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
