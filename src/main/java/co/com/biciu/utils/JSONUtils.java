package co.com.biciu.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JSONUtils {
    public static<K, T extends TypeReference<K>> K readJSONFromFile(File file, T type) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(new FileReader(file), type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean writeJSONToFile(File file, Object data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, data);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}