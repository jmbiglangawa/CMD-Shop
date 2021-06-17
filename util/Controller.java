package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import exception.QuitTriggerException;
import exception.SelectionNotFoundException;
import models.Shop;

public class Controller {
    private Shop shop = new Shop();
    private Printer printer = new Printer(shop.getName());
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private String currentStage = StageConstants.MAIN_MENU;
    private String previousStage = "";

    private String currentInput = "";


    public void start() {    
        while(true) {
            try {
                navigate();
            } catch (SelectionNotFoundException e) {
                printer.setErrorMessage(e.getMessage());
            } catch(QuitTriggerException e) {
                printer.printMessage(PrinterConstants.EXIT_MESSAGE);
                break;
            } catch(Exception e) {
                printer.setErrorMessage(PrinterConstants.EXCEPTION_MESSAGE);
            }
        }
    }


    public void navigate() throws IOException, SelectionNotFoundException, QuitTriggerException {
        // Print current stage
        switch(currentStage) {
            case StageConstants.MAIN_SHOP:
                printer.printScreen(PrinterConstants.GO_SHOPPING);
            case StageConstants.MAIN_MANAGE:
                printer.printScreen(PrinterConstants.MANAGE_ITEMS);
                break;
            case StageConstants.QUIT:
                throw new QuitTriggerException();
            default:
                printer.printScreen(PrinterConstants.MAIN_MENU);
        }

        currentInput = reader.readLine();

        // Evaluate user input
        String nextStage = StageConstants.INPUT_MAP.get(currentStage.concat(currentInput));
        setCurrentStage(nextStage);
    }


    public String getCurrentStage() {
        return this.currentStage;
    }

    public void setCurrentStage(String currentStage) throws SelectionNotFoundException {
        if(currentStage == null) {
            throw new SelectionNotFoundException();
        }
        
        this.previousStage = this.currentStage;
        this.currentStage = currentStage;
    }

}
