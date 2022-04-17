package MainFrame;

/**
 * Class of exceptions
 */
public class AppException {
    /**
     * Exception: if table is empty
     */
    public static class EmptyTable extends Exception
    {
        public EmptyTable(){
            super("Table is empty");
        }
    }

    /**
     * Exception: if text field is empty
     */
    public static class EmptyTextField extends Exception
    {
        public EmptyTextField(){
            super("Text field is empty");
        }
    }
}
