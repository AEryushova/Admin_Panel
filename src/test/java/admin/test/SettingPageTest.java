package admin.test;

import admin.data.DataInfo;
import admin.pages.AuthorizationPage;
import admin.pages.HeaderBar;
import admin.pages.SettingPage;
import admin.utils.DataHelper;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import user.pages.AuthPage;
import user.pages.HomePage;
import user.pages.modalWindowReportBug.ReportBug;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Настройки")
@Feature("Настройки ЛК для админа")
public class SettingPageTest {
    private static AuthorizationPage authorizationPage;
    private static HeaderBar headerBar;
    private static SettingPage settingPage;

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
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--auto-open-devtools-for-tabs");
        Configuration.browserCapabilities = options;
        open("http://192.168.6.48:8083");
        localStorage().setItem("Environment", "demo");
        clearBrowserCookies();
        DataInfo dataInfo = new DataInfo("SUPER_ADMIN", "Qqqq123#");
        authorizationPage.authorizationAdminPanel(dataInfo);
        headerBar = new HeaderBar();
        headerBar.headerBarSuperAdmin();
    }

    @BeforeAll
    static void addBugReport() {
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1920x1080";
        open("https://lk.mdapp.online/auth");
        localStorage().setItem("Environment", "freeze");
        clearBrowserCookies();
        AuthPage authPage = new AuthPage();
        HomePage homePage = authPage.authorizationLK();
        homePage.homePage();
        ReportBug reportBug= homePage.sendReportBug();
        reportBug.reportBug();
        reportBug.fillingFieldTextReportBug("Не могу записаться к врачу");
        reportBug.fillingFieldEmailReportBug("Test@mail.ru");
        reportBug.clickSendButton();
        assertEquals("Ваше сообщение об ошибке передано администратору. Благодарим за содействие в улучшении нашего сервиса!", homePage.getNotification());
    }

    @Story("Отображение баг-репорта в админ-панели")
    @Test
    void checkBugReportAfterAdd() {
        settingPage=headerBar.settingTabOpen();
        settingPage.settingPage();
        assertEquals("Федоров Федор Федорович",settingPage.getTextByElement("author"));
        assertEquals("Test@mail.ru",settingPage.getTextByElement("emailAuthor"));
        assertEquals(DataHelper.getCurrentDateRu(),settingPage.getTextByElement("date"));
        assertEquals("Не могу записаться к врачу",settingPage.getTextByElement("text"));
    }

    @Story("Успешное удаление баг-репорта из админ-панели")
    @Test
    void deleteBugReport() {
        settingPage=headerBar.settingTabOpen();
        settingPage.settingPage();
        settingPage.deleteBugReport();
        assertEquals("Сообщение удалено",settingPage.getNotification());
    }
}
