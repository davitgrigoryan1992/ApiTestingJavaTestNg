package tests;

import base.BaseTest;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.HttpClientUtil;
import utils.ThreadUtil;

import java.io.IOException;

public class GetUserTest extends BaseTest {
    private static final Logger logger = LogManager.getLogger(CreateUserTest.class);

    @Test
    public void getValidUser() throws IOException {
        logger.info("Starting testGetValidUser");
        // Check if tests are running in parallel
        ThreadUtil.checkIfRunningInParallel();

        Response response = HttpClientUtil.get("/player/get?playerId=2");
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test
    public void getUserWithInvalidId() throws IOException {
        logger.info("Starting testGetUserWithInvalidId");
        // Check if tests are running in parallel
        ThreadUtil.checkIfRunningInParallel();

        Response response = HttpClientUtil.get("/player/get?playerId=99999");
        Assert.assertEquals(response.statusCode(), 404); // Assuming 404 for invalid playerId
    }

    @Test
    public void getUserWithUnauthorizedRole() throws IOException {
        logger.info("Starting testGetUserWithUnauthorizedRole");
        // Check if tests are running in parallel
        ThreadUtil.checkIfRunningInParallel();

        Response response = HttpClientUtil.get("/player/get?playerId=2");
        Assert.assertEquals(response.statusCode(), 403); // Unauthorized role access
    }
}
