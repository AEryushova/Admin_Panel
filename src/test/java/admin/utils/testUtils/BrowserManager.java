package admin.utils.testUtils;

import admin.data.DataInfo;
import admin.pages.AuthorizationPage;
import admin.pages.HeaderMenu;
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

    public static void AuthGetCookie(String login, String password) {
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1920x1080";
        open(DataInfo.Urls.getUriAdminPanel());
        localStorage().setItem("Environment", DataInfo.Urls.getEnvironmentFreeze());
        WebDriverRunner.getWebDriver().manage().deleteAllCookies();
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authorization(login, password);
        Selenide.sleep(7000);
        Cookie authCookie = WebDriverRunner.getWebDriver().manage().getCookieNamed("token");
        String token = (authCookie != null) ? authCookie.getValue() : null;
        setToken(token);
        Selenide.closeWebDriver();
    }

    public static void openAdminPage() {
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1920x1080";
        open(DataInfo.Urls.getUriAdminPanel() + "/admins");
        localStorage().setItem("Environment", DataInfo.Urls.getEnvironmentFreeze());
        localStorage().setItem("accessToken", token);
        Selenide.refresh();
    }
}

