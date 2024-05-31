package admin.test;

import admin.data.DataTest;
import admin.pages.*;
import admin.pages.calendar.Calendar;
import admin.utils.*;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import admin.pages.modalWindowAdministration.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Epic("Администрирование")
public class AdministrationPageTest {

    @BeforeAll
    static void setUpAllAllure() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeAll
    static void setupAdminPanelAuth() {
        TestSetupAuthAdminPanel.authAdminPanel(DataTest.getLoginSuperAdmin(), DataTest.getPasswordSuperAdmin());
    }

    @BeforeEach
    void openAdministrationPage() {
        TestSetupAuthAdminPanel.openAdministrationPage();
    }

    @Feature("Переключение между вкладками")
    @Story("Переход на вкладку докторов")
    @Test
    void openDoctorsPage() {
        HeaderBar headerBar = new HeaderBar();
        headerBar.headerBarSuperAdmin();
        DoctorsPage doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
    }

    @Feature("Переключение между вкладками")
    @Story("Переход на вкладку услуг")
    @Test
    void openServicesPage() {
        HeaderBar headerBar = new HeaderBar();
        headerBar.headerBarSuperAdmin();
        ServicesPage servicesPage = headerBar.servicesTabOpen();
        servicesPage.servicesPage();
    }

    @Feature("Переключение между вкладками")
    @Story("Переход на вкладку faq")
    @Test
    void openFaqPage() {
        HeaderBar headerBar = new HeaderBar();
        headerBar.headerBarSuperAdmin();
        FaqPage faqPage = headerBar.faqTabOpen();
        faqPage.faqPage();
    }

    @Feature("Переключение между вкладками")
    @Story("Переход на вкладку настроек")
    @Test
    void openSettingPage() {
        HeaderBar headerBar = new HeaderBar();
        headerBar.headerBarSuperAdmin();
        SettingPage settingPage = headerBar.settingTabOpen();
        settingPage.settingPage();
    }

    @Feature("Переключение между вкладками")
    @Story("Сохранение состояния страницы при клике по вкладке администрирования")
    @Test
    void clickAdministrationPage() {
        HeaderBar headerBar = new HeaderBar();
        headerBar.headerBarSuperAdmin();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.adminPage();
    }

    @Feature("Добавление нового админа")
    @Story("Успешное добавление нового админа")
    @Test
    void addedNewAdmin_8613() {
        AdministrationPage adminPage = new AdministrationPage();
        adminPage.getNotExistAdminCard();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillFieldNewAdminLogin(DataTest.getLoginAdminTest());
        newAdminWindow.fillFieldNewAdminPassword(DataTest.getPasswordAdminTest());
        newAdminWindow.fillFieldNewAdminConfirmPassword(DataTest.getPasswordAdminTest());
        newAdminWindow.clickAddButton();
        adminPage.getAdminCard();
        assertEquals("Новый администратор " + DataTest.getLoginAdminTest() + " успешно создан", adminPage.getNotification());
        assertEquals("1", DataBaseUtils.selectAdmin(DataTest.getLoginAdminTest()).getRole_id());
        adminPage.closeNotification();
        TestSetupAPI.deleteAdminCookie(DataTest.getLoginAdminTest());
    }

    @Feature("Добавление нового админа")
    @Story("Добавление нового админа с уже существующим логином")
    @Test
    void addedNewAdminAlreadyExisting_9334() {
        TestSetupAPI.createAdminCookie(DataTest.getLoginAdminTest(), DataTest.getPasswordAdminTest());
        AdministrationPage adminPage = new AdministrationPage();
        adminPage.getAdminCard();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillFieldNewAdminLogin(DataTest.getLoginAdminTest());
        newAdminWindow.fillFieldNewAdminPassword(DataTest.getPasswordAdminTest());
        newAdminWindow.fillFieldNewAdminConfirmPassword(DataTest.getPasswordAdminTest());
        newAdminWindow.clickAddButton();
        assertEquals("{\"error\":\"Пользователь уже существует, логин: " + DataTest.getLoginAdminTest() + "\",\"innerError\":null,\"exception\":\"AlreadyExistException\"}", adminPage.getNotification());
        adminPage.closeNotification();
        TestSetupAPI.deleteAdminCookie(DataTest.getLoginAdminTest());
    }

    @Feature("Добавление нового админа")
    @Story("Добавление нового администраторас пустым полем логина")
    @Test
    void addedNewAdminEmptyFieldsLogin_8670() {
        AdministrationPage adminPage = new AdministrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillFieldNewAdminPassword(DataTest.getPasswordAdminTest());
        newAdminWindow.fillFieldNewAdminConfirmPassword(DataTest.getPasswordAdminTest());
        newAdminWindow.newAdminWindow();
    }

    @Feature("Добавление нового админа")
    @Story("Добавление нового администратора с пустым полем пароля")
    @Test
    void addedNewAdminEmptyFieldsPassword_8671() {
        AdministrationPage adminPage = new AdministrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillFieldNewAdminLogin(DataTest.getLoginAdminTest());
        newAdminWindow.fillFieldNewAdminConfirmPassword(DataTest.getPasswordAdminTest());
        newAdminWindow.newAdminWindow();
        assertEquals("Не соответствует паролю", newAdminWindow.getErrorFieldConfirmPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Добавление нового администратора с пустым полем подтверждения пароля")
    @Test
    void addedNewAdminEmptyFieldsConfirmPassword_8672() {
        AdministrationPage adminPage = new AdministrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillFieldNewAdminLogin(DataTest.getLoginAdminTest());
        newAdminWindow.fillFieldNewAdminPassword(DataTest.getPasswordAdminTest());
        newAdminWindow.newAdminWindow();
    }


    @Feature("Добавление нового админа")
    @Story("Добавление нового администратора с пустым полем логина и пароля")
    @Test
    void addedNewAdminEmptyFieldsLoginPassword_8674() {
        AdministrationPage adminPage = new AdministrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillFieldNewAdminConfirmPassword(DataTest.getPasswordAdminTest());
        newAdminWindow.newAdminWindow();
        assertEquals("Не соответствует паролю", newAdminWindow.getErrorFieldConfirmPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Добавление нового администратора с пустым полем логина и подтверждения пароля")
    @Test
    void addedNewAdminEmptyFieldsLoginConfirmPassword_8675() {
        AdministrationPage adminPage = new AdministrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillFieldNewAdminPassword(DataTest.getPasswordAdminTest());
        newAdminWindow.newAdminWindow();
    }

    @Feature("Добавление нового админа")
    @Story("Добавление нового администратора с пустым полем пароля и подтверждения пароля")
    @Test
    void addedNewAdminEmptyFieldsPasswordConfirmPassword_8676() {
        AdministrationPage adminPage = new AdministrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillFieldNewAdminLogin(DataTest.getLoginAdminTest());
        newAdminWindow.newAdminWindow();
    }

    @Feature("Добавление нового админа")
    @Story("Добавление нового администратора с невалидными значениями")
    @Test
    void addedNewAdminInvalidValue_8688() {
        AdministrationPage adminPage = new AdministrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillFieldNewAdminLogin("АННА_ТЕСТ");
        newAdminWindow.fillFieldNewAdminPassword("WwSs12345");
        newAdminWindow.fillFieldNewAdminConfirmPassword("WwSs12345#");
        newAdminWindow.newAdminWindow();
        assertEquals("Первый символ должен быть латинской буквой или \"_\"", newAdminWindow.getErrorFieldLogin());
        assertEquals("Пароль не валиден", newAdminWindow.getErrorFieldPassword());
        assertEquals("Не соответствует паролю", newAdminWindow.getErrorFieldConfirmPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Отображение уведомления об обязательности полей")
    @Test
    void addedNewAdminObligatoryFields_8681() {
        AdministrationPage adminPage = new AdministrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.clickFieldNewAdminLogin();
        newAdminWindow.clickFieldNewAdminPassword();
        newAdminWindow.clickFieldNewAdminConfirmPassword();
        newAdminWindow.newAdminWindow();
        assertEquals("Обязательное поле", newAdminWindow.getErrorFieldLogin());
        assertEquals("Обязательное поле", newAdminWindow.getErrorFieldPassword());
        assertEquals("Обязательное поле", newAdminWindow.getErrorFieldConfirmPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод валидного логина из 31 символа")
    @Test
    void addedNewAdminLogin31Symbol() {
        AdministrationPage adminPage = new AdministrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillFieldNewAdminLogin("ANNA_TEST_ADMIN123456789_ANNA_1");
        newAdminWindow.newAdminWindow();
        assertFalse(newAdminWindow.isErrorLoginAppear());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод валидного логина из 32 символов")
    @Test
    void addedNewAdminLogin32Symbol() {
        AdministrationPage adminPage = new AdministrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillFieldNewAdminLogin("ANNA_TEST_ADMIN123456789_ANNA_12");
        newAdminWindow.newAdminWindow();
        assertFalse(newAdminWindow.isErrorLoginAppear());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не валидного логина из 33 символов")
    @Test
    void addedNewAdminLogin33Symbol() {
        AdministrationPage adminPage = new AdministrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillFieldNewAdminLogin("ANNA_TEST_ADMIN123456789_ANNA_123");
        newAdminWindow.newAdminWindow();
        assertEquals("Максимальная длина 32 символа", newAdminWindow.getErrorFieldLogin());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не валидного логина на кириллице")
    @Test
    void addedNewAdminLoginCyrillicValue() {
        AdministrationPage adminPage = new AdministrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillFieldNewAdminLogin("АННА_ТЕСТ");
        newAdminWindow.newAdminWindow();
        assertEquals("Первый символ должен быть латинской буквой или \"_\"", newAdminWindow.getErrorFieldLogin());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не валидного логина, начинающегося с цифры")
    @Test
    void addedNewAdminLoginNumberBegin() {
        AdministrationPage adminPage = new AdministrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillFieldNewAdminLogin("1ANNA_TEST");
        newAdminWindow.newAdminWindow();
        assertEquals("Первый символ должен быть латинской буквой или \"_\"", newAdminWindow.getErrorFieldLogin());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не валидного логина, начинающегося с латиницы, далее на кириллице")
    @Test
    void addedNewAdminLoginLatinBeginCyrillicValue() {
        AdministrationPage adminPage = new AdministrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillFieldNewAdminLogin("AННА_ТЕСТ");
        newAdminWindow.newAdminWindow();
        assertEquals("Доступны только числа, латиница и \"_\"", newAdminWindow.getErrorFieldLogin());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не валидного логина с пробелом")
    @Test
    void addedNewAdminLoginWithSpace() {
        AdministrationPage adminPage = new AdministrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillFieldNewAdminLogin("ANNA TEST");
        newAdminWindow.newAdminWindow();
        assertEquals("Доступны только числа, латиница и \"_\"", newAdminWindow.getErrorFieldLogin());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не валидного пароля из 7 символов")
    @Test
    void addedNewAdminPassword7Symbol() {
        AdministrationPage adminPage = new AdministrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillFieldNewAdminPassword("Wwqq12#");
        newAdminWindow.newAdminWindow();
        assertEquals("Пароль не валиден", newAdminWindow.getErrorFieldPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод валидного пароля из 8 символов")
    @Test
    void addedNewAdminPassword8Symbol() {
        AdministrationPage adminPage = new AdministrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillFieldNewAdminPassword("WwSs145#");
        newAdminWindow.newAdminWindow();
        assertFalse(newAdminWindow.isErrorPasswordAppear());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод валидного пароля из 9 символов")
    @Test
    void addedNewAdminPassword9Symbol() {
        AdministrationPage adminPage = new AdministrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillFieldNewAdminPassword("WwSs1234#");
        newAdminWindow.newAdminWindow();
        assertFalse(newAdminWindow.isErrorPasswordAppear());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод валидного пароля из 24 символов")
    @Test
    void addedNewAdminPassword24Symbol() {
        AdministrationPage adminPage = new AdministrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillFieldNewAdminPassword("Wwqq123456789#QQgg123456");
        newAdminWindow.newAdminWindow();
        assertFalse(newAdminWindow.isErrorPasswordAppear());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод валидного пароля из 25 символов")
    @Test
    void addedNewAdminPassword25Symbol() {
        AdministrationPage adminPage = new AdministrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillFieldNewAdminPassword("Wwqq123456789#QQgg1234567");
        newAdminWindow.newAdminWindow();
        assertFalse(newAdminWindow.isErrorPasswordAppear());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не валидного пароля из 26 символов")
    @Test
    void addedNewAdminPassword26Symbol() {
        AdministrationPage adminPage = new AdministrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillFieldNewAdminPassword("Wwqq123456789#QQgg12345678");
        newAdminWindow.newAdminWindow();
        assertEquals("Пароль не валиден", newAdminWindow.getErrorFieldPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не валидного пароля без латинской буквы")
    @Test
    void addedNewAdminPasswordNotLatinValue() {
        AdministrationPage adminPage = new AdministrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillFieldNewAdminPassword("123456789!");
        newAdminWindow.newAdminWindow();
        assertEquals("Пароль не валиден", newAdminWindow.getErrorFieldPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не валидного пароля без спецсимвола")
    @Test
    void addedNewAdminPasswordNotSymbol() {
        AdministrationPage adminPage = new AdministrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillFieldNewAdminPassword("Wwqq1234567");
        newAdminWindow.newAdminWindow();
        assertEquals("Пароль не валиден", newAdminWindow.getErrorFieldPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не валидного пароля без латинской буквы в нижнем регистре")
    @Test
    void addedNewAdminPasswordNotToLowerCase() {
        AdministrationPage adminPage = new AdministrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillFieldNewAdminPassword("WWQQ123456#");
        newAdminWindow.newAdminWindow();
        assertEquals("Пароль не валиден", newAdminWindow.getErrorFieldPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не валидного пароля без латинской буквы в верхнем регистре")
    @Test
    void addedNewAdminPasswordNotToUpperCase() {
        AdministrationPage adminPage = new AdministrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillFieldNewAdminPassword("wwqq123456#");
        newAdminWindow.newAdminWindow();
        assertEquals("Пароль не валиден", newAdminWindow.getErrorFieldPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не валидного пароля без цифры")
    @Test
    void addedNewAdminPasswordNotNumber() {
        AdministrationPage adminPage = new AdministrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillFieldNewAdminPassword("Wwqqwq#@#@");
        newAdminWindow.newAdminWindow();
        assertEquals("Пароль не валиден", newAdminWindow.getErrorFieldPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не валидного пароля на кириллице")
    @Test
    void addedNewAdminPasswordCyrillicValue() {
        AdministrationPage adminPage = new AdministrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillFieldNewAdminPassword("Ффыы123456#");
        newAdminWindow.newAdminWindow();
        assertEquals("Пароль не валиден", newAdminWindow.getErrorFieldPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не валидного пароля с пробелом")
    @Test
    void addedNewAdminPasswordWithSpace() {
        AdministrationPage adminPage = new AdministrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillFieldNewAdminPassword("Wwqq 123456 #");
        newAdminWindow.newAdminWindow();
        assertEquals("Пароль не валиден", newAdminWindow.getErrorFieldPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не соответствующего пароля при подтверждении")
    @Test
    void addedNewAdminMismatchedPasswords_8680() {
        AdministrationPage adminPage = new AdministrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillFieldNewAdminLogin(DataTest.getLoginAdminTest());
        newAdminWindow.fillFieldNewAdminPassword(DataTest.getPasswordAdminTest());
        newAdminWindow.fillFieldNewAdminConfirmPassword("WwSs125#");
        newAdminWindow.newAdminWindow();
        assertEquals("Не соответствует паролю", newAdminWindow.getErrorFieldConfirmPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Очистка полей через кнопку в окне добавления админа")
    @Test
    void clearFieldsThroughButtonClear() {
        AdministrationPage adminPage = new AdministrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillFieldNewAdminLogin(DataTest.getLoginAdminTest());
        newAdminWindow.clearButtonLoginField();
        newAdminWindow.fillFieldNewAdminPassword(DataTest.getPasswordAdminTest());
        newAdminWindow.clearButtonPasswordField();
        newAdminWindow.fillFieldNewAdminConfirmPassword(DataTest.getPasswordAdminTest());
        newAdminWindow.clearButtonConfirmPasswordField();
        newAdminWindow.newAdminWindow();
        assertEquals("", newAdminWindow.getValueLoginField());
        assertEquals("Обязательное поле", newAdminWindow.getErrorFieldLogin());
        assertEquals("", newAdminWindow.getValuePasswordField());
        assertEquals("Обязательное поле", newAdminWindow.getErrorFieldPassword());
        assertEquals("", newAdminWindow.getValueConfirmPasswordField());
        assertEquals("Обязательное поле", newAdminWindow.getErrorFieldConfirmPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Зануление полей в окне добавления админа после закрытия окна")
    @Test
    void closeWindowAddedNewAdmin_8651() {
        AdministrationPage adminPage = new AdministrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillFieldNewAdminLogin(DataTest.getLoginAdminTest());
        newAdminWindow.fillFieldNewAdminPassword(DataTest.getPasswordAdminTest());
        newAdminWindow.fillFieldNewAdminConfirmPassword(DataTest.getPasswordAdminTest());
        newAdminWindow.closeWindowAddedAdmin();
        assertFalse(newAdminWindow.isWindowAppear());
        NewAdminWindow newAdminWindowOpen = adminPage.openWindowAddedNewAdmin();
        newAdminWindowOpen.newAdminWindow();
        assertEquals("", newAdminWindowOpen.getValueLoginField());
        assertEquals("", newAdminWindowOpen.getValuePasswordField());
        assertEquals("", newAdminWindowOpen.getValueConfirmPasswordField());
    }

    @Feature("Смена пароля админу")
    @Story("Успешная смена пароля админу")
    @Test
    void changePasswordAdmin_8617() {
        TestSetupAPI.createAdminCookie(DataTest.getLoginAdminTest(), DataTest.getPasswordAdminTest());
        AdministrationPage adminPage = new AdministrationPage();
        adminPage.getAdminCard();
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.changePasswordAdminWindow();
        changePasswordAdminWindow.fillFieldNewPassword(DataTest.getNewPasswordAdminTest());
        changePasswordAdminWindow.fillFieldConfirmPassword(DataTest.getNewPasswordAdminTest());
        changePasswordAdminWindow.clickSaveNewPasswordButton();
        assertFalse(changePasswordAdminWindow.isWindowAppear());
        assertEquals("Админ" + DataTest.getLoginAdminTest() + "успешно изменен", adminPage.getNotification());
        adminPage.closeNotification();
        TestSetupAPI.deleteAdminCookie(DataTest.getLoginAdminTest());
    }

    @Feature("Смена пароля админу")
    @Story("Смена пароля админу с пустым полем пароля")
    @Test
    void changePasswordAdminEmptyFieldsPassword_8879() {
        AdministrationPage adminPage = new AdministrationPage();
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.changePasswordAdminWindow();
        changePasswordAdminWindow.fillFieldConfirmPassword(DataTest.getNewPasswordAdminTest());
        changePasswordAdminWindow.changePasswordAdminWindow();
        assertEquals("Не соответствует паролю", changePasswordAdminWindow.getErrorFieldConfirmPassword());
    }

    @Feature("Смена пароля админу")
    @Story("Смена пароля админу с пустым полем подтверждения пароля")
    @Test
    void changePasswordAdminEmptyFieldsConfirmPassword_8618() {
        AdministrationPage adminPage = new AdministrationPage();
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.fillFieldNewPassword(DataTest.getNewPasswordAdminTest());
        changePasswordAdminWindow.changePasswordAdminWindow();
    }

    @Feature("Смена пароля админу")
    @Story("Отображение уведомления об обязательности полей")
    @Test
    void changePasswordAdminObligatoryFields_() {
        AdministrationPage adminPage = new AdministrationPage();
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.changePasswordAdminWindow();
        changePasswordAdminWindow.clickFieldNewPassword();
        changePasswordAdminWindow.clickFieldConfirmPassword();
        changePasswordAdminWindow.changePasswordAdminWindow();
        assertEquals("Обязательное поле", changePasswordAdminWindow.getErrorFieldPassword());
        assertEquals("Обязательное поле", changePasswordAdminWindow.getErrorFieldConfirmPassword());
    }

    @Feature("Смена пароля админу")
    @Story("Ввод не соответствующего пароля при подтверждении")
    @Test
    void changePasswordAdminEmptyFieldsPassword_12080() {
        AdministrationPage adminPage = new AdministrationPage();
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.changePasswordAdminWindow();
        changePasswordAdminWindow.clickFieldNewPassword();
        changePasswordAdminWindow.fillFieldNewPassword(DataTest.getNewPasswordAdminTest());
        changePasswordAdminWindow.fillFieldConfirmPassword(DataTest.getPasswordAdminTest());
        changePasswordAdminWindow.clickFieldNewPassword();
        changePasswordAdminWindow.changePasswordAdminWindow();
        assertEquals("Не соответствует паролю", changePasswordAdminWindow.getErrorFieldConfirmPassword());
    }

    @Feature("Смена пароля админу")
    @Story("Ввод не валидного пароля из 7 символов")
    @Test
    void changePasswordAdmin7Symbol() {
        AdministrationPage adminPage = new AdministrationPage();
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.changePasswordAdminWindow();
        changePasswordAdminWindow.fillFieldNewPassword("Wwqq12#");
        changePasswordAdminWindow.changePasswordAdminWindow();
        assertEquals("Пароль не валиден", changePasswordAdminWindow.getErrorFieldPassword());
    }

    @Feature("Смена пароля админу")
    @Story("Ввод валидного пароля из 8 символов")
    @Test
    void changePasswordAdmin8Symbol() {
        AdministrationPage adminPage = new AdministrationPage();
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.changePasswordAdminWindow();
        changePasswordAdminWindow.fillFieldNewPassword("Wwqq123#");
        changePasswordAdminWindow.changePasswordAdminWindow();
        assertFalse(changePasswordAdminWindow.isErrorPasswordAppear());
    }

    @Feature("Смена пароля админу")
    @Story("Ввод валидного пароля из 9 символов")
    @Test
    void changePasswordAdmin9Symbol() {
        AdministrationPage adminPage = new AdministrationPage();
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.changePasswordAdminWindow();
        changePasswordAdminWindow.fillFieldNewPassword("Wwqq1234#");
        changePasswordAdminWindow.changePasswordAdminWindow();
        assertFalse(changePasswordAdminWindow.isErrorPasswordAppear());
    }

    @Feature("Смена пароля админу")
    @Story("Ввод валидного пароля из 24 символов")
    @Test
    void changePasswordAdmin24Symbol() {
        AdministrationPage adminPage = new AdministrationPage();
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.changePasswordAdminWindow();
        changePasswordAdminWindow.fillFieldNewPassword("Wwqq123456789#QQgg123456");
        changePasswordAdminWindow.changePasswordAdminWindow();
        assertFalse(changePasswordAdminWindow.isErrorPasswordAppear());
    }


    @Feature("Смена пароля админу")
    @Story("Ввод валидного пароля из 25 символов")
    @Test
    void changePasswordAdmin25Symbol() {
        AdministrationPage adminPage = new AdministrationPage();
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.changePasswordAdminWindow();
        changePasswordAdminWindow.fillFieldNewPassword("Wwqq123456789#QQgg1234567");
        changePasswordAdminWindow.changePasswordAdminWindow();
        assertFalse(changePasswordAdminWindow.isErrorPasswordAppear());
    }

    @Feature("Смена пароля админу")
    @Story("Ввод не валидного пароля из 26 символов")
    @Test
    void changePasswordAdmin26Symbol() {
        AdministrationPage adminPage = new AdministrationPage();
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.changePasswordAdminWindow();
        changePasswordAdminWindow.fillFieldNewPassword("Wwqq123456789#QQgg12345678");
        changePasswordAdminWindow.changePasswordAdminWindow();
        assertEquals("Пароль не валиден", changePasswordAdminWindow.getErrorFieldPassword());
    }

    @Feature("Смена пароля админу")
    @Story("Ввод не валидного пароля без латинской буквы в верхнем регистре")
    @Test
    void changePasswordAdminNotToUpperCase() {
        AdministrationPage adminPage = new AdministrationPage();
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.changePasswordAdminWindow();
        changePasswordAdminWindow.fillFieldNewPassword("wwqq123456#");
        changePasswordAdminWindow.changePasswordAdminWindow();
        assertEquals("Пароль не валиден", changePasswordAdminWindow.getErrorFieldPassword());
    }

    @Feature("Смена пароля админу")
    @Story("Ввод не валидного пароля без латинской буквы в нижнем регистре")
    @Test
    void changePasswordAdminNotToLowerCase() {
        AdministrationPage adminPage = new AdministrationPage();
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.changePasswordAdminWindow();
        changePasswordAdminWindow.fillFieldNewPassword("WWQQ123456#");
        changePasswordAdminWindow.changePasswordAdminWindow();
        assertEquals("Пароль не валиден", changePasswordAdminWindow.getErrorFieldPassword());
    }

    @Feature("Смена пароля админу")
    @Story("Ввод не валидного пароля на кириллице")
    @Test
    void changePasswordAdminCyrillicValue() {
        AdministrationPage adminPage = new AdministrationPage();
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.changePasswordAdminWindow();
        changePasswordAdminWindow.fillFieldNewPassword("Ффыы123456#");
        changePasswordAdminWindow.changePasswordAdminWindow();
        assertEquals("Пароль не валиден", changePasswordAdminWindow.getErrorFieldPassword());
    }

    @Feature("Смена пароля админу")
    @Story("Ввод не валидного пароля без цифры")
    @Test
    void changePasswordAdminNotNumber() {
        AdministrationPage adminPage = new AdministrationPage();
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.changePasswordAdminWindow();
        changePasswordAdminWindow.fillFieldNewPassword("Wwqqwq#@#@");
        changePasswordAdminWindow.changePasswordAdminWindow();
        assertEquals("Пароль не валиден", changePasswordAdminWindow.getErrorFieldPassword());
    }

    @Feature("Смена пароля админу")
    @Story("Ввод не валидного пароля без спецсимвола")
    @Test
    void changePasswordAdminNotSymbol() {
        AdministrationPage adminPage = new AdministrationPage();
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.changePasswordAdminWindow();
        changePasswordAdminWindow.fillFieldNewPassword("Wwqq1234567");
        changePasswordAdminWindow.changePasswordAdminWindow();
        assertEquals("Пароль не валиден", changePasswordAdminWindow.getErrorFieldPassword());
    }

    @Feature("Смена пароля админу")
    @Story("Ввод не валидного пароля без латинской буквы")
    @Test
    void changePasswordAdminNotLatinValue() {
        AdministrationPage adminPage = new AdministrationPage();
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.changePasswordAdminWindow();
        changePasswordAdminWindow.fillFieldNewPassword("123456789!");
        changePasswordAdminWindow.changePasswordAdminWindow();
        assertEquals("Пароль не валиден", changePasswordAdminWindow.getErrorFieldPassword());
    }

    @Feature("Смена пароля админу")
    @Story("Ввод не валидного пароля с пробелом")
    @Test
    void changePasswordAdminWithSpace() {
        AdministrationPage adminPage = new AdministrationPage();
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.changePasswordAdminWindow();
        changePasswordAdminWindow.fillFieldNewPassword("Wwqq 123456 #");
        changePasswordAdminWindow.changePasswordAdminWindow();
        assertEquals("Пароль не валиден", changePasswordAdminWindow.getErrorFieldPassword());
    }

    @Feature("Смена пароля админу")
    @Story("Зануление полей в окне смены пароля админу после закрытия окна")
    @Test
    void closeWindowChangePasswordAdmin_8880() {
        AdministrationPage adminPage = new AdministrationPage();
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.changePasswordAdminWindow();
        changePasswordAdminWindow.fillFieldNewPassword(DataTest.getNewPasswordAdminTest());
        changePasswordAdminWindow.fillFieldConfirmPassword(DataTest.getNewPasswordAdminTest());
        changePasswordAdminWindow.closeWindowChangePasswordAdmin();
        assertFalse(changePasswordAdminWindow.isWindowAppear());
        ChangePasswordAdminWindow changePasswordAdminWindowOpen = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindowOpen.changePasswordAdminWindow();
        assertEquals("", changePasswordAdminWindowOpen.getValuePasswordField());
        assertEquals("", changePasswordAdminWindowOpen.getValueConfirmPasswordField());

    }

    @Feature("Удаление админа")
    @Story("Отмена удаления админа")
    @Test
    void cancelDeleteAdmin() {
        TestSetupAPI.createAdminCookie(DataTest.getLoginAdminTest(), DataTest.getPasswordAdminTest());
        AdministrationPage adminPage = new AdministrationPage();
        adminPage.getAdminCard();
        DeleteAdminWindow deleteAdminWindow = adminPage.openWindowDeleteAdmin();
        deleteAdminWindow.deleteAdminWindow();
        deleteAdminWindow.cancelDeleteAdmin();
        adminPage.getAdminCard();
    }

    @Feature("Удаление админа")
    @Story("Успешное удаление админа")
    @Test
    void deleteAdmin() {
        TestSetupAPI.createAdminCookie(DataTest.getLoginAdminTest(), DataTest.getPasswordAdminTest());
        AdministrationPage adminPage = new AdministrationPage();
        adminPage.getAdminCard();
        DeleteAdminWindow deleteAdminWindow = adminPage.openWindowDeleteAdmin();
        deleteAdminWindow.deleteAdminWindow();
        deleteAdminWindow.deleteAdmin();
        adminPage.getNotExistAdminCard();
        assertEquals("Админ" + DataTest.getLoginAdminTest() + "успешно удален", adminPage.getNotification());
        adminPage.closeNotification();
        TestSetupAPI.deleteAdminCookie(DataTest.getLoginAdminTest());
    }

    @Feature("Документация")
    @Story("Успешное обновление оферты")
    @Test
    void updateOffer_11737() {
        AdministrationPage adminPage = new AdministrationPage();
        UpdateLegalDocWindow updateLegalDocWindow = adminPage.updateOffer();
        updateLegalDocWindow.uploadDocWindow();
        String srcDoc = updateLegalDocWindow.getSrcDoc();
        updateLegalDocWindow.uploadValidDoc("src/test/resources/Оферта.pdf");
        UpdateLegalDocWindow updateLegalDocWindowOver = adminPage.updateOffer();
        assertEquals(srcDoc, updateLegalDocWindowOver.getSrcDoc());
    }

    @Feature("Документация")
    @Story("Успешное обновление политики обработки")
    @Test
    void updateProcessingPolicy_11739() {
        AdministrationPage adminPage = new AdministrationPage();
        UpdateLegalDocWindow updateLegalDocWindow = adminPage.updateProcessingPolicy();
        updateLegalDocWindow.uploadDocWindow();
        String srcDoc = updateLegalDocWindow.getSrcDoc();
        updateLegalDocWindow.uploadValidDoc("src/test/resources/Политика обработки персональных данных.pdf");
        UpdateLegalDocWindow updateLegalDocWindowOver = adminPage.updateProcessingPolicy();
        assertEquals(srcDoc, updateLegalDocWindowOver.getSrcDoc());
    }

    @Feature("Документация")
    @Story("Закрытие окна обновления оферты")
    @Test
    void closeWindowUpdateOffer_11740() {
        AdministrationPage adminPage = new AdministrationPage();
        UpdateLegalDocWindow updateLegalDocWindow = adminPage.updateOffer();
        updateLegalDocWindow.uploadDocWindow();
        updateLegalDocWindow.closeWindowUpdateLegalDoc();
        assertFalse(updateLegalDocWindow.isWindowAppear());
    }

    @Feature("Документация")
    @Story("Закрытие окна обновления политики обработки")
    @Test
    void closeWindowUpdateProcessingPolicy_11742() {
        AdministrationPage adminPage = new AdministrationPage();
        UpdateLegalDocWindow updateLegalDocWindow = adminPage.updateProcessingPolicy();
        updateLegalDocWindow.uploadDocWindow();
        updateLegalDocWindow.closeWindowUpdateLegalDoc();
        assertFalse(updateLegalDocWindow.isWindowAppear());
    }


    @Feature("Документация")
    @Story("Успешное обновление приказа с даты в текущем месяце")
    @Test
    void updateOrderCurrentMonth_11278() {
        AdministrationPage adminPage = new AdministrationPage();
        UpdateOrderWindow updateOrderWindow = adminPage.updateOrder();
        updateOrderWindow.updateOrderWindow();
        assertEquals(DataHelper.getCurrentDate(), updateOrderWindow.getValuesButtonToday());
        Calendar calendar = updateOrderWindow.openCalendarUpdateOrder();
        calendar.calendar();
        assertEquals(DataHelper.getCurrentMonthYear(), calendar.getCurrentMonthCalendar());
        calendar.selectDateActivation();
        updateOrderWindow.uploadOrder("src/test/resources/Приказ.xlsx");
        assertEquals("Федеральный приказ успешно обновлен", adminPage.getNotification());
    }

    @Feature("Документация")
    @Story("Успешное обновление приказа с даты в следующем месяце")
    @Test
    void updateOrderFutureMonth() {
        AdministrationPage adminPage = new AdministrationPage();
        UpdateOrderWindow updateOrderWindow = adminPage.updateOrder();
        updateOrderWindow.updateOrderWindow();
        assertEquals(DataHelper.getCurrentDate(), updateOrderWindow.getValuesButtonToday());
        Calendar calendar = updateOrderWindow.openCalendarUpdateOrder();
        calendar.calendar();
        assertEquals(DataHelper.getCurrentMonthYear(), calendar.getCurrentMonthCalendar());
        calendar.switchFutureMonth();
        assertEquals(DataHelper.getFutureMonthYear(), calendar.getCurrentMonthCalendar());
        calendar.selectDateActivation();
        updateOrderWindow.uploadOrder("src/test/resources/Приказ.xlsx");
        assertEquals("Федеральный приказ успешно обновлен", adminPage.getNotification());
    }

    @Feature("Документация")
    @Story("Успешное обновление приказа с даты в прошлом месяце")
    @Test
    void updateOrderPreviousMonth() {
        AdministrationPage adminPage = new AdministrationPage();
        UpdateOrderWindow updateOrderWindow = adminPage.updateOrder();
        updateOrderWindow.updateOrderWindow();
        assertEquals(DataHelper.getCurrentDate(), updateOrderWindow.getValuesButtonToday());
        Calendar calendar = updateOrderWindow.openCalendarUpdateOrder();
        calendar.calendar();
        assertEquals(DataHelper.getCurrentMonthYear(), calendar.getCurrentMonthCalendar());
        calendar.switchPreviousMonth();
        assertEquals(DataHelper.getPreviousMonthYear(), calendar.getCurrentMonthCalendar());
        calendar.selectDateActivation();
        updateOrderWindow.uploadOrder("src/test/resources/Приказ.xlsx");
        assertEquals("Федеральный приказ успешно обновлен", adminPage.getNotification());
    }

    @Feature("Документация")
    @Story("Успешное обновление приказа с текущей даты")
    @Test
    void updateOrderToday() {
        AdministrationPage adminPage = new AdministrationPage();
        UpdateOrderWindow updateOrderWindow = adminPage.updateOrder();
        updateOrderWindow.updateOrderWindow();
        assertEquals(DataHelper.getCurrentDate(), updateOrderWindow.getValuesButtonToday());
        Calendar calendar = updateOrderWindow.openCalendarUpdateOrder();
        calendar.calendar();
        assertEquals(DataHelper.getCurrentMonthYear(), calendar.getCurrentMonthCalendar());
        calendar.selectDateActivationToday();
        updateOrderWindow.uploadOrder("src/test/resources/Приказ.xlsx");
        assertEquals("Федеральный приказ успешно обновлен", adminPage.getNotification());
    }

    @Feature("Документация")
    @Story("Успешное обновление приказа с текущей даты без использования календаря")
    @Test
    void updateOrderTodayNotUseCalendar() {
        AdministrationPage adminPage = new AdministrationPage();
        UpdateOrderWindow updateOrderWindow = adminPage.updateOrder();
        updateOrderWindow.updateOrderWindow();
        assertEquals(DataHelper.getCurrentDate(), updateOrderWindow.getValuesButtonToday());
        updateOrderWindow.uploadOrder("src/test/resources/Приказ.xlsx");
        assertEquals("Федеральный приказ успешно обновлен", adminPage.getNotification());
    }

    @Feature("Документация")
    @Story("Закрытие окна обновления приказа")
    @Test
    void closeWindowUpdateOrder_11754() {
        AdministrationPage adminPage = new AdministrationPage();
        UpdateOrderWindow updateOrderWindow = adminPage.updateOrder();
        updateOrderWindow.updateOrderWindow();
        updateOrderWindow.closeWindowUpdateOrder();
        assertFalse(updateOrderWindow.isWindowAppear());
    }

    @Feature("Документация")
    @Story("Успешное обновление прайса с даты в текущем месяце ")
    @Test
    void updatePriceCurrentMonth_8929() {
        AdministrationPage adminPage = new AdministrationPage();
        UpdatePriceWindow updatePriceWindow = adminPage.updatePrice();
        updatePriceWindow.updatePriceWindow();
        assertEquals(DataHelper.getCurrentDate(), updatePriceWindow.getValuesButtonToday());
        Calendar calendar = updatePriceWindow.openCalendarUpdatePrice();
        calendar.calendar();
        assertEquals(DataHelper.getCurrentMonthYear(), calendar.getCurrentMonthCalendar());
        calendar.selectDateActivation();
        updatePriceWindow.uploadPrice("src/test/resources/Прайс.xlsx");
        assertEquals("Прайс успешно обновлен", adminPage.getNotification());
    }

    @Feature("Документация")
    @Story("Успешное обновление прайса с даты в следующем месяце")
    @Test
    void updatePriceFutureMonth() {
        AdministrationPage adminPage = new AdministrationPage();
        UpdatePriceWindow updatePriceWindow = adminPage.updatePrice();
        updatePriceWindow.updatePriceWindow();
        assertEquals(DataHelper.getCurrentDate(), updatePriceWindow.getValuesButtonToday());
        Calendar calendar = updatePriceWindow.openCalendarUpdatePrice();
        calendar.calendar();
        assertEquals(DataHelper.getCurrentMonthYear(), calendar.getCurrentMonthCalendar());
        calendar.switchFutureMonth();
        assertEquals(DataHelper.getFutureMonthYear(), calendar.getCurrentMonthCalendar());
        calendar.selectDateActivation();
        updatePriceWindow.uploadPrice("src/test/resources/Прайс.xlsx");
        assertEquals("Прайс успешно обновлен", adminPage.getNotification());
    }

    @Feature("Документация")
    @Story("Успешное обновление прайса с даты в прошлом месяце")
    @Test
    void updatePricePreviousMonth() {
        AdministrationPage adminPage = new AdministrationPage();
        UpdatePriceWindow updatePriceWindow = adminPage.updatePrice();
        updatePriceWindow.updatePriceWindow();
        assertEquals(DataHelper.getCurrentDate(), updatePriceWindow.getValuesButtonToday());
        Calendar calendar = updatePriceWindow.openCalendarUpdatePrice();
        calendar.calendar();
        assertEquals(DataHelper.getCurrentMonthYear(), calendar.getCurrentMonthCalendar());
        calendar.switchPreviousMonth();
        assertEquals(DataHelper.getPreviousMonthYear(), calendar.getCurrentMonthCalendar());
        calendar.selectDateActivation();
        updatePriceWindow.uploadPrice("src/test/resources/Прайс.xlsx");
        assertEquals("Прайс успешно обновлен", adminPage.getNotification());
    }

    @Feature("Документация")
    @Story("Успешное обновление прайса с текущей даты")
    @Test
    void updatePriceToday() {
        AdministrationPage adminPage = new AdministrationPage();
        UpdatePriceWindow updatePriceWindow = adminPage.updatePrice();
        updatePriceWindow.updatePriceWindow();
        assertEquals(DataHelper.getCurrentDate(), updatePriceWindow.getValuesButtonToday());
        Calendar calendar = updatePriceWindow.openCalendarUpdatePrice();
        calendar.calendar();
        assertEquals(DataHelper.getCurrentMonthYear(), calendar.getCurrentMonthCalendar());
        calendar.selectDateActivationToday();
        updatePriceWindow.uploadPrice("src/test/resources/Прайс.xlsx");
        assertEquals("Прайс успешно обновлен", adminPage.getNotification());
    }

    @Feature("Документация")
    @Story("Успешное обновление прайса с текущей даты без использования календаря")
    @Test
    void updatePriceTodayNotUseCalendar() {
        AdministrationPage adminPage = new AdministrationPage();
        UpdatePriceWindow updatePriceWindow = adminPage.updatePrice();
        updatePriceWindow.updatePriceWindow();
        assertEquals(DataHelper.getCurrentDate(), updatePriceWindow.getValuesButtonToday());
        updatePriceWindow.uploadPrice("src/test/resources/Прайс.xlsx");
        assertEquals("Прайс успешно обновлен", adminPage.getNotification());
    }

    @Feature("Документация")
    @Story("Закрытие окна обновления прайса")
    @Test
    void closeWindowUpdatePrice_9163() {
        AdministrationPage adminPage = new AdministrationPage();
        UpdatePriceWindow updatePriceWindow = adminPage.updatePrice();
        updatePriceWindow.updatePriceWindow();
        updatePriceWindow.closeWindowUpdatePrice();
        assertFalse(updatePriceWindow.isWindowAppear());
    }

    @Feature("Документация")
    @Story("Обновление офферты с невалидным файлом в формате Docx")
    @Test
    void updateOfferFormatDocx_11752() {
        AdministrationPage adminPage = new AdministrationPage();
        UpdateLegalDocWindow updateLegalDocWindow = adminPage.updateOffer();
        updateLegalDocWindow.uploadDocWindow();
        updateLegalDocWindow.uploadInvalidDoc("src/test/resources/Оферта,Политика обработки docx.docx");
        assertEquals("Допускаются файлы с расширением PDF", adminPage.getNotification());
    }

    @Feature("Документация")
    @Story("Обновление офферты с невалидным файлом в формате Xlsx")
    @Test
    void updateOfferFormatXlsx_11762() {
        AdministrationPage adminPage = new AdministrationPage();
        UpdateLegalDocWindow updateLegalDocWindow = adminPage.updateOffer();
        updateLegalDocWindow.uploadDocWindow();
        updateLegalDocWindow.uploadInvalidDoc("src/test/resources/Оферта, Политика обработки .xlsx.xlsx");
        assertEquals("Допускаются файлы с расширением PDF", adminPage.getNotification());
    }

    @Feature("Документация")
    @Story("Обновление офферты с невалидным файлом в формате Jpeg")
    @Test
    void updateOfferFormatJpeg_11765() {
        AdministrationPage adminPage = new AdministrationPage();
        UpdateLegalDocWindow updateLegalDocWindow = adminPage.updateOffer();
        updateLegalDocWindow.uploadDocWindow();
        updateLegalDocWindow.uploadInvalidDoc("src/test/resources/Оферта, Политика обработки jpeg.jpg");
        assertEquals("Допускаются файлы с расширением PDF", adminPage.getNotification());
    }

    @Feature("Документация")
    @Story("Обновление политики обработки с невалидным файлом в формате Docx")
    @Test
    void updateProcessingPolicyFormatDocx_11756() {
        AdministrationPage adminPage = new AdministrationPage();
        UpdateLegalDocWindow updateLegalDocWindow = adminPage.updateProcessingPolicy();
        updateLegalDocWindow.uploadDocWindow();
        updateLegalDocWindow.uploadInvalidDoc("src/test/resources/Оферта,Политика обработки docx.docx");
        assertEquals("Допускаются файлы с расширением PDF", adminPage.getNotification());
    }

    @Feature("Документация")
    @Story("Обновление политики обработки с невалидным файлом в формате Xlsx")
    @Test
    void updateProcessingPolicyFormatXlsx_11764() {
        AdministrationPage adminPage = new AdministrationPage();
        UpdateLegalDocWindow updateLegalDocWindow = adminPage.updateProcessingPolicy();
        updateLegalDocWindow.uploadDocWindow();
        updateLegalDocWindow.uploadInvalidDoc("src/test/resources/Оферта, Политика обработки .xlsx.xlsx");
        assertEquals("Допускаются файлы с расширением PDF", adminPage.getNotification());
    }

    @Feature("Документация")
    @Story("Обновление политики обработки с невалидным файлом в формате Jpeg")
    @Test
    void updateProcessingPolicyFormatJpeg_11767() {
        AdministrationPage adminPage = new AdministrationPage();
        UpdateLegalDocWindow updateLegalDocWindow = adminPage.updateProcessingPolicy();
        updateLegalDocWindow.uploadDocWindow();
        updateLegalDocWindow.uploadInvalidDoc("src/test/resources/Оферта, Политика обработки jpeg.jpg");
        assertEquals("Допускаются файлы с расширением PDF", adminPage.getNotification());
    }

    @Feature("Документация")
    @Story("Обновление приказа с ошибкой в строке")
    @Test
    void updateOrderStringError_11895() {
        AdministrationPage adminPage = new AdministrationPage();
        UpdateOrderWindow updateOrderWindow = adminPage.updateOrder();
        updateOrderWindow.updateOrderWindow();
        assertEquals(DataHelper.getCurrentDate(), updateOrderWindow.getValuesButtonToday());
        updateOrderWindow.uploadOrder("src/test/resources/Приказ с ошибкой в строке 10858.xlsx");
        assertEquals("Ошибка в 10858 строке", adminPage.getNotification());
    }

    @Feature("Документация")
    @Story("Обновление приказа с невалидным файлом в формате Docx")
    @Test
    void updateOrderFormatDocx_11753() {
        AdministrationPage adminPage = new AdministrationPage();
        UpdateOrderWindow updateOrderWindow = adminPage.updateOrder();
        updateOrderWindow.updateOrderWindow();
        assertEquals(DataHelper.getCurrentDate(), updateOrderWindow.getValuesButtonToday());
        Calendar calendar = updateOrderWindow.openCalendarUpdateOrder();
        calendar.calendar();
        assertEquals(DataHelper.getCurrentMonthYear(), calendar.getCurrentMonthCalendar());
        calendar.selectDateActivation();
        updateOrderWindow.uploadOrder("src/test/resources/Оферта,Политика обработки docx.docx");
        assertEquals("Допускаются файлы с расширением xlsx", adminPage.getNotification());
    }

    @Feature("Документация")
    @Story("Обновление приказа с невалидным файлом в формате Jpeg")
    @Test
    void updateOrderFormatJpeg_11760() {
        AdministrationPage adminPage = new AdministrationPage();
        UpdateOrderWindow updateOrderWindow = adminPage.updateOrder();
        updateOrderWindow.updateOrderWindow();
        assertEquals(DataHelper.getCurrentDate(), updateOrderWindow.getValuesButtonToday());
        Calendar calendar = updateOrderWindow.openCalendarUpdateOrder();
        calendar.calendar();
        assertEquals(DataHelper.getCurrentMonthYear(), calendar.getCurrentMonthCalendar());
        calendar.selectDateActivation();
        updateOrderWindow.uploadOrder("src/test/resources/Оферта, Политика обработки jpeg.jpg");
        assertEquals("Допускаются файлы с расширением xlsx", adminPage.getNotification());
    }

    @Feature("Документация")
    @Story("Обновление приказа с невалидным файлом в формате PDF")
    @Test
    void updateOrderFormatPDF_11761() {
        AdministrationPage adminPage = new AdministrationPage();
        UpdateOrderWindow updateOrderWindow = adminPage.updateOrder();
        updateOrderWindow.updateOrderWindow();
        assertEquals(DataHelper.getCurrentDate(), updateOrderWindow.getValuesButtonToday());
        Calendar calendar = updateOrderWindow.openCalendarUpdateOrder();
        calendar.calendar();
        assertEquals(DataHelper.getCurrentMonthYear(), calendar.getCurrentMonthCalendar());
        calendar.selectDateActivation();
        updateOrderWindow.uploadOrder("src/test/resources/Оферта.pdf");
        assertEquals("Допускаются файлы с расширением xlsx", adminPage.getNotification());
    }

    @Feature("Документация")
    @Story("Обновление прайса с ошибкой в строке")
    @Test
    void updatePriceCurrentMonthStringError_11757() {
        AdministrationPage adminPage = new AdministrationPage();
        UpdatePriceWindow updatePriceWindow = adminPage.updatePrice();
        updatePriceWindow.updatePriceWindow();
        assertEquals(DataHelper.getCurrentDate(), updatePriceWindow.getValuesButtonToday());
        Calendar calendar = updatePriceWindow.openCalendarUpdatePrice();
        calendar.calendar();
        assertEquals(DataHelper.getCurrentMonthYear(), calendar.getCurrentMonthCalendar());
        calendar.selectDateActivation();
        updatePriceWindow.uploadPrice("src/test/resources/Прайс с ошибкой в строке 1398.xlsx");
        assertEquals("Ошибка в 1398 строке", adminPage.getNotification());
    }

    @Feature("Документация")
    @Story("Обновление прайса с ошибкой формата стоимости услуги")
    @Test
    void updatePriceCurrentMonthFormatError() {
        AdministrationPage adminPage = new AdministrationPage();
        UpdatePriceWindow updatePriceWindow = adminPage.updatePrice();
        updatePriceWindow.updatePriceWindow();
        assertEquals(DataHelper.getCurrentDate(), updatePriceWindow.getValuesButtonToday());
        Calendar calendar = updatePriceWindow.openCalendarUpdatePrice();
        calendar.calendar();
        assertEquals(DataHelper.getCurrentMonthYear(), calendar.getCurrentMonthCalendar());
        calendar.selectDateActivation();
        updatePriceWindow.uploadPrice("src/test/resources/Прайс с ошибкой формата в услуге А26.30.004.002.xlsx");
        assertEquals("Ошибка формата стоимости услуги, код: 'А26.30.004.002', стоимость: '1000'.", updatePriceWindow.getErrorInfo());
    }

    @Feature("Документация")
    @Story("Обновление прайса с невалидным файлом в формате Docx")
    @Test
    void updatePriceFormatDocx_8940() {
        AdministrationPage adminPage = new AdministrationPage();
        UpdatePriceWindow updatePriceWindow = adminPage.updatePrice();
        updatePriceWindow.updatePriceWindow();
        assertEquals(DataHelper.getCurrentDate(), updatePriceWindow.getValuesButtonToday());
        Calendar calendar = updatePriceWindow.openCalendarUpdatePrice();
        calendar.calendar();
        assertEquals(DataHelper.getCurrentMonthYear(), calendar.getCurrentMonthCalendar());
        calendar.selectDateActivation();
        updatePriceWindow.uploadPrice("src/test/resources/Оферта,Политика обработки docx.docx");
        assertEquals("Допускаются файлы с расширением xlsx", adminPage.getNotification());
    }

    @Feature("Документация")
    @Story("Обновление прайса с невалидным файлом в формате Jpeg")
    @Test
    void updatePriceFormatJpeg_11758() {
        AdministrationPage adminPage = new AdministrationPage();
        UpdatePriceWindow updatePriceWindow = adminPage.updatePrice();
        updatePriceWindow.updatePriceWindow();
        assertEquals(DataHelper.getCurrentDate(), updatePriceWindow.getValuesButtonToday());
        Calendar calendar = updatePriceWindow.openCalendarUpdatePrice();
        calendar.calendar();
        assertEquals(DataHelper.getCurrentMonthYear(), calendar.getCurrentMonthCalendar());
        calendar.selectDateActivation();
        updatePriceWindow.uploadPrice("src/test/resources/Оферта, Политика обработки jpeg.jpg");
        assertEquals("Допускаются файлы с расширением xlsx", adminPage.getNotification());
    }

    @Feature("Документация")
    @Story("Обновление прайса с невалидным файлом в формате PDF")
    @Test
    void updatePriceFormatPDF_11759() {
        AdministrationPage adminPage = new AdministrationPage();
        UpdatePriceWindow updatePriceWindow = adminPage.updatePrice();
        updatePriceWindow.updatePriceWindow();
        assertEquals(DataHelper.getCurrentDate(), updatePriceWindow.getValuesButtonToday());
        Calendar calendar = updatePriceWindow.openCalendarUpdatePrice();
        calendar.calendar();
        assertEquals(DataHelper.getCurrentMonthYear(), calendar.getCurrentMonthCalendar());
        calendar.selectDateActivation();
        updatePriceWindow.uploadPrice("src/test/resources/Оферта.pdf");
        assertEquals("Допускаются файлы с расширением xlsx", adminPage.getNotification());
    }


    @Feature("Документация")
    @Story("Открытие и закрытие правил корректирования")
    @Test
    void openCloseAdjustmentRules() {
        AdministrationPage adminPage = new AdministrationPage();
        UpdatePriceWindow updatePriceWindow = adminPage.updatePrice();
        updatePriceWindow.updatePriceWindow();
        assertEquals(DataHelper.getCurrentDate(), updatePriceWindow.getValuesButtonToday());
        Calendar calendar = updatePriceWindow.openCalendarUpdatePrice();
        calendar.calendar();
        assertEquals(DataHelper.getCurrentMonthYear(), calendar.getCurrentMonthCalendar());
        calendar.selectDateActivation();
        updatePriceWindow.uploadPrice("src/test/resources/Прайс с ошибкой формата в услуге А26.30.004.002.xlsx");
        assertEquals("Ошибка формата стоимости услуги, код: 'А26.30.004.002', стоимость: '1000'.", updatePriceWindow.getErrorInfo());
        updatePriceWindow.openAdjustmentRules();
        updatePriceWindow.closeInfoErrorWindow();
    }

    @Story("Возврат к хэдеру страницы администрирования")
    @Test
    void returnToStartPage() {
        AdministrationPage adminPage = new AdministrationPage();
        adminPage.scrollPageToBottom();
        adminPage.returnToStartPage();
        adminPage.returnButtonDisappears();
    }

    @Story("Закрытие уведомления на странице администрирования по таймауту")
    @Test
    void closeNotificationTimeout() {
        AdministrationPage adminPage = new AdministrationPage();
        UpdateLegalDocWindow updateLegalDocWindow = adminPage.updateProcessingPolicy();
        updateLegalDocWindow.uploadDocWindow();
        updateLegalDocWindow.uploadInvalidDoc("src/test/resources/Оферта, Политика обработки jpeg.jpg");
        assertEquals("Допускаются файлы с расширением PDF", adminPage.getNotification());
        adminPage.notificationDisappears();
    }

    @Story("Закрытие уведомления на странице администрирования")
    @Test
    void closeNotification() {
        AdministrationPage adminPage = new AdministrationPage();
        UpdateLegalDocWindow updateLegalDocWindow = adminPage.updateProcessingPolicy();
        updateLegalDocWindow.uploadDocWindow();
        updateLegalDocWindow.uploadInvalidDoc("src/test/resources/Оферта, Политика обработки jpeg.jpg");
        assertEquals("Допускаются файлы с расширением PDF", adminPage.getNotification());
        adminPage.closeNotification();
    }
}
/*
    @Feature("Документация")
    @Story("Загрузка файла с прайсом")
    @Test
    void downloadPrice() {
        AdministrationPage adminPage = new AdministrationPage();
        UpdatePriceWindow updatePriceWindow = adminPage.updatePrice();
        updatePriceWindow.updatePriceWindow();
        File downloadedFile = updatePriceWindow.downloadPriceDateActivation();
    }
}
*/
