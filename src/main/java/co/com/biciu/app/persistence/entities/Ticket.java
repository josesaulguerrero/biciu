package co.com.biciu.app.persistence.entities;

import co.com.biciu.annotations.Id;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Ticket {
    // T-NNN
    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("user")
    private User user;

    @JsonProperty("suppliedHelmet")
    private Boolean helmetWasSupplied;

    @JsonProperty("date")
    private TicketDate date;

    @JsonProperty("debt")
    private Double debt;

    @JsonProperty("status")
    private TicketStatus status;

    public Ticket() {
    }

    public Ticket(User user, Boolean helmetWasSupplied, TicketDate date, Double debt, TicketStatus status) {
        this.user = user;
        this.helmetWasSupplied = helmetWasSupplied;
        this.date = date;
        this.debt = debt;
        this.status = status;
    }

    public Ticket(String id, User user, Boolean helmetWasSupplied, TicketDate date, Double debt, TicketStatus status) {
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
                ", user=" + user +
                ", helmetWasSupplied=" + helmetWasSupplied +
                ", date=" + date +
                ", debt=" + debt +
                ", status=" + status +
                '}';
    }
}
