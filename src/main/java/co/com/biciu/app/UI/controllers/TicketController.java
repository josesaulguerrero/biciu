package co.com.biciu.app.UI.controllers;

import co.com.biciu.app.domain.services.UserService;
import co.com.biciu.interfaces.BasicMapper;
import co.com.biciu.app.domain.dto.TicketDTO;
import co.com.biciu.app.domain.mappers.TicketMapper;
import co.com.biciu.app.domain.services.TicketService;
import co.com.biciu.app.persistence.entities.Ticket;
import co.com.biciu.utils.UIUtils;

import java.time.LocalDateTime;

public class TicketController {
    private final TicketService service;
    private final UserController userController;
    private final BasicMapper<Ticket, TicketDTO> mapper;

    public TicketController() {
        this.service = new TicketService();
        this.userController = new UserController();
        this.mapper = new TicketMapper();
    }

    public void printAll() {
        this.service.findAll().forEach(System.out::println);
    }

    public Ticket create() {
        String userId = userController.getUserId();
                // TODO add user id validation
        try {
            Ticket newTicket = this.service.save(
                    new TicketDTO(userId, true, LocalDateTime.now(), 0.0, "ACTIVE")
            );
            System.out.println("The information of the just created ticket is: ".concat(newTicket.toString()));
            return newTicket;
        } catch (Exception e) {
            System.out.println("The given id doesn't belong to any user.");
            return null;
        }
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
