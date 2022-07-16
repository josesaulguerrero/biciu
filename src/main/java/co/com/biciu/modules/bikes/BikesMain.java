package co.com.biciu.modules.bikes;

import co.com.biciu.modules.bikes.UI.controllers.BikeController;
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
                "3. Update a bike information."
        );
        int selectedOption = UIUtils.readWithValidatorAndParser(
                value -> NumberUtils.isParsable(value) && Range.between(1, 3).contains(Integer.parseInt(value)),
                Integer::parseInt
        );
        callController(selectedOption);
    }

    private void callController(int option) {

    }
}
