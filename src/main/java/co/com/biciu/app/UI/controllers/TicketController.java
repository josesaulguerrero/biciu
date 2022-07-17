package co.com.biciu.app.UI.controllers;

import co.com.biciu.app.domain.dto.TicketDTO;
import co.com.biciu.app.domain.services.TicketService;
import co.com.biciu.app.persistence.entities.Ticket;
import co.com.biciu.utils.UIUtils;

import java.time.LocalDateTime;

public class TicketController {
    private final TicketService service;

    public TicketController() {
        this.service = new TicketService();
    }

    public void printAll() {
        this.service.findAll().forEach(System.out::println);
    }

    private String getTicketId() {
        UIUtils.renderQuestion("Enter the Id of the ticket: ");
        return UIUtils.readWithValidator(value -> value != null && value.matches("[PS]-\\d+"));
    }

    public void pay() {
        String id = getTicketId();
        Ticket bike = this.service.findById(id);
        // check if all implements are okay
        // calculate debt
        // pay if credit is enough
        // reject if credit is not enough
    }
}
