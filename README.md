# API Test Automation

This repository contains API test automation for testing user creation and other related endpoints using RestAssured and Allure for reporting.

## Prerequisites

- Java 11
- Maven
- IntelliJ IDEA (or any other preferred IDE)
- Allure for generating the reports

## Setup

1. **Clone the Repository**:

   ```bash
   git clone https://github.com/your-repo/api-test-automation.git
   cd api-test-automation

## Install Dependencies:

2. Run the following Maven command to install the required dependencies:

    ```bash
    mvn clean install
   
3. Ensure Allure is Installed:

Allure is used for generating test reports. If you don't have it installed, follow the instructions from Allure's official website to install it.

4. Test Data

Test data is stored in JSON files located in the src/test/resources/testdata folder.

Valid Payload (ValidAdminUser.json): Contains a valid admin user to be created.
Invalid Payload (InvalidAdminUser.json): Contains invalid data (e.g., age less than 16) to test error handling.

5. **Utility Classes**
HttpClientUtil.java
The HttpClientUtil class provides utility methods for making HTTP requests (POST, PUT, GET, DELETE) and logging the details to Allure.

Example:
```java
Response response = HttpClientUtil.post("/player/create/supervisor", payload);
```

TestDataUtil.java
The TestDataUtil class provides methods for loading test data from JSON files.

Example:
```java
String payload = TestDataUtil.getPayloadFromJsonFile("ValidAdminUser.json");
```

6. **Running Tests**
To run the tests, use the following Maven command:

```bash
mvn clean test
```
This will execute the tests and generate Allure reports.

7. **Allure Reports**
After running the tests, Allure reports are generated in the target/allure-results directory. To view the reports:

Generate the report:

```bash
allure serve target/allure-results
```
The Allure server will start, and you can view the results in your browser.