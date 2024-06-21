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
                .baseUri(AppConfig.getURI_ADMIN_PANEL())
                .header("Authorization", "Bearer " + tokenAdmin)
                .header("Environment", AppConfig.getENVIRONMENT())
                .when()
                .get("/api/admins/admin-data")
                .then()
                .statusCode(200);
    }

    private static void tokenGetAuthAdmin(String login, String password) {
        Response response = given()
                .baseUri(AppConfig.getURI_ADMIN_PANEL())
                .header("Environment", AppConfig.getENVIRONMENT())
                .contentType(ContentType.JSON)
                .body(getDataInfoJson(login, password))
                .when()
                .post("/api/admins/sign-in")
                .then()
                .statusCode(200)
                .extract()
                .response();
        tokenAdmin = response.getBody().jsonPath().getString("accessToken");
    }

    public static String getDataInfoJson(String login, String password) {
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
        given()
                .baseUri(AppConfig.getURI_ADMIN_PANEL())
                .header("Authorization", "Bearer " + tokenAdmin)
                .header("Environment", AppConfig.getENVIRONMENT())
                .contentType(ContentType.JSON)
                .body(getChangePasswordJson(login, newPassword))
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

