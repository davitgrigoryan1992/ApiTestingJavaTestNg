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

public class DeleteUserTest extends BaseTest {
        private static final Logger logger = LogManager.getLogger(CreateUserTest.class);

        @Test
        public void deleteValidUser() throws IOException {
                logger.info("Starting testDeleteValidUser");
                // Check if tests are running in parallel
                ThreadUtil.checkIfRunningInParallel();

                Response response = HttpClientUtil.delete("/player/delete/supervisor?playerId=2");
                Assert.assertEquals(response.statusCode(), 200);
        }

        @Test
        public void deleteSupervisor() throws IOException {
                logger.info("Starting testDeleteSupervisor");
                // Check if tests are running in parallel
                ThreadUtil.checkIfRunningInParallel();

                Response response = HttpClientUtil.delete("/player/delete/supervisor?playerId=1");
                Assert.assertEquals(response.statusCode(), 400); // Supervisor cannot be deleted
        }

        @Test
        public void deleteUserWithInvalidId() throws IOException {
                logger.info("Starting testDeleteUserWithInvalidId");
                // Check if tests are running in parallel
                ThreadUtil.checkIfRunningInParallel();

                Response response = HttpClientUtil.delete("/player/delete/supervisor?playerId=99999");
                Assert.assertEquals(response.statusCode(), 404); // Assuming 404 for invalid playerId
        }

        @Test
        public void deleteUserWithUnauthorizedRole() throws IOException {
                logger.info("Starting testDeleteUserWithUnauthorizedRole");
                // Check if tests are running in parallel
                ThreadUtil.checkIfRunningInParallel();

                Response response = HttpClientUtil.delete("/player/delete/user?playerId=2");
                Assert.assertEquals(response.statusCode(), 403); // Unauthorized role
        }
}
