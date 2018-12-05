import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Barcodes {
    public static final String DEST = "/pdf/barcode_table.pdf";

    public static void main(String[] args) throws IOException,
            DocumentException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new Barcodes().createPdf(DEST);
    }

    public void createPdf(String dest) throws IOException, DocumentException {
        //Document document = new Document();
        Document document = new Document(new Rectangle(PageSize.B8).rotate());

        BaseFont fonty = BaseFont.createFont("/fonts/CaviarDreams.TTF", BaseFont.IDENTITY_H,       BaseFont.NOT_EMBEDDED);
        fonty.setSubset(true);
        String titleDesc="3D Bearded Cat Silicone Soft Cell Phone Cover For IPhone 6s / 6 - Pink";
        String priceString= "€ "+String.valueOf(4.90);
        String splited[] = StringUtils.split(titleDesc);
        List<String> tempSplittedlist = new ArrayList<>(Arrays.asList(splited));
        tempSplittedlist.size();
        // tempSplittedlist.length()
        Phrase title = new Phrase(titleDesc, new Font(fonty,3));
        Phrase price = new Phrase(priceString, new Font(fonty,3));




       // Document document = new Document(new Rectangle(PageSize.B8).rotate());

        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
        //PdfPRow pdfPRow= new PdfPRow();

        document.open();
        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(50);
        table.setHeaderRows(1);
        table.addCell(title);
        table.addCell(price);
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