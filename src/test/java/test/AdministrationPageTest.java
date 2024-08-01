package test;

import data.TestData;
import pages.AdministrationPage.*;
import pages.Calendar.Calendar;
import pages.HeaderMenu.HeaderMenu;
import utils.dbUtils.DataBaseQuery;
import utils.otherUtils.TestHelper;
import utils.preparationDataTests.administration.AdminAddDecorator;
import utils.preparationDataTests.administration.AdminAddDeleteDecorator;
import utils.preparationDataTests.administration.AdminDeleteDecorator;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static data.TestData.DataTest.login;
import static data.TestData.DataTest.password;
import static data.TestData.UserData.*;
import static utils.otherUtils.DataGenerator.generateLogin;
import static utils.otherUtils.DataGenerator.generatePassword;
import static org.junit.jupiter.api.Assertions.*;


@Epic("Администрирование")
@DisplayName("Страница Администрирования")
public class AdministrationPageTest extends BaseTest {

    private AdministrationPage adminPage;

    @BeforeAll
    static void setUpAuth() {
        BaseTest.openAdminPanel(LOGIN_SUPER_ADMIN, PASSWORD_SUPER_ADMIN);
        HeaderMenu headerMenu = new HeaderMenu();
        headerMenu.clickAdministrationTab();
    }

    @BeforeEach
    void setUp() {
        Selenide.refresh();
        adminPage = new AdministrationPage();
    }

    @AfterAll
    static void closeWebDriver() {
        Selenide.closeWebDriver();
    }

    @Feature("Добавление нового админа")
    @Story("Успешное добавление нового админа")
    @DisplayName("Успешное добавление нового админа")
    @ExtendWith(AdminDeleteDecorator.class)
    @Test
    void addNewAdmin() {
        NewAdminWindow newAdminWindow = adminPage.clickButtonAddNewAdmin();
        newAdminWindow.verifyNewAdminWindow();
        newAdminWindow.fillFieldNewAdminLogin(generateLogin());
        newAdminWindow.fillFieldNewAdminPassword(generatePassword());
        newAdminWindow.fillFieldNewAdminConfirmPassword(password);
        newAdminWindow.clickAddButton();
        assertEquals("Новый администратор " + login + " успешно создан", adminPage.getTextNotification());
        assertEquals(1, DataBaseQuery.selectAdmin(login).getRole_id());
        assertFalse(DataBaseQuery.selectAdmin(login).getIs_blocked());
        assertFalse(DataBaseQuery.selectAdmin(login).getIs_deleted());
        assertEquals("SIGN_UP_ADMIN_SUCCESS", DataBaseQuery.selectLog(login).getCode());
        assertTrue(adminPage.isVisibleAdminCard(login));
        assertFalse(newAdminWindow.isWindowAppear());
    }


    @Feature("Добавление нового админа")
    @Story("Добавление нового админа с уже существующим логином")
    @DisplayName("Добавление нового админа с уже существующим логином")
    @ExtendWith(AdminAddDeleteDecorator.class)
    @Test
    void addNewAdminIfAdminAlreadyExisting() {
        NewAdminWindow newAdminWindow = adminPage.clickButtonAddNewAdmin();
        newAdminWindow.fillFieldNewAdminLogin(login);
        newAdminWindow.fillFieldNewAdminPassword(generatePassword());
        newAdminWindow.fillFieldNewAdminConfirmPassword(password);
        newAdminWindow.clickAddButton();
        assertEquals("{\"error\":\"Пользователь уже существует, логин: " + login + "\",\"innerError\":null,\"exception\":\"AlreadyExistException\"}", adminPage.getTextNotification());
        assertTrue(adminPage.isVisibleAdminCard(login));
    }


    @Feature("Добавление нового админа")
    @Story("Добавление нового администратора с пустым полем логина")
    @DisplayName("Добавление нового администратора с пустым полем логина")
    @Test
    void addNewAdminEmptyFieldLogin() {
        NewAdminWindow newAdminWindow = adminPage.clickButtonAddNewAdmin();
        newAdminWindow.fillFieldNewAdminPassword(generatePassword());
        newAdminWindow.fillFieldNewAdminConfirmPassword(password);
        assertFalse(newAdminWindow.isEnabledAddButton());
    }

    @Feature("Добавление нового админа")
    @Story("Добавление нового администратора с пустым полем пароля")
    @DisplayName("Добавление нового администратора с пустым полем пароля")
    @Test
    void addNewAdminEmptyFieldPassword() {
        NewAdminWindow newAdminWindow = adminPage.clickButtonAddNewAdmin();
        newAdminWindow.fillFieldNewAdminLogin(generateLogin());
        newAdminWindow.fillFieldNewAdminConfirmPassword(generatePassword());
        assertFalse(newAdminWindow.isEnabledAddButton());
        assertEquals("Не соответствует паролю", newAdminWindow.getErrorFieldConfirmPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Добавление нового администратора с пустым полем подтверждения пароля")
    @DisplayName("Добавление нового администратора с пустым полем подтверждения пароля")
    @Test
    void addNewAdminEmptyFieldConfirmPassword() {
        NewAdminWindow newAdminWindow = adminPage.clickButtonAddNewAdmin();
        newAdminWindow.fillFieldNewAdminLogin(generateLogin());
        newAdminWindow.fillFieldNewAdminPassword(generatePassword());
        assertFalse(newAdminWindow.isEnabledAddButton());
    }


    @Feature("Добавление нового админа")
    @Story("Добавление нового администратора с пустым полем логина и пароля")
    @DisplayName("Добавление нового администратора с пустым полем логина и пароля")
    @Test
    void addNewAdminEmptyFieldsLoginPassword() {
        NewAdminWindow newAdminWindow = adminPage.clickButtonAddNewAdmin();
        newAdminWindow.fillFieldNewAdminConfirmPassword(generatePassword());
        assertFalse(newAdminWindow.isEnabledAddButton());
        assertEquals("Не соответствует паролю", newAdminWindow.getErrorFieldConfirmPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Добавление нового администратора с пустым полем логина и подтверждения пароля")
    @DisplayName("Добавление нового администратора с пустым полем логина и подтверждения пароля")
    @Test
    void addNewAdminEmptyFieldsLoginConfirmPassword() {
        NewAdminWindow newAdminWindow = adminPage.clickButtonAddNewAdmin();
        newAdminWindow.fillFieldNewAdminPassword(generatePassword());
        assertFalse(newAdminWindow.isEnabledAddButton());
    }

    @Feature("Добавление нового админа")
    @Story("Добавление нового администратора с пустым полем пароля и подтверждения пароля")
    @DisplayName("Добавление нового администратора с пустым полем пароля и подтверждения пароля")
    @Test
    void addNewAdminEmptyFieldsPasswordConfirmPassword() {
        NewAdminWindow newAdminWindow = adminPage.clickButtonAddNewAdmin();
        newAdminWindow.fillFieldNewAdminLogin(generateLogin());
        assertFalse(newAdminWindow.isEnabledAddButton());
    }

    @Feature("Добавление нового админа")
    @Story("Отображение уведомления об обязательных полях")
    @DisplayName("Отображение уведомления об обязательных полях")
    @Test
    void displayNotificationAboutRequiredFieldsWindowAddingNewAdmin() {
        NewAdminWindow newAdminWindow = adminPage.clickButtonAddNewAdmin();
        newAdminWindow.clickFieldNewAdminLogin();
        newAdminWindow.clickFieldNewAdminPassword();
        newAdminWindow.clickFieldNewAdminConfirmPassword();
        assertFalse(newAdminWindow.isEnabledAddButton());
        assertEquals("Обязательное поле", newAdminWindow.getErrorFieldLogin());
        assertEquals("Обязательное поле", newAdminWindow.getErrorFieldPassword());
        assertEquals("Обязательное поле", newAdminWindow.getErrorFieldConfirmPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод валидных граничных значений логина из 31 и 32 символов")
    @DisplayName("Ввод валидных граничных значений логина из 31 и 32 символов")
    @ParameterizedTest
    @ValueSource(strings = {"ANNA_TEST_ADMIN123456789_ANNA_1", "ANNA_TEST_ADMIN123456789_ANNA_12"})
    void fillLimitValidValuesLoginAddingNewAdmin_31_32_Symbol(String login) {
        NewAdminWindow newAdminWindow = adminPage.clickButtonAddNewAdmin();
        newAdminWindow.fillFieldNewAdminLogin(login);
        assertFalse(newAdminWindow.isErrorLoginAppear());
    }


    @Feature("Добавление нового админа")
    @Story("Ввод не валидных граничных значений логина из 33 символов")
    @DisplayName("Ввод не валидных граничных значений логина из 33 символов")
    @Test
    void fillLimitInvalidValuesLoginAddingNewAdmin_33_Symbol() {
        NewAdminWindow newAdminWindow = adminPage.clickButtonAddNewAdmin();
        newAdminWindow.fillFieldNewAdminLogin("ANNA_TEST_ADMIN123456789_ANNA_123");
        assertEquals("Максимальная длина 32 символа", newAdminWindow.getErrorFieldLogin());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не валидного логина с не валидным первым символом")
    @DisplayName("Ввод не валидного логина с не валидным первым символом")
    @ParameterizedTest
    @ValueSource(strings = {"БRUCE_LI", "1ANNA_TEST", "Админ_25"})
    void fillInvalidValuesFirstSymbolLoginAddingNewAdmin(String login) {
        NewAdminWindow newAdminWindow = adminPage.clickButtonAddNewAdmin();
        newAdminWindow.fillFieldNewAdminLogin(login);
        assertEquals("Первый символ должен быть латинской буквой или \"_\"", newAdminWindow.getErrorFieldLogin());
    }


    @Feature("Добавление нового админа")
    @Story("Ввод не валидного логина")
    @DisplayName("Ввод не валидного логина")
    @ParameterizedTest
    @ValueSource(strings = {"AННА_ТЕСТ", "ANNA TEST"})
    void fillInvalidValuesLoginAddingNewAdmin(String login) {
        NewAdminWindow newAdminWindow = adminPage.clickButtonAddNewAdmin();
        newAdminWindow.fillFieldNewAdminLogin(login);
        assertEquals("Доступны только числа, латиница и \"_\"", newAdminWindow.getErrorFieldLogin());
    }


    @Feature("Добавление нового админа")
    @Story("Ввод не валидных граничных значений пароля из 7 и 26 символов")
    @DisplayName("Ввод не валидных граничных значений пароля из 7 и 26 символов")
    @ParameterizedTest
    @ValueSource(strings = {"Wwqq12#", "Wwqq123456789#QQgg12345678"})
    void fillLimitInvalidValuesPasswordAddingNewAdmin_7_26_Symbol(String password) {
        NewAdminWindow newAdminWindow = adminPage.clickButtonAddNewAdmin();
        newAdminWindow.fillFieldNewAdminPassword(password);
        assertEquals("Пароль не валиден", newAdminWindow.getErrorFieldPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод валидных граничных значений пароля из 8,9,24 и 25 символов")
    @DisplayName("Ввод валидных граничных значений пароля из 8,9,24 и 25 символов")
    @ParameterizedTest
    @ValueSource(strings = {"Wwqq123#", "Wwqq1234#", "Wwqq123456789#QQgg123456", "Wwqq123456789#QQgg1234567"})
    void fillLimitValidValuesPasswordAddingNewAdmin_8_9_24_25_Symbol(String password) {
        NewAdminWindow newAdminWindow = adminPage.clickButtonAddNewAdmin();
        newAdminWindow.fillFieldNewAdminPassword(password);
        assertFalse(newAdminWindow.isErrorPasswordAppear());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не валидного пароля")
    @DisplayName("Ввод не валидного пароля")
    @ParameterizedTest
    @ValueSource(strings = {"123456789!", "123456789Ss", "123456789!ss", "123456789!SS", "WwqqLLpp!!", "Wwqq 123456 #"})
    void fillInvalidValuesPasswordAddingNewAdmin(String password) {
        NewAdminWindow newAdminWindow = adminPage.clickButtonAddNewAdmin();
        newAdminWindow.fillFieldNewAdminPassword(password);
        assertEquals("Пароль не валиден", newAdminWindow.getErrorFieldPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не соответствующего пароля при подтверждении")
    @DisplayName("Ввод не соответствующего пароля при подтверждении")
    @Test
    void fillInvalidValuesConfirmPasswordAddingNewAdmin() {
        NewAdminWindow newAdminWindow = adminPage.clickButtonAddNewAdmin();
        newAdminWindow.fillFieldNewAdminLogin(generateLogin());
        newAdminWindow.fillFieldNewAdminPassword(generatePassword());
        newAdminWindow.fillFieldNewAdminConfirmPassword(generatePassword());
        assertEquals("Не соответствует паролю", newAdminWindow.getErrorFieldConfirmPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Очистка полей через кнопку в окне добавления админа")
    @DisplayName("Очистка полей через кнопку в окне добавления админа")
    @Test
    void clearFieldsWindowAddingAdminThroughButtonClear() {
        NewAdminWindow newAdminWindow = adminPage.clickButtonAddNewAdmin();
        newAdminWindow.fillFieldNewAdminLogin(generateLogin());
        newAdminWindow.clickClearButtonLoginField();
        assertEquals("Обязательное поле", newAdminWindow.getErrorFieldLogin());
        newAdminWindow.fillFieldNewAdminPassword(generatePassword());
        newAdminWindow.clickClearButtonPasswordField();
        assertEquals("Обязательное поле", newAdminWindow.getErrorFieldPassword());
        newAdminWindow.fillFieldNewAdminConfirmPassword(generatePassword());
        newAdminWindow.clickClearButtonConfirmPasswordField();
        assertEquals("Обязательное поле", newAdminWindow.getErrorFieldConfirmPassword());
        assertEquals("", newAdminWindow.getValueLoginField());
        assertEquals("", newAdminWindow.getValuePasswordField());
        assertEquals("", newAdminWindow.getValueConfirmPasswordField());
    }


    @Feature("Добавление нового админа")
    @Story("Сброс значений полей в окне добавления админа после закрытия окна")
    @DisplayName("Сброс значений полей в окне добавления админа после закрытия окна")
    @Test
    void closeWindowAddingNewAdmin() {
        NewAdminWindow newAdminWindow = adminPage.clickButtonAddNewAdmin();
        newAdminWindow.fillFieldNewAdminLogin(generateLogin());
        newAdminWindow.fillFieldNewAdminPassword(generatePassword());
        newAdminWindow.fillFieldNewAdminConfirmPassword(generatePassword());
        newAdminWindow.closeWindowAddedAdmin();
        assertFalse(newAdminWindow.isWindowAppear());
        adminPage.clickButtonAddNewAdmin();
        assertEquals("", newAdminWindow.getValueLoginField());
        assertEquals("", newAdminWindow.getValuePasswordField());
    }


    @Feature("Замена пароля админу")
    @Story("Успешная замена пароля админу")
    @DisplayName("Успешная замена пароля админу")
    @ExtendWith(AdminAddDeleteDecorator.class)
    @Test
    void changePasswordAdmin() {
        adminPage.verifyAdminCard(login);
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.clickButtonChangePassword(login);
        changePasswordAdminWindow.verifyChangePasswordAdminWindow();
        assertTrue(changePasswordAdminWindow.isHeaderLoginAppear(login));
        changePasswordAdminWindow.fillFieldNewPassword(generatePassword());
        changePasswordAdminWindow.fillFieldConfirmPassword(password);
        changePasswordAdminWindow.clickButtonSaveNewPassword();
        assertEquals("Админ " + login + " успешно изменен", adminPage.getTextNotification());
        assertEquals("RESET_PASSWORD_SADMIN_SUCCESS", DataBaseQuery.selectLog(login).getCode());
        assertFalse(changePasswordAdminWindow.isWindowAppear());
    }

    @Feature("Замена пароля админу")
    @Story("Замена пароля админу с пустым полем пароля")
    @DisplayName("Замена пароля админу с пустым полем пароля")
    @ExtendWith(AdminAddDeleteDecorator.class)
    @Test
    void changePasswordAdminEmptyFieldPassword() {
        adminPage.scrollToCardAdmin(login);
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.clickButtonChangePassword(login);
        changePasswordAdminWindow.fillFieldConfirmPassword(generatePassword());
        assertFalse(changePasswordAdminWindow.isEnabledSaveButton());
        assertEquals("Не соответствует паролю", changePasswordAdminWindow.getErrorFieldConfirmPassword());
    }

    @Feature("Смена пароля админу")
    @Story("Замена пароля админу с пустым полем подтверждения пароля")
    @DisplayName("Замена пароля админу с пустым полем подтверждения пароля")
    @ExtendWith(AdminAddDeleteDecorator.class)
    @Test
    void changePasswordAdminEmptyFieldConfirmPassword() {
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.clickButtonChangePassword(login);
        changePasswordAdminWindow.fillFieldNewPassword(generatePassword());
        assertFalse(changePasswordAdminWindow.isEnabledSaveButton());
    }

    @Feature("Замена пароля админу")
    @Story("Отображение уведомления об обязательности полей")
    @DisplayName("Отображение уведомления об обязательности полей")
    @ExtendWith(AdminAddDeleteDecorator.class)
    @Test
    void displayNotificationAboutRequiredFieldsWindowChangingPasswordAdmin() {
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.clickButtonChangePassword(login);
        changePasswordAdminWindow.clickFieldNewPassword();
        changePasswordAdminWindow.clickFieldConfirmPassword();
        assertEquals("Обязательное поле", changePasswordAdminWindow.getErrorFieldPassword());
        assertEquals("Обязательное поле", changePasswordAdminWindow.getErrorFieldConfirmPassword());
    }

    @Feature("Замена пароля админу")
    @Story("Ввод не соответствующего пароля при подтверждении")
    @DisplayName("Ввод не соответствующего пароля при подтверждении")
    @ExtendWith(AdminAddDeleteDecorator.class)
    @Test
    void fillInvalidValuesConfirmPasswordChangingPasswordAdmin() {
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.clickButtonChangePassword(login);
        changePasswordAdminWindow.clickFieldNewPassword();
        changePasswordAdminWindow.fillFieldNewPassword(generatePassword());
        changePasswordAdminWindow.fillFieldConfirmPassword(generatePassword());
        changePasswordAdminWindow.clickFieldNewPassword();
        assertFalse(changePasswordAdminWindow.isEnabledSaveButton());
        assertEquals("Не соответствует паролю", changePasswordAdminWindow.getErrorFieldConfirmPassword());
    }

    @Feature("Замена пароля админу")
    @Story("Ввод не валидных граничных значений пароля из 7 символов")
    @DisplayName("Ввод не валидных граничных значений пароля из 7 символов")
    @ExtendWith(AdminAddDeleteDecorator.class)
    @Test
    void fillLimitInvalidValuesPasswordChainingPasswordAdmin_7_Symbol() {
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.clickButtonChangePassword(login);
        changePasswordAdminWindow.fillFieldNewPassword("Wwqq12#");
        assertEquals("Пароль не валиден", changePasswordAdminWindow.getErrorFieldPassword());
    }

    @Feature("Замена пароля админу")
    @Story("Ввод валидных граничных значений пароля из 8,9,24 и 25 символов")
    @DisplayName("Ввод валидных граничных значений пароля из 8,9,24 и 25 символов")
    @ExtendWith(AdminAddDeleteDecorator.class)
    @ParameterizedTest
    @ValueSource(strings = {"Wwqq123#", "Wwqq1234#", "Wwqq123456789#QQgg123456", "Wwqq123456789#QQgg1234567"})
    void fillLimitValidValuesPasswordChainingPasswordAdmin_8_9_24_25_Symbol(String login) {
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.clickButtonChangePassword(TestData.DataTest.login);
        changePasswordAdminWindow.fillFieldNewPassword(login);
        assertFalse(changePasswordAdminWindow.isErrorPasswordAppear());
    }


    @Feature("Замена пароля админу")
    @Story("Ввод не валидных граничных значений пароля из 26 символов")
    @DisplayName("Ввод не валидных граничных значений пароля из 26 символов")
    @ExtendWith(AdminAddDeleteDecorator.class)
    @Test
    void fillLimitInvalidValuesPasswordChainingPasswordAdmin_26_Symbol() {
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.clickButtonChangePassword(login);
        changePasswordAdminWindow.fillFieldNewPassword("Wwqq123456789#QQgg12345678");
        assertEquals("Пароль не валиден", changePasswordAdminWindow.getErrorFieldPassword());
    }

    @Feature("Замена пароля админу")
    @Story("Ввод не валидного пароля")
    @DisplayName("Ввод не валидного пароля")
    @ExtendWith(AdminAddDeleteDecorator.class)
    @ParameterizedTest
    @ValueSource(strings = {"123456789!", "123456789Ss", "123456789!ss", "123456789!SS", "WwqqLLpp!!", "Wwqq 123456 #"})
    void fillInvalidValuesPasswordChainingPasswordAdmin() {
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.clickButtonChangePassword(login);
        changePasswordAdminWindow.fillFieldNewPassword("wwqq123456#");
        assertEquals("Пароль не валиден", changePasswordAdminWindow.getErrorFieldPassword());
    }

    @Feature("Замена пароля админу")
    @Story("Очистка полей через кнопку в окне изменения пароля админу")
    @DisplayName("Очистка полей через кнопку в окне изменения пароля админу")
    @ExtendWith(AdminAddDeleteDecorator.class)
    @Test
    void clearFieldsWindowChainingPasswordThroughButtonClear() {
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.clickButtonChangePassword(login);
        changePasswordAdminWindow.fillFieldNewPassword(generatePassword());
        changePasswordAdminWindow.clickClearButtonNewPasswordField();
        assertEquals("Обязательное поле", changePasswordAdminWindow.getErrorFieldPassword());
        changePasswordAdminWindow.fillFieldConfirmPassword(password);
        changePasswordAdminWindow.clickClearButtonConfirmPasswordField();
        assertEquals("Обязательное поле", changePasswordAdminWindow.getErrorFieldConfirmPassword());
        assertEquals("", changePasswordAdminWindow.getValuePasswordField());
        assertEquals("", changePasswordAdminWindow.getValueConfirmPasswordField());
    }

    @Feature("Замена пароля админу")
    @Story("Сброс значений полей в окне смены пароля админу при закрытии окна")
    @DisplayName("Сброс значений полей в окне смены пароля админу при закрытии окна")
    @ExtendWith(AdminAddDeleteDecorator.class)
    @Test
    void closeWindowChainingPasswordAdmin() {
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.clickButtonChangePassword(login);
        changePasswordAdminWindow.fillFieldNewPassword(generatePassword());
        changePasswordAdminWindow.fillFieldConfirmPassword(password);
        changePasswordAdminWindow.closeWindowChangePasswordAdmin();
        assertFalse(changePasswordAdminWindow.isWindowAppear());
        adminPage.clickButtonChangePassword(login);
        assertEquals("", changePasswordAdminWindow.getValuePasswordField());
        assertEquals("", changePasswordAdminWindow.getValueConfirmPasswordField());
    }

    @Feature("Удаление админа")
    @Story("Отмена удаления админа")
    @DisplayName("Отмена удаления админа")
    @ExtendWith(AdminAddDeleteDecorator.class)
    @Test
    void cancelDeleteAdmin() {
        adminPage.verifyAdminCard(login);
        DeleteAdminWindow deleteAdminWindow = adminPage.clickButtonDeleteAdmin(login);
        deleteAdminWindow.clickCancelButtonDeleteAdmin();
        assertTrue(adminPage.isVisibleAdminCard(login));
        assertFalse(deleteAdminWindow.isWindowAppear());
    }

    @Feature("Удаление админа")
    @Story("Успешное удаление админа")
    @DisplayName("Успешное удаление админа")
    @ExtendWith(AdminAddDecorator.class)
    @Test
    void deleteAdmin() {
        adminPage.verifyAdminCard(login);
        DeleteAdminWindow deleteAdminWindow = adminPage.clickButtonDeleteAdmin(login);
        deleteAdminWindow.verifyDeleteAdminWindow();
        assertTrue(deleteAdminWindow.verifyLoginAdmin(login));
        deleteAdminWindow.clickButtonDeleteAdmin();
        assertEquals("Админ " + login + " успешно удален", adminPage.getTextNotification());
        assertFalse(adminPage.isExistAdminCard(login));
        assertNull(DataBaseQuery.selectAdmin(login));
        assertEquals("DELETE_ADMIN_SUCCESS", DataBaseQuery.selectLog(login).getCode());
    }

    @Feature("Документация")
    @Story("Успешное обновление оферты")
    @DisplayName("Успешное обновление оферты")
    @Test
    void updateOffer() {
        UpdateLegalDocWindow updateLegalDocWindow = adminPage.clickButtonUpdateOffer();
        updateLegalDocWindow.verifyUploadDocWindow();
        String srcDoc = updateLegalDocWindow.getSrcDoc();
        updateLegalDocWindow.uploadDoc("src/test/resources/Оферта.pdf");
        adminPage.clickButtonUpdateOffer();
        assertEquals(srcDoc, updateLegalDocWindow.getSrcDoc());
        assertEquals("LEGAL_DOCUMENT_UPDATED_SUCCESS", DataBaseQuery.selectLog(LOGIN_SUPER_ADMIN).getCode());
    }

    @Feature("Документация")
    @Story("Успешное обновление политики обработки")
    @DisplayName("Успешное обновление политики обработки")
    @Test
    void updateProcessingPolicy() {
        UpdateLegalDocWindow updateLegalDocWindow = adminPage.clickButtonUpdateProcessingPolicy();
        updateLegalDocWindow.verifyUploadDocWindow();
        String srcDoc = updateLegalDocWindow.getSrcDoc();
        updateLegalDocWindow.uploadDoc("src/test/resources/Политика обработки персональных данных.pdf");
        adminPage.clickButtonUpdateProcessingPolicy();
        assertEquals(srcDoc, updateLegalDocWindow.getSrcDoc());
        assertEquals("LEGAL_DOCUMENT_UPDATED_SUCCESS", DataBaseQuery.selectLog(LOGIN_SUPER_ADMIN).getCode());
    }

    @Feature("Документация")
    @Story("Закрытие окна обновления оферты")
    @DisplayName("Закрытие окна обновления оферты")
    @Test
    void closeWindowUpdateOffer() {
        UpdateLegalDocWindow updateLegalDocWindow = adminPage.clickButtonUpdateOffer();
        updateLegalDocWindow.closeWindowUpdateLegalDoc();
        assertFalse(updateLegalDocWindow.isWindowAppear());
    }

    @Feature("Документация")
    @Story("Закрытие окна обновления политики обработки")
    @DisplayName("Закрытие окна обновления политики обработки")
    @Test
    void closeWindowUpdateProcessingPolicy() {
        UpdateLegalDocWindow updateLegalDocWindow = adminPage.clickButtonUpdateProcessingPolicy();
        updateLegalDocWindow.closeWindowUpdateLegalDoc();
        assertFalse(updateLegalDocWindow.isWindowAppear());
    }


    @Feature("Документация")
    @Story("Успешное обновление приказа с даты в текущем месяце")
    @DisplayName("Успешное обновление приказа с даты в текущем месяце")
    @Test
    void updateOrderCurrentMonth() {
        UpdateOrderWindow updateOrderWindow = adminPage.clickButtonUpdateOrder();
        updateOrderWindow.verifyUpdateOrderWindow();
        assertEquals(TestHelper.getCurrentDate(), updateOrderWindow.getValuesButtonToday());
        Calendar calendar = updateOrderWindow.openCalendarUpdateOrder();
        calendar.verifyCalendar();
        assertEquals(TestHelper.getCurrentMonthYear(), calendar.getNameCurrentMonth());
        calendar.clickDateActivation();
        updateOrderWindow.uploadOrder("src/test/resources/Приказ.xlsx");
        assertEquals("Федеральный приказ успешно обновлен", adminPage.getTextNotification());
        assertEquals("FEDERAL_SERVICES_UPDATED_SUCCESS", DataBaseQuery.selectLog(LOGIN_SUPER_ADMIN).getCode());
        assertFalse(updateOrderWindow.isWindowAppear());
    }

    @Feature("Документация")
    @Story("Успешное обновление приказа с даты в следующем месяце")
    @DisplayName("Успешное обновление приказа с даты в следующем месяце")
    @Test
    void updateOrderFutureMonth() {
        UpdateOrderWindow updateOrderWindow = adminPage.clickButtonUpdateOrder();
        Calendar calendar = updateOrderWindow.openCalendarUpdateOrder();
        calendar.switchFutureMonth();
        assertEquals(TestHelper.getFutureMonthYear(), calendar.getNameCurrentMonth());
        calendar.clickDateActivation();
        updateOrderWindow.uploadOrder("src/test/resources/Приказ.xlsx");
        assertEquals("Федеральный приказ успешно обновлен", adminPage.getTextNotification());
        assertEquals("FEDERAL_SERVICES_UPDATED_SUCCESS", DataBaseQuery.selectLog(LOGIN_SUPER_ADMIN).getCode());
        assertFalse(updateOrderWindow.isWindowAppear());
    }

    @Feature("Документация")
    @Story("Успешное обновление приказа с даты в предыдущем месяце")
    @DisplayName("Успешное обновление приказа с даты в предыдущем месяце")
    @Test
    void updateOrderPreviousMonth() {
        UpdateOrderWindow updateOrderWindow = adminPage.clickButtonUpdateOrder();
        Calendar calendar = updateOrderWindow.openCalendarUpdateOrder();
        calendar.switchPreviousMonth();
        assertEquals(TestHelper.getPreviousMonthYear(), calendar.getNameCurrentMonth());
        calendar.clickDateActivation();
        updateOrderWindow.uploadOrder("src/test/resources/Приказ.xlsx");
        assertEquals("Федеральный приказ успешно обновлен", adminPage.getTextNotification());
        assertEquals("FEDERAL_SERVICES_UPDATED_SUCCESS", DataBaseQuery.selectLog(LOGIN_SUPER_ADMIN).getCode());
        assertFalse(updateOrderWindow.isWindowAppear());
    }


    @Feature("Документация")
    @Story("Успешное обновление приказа с текущей даты")
    @DisplayName("Успешное обновление приказа с текущей даты")
    @Test
    void updateOrderTodayNotUseCalendar() {
        UpdateOrderWindow updateOrderWindow = adminPage.clickButtonUpdateOrder();
        updateOrderWindow.uploadOrder("src/test/resources/Приказ.xlsx");
        assertEquals("Федеральный приказ успешно обновлен", adminPage.getTextNotification());
        assertEquals("FEDERAL_SERVICES_UPDATED_SUCCESS", DataBaseQuery.selectLog(LOGIN_SUPER_ADMIN).getCode());
        assertFalse(updateOrderWindow.isWindowAppear());
    }

    @Feature("Документация")
    @Story("Закрытие окна обновления приказа")
    @DisplayName("Закрытие окна обновления приказа")
    @Test
    void closeWindowUpdateOrder() {
        UpdateOrderWindow updateOrderWindow = adminPage.clickButtonUpdateOrder();
        updateOrderWindow.closeWindowUpdateOrder();
        assertFalse(updateOrderWindow.isWindowAppear());
    }

    @Feature("Документация")
    @Story("Успешное обновление прайса с даты в текущем месяце")
    @DisplayName("Успешное обновление прайса с даты в текущем месяце")
    @Test
    void clickButtonUpdatePriceCurrentMonth() {
        UpdatePriceWindow updatePriceWindow = adminPage.clickButtonUpdatePrice();
        updatePriceWindow.verifyUpdatePriceWindow();
        assertEquals(TestHelper.getCurrentDate(), updatePriceWindow.getValuesButtonToday());
        Calendar calendar = updatePriceWindow.openCalendarUpdatePrice();
        calendar.verifyCalendar();
        assertEquals(TestHelper.getCurrentMonthYear(), calendar.getNameCurrentMonth());
        calendar.clickDateActivation();
        updatePriceWindow.uploadPrice("src/test/resources/Прайс.xlsx");
        assertEquals("Прайс успешно обновлен", adminPage.getTextNotification());
        assertEquals("PRICE_UPDATED_SUCCESS", DataBaseQuery.selectLog(LOGIN_SUPER_ADMIN).getCode());
        assertFalse(updatePriceWindow.isWindowAppear());
    }

    @Feature("Документация")
    @Story("Успешное обновление прайса с даты в следующем месяце")
    @DisplayName("Успешное обновление прайса с даты в следующем месяце")
    @Test
    void clickButtonUpdatePriceFutureMonth() {
        UpdatePriceWindow updatePriceWindow = adminPage.clickButtonUpdatePrice();
        Calendar calendar = updatePriceWindow.openCalendarUpdatePrice();
        calendar.verifyCalendar();
        calendar.switchFutureMonth();
        assertEquals(TestHelper.getFutureMonthYear(), calendar.getNameCurrentMonth());
        calendar.clickDateActivation();
        updatePriceWindow.uploadPrice("src/test/resources/Прайс.xlsx");
        assertEquals("Прайс успешно обновлен", adminPage.getTextNotification());
        assertEquals("PRICE_UPDATED_SUCCESS", DataBaseQuery.selectLog(LOGIN_SUPER_ADMIN).getCode());
        assertFalse(updatePriceWindow.isWindowAppear());
    }

    @Feature("Документация")
    @Story("Успешное обновление прайса с даты в прошлом месяце")
    @DisplayName("Успешное обновление прайса с даты в прошлом месяце")
    @Test
    void clickButtonUpdatePricePreviousMonth() {
        UpdatePriceWindow updatePriceWindow = adminPage.clickButtonUpdatePrice();
        Calendar calendar = updatePriceWindow.openCalendarUpdatePrice();
        calendar.switchPreviousMonth();
        assertEquals(TestHelper.getPreviousMonthYear(), calendar.getNameCurrentMonth());
        calendar.clickDateActivation();
        updatePriceWindow.uploadPrice("src/test/resources/Прайс.xlsx");
        assertEquals("Прайс успешно обновлен", adminPage.getTextNotification());
        assertEquals("PRICE_UPDATED_SUCCESS", DataBaseQuery.selectLog(LOGIN_SUPER_ADMIN).getCode());
        assertFalse(updatePriceWindow.isWindowAppear());
    }


    @Feature("Документация")
    @Story("Успешное обновление прайса с текущей даты")
    @DisplayName("Успешное обновление прайса с текущей даты")
    @Test
    void clickButtonUpdatePriceTodayNotUseCalendar() {
        UpdatePriceWindow updatePriceWindow = adminPage.clickButtonUpdatePrice();
        updatePriceWindow.uploadPrice("src/test/resources/Прайс.xlsx");
        assertEquals("Прайс успешно обновлен", adminPage.getTextNotification());
        assertEquals("PRICE_UPDATED_SUCCESS", DataBaseQuery.selectLog(LOGIN_SUPER_ADMIN).getCode());
        assertFalse(updatePriceWindow.isWindowAppear());
    }

    @Feature("Документация")
    @Story("Закрытие окна обновления прайса")
    @DisplayName("Закрытие окна обновления прайса")
    @Test
    void closeWindowClickButtonUpdatePrice() {
        UpdatePriceWindow updatePriceWindow = adminPage.clickButtonUpdatePrice();
        updatePriceWindow.closeWindowUpdatePrice();
        assertFalse(updatePriceWindow.isWindowAppear());
    }

    @Feature("Документация")
    @Story("Обновление оферты с не валидным файлом")
    @DisplayName("Обновление оферты с не валидным файлом")
    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/Оферта,Политика обработки docx.docx", "src/test/resources/Оферта, Политика обработки .xlsx.xlsx", "src/test/resources/Оферта, Политика обработки jpeg.jpg",})
    void updateOfferInvalidFormat(String path) {
        UpdateLegalDocWindow updateLegalDocWindow = adminPage.clickButtonUpdateOffer();
        updateLegalDocWindow.uploadDoc(path);
        assertEquals("Допускаются файлы с расширением PDF", adminPage.getTextNotification());
        assertTrue(updateLegalDocWindow.isWindowAppear());
    }


    @Feature("Документация")
    @Story("Обновление политики обработки с не валидным файлом")
    @DisplayName("Обновление политики обработки с не валидным файлом")
    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/Оферта,Политика обработки docx.docx", "src/test/resources/Оферта, Политика обработки .xlsx.xlsx", "src/test/resources/Оферта, Политика обработки jpeg.jpg"})
    void updateProcessingPolicyInvalidFormat(String path) {
        UpdateLegalDocWindow updateLegalDocWindow = adminPage.clickButtonUpdateProcessingPolicy();
        updateLegalDocWindow.uploadDoc(path);
        assertEquals("Допускаются файлы с расширением PDF", adminPage.getTextNotification());
        assertTrue(updateLegalDocWindow.isWindowAppear());
    }


    @Feature("Документация")
    @Story("Обновление приказа с ошибкой в строке")
    @DisplayName("Обновление приказа с ошибкой в строке")
    @Test
    void updateOrderWithStringError() {
        UpdateOrderWindow updateOrderWindow = adminPage.clickButtonUpdateOrder();
        updateOrderWindow.uploadOrder("src/test/resources/Приказ с ошибкой в строке 10858.xlsx");
        assertEquals("Ошибка в 10858 строке", adminPage.getTextNotification());
        assertTrue(updateOrderWindow.isWindowAppear());
    }

    @Feature("Документация")
    @Story("Обновление приказа с не валидным файлом")
    @DisplayName("Обновление приказа с не валидным файлом")
    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/Оферта,Политика обработки docx.docx", "src/test/resources/Оферта, Политика обработки jpeg.jpg", "src/test/resources/Оферта.pdf"})
    void updateOrderInvalidFormat(String path) {
        UpdateOrderWindow updateOrderWindow = adminPage.clickButtonUpdateOrder();
        updateOrderWindow.uploadOrder(path);
        assertEquals("Допускаются файлы с расширением xlsx", adminPage.getTextNotification());
        assertTrue(updateOrderWindow.isWindowAppear());
    }


    @Feature("Документация")
    @Story("Обновление прайса с ошибкой в строке")
    @DisplayName("Обновление прайса с ошибкой в строке")
    @Test
    void clickButtonUpdatePriceWithStringError() {
        UpdatePriceWindow updatePriceWindow = adminPage.clickButtonUpdatePrice();
        updatePriceWindow.uploadPrice("src/test/resources/Прайс с ошибкой в строке 1398.xlsx");
        assertEquals("Ошибка в 1398 строке", adminPage.getTextNotification());
        assertTrue(updatePriceWindow.isWindowAppear());
    }

    @Feature("Документация")
    @Story("Обновление прайса с ошибкой формата стоимости услуги")
    @DisplayName("Обновление прайса с ошибкой формата стоимости услуги")
    @Test
    void clickButtonUpdatePriceFormatPriceServiceError() {
        UpdatePriceWindow updatePriceWindow = adminPage.clickButtonUpdatePrice();
        updatePriceWindow.uploadPrice("src/test/resources/Прайс с ошибкой формата в услуге А26.30.004.002.xlsx");
        PriceErrorsWindow priceErrorsWindow = updatePriceWindow.priceErrorsWindow();
        priceErrorsWindow.verifyPriceErrorsWindow();
        assertEquals("Ошибка формата стоимости услуги, код: 'А26.30.004.002', стоимость: '1000'.", priceErrorsWindow.getErrorInfo());
    }

    @Feature("Документация")
    @Story("Переключение на вкладку правил корректирования файла прайса")
    @DisplayName("Переключение на вкладку правил корректирования файла прайса")
    @Test
    void openAdjustmentRulesPrice() {
        UpdatePriceWindow updatePriceWindow = adminPage.clickButtonUpdatePrice();
        updatePriceWindow.uploadPrice("src/test/resources/Прайс с ошибкой формата в услуге А26.30.004.002.xlsx");
        PriceErrorsWindow priceErrorsWindow = updatePriceWindow.priceErrorsWindow();
        priceErrorsWindow.clickAdjustmentRulesTab();
        assertTrue(priceErrorsWindow.isAdjustmentRulesAppear());
    }

    @Feature("Документация")
    @Story("Переключение на вкладку ошибок файла прайса")
    @DisplayName("Переключение на вкладку ошибок файла прайса")
    @Test
    void openErrorsPrice() {
        UpdatePriceWindow updatePriceWindow = adminPage.clickButtonUpdatePrice();
        updatePriceWindow.uploadPrice("src/test/resources/Прайс с ошибкой формата в услуге А26.30.004.002.xlsx");
        PriceErrorsWindow priceErrorsWindow = updatePriceWindow.priceErrorsWindow();
        priceErrorsWindow.clickAdjustmentRulesTab();
        priceErrorsWindow.clickErrorPriceTab();
        assertTrue(priceErrorsWindow.isErrorInfoAppear());
    }

    @Feature("Документация")
    @Story("Закрытие окна ошибок файла прайса")
    @DisplayName("Закрытие окна ошибок файла прайса")
    @Test
    void closeWindowErrorsPrice() {
        UpdatePriceWindow updatePriceWindow = adminPage.clickButtonUpdatePrice();
        updatePriceWindow.uploadPrice("src/test/resources/Прайс с ошибкой формата в услуге А26.30.004.002.xlsx");
        PriceErrorsWindow priceErrorsWindow = updatePriceWindow.priceErrorsWindow();
        priceErrorsWindow.closeWindowPriceErrors();
        assertFalse(priceErrorsWindow.isWindowAppear());
    }


    @Feature("Документация")
    @Story("Обновление прайса с не валидным файлом")
    @DisplayName("Обновление прайса с не валидным файлом")
    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/Оферта,Политика обработки docx.docx", "src/test/resources/Оферта, Политика обработки jpeg.jpg", "src/test/resources/Оферта.pdf"})
    void clickButtonUpdatePriceInvalidFormat(String path) {
        UpdatePriceWindow updatePriceWindow = adminPage.clickButtonUpdatePrice();
        updatePriceWindow.uploadPrice(path);
        assertEquals("Допускаются файлы с расширением xlsx", adminPage.getTextNotification());
        assertTrue(updatePriceWindow.isWindowAppear());

    }
}
/*
    @Feature("Документация")
    @Story("Загрузка файла с прайсом")
    @DisplayName("Загрузка файла с прайсом")
    @Test
    void downloadPrice() {
        UpdatePriceWindow updatePriceWindow = adminPage.clickButtonUpdatePrice();
        File downloadedFile = updatePriceWindow.downloadPriceDateActivation();
        assertTrue(downloadedFile.exists(), "Downloaded file does not exist");
        assertTrue(downloadedFile.length() > 0, "Downloaded file is empty");
    }
    */

