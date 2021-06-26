package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Printer {
    private String shopName;
    private String errorMessage;
    private String notification;

    public Printer(String shopName) {
        this.shopName = shopName;
    }


    public void printScreen(String[] wordsToPrint) {
        printScreen(wordsToPrint, null, null);
    }

    public void printScreen(String[] wordsToPrint, String customQuestion) {
        printScreen(wordsToPrint, customQuestion, null);
    }

    public void printScreen(String[] wordsToPrint, List<String> shopDesc) {
        printScreen(wordsToPrint, null, shopDesc);
    }

    public void printScreen(List<String> wordsToPrint) {
        printScreen(wordsToPrint, null, null);
    }

    public void printScreen(List<String> wordsToPrint, String customQuestion) {
        printScreen(wordsToPrint, customQuestion, null);
    }

    public void printScreen(List<String> wordsToPrint, String customQuestion, List<String> shopDesc) {
        printScreen(wordsToPrint.toArray(new String[0]), customQuestion, shopDesc);
    }
    
    public void printScreen(String[] wordsToPrint, String customQuestion, List<String> shopDesc) {
        clearScreen();
        printHeader();
        printNotification();
        printErrorMessage();
        printWordArray(shopDesc);
        printWordArray(wordsToPrint);
        printMessage(""); // Print whitespace
        printQuestion(customQuestion);
    }

    public void printErrorMessage() {
        if(this.errorMessage != null) {
            System.err.println(this.errorMessage);
            setErrorMessage(null);
        }
    }

    public void printNotification() {
        if(this.notification != null) {
            printMessage("Note: " + this.notification);
            setNotification(null);
        }
    }

    public void printWordArray(List<String> wordsToPrint) {
        if(wordsToPrint != null) {
            wordsToPrint.add("");
            printWordArray(wordsToPrint.toArray(new String[0]));
        }
    }

    public void printWordArray(String[] wordsToPrint) {
        for(int i = 0; i < wordsToPrint.length; i++) {
            printMessage(wordsToPrint[i]);
        }
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printHeader() {
        printHeader(null);
    }

    public void printHeader(String header) {
        printMessage(shopName);

        if(header != null) {
            printMessage(header);
        }
    }

    public void printQuestion(String customQuestion) {
        String questionMessage = customQuestion == null ? PrinterConstants.COMMON_INPUT : customQuestion;
        System.out.print(questionMessage + " : ");
    }


    public void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> printCreateNewItem(BufferedReader reader) throws IOException {
        List<String> inputs = new ArrayList<String>();
        clearScreen();
        printHeader(PrinterConstants.MANAGE_ADD);

        for(int i = 0; i < PrinterConstants.MANAGE_ADD_INPUTS.length; i++) {
            printQuestion(PrinterConstants.MANAGE_ADD_INPUTS[i]);
            inputs.add(reader.readLine());
        }

        return inputs;
    }


    public String getShopName() {
        return this.shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getNotification() {
        return this.notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    
}
