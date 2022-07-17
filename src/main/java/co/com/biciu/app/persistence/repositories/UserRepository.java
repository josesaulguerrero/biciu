package co.com.biciu.app.persistence.repositories;

import co.com.biciu.app.domain.serializers.UserSerializer;
import co.com.biciu.interfaces.CRUDRepository;
import co.com.biciu.app.persistence.entities.User;
import co.com.biciu.app.persistence.entities.UserType;
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

public class UserRepository implements CRUDRepository<User, String> {
    private List<User> users;
    private final Path pathToPersistenceFile;
    private final Serializer<User, String> serializer;

    public UserRepository() {
        this.serializer = new UserSerializer();
        // "" is a shortcut for the absolute path to the root folder of the project.
        this.pathToPersistenceFile = Paths.get("", "src", "main", "java", "co", "com", "biciu", "app", "persistence", "data", "users.txt");
        this.loadObjectsInMemory();
    }

    private void saveChanges() {
        String serializedUsers = this.users.stream().map(serializer::serialize).collect(Collectors.joining());
        FileUtils.writeToFile(this.pathToPersistenceFile.toFile(), serializedUsers);
    }

    private void loadObjectsInMemory() {
        String content = FileUtils.readFromFile(this.pathToPersistenceFile.toFile());
        this.users = Arrays.stream(content.split("\\?"))
                .filter(line -> !line.isEmpty())
                .map(serializer::deserialize)
                .collect(Collectors.toList());
    }

    private String generateNewId(User user) {
        return (user.getType().equals(UserType.STUDENT) ? "S" : "P").concat("-").concat(user.getDNI());
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
        this.loadObjectsInMemory();
        return this.users;
    }

    @Override
    public Optional<User> findById(String id) {
        this.loadObjectsInMemory();
        if(!this.isValidId(id)) {
            throw new IllegalArgumentException("Invalid id; wrong pattern.");
        }
        return this.users.stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    @Override
    public User save(User object) {
        this.loadObjectsInMemory();
        this.assignIdField(object);
        this.users.add(object);
        this.saveChanges();
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
