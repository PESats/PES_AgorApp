package pes.agorapp.JSONObjects;

/**
 * Created by Nil on 25/11/2017.
 */

public class Coupon {
    private int id;
    private String user_id;
    private String title;
    private String establishment;
    private int pricePoints;
    private int discount;

    public Coupon(int id, String user_id, String title, String establishment, int pricePoints, int discount) {
        this.id = id;
        this.title = title;
        this.user_id = user_id;
        this.establishment = establishment;
        this.pricePoints = pricePoints;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String user_id) {
        this.title = title;
    }

    public String getEstablishment() { return establishment; }

    public void setEstablishment(String establishment) { this.establishment = establishment; }

    public int getPricePoints() { return pricePoints; }

    public void setPricePoints(int pricePoints) { this.pricePoints = pricePoints; }

    public int getDiscount() { return discount; }

    public void setDiscount(int discount) { this.discount = discount; }

}

