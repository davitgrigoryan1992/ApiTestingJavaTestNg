package base;

import config.ConfigManager;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class BaseTest {
    private static final Logger logger = LogManager.getLogger(BaseTest.class);

    @BeforeClass
    public void setup() throws IOException {
        logger.info("Test started.");
        RestAssured.baseURI = ConfigManager.get("baseUrl");
        System.out.println("Base setup completed.");
    }
}
