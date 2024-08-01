package utils.APIUtils;


import test.BaseTest;
import com.google.gson.JsonObject;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Getter;
import static appData.AppData.*;

import java.io.File;

import static io.restassured.RestAssured.given;

public class PreparationDataSettingTest {

    private static String tokenPatient;

    @Getter
    public static String location;


    public static void authPatient() {
        tokenGetAuthPatient();
        given()
                .baseUri(URI_BACK)
                .header("Authorization", "Bearer " + tokenPatient)
                .header("Environment", ENVIRONMENT)
                .when()
                .get("/api/clients/user-info")
                .then()
                .statusCode(200);
    }


    private static void tokenGetAuthPatient() {
        Response response = given()
                .baseUri(URI_BACK)
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
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("message", message);
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("author", author);
        given()
                .baseUri(URI_BACK)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + tokenPatient)
                .header("Environment", ENVIRONMENT)
                .body(jsonObject.toString())
                .when()
                .post("/api/bug-reports")
                .then()
                .statusCode(201);
    }


    public static void uploadPhotoToStorage(File file) {
        Response response = given()
                .baseUri(URI_BACK)
                .header("Authorization", "Bearer " + BaseTest.token)
                .header("Environment", ENVIRONMENT)
                .contentType(ContentType.MULTIPART)
                .multiPart("file", file)
                .when()
                .post("/api/storage/upload")
                .then()
                .statusCode(201)
                .extract()
                .response();
        location = response.getBody().jsonPath().getString("location");
    }

    public static void deleteImageInStorage() {
                 given()
                .baseUri(URI_BACK)
                .header("Authorization", "Bearer " + BaseTest.token)
                .header("Environment", ENVIRONMENT)
                .when()
                .delete(location)
                .then()
                .statusCode(204);

    }

    public static void uploadLogo(File file) {
        given()
                .baseUri(URI_BACK)
                .header("Authorization", "Bearer " + BaseTest.token)
                .header("Environment", ENVIRONMENT)
                .contentType(ContentType.MULTIPART)
                .multiPart("file", file)
                .when()
                .put("/api/storage/logo.png")
                .then()
                .statusCode(201);
    }

}

