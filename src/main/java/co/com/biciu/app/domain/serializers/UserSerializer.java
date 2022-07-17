package co.com.biciu.app.domain.serializers;

import co.com.biciu.app.domain.services.TicketService;
import co.com.biciu.app.persistence.entities.Ticket;
import co.com.biciu.app.persistence.entities.User;
import co.com.biciu.app.persistence.entities.UserType;
import co.com.biciu.interfaces.Serializer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserSerializer implements Serializer<User, String> {
    private final TicketService ticketService;

    public UserSerializer() {
        this.ticketService = new TicketService();
    }

    @Override
    public String serialize(User entity) {
        String stringifiedTickets = entity.getTickets()
                .stream()
                .map(Ticket::getId)
                .reduce("", (accum, id) -> accum.concat(id).concat(","));

        return entity.getId() + ";" +
                entity.getDNI() + ";" +
                entity.getType().name() + ";" +
                entity.getFullName() + ";" +
                entity.getAge() + ";" +
                stringifiedTickets + "?";
    }

    private List<Ticket> getAssociatedTickets(String[] ids) {
        System.out.println("ids.toString() = " + Arrays.toString(ids));
        return Arrays.stream(ids)
                .filter(id -> id.matches("T-\\d+"))
                .map(ticketService::findById)
                .collect(Collectors.toList());
    }

    @Override
    public User deserialize(String serializedObject) {
        String[] properties = serializedObject.split(";");
        List<Ticket> associatedTickets = getAssociatedTickets(properties[5].split(","));
        return new User(
                properties[0],
                properties[1],
                UserType.valueOf(properties[2]),
                properties[3],
                Integer.valueOf(properties[4]),
                associatedTickets
        );
    }
}
