package tests;

import base.BaseTest;
import data.TestDataUtil;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.HttpClientUtil;
import utils.ThreadUtil;

import java.io.IOException;

public class UpdateUserTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(CreateUserTest.class);

    @Test
    public void updateUserScreenName() throws IOException {
        logger.info("Starting testUpdateUserScreenName");
        // Check if tests are running in parallel
        ThreadUtil.checkIfRunningInParallel();

        String payload = TestDataUtil.getPayloadFromJsonFile("UpdateUserScreenName.json");
        Response response = HttpClientUtil.put("/player/update/supervisor/2", payload);
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test
    public void updateWithInvalidEditor() throws IOException {
        logger.info("Starting testUpdateWithInvalidEditor");
        // Check if tests are running in parallel
        ThreadUtil.checkIfRunningInParallel();

        String payload = TestDataUtil.getPayloadFromJsonFile("UpdateWithInvalidEditor.json");
        Response response = HttpClientUtil.put("/player/update/user/2", payload);
        Assert.assertEquals(response.statusCode(), 403); // Unauthorized role for update
    }

    @Test
    public void updateUserWithInvalidAge() throws IOException {
        logger.info("Starting testUpdateUserWithInvalidAge");
        // Check if tests are running in parallel
        ThreadUtil.checkIfRunningInParallel();

        String payload = TestDataUtil.getPayloadFromJsonFile("InvalidAgeUpdateUser.json");
        Response response = HttpClientUtil.put("/player/update/supervisor/2", payload);
        Assert.assertEquals(response.statusCode(), 400); // Invalid age
    }

    @Test
    public void updateUserWithDuplicateScreenName() throws IOException {
        logger.info("Starting testUpdateUserWithDuplicateScreenName");
        // Check if tests are running in parallel
        ThreadUtil.checkIfRunningInParallel();

        String payload = TestDataUtil.getPayloadFromJsonFile("DuplicateScreenNameUser.json");
        Response response = HttpClientUtil.put("/player/update/supervisor/2", payload);
        Assert.assertEquals(response.statusCode(), 400); // Duplicate screen name
    }
}
