package data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class TestDataUtil {

    public static String getPayloadFromJsonFile(String fileName) throws IOException {
        String filePath = "src/test/resources/testdata/" + fileName;
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    public static Map<String, String> getParametersFromJsonFile(String fileName) throws IOException {
        String filePath = "src/test/resources/testdata/" + fileName;
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(filePath), new TypeReference<Map<String, String>>() {});
    }

}
