package tests;

import base.BaseTest;
import data.UserTestData;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import utils.HttpClientUtil;
import utils.ThreadUtil;

public class UpdatePlayerTests extends BaseTest {

    private static final Logger logger = LogManager.getLogger(UpdatePlayerTests.class);
    private Long createdUserId;

    @Test
    public void updateUserScreenNameBySupervisor() {
        logger.info("Starting testUpdateUserScreenName");
        // Check if tests are running in parallel
        ThreadUtil.checkIfRunningInParallel();

        // Create a new user
        createdUserId = createNewUser();

        String payload = String.format("{\"screenName\":  \"%s\"}",  UserTestData.getUpdatedScreenName());
        Response response = HttpClientUtil.patch("/player/update/"
                + UserTestData.getEditorSupervisor() + "/"
                + createdUserId, payload);
        Assert.assertEquals(response.statusCode(), 200);

        String endpointGet = "/player/get";
        // Construct payload for the POST request
        String payloadGet = String.format("{\"playerId\": %d}", createdUserId);
        // Send the POST request with the payload
        Response responseGet = HttpClientUtil.post(endpointGet, payloadGet);
        Assert.assertEquals(responseGet.statusCode(), 200);
        Assert.assertEquals(responseGet.jsonPath().getString("screenName"), UserTestData.getUpdatedScreenName(), "Screen name does not updated!!!");

    }


    @Test
    public void updateUserScreenNameByAdmin() {
        logger.info("Starting testUpdateUserScreenName");
        // Check if tests are running in parallel
        ThreadUtil.checkIfRunningInParallel();

        // Create a new user
        createdUserId = createNewUser();

        String payload = String.format("{\"screenName\":  \"%s\"}",  UserTestData.getUpdatedScreenName());
        Response response = HttpClientUtil.patch("/player/update/"
                + UserTestData.getEditorAdmin() + "/"
                + createdUserId, payload);
        Assert.assertEquals(response.statusCode(), 403, "Admin can delete the Admin user");
    }

    @Test
    public void updateUserScreenNameByUser() {
        logger.info("Starting testUpdateUserScreenName");
        // Check if tests are running in parallel
        ThreadUtil.checkIfRunningInParallel();

        // Create a new user
        createdUserId = createNewUser();

        String payload = String.format("{\"screenName\":  \"%s\"}",  UserTestData.getUpdatedScreenName());
        Response response = HttpClientUtil.patch("/player/update/"
                + UserTestData.getEditorUser() + "/"
                + createdUserId, payload);
        Assert.assertEquals(response.statusCode(), 403, "User can delete the Admin user");
    }


    // Helper method to create a new user
    private Long createNewUser() {
        String endpoint = String.format("/player/create/%s?age=%s&gender=%s&login=%s&password=%s&role=%s&screenName=%s",
                UserTestData.getEditorSupervisor(),
                UserTestData.getAge(),
                UserTestData.getGender(),
                UserTestData.getLogin(),
                UserTestData.getPassword(),
                UserTestData.getRole(),
                UserTestData.getScreenName()
        );
        Response response = HttpClientUtil.get(endpoint);
        Assert.assertEquals(response.statusCode(), 200, "The User is not created!!!");
        return response.jsonPath().getLong("id");
    }

    // Helper method to delete a user
    private Response deleteUser(Long userId, String editor) {
        String endpoint = "/player/delete/" + editor;
        String payload = String.format("{\"playerId\": %d}", userId);
        return HttpClientUtil.delete(endpoint, payload);
    }

    @AfterTest
    public void cleanup() {
        if (createdUserId != null) {
            logger.info("Deleting the created user with ID: " + createdUserId);
            Response response = deleteUser(createdUserId, "supervisor"); // Deleting the user after test completion
            Assert.assertEquals(response.statusCode(), 204, "User deletion failed");
        } else {
            logger.warn("No user ID found for deletion.");
        }
    }
}
