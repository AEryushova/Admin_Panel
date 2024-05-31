package admin.test;

import admin.data.DataTest;
import admin.utils.DataBaseUtils;
import admin.utils.TestSetupAPI;
import admin.utils.TestSetupAuthAdminPanel;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import admin.pages.AuthorizationPage;
import admin.pages.DoctorsPage;
import admin.pages.HeaderBar;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Авторизация")
@Feature("Вход в админ-панель")
public class AuthorizationPageTest {

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
        TestSetupAuthAdminPanel.openAuthPage();
    }

    @Story("Успешная авторизация супер-админа")
    @Test
    void authorizationSuperAdmin_11777() {
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authPage();
        DoctorsPage doctorPage = authorizationPage.authorization(DataTest.getLoginSuperAdmin(),DataTest.getPasswordSuperAdmin());
        doctorPage.doctorsPage();
        HeaderBar headerBar = new HeaderBar();
        headerBar.headerBarSuperAdmin();
        assertEquals("Супер-Администратор", headerBar.checkProfileInfoUser());
        assertEquals("0", DataBaseUtils.selectAdmin(DataTest.getLoginSuperAdmin()).getRole_id());
        headerBar.exitAdminPanel();
    }

    @Story("Успешная авторизация админа")
    @Test
    void authorizationAdmin_8594() {
        TestSetupAPI.authRequest(DataTest.getLoginSuperAdmin(),DataTest.getPasswordSuperAdmin());
        TestSetupAPI.createAdmin(DataTest.getLoginAdminTest(),DataTest.getPasswordAdminTest());
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authPage();
        DoctorsPage doctorPage = authorizationPage.authorization(DataTest.getLoginAdminTest(),DataTest.getPasswordAdminTest());
        doctorPage.doctorsPage();
        HeaderBar headerBar = new HeaderBar();
        headerBar.headerBarAdmin();
        assertEquals("Администратор", headerBar.checkProfileInfoUser());
        assertEquals("1", DataBaseUtils.selectAdmin(DataTest.getLoginAdminTest()).getRole_id());
        headerBar.exitAdminPanel();
        TestSetupAPI.deleteAdmin(DataTest.getLoginAdminTest());
    }

    @Story("Авторизация админа с неверным паролем")
    @Test
    void authorizationAdminWrongPassword_8603() {
        TestSetupAPI.authRequest(DataTest.getLoginSuperAdmin(),DataTest.getPasswordSuperAdmin());
        TestSetupAPI.createAdmin(DataTest.getLoginAdminTest(),DataTest.getPasswordAdminTest());
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authPage();
        authorizationPage.fillLoginField(DataTest.getLoginAdminTest());
        authorizationPage.fillPasswordField("WWqq123456!78");
        authorizationPage.subAuthButton();
        authorizationPage.authPage();
        assertEquals("Неверный логин или пароль", authorizationPage.getNotification());
        TestSetupAPI.deleteAdmin(DataTest.getLoginAdminTest());
    }

    @Story("Авторизация админа с логином на кириллице")
    @Test
    void authorizationAdminInvalidLogin_11776() {
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authPage();
        authorizationPage.fillLoginField("Админ_25");
        authorizationPage.fillPasswordField(DataTest.getPasswordAdminTest());
        authorizationPage.subAuthButton();
        authorizationPage.authPage();
        assertEquals("Первый символ должен быть латинской буквой или \"_\"", authorizationPage.getNotification());
    }

    @Story("Авторизация админа с паролем с кириллицей")
    @Test
    void authorizationAdminInvalidPassword_11775() {
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authPage();
        authorizationPage.fillLoginField(DataTest.getLoginAdminTest());
        authorizationPage.fillPasswordField("ЫЫйй123456!");
        authorizationPage.subAuthButton();
        authorizationPage.authPage();
        assertEquals("1 цифра, 1 спецсимвол, 1 латинская буква в верхнем и нижнем регистре", authorizationPage.getNotification());
    }

    @Story("Авторизация несуществующего админа")
    @Test
    void authorizationAdminNonExistent_8605() {
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authPage();
        authorizationPage.fillLoginField(DataTest.getLoginAdminTest());
        authorizationPage.fillPasswordField(DataTest.getPasswordAdminTest());
        authorizationPage.subAuthButton();
        authorizationPage.authPage();
        assertEquals("AuthorizationAdminClient::SignIn: Ошибка авторизации.", authorizationPage.getNotification());
    }

    @Story("Авторизация админа с паролем из 7 символов")
    @Test
    void authorizationAdminMinimalSymbol_11688() {
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authPage();
        authorizationPage.fillLoginField(DataTest.getLoginAdminTest());
        authorizationPage.fillPasswordField("WwQ12!");
        authorizationPage.subAuthButton();
        authorizationPage.authPage();
        assertEquals("Минимум 8 символов", authorizationPage.getNotification());

    }

    @Story("Авторизация админа без логина")
    @Test
    void authorizationAdminNotLogin_9335() {
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authPage();
        authorizationPage.fillPasswordField(DataTest.getPasswordAdminTest());
        authorizationPage.subAuthButton();
        authorizationPage.authPage();
        assertEquals("Что-то пошло не по плану...", authorizationPage.getNotification());;
    }

    @Story("Авторизация админа без пароля")
    @Test
    void authorizationAdminNotPassword_8672() {
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authPage();
        authorizationPage.fillLoginField(DataTest.getLoginAdminTest());
        authorizationPage.subAuthButton();
        authorizationPage.authPage();
        assertEquals("Что-то пошло не по плану...", authorizationPage.getNotification());
    }

    @Story("Авторизация админа с пустыми полями")
    @Test
    void authorizationAdminEmptyFields_8673() {
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authPage();
        authorizationPage.subAuthButton();
        authorizationPage.authPage();
        assertEquals("Что-то пошло не по плану...", authorizationPage.getNotification());
    }

    @Story("Авторизация админа с пустым полем логина после очистки")
    @Test
    void authorizationAdminEmptyFieldLoginAfterClear_8673() {
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authPage();
        authorizationPage.fillLoginField(DataTest.getLoginAdminTest());
        authorizationPage.fillPasswordField(DataTest.getPasswordAdminTest());
        authorizationPage.clearLoginClickButton();
        authorizationPage.clearPasswordField();
        authorizationPage.subAuthButton();
        authorizationPage.authPage();
        assertEquals("Обязательное поле", authorizationPage.getNotification());
    }

    @Story("Очистка поля логина через кнопку в форме авторизации")
    @Test
    void clearLoginFieldThroughButtonClear_11778() {
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authPage();
        authorizationPage.fillLoginField(DataTest.getLoginAdminTest());
        authorizationPage.clearLoginClickButton();
        authorizationPage.authPage();
        assertEquals("", authorizationPage.getValueLoginField());
        assertEquals("Обязательное поле", authorizationPage.getErrorFieldLogin());
    }

    @Story("Отображение введенного пароля в поле пароля")
    @Test
    void showPasswordValue_8663() {
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authPage();
        authorizationPage.fillPasswordField(DataTest.getPasswordAdminTest());
        assertTrue(authorizationPage.showPassword());
        authorizationPage.authPage();
    }

    @Story("Скрытие отображенного пароля в поле пароля")
    @Test
    void hidePasswordValue_8663() {
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authPage();
        authorizationPage.fillPasswordField(DataTest.getPasswordAdminTest());
        authorizationPage.showPassword();
        assertTrue(authorizationPage.hidePassword());
        authorizationPage.authPage();
    }

    @Story("Отображение уведомления об обязательности поля логина")
    @Test
    void authorizationAdminObligatoryLoginField_11905() {
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authPage();
        authorizationPage.clickLoginField();
        authorizationPage.clickPasswordField();
        authorizationPage.hoverLoginField();
        authorizationPage.authPage();
        assertEquals("Обязательное поле", authorizationPage.getErrorFieldLogin());
    }

    @Story("Отображение уведомления об обязательности поля пароля")
    @Test
    void authorizationAdminObligatoryLoginPassword_11906() {
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authPage();
        authorizationPage.authPage();
        authorizationPage.clickPasswordField();
        authorizationPage.clickLoginField();
        authorizationPage.hoverPasswordField();
        authorizationPage.authPage();
        assertEquals("Обязательное поле", authorizationPage.getErrorFieldPassword());
    }

    @Story("Ввод валидного логина из 31 символа")
    @Test
    void authorizationAdminLogin31Symbol() {
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authPage();
        authorizationPage.fillLoginField("ANNA_TEST_ADMIN123456789_ANNA_1");
        authorizationPage.clickPasswordField();
        authorizationPage.hoverLoginField();
        authorizationPage.authPage();
        assertFalse(authorizationPage.isErrorLoginAppear());
    }

    @Story("Ввод валидного логина из 32 символов")
    @Test
    void authorizationAdminLogin32Symbol() {
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authPage();
        authorizationPage.fillLoginField("ANNA_TEST_ADMIN123456789_ANNA_12");
        authorizationPage.clickPasswordField();
        authorizationPage.hoverLoginField();
        authorizationPage.authPage();
        assertFalse(authorizationPage.isErrorLoginAppear());
    }

    @Story("Ввод не валидного логина из 33 символов")
    @Test
    void authorizationAdminLogin33Symbol() {
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authPage();
        authorizationPage.fillLoginField("ANNA_TEST_ADMIN123456789_ANNA_123");
        authorizationPage.clickPasswordField();
        authorizationPage.hoverLoginField();
        authorizationPage.authPage();
        assertEquals("Максимальная длина 32 символа", authorizationPage.getErrorFieldLogin());
    }

    @Story("Ввод не валидного логина на кириллице")
    @Test
    void authorizationAdminLoginCyrillicValue() {
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authPage();
        authorizationPage.fillLoginField("АННА_ТЕСТ");
        authorizationPage.clickPasswordField();
        authorizationPage.hoverLoginField();
        authorizationPage.authPage();
        assertEquals("Первый символ должен быть латинской буквой или \"_\"", authorizationPage.getErrorFieldLogin());
    }

    @Story("Ввод не валидного логина, начинающегося с цифры")
    @Test
    void authorizationAdminLoginNumberBegin() {
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authPage();
        authorizationPage.fillLoginField("1ANNA_TEST");
        authorizationPage.clickPasswordField();
        authorizationPage.hoverLoginField();
        authorizationPage.authPage();
        assertEquals("Первый символ должен быть латинской буквой или \"_\"", authorizationPage.getErrorFieldLogin());
    }

    @Story("Ввод не валидного логина, начинающегося с латиницы, далее на кириллице")
    @Test
    void authorizationAdminLoginLatinBeginCyrillicValue() {
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authPage();
        authorizationPage.fillLoginField("AННА_ТЕСТ");
        authorizationPage.clickPasswordField();
        authorizationPage.hoverLoginField();
        authorizationPage.authPage();
        assertEquals("Доступны только числа, латиница и \"_\"", authorizationPage.getErrorFieldLogin());
    }

    @Story("Ввод не валидного логина с пробелом")
    @Test
    void authorizationAdminLoginWithSpace() {
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authPage();
        authorizationPage.fillLoginField("ANNA TEST");
        authorizationPage.clickPasswordField();
        authorizationPage.hoverLoginField();
        authorizationPage.authPage();
        assertEquals("Доступны только числа, латиница и \"_\"", authorizationPage.getErrorFieldLogin());
    }

    @Story("Ввод не валидного пароля из 7 символов")
    @Test
    void authorizationAdminPassword7Symbol() {
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authPage();
        authorizationPage.fillPasswordField("Wwqq12#");
        authorizationPage.clickLoginField();
        authorizationPage.hoverPasswordField();
        authorizationPage.authPage();
        assertEquals("Минимум 8 символов", authorizationPage.getErrorFieldPassword());
    }

    @Story("Ввод валидного пароля из 8 символов")
    @Test
    void authorizationAdminPassword8Symbol() {
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authPage();
        authorizationPage.fillPasswordField("Wwqq123#");
        authorizationPage.clickLoginField();
        authorizationPage.hoverPasswordField();
        authorizationPage.authPage();
        assertFalse(authorizationPage.isErrorPasswordAppear());
    }

    @Story("Ввод валидного пароля из 9 символов")
    @Test
    void authorizationAdminPassword9Symbol() {
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authPage();
        authorizationPage.fillPasswordField("Wwqq1234#");
        authorizationPage.clickLoginField();
        authorizationPage.hoverPasswordField();
        authorizationPage.authPage();
        assertFalse(authorizationPage.isErrorPasswordAppear());
    }

    @Story("Ввод валидного пароля из 24 символов")
    @Test
    void authorizationAdminPassword24Symbol() {
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authPage();
        authorizationPage.fillPasswordField("Wwqq123456789#QQgg123456");
        authorizationPage.clickLoginField();
        authorizationPage.hoverPasswordField();
        authorizationPage.authPage();
        assertFalse(authorizationPage.isErrorPasswordAppear());
    }

    @Story("Ввод валидного пароля из 25 символов")
    @Test
    void authorizationAdminPassword25Symbol() {
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authPage();
        authorizationPage.fillPasswordField("Wwqq123456789#QQgg1234567");
        authorizationPage.clickLoginField();
        authorizationPage.hoverPasswordField();
        authorizationPage.authPage();
        assertFalse(authorizationPage.isErrorPasswordAppear());
    }

    @Story("Ввод не валидного пароля из 26 символов")
    @Test
    void authorizationAdminPassword26Symbol() {
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authPage();
        authorizationPage.fillPasswordField("Wwqq123456789#QQgg12345678");
        authorizationPage.clickLoginField();
        authorizationPage.hoverPasswordField();
        authorizationPage.authPage();
        assertEquals("Максимум 25 символов", authorizationPage.getErrorFieldPassword());
    }

    @Story("Ввод не валидного пароля без латинской буквы")
    @Test
    void authorizationAdminPasswordNotLatinValue() {
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authPage();
        authorizationPage.fillPasswordField("123456789!");
        authorizationPage.clickLoginField();
        authorizationPage.hoverPasswordField();
        authorizationPage.authPage();
        assertEquals("1 цифра, 1 спецсимвол, 1 латинская буква в верхнем и нижнем регистре", authorizationPage.getErrorFieldPassword());
    }

    @Story("Ввод не валидного пароля без спецсимвола")
    @Test
    void authorizationAdminPasswordNotSymbol() {
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authPage();
        authorizationPage.fillPasswordField("123456789Ss");
        authorizationPage.clickLoginField();
        authorizationPage.hoverPasswordField();
        authorizationPage.authPage();
        assertEquals("1 цифра, 1 спецсимвол, 1 латинская буква в верхнем и нижнем регистре", authorizationPage.getErrorFieldPassword());
    }

    @Story("Ввод не валидного пароля без латинской буквы в верхнем регистре")
    @Test
    void authorizationAdminPasswordNotToUpperCase() {
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authPage();
        authorizationPage.fillPasswordField("123456789!ss");
        authorizationPage.clickLoginField();
        authorizationPage.hoverPasswordField();
        authorizationPage.authPage();
        assertEquals("1 цифра, 1 спецсимвол, 1 латинская буква в верхнем и нижнем регистре", authorizationPage.getErrorFieldPassword());
    }

    @Story("Ввод не валидного пароля без латинской буквы в нижнем регистре")
    @Test
    void authorizationAdminPasswordNotToLowerCase() {
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authPage();
        authorizationPage.fillPasswordField("123456789!SS");
        authorizationPage.clickLoginField();
        authorizationPage.hoverPasswordField();
        authorizationPage.authPage();
        assertEquals("1 цифра, 1 спецсимвол, 1 латинская буква в верхнем и нижнем регистре", authorizationPage.getErrorFieldPassword());
    }

    @Story("Ввод не валидного пароля без цифры")
    @Test
    void authorizationAdminPasswordNotNumber() {
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authPage();
        authorizationPage.fillPasswordField("WwqqLLpp!!");
        authorizationPage.clickLoginField();
        authorizationPage.hoverPasswordField();
        authorizationPage.authPage();
        assertEquals("1 цифра, 1 спецсимвол, 1 латинская буква в верхнем и нижнем регистре", authorizationPage.getErrorFieldPassword());
    }

    @Story("Закрытие уведомления на странице авторизации по таймауту")
    @Test
    void closeNotificationTimeout() throws InterruptedException {
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authPage();
        authorizationPage.subAuthButton();
        authorizationPage.authPage();
        assertEquals("Что-то пошло не по плану...", authorizationPage.getNotification());
        Thread.sleep(6000);
        assertFalse(authorizationPage.notificationDisappears());
    }

    @Story("Закрытие уведомления на странице авторизации")
    @Test
    void closeNotification() {
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authPage();
        authorizationPage.subAuthButton();
        authorizationPage.authPage();
        assertEquals("Что-то пошло не по плану...", authorizationPage.getNotification());
        authorizationPage.closeNotification();
        assertFalse(authorizationPage.notificationDisappears());
    }

    @Story("Выход из админ-панели")
    @Test
    void exitAdminPanel() {
        TestSetupAPI.authRequest(DataTest.getLoginSuperAdmin(),DataTest.getPasswordSuperAdmin());
        TestSetupAPI.createAdmin(DataTest.getLoginAdminTest(),DataTest.getPasswordAdminTest());
        AuthorizationPage authorizationPage = new AuthorizationPage();
        authorizationPage.authPage();
        DoctorsPage doctorPage = authorizationPage.authorization(DataTest.getLoginAdminTest(),DataTest.getPasswordAdminTest());
        doctorPage.doctorsPage();
        HeaderBar headerBar = new HeaderBar();
        headerBar.headerBarAdmin();
        headerBar.openAndCloseProfileAdmin();
        headerBar.exitAdminPanel();
        authorizationPage.authPage();
        TestSetupAPI.deleteAdmin(DataTest.getLoginAdminTest());
    }
}