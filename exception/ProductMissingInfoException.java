package exception;

public class ProductMissingInfoException extends Exception {
    private static String message = "The item you are trying to add is missing {0}";

    public ProductMissingInfoException(String missingProperty) {
        super(String.format(message, missingProperty));
    }
    
}
