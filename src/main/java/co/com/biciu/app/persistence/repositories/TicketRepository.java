package co.com.biciu.app.persistence.repositories;

import co.com.biciu.app.domain.serializers.TicketSerializer;
import co.com.biciu.interfaces.CRUDRepository;
import co.com.biciu.app.persistence.entities.Ticket;
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


public class TicketRepository implements CRUDRepository<Ticket, String> {

    private List<Ticket> tickets;
    private Integer currentId;
    private final Path pathToPersistenceFile;
    private final Serializer<Ticket, String> serializer;

    public TicketRepository() {
        this.serializer = new TicketSerializer();
        // "" is a shortcut for the absolute path to the root folder of the project.
        this.pathToPersistenceFile = Paths.get("", "src", "main", "java", "co", "com", "biciu", "app", "persistence", "data", "tickets.txt");
        this.loadObjectsInMemory();
        this.currentId = calculateCurrentId();
    }

    private void saveChanges() {
        String serializedTickets = this.tickets.stream().map(serializer::serialize).collect(Collectors.joining());
        FileUtils.writeToFile(this.pathToPersistenceFile.toFile(), serializedTickets);
    }

    private void loadObjectsInMemory() {
        String content = FileUtils.readFromFile(this.pathToPersistenceFile.toFile());
        this.tickets = Arrays.stream(content.trim().split("\\?"))
                .filter(line -> !line.isEmpty())
                .map(serializer::deserialize)
                .collect(Collectors.toList());
    }

    private Integer calculateCurrentId() {
        return tickets
                .stream()
                .map(Ticket -> Ticket.getId().replaceAll("T-(\\d+)", "$1"))
                .map(Integer::parseInt)
                .max(Integer::compare)
                .orElse(0);
    }

    private String generateNewId() {
        int newId = Integer.sum(this.currentId, 1);
        this.currentId = newId;
        return "T-".concat(String.valueOf(newId));
    }

    private void assignIdField(Ticket object) {
        try {
            String id = generateNewId();
            Field field = ReflectionUtils.getIdField(Ticket.class);
            field.setAccessible(true);
            field.set(object, id);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private Boolean isValidId(String id) {
        return id.matches("T-\\d+");
    }

    @Override
    public List<Ticket> findAll() {
        this.loadObjectsInMemory();
        return this.tickets;
    }

    @Override
    public Optional<Ticket> findById(String id) {
        this.loadObjectsInMemory();
        if (!this.isValidId(id)) {
            throw new IllegalArgumentException("Invalid id; wrong pattern.");
        }
        return this.tickets.stream().filter(ticket -> ticket.getId().equals(id)).findFirst();
    }

    @Override
    public Ticket save(Ticket object) {
        this.loadObjectsInMemory();
        this.assignIdField(object);
        this.tickets.add(object);
        this.saveChanges();
        return object;
    }

    private Integer indexOf(String id) {
        Ticket Ticket = this.findById(id).orElseThrow(() -> new IllegalArgumentException(
                "The given id doesn't belong to any of the existent objects in the application."
        ));
        return this.tickets.indexOf(Ticket);
    }

    @Override
    public Ticket update(String id, Ticket updatedObject) {
        this.loadObjectsInMemory();
        if (!id.equals(updatedObject.getId())) {
            throw new IllegalArgumentException("The id given as the first argument doesn't match the id of the object passed as second argument.");
        }
        System.out.println(updatedObject);
        int elementIndex = this.indexOf(id);
        this.tickets.set(elementIndex, updatedObject);
        this.saveChanges();
        return this.tickets.get(elementIndex);
    }

    @Override
    public Boolean delete(String id) {
        this.loadObjectsInMemory();
        boolean wasDeleted;
        try {
            int elementIndex = this.indexOf(id);
            this.tickets.remove(elementIndex);
            this.saveChanges();
            wasDeleted = true;
        } catch (Exception e) {
            wasDeleted = false;
        }
        return wasDeleted;
    }
}
