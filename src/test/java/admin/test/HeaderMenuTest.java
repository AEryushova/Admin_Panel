package admin.test;

import admin.data.TestData;
import admin.pages.BasePage.BasePage;
import admin.pages.HeaderMenu.ChangeMinePasswordWindow;
import admin.pages.HeaderMenu.HeaderMenu;
import admin.pages.HeaderMenu.UserPanel;
import admin.utils.preparationDataTests.general.AllureDecorator;
import admin.utils.preparationDataTests.general.NotificationDecorator;
import admin.utils.preparationDataTests.headerMenu.NewAuthDecorator;
import admin.utils.preparationDataTests.headerMenu.ReturnPasswordAdmin;
import admin.utils.testUtils.*;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Меню навигации")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HeaderMenuTest {

    private HeaderMenu headerMenu;
    private BasePage basePage;

    @ExtendWith(AllureDecorator.class)

    @BeforeAll
    static void setUpAuth() {
        BrowserManager.openAdminPanel(TestData.UserData.LOGIN_ADMIN, TestData.UserData.PASSWORD_ADMIN);
    }

    @BeforeEach
    void setUp(){
        Selenide.refresh();
        headerMenu = new HeaderMenu();
        basePage = new BasePage();
    }

    @AfterAll
    static void closeWebDriver() {
        Selenide.closeWebDriver();
    }

    @Feature("Смена своего пароля админом")
    @Story("Успешная замена своего пароля")
    @ExtendWith({ReturnPasswordAdmin.class, NotificationDecorator.class})
    @Test
    void changeMainPassword() {
        UserPanel userPanel=headerMenu.openAndCloseProfile();
        userPanel.userPanelAdmin();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.changePassword();
        changeMinePassWindow.changeMinePasswordWindow();
        changeMinePassWindow.fillFieldOldPassword(TestData.UserData.PASSWORD_ADMIN);
        changeMinePassWindow.fillFieldNewPassword(TestData.DataTest.getNEW_PASSWORD_ADMIN());
        changeMinePassWindow.clickChangeButton();
        assertEquals("Пароль успешно обновлен",basePage.getNotification());
        assertFalse(changeMinePassWindow.isWindowAppear());
        assertFalse(userPanel.isWindowAppear());
    }


    @Feature("Смена своего пароля админом")
    @Story("Успешная замена своего пароля при совпадающем старом и новом пароле")
    @ExtendWith({ReturnPasswordAdmin.class,NotificationDecorator.class})
    @Test
    void changeMainPasswordEqualsPassword() {
        UserPanel userPanel=headerMenu.openAndCloseProfile();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.changePassword();
        changeMinePassWindow.fillFieldOldPassword(TestData.UserData.PASSWORD_ADMIN);
        changeMinePassWindow.fillFieldNewPassword(TestData.UserData.PASSWORD_ADMIN);
        changeMinePassWindow.clickChangeButton();
        assertEquals("Пароль успешно обновлен",basePage.getNotification());
        assertFalse(changeMinePassWindow.isWindowAppear());
        assertFalse(userPanel.isWindowAppear());
    }

    @Feature("Смена своего пароля админом")
    @Story("Успешная замена своего пароля при не совпадающем старом пароле")
    @ExtendWith(NotificationDecorator.class)
    @Test
    void changeMainPasswordNotEqualsOldPassword() {
        UserPanel userPanel=headerMenu.openAndCloseProfile();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.changePassword();
        changeMinePassWindow.fillFieldOldPassword(TestData.DataTest.getNEW_PASSWORD_ADMIN_TEST());
        changeMinePassWindow.fillFieldNewPassword(TestData.UserData.PASSWORD_ADMIN);
        changeMinePassWindow.clickChangeButton();
        assertEquals("{\"error\":\"Задан неверный пароль\",\"innerError\":null,\"exception\":\"ValidationPlatformException\"}",basePage.getNotification());
        assertTrue(changeMinePassWindow.isWindowAppear());
    }

    @Feature("Смена своего пароля админом")
    @Story("Замена своего пароля с пустым полем старого пароля")
    @Test
    void changeMainPasswordEmptyFieldsOldPassword() {
        UserPanel userPanel=headerMenu.openAndCloseProfile();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.changePassword();
        changeMinePassWindow.fillFieldNewPassword(TestData.UserData.PASSWORD_ADMIN);
        assertFalse(changeMinePassWindow.isEnabledChangeButton());
    }

    @Feature("Смена своего пароля админом")
    @Story("Замена своего пароля с пустым полем нового пароля")
    @Test
    void changeMainPasswordEmptyFieldsNewPassword() {
        UserPanel userPanel=headerMenu.openAndCloseProfile();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.changePassword();
        changeMinePassWindow.fillFieldOldPassword(TestData.UserData.PASSWORD_ADMIN);
        assertFalse(changeMinePassWindow.isEnabledChangeButton());
    }


    @Feature("Смена своего пароля админом")
    @Story("Отображение уведомления об обязательности полей")
    @Test
    void changeMainPasswordObligatoryFields() {
        UserPanel userPanel=headerMenu.openAndCloseProfile();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.changePassword();
        changeMinePassWindow.clickFieldOldPassword();
        changeMinePassWindow.clickFieldNewPassword();
        assertFalse(changeMinePassWindow.isEnabledChangeButton());
        assertEquals("Обязательное поле", changeMinePassWindow.getErrorFieldOldPassword());
        assertEquals("Обязательное поле", changeMinePassWindow.getErrorFieldNewPassword());
    }


    @Feature("Смена своего пароля админом")
    @Story("Ввод не валидного пароля из 7 и 26 символов в поле старого пароля")
    @ParameterizedTest
    @ValueSource(strings = {"Wwqq12#", "Wwqq123456789#QQgg12345678"})
    void changeMainPasswordOldPassword_7_26_Symbol(String password) {
        UserPanel userPanel=headerMenu.openAndCloseProfile();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.changePassword();
        changeMinePassWindow.fillFieldOldPassword(password);
        assertEquals("Пароль не валиден", changeMinePassWindow.getErrorFieldOldPassword());
    }

    @Feature("Смена своего пароля админом")
    @Story("Ввод валидного пароля из 8,9,24 и 25 символов в поле старого пароля")
    @ParameterizedTest
    @ValueSource(strings = {"Wwqq123#", "Wwqq1234#", "Wwqq123456789#QQgg123456", "Wwqq123456789#QQgg1234567"})
    void changeMainPasswordOldPassword_8_9_24_25Symbol(String password) {
        UserPanel userPanel=headerMenu.openAndCloseProfile();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.changePassword();
        changeMinePassWindow.fillFieldOldPassword(password);
        assertFalse(changeMinePassWindow.isErrorOldPasswordAppear());
    }

    @Feature("Смена своего пароля админом")
    @Story("Ввод не валидного пароля без латинской буквы, без спецсимвола, без латинской буквы в верхнем регистре,без латинской буквы в нижнем регистре, без цифр, с пробелом в поле старого пароля")
    @ParameterizedTest
    @ValueSource(strings = {"123456789!", "123456789Ss", "123456789!ss", "123456789!SS", "WwqqLLpp!!", "Wwqq 123456 #"})
    void changeMainPasswordOldPasswordNotLatinValue(String password) {
        UserPanel userPanel=headerMenu.openAndCloseProfile();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.changePassword();
        changeMinePassWindow.fillFieldOldPassword(password);
        assertEquals("Пароль не валиден", changeMinePassWindow.getErrorFieldOldPassword());
    }

    @Feature("Смена своего пароля админом")
    @Story("Ввод не валидного пароля из 7 и 26 символов в поле нового пароля")
    @ParameterizedTest
    @ValueSource(strings = {"Wwqq12#", "Wwqq123456789#QQgg12345678"})
    void changeMainPasswordNewPassword_7_26_Symbol(String password) {
        UserPanel userPanel=headerMenu.openAndCloseProfile();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.changePassword();
        changeMinePassWindow.fillFieldNewPassword(password);
        assertEquals("Пароль не валиден", changeMinePassWindow.getErrorFieldNewPassword());
    }

    @Feature("Смена своего пароля админом")
    @Story("Ввод валидного пароля из 8,9,24 и 25 символов в поле нового пароля")
    @ParameterizedTest
    @ValueSource(strings = {"Wwqq123#", "Wwqq1234#", "Wwqq123456789#QQgg123456", "Wwqq123456789#QQgg1234567"})
    void changeMainPasswordNewPassword_8_9_24_25Symbol(String password) {
        UserPanel userPanel=headerMenu.openAndCloseProfile();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.changePassword();
        changeMinePassWindow.fillFieldNewPassword(password);
        assertFalse(changeMinePassWindow.isErrorNewPasswordAppear());
    }

    @Feature("Смена своего пароля админом")
    @Story("Ввод не валидного пароля без латинской буквы, без спецсимвола, без латинской буквы в верхнем регистре,без латинской буквы в нижнем регистре, без цифр, с пробелом в поле нового пароля")
    @ParameterizedTest
    @ValueSource(strings = {"123456789!", "123456789Ss", "123456789!ss", "123456789!SS", "WwqqLLpp!!", "Wwqq 123456 #"})
    void changeMainPasswordNewPasswordNotLatinValue(String password) {
        UserPanel userPanel=headerMenu.openAndCloseProfile();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.changePassword();
        changeMinePassWindow.fillFieldNewPassword(password);
        assertEquals("Пароль не валиден", changeMinePassWindow.getErrorFieldNewPassword());
    }


    @Feature("Смена своего пароля админом")
    @Story("Очистка полей через кнопку в окне изменения своего пароля")
    @Test
    void clearFieldsThroughButtonClear(){
        UserPanel userPanel=headerMenu.openAndCloseProfile();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.changePassword();
        changeMinePassWindow.fillFieldOldPassword(TestData.UserData.PASSWORD_ADMIN);
        changeMinePassWindow.clickClearButtonOldPasswordField();
        changeMinePassWindow.fillFieldNewPassword(TestData.UserData.PASSWORD_ADMIN);
        changeMinePassWindow.clickClearButtonNewPasswordField();
        assertEquals("", changeMinePassWindow.getValueOldPasswordField());
        assertEquals("Обязательное поле", changeMinePassWindow.getErrorFieldOldPassword());
        assertEquals("", changeMinePassWindow.getValueNewPasswordField());
        assertEquals("Обязательное поле", changeMinePassWindow.getErrorFieldNewPassword());
    }


    @Feature("Смена своего пароля админом")
    @Story("Зануление полей в окне изменения своего пароля и закрытие окна")
    @Test
    void closeWindowChangeMainPassword() {
        UserPanel userPanel=headerMenu.openAndCloseProfile();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.changePassword();
        changeMinePassWindow.fillFieldOldPassword(TestData.UserData.PASSWORD_ADMIN);
        changeMinePassWindow.fillFieldNewPassword(TestData.UserData.PASSWORD_ADMIN);
        changeMinePassWindow.clickCancelButton();
        assertFalse(changeMinePassWindow.isWindowAppear());
        assertFalse(userPanel.isWindowAppear());
        headerMenu.openAndCloseProfile();
        userPanel.changePassword();
        assertEquals("", changeMinePassWindow.getValueOldPasswordField());
        assertEquals("", changeMinePassWindow.getValueNewPasswordField());
    }


    @Feature("Панель пользователя")
    @Story("Закрытие панели пользователя")
    @Test
    void closeUserPanel() {
        UserPanel userPanel=headerMenu.openAndCloseProfile();
        headerMenu.openAndCloseProfile();
        assertFalse(userPanel.isWindowAppear());
    }

}
