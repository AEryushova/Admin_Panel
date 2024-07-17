package admin.utils.APIUtils;


import admin.test.BaseTest;
import com.google.gson.JsonObject;
import io.restassured.http.ContentType;


import static appData.AppData.*;
import static io.restassured.RestAssured.given;

public class PreparationDataAdminTest {


    public static void createAdmin(String login, String password) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("login", login);
        jsonObject.addProperty("password", password);
        given()
                .baseUri(URI_BACK)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + BaseTest.token)
                .header("Environment", ENVIRONMENT)
                .body(jsonObject.toString())
                .when()
                .post("/api/admins/sign-up")
                .then()
                .statusCode(201);
    }


    public static void deleteAdmin(String login) {
        given()
                .baseUri(URI_BACK)
                .queryParam("login", login)
                .header("Authorization", "Bearer " + BaseTest.token)
                .header("Environment",ENVIRONMENT)
                .when()
                .delete("/api/admins")
                .then()
                .statusCode(204);
    }
}
