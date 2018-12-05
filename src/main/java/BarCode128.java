import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.commons.lang3.StringUtils;


public class BarCode128 {

    public static void main(String[] args) throws IOException, DocumentException {

        Document document = new Document(new Rectangle(PageSize.B8).rotate());
        //document.setPageSize(new Rectangle(PageSize.B4));
        BaseFont fonty = BaseFont.createFont("/fonts/CaviarDreams.TTF", BaseFont.IDENTITY_H,       BaseFont.NOT_EMBEDDED);
        fonty.setSubset(true);
        String titleDesc="3D Bearded Cat Silicone Soft Cell Phone\n" +
                         " Cover For IPhone 6s / 6 - Pink";
        String priceString= "€ "+String.valueOf(4.90);
       String splited[] = StringUtils.split(titleDesc);
       List<String> tempSplittedlist = new ArrayList<>(Arrays.asList(splited));
       tempSplittedlist.size();
       // tempSplittedlist.length()
        Phrase title = new Phrase(titleDesc, new Font(fonty,3));
        Phrase price = new Phrase(priceString, new Font(fonty,3));
        Paragraph titlePar = new Paragraph(title);
        titlePar.setAlignment(Element.ALIGN_CENTER);
        Paragraph pricePar = new Paragraph(price);
        pricePar.setAlignment(Element.ALIGN_CENTER);
        PdfWriter writer = PdfWriter.getInstance(document,
                new FileOutputStream("/pdf/test.pdf"));
        document.open();
        document.add(titlePar);
        document.add(pricePar);
        Barcode128 code128 = new Barcode128();
        code128.setGenerateChecksum(true);
        code128.setCode("500658");
       // code128.setTextAlignment(Element.ALIGN_CENTER);
        document.add(code128.createImageWithBarcode(writer.getDirectContent(), null, null));

        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(20);
        table.addCell(createBarcode(writer, String.format("%08d", 1)));
        document.add(table);


        document.close();
        System.out.println("Document Generated...!!!!!!");
    }

    public void createPdf(String dest) throws IOException, DocumentException {
        Document document = new Document();

        // Document document = new Document(new Rectangle(PageSize.B8).rotate());

        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(20);
        table.addCell(createBarcode(writer, String.format("%08d", 1)));
        document.add(table);
        document.close();
    }

    public static PdfPCell createBarcode(PdfWriter writer, String code) throws DocumentException, IOException {
        BarcodeEAN barcode = new BarcodeEAN();
        barcode.setCodeType(Barcode.EAN8);
        barcode.setCode(code);
        PdfPCell cell = new PdfPCell(barcode.createImageWithBarcode(writer.getDirectContent(), BaseColor.BLACK, BaseColor.GRAY), true);
        cell.setPadding(10);
        return cell;
    }

}