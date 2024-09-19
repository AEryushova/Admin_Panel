package ru.adminlk.clinica.samsmu.utils.APIUtils;


import ru.adminlk.clinica.samsmu.test.BaseTest;
import com.google.gson.JsonObject;
import io.restassured.http.ContentType;


import static ru.adminlk.clinica.samsmu.data.AppData.*;
import static io.restassured.RestAssured.given;

public class PreparationDataAdminTest {


    public static void createAdmin(String login, String password) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("login", login);
        jsonObject.addProperty("password", password);
        given()
                .baseUri(URL_BACK)
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
                .baseUri(URL_BACK)
                .queryParam("login", login)
                .header("Authorization", "Bearer " + BaseTest.token)
                .header("Environment",ENVIRONMENT)
                .when()
                .delete("/api/admins")
                .then()
                .statusCode(204);
    }
}
