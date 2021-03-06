package co.com.biciu.app.persistence.entities;

import co.com.biciu.annotations.Id;

public class Bike {
    //BIC-NNN
    @Id
    private String id;

    private String color;

    private Boolean available;

    private BikeType type;

    public Bike() {}

    public Bike(String color, Boolean available, BikeType type) {
        this.color = color;
        this.available = available;
        this.type = type;
    }

    public Bike(String id, String color, Boolean isAvailable, BikeType type) {
        this.id = id;
        this.color = color;
        this.available = isAvailable;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public Boolean isAvailable() {
        return available;
    }

    public BikeType getType() {
        return type;
    }

    public void setIsAvailable(Boolean isAvailable) {
        available = isAvailable;
    }
    @Override
    public String toString() {
        return "Bike{" +
                "id='" + id + '\'' +
                ", color='" + color + '\'' +
                ", available=" + available +
                ", type=" + type +
                '}';
    }
}
