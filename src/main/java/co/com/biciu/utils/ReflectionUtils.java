package co.com.biciu.utils;

import co.com.biciu.annotations.Id;
import co.com.biciu.modules.bikes.persistence.entities.Bike;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

public class ReflectionUtils {
    public static Field getIdField(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        Optional<Field> idField = Optional.empty();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                idField = Optional.of(field);
            }
        }
        return idField.orElseThrow(() -> new RuntimeException("No attribute in the class is marked with @Id"));
    }
}
