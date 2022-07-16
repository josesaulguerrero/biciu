package co.com.biciu.modules.tickets.persistence.entities;

import co.com.biciu.annotations.Id;

public class Ticket {
    // T-NNN
    @Id
    private String id;

    // private User user;

    private Boolean helmetWasSupplied;

    private TicketDate date;

    private Integer debt;

    private TicketStatus status;
}
