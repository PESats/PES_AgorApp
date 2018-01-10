package pes.agorapp.JSONObjects;

/**
 * Created by marc on 14/11/17.
 */

public class Trophy {
    private int id;
    private String name;
    private String description;

    private Boolean unlocked;

    public Trophy() {}

    public Trophy(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getTitle() { return name; }

    public void setTitle(String title) { this.name = title; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Boolean getUnlocked() {
        return unlocked;
    }

    public void setUnlocked(Boolean unlocked) {
        this.unlocked = unlocked;
    }
}
