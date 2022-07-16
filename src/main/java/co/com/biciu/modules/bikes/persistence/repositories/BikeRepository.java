package co.com.biciu.modules.bikes.persistence.repositories;

import co.com.biciu.interfaces.CRUDRepository;
import co.com.biciu.modules.bikes.persistence.entities.Bike;
import com.fasterxml.jackson.core.type.TypeReference;
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
        try {
            // "" is a shortcut for the absolute path to the root folder of the project.
            Path path = Paths.get("",
                    "src", "main", "java", "co", "com", "biciu", "modules", "bikes", "persistence", "data", "bikes.json");
            FileReader reader = new FileReader(new File(path.toUri()));
            ObjectMapper mapper = new ObjectMapper();
            bikes = mapper.readValue(reader, new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Integer calculateCurrentId() {
        return bikes.stream()
                .map(
                    bike -> bike.getId().replaceAll("BIC-(\\d+)", "$1")
                )
                .map(Integer::parseInt)
                .max(Integer::compare)
                .orElseThrow();
    }

    private String generateNewId() {
        int newId = Integer.sum(this.currentId, 1);
        this.currentId = newId;
        return "BIC-" + newId;
    }

    private void assignIdField(Bike object) {
        try {
            String id = generateNewId();
            Class<Bike> clazz = Bike.class;
            Field field = clazz.getDeclaredField("id");
            field.setAccessible(true);
            field.set(object, id);
        } catch (NoSuchFieldException | IllegalAccessException e) {
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
