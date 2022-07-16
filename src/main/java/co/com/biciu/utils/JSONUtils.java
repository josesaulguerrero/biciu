package co.com.biciu.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JSONUtils {

    public static Object readJSONFile(File file, TypeReference<?> type) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(new FileReader(file), type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
