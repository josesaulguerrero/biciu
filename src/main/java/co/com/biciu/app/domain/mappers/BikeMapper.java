package co.com.biciu.app.domain.mappers;

import co.com.biciu.interfaces.BasicMapper;
import co.com.biciu.app.domain.dto.BikeDTO;
import co.com.biciu.app.persistence.entities.Bike;
import co.com.biciu.app.persistence.entities.BikeType;

import java.util.Locale;

public class BikeMapper implements BasicMapper<Bike, BikeDTO> {
    @Override
    public BikeDTO entityToDTO(Bike entity) {
        if (entity == null) return null;

        return new BikeDTO(
                entity.getId(),
                entity.getColor(),
                entity.isAvailable(),
                entity.getType().name()
        );
    }

    @Override
    public Bike DTOToEntity(BikeDTO DTO) {
        if (DTO == null || (DTO.getBikeId() != null && !DTO.getBikeId().matches("BIC-\\d+"))) {
            throw new IllegalArgumentException("The given DTO must be valid, otherwise it can't be mapped to a valid entity");
        }

        return new Bike(
                DTO.getBikeId(),
                DTO.getColor(),
                DTO.getAvailable(),
                BikeType.valueOf(DTO.getType().toUpperCase(Locale.ROOT).trim())
        );
    }
}
