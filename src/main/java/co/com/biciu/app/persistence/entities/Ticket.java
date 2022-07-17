package co.com.biciu.app.persistence.entities;

import co.com.biciu.annotations.Id;
import com.fasterxml.jackson.annotation.*;

public class Ticket {
    // T-NNN
    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("user")
    private User user;

    @JsonProperty("date")
    private TicketDate date;

    @JsonProperty("debt")
    private Double debt;

    @JsonProperty("status")
    private TicketStatus status;

    public Ticket() {
    }

    public Ticket(User user, TicketDate date, Double debt, TicketStatus status) {
        this();
        this.user = user;
        this.date = date;
        this.debt = debt;
        this.status = status;
    }

    public Ticket(String id, User user, TicketDate date, Double debt, TicketStatus status) {
        this(user, date, debt, status);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public TicketDate getDate() {
        return date;
    }

    public Double getDebt() {
        return debt;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setDebt(Double debt) {
        this.debt = debt;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id='" + id + '\'' +
                ", user=" + user.getFullName() + " - " + user.getId() +
                ", debt=" + debt +
                ", status=" + status +
                '}';
    }
}
