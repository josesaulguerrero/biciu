package co.com.biciu.utils;

import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;

public class UIUtils {
    private final static Scanner scanner = new Scanner(System.in);

    public static String read() {
        return new Scanner(System.in).nextLine();
    }

    public static<T> T readWithParser(T targetClass, Function<String, T> parser) {
        return null;
    }

    public static String readWithValidator(Predicate<String> validator) {
        return null;
    }

    public static<T> T readWithValidatorAndParser(T targetClass, Predicate<String> validator, Function<String, T> parser) {
        return null;
    }

    public static void renderQuestion(String question) {
        System.out.println(question);
    }

    public static void renderOptionsList(String... options) {
        // order matters here.
        Arrays.stream(options).forEachOrdered(System.out::println);
    }
}
