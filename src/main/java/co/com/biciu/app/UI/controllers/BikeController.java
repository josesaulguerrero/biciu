package co.com.biciu.app.UI.controllers;

import co.com.biciu.app.domain.dto.BikeDTO;
import co.com.biciu.app.domain.mappers.BikeMapper;
import co.com.biciu.app.domain.services.BikeService;
import co.com.biciu.app.persistence.entities.Bike;
import co.com.biciu.app.persistence.entities.BikeType;
import co.com.biciu.interfaces.BasicMapper;
import co.com.biciu.utils.UIUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.math.NumberUtils;

public class BikeController {
    // TODO borrow bicycle!!
    private final BikeService service;
    private final BasicMapper<Bike, BikeDTO> mapper;

    public BikeController() {
        this.service = new BikeService();
        this.mapper = new BikeMapper();
    }

    public void printAll() {
        this.service.findAll().forEach(System.out::println);
    }

    public void add() {
        UIUtils.renderQuestion("What color is the bike?");
        String color = UIUtils.read();
        UIUtils.renderQuestion("What is the type of the bike? (MOUNTAIN/ROAD)");
        String type = UIUtils.readWithValidator((value) -> EnumUtils.isValidEnumIgnoreCase(BikeType.class, value));
        Bike newBike = this.service.save(new BikeDTO(color, true, type));
        System.out.println("The information of the just created bike is: ".concat(newBike.toString()));
    }

    private String getBikeId() {
        UIUtils.renderQuestion("Enter the Id of the bike: ");
        return UIUtils.readWithValidator(value -> value != null && value.matches("BIC-\\d+"));
    }

    private Bike updateColor(String id, Bike bike) {
        UIUtils.renderQuestion("What is the new color?");
        String color = UIUtils.read();
        BikeDTO dto = mapper.entityToDTO(bike);
        dto.setColor(color);
        return this.service.update(id, dto);
    }

    private Bike updateType(String id, Bike bike) {
        UIUtils.renderQuestion("What is the new type of the bike?");
        String type = UIUtils.readWithValidator(value -> EnumUtils.isValidEnumIgnoreCase(BikeType.class, value));
        BikeDTO dto = mapper.entityToDTO(bike);
        dto.setType(type);
        return this.service.update(id, dto);
    }

    public void update() {
        String id = getBikeId();
        Bike bike = this.service.findById(id);
        UIUtils.renderOptionsList("What field do you want to update? (Enter the number)", "1. Color", "2. Type");
        Integer option = UIUtils.readWithValidatorAndParser(
                value -> NumberUtils.isParsable(value) && Range.between(1, 2).contains(Integer.parseInt(value)),
                value -> Integer.parseInt(value.trim())
        );
        Bike updatedBike;
        if(option.equals(1)) {
            updatedBike = this.updateColor(id, bike);
        } else {
            updatedBike = this.updateType(id, bike);
        }
        System.out.println("The updated information is: ".concat(updatedBike.toString()));
    }

    public void delete() {
        String id = this.getBikeId();
        boolean wasDeleted = this.service.delete(id);
        System.out.println(wasDeleted ? "The bike register was successfully deleted" : "Something went wrong, please try again later.");
    }
}
