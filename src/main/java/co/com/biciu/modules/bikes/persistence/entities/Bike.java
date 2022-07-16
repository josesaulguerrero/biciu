package co.com.biciu.modules.bikes.persistence.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Bike {
    @JsonProperty("id")
    private String id;

    @JsonProperty("color")
    private String color;

    @JsonProperty("isAvailable")
    private Boolean isAvailable;

    @JsonProperty("type")
    private BikeType type;

    public Bike() {}

    public Bike(String id, String color, Boolean isAvailable, BikeType type) {
        this.id = id;
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

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public BikeType getType() {
        return type;
    }

    public void setIsAvailable(Boolean available) {
        isAvailable = available;
    }
    @Override
    public String toString() {
        return "Bike{" +
                "id='" + id + '\'' +
                ", color='" + color + '\'' +
                ", isAvailable=" + isAvailable +
                ", type=" + type +
                '}';
    }
}
