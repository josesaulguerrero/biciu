package co.com.biciu.modules.tickets.persistence.entities;

import co.com.biciu.annotations.Id;
import co.com.biciu.modules.users.persistence.entities.User;

public class Ticket {
    // T-NNN
    @Id
    private String id;

    private User user;

    private Boolean helmetWasSupplied;

    private TicketDate date;

    private Integer debt;

    private TicketStatus status;

    public Ticket() {
    }

    public Ticket(User user, Boolean helmetWasSupplied, TicketDate date, Integer debt, TicketStatus status) {
        this.user = user;
        this.helmetWasSupplied = helmetWasSupplied;
        this.date = date;
        this.debt = debt;
        this.status = status;
    }

    public Ticket(String id, User user, Boolean helmetWasSupplied, TicketDate date, Integer debt, TicketStatus status) {
        this.id = id;
        this.user = user;
        this.helmetWasSupplied = helmetWasSupplied;
        this.date = date;
        this.debt = debt;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Boolean getHelmetWasSupplied() {
        return helmetWasSupplied;
    }

    public TicketDate getDate() {
        return date;
    }

    public Integer getDebt() {
        return debt;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setDebt(Integer debt) {
        this.debt = debt;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id='" + id + '\'' +
                ", user=" + user +
                ", helmetWasSupplied=" + helmetWasSupplied +
                ", date=" + date +
                ", debt=" + debt +
                ", status=" + status +
                '}';
    }
}
