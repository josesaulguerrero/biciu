package co.com.biciu.app.UI.controllers;

import co.com.biciu.app.domain.dto.UserDTO;
import co.com.biciu.app.domain.services.UserService;
import co.com.biciu.app.persistence.entities.User;
import co.com.biciu.app.persistence.entities.UserType;
import co.com.biciu.utils.UIUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.List;

public class UserController {
    private final UserService service;

    public UserController() {
        this.service = new UserService();
    }

    public void printAll() {
        this.service.findAll().forEach(System.out::println);
    }

    public User findUserById(String id) {
        return this.service.findById(id);
    }

    private UserDTO getUserData() {
        UIUtils.renderQuestion("What is your DNI number?");
        String DNI = UIUtils.readWithValidator(value -> value.matches("\\d{5,10}"));
        UIUtils.renderQuestion("What type of user are you? (student/professor)");
        String type = UIUtils.readWithValidator(value -> EnumUtils.isValidEnumIgnoreCase(UserType.class, value));
        UIUtils.renderQuestion("What is your name? (name surname)");
        String fullName = UIUtils.readWithValidator(value -> value.matches("[a-zA-Z]{2,20} [a-zA-Z]{2,30}"));
        UIUtils.renderQuestion("What is your age? (must be over 18)");
        int age = UIUtils.readWithValidatorAndParser(
                value -> NumberUtils.isParsable(value) && Integer.parseInt(value.trim()) > 18,
                Integer::parseInt
        );
        return new UserDTO(DNI, type, fullName, age, List.of());
    }

    public User create() {
        return this.service.save(this.getUserData());
    }

    public String getUserId() {
        UIUtils.renderQuestion("What is your user Id?");
        return UIUtils.readWithValidator(value -> value.matches("[PS]-\\d+"));
    }
}
