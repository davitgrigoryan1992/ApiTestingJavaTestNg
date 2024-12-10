package tests;

import base.BaseTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.UserTestData;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.HttpClientUtil;
import utils.ThreadUtil;

public class GetPlayerTests extends BaseTest {
    private static final Logger logger = LogManager.getLogger(CreatePlayerTests.class);

    @Test
    public void getValidUser() {
        logger.info("Starting testGetValidUser");
        // Check if tests are running in parallel
        ThreadUtil.checkIfRunningInParallel();

        String endpoint = "/player/get";
        // Construct payload for the POST request
        String payload = String.format("{\"playerId\": %d}", 1);
        // Send the POST request with the payload
        Response response = HttpClientUtil.post(endpoint, payload);
        Assert.assertEquals(response.statusCode(), 200);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(response.jsonPath().getLong("id"), "ID should not be null");
        softAssert.assertEquals(response.jsonPath().getString("login"), UserTestData.getEditorSupervisor(), "Login does not match");
        softAssert.assertEquals(response.jsonPath().getString("password"), "testSupervisor", "Password does not match");
        softAssert.assertEquals(response.jsonPath().getString("screenName"), "testSupervisor", "Screen does not match");
        softAssert.assertEquals(response.jsonPath().getString("gender"), UserTestData.getGender(), "Gender does not match");
        softAssert.assertEquals(response.jsonPath().getString("age"), "28", "Age does not match");
        softAssert.assertEquals(response.jsonPath().getString("role"), UserTestData.getEditorSupervisor(), "Role does not match");
        softAssert.assertAll();
    }

    @Test
    public void getUserWithInvalidId() {
        logger.info("Starting testGetUserWithInvalidId");
        // Check if tests are running in parallel
        ThreadUtil.checkIfRunningInParallel();

        String endpoint = "/player/get";
        long invalidIdLong = Long.parseLong(UserTestData.getInvalidId());
        // Construct payload for the POST request
        String payload = String.format("{\"playerId\": %d}", invalidIdLong);
        // Send the POST request with the payload
        Response response = HttpClientUtil.post(endpoint, payload);
        Assert.assertEquals(response.statusCode(), 404, "Invalid Player is exist!!!");
    }


    @Test
    public void getAllPlayerList() throws JsonProcessingException {
        logger.info("Starting testGetAllPlayerList");
        // Check if tests are running in parallel
        ThreadUtil.checkIfRunningInParallel();

        String endpoint = "/player/get/all";
        // Construct payload for the POST request
        Response response = HttpClientUtil.get(endpoint);
        Assert.assertEquals(response.statusCode(), 200, "Response Status is not 200!!!");

        // Parse the JSON response
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.getBody().asString());

        // Check if "players" key exists and is an array
        Assert.assertTrue(rootNode.has("players"), "Key 'players' is missing in the response!");
        Assert.assertTrue(rootNode.get("players").isArray(), "'players' is not an array!");

        // Validate that the array contains at least one object
        JsonNode playersArray = rootNode.get("players");
        Assert.assertTrue(playersArray.size() > 0, "'players' array is empty!");
        Assert.assertTrue(playersArray.get(0).isObject(), "First element in 'players' array is not an object!");
    }
}
