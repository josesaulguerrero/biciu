package co.com.biciu.app.domain.dto;

import java.time.LocalDateTime;

public class TicketDTO {
    private String ticketId;
    private String bikeId;
    private String userId;
    private LocalDateTime startDate;
    private Double debt;
    private String ticketStatus;

    public TicketDTO() {
    }

    public TicketDTO(String bikeId, String userId, LocalDateTime startDate, Double debt, String status) {
        this.bikeId = bikeId;
        this.userId = userId;
        this.startDate = startDate;
        this.debt = debt;
        this.ticketStatus = status;
    }

    public TicketDTO(String ticketId, String bikeId, String userId, LocalDateTime startDate, Double debt, String ticketStatus) {
        this.ticketId = ticketId;
        this.bikeId = bikeId;
        this.userId = userId;
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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public Double getDebt() {
        return debt;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public String getBikeId() {
        return bikeId;
    }

    public void setDebt(Double debt) {
        this.debt = debt;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    @Override
    public String toString() {
        return "TicketDTO{" +
                "ticketId='" + ticketId + '\'' +
                ", userId='" + userId + '\'' +
                ", startDate=" + startDate +
                ", debt=" + debt +
                ", ticketStatus='" + ticketStatus + '\'' +
                '}';
    }
}
