package s5;


import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class HtmlToPdf {
    public static void main(String[] args) {
        String htmlFilePath = "C:/htpdfbox/satya.html"; // Path to your HTML file
        String pdfFilePath = "C:/htpdfbox/satya.pdf"; // Path to save PDF file
        String tempHtmlFilePath = "C:/htpdfbox/temp.html"; // Temporary HTML file path
        String imagesFolder = "C:/htpdfbox/images"; // Folder to save decoded images

        try {
            // Parse the HTML file
            Document htmlDoc = Jsoup.parse(new File(htmlFilePath), "UTF-8");

            // Create the images folder if it does not exist
            Files.createDirectories(Paths.get(imagesFolder));

            // Find all image elements
            Elements imgElements = htmlDoc.select("img");

            // Process each image element
            for (Element imgElement : imgElements) {
                String imgSrc = imgElement.attr("src");

                // Check if the image source is Base64 encoded
                if (imgSrc.startsWith("data:image")) {
                    // Extract the Base64 string from the image source
                    String base64Image = imgSrc.split(",")[1];
                    // Decode Base64 string to bytes
                    byte[] imageBytes = Base64.getDecoder().decode(base64Image);
                    // Generate a unique file name
                    String imageName = "image_" + System.currentTimeMillis() + ".png";
                    // Write decoded bytes to a file
                    try (OutputStream out = new FileOutputStream(Paths.get(imagesFolder, imageName).toFile())) {
                        out.write(imageBytes);
                    }
                    // Update the src attribute to the local path
                    imgElement.attr("src", imagesFolder + "/" + imageName);
                }
            }

            // Save the updated HTML to a temporary file
            try (FileWriter writer = new FileWriter(tempHtmlFilePath)) {
                writer.write(htmlDoc.html());
            }

            // Create PdfWriter instance
            PdfWriter writer = new PdfWriter(pdfFilePath);

            // Create PdfDocument instance
            PdfDocument pdf = new PdfDocument(writer);

            // Set the page size to A3 Landscape
            pdf.setDefaultPageSize(PageSize.A3.rotate());

            // Convert updated HTML to PDF
            HtmlConverter.convertToPdf(new FileInputStream(new File(tempHtmlFilePath)), pdf);

            pdf.close();
            System.out.println("PDF Created!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
