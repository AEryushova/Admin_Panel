package admin.test;

import admin.data.DataInfo;
import admin.pages.*;
import admin.pages.modalWindowAdministration.UpdateLegalDocWindow;
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
import user.AuthPage;
import user.HomePage;
import user.pages.modalWindowReportBug.ReportBug;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Настройки")
public class SettingPageTest {


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
        open("http://192.168.6.48:8083");
        localStorage().setItem("Environment", "demo");
        clearBrowserCookies();
        AuthorizationPage authorizationPage = new AuthorizationPage();
        DataInfo dataInfo = new DataInfo("SUPER_ADMIN", "Qqqq123#");
        authorizationPage.authorizationAdminPanel(dataInfo);
        HeaderBar headerBar = new HeaderBar();
        headerBar.headerBarSuperAdmin();
    }


    static void addBugReport() {
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

    @Feature("Сообщения об ошибках")
    @Story("Отображение баг-репорта в админ-панели")
    @Test
    void checkBugReportAfterAdd() {
        HeaderBar headerBar = new HeaderBar();
        SettingPage settingPage = headerBar.settingTabOpen();
        settingPage.settingPage();
        assertEquals("Федоров Федор Федорович", settingPage.getTextByElement("author"));
        assertEquals("Test@mail.ru", settingPage.getTextByElement("emailAuthor"));
        assertEquals(DataHelper.getCurrentDateRuYear(), settingPage.getTextByElement("date"));
        assertEquals("Не могу записаться к врачу", settingPage.getTextByElement("text"));
    }

    @Feature("Сообщения об ошибках")
    @Story("Успешное удаление баг-репорта")
    @Test
    void deleteBugReport() {
        HeaderBar headerBar = new HeaderBar();
        SettingPage settingPage = headerBar.settingTabOpen();
        settingPage.settingPage();
        settingPage.deleteBugReport();
        assertEquals("Сообщение удалено", settingPage.getNotification());
    }

    @Story("Возврат к хэдеру страницы настроек")
    @Test
    void returnToStartPage() {
        HeaderBar headerBar = new HeaderBar();
        SettingPage settingPage = headerBar.settingTabOpen();
        settingPage.settingPage();
        settingPage.scrollPageToBottom();
        settingPage.returnToStartPage();
        settingPage.returnButtonDisappears();
    }

    @Story("Закрытие уведомления на странице faq")
    @Test
    void closeNotification() {
        HeaderBar headerBar = new HeaderBar();
        SettingPage settingPage = headerBar.settingTabOpen();
        settingPage.settingPage();
        settingPage.deleteBugReport();
        assertEquals("Сообщение удалено", settingPage.getNotification());
        settingPage.closeNotification();
    }
}
