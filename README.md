
# Task Assignment Framework

## Description

A framework for task assignment testing and validation, built with modern tools and frameworks to ensure reliability and efficiency. This project leverages automated testing and detailed reporting to streamline the task assignment process.

## Features

- Automated testing with REST Assured and TestNG.
- Detailed reporting using Allure.
- Clean and maintainable code with Lombok.
- Logging using Log4j.

## Prerequisites

Ensure you have the following installed on your system:

- **Java 11**
- **Maven**

## Tools and Libraries Used

- **Maven**: Build and dependency management.
- **Java 11**: Language support.
- **REST Assured**: API testing.
- **TestNG**: Testing framework.
- **Allure**: Reporting tool.
- **Log4j**: Logging utility.
- **Lombok**: Simplifies code by reducing boilerplate.

## How to Use

### 1. Clone the Repository
```bash
git clone <repository-url>
cd <repository-directory>
```

### 2. Run Tests
Execute the following command to run all tests:
```bash
mvn clean test
```

### 3. Generate Allure Report
After running tests, generate the Allure report:
```bash
mvn allure:report
```

### 4. Serve Allure Report
View the report in your default browser:
```bash
mvn allure:serve
```