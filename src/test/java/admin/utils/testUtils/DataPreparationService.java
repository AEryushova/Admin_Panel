package admin.utils.testUtils;


import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Getter;

import java.io.File;

import static io.restassured.RestAssured.given;

public class DataPreparationService {
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
                .baseUri(admin.data.DataInfo.Urls.getUriPersonalArea())
                .header("Authorization", "Bearer " + tokenPatient)
                .header("Environment", admin.data.DataInfo.Urls.getEnvironmentFreeze())
                .when()
                .get("/api/clients/user-info")
                .then()
                .statusCode(200);
    }


    private static void tokenGetAuthPatient() {
        Response response = given()
                .baseUri(admin.data.DataInfo.Urls.getUriPersonalArea())
                .header("Environment", admin.data.DataInfo.Urls.getEnvironmentFreeze())
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
                .baseUri(admin.data.DataInfo.Urls.getUriPersonalArea())
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + tokenPatient)
                .header("Environment", admin.data.DataInfo.Urls.getEnvironmentFreeze())
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


    private static class ChangePassword {
        private String oldPassword;
        private String newPassword;


        public ChangePassword (String oldPassword,String newPassword) {
            this.oldPassword = oldPassword;
            this.newPassword = newPassword;
        }
    }

    public static void authAdmin(String login, String password) {
        tokenGetAuthAdmin(login, password);
        given()
                .baseUri(admin.data.DataInfo.Urls.getUriAdminPanel())
                .header("Authorization", "Bearer " + tokenAdmin)
                .header("Environment", admin.data.DataInfo.Urls.getEnvironmentFreeze())
                .when()
                .get("/api/admins/admin-data")
                .then()
                .statusCode(200);
    }

    private static void tokenGetAuthAdmin(String login, String password) {
        String dataInfoJson = getDataInfoJson(login,password);
        Response response = given()
                .baseUri(admin.data.DataInfo.Urls.getUriAdminPanel())
                .header("Environment", admin.data.DataInfo.Urls.getEnvironmentFreeze())
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

    public static void changePasswordAdmin(String oldPassword, String newPassword) {
        String changePassword=getChangePasswordJson(oldPassword,newPassword);
        given()
                .baseUri(admin.data.DataInfo.Urls.getUriAdminPanel())
                .header("Authorization", "Bearer " + tokenAdmin)
                .header("Environment", admin.data.DataInfo.Urls.getEnvironmentFreeze())
                .contentType(ContentType.JSON)
                .body(changePassword)
                .when()
                .post("api/admins/change-password")
                .then()
                .statusCode(204);
    }

    private static String getChangePasswordJson(String oldPassword, String newPassword) {
        ChangePassword changePassword=new ChangePassword(oldPassword,newPassword);
        return gson.toJson(changePassword);
    }

    public static void uploadPhoto(File file) {
        Response response = given()
                .baseUri(admin.data.DataInfo.Urls.getUriAdminPanel())
                .header("Authorization", "Bearer " + BrowserManager.token)
                .header("Environment", admin.data.DataInfo.Urls.getEnvironmentFreeze())
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
