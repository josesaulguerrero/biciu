package co.com.biciu.app.UI.controllers;

import co.com.biciu.app.domain.dto.BikeDTO;
import co.com.biciu.app.domain.dto.TicketDTO;
import co.com.biciu.app.domain.mappers.BikeMapper;
import co.com.biciu.app.domain.mappers.TicketMapper;
import co.com.biciu.app.domain.services.BikeService;
import co.com.biciu.app.domain.services.TicketService;
import co.com.biciu.app.domain.services.UserService;
import co.com.biciu.app.persistence.entities.Bike;
import co.com.biciu.app.persistence.entities.BikeType;
import co.com.biciu.app.persistence.entities.Ticket;
import co.com.biciu.app.persistence.entities.TicketStatus;
import co.com.biciu.interfaces.BasicMapper;
import co.com.biciu.utils.UIUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.math.NumberUtils;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Predicate;

public class BikeController {
    private final BikeService service;
    private final UserService userService;
    private final TicketService ticketService;
    private final TicketController ticketController;
    private final BasicMapper<Ticket, TicketDTO> ticketMapper;
    private final BasicMapper<Bike, BikeDTO> mapper;

    public BikeController() {
        this.service = new BikeService();
        this.userService = new UserService();
        this.ticketService = new TicketService();
        this.mapper = new BikeMapper();
        this.ticketMapper = new TicketMapper();
        this.ticketController = new TicketController();
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

    public void borrow() {
        try {
            Ticket ticket = this.ticketController.create();
            this.userService.addNewTicket(ticket.getUserId(), ticket);
            Bike givenBike = this.service.findById(ticket.getBikeId());
            BikeDTO dto = mapper.entityToDTO(givenBike);
            dto.setAvailable(false);
            this.service.update(givenBike.getId(), dto);
            System.out.println("Your assigned bike is: ".concat(givenBike.toString()));
            System.out.println("Remember to retrieve it by one hour or you'll get fees every 30 minutes!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private Double calculateDebt(Ticket ticket, boolean helmetReturned, boolean helmetDamaged, boolean bikeDamaged) {
        double debt = 0.0;
        LocalDateTime now = LocalDateTime.now();
        boolean isLate = ticket.getDate().isLate(now);
        debt += isLate ? ticket.getDate().calculateThirtyMinuteLapses(now) * 3 : 0.0;
        debt += !helmetReturned ? 5 : 0.0;
        debt += helmetDamaged ? 5 : 0.0;
        debt += bikeDamaged ? 5 : 0.0;
        return debt;
    }

    private void performUpdate(Ticket ticket, Bike bike) {
        Predicate<String> yesOrNOValidator = value -> value.toUpperCase(Locale.ROOT).trim().matches("(Y(ES)?|N(O)?)");
        Function<String, Boolean> yesOrNoParser = value -> value.toUpperCase(Locale.ROOT).trim().matches("Y(ES)?");

        UIUtils.renderQuestion("Was the helmet returned? (Y/n)");
        boolean helmetReturned = UIUtils.readWithValidatorAndParser(yesOrNOValidator, yesOrNoParser);
        UIUtils.renderQuestion("Was the helmet damaged? (Y/n)");
        boolean helmetDamaged = UIUtils.readWithValidatorAndParser(yesOrNOValidator, yesOrNoParser);
        UIUtils.renderQuestion("Was the bike damaged? (Y/n)");
        boolean bikeDamaged = UIUtils.readWithValidatorAndParser(yesOrNOValidator, yesOrNoParser);
        Double debt = this.calculateDebt(ticket, helmetReturned, helmetDamaged, bikeDamaged);

        //update ticket
        TicketDTO ticketDTO = ticketMapper.entityToDTO(ticket);
        ticketDTO.setTicketStatus(debt > 0 ? TicketStatus.PENDING.name() : TicketStatus.OK.name());
        ticketDTO.setDebt(debt);
        Ticket updatedTicket = this.ticketService.update(ticket.getId(), ticketDTO);
        //update bike data
        BikeDTO bikeDTO = mapper.entityToDTO(bike);
        bikeDTO.setAvailable(true);
        this.service.update(bikeDTO.getBikeId(), bikeDTO);
        System.out.println(debt > 0
                ? "Your debt is: ".concat(String.valueOf(updatedTicket.getDebt()))
                : "You don't have to pay anything! Thanks for using our service :)"
        );
    }

    public void returnBike() {
        UIUtils.renderQuestion("What is your ticket Id?");
        String ticketId = UIUtils.readWithValidator(value -> value.matches("T-\\d+")).trim();
        try {
            Ticket ticket = this.ticketService.findById(ticketId);
            Bike bike = this.service.findById(ticket.getBikeId());
            if(ticket.getStatus() == TicketStatus.ACTIVE) {
                performUpdate(ticket, bike);
            } else {
                System.out.println("This ticket is ".concat(ticket.getStatus().name()).concat("; the bike was already returned."));
            }
        } catch (Exception e) {
            System.out.println("The given id doesn't belong to any of the existent tickets.");
        }
    }
}
