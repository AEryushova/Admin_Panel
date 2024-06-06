package user.utils;

import com.codeborne.selenide.Configuration;
import user.data.DataInfo;
import user.pages.AuthPage;
import user.pages.HeaderBarLK;
import user.pages.HomePage;

import static com.codeborne.selenide.Selenide.*;

public class TestSetupAuthLK {

    public static void authLK() {
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1920x1080";
        open(DataInfo.getUriPersonalArea());
        localStorage().setItem("Environment", "freeze");
        clearBrowserCookies();
        AuthPage authPage = new AuthPage();
        HomePage homePage = authPage.authorizationLK();
        homePage.homePage();
        HeaderBarLK headerBar = new HeaderBarLK();
        headerBar.headerBarLK();
    }
}
