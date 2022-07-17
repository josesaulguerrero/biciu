package co.com.biciu.app.domain.services;

import co.com.biciu.app.domain.mappers.BikeMapper;
import co.com.biciu.interfaces.BasicMapper;
import co.com.biciu.interfaces.CRUDRepository;
import co.com.biciu.app.domain.dto.BikeDTO;
import co.com.biciu.app.persistence.entities.Bike;
import co.com.biciu.app.persistence.repositories.BikeRepository;

import java.util.List;

public class BikeService {
    private final CRUDRepository<Bike, String> repository;
    private final BasicMapper<Bike, BikeDTO> mapper;

    public BikeService() {
        this.repository = new BikeRepository();
        this.mapper = new BikeMapper();
    }

    public List<Bike> findAll() {
        return this.repository.findAll();
    }

    public Bike findById(String id) {
        return this.repository.findById(id).orElseThrow();
    }

    public Bike save(BikeDTO dto) {
        return this.repository.save(mapper.DTOToEntity(dto));
    }

    public Bike update(String id, BikeDTO dto) {
        return this.repository.update(id, mapper.DTOToEntity(dto));
    }

    public Boolean delete(String id) {
        return this.repository.delete(id);
    }
}
