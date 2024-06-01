package admin.test;

import admin.data.DataTest;
import admin.utils.testUtils.AdminTestDecorator;
import admin.utils.dbUtils.DataBaseUtils;
import admin.utils.testUtils.NotificationDecorator;
import admin.utils.testUtils.TestSetupAuth;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import admin.pages.AuthorizationPage;
import admin.pages.DoctorsPage;
import admin.pages.HeaderBar;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Авторизация")
@Feature("Вход в админ-панель")
public class AuthorizationPageTest {

    private static boolean superAdminTestExecuted = false;
    private static boolean adminTestExecuted = false;
    private AuthorizationPage authPage;
    private HeaderBar headerBar;

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
        authPage = new AuthorizationPage();
        headerBar = new HeaderBar();
        TestSetupAuth.openAuthPage();
    }

    @AfterEach
    void tearDown() {
        if (superAdminTestExecuted && adminTestExecuted) {
            Selenide.closeWebDriver();
        }
    }

    @Story("Успешная авторизация супер-админа")
    @Test
    void authorizationSuperAdmin_11777() {
        authPage.authPage();
        DoctorsPage doctorPage = authPage.authorization(DataTest.getLoginSuperAdmin(), DataTest.getPasswordSuperAdmin());
        doctorPage.doctorsPage();
        headerBar.headerBarSuperAdmin();
        assertEquals("Супер-Администратор", headerBar.checkProfileInfoUser());
        assertEquals("0", DataBaseUtils.selectAdmin(DataTest.getLoginSuperAdmin()).getRole_id());
    }


    @Story("Успешная авторизация админа")
    @ExtendWith(AdminTestDecorator.class)
    @Test
    void authorizationAdmin() {
        authPage.authPage();
        DoctorsPage doctorPage = authPage.authorization(DataTest.getLoginAdminTest(), DataTest.getPasswordAdminTest());
        doctorPage.doctorsPage();
        headerBar.headerBarAdmin();
        assertEquals("Администратор", headerBar.checkProfileInfoUser());
        assertEquals("1", DataBaseUtils.selectAdmin(DataTest.getLoginAdminTest()).getRole_id());
    }

    @Story("Авторизация админа с неверным паролем")
    @ExtendWith({AdminTestDecorator.class, NotificationDecorator.class})
    @Test
    void authorizationAdminWrongPassword() {
        authPage.fillLoginField(DataTest.getLoginAdminTest());
        authPage.fillPasswordField("WWqq123456!78");
        authPage.pressToComeIn();
        authPage.authPage();
        assertEquals("Неверный логин или пароль", authPage.getNotification());
    }

    @Story("Авторизация админа с логином на кириллице")
    @ExtendWith(NotificationDecorator.class)
    @Test
    void authorizationAdminInvalidLogin() {
        authPage.fillLoginField("Админ_25");
        authPage.fillPasswordField(DataTest.getPasswordAdminTest());
        authPage.pressToComeIn();
        authPage.authPage();
        assertEquals("Первый символ должен быть латинской буквой или \"_\"", authPage.getNotification());
    }

    @Story("Авторизация админа с паролем с кириллицей")
    @ExtendWith(NotificationDecorator.class)
    @Test
    void authorizationAdminInvalidPassword() {
        authPage.fillLoginField(DataTest.getLoginAdminTest());
        authPage.fillPasswordField("ЫЫйй123456!");
        authPage.pressToComeIn();
        authPage.authPage();
        assertEquals("1 цифра, 1 спецсимвол, 1 латинская буква в верхнем и нижнем регистре", authPage.getNotification());
    }

    @Story("Авторизация несуществующего админа")
    @ExtendWith(NotificationDecorator.class)
    @Test
    void authorizationAdminNonExistent() {
        authPage.fillLoginField(DataTest.getLoginAdminTest());
        authPage.fillPasswordField(DataTest.getPasswordAdminTest());
        authPage.pressToComeIn();
        authPage.authPage();
        assertEquals("AuthorizationAdminClient::SignIn: Ошибка авторизации.", authPage.getNotification());
    }

    @Story("Авторизация админа с паролем из 7 символов")
    @ExtendWith(NotificationDecorator.class)
    @Test
    void authorizationAdminMinimalSymbol() {
        authPage.fillLoginField(DataTest.getLoginAdminTest());
        authPage.fillPasswordField("WwQ12!");
        authPage.pressToComeIn();
        authPage.authPage();
        assertEquals("Минимум 8 символов", authPage.getNotification());

    }

    @Story("Авторизация админа без логина")
    @ExtendWith(NotificationDecorator.class)
    @Test
    void authorizationAdminNotLogin() {
        authPage.fillPasswordField(DataTest.getPasswordAdminTest());
        authPage.pressToComeIn();
        authPage.authPage();
        assertEquals("Что-то пошло не по плану...", authPage.getNotification());
    }

    @Story("Авторизация админа без пароля")
    @ExtendWith(NotificationDecorator.class)
    @Test
    void authorizationAdminNotPassword() {
        authPage.fillLoginField(DataTest.getLoginAdminTest());
        authPage.pressToComeIn();
        authPage.authPage();
        assertEquals("Что-то пошло не по плану...", authPage.getNotification());
    }

    @Story("Авторизация админа с пустыми полями")
    @ExtendWith(NotificationDecorator.class)
    @Test
    void authorizationAdminEmptyFields() {
        authPage.pressToComeIn();
        authPage.authPage();
        assertEquals("Что-то пошло не по плану...", authPage.getNotification());
    }

    @Story("Авторизация админа с пустым полем логина после очистки")
    @ExtendWith(NotificationDecorator.class)
    @Test
    void authorizationAdminEmptyFieldLoginAfterClear() {
        authPage.fillLoginField(DataTest.getLoginAdminTest());
        authPage.fillPasswordField(DataTest.getPasswordAdminTest());
        authPage.clearLoginClickButton();
        authPage.clearPasswordField();
        authPage.pressToComeIn();
        authPage.authPage();
        assertEquals("Обязательное поле", authPage.getNotification());
    }

    @Story("Очистка поля логина через кнопку в форме авторизации")
    @Test
    void clearLoginFieldThroughButtonClear() {
        authPage.fillLoginField(DataTest.getLoginAdminTest());
        authPage.clearLoginClickButton();
        authPage.authPage();
        assertEquals("", authPage.getValueLoginField());
        assertEquals("Обязательное поле", authPage.getErrorFieldLogin());
    }

    @Story("Скрытие пароля при его вводе в поле пароля")
    @Test
    void fillPasswordHideValue() {
        authPage.fillPasswordField(DataTest.getPasswordAdminTest());
        assertTrue(authPage.isHidePassword());
        authPage.authPage();
    }

    @Story("Отображение введенного пароля в поле пароля")
    @Test
    void showPasswordValue() {
        authPage.fillPasswordField(DataTest.getPasswordAdminTest());
        authPage.showPassword();
        assertFalse(authPage.isHidePassword());
        authPage.authPage();
    }

    @Story("Скрытие отображенного пароля в поле пароля")
    @Test
    void hidePasswordValue() {
        authPage.fillPasswordField(DataTest.getPasswordAdminTest());
        authPage.hidePassword();
        assertTrue(authPage.isHidePassword());
        authPage.authPage();
    }

    @Story("Отображение уведомления об обязательности поля логина")
    @Test
    void authorizationAdminObligatoryLoginField() {
        authPage.clickLoginField();
        authPage.clickPasswordField();
        authPage.hoverLoginField();
        authPage.authPage();
        assertEquals("Обязательное поле", authPage.getErrorFieldLogin());
    }

    @Story("Отображение уведомления об обязательности поля пароля")
    @Test
    void authorizationAdminObligatoryLoginPassword() {
        authPage.clickPasswordField();
        authPage.clickLoginField();
        authPage.hoverPasswordField();
        authPage.authPage();
        assertEquals("Обязательное поле", authPage.getErrorFieldPassword());
    }


    @Story("Ввод валидного логина из 31 и 32 символов")
    @ParameterizedTest
    @ValueSource(strings = {"ANNA_TEST_ADMIN123456789_ANNA_1", "ANNA_TEST_ADMIN123456789_ANNA_12"})
    void authorizationAdminLogin31Symbol(String login) {
        authPage.fillLoginField(login);
        authPage.clickPasswordField();
        authPage.hoverLoginField();
        assertFalse(authPage.isErrorLoginAppear());
    }

    @Story("Ввод не валидного логина из 33 символов")
    @Test
    void authorizationAdminLogin33Symbol() {
        authPage.fillLoginField("ANNA_TEST_ADMIN123456789_ANNA_123");
        authPage.clickPasswordField();
        authPage.hoverLoginField();
        assertEquals("Максимальная длина 32 символа", authPage.getErrorFieldLogin());
    }

    @Story("Ввод не валидного логина на кириллице и логина, начинающегося с цифры")
    @ParameterizedTest
    @ValueSource(strings = {"АННА_ТЕСТ", "1ANNA_TEST"})
    void authorizationAdminLoginCyrillicValue(String login) {
        authPage.fillLoginField(login);
        authPage.clickPasswordField();
        authPage.hoverLoginField();
        assertEquals("Первый символ должен быть латинской буквой или \"_\"", authPage.getErrorFieldLogin());
    }

    @Story("Ввод не валидного логина, начинающегося с латиницы, далее на кириллице и логина с пробелом")
    @ParameterizedTest
    @ValueSource(strings = {"AННА_ТЕСТ", "ANNA TEST"})
    void authorizationAdminLoginLatinBeginCyrillicValue(String login) {
        authPage.fillLoginField(login);
        authPage.clickPasswordField();
        authPage.hoverLoginField();
        assertEquals("Доступны только числа, латиница и \"_\"", authPage.getErrorFieldLogin());
    }

    @Story("Ввод не валидного пароля из 7 символов")
    @Test
    void authorizationAdminPassword7Symbol() {
        authPage.fillPasswordField("Wwqq12#");
        authPage.clickLoginField();
        authPage.hoverPasswordField();
        assertEquals("Минимум 8 символов", authPage.getErrorFieldPassword());
    }

    @Story("Ввод валидного пароля из 8,9,24 и 25 символов")
    @ParameterizedTest
    @ValueSource(strings = {"Wwqq123#", "Wwqq1234#", "Wwqq123456789#QQgg123456", "Wwqq123456789#QQgg1234567"})
    void authorizationAdminPassword8Symbol(String password) {
        authPage.fillPasswordField(password);
        authPage.clickLoginField();
        authPage.hoverPasswordField();
        assertFalse(authPage.isErrorPasswordAppear());
    }

    @Story("Ввод не валидного пароля из 26 символов")
    @Test
    void authorizationAdminPassword26Symbol() {
        authPage.fillPasswordField("Wwqq123456789#QQgg12345678");
        authPage.clickLoginField();
        authPage.hoverPasswordField();
        assertEquals("Максимум 25 символов", authPage.getErrorFieldPassword());
    }

    @Story("Ввод не валидного пароля без латинской буквы, без спецсимвола, без латинской буквы в верхнем регистре,без латинской буквы в нижнем регистре, без цифр, с пробелом ")
    @ParameterizedTest
    @ValueSource(strings = {"123456789!", "123456789Ss", "123456789!ss", "123456789!SS", "WwqqLLpp!!", "Wwqq 123456 #"})
    void authorizationAdminPasswordNotLatinValue(String password) {
        authPage.fillPasswordField(password);
        authPage.clickLoginField();
        authPage.hoverPasswordField();
        assertEquals("1 цифра, 1 спецсимвол, 1 латинская буква в верхнем и нижнем регистре", authPage.getErrorFieldPassword());
    }

    @Story("Закрытие уведомления на странице авторизации по таймауту")
    @Test
    void closeNotificationTimeout() throws InterruptedException {
        authPage.pressToComeIn();
        Thread.sleep(6000);
        assertFalse(authPage.notificationAppear());
    }

    @Story("Закрытие уведомления на странице авторизации")
    @Test
    void closeNotification() {
        authPage.pressToComeIn();
        authPage.closeNotification();
        assertFalse(authPage.notificationAppear());
    }

}