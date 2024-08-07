package test;


import utils.preparationDataTests.general.AllureTestListeners;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.google.gson.JsonObject;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Cookie;

import java.time.Duration;


import static appData.AppData.ENVIRONMENT;
import static appData.AppData.URI_ADMIN_PANEL;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.localStorage;
import static io.restassured.RestAssured.given;

@ExtendWith(AllureTestListeners.class)
public class BaseTest {

    @Setter
    @Getter
    public static String token;


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
        Configuration.browser= System.getProperty("selenide.browser");
        Configuration.browserSize = "1920x1080";
        Configuration.headless = Boolean.parseBoolean(System.getProperty("selenide.headless"));
        Configuration.holdBrowserOpen=true;
        open(URI_ADMIN_PANEL);
        localStorage().setItem("Environment", ENVIRONMENT);
        clearBrowserCookies();
        localStorage().removeItem("accessToken");
        WebDriverRunner.getWebDriver().manage().addCookie(new Cookie("token", token));
        localStorage().setItem("accessToken", token);
        WebDriverRunner.getWebDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
        Selenide.refresh();
    }

}
