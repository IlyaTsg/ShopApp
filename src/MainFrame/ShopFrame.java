package MainFrame;

import org.w3c.dom.*;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Stack;

/**
 * Application with a graphical user interface
 * @author Ilya Tsygankov
 * <p>Display frame class</p>
 */
public class ShopFrame {
    // Main frame
    private JFrame ShopApp;

    // App buttons
    private JButton AddBtn;
    private JButton SaveBtn;
    private JButton DeleteBtn;
    private JButton WorkersInfoBtn;
    private JButton ProductsInfoBtn;
    private JButton ShopInfoBtn;
    private JButton SearchBtn;

    // Field for search info
    private JTextField SearchField;

    // Panels for buttons
    private JPanel TopToolBar;

    // Table elements
    private DefaultTableModel model;
    private JTable Data;
    private JScrollPane Table;

    // Buffers for loaded tables
    private DefaultTableModel WorkerTBuf;
    private DefaultTableModel ProductTBuf;

    // Panel for low elements
    private JPanel LowPanel;

    // Flags for tables
    private boolean WorkerTAlreadyLoad;
    private boolean ProductsTAlreadyLoad;

    // Mode of app work(workers table: 1, products table: 2)
    private int Mode;

    /**
     * Build and show frame
     */
    public void LaunchFrame()
    {
        // Build frame
        ShopApp = new JFrame("ShopApp");
        ShopApp.setSize(560, 400);
        ShopApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add buttons
        AddBtn = new JButton(new ImageIcon("./img/Add.png"));
        AddBtn.setToolTipText("Add");
        AddBtn.setPreferredSize(new Dimension(32, 32));
        SaveBtn = new JButton(new ImageIcon("./img/Save.png"));
        SaveBtn.setToolTipText("Save");
        SaveBtn.setPreferredSize(new Dimension(32, 32));
        DeleteBtn = new JButton(new ImageIcon("./img/Delete.png"));
        DeleteBtn.setToolTipText("Delete");
        DeleteBtn.setPreferredSize(new Dimension(32, 32));
        WorkersInfoBtn = new JButton(new ImageIcon("./img/User.png"));
        WorkersInfoBtn.setToolTipText("Info about workers");
        WorkersInfoBtn.setPreferredSize(new Dimension(32, 32));
        ProductsInfoBtn = new JButton(new ImageIcon("./img/Products.png"));
        ProductsInfoBtn.setToolTipText("Info about products");
        ProductsInfoBtn.setPreferredSize(new Dimension(32, 32));
        ShopInfoBtn = new JButton(new ImageIcon("./img/Info.png"));
        ShopInfoBtn.setToolTipText("Info about shop");
        ShopInfoBtn.setPreferredSize(new Dimension(32, 32));

        // Add buttons on TopToolBar
        TopToolBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        TopToolBar.add(AddBtn);
        TopToolBar.add(SaveBtn);
        TopToolBar.add(DeleteBtn);

        ShopApp.setLayout(new BorderLayout());
        ShopApp.add(TopToolBar, BorderLayout.NORTH);

        // Add buttons on LowToolBar

        LowPanel = new JPanel(new FlowLayout());
        LowPanel.setSize(556, 34);
        LowPanel.add(WorkersInfoBtn);
        LowPanel.add(ProductsInfoBtn);
        LowPanel.add(ShopInfoBtn);

        ShopApp.add(LowPanel, BorderLayout.SOUTH);

        // Default mode
        Mode = -1;

        // Initialize and Add Table on frame
        Table = new JScrollPane();

        WorkerTAlreadyLoad = false;
        ProductsTAlreadyLoad = false;

        LoadProductsTable();
        ShopApp.add(Table, BorderLayout.CENTER);

        // Add search
        SearchField = new JTextField();
        SearchField.setPreferredSize(new Dimension(305, 25));
        SearchBtn = new JButton("Search");
        LowPanel.add(SearchField);
        LowPanel.add(SearchBtn);

        // Add listeners
        AddBtn.addActionListener(new AddBtnListener());

        DeleteBtn.addActionListener(new DeleteBtnListener());

        SaveBtn.addActionListener(new SaveBtnListener());

        SearchBtn.addActionListener(new SearchBtnListener());

        WorkersInfoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoadWorkersTable();
            }
        });

        ProductsInfoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoadProductsTable();
            }
        });

        // Show frame
        ShopApp.setVisible(true);
    }

    /**
     * Save table in XML file
     */
    private void SaveTable()
    {
        String WorkersFileName = "databases/workers.xml";
        String ProductsFileName = "databases/products.xml";
        String FileName = "DefaultFileName";
        try{
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.newDocument();

            switch (Mode) {
                case 1 -> {
                    Node WorkersList = doc.createElement("WorkersList");
                    doc.appendChild(WorkersList);
                    for (int i = 0; i < model.getRowCount(); i++) {
                        Element Worker = doc.createElement("worker");
                        WorkersList.appendChild(Worker);
                        Worker.setAttribute("name", (String) model.getValueAt(i, 0));
                        Worker.setAttribute("position", (String) model.getValueAt(i, 1));
                    }
                    FileName = WorkersFileName;
                }
                case 2 -> {
                    Node ProductsList = doc.createElement("ProductsList");
                    doc.appendChild(ProductsList);
                    for (int i = 0; i < model.getRowCount(); i++) {
                        Element Product = doc.createElement("product");
                        ProductsList.appendChild(Product);
                        Product.setAttribute("name", (String) model.getValueAt(i, 0));
                        Product.setAttribute("venCode", (String) model.getValueAt(i, 1));
                        Product.setAttribute("number", (String) model.getValueAt(i, 2));
                    }
                    FileName = ProductsFileName;
                }
            }

            Transformer trans = TransformerFactory.newInstance().newTransformer();
            java.io.FileWriter writer = new FileWriter(FileName);
            trans.transform(new DOMSource(doc), new StreamResult(writer));
        }catch (ParserConfigurationException | TransformerException | IOException e) { e.printStackTrace(); }
    }

    /**
     * Listener for save button
     */
    private class SaveBtnListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            SaveTable();
        }
    }

    /**
     * Load workers table from XML file
     */
    private void LoadWorkersTable()
    {
        String FileName = "databases/workers.xml";
        if(!WorkerTAlreadyLoad) {
            try {
                // Initialize workers table
                String[] columns = {"Name", "Position"};
                String[][] data = {{"Worker name", "Position"}};
                model = new DefaultTableModel(data, columns);

                model.removeRow(0);

                // Add builder and new document
                DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document doc = builder.parse(FileName);
                doc.getDocumentElement().normalize();

                NodeList WorkersList = doc.getElementsByTagName("worker");
                for (int i = 0; i < WorkersList.getLength(); i++) {
                    Node worker = WorkersList.item(i);
                    NamedNodeMap attrs = worker.getAttributes();
                    String name = attrs.getNamedItem("name").getNodeValue();
                    String position = attrs.getNamedItem("position").getNodeValue();
                    model.addRow(new String[] {name, position});
                }

                WorkerTBuf = model; // Save model in buffer
                WorkerTAlreadyLoad = true;
            } catch (ParserConfigurationException | SAXException | IOException e) {
                e.printStackTrace();
            }
        }

        if(Mode != 1){ // If we need show table
            model = WorkerTBuf;
            Data = new JTable(model);
            Table.setViewportView(Data);
            Mode = 1;
        }
    }

    /**
     * Load products table from XML file
     */
    private void LoadProductsTable()
    {
        String FileName = "databases/products.xml";
        if(!ProductsTAlreadyLoad) {
            try {
                // Initialize products table
                String[] columns = {"Product", "Vendor code", "Number"};
                String[][] data = {{"Melon", "01", "45"}};
                model = new DefaultTableModel(data, columns);

                model.removeRow(0);

                // Add builder and new document
                DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document doc = builder.parse(FileName);
                doc.getDocumentElement().normalize();

                NodeList ProductsList = doc.getElementsByTagName("product");
                for (int i = 0; i < ProductsList.getLength(); i++) {
                    Node product = ProductsList.item(i);
                    NamedNodeMap attrs = product.getAttributes();
                    String name = attrs.getNamedItem("name").getNodeValue();
                    String venCode = attrs.getNamedItem("venCode").getNodeValue();
                    String number = attrs.getNamedItem("number").getNodeValue();
                    model.addRow(new String[] {name, venCode, number});
                }

                ProductTBuf = model; // Save model in buffer
                ProductsTAlreadyLoad = true;
            } catch (ParserConfigurationException | SAXException | IOException e) {
                e.printStackTrace();
            }
        }

        if(Mode != 2){ // If we need show table
            model = ProductTBuf;
            Data = new JTable(model);
            Table.setViewportView(Data);
            Mode = 2;
        }
    }

    /**
     * @param TName table name
     * @throws AppException.EmptyTable if table is empty, throws exception
     */
    private void CheckTable(DefaultTableModel TName) throws AppException.EmptyTable{
        if(model.getRowCount() == 0){
            throw new AppException.EmptyTable();
        }
    }

    /**
     * <p>Listener of delete button</p>
     * <p>Delete row in table, if it doesn't empty</p>
     */
    private class DeleteBtnListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            try{
                CheckTable(model);
                model.removeRow(Data.getSelectedRow());
            }catch (AppException.EmptyTable ex){
                JOptionPane.showMessageDialog(ShopApp, ex.getMessage());
            }catch (ArrayIndexOutOfBoundsException ex1){
                JOptionPane.showMessageDialog(ShopApp, "No line selected!");
            }
        }
    }

    /**
     * <p>Listener of Add button</p>
     * <p>Add new row on table</p>
     */
    private class AddBtnListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (Mode) {
                case (1) -> model.addRow(new String [] {"", ""});
                case (2) -> model.addRow(new String [] {"", "", ""});
            }
        }
    }

    /**
     * @param TFName text field name
     * @throws AppException.EmptyTextField if table is empty, throws exception
     */
    private void CheckTextField(JTextField TFName) throws AppException.EmptyTextField
    {
        String SField = TFName.getText();
        if(SField.length() == 0) throw new AppException.EmptyTextField();
    }

    /**
     * <p>Listener of search button</p>
     */
    private class SearchBtnListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                CheckTextField(SearchField);
                // Search some info
            }catch (AppException.EmptyTextField ex){
                JOptionPane.showMessageDialog(ShopApp, ex.getMessage());
            }
        }
    }

    public static void main(String[] args)
    {
        new ShopFrame().LaunchFrame();
    }
}




