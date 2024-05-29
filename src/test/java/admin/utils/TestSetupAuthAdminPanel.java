package admin.utils;

import admin.data.DataTest;
import admin.pages.AdministrationPage;
import admin.pages.AuthorizationPage;
import admin.pages.DoctorsPage;
import admin.pages.HeaderBar;
import com.codeborne.selenide.Configuration;

import static com.codeborne.selenide.Selenide.*;

public class TestSetupAuthAdminPanel {

    public static void openAuthPageAdminPanel() {
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1920x1080";
        open(DataTest.getUriAdminPanel());
        localStorage().setItem("Environment", "demo");
        clearBrowserCookies();
    }

    public static void authAdminPanel(String login, String password) {
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1920x1080";
        open(DataTest.getUriAdminPanel());
        localStorage().setItem("Environment", "demo");
        clearBrowserCookies();
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authorizationAdminPanel(login,password);
        DoctorsPage doctorsPage=new DoctorsPage();
        doctorsPage.doctorsPage();
        CookieUtils.saveCookies();
    }


}

