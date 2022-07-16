package co.com.biciu.modules.bikes.domain.dto;

import co.com.biciu.modules.bikes.persistence.entities.BikeType;

public class BikeDTO {
    private String color;
    private Boolean isAvailable;
    private BikeType type;

    public BikeDTO() {
    }

    public BikeDTO(String color, Boolean isAvailable, BikeType type) {
        this.color = color;
        this.isAvailable = isAvailable;
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public BikeType getType() {
        return type;
    }

    public void setType(BikeType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "BikeDTO{" +
                "color='" + color + '\'' +
                ", isAvailable=" + isAvailable +
                ", type=" + type +
                '}';
    }
}
