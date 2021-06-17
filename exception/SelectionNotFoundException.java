package exception;

public class SelectionNotFoundException extends Exception {
    private static String message = "The entered selection does not exist";

    public SelectionNotFoundException() {
        super(message);
    }
    
}
