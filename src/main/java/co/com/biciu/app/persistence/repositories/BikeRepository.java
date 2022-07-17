package co.com.biciu.app.persistence.repositories;

import co.com.biciu.app.domain.serializers.BikeSerializer;
import co.com.biciu.interfaces.CRUDRepository;
import co.com.biciu.app.persistence.entities.Bike;
import co.com.biciu.interfaces.Serializer;
import co.com.biciu.utils.FileUtils;
import co.com.biciu.utils.ReflectionUtils;


import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class BikeRepository implements CRUDRepository<Bike, String> {

    private List<Bike> bikes;
    private Integer currentId;
    private final Path pathToPersistenceFile;
    private final Serializer<Bike, String> serializer;

    public BikeRepository() {
        this.serializer = new BikeSerializer();
        // "" is a shortcut for the absolute path to the root folder of the project.
        this.pathToPersistenceFile = Paths.get("", "src", "main", "java", "co", "com", "biciu", "app", "persistence", "data", "bikes.txt");
        this.loadObjectsInMemory();
        this.currentId = calculateCurrentId();
    }

    private void saveChanges() {
        FileUtils
                .writeToFile(
                        this.pathToPersistenceFile.toFile(),
                        bikes.stream().map(serializer::serialize).collect(Collectors.joining())
                );
    }

    private void loadObjectsInMemory() {
        String content = FileUtils.readFromFile(this.pathToPersistenceFile.toFile());
        bikes = Arrays.stream(content.split("\\?")).map(serializer::deserialize).collect(Collectors.toList());
    }

    private Integer calculateCurrentId() {
        return bikes.stream().map(bike -> bike.getId().replaceAll("BIC-(\\d+)", "$1")).map(Integer::parseInt).max(Integer::compare).orElse(0);
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

    private Boolean isValidId(String id) {
        return id.matches("BIC-\\d+");
    }

    @Override
    public List<Bike> findAll() {
        return this.bikes;
    }

    @Override
    public Optional<Bike> findById(String id) {
        if(!this.isValidId(id)) {
            throw new IllegalArgumentException("Invalid id; wrong pattern.");
        }
        return this.bikes.stream().filter(bike -> bike.getId().equals(id)).findFirst();
    }

    @Override
    public Bike save(Bike object) {
        this.assignIdField(object);
        this.bikes.add(object);
        this.saveChanges();
        return object;
    }

    private Integer indexOf(String id) {
        Bike bike = this.findById(id).orElseThrow(() -> new IllegalArgumentException(
                "The given id doesn't belong to any of the existent objects in the application."
        ));
        return this.bikes.indexOf(bike);
    }

    @Override
    public Bike update(String id, Bike updatedObject) {
        if (!id.equals(updatedObject.getId())) {
            throw new IllegalArgumentException("The id given as the first argument doesn't match the id of the object passed as second argument.");
        }
        int elementIndex = this.indexOf(id);
        this.bikes.set(elementIndex, updatedObject);
        this.saveChanges();
        return this.bikes.get(elementIndex);
    }

    @Override
    public Boolean delete(String id) {
        boolean wasDeleted;
        try {
            int elementIndex = this.indexOf(id);
            this.bikes.remove(elementIndex);
            this.saveChanges();
            wasDeleted = true;
        } catch (Exception e) {
            wasDeleted = false;
        }
        return wasDeleted;
    }
}
