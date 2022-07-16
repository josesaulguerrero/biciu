package co.com.biciu;

import co.com.biciu.interfaces.Repository;
import co.com.biciu.modules.bikes.entities.Bike;
import co.com.biciu.modules.bikes.persistence.repositories.BikeRepository;

public class Application {
    public static void main(String[] args) {
        Repository<Bike, String> repository = new BikeRepository();
        repository.findAll();
    }
}
