package ru.adminlk.clinica.samsmu.test;


import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import ru.adminlk.clinica.samsmu.pages.AdministrationPage.AdministrationPage;
import ru.adminlk.clinica.samsmu.pages.AuthorizationPage.AuthorizationPage;
import ru.adminlk.clinica.samsmu.pages.BasePage.BasePage;
import ru.adminlk.clinica.samsmu.pages.CardDoctorPage.CardDoctorPage;
import ru.adminlk.clinica.samsmu.pages.DoctorsPage.DoctorsPage;
import ru.adminlk.clinica.samsmu.pages.FaqPage.FaqPage;
import ru.adminlk.clinica.samsmu.pages.HeaderMenu.HeaderMenu;
import ru.adminlk.clinica.samsmu.pages.ServicesPage.ServicesPage;
import ru.adminlk.clinica.samsmu.pages.SettingPage.SettingPage;
import ru.adminlk.clinica.samsmu.utils.preparationData.general.AllureTestListeners;
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


import static ru.adminlk.clinica.samsmu.appData.AppData.ENVIRONMENT;
import static ru.adminlk.clinica.samsmu.appData.AppData.URI_ADMIN_PANEL;
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

    public static void getAuthToken(String login, String password) {
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

    protected static void openAdminPanel() {
        configureBrowser();
        Configuration.browserSize = "1920x1080";
        Configuration.headless = Boolean.parseBoolean(System.getProperty("selenide.headless"));
        open(URI_ADMIN_PANEL);
        localStorage().setItem("Environment", ENVIRONMENT);
    }

    protected static void openAuthAdminPanel() {
        configureBrowser();
        Configuration.browserSize = "1920x1080";
        Configuration.headless = Boolean.parseBoolean(System.getProperty("selenide.headless"));
        open(URI_ADMIN_PANEL);
        localStorage().setItem("Environment", ENVIRONMENT);
        WebDriverRunner.getWebDriver().manage().addCookie(new Cookie("token", token));
        localStorage().setItem("accessToken", token);
        WebDriverRunner.getWebDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
        Selenide.refresh();
    }

    protected static void closeDriver(){
        clearBrowserLocalStorage();
        clearBrowserCookies();
        Selenide.closeWebDriver();
    }

    private static void configureBrowser() {
        String browser=System.getProperty("selenide.browser");
        Configuration.browser = browser;
        switch (browser) {
            case "firefox":
                configFirefox();
                break;
            case "chrome":
                configChrome();
                break;
            case "edge":
                configEdge();
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }

    private static void configFirefox() {
        FirefoxOptions options = new FirefoxOptions();
        options.addPreference("browser.cache.disk.enable", false);
        options.addPreference("browser.cache.memory.enable", false);
        options.addPreference("browser.cache.offline.enable", false);
        options.addPreference("network.http.use-cache", false);
        Configuration.browserCapabilities = options;
    }

    private static void configChrome() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-cache", "--disk-cache-size=0");
        Configuration.browserCapabilities = options;
    }

    private static void configEdge() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--disable-cache", "--disk-cache-size=0");
        Configuration.browserCapabilities = options;
    }

}
