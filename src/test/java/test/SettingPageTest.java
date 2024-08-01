package test;

import data.TestData;
import pages.HeaderMenu.HeaderMenu;
import pages.SettingPage.BugReport;
import pages.SettingPage.SettingPage;
import pages.SettingPage.EditLogoWindow;
import utils.preparationDataTests.setting.AddBugReportDecorator;
import utils.preparationDataTests.setting.AddDeleteBugReportDecorator;
import utils.preparationDataTests.setting.SetSAMSMU_Logo;
import utils.otherUtils.TestHelper;
import utils.dbUtils.DataBaseQuery;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static data.TestData.UserData.*;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Настройки")
@DisplayName("Страница Настройки")
public class SettingPageTest extends BaseTest {

    private SettingPage settingPage;
    private HeaderMenu headerMenu;


    @BeforeAll
    static void setUpAuth() {
        BaseTest.openAdminPanel(LOGIN_ADMIN,PASSWORD_ADMIN);
        HeaderMenu headerMenu = new HeaderMenu();
        headerMenu.clickSettingTab();
    }

    @BeforeEach
    void setUp() {
        Selenide.refresh();
        settingPage = new SettingPage();
        headerMenu = new HeaderMenu();
    }

    @AfterAll
    static void closeWebDriver() {
        Selenide.closeWebDriver();
    }

    @Feature("Сообщения об ошибках")
    @Story("Отображение баг-репорта в админ-панели после отправки пациентом")
    @DisplayName("Отображение баг-репорта в админ-панели после отправки пациентом")
    @ExtendWith(AddDeleteBugReportDecorator.class)
    @Test
    void checkBugReport() {
        BugReport bugReport =settingPage.getBugReportCard();
        bugReport.verifyBugReport();
        assertEquals(TestData.DataTest.namePatient, bugReport.getAuthorText());
        assertEquals(TestData.DataTest.email, bugReport.getEmailAuthorText());
        assertEquals(TestHelper.getCurrentDateRuYear(), bugReport.getDateText());
        assertEquals(TestData.DataTest.text, bugReport.getReportText());
        assertEquals("BUG_REPORT_CREATED_CLIENT_SUCCESS",DataBaseQuery.selectLog(USER_NAME_LK).getCode());
    }

    @Feature("Сообщения об ошибках")
    @Story("Успешное удаление баг-репорта")
    @DisplayName("Успешное удаление баг-репорта")
    @ExtendWith(AddBugReportDecorator.class)
    @Test
    void deleteBugReport() {
        BugReport bugReport =settingPage.getBugReportCard();
        bugReport.clickButtonDeleteBugReport();
        assertEquals("Сообщение удалено", settingPage.getTextNotification());
        assertFalse(settingPage.isExistsBugReport());
        assertTrue(settingPage.isExistsEmptyList());
        assertNull(DataBaseQuery.selectBugReports());
        assertEquals("BUG_REPORT_DELETED_SUCCESS",DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Настройки личного кабинета")
    @Story("Успешная замена логотипа в формате PNG")
    @DisplayName("Успешная замена логотипа в формате PNG")
    @ExtendWith(SetSAMSMU_Logo.class)
    @Test
    void changeLogo() {
        EditLogoWindow editLogoWindow = settingPage.clickButtonEditLogo();
        int oldHeightLogo = settingPage.getHeightLogo();
        editLogoWindow.verifyEditLogoWindow();
        editLogoWindow.uploadLogo("src/test/resources/visa.png");
        Selenide.Wait().until(condition -> settingPage.getHeightLogo() != oldHeightLogo);
        assertFalse(editLogoWindow.isWindowAppear());
        assertNotEquals(oldHeightLogo,settingPage.getHeightLogo());
        assertNotEquals(oldHeightLogo,headerMenu.getHeightLogo());
    }


    @Feature("Настройки личного кабинета")
    @Story("Замена логотипа в формате JPEG")
    @DisplayName("Замена логотипа в формате JPEG")
    @Test
    void changeLogoInvalidLogoFormat() {
        EditLogoWindow editLogoWindow = settingPage.clickButtonEditLogo();
        editLogoWindow.verifyEditLogoWindow();
        editLogoWindow.uploadLogo("src/test/resources/Photo 3,7mbJpeg.jpg");
        assertEquals("Неверный запрос (400)", settingPage.getTextNotification());
        assertTrue(editLogoWindow.isWindowAppear());
    }

    @Feature("Настройки личного кабинета")
    @Story("Замена логотипа весом более 4mb")
    @DisplayName("Замена логотипа весом более 4mb")
    @Test
    void changeLogoWeightMoreThan4mb() {
        EditLogoWindow editLogoWindow = settingPage.clickButtonEditLogo();
        editLogoWindow.verifyEditLogoWindow();
        editLogoWindow.uploadLogo("src/test/resources/Photo-6_8mbPng.png");
        assertEquals("Допускаются файлы размером не выше 4Мб",settingPage.getTextNotification());
        assertTrue(editLogoWindow.isWindowAppear());
    }


    @Feature("Настройки личного кабинета")
    @Story("Замена логотипа с файлом в невалидном формате")
    @DisplayName("Замена логотипа с файлом в невалидном формате")
    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/Оферта,Политика обработки docx.docx", "src/test/resources/Оферта, Политика обработки .xlsx.xlsx", "src/test/resources/Политика обработки персональных данных.pdf"})
    void changeLogoInvalidFormat(String path) {
        EditLogoWindow editLogoWindow = settingPage.clickButtonEditLogo();
        editLogoWindow.verifyEditLogoWindow();
        editLogoWindow.uploadLogo(path);
        assertEquals("Допускаются файлы с расширением jpg jpeg png",settingPage.getTextNotification());
        assertTrue(editLogoWindow.isWindowAppear());
    }

    @Feature("Настройки личного кабинета")
    @Story("Закрытие окна замены логотипа")
    @DisplayName("Закрытие окна замены логотипа")
    @Test
    void closeWindowEditLogo() {
        EditLogoWindow editLogoWindow = settingPage.clickButtonEditLogo();
        editLogoWindow.closeWindowEditLogo();
        assertFalse(editLogoWindow.isWindowAppear());
    }

}

