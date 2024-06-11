package user.utils;

import com.codeborne.selenide.Configuration;
import user.data.DataInfo;
import user.pages.AuthPage.AuthPage;
import user.pages.HeaderMenu.HeaderMenu;
import user.pages.HomePage.HomePage;

import static com.codeborne.selenide.Selenide.*;

public class BrowserManager {

    public static void authPersonalArea() {
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1920x1080";
        open(DataInfo.getUriPersonalArea());
        localStorage().setItem("Environment", "freeze");
        clearBrowserCookies();
        AuthPage authPage = new AuthPage();
        authPage.authorizationLK();;
    }
}
