package ru.adminlk.clinica.test;


import ru.adminlk.clinica.pages.HeaderMenu.UserPanel;
import ru.adminlk.clinica.utils.dbUtils.dbUtils.DataBaseQuery;
import com.codeborne.selenide.Condition;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import ru.adminlk.clinica.pages.AuthorizationPage.AuthorizationPage;
import ru.adminlk.clinica.pages.DoctorsPage.DoctorsPage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import java.time.Duration;

import static ru.adminlk.clinica.data.FinalTestData.UserData.*;
import static ru.adminlk.clinica.utils.testsUtils.DataGenerator.generateLogin;
import static ru.adminlk.clinica.utils.testsUtils.DataGenerator.generatePassword;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Авторизация")
@Feature("Вход и выход в админ-панели")
@DisplayName("Страница Авторизации")
public class AuthorizationPageTest extends BaseTest {

    @BeforeEach
    void setUp() {
        openAdminPanel();
        authPage.verifyAuthPage();
    }

    @AfterEach()
    void closeWebDriver() {
        closeDriver();
    }


    @Story("Успешная авторизация супер-админа")
    @DisplayName("Успешная авторизация супер-админа")
    @Test
    void authSuperAdmin() {
        DoctorsPage doctorPage = authPage.authorization(LOGIN_SUPER_ADMIN, PASSWORD_SUPER_ADMIN);
        doctorPage.verifyDoctorsPage();
        headerMenu.verifyHeaderBarSuperAdmin();
        UserPanel userPanel = headerMenu.clickButtonUserPanel();
        userPanel.verifyUserPanelSuperAdmin();
        assertEquals("Супер-Администратор", userPanel.getProfileInfoUser());
        assertEquals(LOGIN_SUPER_ADMIN, userPanel.getLogin());
        assertEquals(0, DataBaseQuery.selectAdmin(LOGIN_SUPER_ADMIN).getRole_id());
        assertEquals("SIGN_IN_ADMIN_SUCCESS", DataBaseQuery.selectLog(LOGIN_SUPER_ADMIN).getCode());
    }

    @Story("Успешная авторизация админа")
    @DisplayName("Успешная авторизация админа")
    @Test
    void authAdmin() {
        DoctorsPage doctorPage = authPage.authorization(LOGIN_ADMIN, PASSWORD_ADMIN);
        doctorPage.verifyDoctorsPage();
        headerMenu.verifyHeaderBarAdmin();
        UserPanel userPanel = headerMenu.clickButtonUserPanel();
        userPanel.verifyUserPanelAdmin();
        assertEquals("Администратор", userPanel.getProfileInfoUser());
        assertEquals(LOGIN_ADMIN, userPanel.getLogin());
        assertEquals(1, DataBaseQuery.selectAdmin(LOGIN_ADMIN).getRole_id());
        assertEquals("SIGN_IN_ADMIN_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Story("Авторизация админа с неверным паролем")
    @DisplayName("Авторизация админа с неверным паролем")
    @Test
    void authAdminWithWrongPassword() {
        authPage.fillLoginField(LOGIN_ADMIN)
                .fillPasswordField(generatePassword())
                .clickToComeIn();
        assertTrue(authPage.isEnabledComeInButton());
        assertEquals("Неверный логин или пароль", authPage.getTextNotification());
    }

    @Story("Авторизация несуществующего админа")
    @DisplayName("Авторизация несуществующего админа")
    @Test
    void authNonExistentAdmin() {
        authPage.fillLoginField(generateLogin())
                .fillPasswordField(generatePassword())
                .clickToComeIn();
        assertTrue(authPage.isEnabledComeInButton());
        assertEquals("AuthorizationAdminClient::SignIn: Ошибка авторизации.", authPage.getTextNotification());
    }

    @Story("Авторизация админа с пустым полем логина")
    @DisplayName("Авторизация админа с пустым полем логина")
    @Test
    void authAdminEmptyFieldLogin() {
        authPage.fillPasswordField(generatePassword());
        assertFalse(authPage.isEnabledComeInButton());
    }

    @Story("Авторизация админа с пустым полем пароля")
    @DisplayName("Авторизация админа с пустым полем пароля")
    @Test
    void authAdminEmptyFieldPassword() {
        authPage.fillLoginField(generateLogin());
        assertFalse(authPage.isEnabledComeInButton());
    }

    @Story("Очистка поля логина через кнопку в форме авторизации")
    @DisplayName("Очистка поля логина через кнопку в форме авторизации")
    @Test
    void clearFieldLoginVerifyAuthPageThroughButtonClear() {
        authPage.fillLoginField(generateLogin())
                .clickClearButtonLoginField();
        assertEquals("", authPage.getValueLoginField());
        assertEquals("Обязательное поле", authPage.getErrorFieldLogin());
    }

    @Story("Скрытие пароля при его вводе в поле пароля")
    @DisplayName("Скрытие пароля при его вводе в поле пароля")
    @Test
    void hideValuePasswordWhenFillPasswordField() {
        authPage.fillPasswordField(generatePassword());
        assertTrue(authPage.isHidePassword());
    }

    @Story("Отображение и скрытие введенного пароля в поле пароля")
    @DisplayName("Отображение и скрытие введенного пароля в поле пароля")
    @Test
    void showValuePasswordField() {
        authPage.fillPasswordField(generatePassword())
                .showPassword();
        assertFalse(authPage.isHidePassword());
        authPage.hidePassword();
        assertTrue(authPage.isHidePassword());
    }

    @Story("Отображение уведомления об обязательности полей")
    @DisplayName("Отображение уведомления об обязательности полей")
    @Test
    void displayNotificationAboutRequiredFieldsVerifyAuthPage() {
        authPage.clickLoginField()
                .clickPasswordField();
        assertEquals("Обязательное поле", authPage.getErrorFieldLogin());
        assertEquals("Обязательное поле", authPage.getErrorFieldPassword());
    }

    @Story("Ввод валидных граничных значений логина из 31 и 32 символов")
    @DisplayName("Ввод валидных граничных значений логина из 31 и 32 символов")
    @ParameterizedTest
    @ValueSource(strings = {"ANNA_TEST_ADMIN123456789_ANNA_1", "ANNA_TEST_ADMIN123456789_ANNA_12"})
    void fillLimitValidValuesLoginVerifyAuthPage_31_32_Symbol(String login) {
        authPage.fillLoginField(login);
        assertFalse(authPage.isErrorLoginAppear());
        assertFalse(authPage.isEnabledComeInButton());
    }

    @Story("Ввод не валидных граничных значений логина из 33 символов")
    @DisplayName("Ввод не валидных граничных значений логина из 33 символов")
    @Test
    void fillLimitInvalidValuesLoginVerifyAuthPage_33_Symbol() {
        authPage.fillLoginField("ANNA_TEST_ADMIN123456789_ANNA_123");
        assertEquals("Максимальная длина 32 символа", authPage.getErrorFieldLogin());
        assertFalse(authPage.isEnabledComeInButton());
    }

    @Story("Ввод не валидного логина с не валидным первым символом")
    @DisplayName("Ввод не валидного логина с не валидным первым символом")
    @ParameterizedTest
    @ValueSource(strings = {"БRUCE_LI", "1ANNA_TEST", "Админ_25"})
    void fillInvalidValuesFirstSymbolLoginVerifyAuthPage(String login) {
        authPage.fillLoginField(login);
        assertEquals("Первый символ должен быть латинской буквой или \"_\"", authPage.getErrorFieldLogin());
        assertFalse(authPage.isEnabledComeInButton());
    }

    @Story("Ввод не валидного логина")
    @DisplayName("Ввод не валидного логина")
    @ParameterizedTest
    @ValueSource(strings = {"AННА_ТЕСТ", "ANNA TEST"})
    void fillInvalidValuesLoginVerifyAuthPage(String login) {
        authPage.fillLoginField(login);
        assertEquals("Доступны только числа, латиница и \"_\"", authPage.getErrorFieldLogin());
        assertFalse(authPage.isEnabledComeInButton());
    }

    @Story("Ввод валидных граничных значений пароля из 8,9,24 и 25 символов")
    @DisplayName("Ввод валидных граничных значений пароля из 8,9,24 и 25 символов")
    @ParameterizedTest
    @ValueSource(strings = {"Wwqq123#", "Wwqq1234#", "Wwqq123456789#QQgg123456", "Wwqq123456789#QQgg1234567"})
    void fillLimitValidValuesPasswordVerifyAuthPage_8_9_24_25_Symbol(String password) {
        authPage.fillPasswordField(password);
        assertFalse(authPage.isErrorPasswordAppear());
        assertFalse(authPage.isEnabledComeInButton());
    }

    @Story("Ввод не валидных граничных значений пароля из 7 и 26 символов")
    @DisplayName("Ввод не валидных граничных значений пароля из 7 и 26 символов")
    @ParameterizedTest
    @ValueSource(strings = {"Wwqq12#", "Wwqq123456789#QQgg12345678"})
    void fillLimitInvalidValuesPasswordVerifyAuthPage_7_26_Symbol(String password) {
        authPage.fillPasswordField(password);
        assertEquals("Пароль не валиден", authPage.getErrorFieldPassword());
        assertFalse(authPage.isEnabledComeInButton());
    }

    @Story("Ввод не валидного пароля")
    @DisplayName("Ввод не валидного пароля")
    @ParameterizedTest
    @ValueSource(strings = {"123456789!", "123456789Ss", "123456789!ss", "123456789!SS", "WwqqLLpp!!", "Wwqq 123456 #"})
    void fillInvalidValuesPasswordVerifyAuthPage(String password) {
        authPage.fillPasswordField(password);
        assertEquals("Пароль не валиден", authPage.getErrorFieldPassword());
        assertFalse(authPage.isEnabledComeInButton());
    }

    @Story("Закрытие уведомления на странице авторизации по таймауту")
    @DisplayName("Закрытие уведомления на странице авторизации по таймауту")
    @Test
    void closeNotificationTimeout() {
        authPage.fillLoginField(LOGIN_ADMIN)
                .fillPasswordField(generatePassword());
        assertTrue(authPage.isEnabledComeInButton());
        authPage.clickToComeIn();
        basePage.getNotification().shouldBe(Condition.visible);
        assertTrue(basePage.isNotificationAppear());
        basePage.getNotification().shouldBe(Condition.disappear, Duration.ofSeconds(7));
        assertFalse(basePage.isNotificationAppear());
    }

    @Story("Закрытие уведомления на странице авторизации")
    @DisplayName("Закрытие уведомления на странице авторизации")
    @Test
    void closeNotification() {
        authPage.fillLoginField(LOGIN_ADMIN)
                .fillPasswordField(generatePassword());
        assertTrue(authPage.isEnabledComeInButton());
        authPage.clickToComeIn();
        basePage.getNotification().should(Condition.visible);
        assertTrue(basePage.isNotificationAppear());
        basePage.closeNotification();
        assertFalse(basePage.isNotificationAppear());
    }

    @Story("Успешный выход из админ-панели")
    @DisplayName("Успешный выход из админ-панели")
    @Test
    void exitAdminPanel() {
        authPage.authorization(LOGIN_SUPER_ADMIN, PASSWORD_SUPER_ADMIN);
        UserPanel userPanel = headerMenu.clickButtonUserPanel();
        userPanel.verifyUserPanelSuperAdmin();
        AuthorizationPage authPage = userPanel.clickButtonExitAdminPanel();
        authPage.verifyAuthPage();
        assertEquals("SIGN_OUT_ADMIN_SUCCESS", DataBaseQuery.selectLog(LOGIN_SUPER_ADMIN).getCode());
    }
}