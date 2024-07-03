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

    private BrowserManager() {
    }

    public static void openBrowser(){
        Configuration.browserSize = "1920x1080";
        open(URI_ADMIN_PANEL);
        localStorage().setItem("Environment", ENVIRONMENT);
        clearBrowserCookies();
    }

    public static void openAdminPanel(String login, String password) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("login", login);
        jsonObject.addProperty("password", password);
        Response response = given()
                .baseUri(URI_ADMIN_PANEL)
                .header("Environment", ENVIRONMENT)
                .contentType(ContentType.JSON)
                .body(jsonObject.toString())
                .when()
                .post("/api/admins/sign-in")
                .then()
                .statusCode(200)
                .extract()
                .response();
        token = response.getBody().jsonPath().getString("accessToken");
        Configuration.browser=System.getProperty("selenide.browser", "chrome");
        Configuration.browserSize = "1920x1080";
        open(URI_ADMIN_PANEL);
        localStorage().setItem("Environment", ENVIRONMENT);
        clearBrowserCookies();
        localStorage().removeItem("accessToken");
        WebDriverRunner.getWebDriver().manage().addCookie(new Cookie("token", token));
        localStorage().setItem("accessToken", token);
        Selenide.refresh();
    }

}

