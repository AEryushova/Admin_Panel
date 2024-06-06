package admin.test;

import admin.data.DataInfo;
import admin.pages.*;
import admin.utils.DataHelper;
import admin.utils.testUtils.*;
import com.codeborne.selenide.Configuration;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import user.pages.AuthPage;
import user.pages.HomePage;
import user.pages.modalWindowReportBug.ReportBug;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Настройки")
public class SettingPageTest {

    private SettingPage settingPage;
    private HeaderMenu headerMenu;

    @ExtendWith(AllureDecorator.class)

    @BeforeAll
    static void setUpAuth() {
        BrowserManager.authGetCookie(DataInfo.UserData.getLoginAdmin(), DataInfo.UserData.getPasswordAdmin());
    }

    @BeforeEach
    void setUp(){
        BrowserManager.openPages();
        settingPage=new SettingPage();
        headerMenu= new HeaderMenu();
        headerMenu.settingTabOpen();
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
        HeaderMenu headerBar = new HeaderMenu();
        SettingPage settingPage = headerBar.settingTabOpen();
        settingPage.settingPage();
        assertEquals("Федоров Федор Федорович", settingPage.getAuthorText());
        assertEquals("Test@mail.ru", settingPage.getEmailAuthorText());
        assertEquals(DataHelper.getCurrentDateRuYear(), settingPage.getDateText());
        assertEquals("Не могу записаться к врачу", settingPage.getReportText());
    }

    @Feature("Сообщения об ошибках")
    @Story("Успешное удаление баг-репорта")
    @Test
    void deleteBugReport() {
        HeaderMenu headerBar = new HeaderMenu();
        SettingPage settingPage = headerBar.settingTabOpen();
        settingPage.settingPage();
        settingPage.deleteBugReport();
        assertEquals("Сообщение удалено", settingPage.getNotification());
    }

    @Story("Возврат к хэдеру страницы настроек")
    @Test
    void returnToStartPage() {
        HeaderMenu headerBar = new HeaderMenu();
        SettingPage settingPage = headerBar.settingTabOpen();
        settingPage.settingPage();
        settingPage.scrollPageToBottom();
        settingPage.returnToStartPage();
        settingPage.isReturnButtonAppear();
    }

    @Story("Закрытие уведомления на странице faq")
    @Test
    void closeNotification() {
        HeaderMenu headerBar = new HeaderMenu();
        SettingPage settingPage = headerBar.settingTabOpen();
        settingPage.settingPage();
        settingPage.deleteBugReport();
        assertEquals("Сообщение удалено", settingPage.getNotification());
        settingPage.closeNotification();
    }
}
