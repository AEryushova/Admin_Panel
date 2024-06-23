package admin.utils.testUtils;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.Cookie;

import static appData.AppData.ENVIRONMENT;
import static appData.AppData.URI_ADMIN_PANEL;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;

public class BrowserManager {

    @Setter
    @Getter
    public static String token;

    private static final Gson gson = new Gson();
    private static final JsonObject jsonObject = new JsonObject();

    private BrowserManager() {
    }

    public static void openBrowser(){
        Configuration.holdBrowserOpen = true;
        open(URI_ADMIN_PANEL);
        localStorage().setItem("Environment", ENVIRONMENT);
        clearBrowserCookies();
    }

    public static void openAdminPanel(String login, String password) {
        Response response = given()
                .baseUri(URI_ADMIN_PANEL)
                .header("Environment", ENVIRONMENT)
                .contentType(ContentType.JSON)
                .body(getDataInfoJson(login, password))
                .when()
                .post("/api/admins/sign-in")
                .then()
                .statusCode(200)
                .extract()
                .response();
        token = response.getBody().jsonPath().getString("accessToken");
        Configuration.holdBrowserOpen = true;
        open(URI_ADMIN_PANEL);
        localStorage().setItem("Environment", ENVIRONMENT);
        clearBrowserCookies();
        localStorage().removeItem("accessToken");
        WebDriverRunner.getWebDriver().manage().addCookie(new Cookie("token", token));
        localStorage().setItem("accessToken", token);
        Selenide.refresh();
    }


    private static String getDataInfoJson(String login, String password) {
        jsonObject.addProperty("login", login);
        jsonObject.addProperty("password", password);
        return gson.toJson(jsonObject);
    }

}

