package MainFrame;

import org.junit.Before;
import org.junit.Test;

import javax.swing.table.DefaultTableModel;

import static org.testng.Assert.fail;

/**
 * Tests for ShopFrame
 */
public class ShopFrameTest {
    private ShopFrame NewFrame;

    @Before
    public void SetUp(){
        NewFrame = new ShopFrame();
        NewFrame.LaunchFrame();
    }

    /**
     * Test method CheckTextField
     */
    @Test
    public void TestCheckTextField(){
        NewFrame.SearchField.setText(""); // Add empty string in search field
        try {
            NewFrame.CheckTextField(NewFrame.SearchField);
            fail(); // If empty field doesn't throw exception
        }catch(AppException.EmptyTextField ex){
            // pass
        }
    }

    /**
     * Test method CheckTable
     */
    @Test
    public void TestCheckTable(){
        String[] columns = {"Product", "Vendor code", "Number"};
        String[][] data = {{"Melon", "01", "45"}};
        DefaultTableModel model1 = new DefaultTableModel(data, columns);
        model1.removeRow(0);

        try {
            NewFrame.CheckTable(model1);
            fail();
        }catch (AppException.EmptyTable ex){
            //pass
        }
    }
}
