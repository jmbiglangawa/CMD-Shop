package exception;

public class ItemNotFoundException extends Exception {
    private static String message = "The item with the given product Id {0} is not found";

    public ItemNotFoundException(String message) {
        super(message);
    }

    public ItemNotFoundException(Long productId) {
        super(String.format(message, productId));
    }
    
}
