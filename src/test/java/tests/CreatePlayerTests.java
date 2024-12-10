package tests;

import base.BaseTest;
import data.UserTestData;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.HttpClientUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ThreadUtil;

import java.io.IOException;

public class CreatePlayerTests extends BaseTest {

    private static final Logger logger = LogManager.getLogger(CreatePlayerTests.class);
    private Long createdUserId;

    public CreatePlayerTests() throws IOException {
        // Implicitly required due to the exception thrown by the above method.
    }

    @Test
    public void createValidAdminUserBySupervisor() {
        logger.info("Starting createValidAdminUserBySupervisor test");

        // Check if tests are running in parallel
        ThreadUtil.checkIfRunningInParallel();

        // Create the endpoint dynamically
        String endpoint = String.format(
                "/player/create/%s?age=%s&gender=%s&login=%s&password=%s&role=%s&screenName=%s",
                UserTestData.getEditorSupervisor(),
                UserTestData.getAge(),
                UserTestData.getGender(),
                UserTestData.getLogin(),
                UserTestData.getPassword(),
                UserTestData.getRole(),
                UserTestData.getScreenName()
        );

        // Make the GET request and assert the response
        Response response = HttpClientUtil.get(endpoint);
        handleResponse(response, 200, "The User is not created!!!");
    }

    @Test
    public void createValidAdminUserByAdmin() {
        logger.info("Starting createValidAdminUserBySupervisor test");

        // Check if tests are running in parallel
        ThreadUtil.checkIfRunningInParallel();

        // Create the endpoint dynamically
        String endpoint = String.format(
                "/player/create/%s?age=%s&gender=%s&login=%s&password=%s&role=%s&screenName=%s",
                UserTestData.getEditorAdmin(),
                UserTestData.getAge(),
                UserTestData.getGender(),
                UserTestData.getLogin(),
                UserTestData.getPassword(),
                UserTestData.getRole(),
                UserTestData.getScreenName()
        );

        // Make the GET request and assert the response
        Response response = HttpClientUtil.get(endpoint);
        handleResponse(response, 200, "The User is not created!!!");
    }

    @Test
    public void createValidSupervisorUserBySupervisor() {
        logger.info("Starting createValidSupervisorUserBySupervisor test");

        // Check if tests are running in parallel
        ThreadUtil.checkIfRunningInParallel();

        // Create the endpoint dynamically
        String endpoint = String.format(
                "/player/create/%s?age=%s&gender=%s&login=%s&password=%s&role=%s&screenName=%s",
                UserTestData.getEditorSupervisor(),
                UserTestData.getAge(),
                UserTestData.getGender(),
                UserTestData.getLogin(),
                UserTestData.getPassword(),
                UserTestData.getEditorSupervisor(),
                UserTestData.getScreenName()
        );

        // Make the GET request and assert the response
        Response response = HttpClientUtil.get(endpoint);
        handleResponse(response, 400, "The Supervisor is created!!!");
    }

    @Test
    public void createUserWithAgeBelow16() {
        logger.info("Starting createUserWithAgeBelow16 test");

        // Check if tests are running in parallel
        ThreadUtil.checkIfRunningInParallel();

        // Create the endpoint dynamically
        String endpoint = String.format(
                "/player/create/%s?age=%s&gender=%s&login=%s&password=%s&role=%s&screenName=%s",
                UserTestData.getEditorSupervisor(),
                15,
                UserTestData.getGender(),
                UserTestData.getLogin(),
                UserTestData.getPassword(),
                UserTestData.getRole(),
                UserTestData.getScreenName()
        );

        // Make the GET request and assert the response
        Response response = HttpClientUtil.get(endpoint);
        handleResponse(response, 400, "Expecting a 400 for invalid age");
    }

    @Test
    public void createUserWithAgeAbove60() {
        logger.info("Starting createUserWithAgeAbove60 test");

        // Check if tests are running in parallel
        ThreadUtil.checkIfRunningInParallel();

        // Create the endpoint dynamically
        String endpoint = String.format(
                "/player/create/%s?age=%s&gender=%s&login=%s&password=%s&role=%s&screenName=%s",
                UserTestData.getEditorSupervisor(),
                61,
                UserTestData.getGender(),
                UserTestData.getLogin(),
                UserTestData.getPassword(),
                UserTestData.getRole(),
                UserTestData.getScreenName()
        );

        // Make the GET request and assert the response
        Response response = HttpClientUtil.get(endpoint);
        handleResponse(response, 400, " Expecting a 400 for invalid age");
    }

    @Test
    public void createUserWithInvalidRole() {
        logger.info("Starting createUserWithInvalidRole test");

        // Check if tests are running in parallel
        ThreadUtil.checkIfRunningInParallel();

        // Create the endpoint dynamically with an invalid role
        String endpoint = String.format(
                "/player/create/%s?age=%s&gender=%s&login=%s&password=%s&role=%s&screenName=%s",
                UserTestData.getEditorSupervisor(),
                UserTestData.getAge(),
                UserTestData.getGender(),
                UserTestData.getLogin(),
                UserTestData.getPassword(),
                "invalidRole",
                UserTestData.getScreenName()
        );

        // Make the GET request and assert the response
        Response response = HttpClientUtil.get(endpoint);
        handleResponse(response, 400, "Expecting a 400 for invalid role");
    }

    @Test
    public void createUserWithDuplicateLogin() {
        logger.info("Starting createUserWithDuplicateLogin test");

        // Check if tests are running in parallel
        ThreadUtil.checkIfRunningInParallel();

        String endpoint = String.format(
                "/player/create/%s?age=%s&gender=%s&login=%s&password=%s&role=%s&screenName=%s",
                UserTestData.getEditorSupervisor(),
                UserTestData.getAge(),
                UserTestData.getGender(),
                UserTestData.getLogin(),
                UserTestData.getPassword(),
                UserTestData.getRole(),
                UserTestData.getScreenName()
        );
        String endpointNew = String.format(
                "/player/create/%s?age=%s&gender=%s&login=%s&password=%s&role=%s&screenName=%s",
                UserTestData.getEditorSupervisor(),
                UserTestData.getAge(),
                UserTestData.getGender(),
                UserTestData.getLogin(),
                UserTestData.getPassword()+"New",
                UserTestData.getRole(),
                UserTestData.getScreenName()+"New"
        );

        // Make the GET request to create the user
        Response response = HttpClientUtil.get(endpoint);
        createdUserId = response.jsonPath().getLong("id");
        Response responseNew = HttpClientUtil.get(endpointNew);

        // Assert the response
        Assert.assertEquals(responseNew.statusCode(), 400, "Expecting a 400 for duplicate login");
    }


    @Test
    public void createUserWithDuplicateScreenName() {
        logger.info("Starting createUserWithDuplicateScreenName test");

        // Check if tests are running in parallel
        ThreadUtil.checkIfRunningInParallel();

        String endpoint = String.format(
                "/player/create/%s?age=%s&gender=%s&login=%s&password=%s&role=%s&screenName=%s",
                UserTestData.getEditorSupervisor(),
                UserTestData.getAge(),
                UserTestData.getGender(),
                UserTestData.getLogin(),
                UserTestData.getPassword(),
                UserTestData.getRole(),
                UserTestData.getScreenName()
        );
        String endpointNew = String.format(
                "/player/create/%s?age=%s&gender=%s&login=%s&password=%s&role=%s&screenName=%s",
                UserTestData.getEditorSupervisor(),
                UserTestData.getAge(),
                UserTestData.getGender(),
                UserTestData.getLogin()+"New",
                UserTestData.getPassword()+"New",
                UserTestData.getRole(),
                UserTestData.getScreenName()
        );

        // Make the GET request to create the user
        Response response = HttpClientUtil.get(endpoint);
        createdUserId = response.jsonPath().getLong("id");
        Response responseNew = HttpClientUtil.get(endpointNew);

        // Assert the response
        Assert.assertEquals(responseNew.statusCode(), 400, "Expecting a 400 for duplicate screen Name");
    }

    @Test
    public void createUserWithInvalidPassword() {
        logger.info("Starting createUserWithInvalidPassword test");

        // Check if tests are running in parallel
        ThreadUtil.checkIfRunningInParallel();

        // Create the endpoint dynamically with an invalid password
        String endpoint = String.format(
                "/player/create/%s?age=%s&gender=%s&login=%s&password=%s&role=%s&screenName=%s",
                UserTestData.getEditorSupervisor(),
                UserTestData.getAge(),
                UserTestData.getGender(),
                UserTestData.getLogin(),
                "short",
                UserTestData.getRole(),
                UserTestData.getScreenName()
        );

        // Make the GET request and assert the response
        Response response = HttpClientUtil.get(endpoint);
        handleResponse(response, 400, "Expecting a 400 for invalid password");
    }

    // Helper method to handle responses and reduce repetition
    private void handleResponse(Response response, int expectedStatusCode, String message) {
        try {
            Assert.assertEquals(response.statusCode(), expectedStatusCode, message);
            SoftAssert softAssert = new SoftAssert();
            if (response.statusCode() == 200) {
                softAssert.assertNotNull(response.jsonPath().getLong("id"), "ID should not be null");
                createdUserId = response.jsonPath().getLong("id");
                softAssert.assertEquals(response.jsonPath().getString("login"), UserTestData.getLogin(), "Login does not match");
                softAssert.assertEquals(response.jsonPath().getString("password"), UserTestData.getPassword(), "Password does not match");
                softAssert.assertEquals(response.jsonPath().getString("screenName"), UserTestData.getScreenName(), "Screen does not match");
                softAssert.assertEquals(response.jsonPath().getString("gender"), UserTestData.getGender(), "Gender does not match");
                softAssert.assertEquals(response.jsonPath().getString("age"), UserTestData.getAge(), "Age does not match");
                softAssert.assertEquals(response.jsonPath().getString("role"), UserTestData.getRole(), "Role does not match");
            }
            softAssert.assertAll();
        } catch (Exception e) {
            logger.error("Error while validating response: ", e);
            Assert.fail("Test failed due to unexpected error");
        }
    }

    @AfterTest
    public void cleanup() {
        if (createdUserId != null) {
            logger.info("Deleting the created user with ID: " + createdUserId);
            String endpoint = "/player/delete/supervisor";
            // Construct payload for the DELETE request
            String payload = String.format("{\"playerId\": %d}", createdUserId);
            // Send the DELETE request with the payload
            Response response = HttpClientUtil.delete(endpoint, payload);
            Assert.assertEquals(response.statusCode(), 204, "User deletion failed");
        } else {
            logger.warn("No user ID found for deletion.");
        }
    }
}
