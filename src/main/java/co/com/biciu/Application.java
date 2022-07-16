package co.com.biciu;

import co.com.biciu.modules.bikes.BikesMain;
import co.com.biciu.utils.UIUtils;
import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.math.NumberUtils;

public class Application {
    public static void main(String[] args) {
        UIUtils.renderOptionsList(
                "1. Bikes.",
                "2. Users.",
                "3. Tickets."
        );
        int selectedOption = UIUtils.readWithValidatorAndParser(
                value -> NumberUtils.isParsable(value) && Range.between(1, 3).contains(Integer.parseInt(value)),
                Integer::parseInt
        );
        callModule(selectedOption);
    }

    private static void callModule(int option) {
        switch (option) {
            case 1:
                BikesMain.getInstance().main();
                break;
            case 2:
                // call users module.
                break;
            case 3:
                // call tickets module.
        }
    }
}
