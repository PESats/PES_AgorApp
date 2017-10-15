package pes.agorapp.JSONObjects;

/**
 * Created by marc on 14/10/17.
 */

public class UserFacebook {
    private String id;
    private String name;

    public UserFacebook(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
