package utils.APIUtils;


import com.google.gson.JsonObject;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Getter;

import static appData.AppData.*;
import static io.restassured.RestAssured.given;

public class PreparationDataHeaderTest {

    @Getter
    private static String tokenAdmin;


    public static void authAdmin(String login, String password) {
        tokenGetAuthAdmin(login, password);
        given()
                .baseUri(URI_BACK)
                .header("Authorization", "Bearer " + tokenAdmin)
                .header("Environment",ENVIRONMENT)
                .when()
                .get("/api/admins/admin-data")
                .then()
                .statusCode(200);
    }

    private static void tokenGetAuthAdmin(String login, String password) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("login", login);
        jsonObject.addProperty("password", password);
        Response response = given()
                .baseUri(URI_BACK)
                .header("Environment", ENVIRONMENT)
                .contentType(ContentType.JSON)
                .body(jsonObject.toString())
                .when()
                .post("/api/admins/sign-in")
                .then()
                .statusCode(200)
                .extract()
                .response();
        tokenAdmin = response.getBody().jsonPath().getString("accessToken");
    }


    public static void changePasswordAdmin(String login, String newPassword) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("login", login);
        jsonObject.addProperty("newPassword", newPassword);
        given()
                .baseUri(URI_BACK)
                .header("Authorization", "Bearer " + tokenAdmin)
                .header("Environment", ENVIRONMENT)
                .contentType(ContentType.JSON)
                .body(jsonObject.toString())
                .when()
                .post("/api/admins/reset-password")
                .then()
                .statusCode(204);
    }

}

