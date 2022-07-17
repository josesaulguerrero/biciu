package co.com.biciu.app.domain.mappers;

import co.com.biciu.app.domain.dto.UserDTO;
import co.com.biciu.app.persistence.entities.Ticket;
import co.com.biciu.app.persistence.entities.User;
import co.com.biciu.app.persistence.entities.UserType;
import co.com.biciu.interfaces.BasicMapper;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class UserMapper implements BasicMapper<User, UserDTO> {

    private List<String> getTicketsIds(List<Ticket> tickets) {
        return tickets
                .stream()
                .map(Ticket::getId)
                .collect(Collectors.toList());
    }
    @Override
    public UserDTO entityToDTO(User entity) {
        if (entity == null) return null;

        return new UserDTO(
                entity.getId(),
                entity.getType().name(),
                entity.getFullName(),
                entity.getAge(),
                entity.getCredit(),
                getTicketsIds(entity.getTickets())
        );
    }

    private List<Ticket> getTicketsFromIds(List<String> ids) {
        // TODO get tickets from the ids
        return null;
    }

    @Override
    public User DTOToEntity(UserDTO DTO) {
        if (DTO == null || (DTO.getUserId() != null && !DTO.getUserId().matches("[SP]-\\d+"))) {
            throw new IllegalArgumentException("The given DTO must be valid, otherwise it can't be mapped to a valid entity");
        }

        return new User(
                DTO.getUserId(),
                UserType.valueOf(DTO.getUserType().toUpperCase(Locale.ROOT).trim()),
                DTO.getFullName(),
                DTO.getAge(),
                DTO.getCredit(),
                getTicketsFromIds(DTO.getTicketsIds())
        );
    }
}
