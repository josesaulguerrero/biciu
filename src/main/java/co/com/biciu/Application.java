package co.com.biciu;

import co.com.biciu.app.UI.BikesMain;
import co.com.biciu.app.UI.TicketsMain;
import co.com.biciu.app.UI.UsersMain;
import co.com.biciu.app.domain.serializers.TicketSerializer;
import co.com.biciu.app.domain.serializers.UserSerializer;
import co.com.biciu.app.persistence.entities.*;
import co.com.biciu.interfaces.Serializer;
import co.com.biciu.utils.UIUtils;
import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.math.NumberUtils;

import java.time.LocalDateTime;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        Integer selectedOption = null;
        while (selectedOption == null || selectedOption != 0) {
            UIUtils.renderOptionsList(
                    "Which module do you want to visit? (Enter the number)",
                    "1. Bikes.",
                    "2. Users.",
                    "3. Tickets.",
                    "0. Exit."
            );
            selectedOption = UIUtils.readWithValidatorAndParser(
                    value -> NumberUtils.isParsable(value) && Range.between(0, 3).contains(Integer.parseInt(value)),
                    value -> Integer.parseInt(value.trim())
            );
            callModule(selectedOption);
            UIUtils.printLineSeparator();
        }
    }

    private static void callModule(int option) {
        switch (option) {
            case 1:
                BikesMain.getInstance().main();
                break;
            case 2:
                UsersMain.getInstance().main();
                break;
            case 3:
                TicketsMain.getInstance().main();
        }
    }
}
