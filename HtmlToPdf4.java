package s3;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class HtmlToPdf4 {
    public static void main(String[] args) {
        String htmlFilePath = "C:/htmltopdfusingjsoup/s4.html"; // Change this to your HTML file path
        String pdfFilePath = "C:/htmltopdfusingjsoup/s4.pdf";

        try {
            // Create PdfWriter instance
            PdfWriter writer = new PdfWriter(pdfFilePath);

            // Create PdfDocument instance
            PdfDocument pdf = new PdfDocument(writer);

            // Set the page size to A4 Landscape
            pdf.setDefaultPageSize(PageSize.A4.rotate());

            // Convert HTML to PDF
            HtmlConverter.convertToPdf(new FileInputStream(new File(htmlFilePath)), pdf);

            pdf.close();
            System.out.println("PDF Created!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
