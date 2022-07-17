package co.com.biciu.app.domain.services;

import co.com.biciu.app.domain.dto.UserDTO;
import co.com.biciu.app.domain.mappers.UserMapper;
import co.com.biciu.app.persistence.entities.Ticket;
import co.com.biciu.app.persistence.entities.User;
import co.com.biciu.app.persistence.repositories.UserRepository;
import co.com.biciu.interfaces.BasicMapper;
import co.com.biciu.interfaces.CRUDRepository;

import java.util.List;

public class UserService {
    private final CRUDRepository<User, String> repository;
    private final BasicMapper<User, UserDTO> mapper;

    public UserService() {
        this.repository = new UserRepository();
        this.mapper = new UserMapper();
    }

    public List<User> findAll() {
        return this.repository.findAll();
    }

    public User findById(String id) {
        return this.repository.findById(id).orElseThrow();
    }

    public User save(UserDTO dto) {
        return this.repository.save(mapper.DTOToEntity(dto));
    }

    public User update(String id, UserDTO dto) {
        return this.repository.update(id, mapper.DTOToEntity(dto));
    }

    public User addNewTicket(String id, Ticket ticket) {
        User user = this.findById(id);
        UserDTO dto = mapper.entityToDTO(user);
        dto.addTicketId(ticket.getId());
        return this.update(id, dto);
    }
}
