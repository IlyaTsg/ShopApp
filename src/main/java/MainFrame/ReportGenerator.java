package MainFrame;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * PDF Reports from XML-file Generator Class
 */
public class ReportGenerator {
    public static void GenerateWorkers(String datasource, String resultpath, String ShopAddress) throws FileNotFoundException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(resultpath));
        pdfDoc.addNewPage();

        Document doc = new Document(pdfDoc);

        Paragraph Address = new Paragraph("Address: " + ShopAddress);
        Address.setFontSize(20);

        Paragraph ReportType = new Paragraph("Report: Workers");
        ReportType.setFontSize(20);

        float[] ColumnWidth = {200, 200};
        Table table = new Table(ColumnWidth);

        table.addCell(new Cell().add("Name"));
        table.addCell(new Cell().add("Position"));

        try{
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            org.w3c.dom.Document WorkersDoc = builder.parse(datasource);
            WorkersDoc.getDocumentElement().normalize();

            NodeList WorkersList = WorkersDoc.getElementsByTagName("worker");
            for (int i = 0; i < WorkersList.getLength(); i++) {
                Node worker = WorkersList.item(i);
                NamedNodeMap attrs = worker.getAttributes();
                String name = attrs.getNamedItem("name").getNodeValue();
                String position = attrs.getNamedItem("position").getNodeValue();

                table.addCell(new Cell().add(name));
                table.addCell(new Cell().add(position));
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        doc.add(Address);
        doc.add(ReportType);
        doc.add(table);

        doc.close();
    }
    public static void GenerateProducts(String datasource, String resultpath, String ShopAddress) throws FileNotFoundException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(resultpath));
        pdfDoc.addNewPage();

        Document doc = new Document(pdfDoc);

        Paragraph Address = new Paragraph("Address: " + ShopAddress);
        Address.setFontSize(20);

        Paragraph ReportType = new Paragraph("Report: Products");
        ReportType.setFontSize(20);

        float[] ColumnWidth = {150, 150, 150};
        Table table = new Table(ColumnWidth);

        table.addCell(new Cell().add("Product"));
        table.addCell(new Cell().add("Vendor Code"));
        table.addCell(new Cell().add("Number"));

        try{
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            org.w3c.dom.Document ProductsDoc = builder.parse(datasource);
            ProductsDoc.getDocumentElement().normalize();

            NodeList ProductsList = ProductsDoc.getElementsByTagName("product");
            for (int i = 0; i < ProductsList.getLength(); i++) {
                Node product = ProductsList.item(i);
                NamedNodeMap attrs = product.getAttributes();
                String name = attrs.getNamedItem("name").getNodeValue();
                String venCode = attrs.getNamedItem("venCode").getNodeValue();
                String number = attrs.getNamedItem("number").getNodeValue();

                table.addCell(new Cell().add(name));
                table.addCell(new Cell().add(venCode));
                table.addCell(new Cell().add(number));
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        doc.add(Address);
        doc.add(ReportType);
        doc.add(table);

        doc.close();
    }
}
