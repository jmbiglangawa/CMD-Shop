package util;

import java.util.HashMap;
import java.util.Map;

public class StageConstants {
    public static final String MAIN_MENU = "mainMenu";
    public static final String MAIN_SHOP = "mainShop";
    public static final String MAIN_MANAGE = "mainManage";
    
    public static final String MANAGE_ADD = "manageAdd";
    public static final String MANAGE_REMOVE = "manageRemove";
    public static final String MANAGE_MODIFY = "manageModify";

    public static final String QUIT = "quit";
    public static final String RETURN = "return";

    public static final Map<String, String> INPUT_MAP = new HashMap<>() {{
        put(MAIN_MENU + "1", MAIN_SHOP);
        put(MAIN_MENU + "2", MAIN_MANAGE);
        put(MAIN_MENU + "3", QUIT);

        put(MAIN_MANAGE + "1", MANAGE_ADD);
        put(MAIN_MANAGE + "2", MANAGE_REMOVE);
        put(MAIN_MANAGE + "3", MANAGE_MODIFY);
        put(MAIN_MANAGE + "4", RETURN);


    }};
}
