package s2;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class HtmlToPdf {
    public static void main(String[] args) {
        // Input HTML file
        String htmlFilePath = "C:/htmltopdfusingjsoup/satya.html";
        // Output PDF file
        String pdfFilePath = "C:/htmltopdfusingjsoup/satya.pdf";

        convertHtmlToPdf(htmlFilePath, pdfFilePath);
    }

    public static void convertHtmlToPdf(String htmlFilePath, String pdfFilePath) {
        // Create a PdfWriter
        try (PdfWriter writer = new PdfWriter(new FileOutputStream(pdfFilePath))) {
            // Set the page size to A4 landscape
            PageSize pageSize = PageSize.A4.rotate();
            // Create a PdfDocument with the specified page size
            PdfDocument pdfDocument = new PdfDocument(writer);
            pdfDocument.setDefaultPageSize(pageSize);
            Document document = new Document(pdfDocument);

            // Adjust margins to avoid content cutting off
            float leftMargin = 5; // Decrease left margin
            float rightMargin = 20;
            float topMargin = 20;
            float bottomMargin = 20;
            document.setMargins(topMargin, rightMargin, bottomMargin, leftMargin);

            // Create the ConverterProperties
            ConverterProperties converterProperties = new ConverterProperties();
            converterProperties.setCharset("UTF-8");

            // Ensure proper font rendering
            DefaultFontProvider fontProvider = new DefaultFontProvider(true, true, true);
            converterProperties.setFontProvider(fontProvider);

            // Base URI for resolving relative URLs in the HTML
            converterProperties.setBaseUri(new File(htmlFilePath).getParent());

            // Read the HTML file
            FileInputStream htmlStream = new FileInputStream(new File(htmlFilePath));

            // Convert HTML to PDF
            HtmlConverter.convertToPdf(htmlStream, pdfDocument, converterProperties);

            // Close the document
            document.close();

            System.out.println("PDF created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
