package pes.agorapp.JSONObjects;

/**
 * Created by marc on 15/10/17.
 */

public class UserAgorApp {

    private String id;
    private String active_token;
    private String name;
    private String platform_name;
    private String email;
    private String image_url;
    private String created_at;
    private String updated_at;
    private Integer coins;
    private Float average_evaluation;
    private Botiga shop;

    public UserAgorApp(String name, String image_url, String id,
                       String active_token, String platform_name,
                       String email, String created_at, String updated_at,
                       Integer coins, Float average_evaluation, Botiga shop) {
        this.name = name;
        this.image_url = image_url;
        this.id = id;
        this.active_token = active_token;
        this.platform_name = platform_name;
        this.email = email;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.coins = coins;
        this.average_evaluation = average_evaluation;
        this.shop = shop;
    }

    public UserAgorApp() { }

    public String getName() {
        return name;
    }

    public String getImage_url() { return image_url; }

    public String getId() {
        return id;
    }

    public String getActiveToken() {
        return active_token;
    }

    public String getEmail() {
        return email;
    }

    public String getPlatformName() {
        return platform_name;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public String getUpdatedAt() {
        return updated_at;
    }

    public Integer getCoins() { return coins; }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCoins(Integer coins) {
        this.coins = coins;
    }

    public Botiga getShop() { return shop; }

    public void setShop(Botiga shop) { this.shop = shop; }

    public Float getAverage_evaluation() {
        return average_evaluation;
    }

    public void setAverage_evaluation(Float average_evaluation) {
        this.average_evaluation = average_evaluation;
    }
}
