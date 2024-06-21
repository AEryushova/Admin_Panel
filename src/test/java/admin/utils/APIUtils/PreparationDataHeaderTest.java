package admin.utils.APIUtils;

import admin.data.AppData;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Getter;


import static io.restassured.RestAssured.given;

public class PreparationDataHeaderTest {
    private static final Gson gson = new Gson();
    private static final JsonObject jsonObject = new JsonObject();

    @Getter
    private static String tokenAdmin;


    public static void authAdmin(String login, String password) {
        tokenGetAuthAdmin(login, password);
        given()
                .baseUri(AppData.URI_ADMIN_PANEL)
                .header("Authorization", "Bearer " + tokenAdmin)
                .header("Environment", AppData.ENVIRONMENT)
                .when()
                .get("/api/admins/admin-data")
                .then()
                .statusCode(200);
    }

    private static void tokenGetAuthAdmin(String login, String password) {
        Response response = given()
                .baseUri(AppData.URI_ADMIN_PANEL)
                .header("Environment", AppData.ENVIRONMENT)
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

    private static String getDataInfoJson(String login, String password) {
        jsonObject.addProperty("login", login);
        jsonObject.addProperty("password", password);
        return gson.toJson(jsonObject);
    }


    public static void changePasswordAdmin(String login, String newPassword) {
        given()
                .baseUri(AppData.URI_ADMIN_PANEL)
                .header("Authorization", "Bearer " + tokenAdmin)
                .header("Environment", AppData.ENVIRONMENT)
                .contentType(ContentType.JSON)
                .body(getChangePasswordJson(login, newPassword))
                .when()
                .post("/api/admins/reset-password")
                .then()
                .statusCode(204);
    }

    private static String getChangePasswordJson(String login, String newPassword) {
        jsonObject.addProperty("login", login);
        jsonObject.addProperty("newPassword", newPassword);
        return gson.toJson(jsonObject);
    }
}

