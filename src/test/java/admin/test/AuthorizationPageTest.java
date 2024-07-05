package admin.test;

import admin.pages.BasePage.BasePage;
import admin.pages.HeaderMenu.UserPanel;
import admin.utils.dbUtils.DataBaseQuery;
import admin.utils.preparationDataTests.general.AllureDecorator;
import admin.utils.preparationDataTests.authorization.CloseWebDriverDecorator;
import admin.utils.testUtils.*;
import com.codeborne.selenide.Selenide;
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

import static admin.data.TestData.DataTest.*;
import static admin.data.TestData.UserData.*;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Авторизация")
@Feature("Вход и выход в админ-панели")
@DisplayName("Страница Авторизации")
public class AuthorizationPageTest extends BaseTest {

    private AuthorizationPage authPage;
    private HeaderMenu headerMenu;
    private BasePage basePage;

    @ExtendWith(AllureDecorator.class)

    @BeforeAll
    static void setUpAuth() {
        BrowserManager.openBrowser();
    }

    @BeforeEach
    void setUp() {
        Selenide.refresh();
        authPage = new AuthorizationPage();
        headerMenu = new HeaderMenu();
        basePage = new BasePage();
    }

    @AfterAll
    static void closeWebDriver() {
        Selenide.closeWebDriver();
    }

    @Story("Успешная авторизация супер-админа")
    @DisplayName("Успешная авторизация супер-админа")
    @ExtendWith(CloseWebDriverDecorator.class)
    @Test
    void authSuperAdmin() {
        authPage.authPage();
        DoctorsPage doctorPage = authPage.authorization(LOGIN_SUPER_ADMIN, PASSWORD_SUPER_ADMIN);
        doctorPage.doctorsPage();
        headerMenu.headerBarSuperAdmin();
        UserPanel userPanel=headerMenu.openAndCloseUserPanel();
        userPanel.userPanelSuperAdmin();
        assertEquals("Супер-Администратор", userPanel.checkProfileInfoUser());
        assertEquals(LOGIN_SUPER_ADMIN,userPanel.checkLogin());
        assertEquals(0, DataBaseQuery.selectAdmin(LOGIN_SUPER_ADMIN).getRole_id());
    }


    @Story("Успешная авторизация админа")
    @DisplayName("Успешная авторизация админа")
    @ExtendWith(CloseWebDriverDecorator.class)
    @Test
    void authAdmin() {
        authPage.authPage();
        DoctorsPage doctorPage = authPage.authorization(LOGIN_ADMIN, PASSWORD_ADMIN);
        doctorPage.doctorsPage();
        headerMenu.headerBarAdmin();
        UserPanel userPanel=headerMenu.openAndCloseUserPanel();
        userPanel.userPanelAdmin();
        assertEquals("Администратор", userPanel.checkProfileInfoUser());
        assertEquals(LOGIN_ADMIN,userPanel.checkLogin());
        assertEquals(1, DataBaseQuery.selectAdmin(LOGIN_ADMIN).getRole_id());
    }

    @Story("Авторизация админа с неверным паролем")
    @DisplayName("Авторизация админа с неверным паролем")
    @Test
    void authAdminWithWrongPassword() {
        authPage.fillLoginField(LOGIN_ADMIN);
        authPage.fillPasswordField("WWqq123456!78");
        authPage.clickToComeIn();
        assertTrue(authPage.isEnabledComeInButton());
        assertEquals("Неверный логин или пароль", authPage.getNotification());
    }

    @Story("Авторизация несуществующего админа")
    @DisplayName("Авторизация несуществующего админа")
    @Test
    void authNonExistentAdmin() {
        authPage.fillLoginField(LOGIN_ADMIN_TEST);
        authPage.fillPasswordField(PASSWORD_ADMIN_TEST);
        authPage.clickToComeIn();
        assertTrue(authPage.isEnabledComeInButton());
        assertEquals("AuthorizationAdminClient::SignIn: Ошибка авторизации.", authPage.getNotification());
    }


    @Story("Авторизация админа с пустым полем логина")
    @DisplayName("Авторизация админа с пустым полем логина")
    @Test
    void authAdminEmptyFieldLogin() {
        authPage.fillPasswordField(PASSWORD_ADMIN_TEST);
        authPage.authPage();
        assertFalse(authPage.isEnabledComeInButton());
    }

    @Story("Авторизация админа с пустым полем пароля")
    @DisplayName("Авторизация админа с пустым полем пароля")
    @Test
    void authAdminEmptyFieldPassword() {
        authPage.fillLoginField(LOGIN_ADMIN_TEST);
        authPage.authPage();
        assertFalse(authPage.isEnabledComeInButton());
    }

    @Story("Очистка поля логина через кнопку в форме авторизации")
    @DisplayName("Очистка поля логина через кнопку в форме авторизации")
    @Test
    void clearFieldLoginAuthPageThroughButtonClear() {
        authPage.fillLoginField(LOGIN_ADMIN_TEST);
        authPage.clearLoginClickButton();
        authPage.authPage();
        assertEquals("", authPage.getValueLoginField());
        assertEquals("Обязательное поле", authPage.getErrorFieldLogin());
    }

    @Story("Скрытие пароля при его вводе в поле пароля")
    @DisplayName("Скрытие пароля при его вводе в поле пароля")
    @Test
    void hideValuePasswordWhenFillPasswordField() {
        authPage.fillPasswordField(PASSWORD_ADMIN_TEST);
        authPage.authPage();
        assertTrue(authPage.isHidePassword());
    }

    @Story("Отображение и скрытие введенного пароля в поле пароля")
    @DisplayName("Отображение и скрытие введенного пароля в поле пароля")
    @Test
    void showValuePasswordField() {
        authPage.fillPasswordField(PASSWORD_ADMIN_TEST);
        authPage.showPassword();
        authPage.authPage();
        assertFalse(authPage.isHidePassword());
        authPage.hidePassword();
        authPage.authPage();
        assertTrue(authPage.isHidePassword());
    }

    @Story("Отображение уведомления об обязательности полей")
    @DisplayName("Отображение уведомления об обязательности полей")
    @Test
    void displayNotificationAboutRequiredFieldsAuthPage() {
        authPage.clickLoginField();
        authPage.clickPasswordField();
        authPage.authPage();
        assertEquals("Обязательное поле", authPage.getErrorFieldLogin());
        assertEquals("Обязательное поле", authPage.getErrorFieldPassword());
    }

    @Story("Ввод валидных граничных значений логина из 31 и 32 символов")
    @DisplayName("Ввод валидных граничных значений логина из 31 и 32 символов")
    @ParameterizedTest
    @ValueSource(strings = {"ANNA_TEST_ADMIN123456789_ANNA_1", "ANNA_TEST_ADMIN123456789_ANNA_12"})
    void fillLimitValidValuesLoginAuthPage_31_32_Symbol(String login) {
        authPage.fillLoginField(login);
        authPage.authPage();
        assertFalse(authPage.isErrorLoginAppear());
        assertFalse(authPage.isEnabledComeInButton());
    }

    @Story("Ввод не валидных граничных значений логина из 33 символов")
    @DisplayName("Ввод не валидных граничных значений логина из 33 символов")
    @Test
    void fillLimitInvalidValuesLoginAuthPage_33_Symbol() {
        authPage.fillLoginField("ANNA_TEST_ADMIN123456789_ANNA_123");
        authPage.authPage();
        assertEquals("Максимальная длина 32 символа", authPage.getErrorFieldLogin());
        assertFalse(authPage.isEnabledComeInButton());
    }

    @Story("Ввод не валидного логина с не валидным первым символом")
    @DisplayName("Ввод не валидного логина с не валидным первым символом")
    @ParameterizedTest
    @ValueSource(strings = {"БRUCE_LI", "1ANNA_TEST", "Админ_25"})
    void fillInvalidValuesFirstSymbolLoginAuthPage(String login) {
        authPage.fillLoginField(login);
        authPage.authPage();
        assertEquals("Первый символ должен быть латинской буквой или \"_\"", authPage.getErrorFieldLogin());
        assertFalse(authPage.isEnabledComeInButton());
    }

    @Story("Ввод не валидного логина")
    @DisplayName("Ввод не валидного логина")
    @ParameterizedTest
    @ValueSource(strings = {"AННА_ТЕСТ", "ANNA TEST"})
    void fillInvalidValuesLoginAuthPage(String login) {
        authPage.fillLoginField(login);
        authPage.authPage();
        assertEquals("Доступны только числа, латиница и \"_\"", authPage.getErrorFieldLogin());
        assertFalse(authPage.isEnabledComeInButton());
    }


    @Story("Ввод валидных граничных значений пароля из 8,9,24 и 25 символов")
    @DisplayName("Ввод валидных граничных значений пароля из 8,9,24 и 25 символов")
    @ParameterizedTest
    @ValueSource(strings = {"Wwqq123#", "Wwqq1234#", "Wwqq123456789#QQgg123456", "Wwqq123456789#QQgg1234567"})
    void fillLimitValidValuesPasswordAuthPage_8_9_24_25_Symbol(String password) {
        authPage.fillPasswordField(password);
        authPage.authPage();
        assertFalse(authPage.isErrorPasswordAppear());
        assertFalse(authPage.isEnabledComeInButton());
    }

    @Story("Ввод не валидных граничных значений пароля из 7 и 26 символов")
    @DisplayName("Ввод не валидных граничных значений пароля из 7 и 26 символов")
    @ParameterizedTest
    @ValueSource(strings = {"Wwqq12#","Wwqq123456789#QQgg12345678"})
    void fillLimitInvalidValuesPasswordAuthPage_7_26_Symbol(String password) {
        authPage.fillPasswordField(password);
        authPage.authPage();
        assertEquals("Пароль не валиден", authPage.getErrorFieldPassword());
        assertFalse(authPage.isEnabledComeInButton());
    }

    @Story("Ввод не валидного пароля")
    @DisplayName("Ввод не валидного пароля")
    @ParameterizedTest
    @ValueSource(strings = {"123456789!", "123456789Ss", "123456789!ss", "123456789!SS", "WwqqLLpp!!", "Wwqq 123456 #"})
    void fillInvalidValuesPasswordAuthPage(String password) {
        authPage.fillPasswordField(password);
        authPage.authPage();
        assertEquals("Пароль не валиден", authPage.getErrorFieldPassword());
        assertFalse(authPage.isEnabledComeInButton());
    }

    @Story("Закрытие уведомления на странице авторизации по таймауту")
    @DisplayName("Закрытие уведомления на странице авторизации по таймауту")
    @Test
    void closeNotificationTimeout()  {
        authPage.fillLoginField(LOGIN_ADMIN);
        authPage.fillPasswordField("WWqq123456!78");
        assertTrue(authPage.isEnabledComeInButton());
        authPage.clickToComeIn();
        checkCloseNotificationTimeout(basePage);
    }

    @Story("Закрытие уведомления на странице авторизации")
    @DisplayName("Закрытие уведомления на странице авторизации")
    @Test
    void closeNotification() {
        authPage.fillLoginField(LOGIN_ADMIN);
        authPage.fillPasswordField("WWqq123456!78");
        assertTrue(authPage.isEnabledComeInButton());
        authPage.clickToComeIn();
        checkCloseNotification(basePage);
    }


    @Story("Успешный выход из админ-панели")
    @DisplayName("Успешный выход из админ-панели")
    @Test
    void exitAdminPanel() {
        authPage.authorization(LOGIN_SUPER_ADMIN, PASSWORD_SUPER_ADMIN);
        UserPanel userPanel=headerMenu.openAndCloseUserPanel();
        userPanel.userPanelSuperAdmin();
        AuthorizationPage authPage=userPanel.exitAdminPanel();
        authPage.authPage();
    }

}