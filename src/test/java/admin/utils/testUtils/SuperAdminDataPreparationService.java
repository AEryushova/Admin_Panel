package admin.utils.testUtils;


import com.google.gson.Gson;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class SuperAdminDataPreparationService {
    private static final Gson gson = new Gson();


    private static class DataInfo {
        private String login;
        private String password;

        public DataInfo(String login, String password) {
            this.login = login;
            this.password = password;
        }
    }

    /*
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
    */

    private static String getDataInfoJson(String login, String password) {
        DataInfo dataInfo = new DataInfo(login, password);
        return gson.toJson(dataInfo);
    }


    public static void createAdmin(String login, String password) {
        String dataInfoJson = getDataInfoJson(login, password);
        given()
                .baseUri(admin.data.DataInfo.Urls.getUriAdminPanel())
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + BrowserManager.token)
                .header("Environment", admin.data.DataInfo.Urls.getEnvironmentFreeze())
                .body(dataInfoJson)
                .when()
                .post("/api/admins/sign-up")
                .then()
                .statusCode(201);
    }


    public static void deleteAdmin(String login) {
        given()
                .baseUri(admin.data.DataInfo.Urls.getUriAdminPanel())
                .queryParam("login", login)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + BrowserManager.token)
                .header("Environment", admin.data.DataInfo.Urls.getEnvironmentFreeze())
                .when()
                .delete("/api/admins")
                .then()
                .statusCode(204);
    }
}
