package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import exception.ItemAlreadyExistsException;
import exception.ItemNotFoundException;
import exception.NoItemInCartException;
import exception.ProductMissingInfoException;
import exception.QuitTriggerException;
import exception.SelectionNotFoundException;
import models.Item;
import models.Shop;
import models.ShoppingCart;

public class Controller {
    private Shop shop = new Shop();
    private ShoppingCart shoppingCart = new ShoppingCart();
    private Printer printer = new Printer(shop.getName());
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private String currentStage = StageConstants.MAIN_MENU;
    

    public void start() {    
        while(true) {
            try {
                navigate();
            } catch (NumberFormatException | IOException | ItemAlreadyExistsException
                    | ProductMissingInfoException | SelectionNotFoundException e) {
                printer.setErrorMessage(e.getMessage());
            } catch(ItemNotFoundException e) {
                printer.setErrorMessage(e.getMessage());
                setCurrentStage(StageConstants.MAIN_MANAGE);
            } catch(NoItemInCartException e) {
                printer.setErrorMessage(e.getMessage());
                setCurrentStage(StageConstants.MAIN_SHOP);
            } catch(QuitTriggerException e) {
                printer.printMessage(PrinterConstants.EXIT_MESSAGE);
                break;
            } catch(Exception e) {
                printer.setErrorMessage(PrinterConstants.EXCEPTION_MESSAGE);
            }
        }
    }


    public void navigate() throws IOException, SelectionNotFoundException, QuitTriggerException, 
            NumberFormatException, ItemNotFoundException, ItemAlreadyExistsException, ProductMissingInfoException, 
            NoItemInCartException {
        // Print current stage
        switch(currentStage) {
            case StageConstants.MAIN_MANAGE:
                printer.printScreen(PrinterConstants.MANAGE_ITEMS);
                break;
            case StageConstants.MANAGE_ADD:
                createNewItem();
                setCurrentStage(StageConstants.MAIN_MANAGE);
                return;
            case StageConstants.MANAGE_DISPLAY:
                setCurrentStage(StageConstants.MAIN_MANAGE);
                if(shop.doesItemsExists()) {
                    printer.printScreen(PrinterConstants.MANAGE_ITEMS, shop.getShopListDesc());
                }else {
                    printer.setNotification(PrinterConstants.NOTIF_NO_ITEMS);
                    return;
                }
                break;
            case StageConstants.MANAGE_MODIFY:
                modifyItem();
                setCurrentStage(StageConstants.MAIN_MANAGE);
                return;
            case StageConstants.MANAGE_REMOVE:
                removeItem();
                setCurrentStage(StageConstants.MAIN_MANAGE);
                return;
            case StageConstants.MAIN_SHOP:
                printer.printScreen(shoppingCart.getItemsDesc(), PrinterConstants.QUESTION_SHOP_BUY, shop.getShopListDesc());
                break;
            case StageConstants.SHOP_CHECKOUT:
                printer.printScreen(shoppingCart.checkout(), PrinterConstants.QUESTION_ANY_KEY);
                break;
            case StageConstants.QUIT:
                throw new QuitTriggerException();
            default:
                printer.printScreen(PrinterConstants.MAIN_MENU);
        }
        
        String currentInput = reader.readLine();

        String nextStage = StageConstants.INPUT_MAP.get(currentStage.concat(currentInput));
        if((nextStage != null && nextStage.equals(StageConstants.RETURN)) || currentStage == StageConstants.SHOP_CHECKOUT) {
            setCurrentStage(StageConstants.MAIN_MENU);
            return;
        }

        // Evaluate user input
        if(currentStage != StageConstants.MAIN_SHOP) {
            setCurrentStage(nextStage);
        }else {
            if(nextStage == null) {
                shoppingCart.addItem(shop.putItemToCart(Long.parseLong(currentInput)));
                setCurrentStage(StageConstants.MAIN_SHOP);
            }else if(nextStage.equals(StageConstants.SHOP_CHECKOUT)) {
                setCurrentStage(nextStage);
            }
        }
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

    public void modifyItem() throws NumberFormatException, IOException, ItemNotFoundException, SelectionNotFoundException {
        printer.printQuestion(PrinterConstants.QUESTION_MODIFY_1);
        Long productId = Long.parseLong(reader.readLine());
        
        Item itemToModify = shop.getItem(productId);
        if(itemToModify == null) {
            throw new ItemNotFoundException(productId);
        }

        printer.printMessage(
            String.format(PrinterConstants.QUESTION_MODIFY_2, 
                itemToModify.getProductId(), 
                itemToModify.getName(), 
                itemToModify.getPrice(), 
                itemToModify.getIsPerishable() ? "Y" : "N"));
        
        printer.printQuestion(PrinterConstants.QUESTION_MODIFY_3);
        int propertyToModify = 0;
        propertyToModify = Integer.parseInt(reader.readLine());

        if(propertyToModify < 0 && propertyToModify > 5) {
            throw new SelectionNotFoundException();
        }

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

        printer.setNotification(PrinterConstants.NOTIF_MODIFY);
    }

    public Boolean removeItem() throws NumberFormatException, IOException, ItemNotFoundException {
        printer.printQuestion(PrinterConstants.QUESTION_MODIFY_1);
        Long productId = Long.parseLong(reader.readLine());
        boolean result = shop.removeItem(productId);

        if(result) {
            printer.setNotification(PrinterConstants.NOTIF_REMOVE);
        }else {
            printer.setErrorMessage(PrinterConstants.EXCEPTION_MESSAGE);
        }
        return result;
    }

    public String getCurrentStage() {
        return this.currentStage;
    }

    public void setCurrentStage(String currentStage) throws SelectionNotFoundException {
        if(currentStage == null) {
            throw new SelectionNotFoundException();
        }
        
        this.currentStage = currentStage;
    }

}
