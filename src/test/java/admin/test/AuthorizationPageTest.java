package admin.test;

import admin.data.DataConfig;
import admin.pages.BasePage.BasePage;
import admin.pages.HeaderMenu.UserPanel;
import admin.utils.dbUtils.DataBaseQuery;
import admin.utils.preparationDataTests.general.AllureDecorator;
import admin.utils.preparationDataTests.authorization.CloseWebDriverDecorator;
import admin.utils.testUtils.*;
import admin.utils.preparationDataTests.general.NotificationDecorator;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import admin.pages.AuthorizationPage.AuthorizationPage;
import admin.pages.DoctorsPage.DoctorsPage;
import admin.pages.HeaderMenu.HeaderMenu;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Авторизация")
@Feature("Вход в админ-панель")
public class AuthorizationPageTest extends BaseTest {

    private AuthorizationPage authPage;
    private HeaderMenu headerMenu;
    private BasePage basePage;

    @ExtendWith(AllureDecorator.class)

    @BeforeEach
    void setUp() {
        BrowserManager.openAdminPanel();
        authPage = new AuthorizationPage();
        headerMenu = new HeaderMenu();
        basePage = new BasePage();
    }

    @Story("Успешная авторизация супер-админа")
    @ExtendWith(CloseWebDriverDecorator.class)
    @Test
    void authorizationSuperAdmin() {
        authPage.authPage();
        DoctorsPage doctorPage = authPage.authorization(DataConfig.UserData.getLoginSuperAdmin(), DataConfig.UserData.getPasswordSuperAdmin());
        doctorPage.doctorsPage();
        headerMenu.headerBarSuperAdmin();
        UserPanel userPanel=headerMenu.openAndCloseProfile();
        userPanel.userPanelSuperAdmin();
        assertEquals("Супер-Администратор", userPanel.checkProfileInfoUser());
        assertEquals(DataConfig.UserData.getLoginSuperAdmin(),userPanel.checkLogin());
        assertEquals(0, DataBaseQuery.selectAdmin(DataConfig.UserData.getLoginSuperAdmin()).getRole_id());
    }


    @Story("Успешная авторизация админа")
    @ExtendWith(CloseWebDriverDecorator.class)
    @Test
    void authorizationAdmin() {
        authPage.authPage();
        DoctorsPage doctorPage = authPage.authorization(DataConfig.UserData.getLoginAdmin(), DataConfig.UserData.getPasswordAdmin());
        doctorPage.doctorsPage();
        headerMenu.headerBarAdmin();
        UserPanel userPanel=headerMenu.openAndCloseProfile();
        userPanel.userPanelAdmin();
        assertEquals("Администратор", userPanel.checkProfileInfoUser());
        assertEquals(DataConfig.UserData.getLoginAdmin(),userPanel.checkLogin());
        assertEquals(1, DataBaseQuery.selectAdmin(DataConfig.UserData.getLoginAdmin()).getRole_id());
    }

    @Story("Авторизация админа с неверным паролем")
    @ExtendWith(NotificationDecorator.class)
    @Test
    void authorizationAdminWrongPassword() {
        authPage.fillLoginField(DataConfig.UserData.getLoginAdmin());
        authPage.fillPasswordField("WWqq123456!78");
        authPage.clickToComeIn();
        assertTrue(authPage.isEnabledComeInButton());
        assertEquals("Неверный логин или пароль", authPage.getNotification());
    }

    @Story("Авторизация несуществующего админа")
    @ExtendWith(NotificationDecorator.class)
    @Test
    void authorizationAdminNonExistent() {
        authPage.fillLoginField(DataConfig.DataTest.getLoginAdminTest());
        authPage.fillPasswordField(DataConfig.DataTest.getPasswordAdminTest());
        authPage.clickToComeIn();
        assertTrue(authPage.isEnabledComeInButton());
        assertEquals("AuthorizationAdminClient::SignIn: Ошибка авторизации.", authPage.getNotification());
    }


    @Story("Авторизация админа без логина")
    @Test
    void authorizationAdminNotLogin() {
        authPage.fillPasswordField(DataConfig.DataTest.getPasswordAdminTest());
        authPage.authPage();
        assertFalse(authPage.isEnabledComeInButton());
    }

    @Story("Авторизация админа без пароля")
    @Test
    void authorizationAdminNotPassword() {
        authPage.fillLoginField(DataConfig.DataTest.getLoginAdminTest());
        authPage.authPage();
        assertFalse(authPage.isEnabledComeInButton());
    }

    @Story("Очистка поля логина через кнопку в форме авторизации")
    @Test
    void clearLoginFieldThroughButtonClear() {
        authPage.fillLoginField(DataConfig.DataTest.getLoginAdminTest());
        authPage.clearLoginClickButton();
        authPage.authPage();
        assertEquals("", authPage.getValueLoginField());
        assertEquals("Обязательное поле", authPage.getErrorFieldLogin());
    }

    @Story("Скрытие пароля при его вводе в поле пароля")
    @Test
    void fillPasswordHideValue() {
        authPage.fillPasswordField(DataConfig.DataTest.getPasswordAdminTest());
        authPage.authPage();
        assertTrue(authPage.isHidePassword());
    }

    @Story("Отображение и скрытие введенного пароля в поле пароля")
    @Test
    void showPasswordValue() {
        authPage.fillPasswordField(DataConfig.DataTest.getPasswordAdminTest());
        authPage.showPassword();
        authPage.authPage();
        assertFalse(authPage.isHidePassword());
        authPage.hidePassword();
        authPage.authPage();
        assertTrue(authPage.isHidePassword());
    }

    @Story("Отображение уведомления об обязательности полей")
    @Test
    void authorizationAdminObligatoryFields() {
        authPage.clickLoginField();
        authPage.clickPasswordField();
        authPage.authPage();
        assertEquals("Обязательное поле", authPage.getErrorFieldLogin());
        assertEquals("Обязательное поле", authPage.getErrorFieldPassword());
    }

    @Story("Ввод валидного логина из 31 и 32 символов")
    @ParameterizedTest
    @ValueSource(strings = {"ANNA_TEST_ADMIN123456789_ANNA_1", "ANNA_TEST_ADMIN123456789_ANNA_12"})
    void authorizationAdminLogin_31_32_Symbol(String login) {
        authPage.fillLoginField(login);
        authPage.authPage();
        assertFalse(authPage.isErrorLoginAppear());
        assertFalse(authPage.isEnabledComeInButton());
    }

    @Story("Ввод не валидного логина из 33 символов")
    @Test
    void authorizationAdminLogin_33_Symbol() {
        authPage.fillLoginField("ANNA_TEST_ADMIN123456789_ANNA_123");
        authPage.authPage();
        assertEquals("Максимальная длина 32 символа", authPage.getErrorFieldLogin());
        assertFalse(authPage.isEnabledComeInButton());
    }

    @Story("Ввод не валидного логина на кириллице и логина, начинающегося с цифры, логина на кириллице")
    @ParameterizedTest
    @ValueSource(strings = {"АННА_ТЕСТ", "1ANNA_TEST", "Админ_25"})
    void authorizationAdminInvalidBeginLogin(String login) {
        authPage.fillLoginField(login);
        authPage.authPage();
        assertEquals("Первый символ должен быть латинской буквой или \"_\"", authPage.getErrorFieldLogin());
        assertFalse(authPage.isEnabledComeInButton());
    }

    @Story("Ввод не валидного логина, начинающегося с латиницы, далее на кириллице и логина с пробелом")
    @ParameterizedTest
    @ValueSource(strings = {"AННА_ТЕСТ", "ANNA TEST"})
    void authorizationAdminInvalidLogin(String login) {
        authPage.fillLoginField(login);
        authPage.authPage();
        assertEquals("Доступны только числа, латиница и \"_\"", authPage.getErrorFieldLogin());
        assertFalse(authPage.isEnabledComeInButton());
    }


    @Story("Ввод валидного пароля из 8,9,24 и 25 символов")
    @ParameterizedTest
    @ValueSource(strings = {"Wwqq123#", "Wwqq1234#", "Wwqq123456789#QQgg123456", "Wwqq123456789#QQgg1234567"})
    void authorizationAdminPassword_8_9_24_25_Symbol(String password) {
        authPage.fillPasswordField(password);
        authPage.authPage();
        assertFalse(authPage.isErrorPasswordAppear());
        assertFalse(authPage.isEnabledComeInButton());
    }

    @Story("Ввод не валидного пароля из 7,26 символов")
    @ParameterizedTest
    @ValueSource(strings = {"Wwqq12#","Wwqq123456789#QQgg12345678"})
    void authorizationAdminPassword_7_26_Symbol(String password) {
        authPage.fillPasswordField(password);
        authPage.authPage();
        assertEquals("Пароль не валиден", authPage.getErrorFieldPassword());
        assertFalse(authPage.isEnabledComeInButton());
    }

    @Story("Ввод не валидного пароля без латинской буквы, без спецсимвола, без латинской буквы в верхнем регистре,без латинской буквы в нижнем регистре, без цифр, с пробелом ")
    @ParameterizedTest
    @ValueSource(strings = {"123456789!", "123456789Ss", "123456789!ss", "123456789!SS", "WwqqLLpp!!", "Wwqq 123456 #"})
    void authorizationAdminPasswordNotLatinValue(String password) {
        authPage.fillPasswordField(password);
        authPage.authPage();
        assertEquals("Пароль не валиден", authPage.getErrorFieldPassword());
        assertFalse(authPage.isEnabledComeInButton());
    }

    @Story("Закрытие уведомления на странице авторизации по таймауту")
    @Test
    void closeNotificationTimeout()  {
        authPage.fillLoginField(DataConfig.UserData.getLoginAdmin());
        authPage.fillPasswordField("WWqq123456!78");
        assertTrue(authPage.isEnabledComeInButton());
        authPage.clickToComeIn();
        checkCloseNotificationTimeout(basePage);
    }

    @Story("Закрытие уведомления на странице авторизации")
    @Test
    void closeNotification() {
        authPage.fillLoginField(DataConfig.UserData.getLoginAdmin());
        authPage.fillPasswordField("WWqq123456!78");
        assertTrue(authPage.isEnabledComeInButton());
        authPage.clickToComeIn();
        checkCloseNotification(basePage);
    }

    @Feature("Авторизация")
    @Story("Успешный выход из админ-панели")
    @Test
    void exitAdminPanel() {
        authPage.authorization(DataConfig.UserData.getLoginSuperAdmin(), DataConfig.UserData.getPasswordSuperAdmin());
        UserPanel userPanel=headerMenu.openAndCloseProfile();
        userPanel.userPanelSuperAdmin();
        AuthorizationPage authPage=userPanel.exitAdminPanel();
        authPage.authPage();
    }

}