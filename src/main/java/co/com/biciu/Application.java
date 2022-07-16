package co.com.biciu;

import co.com.biciu.interfaces.CRUDRepository;
import co.com.biciu.modules.bikes.persistence.entities.Bike;
import co.com.biciu.modules.bikes.persistence.entities.BikeType;
import co.com.biciu.modules.bikes.persistence.repositories.BikeRepository;

public class Application {
    public static void main(String[] args) {
        CRUDRepository<Bike, String> repository = new BikeRepository();
        repository.findAll();
        Bike bike = repository.save(new Bike("BIC-1", "Blue", true, BikeType.MOUNTAIN));
        System.out.println("bike = " + bike);
    }
}
