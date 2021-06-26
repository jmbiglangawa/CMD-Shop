package exception;

import util.PrinterConstants;

public class NoItemInCartException extends Exception {
    private static String message = PrinterConstants.CART_NO_ITEMS;

    public NoItemInCartException() {
        super(message);
    }
}
