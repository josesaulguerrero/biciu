package co.com.biciu.modules.bikes.persistence.repositories;

import co.com.biciu.interfaces.CRUDRepository;
import co.com.biciu.modules.bikes.persistence.entities.Bike;
import co.com.biciu.utils.JSONUtils;
import co.com.biciu.utils.ReflectionUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class BikeRepository implements CRUDRepository<Bike, String> {

    private List<Bike> bikes;
    private Integer currentId;

    public BikeRepository() {
        this.loadObjectsInMemory();
        this.currentId = calculateCurrentId();
    }

    private void loadObjectsInMemory() {
        // "" is a shortcut for the absolute path to the root folder of the project.
        Path path = Paths.get("",
                "src", "main", "java", "co", "com", "biciu", "modules", "bikes", "persistence", "data", "bikes.json");
        TypeReference<List<Bike>> reference = new TypeReference<>() {};
        this.bikes = (List<Bike>) JSONUtils.readJSONFile(path.toAbsolutePath().toFile(), reference);
    }

    private Integer calculateCurrentId() {
        for (Bike bike : bikes) {
            System.out.println("bike = " + bike);
        }
        return null;
                /*.map(
                    bike -> bike.getId().replaceAll("BIC-(\\d+)", "$1")
                )
                .map(Integer::parseInt)
                .max(Integer::compare)
                .orElseThrow();*/
    }

    private String generateNewId() {
        int newId = Integer.sum(this.currentId, 1);
        this.currentId = newId;
        return "BIC-" + newId;
    }

    private void assignIdField(Bike object) {
        try {
            String id = generateNewId();
            Field field = ReflectionUtils.getIdField(Bike.class);
            field.setAccessible(true);
            field.set(object, id);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Bike> findAll() {
        return this.bikes;
    }

    @Override
    public Bike findById(String id) {
        return null;
    }

    @Override
    public Bike save(Bike object) {
        this.assignIdField(object);
        this.bikes.add(object);
        Path path = Paths.get("",
                "src", "main", "java", "co", "com", "biciu", "modules", "bikes", "persistence", "data", "bikes.json");
        File file = new File(path.toAbsolutePath().toUri());
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, bikes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return object;
    }

    @Override
    public Bike update(String id, Bike updatedObject) {
        return null;
    }

    @Override
    public Boolean delete(String id) {
        return null;
    }
}
