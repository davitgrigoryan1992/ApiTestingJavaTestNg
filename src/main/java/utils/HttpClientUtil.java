package utils;

import config.ConfigManager;
import io.qameta.allure.Allure;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class HttpClientUtil {
    public static Response post(String endpoint, String payload) {
        String url = ConfigManager.get("baseUrl") + endpoint;
        logRequestDetails("POST", url, payload);
        Response response = given()
                .log().all()
                .header("Content-Type", "application/json")
                .accept("application/json")
                .body(payload)
                .post(url)
                .then()
                .log().all()
                .extract().response();
        logResponseDetails(response);
        return response;
    }

    public static Response patch(String endpoint, String payload) {
        String url = ConfigManager.get("baseUrl") + endpoint;
        logRequestDetails("PATCH", url, payload);
        Response response = given()
                .log().all()
                .header("Content-Type", "application/json")
                .accept("application/json")
                .body(payload)
                .patch(url)
                .then()
                .log().all()
                .extract().response();
        logResponseDetails(response);
        return response;
    }

    public static Response delete(String endpoint, String payload) {
        String url = ConfigManager.get("baseUrl") + endpoint;
        logRequestDetails("DELETE", url, null);
        Response response = given()
                .log().all()
                .header("Content-Type", "application/json")
                .accept("application/json")
                .body(payload)
                .delete(url)
                .then()
                .log().all()
                .extract().response();
        logResponseDetails(response);
        return response;
    }

    public static Response get(String endpoint) {
        String url = ConfigManager.get("baseUrl") + endpoint;
        logRequestDetails("GET", url, null);
        Response response = given()
                .log().all()
                .header("Content-Type", "application/json")
                .accept("application/json")
                .get(url)
                .then()
                .log().all()
                .extract().response();
        logResponseDetails(response);
        return response;
    }

    private static void logRequestDetails(String method, String url, String payload) {
        Allure.step("Request Details",
                () -> {
                    Allure.addAttachment("HTTP Method", method);
                    Allure.addAttachment("Endpoint", url);
                    if (payload != null) {
                        Allure.addAttachment("Payload", "application/json", payload, "json");
                    }
                });
    }

    private static void logResponseDetails(Response response) {
        Allure.step("Response Details",
                () -> {
                    Allure.addAttachment("Response Code", String.valueOf(response.getStatusCode()));
                    Allure.addAttachment("Response Body", "application/json", response.asString(), "json");
                });
    }
}
