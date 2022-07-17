package co.com.biciu.modules.tickets.domain.services;

import co.com.biciu.interfaces.BasicMapper;
import co.com.biciu.interfaces.CRUDRepository;
import co.com.biciu.modules.tickets.domain.dto.TicketDTO;
import co.com.biciu.modules.tickets.domain.mapper.TicketMapper;
import co.com.biciu.modules.tickets.persistence.entities.Ticket;
import co.com.biciu.modules.tickets.persistence.repositories.TicketRepository;

import java.util.List;

public class TicketService {
    private final CRUDRepository<Ticket, String> repository;
    private final BasicMapper<Ticket, TicketDTO> mapper;

    public TicketService() {
        this.repository = new TicketRepository();
        this.mapper = new TicketMapper();
    }

    public List<Ticket> findAll() {
        return this.repository.findAll();
    }

    public Ticket findById(String id) {
        return this.repository.findById(id).orElseThrow();
    }

    public Ticket save(TicketDTO dto) {
        return this.repository.save(mapper.DTOToEntity(dto));
    }

    public Ticket update(String id, TicketDTO dto) {
        return this.repository.update(id, mapper.DTOToEntity(dto));
    }
}
