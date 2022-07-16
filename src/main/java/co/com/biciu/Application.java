package co.com.biciu;

import co.com.biciu.interfaces.Repository;
import co.com.biciu.modules.bikes.domain.dto.BikeDTO;
import co.com.biciu.modules.bikes.persistence.entities.Bike;
import co.com.biciu.modules.bikes.persistence.repositories.BikeRepository;

public class Application {
    public static void main(String[] args) {
        Repository<Bike, String> repository = new BikeRepository();
        repository.findAll();
        // repository.save(new BikeDTO());
    }
}
