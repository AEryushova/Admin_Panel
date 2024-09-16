package ru.adminlk.clinica.samsmu.test;

import ru.adminlk.clinica.samsmu.pages.SettingPage.BugReport;
import ru.adminlk.clinica.samsmu.pages.SettingPage.EditLogoWindow;
import ru.adminlk.clinica.samsmu.utils.preparationData.setting.AddBugReport;
import ru.adminlk.clinica.samsmu.utils.preparationData.setting.AddDeleteBugReport;
import ru.adminlk.clinica.samsmu.utils.preparationData.setting.SetSAMSMU_Logo;
import ru.adminlk.clinica.samsmu.utils.dbUtils.DataBaseQuery;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static ru.adminlk.clinica.samsmu.data.TestData.DataTest.text;
import static ru.adminlk.clinica.samsmu.data.TestData.DataTest.email;
import static ru.adminlk.clinica.samsmu.data.TestData.DataTest.namePatient;
import static ru.adminlk.clinica.samsmu.data.TestData.UserData.*;
import static org.junit.jupiter.api.Assertions.*;
import static ru.adminlk.clinica.samsmu.utils.testsUtils.TestHelper.getDate;

@Epic("Настройки")
@DisplayName("Страница Настройки")
public class SettingPageTest extends BaseTest {

    @BeforeAll
    static void setUpAuth() {
        getAuthToken(LOGIN_ADMIN, PASSWORD_ADMIN);
    }

    @BeforeEach
    void setUp() {
        openAuthAdminPanel();
        headerMenu.clickSettingTab();
        settingPage.verifySettingPage();
    }

    @AfterEach()
    void closeWebDriver() {
        closeDriver();
    }


    @Feature("Сообщения об ошибках")
    @Story("Отображение баг-репорта в админ-панели после отправки пациентом")
    @DisplayName("Отображение баг-репорта в админ-панели после отправки пациентом")
    @ExtendWith(AddDeleteBugReport.class)
    @Test
    void checkBugReport() {
        BugReport bugReport = settingPage.getBugReportCard();
        bugReport.verifyBugReport();
        assertEquals(namePatient, bugReport.getAuthorText());
        assertEquals(email, bugReport.getEmailAuthorText());
        assertEquals(text, bugReport.getReportText());
        assertEquals(getDate("current",0,0,"d MMMM yyyy 'г.'"), bugReport.getDateText());
    }

    @Feature("Сообщения об ошибках")
    @Story("Успешное удаление баг-репорта")
    @DisplayName("Успешное удаление баг-репорта")
    @ExtendWith(AddBugReport.class)
    @Test
    void deleteBugReport() {
        BugReport bugReport = settingPage.getBugReportCard();
        bugReport.clickButtonDeleteBugReport();
        assertEquals("Сообщение удалено", settingPage.getTextNotification());
        assertFalse(settingPage.isExistsBugReport());
        assertTrue(settingPage.isExistsEmptyList());
        assertNull(DataBaseQuery.selectBugReports());
        assertEquals("BUG_REPORT_DELETED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Настройки личного кабинета")
    @Story("Успешная замена логотипа в формате PNG")
    @DisplayName("Успешная замена логотипа в формате PNG")
    @ExtendWith(SetSAMSMU_Logo.class)
    @Test
    void changeLogo() {
        EditLogoWindow editLogoWindow = settingPage.clickButtonEditLogo();
        int oldHeightLogo = settingPage.getHeightLogo();
        editLogoWindow.verifyEditLogoWindow()
                .uploadLogo("src/test/resources/images/visa.png");
        Selenide.Wait().until(condition -> settingPage.getHeightLogo() != oldHeightLogo);
        assertNotEquals(oldHeightLogo, settingPage.getHeightLogo());
        assertNotEquals(oldHeightLogo, headerMenu.getHeightLogo());
    }

    @Feature("Настройки личного кабинета")
    @Story("Замена логотипа в формате JPEG")
    @DisplayName("Замена логотипа в формате JPEG")
    @Test
    void changeLogoInvalidLogoFormat() {
        EditLogoWindow editLogoWindow = settingPage.clickButtonEditLogo();
        int oldHeightLogo = settingPage.getHeightLogo();
        editLogoWindow.verifyEditLogoWindow()
                .uploadLogo("src/test/resources/images/Photo 3,7mbJpeg.jpg");
        assertEquals("Неверный запрос (400)", settingPage.getTextNotification());
        assertEquals(oldHeightLogo, settingPage.getHeightLogo());
    }

    @Feature("Настройки личного кабинета")
    @Story("Замена логотипа весом более 4mb")
    @DisplayName("Замена логотипа весом более 4mb")
    @Test
    void changeLogoWeightMoreThan4mb() {
        EditLogoWindow editLogoWindow = settingPage.clickButtonEditLogo();
        int oldHeightLogo = settingPage.getHeightLogo();
        editLogoWindow.verifyEditLogoWindow()
                .uploadLogo("src/test/resources/images/Photo-6_8mbPng.png");
        assertEquals("Допускаются файлы размером не выше 4Мб", settingPage.getTextNotification());
        assertEquals(oldHeightLogo, settingPage.getHeightLogo());
    }

    @Feature("Настройки личного кабинета")
    @Story("Замена логотипа с файлом в невалидном формате")
    @DisplayName("Замена логотипа с файлом в невалидном формате")
    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/files/Оферта,Политика обработки docx.docx", "src/test/resources/files/Оферта, Политика обработки .xlsx.xlsx", "src/test/resources/files/Политика обработки персональных данных.pdf"})
    void changeLogoInvalidFormat(String path) {
        EditLogoWindow editLogoWindow = settingPage.clickButtonEditLogo();
        int oldHeightLogo = settingPage.getHeightLogo();
        editLogoWindow.verifyEditLogoWindow()
                .uploadLogo(path);
        assertEquals("Допускаются файлы с расширением jpg jpeg png", settingPage.getTextNotification());
        assertEquals(oldHeightLogo, settingPage.getHeightLogo());
    }

    @Feature("Настройки личного кабинета")
    @Story("Закрытие окна замены логотипа")
    @DisplayName("Закрытие окна замены логотипа")
    @Test
    void closeWindowEditLogo() {
        EditLogoWindow editLogoWindow = settingPage.clickButtonEditLogo();
        editLogoWindow.verifyEditLogoWindow()
                .closeWindowEditLogo();
        assertFalse(editLogoWindow.isWindowAppear());
    }
}

