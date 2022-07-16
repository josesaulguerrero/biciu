package co.com.biciu;

import co.com.biciu.modules.bikes.domain.dto.BikeDTO;
import co.com.biciu.modules.bikes.domain.services.BikeService;
import co.com.biciu.modules.bikes.persistence.entities.Bike;
import co.com.biciu.modules.bikes.persistence.entities.BikeType;

public class Application {
    public static void main(String[] args) {
        BikeService service = new BikeService();
    }
}
