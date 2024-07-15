package admin.test;

import admin.data.TestData;
import admin.pages.HeaderMenu.HeaderMenu;
import admin.pages.SettingPage.BugReport;
import admin.pages.SettingPage.SettingPage;
import admin.pages.SettingPage.EditLogoWindow;
import admin.utils.preparationDataTests.general.AllureDecorator;
import admin.utils.preparationDataTests.setting.AddBugReportDecorator;
import admin.utils.preparationDataTests.setting.AddDeleteBugReportDecorator;
import admin.utils.preparationDataTests.setting.SetSAMSMU_Logo;
import admin.utils.otherUtils.DataHelper;
import admin.utils.dbUtils.DataBaseQuery;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static admin.data.TestData.UserData.*;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Настройки")
@DisplayName("Страница Настройки")
public class SettingPageTest extends BaseTest {

    private SettingPage settingPage;
    private HeaderMenu headerMenu;


    @ExtendWith(AllureDecorator.class)


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
        assertEquals(TestData.DataTest.NAME_PATIENT, bugReport.getAuthorText());
        assertEquals(TestData.DataTest.EMAIL_PATIENT, bugReport.getEmailAuthorText());
        assertEquals(DataHelper.getCurrentDateRuYear(), bugReport.getDateText());
        assertEquals(TestData.DataTest.MESSAGE_BUG_REPORT, bugReport.getReportText());
        assertEquals(DataHelper.getCurrentDateTime(),DataBaseQuery.selectLog(USER_NAME_LK,"BUG_REPORT_CREATED_CLIENT_SUCCESS").getTimeDate());
    }

    @Feature("Сообщения об ошибках")
    @Story("Успешное удаление баг-репорта")
    @DisplayName("Успешное удаление баг-репорта")
    @ExtendWith(AddBugReportDecorator.class)
    @Test
    void deleteBugReport() {
        BugReport bugReport =settingPage.getBugReportCard();
        bugReport.clickButtonDeleteBugReport();
        assertEquals("Сообщение удалено", settingPage.getNotification());
        assertFalse(settingPage.isExistsBugReport());
        assertTrue(settingPage.isExistsEmptyList());
        assertNull(DataBaseQuery.selectBugReports());
        assertEquals(DataHelper.getCurrentDateTime(),DataBaseQuery.selectLog(LOGIN_ADMIN,"BUG_REPORT_DELETED_SUCCESS").getTimeDate());
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
        Selenide.sleep(5000);
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
        assertEquals("Неверный запрос (400)", settingPage.getNotification());
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
        assertEquals("Допускаются файлы размером не выше 4Мб",settingPage.getNotification());
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
        assertEquals("Допускаются файлы с расширением jpg jpeg png",settingPage.getNotification());
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

