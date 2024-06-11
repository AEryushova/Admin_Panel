package admin.test;

import admin.data.DataConfig;
import admin.pages.BasePage.BasePage;
import admin.pages.HeaderMenu.ChangeMinePasswordWindow;
import admin.pages.HeaderMenu.HeaderMenu;
import admin.pages.HeaderMenu.UserPanel;
import admin.utils.preparationDataTests.general.AllureDecorator;
import admin.utils.preparationDataTests.general.NotificationDecorator;
import admin.utils.preparationDataTests.headerMenu.NewAuthDecorator;
import admin.utils.preparationDataTests.headerMenu.ReturnPasswordAdmin;
import admin.utils.testUtils.*;
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
        BrowserManager.authGetCookie(DataConfig.UserData.getLoginAdmin(), DataConfig.UserData.getPasswordAdmin());
    }

    @BeforeEach
    void setUp(){
        BrowserManager.openPagesAfterAuth();
        headerMenu = new HeaderMenu();
        basePage = new BasePage();
    }


    @Feature("Смена своего пароля админом")
    @Story("Успешная замена своего пароля")
    @ExtendWith({ReturnPasswordAdmin.class, NewAuthDecorator.class, NotificationDecorator.class})
    @Test
    void changeMainPassword() {
        UserPanel userPanel=headerMenu.openAndCloseProfile();
        userPanel.userPanelAdmin();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.changePassword();
        changeMinePassWindow.changeMinePasswordWindow();
        changeMinePassWindow.fillFieldOldPassword(DataConfig.UserData.getPasswordAdmin());
        changeMinePassWindow.fillFieldNewPassword(DataConfig.UserData.getNewPasswordAdmin());
        changeMinePassWindow.clickChangeButton();
        assertEquals("Пароль успешно обновлен",basePage.getNotification());
        assertFalse(changeMinePassWindow.isWindowAppear());
        assertFalse(userPanel.isWindowAppear());
    }


    @Feature("Смена своего пароля админом")
    @Story("Успешная замена своего пароля при совпадающем старом и новом пароле")
    @ExtendWith({NewAuthDecorator.class,NotificationDecorator.class})
    @Test
    void changeMainPasswordEqualsPassword() {
        UserPanel userPanel=headerMenu.openAndCloseProfile();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.changePassword();
        changeMinePassWindow.fillFieldOldPassword(DataConfig.UserData.getPasswordAdmin());
        changeMinePassWindow.fillFieldNewPassword(DataConfig.UserData.getPasswordAdmin());
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
        changeMinePassWindow.fillFieldOldPassword(DataConfig.UserData.getNewPasswordAdminTest());
        changeMinePassWindow.fillFieldNewPassword(DataConfig.UserData.getPasswordAdmin());
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
        changeMinePassWindow.fillFieldNewPassword(DataConfig.UserData.getPasswordAdmin());
        assertFalse(changeMinePassWindow.isEnabledChangeButton());
    }

    @Feature("Смена своего пароля админом")
    @Story("Замена своего пароля с пустым полем нового пароля")
    @Test
    void changeMainPasswordEmptyFieldsNewPassword() {
        UserPanel userPanel=headerMenu.openAndCloseProfile();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.changePassword();
        changeMinePassWindow.fillFieldOldPassword(DataConfig.UserData.getPasswordAdmin());
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
    @Story("Очистка поля старого пароля через кнопку в окне изменения своего пароля")
    @Test
    void clearFieldOldPasswordThroughButtonClear(){
        UserPanel userPanel=headerMenu.openAndCloseProfile();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.changePassword();
        changeMinePassWindow.fillFieldOldPassword(DataConfig.UserData.getPasswordAdmin());
        changeMinePassWindow.clickClearButtonOldPasswordField();
        assertEquals("", changeMinePassWindow.getValueOldPasswordField());
        assertEquals("Обязательное поле", changeMinePassWindow.getErrorFieldOldPassword());
    }

    @Feature("Смена своего пароля админом")
    @Story("Очистка поля нового пароля через кнопку в окне изменения своего пароля")
    @Test
    void clearFieldNewPasswordThroughButtonClear(){
        UserPanel userPanel=headerMenu.openAndCloseProfile();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.changePassword();
        changeMinePassWindow.fillFieldNewPassword(DataConfig.UserData.getPasswordAdmin());
        changeMinePassWindow.clickClearButtonNewPasswordField();
        assertEquals("", changeMinePassWindow.getValueNewPasswordField());
        assertEquals("Обязательное поле", changeMinePassWindow.getErrorFieldNewPassword());
    }


    @Feature("Смена своего пароля админом")
    @Story("Зануление полей в окне изменения своего пароля и закрытие окна")
    @Test
    void closeWindowChangeMainPassword() {
        UserPanel userPanel=headerMenu.openAndCloseProfile();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.changePassword();
        changeMinePassWindow.fillFieldOldPassword(DataConfig.UserData.getPasswordAdmin());
        changeMinePassWindow.fillFieldNewPassword(DataConfig.UserData.getPasswordAdmin());
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
