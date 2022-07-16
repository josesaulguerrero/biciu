package co.com.biciu.modules.bikes.domain.dto;

public class BikeDTO {
    private String bikeId;
    private String color;
    private Boolean isAvailable;
    private String type;

    public BikeDTO() {
    }

    public BikeDTO(String color, Boolean isAvailable, String type) {
        this.color = color;
        this.isAvailable = isAvailable;
        this.type = type;
    }

    public BikeDTO(String bikeId, String color, Boolean isAvailable, String type) {
        this.bikeId = bikeId;
        this.color = color;
        this.isAvailable = isAvailable;
        this.type = type;
    }

    public String getBikeId() {
        return bikeId;
    }

    public void setBikeId(String bikeId) {
        this.bikeId = bikeId;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
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
