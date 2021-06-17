package models;

import java.util.ArrayList;
import java.util.List;

import exception.ItemAlreadyExistsException;
import exception.ItemNotFoundException;
import exception.ProductMissingInfoException;

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

        if(getItem(item.getProductId()) != null) {
            throw new ItemAlreadyExistsException(item.getProductId());
        }

        return this.items.add(item);
    }

    public Boolean removeItem(Long productId) throws ItemNotFoundException {
        if(productId == null) {
            throw new NullPointerException("productId cannot be null");
        }

        return this.items.remove(getItem(productId));
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

    public Item getItem(Long productId) throws ItemNotFoundException {
        Item item = null;
        for(Item itemInShop : items) {
            if(itemInShop.getProductId() == productId) {
                item = itemInShop;
                break;
            }
        }

        if(item == null) {
            throw new ItemNotFoundException(productId);
        }

        return item;
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
