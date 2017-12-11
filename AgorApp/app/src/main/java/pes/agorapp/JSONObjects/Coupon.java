package pes.agorapp.JSONObjects;

/**
 * Created by Nil on 25/11/2017.
 */

public class Coupon {
    private int id;
    private String description;
    private Integer shop_id;
    private int price;
    private int discount;
    private Botiga shop;

    public Coupon(int id, String description, Integer shop_id, int price, int discount, Botiga shop) {
        this.id = id;
        this.description = description;
        this.shop_id = shop_id;
        this.price = price;
        this.discount = discount;
        this.shop = shop;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getDescription() {
        return description;
    }

    public void setUser_id(String user_id) {
        this.description = user_id;
    }

    public Integer getShopId() { return shop_id; }

    public void setShopId(Integer shop_id) { this.shop_id = shop_id; }

    public int getPrice() { return price; }

    public void setPrice(int price) { this.price = price; }

    public int getDiscount() { return discount; }

    public void setDiscount(int discount) { this.discount = discount; }

    public Botiga getBotiga() {
        return shop;
    }

    public void setBotiga(Botiga shop) {
        this.shop = shop;
    }
}

