package admin.utils.testUtils;

import admin.data.DataInfo;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TestSetupAPI {
    private static final String baseUri = "http://192.168.6.48:8083";
    private static final Gson gson = new Gson();
    private static String token;

    private static void tokenAuthRequest(String login, String password) {
        String dataInfoJson = getDataInfoJson(login,password);
        Response response = given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .body(dataInfoJson)
                .when()
                .post("/api/admins/sign-in")
                .then()
                .statusCode(200)
                .extract()
                .response();

        token = response.getBody().jsonPath().getString("accessToken");
    }

    public static void authRequest(String login, String password) {
        tokenAuthRequest(login, password);
        given()
                .baseUri(baseUri)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/admins/admin-data")
                .then()
                .statusCode(200);
    }

    private static String getDataInfoJson(String login, String password) {
        DataInfo dataInfo = new DataInfo(login, password);
        return gson.toJson(dataInfo);
    }


    public static void createAdmin(String login, String password) {
        String dataInfoJson = getDataInfoJson(login, password);
        given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(dataInfoJson)
                .when()
                .post("/api/admins/sign-up")
                .then()
                .statusCode(201);
    }

    public static void createAdminCookie(String login, String password) {
        String dataInfoJson = getDataInfoJson(login, password);
        given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + CookieUtils.getToken())
                .body(dataInfoJson)
                .when()
                .post("/api/admins/sign-up")
                .then()
                .statusCode(201);
    }

    public static void deleteAdmin(String login) {
        given()
                .baseUri(baseUri)
                .queryParam("login", login)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/api/admins")
                .then()
                .statusCode(204);
    }

    public static void deleteAdminCookie(String login) {
        given()
                .baseUri(baseUri)
                .queryParam("login", login)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + CookieUtils.getToken())
                .when()
                .delete("/api/admins")
                .then()
                .statusCode(204);
    }
}
