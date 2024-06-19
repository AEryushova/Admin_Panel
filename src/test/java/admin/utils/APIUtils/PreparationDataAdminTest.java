package admin.utils.APIUtils;

import admin.data.AppConfig;
import admin.utils.testUtils.BrowserManager;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import lombok.AllArgsConstructor;
import lombok.Value;

import static io.restassured.RestAssured.given;

public class PreparationDataAdminTest {
    private static final Gson gson = new Gson();


    @Value
    @AllArgsConstructor
    private static class DataInfo {
        String login;
        String password;

    }

    public static void createAdmin(String login, String password) {
        String dataInfoJson = getDataInfoJson(login, password);
        given()
                .baseUri(AppConfig.getURI_ADMIN_PANEL())
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + BrowserManager.token)
                .header("Environment", AppConfig.getENVIRONMENT())
                .body(dataInfoJson)
                .when()
                .post("/api/admins/sign-up")
                .then()
                .statusCode(201);
    }


    public static void deleteAdmin(String login) {
        given()
                .baseUri(AppConfig.getURI_ADMIN_PANEL())
                .queryParam("login", login)
                .header("Authorization", "Bearer " + BrowserManager.token)
                .header("Environment", AppConfig.getENVIRONMENT())
                .when()
                .delete("/api/admins")
                .then()
                .statusCode(204);
    }

    private static String getDataInfoJson(String login, String password) {
        DataInfo dataInfo = new DataInfo(login, password);
        return gson.toJson(dataInfo);
    }

}
