package admin.utils.preparationDataTests.requestAPI;


import admin.data.DataConfig;
import admin.utils.testUtils.BrowserManager;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Getter;

import java.io.File;

import static io.restassured.RestAssured.given;

public class PreparationDataService {
    private static final Gson gson = new Gson();
    private static String tokenPatient;
    private static String tokenAdmin;

    @Getter
    public static String location;

    private static class DataInfo {
        private String login;
        private String password;

        public DataInfo(String login, String password) {
            this.login = login;
            this.password = password;
        }
    }


    public static void createAdmin(String login, String password) {
        String dataInfoJson = getDataInfoJson(login, password);
        given()
                .baseUri(DataConfig.Urls.getUriAdminPanel())
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + BrowserManager.token)
                .header("Environment", DataConfig.Urls.getEnvironment())
                .body(dataInfoJson)
                .when()
                .post("/api/admins/sign-up")
                .then()
                .statusCode(201);
    }


    public static void deleteAdmin(String login) {
        given()
                .baseUri(DataConfig.Urls.getUriAdminPanel())
                .queryParam("login", login)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + BrowserManager.token)
                .header("Environment", DataConfig.Urls.getEnvironment())
                .when()
                .delete("/api/admins")
                .then()
                .statusCode(204);
    }

    private static String getDataInfoJson(String login, String password) {
        DataInfo dataInfo = new DataInfo(login, password);
        return gson.toJson(dataInfo);
    }

    private static class BugReportInfo {
        private String message;
        private String email;
        private String author;

        public BugReportInfo (String message, String email, String author) {
            this.message = message;
            this.email = email;
            this.author=author;
        }
    }

    public static void authPatient() {
        tokenGetAuthPatient();
        given()
                .baseUri(DataConfig.Urls.getUriPersonalArea())
                .header("Authorization", "Bearer " + tokenPatient)
                .header("Environment", DataConfig.Urls.getEnvironment())
                .when()
                .get("/api/clients/user-info")
                .then()
                .statusCode(200);
    }


    private static void tokenGetAuthPatient() {
        Response response = given()
                .baseUri(DataConfig.Urls.getUriPersonalArea())
                .header("Environment", DataConfig.Urls.getEnvironment())
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
                .baseUri(DataConfig.Urls.getUriPersonalArea())
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + tokenPatient)
                .header("Environment", DataConfig.Urls.getEnvironment())
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

    private static class DataChangePassword {
        private String login;
        private String newPassword;

        public DataChangePassword(String login, String newPassword) {
            this.login = login;
            this.newPassword = newPassword;
        }
    }


    public static void authAdmin(String login, String password) {
        tokenGetAuthAdmin(login, password);
        given()
                .baseUri(DataConfig.Urls.getUriAdminPanel())
                .header("Authorization", "Bearer " + tokenAdmin)
                .header("Environment", DataConfig.Urls.getEnvironment())
                .when()
                .get("/api/admins/admin-data")
                .then()
                .statusCode(200);
    }

    private static void tokenGetAuthAdmin(String login, String password) {
        String dataInfoJson = getDataInfoJson(login,password);
        Response response = given()
                .baseUri(DataConfig.Urls.getUriAdminPanel())
                .header("Environment", DataConfig.Urls.getEnvironment())
                .contentType(ContentType.JSON)
                .body(dataInfoJson)
                .when()
                .post("/api/admins/sign-in")
                .then()
                .statusCode(200)
                .extract()
                .response();
        tokenAdmin = response.getBody().jsonPath().getString("accessToken");
    }

    public static String changePasswordAdmin(String login, String newPassword) {
        String changePassword = getChangePasswordJson(login, newPassword);
        String response = given()
                .baseUri(DataConfig.Urls.getUriAdminPanel())
                .header("Authorization", "Bearer " + tokenAdmin)
                .header("Environment", DataConfig.Urls.getEnvironment())
                .contentType(ContentType.JSON)
                .body(changePassword)
                .when()
                .post("/api/admins/reset-password")
                .then()
                .statusCode(204)
                .extract()
                .response()
                .asString();
        return response;
    }

    private static String getChangePasswordJson(String login, String newPassword) {
        DataChangePassword dataChangePassword =new DataChangePassword(login,newPassword);
        return gson.toJson(dataChangePassword);
    }



    public static void uploadPhoto(File file) {
        Response response = given()
                .baseUri(DataConfig.Urls.getUriAdminPanel())
                .header("Authorization", "Bearer " + BrowserManager.token)
                .header("Environment", DataConfig.Urls.getEnvironment())
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
