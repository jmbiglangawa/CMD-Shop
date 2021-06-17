package models;

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


}
