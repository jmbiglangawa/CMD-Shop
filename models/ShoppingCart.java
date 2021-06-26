package models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import exception.ItemAlreadyExistsException;
import exception.ItemNotFoundException;
import exception.NoItemInCartException;
import exception.ProductMissingInfoException;
import util.PrinterConstants;

public class ShoppingCart {
    private List<Item> items = new ArrayList<Item>();


    public List<Item> getItems() {
        return this.items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<String> getItemsDesc() {
        return getItemsDesc(false);
    }
    
    public List<String> getItemsDesc(Boolean isReceipt) {
        List<String> shopListDesc = new ArrayList<String>();
        
        if(isReceipt) {
            shopListDesc.add(PrinterConstants.RECEIPT_THANK);
            shopListDesc.add(PrinterConstants.RECEIPT);
        }else {
            shopListDesc.add(PrinterConstants.SHOPPING_CART);
        }

        if(this.items.size() == 0) {
            shopListDesc.add(PrinterConstants.CART_NO_ITEMS);
        }else {
            shopListDesc.addAll(this.items.stream()
            .map(Item::toString)
            .collect(Collectors.toList()));
        }

        return shopListDesc;
    }

    public List<String> checkout() throws NoItemInCartException {
        if(this.items.size() == 0) {
            throw new NoItemInCartException();
        }
        
        Float totalPrice = Float.valueOf(0);
        // this.items.forEach((item) -> totalPrice += item.getPrice());
        for(Item item : this.items) {
            totalPrice += item.getPrice();
        }
        
        List<String> receipt = getItemsDesc(true);
        receipt.add(PrinterConstants.RECEIPT_TOTAL.concat("₱" + totalPrice));
        this.items.clear();
        return receipt;
    }

    public Boolean hasItem(Long productId) {
        return this.items.contains((Object) productId);
    }
    
    public Boolean addItem(Item item) throws ItemNotFoundException, ItemAlreadyExistsException, 
            ProductMissingInfoException {
        if(hasItem(item.getProductId())) {
            throw new ItemAlreadyExistsException(item.getProductId());
        }

        return this.items.add(item);
    }

    public Boolean removeItem(Long productId) throws ItemNotFoundException {
        if(productId == null) {
            throw new NullPointerException("productId cannot be null");
        }

        if(!hasItem(productId)) {
            throw new ItemNotFoundException(productId);
        }

        return this.items.remove(getItem(productId));
    }

    public Item getItem(Long productId) {
        Item item = null;
        for(Item itemInShop : items) {
            if(itemInShop.getProductId().compareTo(productId) == 0) {
                item = itemInShop;
                break;
            }
        }
        return item;
    }
    
}
