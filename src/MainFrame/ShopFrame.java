package MainFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

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
    private JPanel TopToolBar;

    // Table elements
    private DefaultTableModel model;
    private JTable WorkerData;
    private JScrollPane WorkersTable;

    // Panel for low elements
    private JPanel LowPanel;

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
        EditBtn = new JButton(new ImageIcon("./img/Edit.png"));
        EditBtn.setToolTipText("Change");
        EditBtn.setPreferredSize(new Dimension(32, 32));
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
        TopToolBar.add(EditBtn);
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

        // Add listeners
        EditBtn.setActionCommand("Edit is pressed");
        EditBtn.addActionListener(new EditBtnListener());

        AddBtn.setActionCommand("Add is pressed!");
        AddBtn.addActionListener(new AddBtnListener());

        DeleteBtn.setActionCommand("Delete is pressed");
        DeleteBtn.addActionListener(new DeleteBtnListener());

        SearchBtn.addActionListener(new SearchBtnListener());

        // Show frame
        ShopApp.setVisible(true);
    }

    /**
     * <p>Listener of edit button</p>
     * <p>Show dialog window</p>
     */
    private static class EditBtnListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(e.getActionCommand());
            JOptionPane.showMessageDialog(null, e.getActionCommand());
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
            System.out.println(e.getActionCommand());
            try{
                CheckTable(model);
                model.removeRow(WorkerData.getSelectedRow());
            }catch (AppException.EmptyTable ex){
                JOptionPane.showMessageDialog(ShopApp, ex.getMessage());
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
            System.out.println(e.getActionCommand());
            model.addRow(new String [] {"Test Worker" + model.getRowCount(), "Test Position"});
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




