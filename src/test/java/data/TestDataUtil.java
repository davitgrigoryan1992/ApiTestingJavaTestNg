package data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestDataUtil {

    public static String getPayloadFromJsonFile(String fileName) throws IOException {
        String filePath = "src/test/resources/testdata/" + fileName;
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }
}
