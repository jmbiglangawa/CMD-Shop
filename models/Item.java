package models;

import java.util.Objects;

public class Item {
    private Long productId;
    private String name;
    private Float price;
    private Boolean isPerishable;


    public Item(Long productId, String name, Float price, Boolean isPerishable) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.isPerishable = isPerishable;
    }

    public Item(Float price) {
        this.price = price;
    }
    

    public Long getProductId() {
        return this.productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return this.price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Boolean isIsPerishable() {
        return this.isPerishable;
    }

    public Boolean getIsPerishable() {
        return this.isPerishable;
    }

    public void setIsPerishable(Boolean isPerishable) {
        this.isPerishable = isPerishable;
    }



    @Override
    public String toString() {
        return "[" + getProductId() + "] " + getName() + " - ₱" + getPrice();
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Item) || !(o instanceof Long)) {
            return false;
        }
        
        if(o instanceof Item) {
            Item item = (Item) o;
            return Objects.equals(productId, item.productId) && Objects.equals(name, item.name) && Objects.equals(price, item.price) && Objects.equals(isPerishable, item.isPerishable);
        }else if(o instanceof Long){
            Long productId = (Long) o;
            return Objects.equals(this.productId, productId);
        }else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, name, price, isPerishable);
    }



}
