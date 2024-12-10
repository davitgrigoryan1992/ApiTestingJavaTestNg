package tests;

import base.BaseTest;
import data.TestDataUtil;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.HttpClientUtil;
import utils.ThreadUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;


public class CreateUserTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(CreateUserTest.class);

    @Test
    public void createValidAdminUser() throws IOException {
        logger.info("Starting testCreateUserWithValidData");
        // Check if tests are running in parallel
        ThreadUtil.checkIfRunningInParallel();

        String payload = TestDataUtil.getPayloadFromJsonFile("ValidAdminUser.json");
        Response response = HttpClientUtil.post("/player/create/supervisor", payload);
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test
    public void createUserWithInvalidAge() throws IOException {
        logger.info("Starting testCreateUserWithInvalidAge");
        // Check if tests are running in parallel
        ThreadUtil.checkIfRunningInParallel();

        String payload = TestDataUtil.getPayloadFromJsonFile("InvalidAgeUser.json");
        Response response = HttpClientUtil.post("/player/create/supervisor", payload);
        Assert.assertEquals(response.statusCode(), 400); // Assuming 400 for invalid data
    }

    @Test
    public void createUserWithInvalidRole() throws IOException {
        logger.info("Starting testCreateUserWithInvalidRole");
        // Check if tests are running in parallel
        ThreadUtil.checkIfRunningInParallel();

        String payload = TestDataUtil.getPayloadFromJsonFile("InvalidRoleUser.json");
        Response response = HttpClientUtil.post("/player/create/supervisor", payload);
        Assert.assertEquals(response.statusCode(), 400); // Assuming 400 for invalid role
    }

    @Test
    public void createUserWithDuplicateLogin() throws IOException {
        logger.info("Starting testCreateUserWithDuplicateLogin");
        // Check if tests are running in parallel
        ThreadUtil.checkIfRunningInParallel();

        String payload = TestDataUtil.getPayloadFromJsonFile("DuplicateLoginUser.json");
        Response response = HttpClientUtil.post("/player/create/supervisor", payload);
        Assert.assertEquals(response.statusCode(), 400); // Assuming 400 for duplicate login
    }

    @Test
    public void createUserWithInvalidPassword() throws IOException {
        logger.info("Starting testCreateUserWithInvalidPassword");
        // Check if tests are running in parallel
        ThreadUtil.checkIfRunningInParallel();

        String payload = TestDataUtil.getPayloadFromJsonFile("InvalidPasswordUser.json");
        Response response = HttpClientUtil.post("/player/create/supervisor", payload);
        Assert.assertEquals(response.statusCode(), 400); // Assuming 400 for invalid password
    }
}
