package exception;

public class ItemAlreadyExistsException extends Exception {
    private static String message = "The item with the given product Id {0} already exists in the shop";

    public ItemAlreadyExistsException(String message) {
        super(message);
    }

    public ItemAlreadyExistsException(Long productId) {
        super(String.format(message, productId));
    }
    
}
