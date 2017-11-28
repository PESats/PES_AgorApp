package pes.agorapp.JSONObjects;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by marc on 28/11/17.
 */

public class Botiga {
    private int id;
    private String name;
    private String description;

    private float latitude;
    private float longitude;

    public Botiga() {}

    public Botiga(int id, String name, String description, float latitude, float longitude) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
