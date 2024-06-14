package admin.utils.testUtils;

import admin.data.DataConfig;
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
        open(DataConfig.Urls.getUriAdminPanel());
        localStorage().setItem("Environment", DataConfig.Urls.getEnvironment());
        clearBrowserCookies();
    }

    public static void authGetCookie(String login, String password) {
        Configuration.holdBrowserOpen = true;
        open(DataConfig.Urls.getUriAdminPanel());
        localStorage().setItem("Environment", DataConfig.Urls.getEnvironment());
        WebDriverRunner.getWebDriver().manage().deleteAllCookies();
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authorization(login, password);
        Selenide.sleep(7000);
        Cookie authCookie = WebDriverRunner.getWebDriver().manage().getCookieNamed("token");
        String token = (authCookie != null) ? authCookie.getValue() : null;
        setToken(token);
        Selenide.closeWebDriver();
    }

    public static void openPagesAfterAuth() {
        Configuration.holdBrowserOpen = true;
        open(DataConfig.Urls.getUriAdminPanel());
        localStorage().setItem("Environment", DataConfig.Urls.getEnvironment());
        localStorage().setItem("accessToken", token);
        Selenide.refresh();
    }
}

