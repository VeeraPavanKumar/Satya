package s4;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class HtmlToPdf {
    public static void main(String[] args) {
        String htmlFilePath = "C:/htmltopdfusingjsoup/satya.html"; // Path to your HTML file
        String pdfFilePath = "C:/htmltopdfusingjsoup/satya.pdf"; // Path to save PDF file
        String tempHtmlFilePath = "C:/htmltopdfusingjsoup/temp.html"; // Temporary HTML file path
        String imagesFolder = "C:/htmltopdfusingjsoup/images"; // Folder to save downloaded images

        try {
            // Parse the HTML file
            Document htmlDoc = Jsoup.parse(new File(htmlFilePath), "UTF-8");

            // Create the images folder if it does not exist
            Files.createDirectories(Paths.get(imagesFolder));

            // Find all image elements
            Elements imgElements = htmlDoc.select("img");

            // Download images and update src attributes
            for (Element imgElement : imgElements) {
                String imgUrl = imgElement.attr("src");
                String imageName;

                // Check if the image source is a URL or a local path
                if (isValidURL(imgUrl)) {
                    imageName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1);
                    downloadImageFromURL(imgUrl, imagesFolder, imageName);
                } else {
                    imageName = new File(imgUrl).getName();
                    // If it's a local path, copy the file to the images folder
                    copyLocalImage(imgUrl, imagesFolder, imageName);
                }

                // Update the src attribute to the local path
                imgElement.attr("src", imagesFolder + "/" + imageName);
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

    // Helper method to check if a string is a valid URL
    private static boolean isValidURL(String urlString) {
        try {
            new URL(urlString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Helper method to download an image from a URL and override existing file if present
    private static void downloadImageFromURL(String imgUrl, String destinationFolder, String imageName) throws IOException {
        try (InputStream in = new URL(imgUrl).openStream()) {
            Files.copy(in, Paths.get(destinationFolder, imageName), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    // Helper method to copy a local image file to the destination folder and override existing file if present
    private static void copyLocalImage(String sourcePath, String destinationFolder, String imageName) throws IOException {
        Files.copy(Paths.get(sourcePath), Paths.get(destinationFolder, imageName), StandardCopyOption.REPLACE_EXISTING);
    }
}
