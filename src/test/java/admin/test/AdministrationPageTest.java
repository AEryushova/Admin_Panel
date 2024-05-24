package admin.test;

import admin.pages.AuthorizationPage;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import admin.data.DataInfo;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import admin.pages.AdministrationPage;
import admin.pages.HeaderBar;
import admin.pages.modalWindowAdministration.*;
import admin.pages.calendar.Calendar;
import admin.utils.DataHelper;

import java.io.File;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Feature("Добавление нового админа")
    @Story("Успешное добавление нового админа")
    @Test
    void addedNewAdmin_8613() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillingFieldNewAdminLogin("ADMIN_TESTUI_2");
        newAdminWindow.fillingFieldNewAdminPassword("WwSs12345#");
        newAdminWindow.fillingFieldNewAdminConfirmPassword("WwSs12345#");
        newAdminWindow.clickAddButton();
        adminPage.getAdminCard();
        assertEquals("Новый администратор ADMIN_TESTUI_2 успешно создан", adminPage.getNotification());
    }

    @Feature("Добавление нового админа")
    @Story("Добавление нового админа с уже существующим логином")
    @Test
    void addedNewAdminAlreadyExisting_9334() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillingFieldNewAdminLogin("ADMIN_TESTUI_2");
        newAdminWindow.fillingFieldNewAdminPassword("WwSs12345#");
        newAdminWindow.fillingFieldNewAdminConfirmPassword("WwSs12345#");
        newAdminWindow.clickAddButton();
        assertEquals("{\"error\":\"Пользователь уже существует, логин: ADMIN_TESTUI_2\",\"innerError\":null,\"exception\":\"AlreadyExistException\"}", adminPage.getNotification());
    }

    @Feature("Добавление нового админа")
    @Story("Добавление нового администраторас пустым полем логина")
    @Test
    void addedNewAdminEmptyFieldsLogin_8670() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillingFieldNewAdminPassword("WwSs12345#");
        newAdminWindow.fillingFieldNewAdminConfirmPassword("WwSs12345#");
        newAdminWindow.newAdminWindow();
    }

    @Feature("Добавление нового админа")
    @Story("Добавление нового администратора с пустым полем пароля")
    @Test
    void addedNewAdminEmptyFieldsPassword_8671() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillingFieldNewAdminLogin("ADMIN_TESTUI_2");
        newAdminWindow.fillingFieldNewAdminConfirmPassword("WwSs12345#");
        newAdminWindow.newAdminWindow();
        assertEquals("Не соответствует паролю", newAdminWindow.getErrorFieldConfirmPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Добавление нового администратора с пустым полем подтверждения пароля")
    @Test
    void addedNewAdminEmptyFieldsConfirmPassword_8672() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillingFieldNewAdminLogin("ADMIN_TESTUI_2");
        newAdminWindow.fillingFieldNewAdminPassword("WwSs12345#");
        newAdminWindow.newAdminWindow();

    }

    @Feature("Добавление нового админа")
    @Story("Добавление нового администратора с пустым полем логина и пароля")
    @Test
    void addedNewAdminEmptyFieldsLoginPassword_8674() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillingFieldNewAdminConfirmPassword("WwSs12345#");
        newAdminWindow.newAdminWindow();
        assertEquals("Не соответствует паролю", newAdminWindow.getErrorFieldConfirmPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Добавление нового администратора с пустым полем логина и подтверждения пароля")
    @Test
    void addedNewAdminEmptyFieldsLoginConfirmPassword_8675() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillingFieldNewAdminPassword("WwSs12345#");
        newAdminWindow.newAdminWindow();
    }

    @Feature("Добавление нового админа")
    @Story("Добавление нового администратора с пустым полем пароля и подтверждения пароля")
    @Test
    void addedNewAdminEmptyFieldsPasswordConfirmPassword_8676() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillingFieldNewAdminLogin("ADMIN_TESTUI_2");
        newAdminWindow.newAdminWindow();
    }

    @Feature("Добавление нового админа")
    @Story("Добавление нового администратора с невалидными значениями")
    @Test
    void addedNewAdminInvalidValue_8688() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillingFieldNewAdminLogin("АННА_ТЕСТ");
        newAdminWindow.fillingFieldNewAdminPassword("WwSs12345");
        newAdminWindow.fillingFieldNewAdminConfirmPassword("WwSs12345#");
        newAdminWindow.newAdminWindow();
        assertEquals("Первый символ должен быть латинской буквой или \"_\"", newAdminWindow.getErrorFieldLogin());
        assertEquals("Пароль не валиден", newAdminWindow.getErrorFieldPassword());
        assertEquals("Не соответствует паролю", newAdminWindow.getErrorFieldConfirmPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Отображение уведомления об обязательности полей")
    @Test
    void addedNewAdminObligatoryFields_8681() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
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
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillingFieldNewAdminLogin("ANNA_TEST_ADMIN123456789_ANNA_1");
        newAdminWindow.newAdminWindow();
        newAdminWindow.hiddenErrorFieldLogin();
    }

    @Feature("Добавление нового админа")
    @Story("Ввод валидного логина из 32 символов")
    @Test
    void addedNewAdminLogin32Symbol() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillingFieldNewAdminLogin("ANNA_TEST_ADMIN123456789_ANNA_12");
        newAdminWindow.newAdminWindow();
        newAdminWindow.hiddenErrorFieldLogin();
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не валидного логина из 33 символов")
    @Test
    void addedNewAdminLogin33Symbol() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillingFieldNewAdminLogin("ANNA_TEST_ADMIN123456789_ANNA_123");
        newAdminWindow.newAdminWindow();
        assertEquals("Максимальная длина 32 символа", newAdminWindow.getErrorFieldLogin());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не валидного логина на кириллице")
    @Test
    void addedNewAdminLoginCyrillicValue() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillingFieldNewAdminLogin("АННА_ТЕСТ");
        newAdminWindow.newAdminWindow();
        assertEquals("Первый символ должен быть латинской буквой или \"_\"", newAdminWindow.getErrorFieldLogin());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не валидного логина, начинающегося с цифры")
    @Test
    void addedNewAdminLoginNumberBegin() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillingFieldNewAdminLogin("1ANNA_TEST");
        newAdminWindow.newAdminWindow();
        assertEquals("Первый символ должен быть латинской буквой или \"_\"", newAdminWindow.getErrorFieldLogin());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не валидного логина, начинающегося с латиницы, далее на кириллице")
    @Test
    void addedNewAdminLoginLatinBeginCyrillicValue() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillingFieldNewAdminLogin("AННА_ТЕСТ");
        newAdminWindow.newAdminWindow();
        assertEquals("Доступны только числа, латиница и \"_\"", newAdminWindow.getErrorFieldLogin());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не валидного логина с пробелом")
    @Test
    void addedNewAdminLoginWithSpace() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillingFieldNewAdminLogin("ANNA TEST");
        newAdminWindow.newAdminWindow();
        assertEquals("Доступны только числа, латиница и \"_\"", newAdminWindow.getErrorFieldLogin());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не валидного пароля из 7 символов")
    @Test
    void addedNewAdminPassword7Symbol() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillingFieldNewAdminPassword("Wwqq12#");
        newAdminWindow.newAdminWindow();
        assertEquals("Пароль не валиден", newAdminWindow.getErrorFieldPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод валидного пароля из 8 символов")
    @Test
    void addedNewAdminPassword8Symbol() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        ;
        adminPage.administrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillingFieldNewAdminPassword("WwSs145#");
        newAdminWindow.newAdminWindow();
        newAdminWindow.hiddenErrorFieldPassword();
    }

    @Feature("Добавление нового админа")
    @Story("Ввод валидного пароля из 9 символов")
    @Test
    void addedNewAdminPassword9Symbol() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillingFieldNewAdminPassword("WwSs1234#");
        newAdminWindow.newAdminWindow();
        newAdminWindow.hiddenErrorFieldPassword();
    }

    @Feature("Добавление нового админа")
    @Story("Ввод валидного пароля из 24 символов")
    @Test
    void addedNewAdminPassword24Symbol() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillingFieldNewAdminPassword("Wwqq123456789#QQgg123456");
        newAdminWindow.newAdminWindow();
        newAdminWindow.hiddenErrorFieldPassword();
    }

    @Feature("Добавление нового админа")
    @Story("Ввод валидного пароля из 25 символов")
    @Test
    void addedNewAdminPassword25Symbol() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillingFieldNewAdminPassword("Wwqq123456789#QQgg1234567");
        newAdminWindow.newAdminWindow();
        newAdminWindow.hiddenErrorFieldPassword();
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не валидного пароля из 26 символов")
    @Test
    void addedNewAdminPassword26Symbol() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillingFieldNewAdminPassword("Wwqq123456789#QQgg12345678");
        newAdminWindow.newAdminWindow();
        assertEquals("Пароль не валиден", newAdminWindow.getErrorFieldPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не валидного пароля без латинской буквы")
    @Test
    void addedNewAdminPasswordNotLatinValue() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillingFieldNewAdminPassword("123456789!");
        newAdminWindow.newAdminWindow();
        assertEquals("Пароль не валиден", newAdminWindow.getErrorFieldPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не валидного пароля без спецсимвола")
    @Test
    void addedNewAdminPasswordNotSymbol() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillingFieldNewAdminPassword("Wwqq1234567");
        newAdminWindow.newAdminWindow();
        assertEquals("Пароль не валиден", newAdminWindow.getErrorFieldPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не валидного пароля без латинской буквы в нижнем регистре")
    @Test
    void addedNewAdminPasswordNotToLowerCase() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillingFieldNewAdminPassword("WWQQ123456#");
        newAdminWindow.newAdminWindow();
        assertEquals("Пароль не валиден", newAdminWindow.getErrorFieldPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не валидного пароля без латинской буквы в верхнем регистре")
    @Test
    void addedNewAdminPasswordNotToUpperCase() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillingFieldNewAdminPassword("wwqq123456#");
        newAdminWindow.newAdminWindow();
        assertEquals("Пароль не валиден", newAdminWindow.getErrorFieldPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не валидного пароля без цифры")
    @Test
    void addedNewAdminPasswordNotNumber() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillingFieldNewAdminPassword("Wwqqwq#@#@");
        newAdminWindow.newAdminWindow();
        assertEquals("Пароль не валиден", newAdminWindow.getErrorFieldPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не валидного пароля на кириллице")
    @Test
    void addedNewAdminPasswordCyrillicValue() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillingFieldNewAdminPassword("Ффыы123456#");
        newAdminWindow.newAdminWindow();
        assertEquals("Пароль не валиден", newAdminWindow.getErrorFieldPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не валидного пароля с пробелом")
    @Test
    void addedNewAdminPasswordWithSpace() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillingFieldNewAdminPassword("Wwqq 123456 #");
        newAdminWindow.newAdminWindow();
        assertEquals("Пароль не валиден", newAdminWindow.getErrorFieldPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не соответствующего пароля при подтверждении")
    @Test
    void addedNewAdminMismatchedPasswords_8680() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillingFieldNewAdminLogin("ADMIN_TESTUI_2");
        newAdminWindow.fillingFieldNewAdminPassword("WwSs12345#");
        newAdminWindow.fillingFieldNewAdminConfirmPassword("WwSs125#");
        newAdminWindow.newAdminWindow();
        assertEquals("Не соответствует паролю", newAdminWindow.getErrorFieldConfirmPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Очистка полей через кнопку в окне добавления админа")
    @Test
    void clearFieldsThroughButtonClear() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillingFieldNewAdminLogin("ADMIN_TESTUI_2");
        newAdminWindow.clearButtonLoginField();
        newAdminWindow.fillingFieldNewAdminPassword("WwSs12345#");
        newAdminWindow.clearButtonPasswordField();
        newAdminWindow.fillingFieldNewAdminConfirmPassword("WwSs12345#");
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
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillingFieldNewAdminLogin("ADMIN_TESTUI_2");
        newAdminWindow.fillingFieldNewAdminPassword("WwSs12345#");
        newAdminWindow.fillingFieldNewAdminConfirmPassword("WwSs12345#");
        newAdminWindow.closeWindowAddedAdmin();
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
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.changePasswordAdminWindow();
        changePasswordAdminWindow.fillingFieldNewPassword("WwQq98765*");
        changePasswordAdminWindow.fillingFieldConfirmPassword("WwQq98765*");
        changePasswordAdminWindow.clickSaveNewPasswordButton();
        adminPage.getAdminCard();
        assertEquals("Админ ADMIN_TESTUI_2 успешно изменен", adminPage.getNotification());
        headerBar.openAndCloseProfileAdmin();
        headerBar.exitAdminPanel();
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authorizationPage();
        DataInfo dataInfo = new DataInfo("ADMIN_TESTUI_2", "WwQq98765*");
        authorizationPage.authorizationAdminPanel(dataInfo);
        headerBar = new HeaderBar();
        headerBar.headerBarAdmin();
    }

    @Feature("Смена пароля админу")
    @Story("Смена пароля админу с пустым полем пароля")
    @Test
    void changePasswordAdminEmptyFieldsPassword_8879() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.changePasswordAdminWindow();
        changePasswordAdminWindow.fillingFieldConfirmPassword("WwSs12345#");
        changePasswordAdminWindow.changePasswordAdminWindow();
        assertEquals("Не соответствует паролю", changePasswordAdminWindow.getErrorFieldConfirmPassword());
    }

    @Feature("Смена пароля админу")
    @Story("Смена пароля админу с пустым полем подтверждения пароля")
    @Test
    void changePasswordAdminEmptyFieldsConfirmPassword_8618() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.fillingFieldNewPassword("WwSs12345#");
        changePasswordAdminWindow.changePasswordAdminWindow();
    }

    @Feature("Смена пароля админу")
    @Story("Отображение уведомления об обязательности полей")
    @Test
    void changePasswordAdminObligatoryFields_() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
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
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.changePasswordAdminWindow();
        changePasswordAdminWindow.clickFieldNewPassword();
        changePasswordAdminWindow.fillingFieldNewPassword("WwSs12345#");
        changePasswordAdminWindow.fillingFieldConfirmPassword("WwSs1234567#");
        changePasswordAdminWindow.clickFieldNewPassword();
        changePasswordAdminWindow.changePasswordAdminWindow();
        assertEquals("Не соответствует паролю", changePasswordAdminWindow.getErrorFieldConfirmPassword());
    }

    @Feature("Смена пароля админу")
    @Story("Ввод не валидного пароля из 7 символов")
    @Test
    void changePasswordAdmin7Symbol() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.changePasswordAdminWindow();
        changePasswordAdminWindow.fillingFieldNewPassword("Wwqq12#");
        changePasswordAdminWindow.changePasswordAdminWindow();
        assertEquals("Пароль не валиден", changePasswordAdminWindow.getErrorFieldPassword());
    }

    @Feature("Смена пароля админу")
    @Story("Ввод валидного пароля из 8 символов")
    @Test
    void changePasswordAdmin8Symbol() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.changePasswordAdminWindow();
        changePasswordAdminWindow.fillingFieldNewPassword("Wwqq123#");
        changePasswordAdminWindow.changePasswordAdminWindow();
        changePasswordAdminWindow.hiddenErrorFieldPassword();
    }

    @Feature("Смена пароля админу")
    @Story("Ввод валидного пароля из 9 символов")
    @Test
    void changePasswordAdmin9Symbol() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.changePasswordAdminWindow();
        changePasswordAdminWindow.fillingFieldNewPassword("Wwqq1234#");
        changePasswordAdminWindow.changePasswordAdminWindow();
        changePasswordAdminWindow.hiddenErrorFieldPassword();
    }

    @Feature("Смена пароля админу")
    @Story("Ввод валидного пароля из 24 символов")
    @Test
    void changePasswordAdmin24Symbol() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.changePasswordAdminWindow();
        changePasswordAdminWindow.fillingFieldNewPassword("Wwqq123456789#QQgg123456");
        changePasswordAdminWindow.changePasswordAdminWindow();
        changePasswordAdminWindow.hiddenErrorFieldPassword();
    }


    @Feature("Смена пароля админу")
    @Story("Ввод валидного пароля из 25 символов")
    @Test
    void changePasswordAdmin25Symbol() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.changePasswordAdminWindow();
        changePasswordAdminWindow.fillingFieldNewPassword("Wwqq123456789#QQgg1234567");
        changePasswordAdminWindow.changePasswordAdminWindow();
        changePasswordAdminWindow.hiddenErrorFieldPassword();
    }

    @Feature("Смена пароля админу")
    @Story("Ввод не валидного пароля из 26 символов")
    @Test
    void changePasswordAdmin26Symbol() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.changePasswordAdminWindow();
        changePasswordAdminWindow.fillingFieldNewPassword("Wwqq123456789#QQgg12345678");
        changePasswordAdminWindow.changePasswordAdminWindow();
        assertEquals("Пароль не валиден", changePasswordAdminWindow.getErrorFieldPassword());
    }

    @Feature("Смена пароля админу")
    @Story("Ввод не валидного пароля без латинской буквы в верхнем регистре")
    @Test
    void changePasswordAdminNotToUpperCase() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.changePasswordAdminWindow();
        changePasswordAdminWindow.fillingFieldNewPassword("wwqq123456#");
        changePasswordAdminWindow.changePasswordAdminWindow();
        assertEquals("Пароль не валиден", changePasswordAdminWindow.getErrorFieldPassword());
    }

    @Feature("Смена пароля админу")
    @Story("Ввод не валидного пароля без латинской буквы в нижнем регистре")
    @Test
    void changePasswordAdminNotToLowerCase() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.changePasswordAdminWindow();
        changePasswordAdminWindow.fillingFieldNewPassword("WWQQ123456#");
        changePasswordAdminWindow.changePasswordAdminWindow();
        assertEquals("Пароль не валиден", changePasswordAdminWindow.getErrorFieldPassword());
    }

    @Feature("Смена пароля админу")
    @Story("Ввод не валидного пароля на кириллице")
    @Test
    void changePasswordAdminCyrillicValue() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.changePasswordAdminWindow();
        changePasswordAdminWindow.fillingFieldNewPassword("Ффыы123456#");
        changePasswordAdminWindow.changePasswordAdminWindow();
        assertEquals("Пароль не валиден", changePasswordAdminWindow.getErrorFieldPassword());
    }

    @Feature("Смена пароля админу")
    @Story("Ввод не валидного пароля без цифры")
    @Test
    void changePasswordAdminNotNumber() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.changePasswordAdminWindow();
        changePasswordAdminWindow.fillingFieldNewPassword("Wwqqwq#@#@");
        changePasswordAdminWindow.changePasswordAdminWindow();
        assertEquals("Пароль не валиден", changePasswordAdminWindow.getErrorFieldPassword());
    }

    @Feature("Смена пароля админу")
    @Story("Ввод не валидного пароля без спецсимвола")
    @Test
    void changePasswordAdminNotSymbol() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.changePasswordAdminWindow();
        changePasswordAdminWindow.fillingFieldNewPassword("Wwqq1234567");
        changePasswordAdminWindow.changePasswordAdminWindow();
        assertEquals("Пароль не валиден", changePasswordAdminWindow.getErrorFieldPassword());
    }

    @Feature("Смена пароля админу")
    @Story("Ввод не валидного пароля без латинской буквы")
    @Test
    void changePasswordAdminNotLatinValue() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.changePasswordAdminWindow();
        changePasswordAdminWindow.fillingFieldNewPassword("123456789!");
        changePasswordAdminWindow.changePasswordAdminWindow();
        assertEquals("Пароль не валиден", changePasswordAdminWindow.getErrorFieldPassword());
    }

    @Feature("Смена пароля админу")
    @Story("Ввод не валидного пароля с пробелом")
    @Test
    void changePasswordAdminWithSpace() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.changePasswordAdminWindow();
        changePasswordAdminWindow.fillingFieldNewPassword("Wwqq 123456 #");
        changePasswordAdminWindow.changePasswordAdminWindow();
        assertEquals("Пароль не валиден", changePasswordAdminWindow.getErrorFieldPassword());
    }

    @Feature("Смена пароля админу")
    @Story("Зануление полей в окне смены пароля админу после закрытия окна")
    @Test
    void closeWindowChangePasswordAdmin_8880() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.changePasswordAdminWindow();
        changePasswordAdminWindow.fillingFieldNewPassword("Wwqq123456#");
        changePasswordAdminWindow.fillingFieldConfirmPassword("Wwqq123456#");
        changePasswordAdminWindow.closeWindowChangePasswordAdmin();
        ChangePasswordAdminWindow changePasswordAdminWindowOpen = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindowOpen.changePasswordAdminWindow();
        assertEquals("", changePasswordAdminWindowOpen.getValuePasswordField());
        assertEquals("", changePasswordAdminWindowOpen.getValueConfirmPasswordField());

    }

    @Feature("Удаление админа")
    @Story("Отмена удаления админа")
    @Test
    void cancelDeleteAdmin() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        adminPage.openWindowDeleteAdmin();
        adminPage.cancelDeleteAdmin();
        adminPage.getAdminCard();
    }

    @Feature("Удаление админа")
    @Story("Успешное удаление админа")
    @Test
    void deleteAdmin() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        adminPage.deleteAdmin();
        adminPage.getNotExistAdminCard();
        assertEquals("Админ ADMIN_TESTUI_2 успешно удален", adminPage.getNotification());
    }

    @Feature("Документация")
    @Story("Успешное обновление оферты")
    @Test
    void updateOffer_11737() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
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
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
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
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        UpdateLegalDocWindow updateLegalDocWindow = adminPage.updateOffer();
        updateLegalDocWindow.uploadDocWindow();
        updateLegalDocWindow.closeWindowUpdateLegalDoc();
    }

    @Feature("Документация")
    @Story("Закрытие окна обновления политики обработки")
    @Test
    void closeWindowUpdateProcessingPolicy_11742() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        UpdateLegalDocWindow updateLegalDocWindow = adminPage.updateProcessingPolicy();
        updateLegalDocWindow.uploadDocWindow();
        updateLegalDocWindow.closeWindowUpdateLegalDoc();
    }


    @Feature("Документация")
    @Story("Успешное обновление приказа с даты в текущем месяце")
    @Test
    void updateOrderCurrentMonth_11278() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
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
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
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
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
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
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
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
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
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
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        UpdateOrderWindow updateOrderWindow = adminPage.updateOrder();
        updateOrderWindow.updateOrderWindow();
        assertEquals(DataHelper.getCurrentDate(), updateOrderWindow.getValuesButtonToday());
        updateOrderWindow.closeWindowUpdateOrder();
    }

    @Feature("Документация")
    @Story("Успешное обновление прайса с даты в текущем месяце ")
    @Test
    void updatePriceCurrentMonth_8929() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
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
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
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
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
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
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
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
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
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
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        UpdatePriceWindow updatePriceWindow = adminPage.updatePrice();
        updatePriceWindow.updatePriceWindow();
        assertEquals(DataHelper.getCurrentDate(), updatePriceWindow.getValuesButtonToday());
        updatePriceWindow.closeWindowUpdatePrice();
    }

    @Feature("Документация")
    @Story("Обновление офферты с невалидным файлом в формате Docx")
    @Test
    void updateOfferFormatDocx_11752() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        UpdateLegalDocWindow updateLegalDocWindow = adminPage.updateOffer();
        updateLegalDocWindow.uploadDocWindow();
        updateLegalDocWindow.uploadInvalidDoc("src/test/resources/Оферта,Политика обработки docx.docx");
        assertEquals("Допускаются файлы с расширением PDF", adminPage.getNotification());
    }

    @Feature("Документация")
    @Story("Обновление офферты с невалидным файлом в формате Xlsx")
    @Test
    void updateOfferFormatXlsx_11762() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        UpdateLegalDocWindow updateLegalDocWindow = adminPage.updateOffer();
        updateLegalDocWindow.uploadDocWindow();
        updateLegalDocWindow.uploadInvalidDoc("src/test/resources/Оферта, Политика обработки .xlsx.xlsx");
        assertEquals("Допускаются файлы с расширением PDF", adminPage.getNotification());
    }

    @Feature("Документация")
    @Story("Обновление офферты с невалидным файлом в формате Jpeg")
    @Test
    void updateOfferFormatJpeg_11765() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        UpdateLegalDocWindow updateLegalDocWindow = adminPage.updateOffer();
        updateLegalDocWindow.uploadDocWindow();
        updateLegalDocWindow.uploadInvalidDoc("src/test/resources/Оферта, Политика обработки jpeg.jpg");
        assertEquals("Допускаются файлы с расширением PDF", adminPage.getNotification());
    }

    @Feature("Документация")
    @Story("Обновление политики обработки с невалидным файлом в формате Docx")
    @Test
    void updateProcessingPolicyFormatDocx_11756() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        UpdateLegalDocWindow updateLegalDocWindow = adminPage.updateProcessingPolicy();
        updateLegalDocWindow.uploadDocWindow();
        updateLegalDocWindow.uploadInvalidDoc("src/test/resources/Оферта,Политика обработки docx.docx");
        assertEquals("Допускаются файлы с расширением PDF", adminPage.getNotification());
    }

    @Feature("Документация")
    @Story("Обновление политики обработки с невалидным файлом в формате Xlsx")
    @Test
    void updateProcessingPolicyFormatXlsx_11764() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        UpdateLegalDocWindow updateLegalDocWindow = adminPage.updateProcessingPolicy();
        updateLegalDocWindow.uploadDocWindow();
        updateLegalDocWindow.uploadInvalidDoc("src/test/resources/Оферта, Политика обработки .xlsx.xlsx");
        assertEquals("Допускаются файлы с расширением PDF", adminPage.getNotification());
    }

    @Feature("Документация")
    @Story("Обновление политики обработки с невалидным файлом в формате Jpeg")
    @Test
    void updateProcessingPolicyFormatJpeg_11767() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        UpdateLegalDocWindow updateLegalDocWindow = adminPage.updateProcessingPolicy();
        updateLegalDocWindow.uploadDocWindow();
        updateLegalDocWindow.uploadInvalidDoc("src/test/resources/Оферта, Политика обработки jpeg.jpg");
        assertEquals("Допускаются файлы с расширением PDF", adminPage.getNotification());
    }

    @Feature("Документация")
    @Story("Обновление приказа с ошибкой в строке")
    @Test
    void updateOrderStringError_11895() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
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
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
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
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
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
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
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
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
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
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
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
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
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
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
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
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
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
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
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
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        adminPage.scrollPageToBottom();
        adminPage.returnToStartPage();
        adminPage.returnButtonDisappears();
    }

    @Story("Закрытие уведомления на странице администрирования")
    @Test
    void closeNotification() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        UpdateLegalDocWindow updateLegalDocWindow = adminPage.updateProcessingPolicy();
        updateLegalDocWindow.uploadDocWindow();
        updateLegalDocWindow.uploadInvalidDoc("src/test/resources/Оферта, Политика обработки jpeg.jpg");
        assertEquals("Допускаются файлы с расширением PDF", adminPage.getNotification());
        adminPage.closeNotification();
    }

    @Feature("Документация")
    @Story("Загрузка файла с прайсом")
    @Test
    void downloadPrice() {
        HeaderBar headerBar = new HeaderBar();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.administrationPage();
        UpdatePriceWindow updatePriceWindow = adminPage.updatePrice();
        updatePriceWindow.updatePriceWindow();
        File downloadedFile = updatePriceWindow.downloadPriceDateActivation();
    }
}
