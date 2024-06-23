package admin.utils.APIUtils;


import admin.utils.testUtils.BrowserManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Getter;
import static appData.AppData.*;

import java.io.File;

import static io.restassured.RestAssured.given;

public class PreparationDataSettingTest {

    private static final Gson gson = new Gson();
    private static final JsonObject jsonObject = new JsonObject();
    private static String tokenPatient;

    @Getter
    public static String location;


    public static void authPatient() {
        tokenGetAuthPatient();
        given()
                .baseUri(URI_PERSONAL_AREA)
                .header("Authorization", "Bearer " + tokenPatient)
                .header("Environment", ENVIRONMENT)
                .when()
                .get("/api/clients/user-info")
                .then()
                .statusCode(200);
    }


    private static void tokenGetAuthPatient() {
        Response response = given()
                .baseUri(URI_PERSONAL_AREA)
                .header("Environment", ENVIRONMENT)
                .queryParam("code", "123code")
                .when()
                .get("/api/clients/sign-in")
                .then()
                .statusCode(200)
                .extract()
                .response();

        tokenPatient = response.getBody().jsonPath().getString("accessToken");
    }

    public static void addBugReportPatient(String message, String email, String author) {
        given()
                .baseUri(URI_PERSONAL_AREA)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + tokenPatient)
                .header("Environment", ENVIRONMENT)
                .body(getBugReportJson(message,email,author))
                .when()
                .post("/api/bug-reports")
                .then()
                .statusCode(201);
    }

    private static String getBugReportJson(String message, String email, String author) {
        jsonObject.addProperty("message", message);
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("author", author);
        return gson.toJson(jsonObject);
    }


    public static void uploadPhoto(File file) {
        Response response = given()
                .baseUri(URI_ADMIN_PANEL)
                .header("Authorization", "Bearer " + BrowserManager.token)
                .header("Environment", ENVIRONMENT)
                .contentType(ContentType.MULTIPART)
                .multiPart("file", file)
                .when()
                .post("api/storage/upload")
                .then()
                .statusCode(201)
                .extract()
                .response();
        location = response.getBody().jsonPath().getString("location");
    }

    public static void uploadLogo(File file) {
        given()
                .baseUri(URI_ADMIN_PANEL)
                .header("Authorization", "Bearer " + BrowserManager.token)
                .header("Environment", ENVIRONMENT)
                .contentType(ContentType.MULTIPART)
                .multiPart("file", file)
                .when()
                .put("api/storage/logo.png")
                .then()
                .statusCode(201);
    }
}

