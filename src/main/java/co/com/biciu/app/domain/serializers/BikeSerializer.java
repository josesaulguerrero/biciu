package co.com.biciu.app.domain.serializers;

import co.com.biciu.app.persistence.entities.Bike;
import co.com.biciu.app.persistence.entities.BikeType;
import co.com.biciu.interfaces.Serializer;

import java.util.Arrays;

public class BikeSerializer implements Serializer<Bike, String> {
    @Override
    public String serialize(Bike entity) {
        return entity.getId() + ";" +
                entity.getColor() + ";" +
                entity.isAvailable() + ";" +
                entity.getType().name() + "?";
    }

    @Override
    public Bike deserialize(String serializedObject) {
        String[] properties = serializedObject.split(";");
        return new Bike(
                properties[0],
                properties[1],
                Boolean.valueOf(properties[2]),
                BikeType.valueOf(properties[3])
        );
    }
}
