package admin.utils.APIUtils;


import admin.utils.testUtils.BrowserManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.restassured.http.ContentType;


import static appData.AppData.*;
import static io.restassured.RestAssured.given;

public class PreparationDataAdminTest {

    private static final Gson gson = new Gson();
    private static final JsonObject jsonObject = new JsonObject();


    public static void createAdmin(String login, String password) {
        given()
                .baseUri(URI_ADMIN_PANEL)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + BrowserManager.token)
                .header("Environment", ENVIRONMENT)
                .body(getDataInfoJson(login, password))
                .when()
                .post("/api/admins/sign-up")
                .then()
                .statusCode(201);
    }


    public static void deleteAdmin(String login) {
        given()
                .baseUri(URI_ADMIN_PANEL)
                .queryParam("login", login)
                .header("Authorization", "Bearer " + BrowserManager.token)
                .header("Environment",ENVIRONMENT)
                .when()
                .delete("/api/admins")
                .then()
                .statusCode(204);
    }

    private static String getDataInfoJson(String login, String password) {
        jsonObject.addProperty("login", login);
        jsonObject.addProperty("password", password);
        return gson.toJson(jsonObject);
    }

}
