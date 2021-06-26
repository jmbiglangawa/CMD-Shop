package models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import exception.ItemAlreadyExistsException;
import exception.ItemNotFoundException;
import exception.ProductMissingInfoException;
import util.PrinterConstants;

public class Shop {
    private String name = "CMDShop";
    private List<Item> items = new ArrayList<Item>();

    
    public Boolean addItem(Item item) throws ItemNotFoundException, ItemAlreadyExistsException, 
            ProductMissingInfoException {
        if(item.getProductId() == null) {
            throw new ProductMissingInfoException("Product ID");
        }

        if(item.getName() == null) {
            throw new ProductMissingInfoException("Name");
        }

        if(item.getPrice() == null) {
            throw new ProductMissingInfoException("Price");

        }

        if(hasItem(item.getProductId())) {
            throw new ItemAlreadyExistsException(item.getProductId());
        }

        return this.items.add(item);
    }

    public Boolean removeItem(Long productId) throws ItemNotFoundException {
        if(productId == null) {
            throw new NullPointerException("productId cannot be null");
        }

        Item itemToRemove = getItem(productId);
        if(itemToRemove == null) {
            throw new ItemNotFoundException(productId);
        }
        return this.items.remove(itemToRemove);
    }

    public Boolean modifyItem(Long productId, Integer propertyToModify, String newValue) throws ItemNotFoundException {
        if(productId == null) {
            throw new NullPointerException("productId cannot be null");
        }

        if(propertyToModify == null) {
            throw new NullPointerException("propertyToModify cannot be null");
        }

        if(newValue == null) {
            throw new NullPointerException("newValue cannot be null");
        }

        Boolean isSuccess = false;
        Item itemToModify = getItem(productId);
        
        if(itemToModify == null) {
            throw new ItemNotFoundException(productId);
        }

        if(propertyToModify == 1) {
            isSuccess = true;
            itemToModify.setProductId(Long.parseLong(newValue));
        }else if(propertyToModify == 2) {
            isSuccess = true;
            itemToModify.setName(newValue);
        }else if(propertyToModify == 3) {
            isSuccess = true;
            itemToModify.setPrice(Float.parseFloat(newValue));
        }
        
        return isSuccess;
    }

    public Boolean hasItem(Long productId) {
        return this.items.contains((Object) productId);
    }

    public Item getItem(Long productId) throws ItemNotFoundException {
        Item item = null;
        for(Item itemInShop : items) {
            if(itemInShop.getProductId().compareTo(productId) == 0) {
                item = itemInShop;
                break;
            }
        }
        return item;
    }

    public Item putItemToCart(Long productId) throws ItemNotFoundException {
        Item item = getItem(productId);
        removeItem(item.getProductId());
        return item;
    }

    public List<String> getShopListDesc() {
        List<String> shopListDesc = new ArrayList<String>();
        shopListDesc.add(PrinterConstants.ITEMS_HEADER);
        shopListDesc.add(PrinterConstants.PERISHABLE_HEADER);
        List<String> perishableItems = this.items.stream()
            .filter(item -> item.getIsPerishable())
            .map(Item::toString)
            .collect(Collectors.toList());

        if(perishableItems != null && perishableItems.size() > 0) {
            shopListDesc.addAll(perishableItems);
        }else {
            shopListDesc.add(PrinterConstants.NO_ITEMS);
        }

        shopListDesc.add(PrinterConstants.NON_PERISHABLE_HEADER);
        List<String> nonPerishableItems = this.items.stream()
            .filter(item -> !item.getIsPerishable())
            .map(Item::toString)
            .collect(Collectors.toList());
        if(nonPerishableItems != null && nonPerishableItems.size() > 0) {
            shopListDesc.addAll(nonPerishableItems);
        }else {
            shopListDesc.add(PrinterConstants.NO_ITEMS);
        }
        
        return shopListDesc;
    }

    public Boolean doesItemsExists() {
        return this.items.size() > 0;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItems() {
        return this.items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}
