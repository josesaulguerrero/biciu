package co.com.biciu.app.domain.dto;

import java.time.LocalDateTime;

public class TicketDTO {
    private String ticketId;
    private String userId;
    private Boolean suppliedHelmet;
    private LocalDateTime startDate;
    private Double debt;
    private String ticketStatus;

    public TicketDTO() {
    }

    public TicketDTO(String userId, Boolean suppliedHelmet, LocalDateTime startDate, Double debt, String status) {
        this.userId = userId;
        this.suppliedHelmet = suppliedHelmet;
        this.startDate = startDate;
        this.debt = debt;
        this.ticketStatus = status;
    }

    public TicketDTO(String ticketId, String userId, Boolean suppliedHelmet, LocalDateTime startDate, Double debt, String ticketStatus) {
        this.ticketId = ticketId;
        this.userId = userId;
        this.suppliedHelmet = suppliedHelmet;
        this.startDate = startDate;
        this.debt = debt;
        this.ticketStatus = ticketStatus;
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getUserId() {
        return userId;
    }

    public Boolean getSuppliedHelmet() {
        return suppliedHelmet;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public Double getDebt() {
        return debt;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }
}
