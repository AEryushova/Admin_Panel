package ru.adminlk.clinica.test;


import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import ru.adminlk.clinica.pages.AdministrationPage.AdministrationPage;
import ru.adminlk.clinica.pages.AuthorizationPage.AuthorizationPage;
import ru.adminlk.clinica.pages.BasePage.BasePage;
import ru.adminlk.clinica.pages.CardDoctorPage.CardDoctorPage;
import ru.adminlk.clinica.pages.DoctorsPage.DoctorsPage;
import ru.adminlk.clinica.pages.FaqPage.FaqPage;
import ru.adminlk.clinica.pages.HeaderMenu.HeaderMenu;
import ru.adminlk.clinica.pages.ServicesPage.ServicesPage;
import ru.adminlk.clinica.pages.SettingPage.SettingPage;
import ru.adminlk.clinica.utils.preparationData.general.AllureTestListeners;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Cookie;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;


import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.localStorage;
import static ru.adminlk.clinica.data.AppData.*;

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

    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();


    public static void getAuthToken(String login, String password) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("login", login);
        jsonObject.addProperty("password", password);
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString()))
                    .uri(URI.create(URL_BACK + "/api/admins/sign-in"))
                    .header("Environment", ENVIRONMENT)
                    .header("Content-Type", "application/json")
                    .build();
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("Authorization token successfully received");
                JsonObject jsonResponse = com.google.gson.JsonParser.parseString(response.body()).getAsJsonObject();
                token = jsonResponse.get("accessToken").getAsString();
            } else {
                System.out.println("Failed to obtain authorization token. Status code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    protected static void openAdminPanel() {
        configureBrowser();
        Configuration.browserSize = "1920x1080";
        Configuration.headless = Boolean.parseBoolean(System.getProperty("selenide.headless"));
        Configuration.remote = "http://192.168.13.57:8080/wd/hub";
        open(URL_ADMIN_PANEL);
        localStorage().setItem("Environment", ENVIRONMENT);
    }

    protected static void openAuthAdminPanel() {
        configureBrowser();
        baseUrl = URL_ADMIN_PANEL;
        Configuration.browserSize = "1920x1080";
        Configuration.headless = Boolean.parseBoolean(System.getProperty("selenide.headless"));
        Configuration.remote = "http://192.168.13.57:8080/wd/hub";
        open(baseUrl);
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
            case "firefox" -> configFirefox();
            case "chrome" -> configChrome();
            case "edge" -> configEdge();
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }

    private static void configChrome() {
        ChromeOptions options = new ChromeOptions();
        Configuration.browserVersion="128.0";
        options.addArguments("--disable-cache", "--disk-cache-size=0");
        Configuration.browserCapabilities = options;
    }

    private static void configFirefox() {
        FirefoxOptions options = new FirefoxOptions();
        Configuration.browserVersion="125.0";
        options.addPreference("browser.cache.disk.enable", false);
        options.addPreference("browser.cache.memory.enable", false);
        options.addPreference("browser.cache.offline.enable", false);
        options.addPreference("network.http.use-cache", false);
        Configuration.browserCapabilities = options;
    }

    private static void configEdge() {
        EdgeOptions options = new EdgeOptions();
        Configuration.browserVersion="129.0";
        options.addArguments("--disable-cache", "--disk-cache-size=0");
        Configuration.browserCapabilities = options;
    }

}
