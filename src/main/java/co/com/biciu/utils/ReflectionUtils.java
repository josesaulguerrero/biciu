package co.com.biciu.utils;

import co.com.biciu.annotations.Id;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

public class ReflectionUtils {
    private static void validateAnnotationIsNotPresentMoreThanOnce(Class<?> clazz) {
        long attributesMarkedWithId = Arrays
                .stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .count();
        if(attributesMarkedWithId > 1) {
            throw new RuntimeException("Only one field should be marked with the Id annotation.");
        }
    }
    public static Field getIdField(Class<?> clazz) {
        validateAnnotationIsNotPresentMoreThanOnce(clazz);

        Optional<Field> idField = Optional.empty();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                idField = Optional.of(field);
            }
        }
        return idField.orElseThrow(() -> new RuntimeException("No attribute in the class is marked with @Id"));
    }
}
