package co.com.biciu.utils;

import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;

public class UIUtils {
    private final static Scanner scanner = new Scanner(System.in);

    public static String read() {
        return scanner.nextLine();
    }

    public static <T> T readWithParser(Function<String, T> parser) {
        return parser.apply(read());
    }

    public static String readWithValidator(Predicate<String> isValid) {
        String input = null;
        while (!isValid.test(input)) {
            input = read();
            if(!isValid.test(input)) {
                System.out.println("The given option isn't valid. Please try again: ");
            }
        }
        return input;
    }

    public static <T> T readWithValidatorAndParser(Predicate<String> isValid, Function<String, T> parser) {
        String input = readWithValidator(isValid);
        return parser.apply(input);
    }

    public static void renderQuestion(String question) {
        System.out.println(question);
    }

    public static void renderOptionsList(String... options) {
        // order matters here.
        Arrays.stream(options).forEachOrdered(System.out::println);
    }
}
