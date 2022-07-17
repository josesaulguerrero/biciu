package co.com.biciu.app.domain.mappers;

import co.com.biciu.app.persistence.entities.User;
import co.com.biciu.app.persistence.repositories.UserRepository;
import co.com.biciu.interfaces.BasicMapper;
import co.com.biciu.app.domain.dto.TicketDTO;
import co.com.biciu.app.persistence.entities.Ticket;
import co.com.biciu.app.persistence.entities.TicketDate;
import co.com.biciu.app.persistence.entities.TicketStatus;
import co.com.biciu.interfaces.CRUDRepository;

import java.util.Locale;

public class TicketMapper implements BasicMapper<Ticket, TicketDTO> {
    @Override
    public TicketDTO entityToDTO(Ticket entity) {
        if (entity == null) return null;

        return new TicketDTO(
                entity.getId(),
                entity.getUserId(),
                entity.getDate().getStartDate(),
                entity.getDebt(),
                entity.getStatus().name()
        );
    }

    @Override
    public Ticket DTOToEntity(TicketDTO DTO) {
        if (DTO == null || (DTO.getTicketId() != null && !DTO.getTicketId().matches("T-\\d+"))) {
            throw new IllegalArgumentException("The given DTO must be valid, otherwise it can't be mapped to a valid entity");
        }
        return new Ticket(
                DTO.getTicketId(),
                DTO.getUserId(),
                new TicketDate(DTO.getStartDate()),
                DTO.getDebt(),
                TicketStatus.valueOf(DTO.getTicketStatus().toUpperCase(Locale.ROOT).trim())
        );
    }
}
