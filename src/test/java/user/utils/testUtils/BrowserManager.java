package user.utils.testUtils;

import com.codeborne.selenide.Configuration;
import user.pages.AuthPage.AuthPage;

import static appData.AppData.URI_PERSONAL_AREA;
import static com.codeborne.selenide.Selenide.*;


public class BrowserManager {

    public static void authPersonalArea() {
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1920x1080";
        open(URI_PERSONAL_AREA);
        localStorage().setItem("Environment", "freeze");
        clearBrowserCookies();
        AuthPage authPage = new AuthPage();
        authPage.authorizationLK();;
    }
}
