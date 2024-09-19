package ru.adminlk.clinica.samsmu.test;


import ru.adminlk.clinica.samsmu.pages.HeaderMenu.ChangeMinePasswordWindow;
import ru.adminlk.clinica.samsmu.pages.HeaderMenu.UserPanel;
import ru.adminlk.clinica.samsmu.utils.dbUtils.DataBaseQuery;
import ru.adminlk.clinica.samsmu.utils.preparationData.headerMenu.ReturnPasswordAdmin;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static ru.adminlk.clinica.samsmu.data.FinalTestData.UserData.*;
import static ru.adminlk.clinica.samsmu.utils.testsUtils.DataGenerator.generatePassword;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Юзер панель")
@DisplayName("Юзер панель")
public class HeaderMenuTest extends BaseTest {

    @BeforeAll
    static void setUpAuth() {
        getAuthToken(LOGIN_ADMIN, PASSWORD_ADMIN);
    }

    @BeforeEach
    void setUp() {
        openAuthAdminPanel();
        headerMenu.verifyHeaderBarAdmin();
    }

    @AfterEach()
    void closeWebDriver() {
        closeDriver();
    }


    @Feature("Смена своего пароля админом")
    @Story("Успешная замена своего пароля")
    @DisplayName("Успешная замена своего пароля")
    @ExtendWith(ReturnPasswordAdmin.class)
    @Test
    void changeMainPassword() {
        UserPanel userPanel = headerMenu.clickButtonUserPanel();
        userPanel.verifyUserPanelAdmin();
        ChangeMinePasswordWindow changeMinePassWindow = userPanel.clickButtonChangePassword();
        changeMinePassWindow.verifyChangeMinePasswordWindow()
                .fillFieldOldPassword(PASSWORD_ADMIN)
                .fillFieldNewPassword(generatePassword())
                .clickButtonChangePassword();
        assertEquals("Пароль успешно обновлен", basePage.getTextNotification());
        assertEquals("CHANGE_PASSWORD_ADMIN_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Смена своего пароля админом")
    @Story("Успешная замена своего пароля при совпадающем старом и новом пароле")
    @DisplayName("Успешная замена своего пароля при совпадающем старом и новом пароле")
    @ExtendWith(ReturnPasswordAdmin.class)
    @Test
    void changeMainPasswordEqualsPassword() {
        UserPanel userPanel = headerMenu.clickButtonUserPanel();
        ChangeMinePasswordWindow changeMinePassWindow = userPanel.clickButtonChangePassword();
        changeMinePassWindow.verifyChangeMinePasswordWindow()
                .fillFieldOldPassword(PASSWORD_ADMIN)
                .fillFieldNewPassword(PASSWORD_ADMIN)
                .clickButtonChangePassword();
        assertEquals("Пароль успешно обновлен", basePage.getTextNotification());
    }

    @Feature("Смена своего пароля админом")
    @Story("Замена своего пароля при не совпадающем старом пароле")
    @DisplayName("Замена своего пароля при не совпадающем старом пароле")
    @Test
    void changeMainPasswordNotEqualsOldPassword() {
        UserPanel userPanel = headerMenu.clickButtonUserPanel();
        ChangeMinePasswordWindow changeMinePassWindow = userPanel.clickButtonChangePassword();
        changeMinePassWindow.verifyChangeMinePasswordWindow()
                .fillFieldOldPassword(generatePassword())
                .fillFieldNewPassword(generatePassword())
                .clickButtonChangePassword();
        assertEquals("{\"error\":\"Задан неверный пароль\",\"innerError\":null,\"exception\":\"ValidationPlatformException\"}", basePage.getTextNotification());
    }

    @Feature("Смена своего пароля админом")
    @Story("Замена своего пароля с пустым полем старого пароля")
    @DisplayName("Замена своего пароля с пустым полем старого пароля")
    @Test
    void changeMainPasswordEmptyFieldsOldPassword() {
        UserPanel userPanel = headerMenu.clickButtonUserPanel();
        ChangeMinePasswordWindow changeMinePassWindow = userPanel.clickButtonChangePassword();
        changeMinePassWindow.verifyChangeMinePasswordWindow()
                .fillFieldNewPassword(generatePassword());
        assertFalse(changeMinePassWindow.isEnabledChangeButton());
    }

    @Feature("Смена своего пароля админом")
    @Story("Замена своего пароля с пустым полем нового пароля")
    @DisplayName("Замена своего пароля с пустым полем нового пароля")
    @Test
    void changeMainPasswordEmptyFieldsNewPassword() {
        UserPanel userPanel = headerMenu.clickButtonUserPanel();
        ChangeMinePasswordWindow changeMinePassWindow = userPanel.clickButtonChangePassword();
        changeMinePassWindow.verifyChangeMinePasswordWindow()
                .fillFieldOldPassword(generatePassword());
        assertFalse(changeMinePassWindow.isEnabledChangeButton());
    }

    @Feature("Смена своего пароля админом")
    @Story("Отображение уведомления об обязательности полей")
    @DisplayName("Отображение уведомления об обязательности полей")
    @Test
    void displayNotificationAboutRequiredFieldsWindowChangeMainPassword() {
        UserPanel userPanel = headerMenu.clickButtonUserPanel();
        ChangeMinePasswordWindow changeMinePassWindow = userPanel.clickButtonChangePassword();
        changeMinePassWindow.verifyChangeMinePasswordWindow()
                .clickFieldOldPassword()
                .clickFieldNewPassword();
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
        UserPanel userPanel = headerMenu.clickButtonUserPanel();
        ChangeMinePasswordWindow changeMinePassWindow = userPanel.clickButtonChangePassword();
        changeMinePassWindow.verifyChangeMinePasswordWindow()
                .fillFieldOldPassword(password);
        assertEquals("Пароль не валиден", changeMinePassWindow.getErrorFieldOldPassword());
    }

    @Feature("Смена своего пароля админом")
    @Story("Ввод валидных граничных значений старого пароля из 8,9,24 и 25 символов")
    @DisplayName("Ввод валидных граничных значений старого пароля из 8,9,24 и 25 символов")
    @ParameterizedTest
    @ValueSource(strings = {"Wwqq123#", "Wwqq1234#", "Wwqq123456789#QQgg123456", "Wwqq123456789#QQgg1234567"})
    void fillLimitValidValuesOldPasswordChangeMainPassword_8_9_24_25_Symbol(String password) {
        UserPanel userPanel = headerMenu.clickButtonUserPanel();
        ChangeMinePasswordWindow changeMinePassWindow = userPanel.clickButtonChangePassword();
        changeMinePassWindow.verifyChangeMinePasswordWindow()
                .fillFieldOldPassword(password);
        assertFalse(changeMinePassWindow.isErrorOldPasswordAppear());
    }

    @Feature("Смена своего пароля админом")
    @Story("Ввод не валидного старого пароля")
    @DisplayName("Ввод не валидного старого пароля")
    @ParameterizedTest
    @ValueSource(strings = {"123456789!", "123456789Ss", "123456789!ss", "123456789!SS", "WwqqLLpp!!", "Wwqq 123456 #"})
    void fillInvalidValuesOldPasswordChangeMainPassword(String password) {
        UserPanel userPanel = headerMenu.clickButtonUserPanel();
        ChangeMinePasswordWindow changeMinePassWindow = userPanel.clickButtonChangePassword();
        changeMinePassWindow.verifyChangeMinePasswordWindow()
                .fillFieldOldPassword(password);
        assertEquals("Пароль не валиден", changeMinePassWindow.getErrorFieldOldPassword());
    }

    @Feature("Смена своего пароля админом")
    @Story("Ввод не валидных граничных значений нового пароля из 7 и 26 символов")
    @DisplayName("Ввод не валидных граничных значений нового пароля из 7 и 26 символов")
    @ParameterizedTest
    @ValueSource(strings = {"Wwqq12#", "Wwqq123456789#QQgg12345678"})
    void fillLimitInvalidValuesNewPasswordChangeMainPassword_7_26_Symbol(String password) {
        UserPanel userPanel = headerMenu.clickButtonUserPanel();
        ChangeMinePasswordWindow changeMinePassWindow = userPanel.clickButtonChangePassword();
        changeMinePassWindow.verifyChangeMinePasswordWindow()
                .fillFieldNewPassword(password);
        assertEquals("Пароль не валиден", changeMinePassWindow.getErrorFieldNewPassword());
    }

    @Feature("Смена своего пароля админом")
    @Story("Ввод валидных граничных значений нового пароля из 8,9,24 и 25 символов")
    @DisplayName("Ввод валидных граничных значений нового пароля из 8,9,24 и 25 символов")
    @ParameterizedTest
    @ValueSource(strings = {"Wwqq123#", "Wwqq1234#", "Wwqq123456789#QQgg123456", "Wwqq123456789#QQgg1234567"})
    void fillLimitValidValuesNewPasswordChangeMainPassword_8_9_24_25_Symbol(String password) {
        UserPanel userPanel = headerMenu.clickButtonUserPanel();
        ChangeMinePasswordWindow changeMinePassWindow = userPanel.clickButtonChangePassword();
        changeMinePassWindow.verifyChangeMinePasswordWindow()
                .fillFieldNewPassword(password);
        assertFalse(changeMinePassWindow.isErrorNewPasswordAppear());
    }

    @Feature("Смена своего пароля админом")
    @Story("Ввод не валидного нового пароля")
    @DisplayName("Ввод не валидного нового пароля")
    @ParameterizedTest
    @ValueSource(strings = {"123456789!", "123456789Ss", "123456789!ss", "123456789!SS", "WwqqLLpp!!", "Wwqq 123456 #"})
    void fillInvalidValuesNewPasswordChangeMainPassword(String password) {
        UserPanel userPanel = headerMenu.clickButtonUserPanel();
        ChangeMinePasswordWindow changeMinePassWindow = userPanel.clickButtonChangePassword();
        changeMinePassWindow.verifyChangeMinePasswordWindow()
                .fillFieldNewPassword(password);
        assertEquals("Пароль не валиден", changeMinePassWindow.getErrorFieldNewPassword());
    }

    @Feature("Смена своего пароля админом")
    @Story("Очистка полей через кнопку в окне изменения своего пароля")
    @DisplayName("Очистка полей через кнопку в окне изменения своего пароля")
    @Test
    void clearFieldsWindowChangeMainPasswordThroughButtonClear() {
        UserPanel userPanel = headerMenu.clickButtonUserPanel();
        ChangeMinePasswordWindow changeMinePassWindow = userPanel.clickButtonChangePassword();
        changeMinePassWindow.verifyChangeMinePasswordWindow()
                .fillFieldOldPassword(generatePassword())
                .clickClearButtonOldPasswordField()
                .fillFieldNewPassword(generatePassword())
                .clickClearButtonNewPasswordField();
        assertEquals("Обязательное поле", changeMinePassWindow.getErrorFieldOldPassword());
        assertEquals("Обязательное поле", changeMinePassWindow.getErrorFieldNewPassword());
        assertEquals("", changeMinePassWindow.getValueOldPasswordField());
        assertEquals("", changeMinePassWindow.getValueNewPasswordField());
    }

    @Feature("Смена своего пароля админом")
    @Story("Сброс значений полей в окне изменения своего пароля после закрытия окна")
    @DisplayName("Сброс значений полей в окне изменения своего пароля после закрытия окна")
    @Test
    void closeWindowChangeMainPassword() {
        UserPanel userPanel = headerMenu.clickButtonUserPanel();
        ChangeMinePasswordWindow changeMinePassWindow = userPanel.clickButtonChangePassword();
        changeMinePassWindow.verifyChangeMinePasswordWindow()
                .fillFieldOldPassword(generatePassword())
                .fillFieldNewPassword(generatePassword())
                .clickCancelButtonChangePassword();
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
        UserPanel userPanel = headerMenu.clickButtonUserPanel();
        headerMenu.clickButtonUserPanel();
        assertFalse(userPanel.isWindowAppear());
    }
}
