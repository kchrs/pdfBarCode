import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;



public class BarCode128 {

    public static void main(String[] args) throws IOException, DocumentException {

        Document document = new Document(new Rectangle(PageSize.A8).rotate());

        //PdfPage pdfPage = new PdfPage();


        BaseFont fonty = BaseFont.createFont("/fonts/CaviarDreams.TTF", BaseFont.IDENTITY_H,       BaseFont.NOT_EMBEDDED);
        fonty.setSubset(true);
        Phrase title = new Phrase("3D Bearded Cat Silicone Soft Cell Phone Cover For IPhone 6s / 6 - Pink", new Font(fonty,4));

        PdfWriter writer = PdfWriter.getInstance(document,
                new FileOutputStream("/Users/kchris/Desktop/test.pdf"));

        document.open();

        document.add(new Paragraph(title));

        Barcode128 code128 = new Barcode128();
        code128.setGenerateChecksum(true);
        code128.setCode("500658");

        document.add(code128.createImageWithBarcode(writer.getDirectContent(), null, null));
        document.close();

        System.out.println("Document Generated...!!!!!!");
    }
}