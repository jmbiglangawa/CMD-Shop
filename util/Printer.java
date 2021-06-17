package util;

import java.io.IOException;

public class Printer {
    private String shopName;
    private String errorMessage;

    public Printer(String shopName) {
        this.shopName = shopName;
    }


    public void printScreen(String[] wordsToPrint) {
        printScreen(wordsToPrint, null);
    }
    
    public void printScreen(String[] wordsToPrint, String customQuestion) {
        clearScreen();
        printMessage(shopName);
        printErrorMessage();
        printWordArray(wordsToPrint);
        printMessage(""); // Print whitespace

        String questionMessage = customQuestion == null ? PrinterConstants.COMMON_INPUT : customQuestion;
        System.out.print(questionMessage + " : ");
    }

    public void printErrorMessage() {
        if(this.errorMessage != null) {
            System.err.println(this.errorMessage);
            setErrorMessage(null);
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


    public void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
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

    
}
