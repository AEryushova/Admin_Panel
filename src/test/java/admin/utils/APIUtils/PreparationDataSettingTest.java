package admin.utils.APIUtils;

import admin.data.AppConfig;
import admin.utils.testUtils.BrowserManager;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.io.File;

import static io.restassured.RestAssured.given;

public class PreparationDataSettingTest {

    private static final Gson gson = new Gson();
    private static String tokenPatient;

    @Getter
    public static String location;

    @Value
    @AllArgsConstructor
    private static class BugReportInfo {
        String message;
        String email;
        String author;
    }

    public static void authPatient() {
        tokenGetAuthPatient();
        given()
                .baseUri(AppConfig.getURI_PERSONAL_AREA())
                .header("Authorization", "Bearer " + tokenPatient)
                .header("Environment", AppConfig.getENVIRONMENT())
                .when()
                .get("/api/clients/user-info")
                .then()
                .statusCode(200);
    }


    private static void tokenGetAuthPatient() {
        Response response = given()
                .baseUri(AppConfig.getURI_PERSONAL_AREA())
                .header("Environment", AppConfig.getENVIRONMENT())
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
        String bugReport=getBugReportJson(message,email,author);
        given()
                .baseUri(AppConfig.getURI_PERSONAL_AREA())
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + tokenPatient)
                .header("Environment", AppConfig.getENVIRONMENT())
                .body(bugReport)
                .when()
                .post("/api/bug-reports")
                .then()
                .statusCode(201);
    }

    private static String getBugReportJson(String message, String email, String author) {
        BugReportInfo bugReport=new BugReportInfo(message,email,author);
        return gson.toJson(bugReport);
    }


    public static void uploadPhoto(File file) {
        Response response = given()
                .baseUri(AppConfig.getURI_ADMIN_PANEL())
                .header("Authorization", "Bearer " + BrowserManager.token)
                .header("Environment", AppConfig.getENVIRONMENT())
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
}

