package co.com.biciu.modules.bikes.entities;

public class Bike {
    String id;
    String color;
    Boolean isAvailable;
    BikeType type;

    public Bike(String id, String color, Boolean isAvailable, BikeType type) {
        this.id = id;
        this.color = color;
        this.isAvailable = isAvailable;
        this.type = type;
    }

    public Bike(String color, Boolean isAvailable, BikeType type) {
        this.color = color;
        this.isAvailable = isAvailable;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public BikeType getType() {
        return type;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }
}
