package co.com.biciu.app.domain.mappers;

import co.com.biciu.app.domain.services.UserService;
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
    private final CRUDRepository<User, String> repository;

    public TicketMapper() {
        this.repository = new UserRepository();
    }

    @Override
    public TicketDTO entityToDTO(Ticket entity) {
        if (entity == null) return null;

        return new TicketDTO(
                entity.getId(),
                entity.getHelmetWasSupplied(),
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

        User user = this.repository.findById(DTO.getUserId()).orElseThrow(() -> new IllegalStateException("The id of this DTO doesn't belong to any of the registered users."));
        return new Ticket(
                DTO.getTicketId(),
                user,
                DTO.getSuppliedHelmet(),
                new TicketDate(DTO.getStartDate()),
                DTO.getDebt(),
                TicketStatus.valueOf(DTO.getTicketStatus().toUpperCase(Locale.ROOT).trim())
        );
    }
}
