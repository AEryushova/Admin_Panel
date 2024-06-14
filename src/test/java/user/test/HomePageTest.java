package user.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import user.pages.AuthPage.AuthPage;
import user.pages.HeaderMenu.HeaderMenuLK;
import user.pages.HomePage.HomePage;
import user.pages.HomePage.ReportBug;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomePageTest {
    private static HomePage homePage;


    @BeforeAll
    static void setUpAllAllure() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }


    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setUp() {
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1920x1080";
        open("https://lk.mdapp.online/auth");
        localStorage().setItem("Environment", "freeze");
        clearBrowserCookies();
        AuthPage authPage = new AuthPage();
        homePage = authPage.authorizationLK();
        homePage.homePage();
        HeaderMenuLK headerBar = new HeaderMenuLK();
        headerBar.headerBarLK();
    }

    static void addBugReport() {
        ReportBug reportBug = homePage.sendReportBug();
        reportBug.reportBug();
        reportBug.fillingFieldTextReportBug("Не могу записаться к врачу");
        reportBug.fillingFieldEmailReportBug("Test@mail.ru");
        reportBug.clickSendButton();
        assertEquals("Ваше сообщение об ошибке передано администратору. Благодарим за содействие в улучшении нашего сервиса!", homePage.getNotification());
    }

    static void addBugReport2() {
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1920x1080";
        open("https://lk.mdapp.online/auth");
        localStorage().setItem("Environment", "freeze");
        clearBrowserCookies();
        AuthPage authPage = new AuthPage();
        HomePage homePage = authPage.authorizationLK();
        homePage.homePage();
        ReportBug reportBug = homePage.sendReportBug();
        reportBug.reportBug();
        reportBug.fillingFieldTextReportBug("Не могу записаться к врачу");
        reportBug.fillingFieldEmailReportBug("Test@mail.ru");
        reportBug.clickSendButton();
        assertEquals("Ваше сообщение об ошибке передано администратору. Благодарим за содействие в улучшении нашего сервиса!", homePage.getNotification());
    }

}
