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

public class DeletePlayerTests extends BaseTest {

        private static final Logger logger = LogManager.getLogger(DeletePlayerTests.class);
        private Long createdUserId;

        @Test
        public void deleteValidAdminUserBySupervisor() {
                logger.info("Starting testDeleteValidUserBySupervisor");

                // Check if tests are running in parallel
                ThreadUtil.checkIfRunningInParallel();

                // Create a new user
                createdUserId = createNewUser();

                // Delete the created user
                Response responseDeleteRequest = deleteUser(createdUserId, UserTestData.getEditorSupervisor());
                Assert.assertEquals(responseDeleteRequest.statusCode(), 204, "User deletion failed");
        }

        @Test
        public void deleteValidAdminUserByAdmin() {
                logger.info("Starting testDeleteValidUserByAdmin");

                // Check if tests are running in parallel
                ThreadUtil.checkIfRunningInParallel();

                // Create a new user
                createdUserId = createNewUser();

                // Delete the created user
                Response responseDeleteRequest = deleteUser(createdUserId, UserTestData.getEditorAdmin());
                Assert.assertEquals(responseDeleteRequest.statusCode(), 204, "User deletion failed");
        }

        @Test
        public void deleteSupervisor() {
                logger.info("Starting testDeleteSupervisor");

                // Check if tests are running in parallel
                ThreadUtil.checkIfRunningInParallel();

                // Attempt to delete the supervisor
                Response responseDeleteRequest = deleteUser(1L, UserTestData.getEditorSupervisor()); // Assuming 1 is the supervisor ID
                Assert.assertEquals(responseDeleteRequest.statusCode(), 403, "Supervisor cannot be deleted!!!");
        }

        @Test
        public void deleteUserWithInvalidId() {
                logger.info("Starting testDeleteUserWithInvalidId");

                // Check if tests are running in parallel
                ThreadUtil.checkIfRunningInParallel();

                // Convert invalid ID to long
                long invalidIdLong = Long.parseLong(UserTestData.getInvalidId());

                // Attempt to delete the user with invalid ID
                Response responseDeleteRequest = deleteUser(invalidIdLong, UserTestData.getEditorSupervisor());
                Assert.assertEquals(responseDeleteRequest.statusCode(), 404, "User with invalid ID should return 404");
        }

        @Test
        public void deleteUserWithUnauthorizedRole() {
                logger.info("Starting testDeleteUserWithUnauthorizedRole");

                // Check if tests are running in parallel
                ThreadUtil.checkIfRunningInParallel();

                // Create a new user
                createdUserId = createNewUser();

                // Attempt to delete the created user with an unauthorized role
                Response responseDeleteRequest = deleteUser(createdUserId, UserTestData.getEditorUser());
                Assert.assertEquals(responseDeleteRequest.statusCode(), 403, "User with unauthorized role can delete other user!!!");
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
