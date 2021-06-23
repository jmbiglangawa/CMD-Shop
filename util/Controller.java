package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import exception.ItemAlreadyExistsException;
import exception.ItemNotFoundException;
import exception.ProductMissingInfoException;
import exception.QuitTriggerException;
import exception.SelectionNotFoundException;
import models.Item;
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
            } catch (NumberFormatException | IOException | ItemNotFoundException | ItemAlreadyExistsException
                    | ProductMissingInfoException | SelectionNotFoundException e) {
                printer.setErrorMessage(e.getMessage());
                e.printStackTrace();
            } catch(QuitTriggerException e) {
                printer.printMessage(PrinterConstants.EXIT_MESSAGE);
                e.printStackTrace();
                break;
            } catch(Exception e) {
                printer.setErrorMessage(PrinterConstants.EXCEPTION_MESSAGE);
                e.printStackTrace();
            }
        }
    }


    public void navigate() throws IOException, SelectionNotFoundException, QuitTriggerException, 
            NumberFormatException, ItemNotFoundException, ItemAlreadyExistsException, ProductMissingInfoException {
        // Print current stage
        switch(currentStage) {
            case StageConstants.MAIN_SHOP:
                printer.printScreen(PrinterConstants.GO_SHOPPING);
                break;
            case StageConstants.MAIN_MANAGE:
                printer.printScreen(PrinterConstants.MANAGE_ITEMS);
                break;
            case StageConstants.MANAGE_ADD:
                createNewItem();
                setCurrentStage(StageConstants.MAIN_MANAGE);
                return;
            case StageConstants.MANAGE_DISPLAY:
                printer.printScreen(PrinterConstants.MANAGE_ITEMS, shop.getShopListDesc());
                setCurrentStage(StageConstants.MAIN_MANAGE);
                break;
            case StageConstants.MANAGE_MODIFY:
                modifyItem();
                setCurrentStage(StageConstants.MAIN_MANAGE);
                return;
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

    public void createNewItem() throws IOException, NumberFormatException, ItemNotFoundException, 
            ItemAlreadyExistsException, ProductMissingInfoException {
        List<String> inputs = printer.printCreateNewItem(reader);
        Boolean result = shop.addItem(new Item(
            Long.parseLong(inputs.get(0)), inputs.get(1), Float.parseFloat(inputs.get(2)), 
                inputs.get(3).equalsIgnoreCase("Y")));

        if(result) {
            printer.setNotification(PrinterConstants.NOTIF_ADD);
        }else {
            printer.setErrorMessage(PrinterConstants.EXCEPTION_MESSAGE);
        }
    }

    public void modifyItem() throws NumberFormatException, IOException, ItemNotFoundException {
        printer.printQuestion(PrinterConstants.QUESTION_MODIFY_1);
        Long productId = Long.parseLong(reader.readLine());
        
        Item itemToModify = shop.getItem(productId);
        printer.printMessage(
            String.format(PrinterConstants.QUESTION_MODIFY_2, 
                itemToModify.getProductId(), 
                itemToModify.getName(), 
                itemToModify.getPrice(), 
                itemToModify.getIsPerishable() ? "Y" : "N"));
        
        printer.printQuestion(PrinterConstants.QUESTION_MODIFY_3);
        int propertyToModify = Integer.parseInt(reader.readLine());
        printer.printMessage(""); // Print a blank space
        
        printer.printQuestion(PrinterConstants.QUESTION_MODIFY_4);
        String newValue = reader.readLine();
        if(propertyToModify == 1) {
            itemToModify.setProductId(Long.parseLong(newValue));
        }else if(propertyToModify == 2) {
            itemToModify.setName(newValue);
        }else if(propertyToModify == 3) {
            itemToModify.setPrice(Float.parseFloat(newValue));
        }else if(propertyToModify == 4) {
            itemToModify.setIsPerishable(newValue.equals("Y"));
        }
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
