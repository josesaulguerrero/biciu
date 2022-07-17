package co.com.biciu.app.UI.controllers;

import co.com.biciu.app.domain.dto.BikeDTO;
import co.com.biciu.app.domain.dto.TicketDTO;
import co.com.biciu.app.domain.mappers.BikeMapper;
import co.com.biciu.app.domain.services.BikeService;
import co.com.biciu.app.domain.services.TicketService;
import co.com.biciu.app.domain.services.UserService;
import co.com.biciu.app.persistence.entities.Bike;
import co.com.biciu.app.persistence.entities.BikeType;
import co.com.biciu.app.persistence.entities.Ticket;
import co.com.biciu.interfaces.BasicMapper;
import co.com.biciu.utils.UIUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.math.NumberUtils;

import java.time.LocalDateTime;

public class BikeController {
    private final BikeService service;
    private final UserService userService;
    private final TicketService ticketService;
    private final BasicMapper<Bike, BikeDTO> mapper;

    public BikeController() {
        this.service = new BikeService();
        this.userService = new UserService();
        this.ticketService = new TicketService();
        this.mapper = new BikeMapper();
    }

    public void printAll() {
        this.service.findAll().forEach(System.out::println);
    }

    public void add() {
        UIUtils.renderQuestion("What color is the bike?");
        String color = UIUtils.read();
        UIUtils.renderQuestion("What is the type of the bike? (MOUNTAIN/ROAD)");
        String type = UIUtils.readWithValidator((value) -> EnumUtils.isValidEnumIgnoreCase(BikeType.class, value));
        Bike newBike = this.service.save(new BikeDTO(color, true, type));
        System.out.println("The information of the just created bike is: ".concat(newBike.toString()));
    }

    private String getBikeId() {
        UIUtils.renderQuestion("Enter the Id of the bike: ");
        return UIUtils.readWithValidator(value -> value != null && value.matches("BIC-\\d+"));
    }

    private Bike updateColor(String id, Bike bike) {
        UIUtils.renderQuestion("What is the new color?");
        String color = UIUtils.read();
        BikeDTO dto = mapper.entityToDTO(bike);
        dto.setColor(color);
        return this.service.update(id, dto);
    }

    private Bike updateType(String id, Bike bike) {
        UIUtils.renderQuestion("What is the new type of the bike?");
        String type = UIUtils.readWithValidator(value -> EnumUtils.isValidEnumIgnoreCase(BikeType.class, value));
        BikeDTO dto = mapper.entityToDTO(bike);
        dto.setType(type);
        return this.service.update(id, dto);
    }

    public void update() {
        String id = getBikeId();
        Bike bike = this.service.findById(id);
        UIUtils.renderOptionsList("What field do you want to update? (Enter the number)", "1. Color", "2. Type");
        Integer option = UIUtils.readWithValidatorAndParser(
                value -> NumberUtils.isParsable(value) && Range.between(1, 2).contains(Integer.parseInt(value)),
                value -> Integer.parseInt(value.trim())
        );
        Bike updatedBike = option.equals(1) ? this.updateColor(id, bike) : this.updateType(id, bike);
        System.out.println("The updated information is: ".concat(updatedBike.toString()));
    }

    private Ticket createTicket() {
        UIUtils.renderQuestion("What is your user Id?");
        String userId = UIUtils.readWithValidator(value -> value.matches("[PS]-\\d+"));
        try {
            Ticket newTicket = this.ticketService.save(
                    new TicketDTO(userId, true, LocalDateTime.now(), 0.0, "ACTIVE")
            );
            System.out.println("The information of the just created ticket is: ".concat(newTicket.toString()));
            return newTicket;
        } catch (Exception e) {
            throw new RuntimeException("The given id doesn't belong to any user.");
        }
    }

    public void borrow() {
        UIUtils.renderQuestion("What is your user Id?");
        String userId = UIUtils.readWithValidator(value -> value != null && value.matches("[PS]-\\d+"));
        try {
            Ticket ticket = this.createTicket();
            this.userService.addNewTicket(userId, ticket);
            Bike availableBike = this.service.findAvailable();
            BikeDTO dto = mapper.entityToDTO(availableBike);
            dto.setAvailable(false);
            this.service.update(availableBike.getId(), dto);
            System.out.println("Your assigned bike is: ".concat(availableBike.toString()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
