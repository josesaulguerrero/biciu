package co.com.biciu.app.UI;

import co.com.biciu.app.UI.controllers.TicketController;
import co.com.biciu.utils.UIUtils;
import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.math.NumberUtils;

public class TicketsMain {
    private final TicketController controller;
    private static TicketsMain instance;

    private TicketsMain() {
        this.controller = new TicketController();
    }

    public static TicketsMain getInstance() {
        if(instance == null) {
            instance = new TicketsMain();
        }
        return instance;
    }

    public void main() {
        UIUtils.renderOptionsList(
                "1. Print all the tickets information.",
                "2. Pay a ticket."
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
                controller.printAll();
                break;
            case 2:
                controller.pay();
        }
    }
}
