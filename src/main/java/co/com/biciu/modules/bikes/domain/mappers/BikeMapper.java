package co.com.biciu.modules.bikes.domain.mappers;

import co.com.biciu.interfaces.BasicMapper;
import co.com.biciu.modules.bikes.domain.dto.BikeDTO;
import co.com.biciu.modules.bikes.persistence.entities.Bike;
import co.com.biciu.modules.bikes.persistence.entities.BikeType;

import java.util.Locale;

public class BikeMapper implements BasicMapper<Bike, BikeDTO> {
    @Override
    public BikeDTO entityToDTO(Bike entity) {
        if(entity == null) return null;

        return new BikeDTO(
                entity.getId(),
                entity.getColor(),
                entity.isAvailable(),
                entity.getType().name()
        );
    }

    @Override
    public Bike DTOToEntity(BikeDTO DTO) {
        if(DTO == null) return null;

        return new Bike(
                DTO.getBikeId(),
                DTO.getColor(),
                DTO.getAvailable(),
                BikeType.valueOf(DTO.getType().toUpperCase(Locale.ROOT).trim())
        );
    }
}
