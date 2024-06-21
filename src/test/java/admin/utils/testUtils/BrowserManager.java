package admin.utils.testUtils;

import admin.config.AppConfig;
import admin.pages.AuthorizationPage.AuthorizationPage;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.Cookie;


import static com.codeborne.selenide.Selenide.*;

public class BrowserManager {

    @Setter
    @Getter
    public static String token;


    public static void openAdminPanel(){
        Configuration.holdBrowserOpen = true;
        open(AppConfig.getURI_ADMIN_PANEL());
        localStorage().setItem("Environment", AppConfig.getENVIRONMENT());
        clearBrowserCookies();
    }

    public static void openBrowser(String login, String password) {
        Configuration.holdBrowserOpen = true;
        open(AppConfig.getURI_ADMIN_PANEL());
        localStorage().setItem("Environment", AppConfig.getENVIRONMENT());
        clearBrowserCookies();
        localStorage().removeItem("accessToken");
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authorization(login, password);
        Selenide.sleep(7000);
        Cookie authCookie = WebDriverRunner.getWebDriver().manage().getCookieNamed("token");
        String token = (authCookie != null) ? authCookie.getValue() : null;
        setToken(token);
    }

}

