package co.com.biciu.modules.bikes.persistence.repositories;

import co.com.biciu.interfaces.Repository;
import co.com.biciu.modules.bikes.entities.Bike;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class BikeRepository implements Repository<Bike, String> {
    @Override
    public List<Bike> findAll() {
        try {
            // "" is a shortcut for the absolute path to the root folder of the project.
            Path path = Paths.get("",
                    "src", "main", "java", "co", "com", "biciu", "modules", "bikes", "persistence", "data", "bikes.json");
            FileReader reader = new FileReader(new File(path.toUri()));
            ObjectMapper mapper = new ObjectMapper();
            List<Bike> bikes = mapper.readValue(reader, new TypeReference<>() {});
            bikes.forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return List.of();
    }

    @Override
    public Bike findById(String id) {
        return null;
    }

    @Override
    public Bike save(Bike object) {
        return null;
    }

    @Override
    public Bike update(String id, Bike updatedObject) {
        return null;
    }

    @Override
    public Boolean delete(String id) {
        return null;
    }
}
