package co.com.biciu.app.persistence.repositories;

import co.com.biciu.interfaces.CRUDRepository;
import co.com.biciu.app.persistence.entities.Ticket;
import co.com.biciu.utils.JSONUtils;
import co.com.biciu.utils.ReflectionUtils;
import com.fasterxml.jackson.core.type.TypeReference;


import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;


public class TicketRepository implements CRUDRepository<Ticket, String> {

    private List<Ticket> tickets;
    private Integer currentId;
    private final Path pathToPersistenceFile;

    public TicketRepository() {
        // "" is a shortcut for the absolute path to the root folder of the project.
        this.pathToPersistenceFile = Paths.get("", "src", "main", "java", "co", "com", "biciu", "app", "persistence", "data", "tickets.json");
        this.loadObjectsInMemory();
        this.currentId = calculateCurrentId();
    }

    private Boolean saveChanges() {
        return JSONUtils.writeJSONToFile(this.pathToPersistenceFile.toFile(), this.tickets);
    }

    private void loadObjectsInMemory() {
        TypeReference<List<Ticket>> reference = new TypeReference<>() {};
        this.tickets = JSONUtils.readJSONFromFile(this.pathToPersistenceFile.toFile(), reference);
    }

    private Integer calculateCurrentId() {
        return tickets
                .stream()
                .map(Ticket -> Ticket.getId().replaceAll("T-(\\d+)", "$1"))
                .map(Integer::parseInt)
                .max(Integer::compare)
                .orElseThrow();
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
        return this.tickets;
    }

    @Override
    public Optional<Ticket> findById(String id) {
        if(!this.isValidId(id)) {
            throw new IllegalArgumentException("Invalid id; wrong pattern.");
        }
        return this.tickets.stream().filter(ticket -> ticket.getId().equals(id)).findFirst();
    }

    @Override
    public Ticket save(Ticket object) {
        this.assignIdField(object);
        this.tickets.add(object);
        boolean wasWrittenSuccessfully = this.saveChanges();
        if (!wasWrittenSuccessfully) {
            throw new RuntimeException("Something went wrong and the object couldn't be saved. Check the Stack Trace for more information.");
        }
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
        if (!id.equals(updatedObject.getId())) {
            throw new IllegalArgumentException("The id given as the first argument doesn't match the id of the object passed as second argument.");
        }
        int elementIndex = this.indexOf(id);
        this.tickets.set(elementIndex, updatedObject);
        this.saveChanges();
        return this.tickets.get(elementIndex);
    }

    @Override
    public Boolean delete(String id) {
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
