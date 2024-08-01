package test;


import pages.BasePage.BasePage;
import pages.HeaderMenu.ChangeMinePasswordWindow;
import pages.HeaderMenu.HeaderMenu;
import pages.HeaderMenu.UserPanel;
import utils.dbUtils.DataBaseQuery;
import utils.preparationDataTests.headerMenu.ReturnPasswordAdmin;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static data.TestData.UserData.*;
import static utils.otherUtils.DataGenerator.generatePassword;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Юзер панель")
@DisplayName("Юзер панель")
public class HeaderMenuTest extends BaseTest {

    private HeaderMenu headerMenu;
    private BasePage basePage;

    @BeforeAll
    static void setUpAuth() {
        BaseTest.openAdminPanel(LOGIN_ADMIN, PASSWORD_ADMIN);
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
    @DisplayName("Успешная замена своего пароля")
    @ExtendWith(ReturnPasswordAdmin.class)
    @Test
    void changeMainPassword() {
        UserPanel userPanel=headerMenu.clickButtonUserPanel();
        userPanel.verifyUserPanelAdmin();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.clickButtonChangePassword();
        changeMinePassWindow.verifyChangeMinePasswordWindow();
        changeMinePassWindow.fillFieldOldPassword(PASSWORD_ADMIN);
        changeMinePassWindow.fillFieldNewPassword(generatePassword());
        changeMinePassWindow.clickButtonChangePassword();
        assertEquals("Пароль успешно обновлен",basePage.getTextNotification());
        assertEquals("CHANGE_PASSWORD_ADMIN_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
        assertFalse(changeMinePassWindow.isWindowAppear());
        assertFalse(userPanel.isWindowAppear());
    }


    @Feature("Смена своего пароля админом")
    @Story("Успешная замена своего пароля при совпадающем старом и новом пароле")
    @DisplayName("Успешная замена своего пароля при совпадающем старом и новом пароле")
    @ExtendWith(ReturnPasswordAdmin.class)
    @Test
    void changeMainPasswordEqualsPassword() {
        UserPanel userPanel=headerMenu.clickButtonUserPanel();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.clickButtonChangePassword();
        changeMinePassWindow.fillFieldOldPassword(PASSWORD_ADMIN);
        changeMinePassWindow.fillFieldNewPassword(PASSWORD_ADMIN);
        changeMinePassWindow.clickButtonChangePassword();
        assertEquals("Пароль успешно обновлен",basePage.getTextNotification());
        assertFalse(changeMinePassWindow.isWindowAppear());
        assertFalse(userPanel.isWindowAppear());
    }

    @Feature("Смена своего пароля админом")
    @Story("Замена своего пароля при не совпадающем старом пароле")
    @DisplayName("Замена своего пароля при не совпадающем старом пароле")
    @Test
    void changeMainPasswordNotEqualsOldPassword() {
        UserPanel userPanel=headerMenu.clickButtonUserPanel();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.clickButtonChangePassword();
        changeMinePassWindow.fillFieldOldPassword(generatePassword());
        changeMinePassWindow.fillFieldNewPassword(generatePassword());
        changeMinePassWindow.clickButtonChangePassword();
        assertEquals("{\"error\":\"Задан неверный пароль\",\"innerError\":null,\"exception\":\"ValidationPlatformException\"}",basePage.getTextNotification());
        assertTrue(changeMinePassWindow.isWindowAppear());
    }

    @Feature("Смена своего пароля админом")
    @Story("Замена своего пароля с пустым полем старого пароля")
    @DisplayName("Замена своего пароля с пустым полем старого пароля")
    @Test
    void changeMainPasswordEmptyFieldsOldPassword() {
        UserPanel userPanel=headerMenu.clickButtonUserPanel();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.clickButtonChangePassword();
        changeMinePassWindow.fillFieldNewPassword(generatePassword());
        assertFalse(changeMinePassWindow.isEnabledChangeButton());
    }

    @Feature("Смена своего пароля админом")
    @Story("Замена своего пароля с пустым полем нового пароля")
    @DisplayName("Замена своего пароля с пустым полем нового пароля")
    @Test
    void changeMainPasswordEmptyFieldsNewPassword() {
        UserPanel userPanel=headerMenu.clickButtonUserPanel();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.clickButtonChangePassword();
        changeMinePassWindow.fillFieldOldPassword(generatePassword());
        assertFalse(changeMinePassWindow.isEnabledChangeButton());
    }


    @Feature("Смена своего пароля админом")
    @Story("Отображение уведомления об обязательности полей")
    @DisplayName("Отображение уведомления об обязательности полей")
    @Test
    void displayNotificationAboutRequiredFieldsWindowChangeMainPassword() {
        UserPanel userPanel=headerMenu.clickButtonUserPanel();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.clickButtonChangePassword();
        changeMinePassWindow.clickFieldOldPassword();
        changeMinePassWindow.clickFieldNewPassword();
        assertFalse(changeMinePassWindow.isEnabledChangeButton());
        assertEquals("Обязательное поле", changeMinePassWindow.getErrorFieldOldPassword());
        assertEquals("Обязательное поле", changeMinePassWindow.getErrorFieldNewPassword());
    }


    @Feature("Смена своего пароля админом")
    @Story("Ввод не валидных граничных значений старого пароля из 7 и 26 символов")
    @DisplayName("Ввод не валидных граничных значений старого пароля из 7 и 26 символов")
    @ParameterizedTest
    @ValueSource(strings = {"Wwqq12#", "Wwqq123456789#QQgg12345678"})
    void fillLimitInvalidValuesOldPasswordChangeMainPassword_7_26_Symbol(String password) {
        UserPanel userPanel=headerMenu.clickButtonUserPanel();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.clickButtonChangePassword();
        changeMinePassWindow.fillFieldOldPassword(password);
        assertEquals("Пароль не валиден", changeMinePassWindow.getErrorFieldOldPassword());
    }

    @Feature("Смена своего пароля админом")
    @Story("Ввод валидных граничных значений старого пароля из 8,9,24 и 25 символов")
    @DisplayName("Ввод валидных граничных значений старого пароля из 8,9,24 и 25 символов")
    @ParameterizedTest
    @ValueSource(strings = {"Wwqq123#", "Wwqq1234#", "Wwqq123456789#QQgg123456", "Wwqq123456789#QQgg1234567"})
    void fillLimitValidValuesOldPasswordChangeMainPassword_8_9_24_25_Symbol(String password) {
        UserPanel userPanel=headerMenu.clickButtonUserPanel();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.clickButtonChangePassword();
        changeMinePassWindow.fillFieldOldPassword(password);
        assertFalse(changeMinePassWindow.isErrorOldPasswordAppear());
    }

    @Feature("Смена своего пароля админом")
    @Story("Ввод не валидного старого пароля")
    @DisplayName("Ввод не валидного старого пароля")
    @ParameterizedTest
    @ValueSource(strings = {"123456789!", "123456789Ss", "123456789!ss", "123456789!SS", "WwqqLLpp!!", "Wwqq 123456 #"})
    void fillInvalidValuesOldPasswordChangeMainPassword(String password) {
        UserPanel userPanel=headerMenu.clickButtonUserPanel();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.clickButtonChangePassword();
        changeMinePassWindow.fillFieldOldPassword(password);
        assertEquals("Пароль не валиден", changeMinePassWindow.getErrorFieldOldPassword());
    }

    @Feature("Смена своего пароля админом")
    @Story("Ввод не валидных граничных значений нового пароля из 7 и 26 символов")
    @DisplayName("Ввод не валидных граничных значений нового пароля из 7 и 26 символов")
    @ParameterizedTest
    @ValueSource(strings = {"Wwqq12#", "Wwqq123456789#QQgg12345678"})
    void fillLimitInvalidValuesNewPasswordChangeMainPassword_7_26_Symbol(String password) {
        UserPanel userPanel=headerMenu.clickButtonUserPanel();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.clickButtonChangePassword();
        changeMinePassWindow.fillFieldNewPassword(password);
        assertEquals("Пароль не валиден", changeMinePassWindow.getErrorFieldNewPassword());
    }

    @Feature("Смена своего пароля админом")
    @Story("Ввод валидных граничных значений нового пароля из 8,9,24 и 25 символов")
    @DisplayName("Ввод валидных граничных значений нового пароля из 8,9,24 и 25 символов")
    @ParameterizedTest
    @ValueSource(strings = {"Wwqq123#", "Wwqq1234#", "Wwqq123456789#QQgg123456", "Wwqq123456789#QQgg1234567"})
    void fillLimitValidValuesNewPasswordChangeMainPassword_8_9_24_25_Symbol(String password) {
        UserPanel userPanel=headerMenu.clickButtonUserPanel();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.clickButtonChangePassword();
        changeMinePassWindow.fillFieldNewPassword(password);
        assertFalse(changeMinePassWindow.isErrorNewPasswordAppear());
    }

    @Feature("Смена своего пароля админом")
    @Story("Ввод не валидного нового пароля")
    @DisplayName("вод не валидного нового пароля")
    @ParameterizedTest
    @ValueSource(strings = {"123456789!", "123456789Ss", "123456789!ss", "123456789!SS", "WwqqLLpp!!", "Wwqq 123456 #"})
    void fillInvalidValuesNewPasswordChangeMainPassword(String password) {
        UserPanel userPanel=headerMenu.clickButtonUserPanel();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.clickButtonChangePassword();
        changeMinePassWindow.fillFieldNewPassword(password);
        assertEquals("Пароль не валиден", changeMinePassWindow.getErrorFieldNewPassword());
    }


    @Feature("Смена своего пароля админом")
    @Story("Очистка полей через кнопку в окне изменения своего пароля")
    @DisplayName("Очистка полей через кнопку в окне изменения своего пароля")
    @Test
    void clearFieldsWindowChangeMainPasswordThroughButtonClear(){
        UserPanel userPanel=headerMenu.clickButtonUserPanel();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.clickButtonChangePassword();
        changeMinePassWindow.fillFieldOldPassword(generatePassword());
        changeMinePassWindow.clickClearButtonOldPasswordField();
        assertEquals("Обязательное поле", changeMinePassWindow.getErrorFieldOldPassword());
        changeMinePassWindow.fillFieldNewPassword(generatePassword());
        changeMinePassWindow.clickClearButtonNewPasswordField();
        assertEquals("Обязательное поле", changeMinePassWindow.getErrorFieldNewPassword());
        assertEquals("", changeMinePassWindow.getValueOldPasswordField());
        assertEquals("", changeMinePassWindow.getValueNewPasswordField());
    }


    @Feature("Смена своего пароля админом")
    @Story("Сброс значений полей в окне изменения своего пароля при закрытии окна")
    @DisplayName("Сброс значений полей в окне изменения своего пароля при закрытии окна")
    @Test
    void closeWindowChangeMainPassword() {
        UserPanel userPanel=headerMenu.clickButtonUserPanel();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.clickButtonChangePassword();
        changeMinePassWindow.fillFieldOldPassword(generatePassword());
        changeMinePassWindow.fillFieldNewPassword(generatePassword());
        changeMinePassWindow.clickCancelButtonChangePassword();
        assertFalse(changeMinePassWindow.isWindowAppear());
        assertFalse(userPanel.isWindowAppear());
        headerMenu.clickButtonUserPanel();
        userPanel.clickButtonChangePassword();
        assertEquals("", changeMinePassWindow.getValueOldPasswordField());
        assertEquals("", changeMinePassWindow.getValueNewPasswordField());
    }


    @Story("Закрытие панели пользователя")
    @DisplayName("Закрытие панели пользователя")
    @Test
    void closeUserPanel() {
        UserPanel userPanel=headerMenu.clickButtonUserPanel();
        headerMenu.clickButtonUserPanel();
        assertFalse(userPanel.isWindowAppear());
    }
}
