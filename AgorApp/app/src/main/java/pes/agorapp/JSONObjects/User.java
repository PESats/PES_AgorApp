package pes.agorapp.JSONObjects;

/**
 * Created by marc on 15/10/17.
 */

public class User {

    private String name;
    private String image_url;
    private String uuid;

    public User(String name, String image_url, String uuid) {
        this.name = name;
        this.image_url = image_url;
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getUuid() {
        return uuid;
    }

}
