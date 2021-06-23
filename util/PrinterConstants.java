package util;

public class PrinterConstants {
    
    public static final String[] MAIN_MENU = {
        "1. Go shopping",
        "2. Manage items",
        "3. Quit"
    };

    public static final String[] GO_SHOPPING = {
        "Shopping directory"
    };

    public static final String NON_PERISHABLE_HEADER = " - Non-perishable";
    public static final String PERISHABLE_HEADER = " - Perishable";
    public static final String ITEMS_HEADER = "Items";
    public static final String MANAGE_ITEMS_HEADER = "Manage items";

    public static final String[] MANAGE_ITEMS = {
        MANAGE_ITEMS_HEADER,
        "1. Add",
        "2. Remove",
        "3. Modify",
        "4. Display all",
        "5. Return"
    };

    public static final String MANAGE_ADD = MANAGE_ITEMS_HEADER + " (ADD)";
    public static final String MANAGE_MODIFY = MANAGE_ITEMS_HEADER + " (MODIFY)";
    public static final String MANAGE_REMOVE = MANAGE_ITEMS_HEADER + " (REMOVE)";
    
    public static final String[] MANAGE_ADD_INPUTS = {
        "Id",
	    "Name",
	    "Price",
	    "Is Perishable?[Y/N]"
    };

    public static final String NOTIF_ADD = "Your item has been added to the shop!";
    public static final String NOTIF_REMOVE = "Your item has been removed from the shop!";
    public static final String NOTIF_MODIFY = "Your item has been modified!";

    public static final String COMMON_INPUT = "Please enter your input";
    public static final String EXCEPTION_MESSAGE = "An unknown error has occurred";
    public static final String EXIT_MESSAGE = "\n\nThank you for using this app!";

    public static final String QUESTION_MODIFY_1 = "Enter your product ID";
    public static final String QUESTION_MODIFY_2 = "Your product details:\n" +
        "[1] Id: %s \n" +
        "[2] Name: %s \n" +
        "[3] Price: %s \n" +
        "[4] Is perishable: %s";
    public static final String QUESTION_MODIFY_3 = "Modify which property?";
    public static final String QUESTION_MODIFY_4 = "Enter your new [id/name/price/is perishable]";

}
