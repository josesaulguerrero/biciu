package co.com.biciu.app.domain.serializers;

import co.com.biciu.app.persistence.entities.Ticket;
import co.com.biciu.app.persistence.entities.TicketDate;
import co.com.biciu.app.persistence.entities.TicketStatus;
import co.com.biciu.interfaces.Serializer;

import java.time.LocalDateTime;

public class TicketSerializer implements Serializer<Ticket, String> {
    @Override
    public String serialize(Ticket entity) {
        return entity.getId() + ";" +
                entity.getUserId() + ";" +
                entity.getDate().getStartDate() + ";" +
                entity.getDebt() + ";" +
                entity.getStatus().name() + "?";
    }

    @Override
    public Ticket deserialize(String serializedObject) {
        String[] properties = serializedObject.split(";");
        return new Ticket(
                properties[0],
                properties[1],
                new TicketDate(LocalDateTime.parse(properties[2])),
                Double.parseDouble(properties[3]),
                TicketStatus.valueOf(properties[4])
        );
    }
}
