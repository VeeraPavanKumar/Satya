package hhh;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

public class HtmlToPdf {

    public static void main(String[] args) {
        try {
            File htmlFile = new File("C:/htmltopdfusingjsoup/Form.html");
            Document doc = Jsoup.parse(htmlFile, "UTF-8");
            doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml);

            try (OutputStream os = new FileOutputStream("C:/htmltopdfusingjsoup/output.pdf")) {
                ITextRenderer renderer = new ITextRenderer();
                SharedContext cntxt = renderer.getSharedContext();
                cntxt.setPrint(true);
                cntxt.setInteractive(false);

                String baseUrl = FileSystems.getDefault().getPath("C:/htmltopdfusingjsoup")
                        .toUri().toURL().toString();

                renderer.setDocumentFromString(doc.html(), baseUrl);
                renderer.layout();
                renderer.createPDF(os);

                System.out.println("done");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
