package co.com.biciu.modules.bikes.persistence.repositories;

import co.com.biciu.interfaces.CRUDRepository;
import co.com.biciu.modules.bikes.persistence.entities.Bike;
import co.com.biciu.utils.JSONUtils;
import co.com.biciu.utils.ReflectionUtils;
import com.fasterxml.jackson.core.type.TypeReference;


import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;


public class BikeRepository implements CRUDRepository<Bike, String> {

    private List<Bike> bikes;
    private Integer currentId;
    private final Path pathToPersistenceFile;

    public BikeRepository() {
        // "" is a shortcut for the absolute path to the root folder of the project.
        this.pathToPersistenceFile = Paths.get("", "src", "main", "java", "co", "com", "biciu", "modules", "bikes", "persistence", "data", "bikes.json");
        this.loadObjectsInMemory();
        this.currentId = calculateCurrentId();
    }

    private void loadObjectsInMemory() {
        TypeReference<List<Bike>> reference = new TypeReference<>() {
        };
        this.bikes = JSONUtils.readJSONFromFile(this.pathToPersistenceFile.toFile(), reference);
    }

    private Integer calculateCurrentId() {
        return bikes.stream().map(bike -> bike.getId().replaceAll("BIC-(\\d+)", "$1")).map(Integer::parseInt).max(Integer::compare).orElseThrow();
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
    public Optional<Bike> findById(String id) {
        return this.bikes.stream().filter(bike -> bike.getId().equals(id)).findFirst();
    }

    @Override
    public Bike save(Bike object) {
        this.assignIdField(object);
        this.bikes.add(object);
        boolean wasWrittenSuccessfully = JSONUtils.writeJSONToFile(this.pathToPersistenceFile.toFile(), bikes);
        if (!wasWrittenSuccessfully) {
            throw new RuntimeException("Something went wrong and the object couldn't be saved. Check the Stack Trace for more information.");
        }
        return object;
    }

    private Integer indexOf(String id) {
        Bike bike = this.findById(id).orElseThrow(() -> new IllegalArgumentException("The given id doesn't belong to any of the existent objects in the application."));
        return this.bikes.indexOf(bike);
    }

    @Override
    public Bike update(String id, Bike updatedObject) {
        if (!id.equals(updatedObject.getId())) {
            throw new IllegalArgumentException("The id given as the first argument doesn't match the id of the object passed as second argument.");
        }
        int elementIndex = this.indexOf(id);
        this.bikes.set(elementIndex, updatedObject);
        return this.bikes.get(elementIndex);
    }

    @Override
    public Boolean delete(String id) {
        boolean wasDeleted;
        try {
            int elementIndex = this.indexOf(id);
            this.bikes.remove(elementIndex);
            wasDeleted = true;
        } catch (Exception e) {
            wasDeleted = false;
        }
        return wasDeleted;
    }
}
