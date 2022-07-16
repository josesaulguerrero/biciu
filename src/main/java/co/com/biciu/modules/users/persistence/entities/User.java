package co.com.biciu.modules.users.persistence.entities;

import co.com.biciu.annotations.Id;
import co.com.biciu.modules.tickets.persistence.entities.Ticket;

import java.util.List;

public class User {
    // P-DNI or S-DNI
    @Id
    private String id;

    private String DNI;

    private UserType type;

    // name lastname
    private String fullName;

    //over 18
    private Integer age;

    private List<Ticket> tickets;
}
