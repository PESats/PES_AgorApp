package pes.agorapp.JSONObjects;

/**
 * Created by Nil on 25/11/2017.
 */

public class Coupon {
    private int id;
    private String user_id;
    private String establishment;
    private int price;
    private int discount;

    public Coupon(int id, String user_id, String establishment, int price, int discount) {
        this.id = id;
        this.user_id = user_id;
        this.establishment = establishment;
        this.price = price;
        this.discount = discount;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEstablishment() { return establishment; }

    public void setEstablishment(String establishment) { this.establishment = establishment; }

    public int getPrice() { return price; }

    public void setPrice(int price) { this.price = price; }

    public int getDiscount() { return discount; }

    public void setDiscount(int discount) { this.discount = discount; }

}

