package co.com.biciu.app.UI.controllers;

import co.com.biciu.app.domain.dto.TicketDTO;
import co.com.biciu.app.domain.mappers.TicketMapper;
import co.com.biciu.app.domain.services.TicketService;
import co.com.biciu.app.persistence.entities.Ticket;
import co.com.biciu.app.persistence.entities.TicketStatus;
import co.com.biciu.interfaces.BasicMapper;
import co.com.biciu.utils.UIUtils;

import java.time.LocalDateTime;

public class TicketController {
    private final TicketService service;
    private final BasicMapper<Ticket, TicketDTO> mapper;

    public TicketController() {
        this.service = new TicketService();
        this.mapper = new TicketMapper();
    }

    public void printAll() {
        this.service.findAll().forEach(System.out::println);
    }

    private String getTicketId() {
        UIUtils.renderQuestion("Enter the Id of the ticket: ");
        return UIUtils.readWithValidator(value -> value != null && value.matches("T-\\d+"));
    }

    public Ticket create() {
        UIUtils.renderQuestion("What is your user Id?");
        String userId = UIUtils.readWithValidator(value -> value.matches("[PS]-\\w+")).trim();
        try {
            Ticket newTicket = this.service.save(
                    new TicketDTO(userId, LocalDateTime.now(), 0.0, "ACTIVE")
            );
            System.out.println("The information of the just created ticket is: ".concat(newTicket.toString()));
            return newTicket;
        } catch (Exception e) {
            throw new RuntimeException("The given id doesn't belong to any user.");
        }
    }

    public void pay() {
        String id = getTicketId();
        try {
            Ticket ticket = this.service.findById(id);
            TicketDTO dto = mapper.entityToDTO(ticket);
            dto.setTicketStatus(TicketStatus.OK.name());
            dto.setDebt(0.0);
            this.service.update(ticket.getId(), dto);
        } catch (Exception e) {
            System.out.println("The given id doesn't belong to any of the existent tickets.");
        }
        // check if all implements are okay
        // calculate debt
        // pay if credit is enough
        // reject if credit is not enough
    }
}
