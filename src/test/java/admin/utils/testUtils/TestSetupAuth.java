package admin.utils.testUtils;

import admin.data.DataInfo;
import admin.pages.AuthorizationPage;
import admin.pages.HeaderMenu;
import com.codeborne.selenide.Configuration;

import static com.codeborne.selenide.Selenide.*;

public class TestSetupAuth {

    public static void openAuthPage() {
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1920x1080";
        open(DataInfo.Urls.getUriAdminPanel());
        localStorage().setItem("Environment", "demo");
        clearBrowserCookies();
    }


    public static void authAdminPanel(String login, String password) {
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1920x1080";
        open(DataInfo.Urls.getUriAdminPanel());
        localStorage().setItem("Environment", "demo");
        clearBrowserCookies();
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authorization(login,password);
        CookieUtils.saveCookies();
    }

    public static void openAdministrationPage() {
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1920x1080";
        open(DataInfo.Urls.getUriAdminPanel() + "/admins");
        localStorage().setItem("Environment", "demo");
        CookieUtils.loadCookies();
    }

    public static void openServicesPage() {
        HeaderMenu headerBar = new HeaderMenu();
        headerBar.servicesTabOpen();
    }

    public static void openFaqPage() {
        HeaderMenu headerBar = new HeaderMenu();
        headerBar.faqTabOpen();
    }

    public static void openSettingPage() {
        HeaderMenu headerBar = new HeaderMenu();
        headerBar.settingTabOpen();
    }







}

