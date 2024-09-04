package test;


import pages.AdministrationPage.AdministrationPage;
import pages.AuthorizationPage.AuthorizationPage;
import pages.BasePage.BasePage;
import pages.CardDoctorPage.CardDoctorPage;
import pages.DoctorsPage.DoctorsPage;
import pages.FaqPage.FaqPage;
import pages.HeaderMenu.HeaderMenu;
import pages.ServicesPage.ServicesPage;
import pages.SettingPage.SettingPage;
import utils.preparationData.general.AllureTestListeners;
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

    AdministrationPage adminPage = new AdministrationPage();
    AuthorizationPage authPage = new AuthorizationPage();
    HeaderMenu headerMenu = new HeaderMenu();
    BasePage basePage = new BasePage();
    CardDoctorPage cardDoctor = new CardDoctorPage();
    DoctorsPage doctorsPage = new DoctorsPage();
    FaqPage faqPage = new FaqPage();
    ServicesPage servicesPage = new ServicesPage();
    SettingPage settingPage = new SettingPage();

    @Setter
    @Getter
    public static String token;


    public static void openBrowser() {
        Configuration.browser = System.getProperty("selenide.browser");
        Configuration.browserSize = "1920x1080";
        Configuration.headless = Boolean.parseBoolean(System.getProperty("selenide.headless"));
        open(URI_ADMIN_PANEL);
        localStorage().setItem("Environment", ENVIRONMENT);
        clearBrowserCookies();
    }

    public static void authAdminPanel(String login, String password) {
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
    }

    public static void openAdminPanel() {
        Configuration.browser = System.getProperty("selenide.browser");
        Configuration.browserSize = "1920x1080";
        Configuration.headless = Boolean.parseBoolean(System.getProperty("selenide.headless"));
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
