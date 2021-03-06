package co.com.biciu.app.persistence.entities;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TicketDate {
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public TicketDate() {}

    public TicketDate(LocalDateTime startDate) {
        this.startDate = startDate;
        // by default the bikes are lent for an exact hour.
        this.endDate = startDate.plusHours(1);
    }

    public int calculateThirtyMinuteLapses(LocalDateTime now) {
        return Math.toIntExact(this.startDate.until(now, ChronoUnit.MINUTES) / 30);
    }

    public Boolean isLate(LocalDateTime now) {
        return !this.endDate.isBefore(now);
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
