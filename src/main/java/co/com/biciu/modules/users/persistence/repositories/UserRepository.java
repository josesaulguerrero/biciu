package co.com.biciu.modules.users.persistence.repositories;

import co.com.biciu.interfaces.CRUDRepository;
import co.com.biciu.modules.users.persistence.entities.User;
import co.com.biciu.modules.users.persistence.entities.UserType;
import co.com.biciu.utils.JSONUtils;
import co.com.biciu.utils.ReflectionUtils;
import com.fasterxml.jackson.core.type.TypeReference;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class UserRepository implements CRUDRepository<User, String> {
    private List<User> users;
    private final Path pathToPersistenceFile;

    public UserRepository() {
        // "" is a shortcut for the absolute path to the root folder of the project.
        this.pathToPersistenceFile = Paths.get("", "src", "main", "java", "co", "com", "biciu", "modules", "users", "persistence", "data", "users.json");
        this.loadObjectsInMemory();
    }

    private Boolean saveChanges() {
        return JSONUtils.writeJSONToFile(this.pathToPersistenceFile.toFile(), users);
    }

    private void loadObjectsInMemory() {
        TypeReference<List<User>> reference = new TypeReference<>() {};
        this.users = JSONUtils.readJSONFromFile(this.pathToPersistenceFile.toFile(), reference);
    }

    private String generateNewId(User user) {
        return (user.getType().equals(UserType.STUDENT) ? "S" : "P").concat(user.getDNI());
    }

    private void assignIdField(User object) {
        try {
            String id = generateNewId(object);
            Field field = ReflectionUtils.getIdField(User.class);
            field.setAccessible(true);
            field.set(object, id);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private Boolean isValidId(String id) {
        return id.matches("[PS]-\\w+");
    }

    @Override
    public List<User> findAll() {
        return this.users;
    }

    @Override
    public Optional<User> findById(String id) {
        if(!this.isValidId(id)) {
            throw new IllegalArgumentException("Invalid id; wrong pattern.");
        }
        return this.users.stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    @Override
    public User save(User object) {
        this.assignIdField(object);
        this.users.add(object);
        boolean wasWrittenSuccessfully = this.saveChanges();
        if (!wasWrittenSuccessfully) {
            throw new RuntimeException("Something went wrong and the object couldn't be saved. Check the Stack Trace for more information.");
        }
        return object;
    }

    private Integer indexOf(String id) {
        User user = this.findById(id).orElseThrow(() -> new IllegalArgumentException(
                "The given id doesn't belong to any of the existent objects in the application."
        ));
        return this.users.indexOf(user);
    }

    @Override
    public User update(String id, User updatedObject) {
        if (!id.equals(updatedObject.getId())) {
            throw new IllegalArgumentException("The id given as the first argument doesn't match the id of the object passed as second argument.");
        }
        int elementIndex = this.indexOf(id);
        this.users.set(elementIndex, updatedObject);
        this.saveChanges();
        return this.users.get(elementIndex);
    }

    @Override
    public Boolean delete(String id) {
        boolean wasDeleted;
        try {
            int elementIndex = this.indexOf(id);
            this.users.remove(elementIndex);
            this.saveChanges();
            wasDeleted = true;
        } catch (Exception e) {
            wasDeleted = false;
        }
        return wasDeleted;
    }
}
