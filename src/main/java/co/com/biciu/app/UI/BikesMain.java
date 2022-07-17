package co.com.biciu.app.UI;

import co.com.biciu.app.UI.controllers.BikeController;
import co.com.biciu.utils.UIUtils;
import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.math.NumberUtils;

public class BikesMain {
    private final BikeController bikeController;
    private static BikesMain instance;

    private BikesMain() {
        this.bikeController = new BikeController();
    }

    public static BikesMain getInstance() {
        if(instance == null) {
            instance = new BikesMain();
        }
        return instance;
    }

    public void main() {
        UIUtils.renderOptionsList(
                "1. Add a new bike to the register.",
                "2. Print all the bikes information.",
                "3. Update a bike information.",
                "4. Borrow a bike."
        );
        int selectedOption = UIUtils.readWithValidatorAndParser(
                value -> NumberUtils.isParsable(value) && Range.between(1, 4).contains(Integer.parseInt(value)),
                value -> Integer.parseInt(value.trim())
        );
        callController(selectedOption);
    }

    private void callController(int option) {
        switch (option) {
            case 1:
                bikeController.add();
                break;
            case 2:
                bikeController.printAll();
                break;
            case 3:
                bikeController.update();
                break;
            case 4:
                bikeController.borrow();
        }
    }
}
