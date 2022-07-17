package co.com.biciu.app.persistence.entities;

import co.com.biciu.annotations.Id;

public class Ticket {
    @Id
    private String id;
    private String userId;
    private TicketDate date;
    private Double debt;
    private TicketStatus status;

    public Ticket() {
    }

    public Ticket(String userId, TicketDate date, Double debt, TicketStatus status) {
        this.userId = userId;
        this.date = date;
        this.debt = debt;
        this.status = status;
    }

    public Ticket(String id, String userId, TicketDate date, Double debt, TicketStatus status) {
        this(userId, date, debt, status);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
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
                ", userId=" + userId +
                ", debt=" + debt +
                ", status=" + status +
                '}';
    }
}
