package admin.test;

import admin.data.DataInfo;
import admin.pages.BasePage.BasePage;
import admin.pages.HeaderMenu.HeaderMenu;
import admin.pages.SettingPage.BugReport;
import admin.pages.SettingPage.SettingPage;
import admin.pages.SettingPage.EditLogoWindow;
import admin.utils.testUtils.DataHelper;
import admin.utils.dbUtils.DataBaseUtils;
import admin.utils.decoratorsTest.*;
import admin.utils.testUtils.*;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Настройки")
public class SettingPageTest extends BaseTest {

    private SettingPage settingPage;
    private HeaderMenu headerMenu;
    private BasePage basePage;


    @ExtendWith(AllureDecorator.class)


    @BeforeAll
    static void setUpAuth() {
        BrowserManager.authGetCookie(DataInfo.UserData.getLoginAdmin(), DataInfo.UserData.getPasswordAdmin());
    }

    @BeforeEach
    void setUp() {
        BrowserManager.openPagesAfterAuth();
        settingPage = new SettingPage();
        headerMenu = new HeaderMenu();
        basePage = new BasePage();
        headerMenu.settingTabOpen();
    }

    @Feature("Сообщения об ошибках")
    @Story("Отображение баг-репорта в админ-панели после отправки пациентом")
    @ExtendWith(AddDeleteBugReportDecorator.class)
    @Test
    void checkBugReport() {
        settingPage.settingPage();
        BugReport bugReport =settingPage.bugReportCard();
        bugReport.bugReport();
        assertEquals(DataInfo.DataTest.getNamePatient(), bugReport.getAuthorText());
        assertEquals(DataInfo.DataTest.getEmailPatient(), bugReport.getEmailAuthorText());
        assertEquals(DataHelper.getCurrentDateRuYear(), bugReport.getDateText());
        assertEquals(DataInfo.DataTest.getMessageBugReport(), bugReport.getReportText());
    }

    @Feature("Сообщения об ошибках")
    @Story("Успешное удаление баг-репорта")
    @ExtendWith({AddBugReportDecorator.class,NotificationDecorator.class})
    @Test
    void deleteBugReport() {
        BugReport bugReport =settingPage.bugReportCard();
        bugReport.deleteBugReport();
        assertEquals("Сообщение удалено", settingPage.getNotification());
        assertFalse(settingPage.isExistsBugReport());
        assertNull(DataBaseUtils.selectBugReports());
    }

    @Feature("Настройки личного кабинета")
    @Story("Успешная замена логотипа в формате PNG")
    @ExtendWith(SetSAMSMU_Logo.class)
    @Test
    void changeLogoPNG() {
        EditLogoWindow editLogoWindow = settingPage.openWindowEditLogo();
        editLogoWindow.editLogoWindow();
        editLogoWindow.uploadLogo("src/test/resources/visa.png");
        Selenide.sleep(5000);
        assertFalse(editLogoWindow.isWindowAppear());
        assertEquals(113, settingPage.getHeightLogo());
    }


    @Feature("Настройки личного кабинета")
    @Story("Замена логотипа в формате JPEG")
    @ExtendWith(NotificationDecorator.class)
    @Test
    void changeLogoJPEG() {
        EditLogoWindow editLogoWindow = settingPage.openWindowEditLogo();
        editLogoWindow.editLogoWindow();
        editLogoWindow.uploadLogo("src/test/resources/Photo 3,7mbJpeg.jpg");
        assertEquals("Неверный запрос (400)", settingPage.getNotification());
        assertTrue(editLogoWindow.isWindowAppear());
    }

    @Feature("Настройки личного кабинета")
    @Story("Замена логотипа весом более 4mb")
    @ExtendWith(NotificationDecorator.class)
    @Test
    void changeLogoLess4mb() {
        EditLogoWindow editLogoWindow = settingPage.openWindowEditLogo();
        editLogoWindow.editLogoWindow();
        editLogoWindow.uploadLogo("src/test/resources/Photo-6_8mbPng.png");
        assertEquals("Допускаются файлы размером не выше 4Мб",settingPage.getNotification());
        assertTrue(editLogoWindow.isWindowAppear());
    }


    @Feature("Настройки личного кабинета")
    @Story("Замена логотипа с файлом в невалидном формате")
    @ExtendWith(NotificationDecorator.class)
    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/Оферта,Политика обработки docx.docx", "src/test/resources/Оферта, Политика обработки .xlsx.xlsx", "src/test/resources/Политика обработки персональных данных.pdf"})
    void changeLogoInvalidFormat(String path) {
        EditLogoWindow editLogoWindow = settingPage.openWindowEditLogo();
        editLogoWindow.editLogoWindow();
        editLogoWindow.uploadLogo(path);
        assertEquals("Допускаются файлы с расширением jpg jpeg png",settingPage.getNotification());
        assertTrue(editLogoWindow.isWindowAppear());
    }


    @Feature("Настройки личного кабинета")
    @Story("Закрытие окна замены логотипа")
    @Test
    void closeWindowErrorsPrice() {
        EditLogoWindow editLogoWindow = settingPage.openWindowEditLogo();
        editLogoWindow.closeWindowEditLogo();
        assertFalse(editLogoWindow.isWindowAppear());
    }


    @Story("Возврат к хэдеру страницы настроек")
    @ExtendWith(AddSomeBugsReport.class)
    @Test
    void returnToStartPage() {
        settingPage.scrollPage();
        Selenide.sleep(2000);
        assertTrue(settingPage.isReturnButtonAppear());
        settingPage.returnToStartPage();
        Selenide.sleep(2000);
        assertFalse(settingPage.isReturnButtonAppear());
    }

    @Story("Закрытие уведомления на странице настроек по таймауту")
    @Test
    void closeNotificationTimeout() {
        EditLogoWindow editLogoWindow = settingPage.openWindowEditLogo();
        editLogoWindow.uploadLogo("src/test/resources/Photo-6_8mbPng.png");
        checkCloseNotificationTimeout(basePage);

    }

    @Story("Закрытие уведомления на странице настроек")
    @Test
    void closeNotification() {
        EditLogoWindow editLogoWindow = settingPage.openWindowEditLogo();
        editLogoWindow.uploadLogo("src/test/resources/Photo-6_8mbPng.png");
        checkCloseNotification(basePage);
    }
}