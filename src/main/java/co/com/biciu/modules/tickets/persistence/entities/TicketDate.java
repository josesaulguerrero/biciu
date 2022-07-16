package co.com.biciu.modules.tickets.persistence.entities;

import java.time.LocalDateTime;

public class TicketDate {
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public TicketDate() {}

    public TicketDate(LocalDateTime startDate) {
        this.startDate = startDate;
        // by default the bikes are lent for an exact hour.
        this.endDate = startDate.plusHours(1);
    }

    public Boolean isLate(LocalDateTime now) {
        return now.isAfter(this.endDate);
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return "TicketDate{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
