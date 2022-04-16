package MainFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Application with a graphical user interface
 * @author Ilya Tsygankov<p>
 * Display frame class
 */
public class ShopFrame {
    // Main frame
    private JFrame ShopApp;

    // App buttons
    private JButton AddBtn;
    private JButton EditBtn;
    private JButton SaveBtn;
    private JButton DeleteBtn;
    private JButton WorkersInfoBtn;
    private JButton ProductsInfoBtn;
    private JButton ShopInfoBtn;
    private JButton SearchBtn;

    // Field for search info
    private JTextField SearchField;

    // Panels for buttons
    private JToolBar TopToolBar;
    private JToolBar LowToolBar;

    // Table elements
    private DefaultTableModel model;
    private JTable WorkerData;
    private JScrollPane WorkersTable;

    // Panel for low elements
    private JPanel LowPanel;

    // Build and show frame
    public void show()
    {
        // Build frame
        ShopApp = new JFrame("ShopApp");
        ShopApp.setSize(560, 400);
        ShopApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add buttons
        AddBtn = new JButton(new ImageIcon("./img/Add.png"));
        AddBtn.setToolTipText("Add");
        EditBtn = new JButton(new ImageIcon("./img/Edit.png"));
        EditBtn.setToolTipText("Change");
        SaveBtn = new JButton(new ImageIcon("./img/Save.png"));
        SaveBtn.setToolTipText("Save");
        DeleteBtn = new JButton(new ImageIcon("./img/Delete.png"));
        DeleteBtn.setToolTipText("Delete");
        WorkersInfoBtn = new JButton(new ImageIcon("./img/User.png"));
        WorkersInfoBtn.setToolTipText("Info about workers");
        ProductsInfoBtn = new JButton(new ImageIcon("./img/Products.png"));
        ProductsInfoBtn.setToolTipText("Info about products");
        ShopInfoBtn = new JButton(new ImageIcon("./img/Info.png"));
        ShopInfoBtn.setToolTipText("Info about shop");

        // Add buttons on TopToolBar
        TopToolBar = new JToolBar("TopTool panel");
        TopToolBar.add(AddBtn);
        TopToolBar.add(EditBtn);
        TopToolBar.add(SaveBtn);
        TopToolBar.add(DeleteBtn);

        ShopApp.setLayout(new BorderLayout());
        ShopApp.add(TopToolBar, BorderLayout.NORTH);

        // Add buttons on LowToolBar
        LowToolBar = new JToolBar("LowTool panel");
        LowToolBar.add(WorkersInfoBtn);
        LowToolBar.add(ProductsInfoBtn);
        LowToolBar.add(ShopInfoBtn);

        LowPanel = new JPanel();
        LowPanel.setSize(556, 34);
        LowPanel.add(LowToolBar);

        ShopApp.add(LowPanel, BorderLayout.SOUTH);

        // Add table
        String [] columns = {"Name", "Position"};
        String [][] data = {{"Ivanov Ivan", "Cashier"},
                            {"Ivanova Maria", "Cleaner"}};
        model = new DefaultTableModel(data, columns);
        WorkerData = new JTable(model);
        WorkersTable = new JScrollPane(WorkerData);

        ShopApp.add(WorkersTable, BorderLayout.CENTER);

        // Add search
        SearchField = new JTextField();
        SearchField.setPreferredSize(new Dimension(305, 25));
        SearchBtn = new JButton("Search");
        LowPanel.add(SearchField);
        LowPanel.add(SearchBtn);

        // Show frame
        ShopApp.pack();
        ShopApp.setVisible(true);
    }

    public static void main(String[] args)
    {
        new ShopFrame().show();
    }
}
