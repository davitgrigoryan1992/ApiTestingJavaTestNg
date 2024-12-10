package data;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

public class UserTestData {
    private static final Random random = new Random();
    private static final Map<String, String> params;

    static {
        try {
            params = TestDataUtil.getParametersFromJsonFile("UserData.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String editorSupervisor = params.get("editorSupervisor");
    private static final String editorAdmin = params.get("editorAdmin");
    private static final String age = params.get("age");
    private static final String gender = params.get("gender");
    private static final String password = params.get("password");
    private static final String role = params.get("role");
    private static final String editorUser = params.get("editorUser");
    private static final String invalidId = params.get("invalidId");
    private static final String updatedScreenName = params.get("updatedScreenName");


    public static String getLogin() {
        return "user" + random.nextInt(1000);
    }

    public static String getScreenName() {
        return "screenName" + random.nextInt(1000);
    }

    public static String getEditorSupervisor() {
        return editorSupervisor;
    }

    public static String getEditorAdmin() {
        return editorAdmin;
    }

    public static String getAge() {
        return age;
    }

    public static String getGender() {
        return gender;
    }

    public static String getPassword() {
        return password;
    }

    public static String getRole() {
        return role;
    }

    public static String getEditorUser() {
        return editorUser;
    }

    public static String getInvalidId() {
        return invalidId;
    }

    public static String getUpdatedScreenName() {
        return updatedScreenName;
    }
}
