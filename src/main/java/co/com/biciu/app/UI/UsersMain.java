package co.com.biciu.app.UI;

import co.com.biciu.app.UI.controllers.TicketController;
import co.com.biciu.app.UI.controllers.UserController;
import co.com.biciu.utils.UIUtils;
import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.math.NumberUtils;

public class UsersMain {
    private final UserController controller;
    private static UsersMain instance;

    private UsersMain() {
        this.controller = new UserController();
    }

    public static UsersMain getInstance() {
        if(instance == null) {
            instance = new UsersMain();
        }
        return instance;
    }

    public void main() {
        UIUtils.renderOptionsList(
                "1. Create a new user.",
                "2. Print all users information."
        );
        int selectedOption = UIUtils.readWithValidatorAndParser(
                value -> NumberUtils.isParsable(value) && Range.between(1, 2).contains(Integer.parseInt(value)),
                value -> Integer.parseInt(value.trim())
        );
        callController(selectedOption);
    }

    private void callController(int option) {
        switch (option) {
            case 1:
                controller.create();
                break;
            case 2:
                controller.printAll();
        }
    }
}
