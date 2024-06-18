package admin.utils.APIUtils;

import admin.data.AppConfig;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import static io.restassured.RestAssured.given;

public class PreparationDataHeaderTest {
    private static final Gson gson = new Gson();

    @Getter
    private static String tokenAdmin;


    @Value
    @AllArgsConstructor
    private static class DataInfo {
        String login;
        String password;
    }

    public static void authAdmin(String login, String password) {
        tokenGetAuthAdmin(login, password);
        given()
                .baseUri(AppConfig.getUriAdminPanel())
                .header("Authorization", "Bearer " + tokenAdmin)
                .header("Environment", AppConfig.getEnvironment())
                .when()
                .get("/api/admins/admin-data")
                .then()
                .statusCode(200);
    }

    private static void tokenGetAuthAdmin(String login, String password) {
        String dataInfoJson = getDataInfoJson(login, password);
        Response response = given()
                .baseUri(AppConfig.getUriAdminPanel())
                .header("Environment", AppConfig.getEnvironment())
                .contentType(ContentType.JSON)
                .body(dataInfoJson)
                .when()
                .post("/api/admins/sign-in")
                .then()
                .statusCode(200)
                .extract()
                .response();
        tokenAdmin = response.getBody().jsonPath().getString("accessToken");
    }

    private static String getDataInfoJson(String login, String password) {
        DataInfo dataInfo = new DataInfo(login, password);
        return gson.toJson(dataInfo);
    }


    @Value
    @AllArgsConstructor
    private static class DataChangePassword {
        String login;
        String newPassword;
    }

    public static void changePasswordAdmin(String login, String newPassword) {
        String changePassword = getChangePasswordJson(login, newPassword);
        given()
                .baseUri(AppConfig.getUriAdminPanel())
                .header("Authorization", "Bearer " + tokenAdmin)
                .header("Environment", AppConfig.getEnvironment())
                .contentType(ContentType.JSON)
                .body(changePassword)
                .when()
                .post("/api/admins/reset-password")
                .then()
                .statusCode(204);
    }

    private static String getChangePasswordJson(String login, String newPassword) {
        DataChangePassword dataChangePassword = new DataChangePassword(login, newPassword);
        return gson.toJson(dataChangePassword);
    }
}

