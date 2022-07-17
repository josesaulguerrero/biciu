package co.com.biciu.modules.tickets.domain.dto;

import java.time.LocalDateTime;

public class TicketDTO {
    private String id;
    private String userId;
    private Boolean suppliedHelmet;
    private LocalDateTime startDate;
    private Integer debt;
    private String status;

    public TicketDTO() {
    }

    public TicketDTO(Boolean suppliedHelmet, LocalDateTime startDate, Integer debt, String status) {
        this.suppliedHelmet = suppliedHelmet;
        this.startDate = startDate;
        this.debt = debt;
        this.status = status;
    }

    public TicketDTO(String userId, Boolean suppliedHelmet, LocalDateTime startDate, Integer debt, String status) {
        this.userId = userId;
        this.suppliedHelmet = suppliedHelmet;
        this.startDate = startDate;
        this.debt = debt;
        this.status = status;
    }

    public String getId() {
        return id;
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

    public Integer getDebt() {
        return debt;
    }

    public String getStatus() {
        return status;
    }

    public void setDebt(Integer debt) {
        this.debt = debt;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
