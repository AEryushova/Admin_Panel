package test;

import data.TestData;
import pages.AdministrationPage.*;
import pages.Calendar.Calendar;
import pages.HeaderMenu.HeaderMenu;
import utils.dbUtils.DataBaseQuery;
import utils.preparationData.administration.AdminAdd;
import utils.preparationData.administration.AdminAddDelete;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import utils.preparationData.administration.AdminDelete;

import static data.TestData.DataTest.login;
import static data.TestData.DataTest.password;
import static data.TestData.UserData.*;
import static utils.testsUtils.DataGenerator.generateLogin;
import static utils.testsUtils.DataGenerator.generatePassword;
import static org.junit.jupiter.api.Assertions.*;
import static utils.testsUtils.TestHelper.*;


@Epic("Администрирование")
@DisplayName("Страница Администрирования")
public class AdministrationPageTest extends BaseTest {

    @BeforeAll
    static void setUpAuth() {
        getAuthToken(LOGIN_SUPER_ADMIN, PASSWORD_SUPER_ADMIN);
    }

    @BeforeEach
    void setUp() {
        openAuthAdminPanel();
        HeaderMenu headerMenu = new HeaderMenu();
        headerMenu.clickAdministrationTab();
        adminPage.verifyAdminPage();
    }

    @AfterEach()
    void closeWebDriver() {
        closeDriver();
    }


    @Feature("Добавление нового админа")
    @Story("Успешное добавление нового админа")
    @DisplayName("Успешное добавление нового админа")
    @Tag("CRITICAL")
    @ExtendWith(AdminDelete.class)
    @Test
    void addNewAdmin() {
        NewAdminWindow newAdminWindow = adminPage.clickButtonAddNewAdmin();
        newAdminWindow.verifyNewAdminWindow()
                .fillFieldNewAdminLogin(generateLogin())
                .fillFieldNewAdminPassword(generatePassword())
                .fillFieldNewAdminConfirmPassword(password)
                .clickAddButton();
        assertEquals("Новый администратор " + login + " успешно создан", adminPage.getTextNotification());
        assertEquals(1, DataBaseQuery.selectAdmin(login).getRole_id());
        assertFalse(DataBaseQuery.selectAdmin(login).getIs_blocked());
        assertFalse(DataBaseQuery.selectAdmin(login).getIs_deleted());
        assertEquals("SIGN_UP_ADMIN_SUCCESS", DataBaseQuery.selectLog(login).getCode());
        assertTrue(adminPage.isVisibleAdminCard(login));
    }

    @Feature("Добавление нового админа")
    @Story("Добавление нового админа с уже существующим логином")
    @DisplayName("Добавление нового админа с уже существующим логином")
    @ExtendWith(AdminAddDelete.class)
    @Test
    void addNewAdminIfAdminAlreadyExisting() {
        NewAdminWindow newAdminWindow = adminPage.clickButtonAddNewAdmin();
        newAdminWindow.verifyNewAdminWindow()
                .fillFieldNewAdminLogin(login)
                .fillFieldNewAdminPassword(generatePassword())
                .fillFieldNewAdminConfirmPassword(password)
                .clickAddButton();
        assertEquals("{\"error\":\"Пользователь уже существует, логин: " + login + "\",\"innerError\":null,\"exception\":\"AlreadyExistException\"}", adminPage.getTextNotification());
        assertTrue(adminPage.isVisibleAdminCard(login));
    }

    @Feature("Добавление нового админа")
    @Story("Добавление нового администратора с пустым полем логина")
    @DisplayName("Добавление нового администратора с пустым полем логина")
    @Test
    void addNewAdminEmptyFieldLogin() {
        NewAdminWindow newAdminWindow = adminPage.clickButtonAddNewAdmin()
                .verifyNewAdminWindow()
                .fillFieldNewAdminPassword(generatePassword())
                .fillFieldNewAdminConfirmPassword(password);
        assertFalse(newAdminWindow.isEnabledAddButton());
    }

    @Feature("Добавление нового админа")
    @Story("Добавление нового администратора с пустым полем пароля")
    @DisplayName("Добавление нового администратора с пустым полем пароля")
    @Test
    void addNewAdminEmptyFieldPassword() {
        NewAdminWindow newAdminWindow = adminPage.clickButtonAddNewAdmin()
                .verifyNewAdminWindow()
                .fillFieldNewAdminLogin(generateLogin())
                .fillFieldNewAdminConfirmPassword(generatePassword());
        assertFalse(newAdminWindow.isEnabledAddButton());
        assertEquals("Не соответствует паролю", newAdminWindow.getErrorFieldConfirmPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Добавление нового администратора с пустым полем подтверждения пароля")
    @DisplayName("Добавление нового администратора с пустым полем подтверждения пароля")
    @Test
    void addNewAdminEmptyFieldConfirmPassword() {
        NewAdminWindow newAdminWindow = adminPage.clickButtonAddNewAdmin()
                .verifyNewAdminWindow()
                .fillFieldNewAdminLogin(generateLogin())
                .fillFieldNewAdminPassword(generatePassword());
        assertFalse(newAdminWindow.isEnabledAddButton());
    }

    @Feature("Добавление нового админа")
    @Story("Добавление нового администратора с пустым полем логина и пароля")
    @DisplayName("Добавление нового администратора с пустым полем логина и пароля")
    @Test
    void addNewAdminEmptyFieldsLoginPassword() {
        NewAdminWindow newAdminWindow = adminPage.clickButtonAddNewAdmin()
                .verifyNewAdminWindow()
                .fillFieldNewAdminConfirmPassword(generatePassword());
        assertFalse(newAdminWindow.isEnabledAddButton());
        assertEquals("Не соответствует паролю", newAdminWindow.getErrorFieldConfirmPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Добавление нового администратора с пустым полем логина и подтверждения пароля")
    @DisplayName("Добавление нового администратора с пустым полем логина и подтверждения пароля")
    @Test
    void addNewAdminEmptyFieldsLoginConfirmPassword() {
        NewAdminWindow newAdminWindow = adminPage.clickButtonAddNewAdmin()
                .verifyNewAdminWindow()
                .fillFieldNewAdminPassword(generatePassword());
        assertFalse(newAdminWindow.isEnabledAddButton());
    }

    @Feature("Добавление нового админа")
    @Story("Добавление нового администратора с пустым полем пароля и подтверждения пароля")
    @DisplayName("Добавление нового администратора с пустым полем пароля и подтверждения пароля")
    @Test
    void addNewAdminEmptyFieldsPasswordConfirmPassword() {
        NewAdminWindow newAdminWindow = adminPage.clickButtonAddNewAdmin()
                .verifyNewAdminWindow()
                .fillFieldNewAdminLogin(generateLogin());
        assertFalse(newAdminWindow.isEnabledAddButton());
    }

    @Feature("Добавление нового админа")
    @Story("Отображение уведомления об обязательных полях")
    @DisplayName("Отображение уведомления об обязательных полях")
    @Test
    void displayNotificationAboutRequiredFieldsWindowAddingNewAdmin() {
        NewAdminWindow newAdminWindow = adminPage.clickButtonAddNewAdmin()
                .verifyNewAdminWindow()
                .clickFieldNewAdminLogin()
                .clickFieldNewAdminPassword()
                .clickFieldNewAdminConfirmPassword();
        assertFalse(newAdminWindow.isEnabledAddButton());
        assertEquals("Обязательное поле", newAdminWindow.getErrorFieldLogin());
        assertEquals("Обязательное поле", newAdminWindow.getErrorFieldPassword());
        assertEquals("Обязательное поле", newAdminWindow.getErrorFieldConfirmPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод валидных граничных значений логина из 31 и 32 символов")
    @DisplayName("Ввод валидных граничных значений логина из 31 и 32 символов")
    @ParameterizedTest()
    @ValueSource(strings = {"ANNA_TEST_ADMIN123456789_ANNA_1", "ANNA_TEST_ADMIN123456789_ANNA_12"})
    void fillLimitValidValuesLoginAddingNewAdmin_31_32_Symbol(String login) {
        NewAdminWindow newAdminWindow = adminPage.clickButtonAddNewAdmin()
                .verifyNewAdminWindow()
                .fillFieldNewAdminLogin(login);
        assertFalse(newAdminWindow.isErrorLoginAppear());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не валидных граничных значений логина из 33 символов")
    @DisplayName("Ввод не валидных граничных значений логина из 33 символов")
    @Test
    void fillLimitInvalidValuesLoginAddingNewAdmin_33_Symbol() {
        NewAdminWindow newAdminWindow = adminPage.clickButtonAddNewAdmin()
                .verifyNewAdminWindow()
                .fillFieldNewAdminLogin("ANNA_TEST_ADMIN123456789_ANNA_123");
        assertEquals("Максимальная длина 32 символа", newAdminWindow.getErrorFieldLogin());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не валидного логина с не валидным первым символом")
    @DisplayName("Ввод не валидного логина с не валидным первым символом")
    @ParameterizedTest
    @ValueSource(strings = {"БRUCE_LI", "1ANNA_TEST", "Админ_25"})
    void fillInvalidValuesFirstSymbolLoginAddingNewAdmin(String login) {
        NewAdminWindow newAdminWindow = adminPage.clickButtonAddNewAdmin()
                .verifyNewAdminWindow()
                .fillFieldNewAdminLogin(login);
        assertEquals("Первый символ должен быть латинской буквой или \"_\"", newAdminWindow.getErrorFieldLogin());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не валидного логина")
    @DisplayName("Ввод не валидного логина")
    @ParameterizedTest
    @ValueSource(strings = {"AННА_ТЕСТ", "ANNA TEST"})
    void fillInvalidValuesLoginAddingNewAdmin(String login) {
        NewAdminWindow newAdminWindow = adminPage.clickButtonAddNewAdmin()
                .verifyNewAdminWindow()
                .fillFieldNewAdminLogin(login);
        assertEquals("Доступны только числа, латиница и \"_\"", newAdminWindow.getErrorFieldLogin());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не валидных граничных значений пароля из 7 и 26 символов")
    @DisplayName("Ввод не валидных граничных значений пароля из 7 и 26 символов")
    @ParameterizedTest
    @ValueSource(strings = {"Wwqq12#", "Wwqq123456789#QQgg12345678"})
    void fillLimitInvalidValuesPasswordAddingNewAdmin_7_26_Symbol(String password) {
        NewAdminWindow newAdminWindow = adminPage.clickButtonAddNewAdmin()
                .verifyNewAdminWindow()
                .fillFieldNewAdminPassword(password);
        assertEquals("Пароль не валиден", newAdminWindow.getErrorFieldPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод валидных граничных значений пароля из 8,9,24 и 25 символов")
    @DisplayName("Ввод валидных граничных значений пароля из 8,9,24 и 25 символов")
    @ParameterizedTest
    @ValueSource(strings = {"Wwqq123#", "Wwqq1234#", "Wwqq123456789#QQgg123456", "Wwqq123456789#QQgg1234567"})
    void fillLimitValidValuesPasswordAddingNewAdmin_8_9_24_25_Symbol(String password) {
        NewAdminWindow newAdminWindow = adminPage.clickButtonAddNewAdmin()
                .verifyNewAdminWindow()
                .fillFieldNewAdminPassword(password);
        assertFalse(newAdminWindow.isErrorPasswordAppear());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не валидного пароля")
    @DisplayName("Ввод не валидного пароля")
    @ParameterizedTest
    @ValueSource(strings = {"123456789!", "123456789Ss", "123456789!ss", "123456789!SS", "WwqqLLpp!!", "Wwqq 123456 #"})
    void fillInvalidValuesPasswordAddingNewAdmin(String password) {
        NewAdminWindow newAdminWindow = adminPage.clickButtonAddNewAdmin()
                .verifyNewAdminWindow()
                .fillFieldNewAdminPassword(password);
        assertEquals("Пароль не валиден", newAdminWindow.getErrorFieldPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не соответствующего пароля при подтверждении")
    @DisplayName("Ввод не соответствующего пароля при подтверждении")
    @Test
    void fillInvalidValuesConfirmPasswordAddingNewAdmin() {
        NewAdminWindow newAdminWindow = adminPage.clickButtonAddNewAdmin()
                .verifyNewAdminWindow()
                .fillFieldNewAdminLogin(generateLogin())
                .fillFieldNewAdminPassword(generatePassword())
                .fillFieldNewAdminConfirmPassword(generatePassword());
        assertEquals("Не соответствует паролю", newAdminWindow.getErrorFieldConfirmPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Очистка полей через кнопку в окне добавления админа")
    @DisplayName("Очистка полей через кнопку в окне добавления админа")
    @Test
    void clearFieldsWindowAddingAdminThroughButtonClear() {
        NewAdminWindow newAdminWindow = adminPage.clickButtonAddNewAdmin();
        newAdminWindow.verifyNewAdminWindow()
                .fillFieldNewAdminLogin(generateLogin())
                .clickClearButtonLoginField()
                .fillFieldNewAdminPassword(generatePassword())
                .clickClearButtonPasswordField()
                .fillFieldNewAdminConfirmPassword(generatePassword())
                .clickClearButtonConfirmPasswordField();
        assertEquals("Обязательное поле", newAdminWindow.getErrorFieldLogin());
        assertEquals("Обязательное поле", newAdminWindow.getErrorFieldPassword());
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
        newAdminWindow.verifyNewAdminWindow()
                .fillFieldNewAdminLogin(generateLogin())
                .fillFieldNewAdminPassword(generatePassword())
                .fillFieldNewAdminConfirmPassword(generatePassword())
                .closeWindowAddedAdmin();
        assertFalse(newAdminWindow.isWindowAppear());
        adminPage.clickButtonAddNewAdmin();
        assertEquals("", newAdminWindow.getValueLoginField());
        assertEquals("", newAdminWindow.getValuePasswordField());
    }

    @Feature("Замена пароля админу")
    @Story("Успешная замена пароля админу")
    @DisplayName("Успешная замена пароля админу")
    @ExtendWith(AdminAddDelete.class)
    @Test
    void changePasswordAdmin() {
        adminPage.scrollToCardAdmin(login)
                .verifyAdminCard(login);
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.clickButtonChangePassword(login)
                .verifyChangePasswordAdminWindow();
        assertTrue(changePasswordAdminWindow.isHeaderLoginAppear(login));
        changePasswordAdminWindow.fillFieldNewPassword(generatePassword())
                .fillFieldConfirmPassword(password)
                .clickButtonSaveNewPassword();
        assertEquals("Админ " + login + " успешно изменен", adminPage.getTextNotification());
        assertEquals("RESET_PASSWORD_SADMIN_SUCCESS", DataBaseQuery.selectLog(login).getCode());
    }

    @Feature("Замена пароля админу")
    @Story("Замена пароля админу с пустым полем пароля")
    @DisplayName("Замена пароля админу с пустым полем пароля")
    @ExtendWith(AdminAddDelete.class)
    @Test
    void changePasswordAdminEmptyFieldPassword() {
        adminPage.scrollToCardAdmin(login);
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.clickButtonChangePassword(login)
                .verifyChangePasswordAdminWindow()
                .fillFieldConfirmPassword(generatePassword());
        assertFalse(changePasswordAdminWindow.isEnabledSaveButton());
        assertEquals("Не соответствует паролю", changePasswordAdminWindow.getErrorFieldConfirmPassword());
    }

    @Feature("Смена пароля админу")
    @Story("Замена пароля админу с пустым полем подтверждения пароля")
    @DisplayName("Замена пароля админу с пустым полем подтверждения пароля")
    @ExtendWith(AdminAddDelete.class)
    @Test
    void changePasswordAdminEmptyFieldConfirmPassword() {
        adminPage.scrollToCardAdmin(login);
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.clickButtonChangePassword(login)
                .verifyChangePasswordAdminWindow()
                .fillFieldNewPassword(generatePassword());
        assertFalse(changePasswordAdminWindow.isEnabledSaveButton());
    }

    @Feature("Замена пароля админу")
    @Story("Отображение уведомления об обязательности полей")
    @DisplayName("Отображение уведомления об обязательности полей")
    @ExtendWith(AdminAddDelete.class)
    @Test
    void displayNotificationAboutRequiredFieldsWindowChangingPasswordAdmin() {
        adminPage.scrollToCardAdmin(login);
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.clickButtonChangePassword(login)
                .verifyChangePasswordAdminWindow()
                .clickFieldNewPassword()
                .clickFieldConfirmPassword();
        assertEquals("Обязательное поле", changePasswordAdminWindow.getErrorFieldPassword());
        assertEquals("Обязательное поле", changePasswordAdminWindow.getErrorFieldConfirmPassword());
    }

    @Feature("Замена пароля админу")
    @Story("Ввод не соответствующего пароля при подтверждении")
    @DisplayName("Ввод не соответствующего пароля при подтверждении")
    @ExtendWith(AdminAddDelete.class)
    @Test
    void fillInvalidValuesConfirmPasswordChangingPasswordAdmin() {
        adminPage.scrollToCardAdmin(login);
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.clickButtonChangePassword(login)
                .verifyChangePasswordAdminWindow()
                .clickFieldNewPassword()
                .fillFieldNewPassword(generatePassword())
                .fillFieldConfirmPassword(generatePassword())
                .clickFieldNewPassword();
        assertFalse(changePasswordAdminWindow.isEnabledSaveButton());
        assertEquals("Не соответствует паролю", changePasswordAdminWindow.getErrorFieldConfirmPassword());
    }

    @Feature("Замена пароля админу")
    @Story("Ввод не валидных граничных значений пароля из 7 символов")
    @DisplayName("Ввод не валидных граничных значений пароля из 7 символов")
    @ExtendWith(AdminAddDelete.class)
    @Test
    void fillLimitInvalidValuesPasswordChainingPasswordAdmin_7_Symbol() {
        adminPage.scrollToCardAdmin(login);
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.clickButtonChangePassword(login)
                .verifyChangePasswordAdminWindow()
                .fillFieldNewPassword("Wwqq12#");
        assertEquals("Пароль не валиден", changePasswordAdminWindow.getErrorFieldPassword());
    }

    @Feature("Замена пароля админу")
    @Story("Ввод валидных граничных значений пароля из 8,9,24 и 25 символов")
    @DisplayName("Ввод валидных граничных значений пароля из 8,9,24 и 25 символов")
    @ExtendWith(AdminAddDelete.class)
    @ParameterizedTest
    @ValueSource(strings = {"Wwqq123#", "Wwqq1234#", "Wwqq123456789#QQgg123456", "Wwqq123456789#QQgg1234567"})
    void fillLimitValidValuesPasswordChainingPasswordAdmin_8_9_24_25_Symbol(String password) {
        adminPage.scrollToCardAdmin(login);
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.clickButtonChangePassword(TestData.DataTest.login)
                .verifyChangePasswordAdminWindow()
                .fillFieldNewPassword(password);
        assertFalse(changePasswordAdminWindow.isErrorPasswordAppear());
    }

    @Feature("Замена пароля админу")
    @Story("Ввод не валидных граничных значений пароля из 26 символов")
    @DisplayName("Ввод не валидных граничных значений пароля из 26 символов")
    @ExtendWith(AdminAddDelete.class)
    @Test
    void fillLimitInvalidValuesPasswordChainingPasswordAdmin_26_Symbol() {
        adminPage.scrollToCardAdmin(login);
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.clickButtonChangePassword(login)
                .verifyChangePasswordAdminWindow()
                .fillFieldNewPassword("Wwqq123456789#QQgg12345678");
        assertEquals("Пароль не валиден", changePasswordAdminWindow.getErrorFieldPassword());
    }

    @Feature("Замена пароля админу")
    @Story("Ввод не валидного пароля")
    @DisplayName("Ввод не валидного пароля")
    @ExtendWith(AdminAddDelete.class)
    @ParameterizedTest
    @ValueSource(strings = {"123456789!", "123456789Ss", "123456789!ss", "123456789!SS", "WwqqLLpp!!", "Wwqq 123456 #"})
    void fillInvalidValuesPasswordChainingPasswordAdmin(String password) {
        adminPage.scrollToCardAdmin(login);
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.clickButtonChangePassword(login)
                .verifyChangePasswordAdminWindow()
                .fillFieldNewPassword(password);
        assertEquals("Пароль не валиден", changePasswordAdminWindow.getErrorFieldPassword());
    }

    @Feature("Замена пароля админу")
    @Story("Очистка полей через кнопку в окне изменения пароля админу")
    @DisplayName("Очистка полей через кнопку в окне изменения пароля админу")
    @ExtendWith(AdminAddDelete.class)
    @Test
    void clearFieldsWindowChainingPasswordThroughButtonClear() {
        adminPage.scrollToCardAdmin(login);
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.clickButtonChangePassword(login)
                .verifyChangePasswordAdminWindow()
                .fillFieldNewPassword(generatePassword())
                .clickClearButtonNewPasswordField()
                .fillFieldConfirmPassword(password)
                .clickClearButtonConfirmPasswordField();
        assertEquals("Обязательное поле", changePasswordAdminWindow.getErrorFieldPassword());
        assertEquals("Обязательное поле", changePasswordAdminWindow.getErrorFieldConfirmPassword());
        assertEquals("", changePasswordAdminWindow.getValuePasswordField());
        assertEquals("", changePasswordAdminWindow.getValueConfirmPasswordField());
    }

    @Feature("Замена пароля админу")
    @Story("Сброс значений полей в окне смены пароля админу после закрытия окна")
    @DisplayName("Сброс значений полей в окне смены пароля админу после закрытия окна")
    @ExtendWith(AdminAddDelete.class)
    @Test
    void closeWindowChainingPasswordAdmin() {
        adminPage.scrollToCardAdmin(login);
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.clickButtonChangePassword(login);
        changePasswordAdminWindow.verifyChangePasswordAdminWindow()
                .fillFieldNewPassword(generatePassword())
                .fillFieldConfirmPassword(password)
                .closeWindowChangePasswordAdmin();
        assertFalse(changePasswordAdminWindow.isWindowAppear());
        adminPage.clickButtonChangePassword(login);
        assertEquals("", changePasswordAdminWindow.getValuePasswordField());
        assertEquals("", changePasswordAdminWindow.getValueConfirmPasswordField());
    }

    @Feature("Удаление админа")
    @Story("Отмена удаления админа")
    @DisplayName("Отмена удаления админа")
    @ExtendWith(AdminAddDelete.class)
    @Test
    void cancelDeleteAdmin() {
        adminPage.scrollToCardAdmin(login)
                .verifyAdminCard(login);
        DeleteAdminWindow deleteAdminWindow = adminPage.clickButtonDeleteAdmin(login);
        deleteAdminWindow.clickCancelButtonDeleteAdmin();
        assertEquals(1, DataBaseQuery.selectAdmin(login).getRole_id());
        assertFalse(DataBaseQuery.selectAdmin(login).getIs_blocked());
        assertFalse(DataBaseQuery.selectAdmin(login).getIs_deleted());
        assertTrue(adminPage.isVisibleAdminCard(login));
    }

    @Feature("Удаление админа")
    @Story("Успешное удаление админа")
    @DisplayName("Успешное удаление админа")
    @ExtendWith(AdminAdd.class)
    @Test
    void deleteAdmin() {
        adminPage.scrollToCardAdmin(login)
                .verifyAdminCard(login);
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
        updateLegalDocWindow.uploadDoc("src/test/resources/files/Оферта.pdf");
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
        updateLegalDocWindow.uploadDoc("src/test/resources/files/Политика обработки персональных данных.pdf");
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
        updateLegalDocWindow.verifyUploadDocWindow()
                .closeWindowUpdateLegalDoc();
        assertFalse(updateLegalDocWindow.isWindowAppear());
    }

    @Feature("Документация")
    @Story("Закрытие окна обновления политики обработки")
    @DisplayName("Закрытие окна обновления политики обработки")
    @Test
    void closeWindowUpdateProcessingPolicy() {
        UpdateLegalDocWindow updateLegalDocWindow = adminPage.clickButtonUpdateProcessingPolicy();
        updateLegalDocWindow.verifyUploadDocWindow()
                .closeWindowUpdateLegalDoc();
        assertFalse(updateLegalDocWindow.isWindowAppear());
    }

    @Feature("Документация")
    @Story("Успешное обновление приказа с даты в текущем месяце")
    @DisplayName("Успешное обновление приказа с даты в текущем месяце")
    @Test
    void updateOrderCurrentMonth() {
        UpdateOrderWindow updateOrderWindow = adminPage.clickButtonUpdateOrder();
        updateOrderWindow.verifyUpdateOrderWindow();
        Calendar calendar = updateOrderWindow.openCalendarUpdateOrder();
        calendar.verifyCalendar();
        assertEquals(getDate("current",0,0,"LLLL yyyy"), calendar.getNameCurrentMonth());
        calendar.clickDateActivation(getDay("current",null,0,2));
        assertFalse(calendar.isCalendarAppear());
        updateOrderWindow.uploadOrder("src/test/resources/files/Приказ.xlsx");
        assertEquals("Федеральный приказ успешно обновлен", adminPage.getTextNotification());
        assertEquals("FEDERAL_SERVICES_UPDATED_SUCCESS", DataBaseQuery.selectLog(LOGIN_SUPER_ADMIN).getCode());
    }

    @Feature("Документация")
    @Story("Успешное обновление приказа с даты в следующем месяце")
    @DisplayName("Успешное обновление приказа с даты в следующем месяце")
    @Test
    void updateOrderFutureMonth() {
        UpdateOrderWindow updateOrderWindow = adminPage.clickButtonUpdateOrder();
        updateOrderWindow.verifyUpdateOrderWindow();
        Calendar calendar = updateOrderWindow.openCalendarUpdateOrder();
        calendar.verifyCalendar()
                .switchFutureMonth();
        assertEquals(getDate("future",1,0,"LLLL yyyy"), calendar.getNameCurrentMonth());
        calendar.clickDateActivation(getDay("future",null,1,2));
        assertFalse(calendar.isCalendarAppear());
        updateOrderWindow.uploadOrder("src/test/resources/files/Приказ.xlsx");
        assertEquals("Федеральный приказ успешно обновлен", adminPage.getTextNotification());
        assertEquals("FEDERAL_SERVICES_UPDATED_SUCCESS", DataBaseQuery.selectLog(LOGIN_SUPER_ADMIN).getCode());
    }

    @Feature("Документация")
    @Story("Успешное обновление приказа с даты в предыдущем месяце")
    @DisplayName("Успешное обновление приказа с даты в предыдущем месяце")
    @Test
    void updateOrderPreviousMonth() {
        UpdateOrderWindow updateOrderWindow = adminPage.clickButtonUpdateOrder();
        updateOrderWindow.verifyUpdateOrderWindow();
        Calendar calendar = updateOrderWindow.openCalendarUpdateOrder();
        calendar.verifyCalendar()
                .switchPreviousMonth();
        assertEquals(getDate("previous",1,0,"LLLL yyyy"), calendar.getNameCurrentMonth());
        calendar.clickDateActivation(getDay("previous",null,1,2));
        assertFalse(calendar.isCalendarAppear());
        updateOrderWindow.uploadOrder("src/test/resources/files/Приказ.xlsx");
        assertEquals("Федеральный приказ успешно обновлен", adminPage.getTextNotification());
        assertEquals("FEDERAL_SERVICES_UPDATED_SUCCESS", DataBaseQuery.selectLog(LOGIN_SUPER_ADMIN).getCode());
    }

    @Feature("Документация")
    @Story("Успешное обновление приказа с текущей даты")
    @DisplayName("Успешное обновление приказа с текущей даты")
    @Test
    void updateOrderTodayNotUseCalendar() {
        UpdateOrderWindow updateOrderWindow = adminPage.clickButtonUpdateOrder();
        updateOrderWindow.verifyUpdateOrderWindow();
        assertEquals(getDate("current",0,0,"dd.MM.yyyy"), updateOrderWindow.getValuesButtonToday());
        updateOrderWindow.uploadOrder("src/test/resources/files/Приказ.xlsx");
        assertEquals("Федеральный приказ успешно обновлен", adminPage.getTextNotification());
        assertEquals("FEDERAL_SERVICES_UPDATED_SUCCESS", DataBaseQuery.selectLog(LOGIN_SUPER_ADMIN).getCode());
    }

    @Feature("Документация")
    @Story("Закрытие окна обновления приказа")
    @DisplayName("Закрытие окна обновления приказа")
    @Test
    void closeWindowUpdateOrder() {
        UpdateOrderWindow updateOrderWindow = adminPage.clickButtonUpdateOrder();
        updateOrderWindow.verifyUpdateOrderWindow()
                .closeWindowUpdateOrder();
        assertFalse(updateOrderWindow.isWindowAppear());
    }

    @Feature("Документация")
    @Story("Успешное обновление прайса с даты в текущем месяце")
    @DisplayName("Успешное обновление прайса с даты в текущем месяце")
    @Test
    void clickButtonUpdatePriceCurrentMonth() {
        UpdatePriceWindow updatePriceWindow = adminPage.clickButtonUpdatePrice();
        updatePriceWindow.verifyUpdatePriceWindow();
        Calendar calendar = updatePriceWindow.openCalendarUpdatePrice();
        calendar.verifyCalendar();
        assertEquals(getDate("current",0,0,"LLLL yyyy"), calendar.getNameCurrentMonth());
        calendar.clickDateActivation(getDay("current",null,0,2));
        assertFalse(calendar.isCalendarAppear());
        updatePriceWindow.uploadPrice("src/test/resources/files/Прайс.xlsx");
        assertEquals("Прайс успешно обновлен", adminPage.getTextNotification());
        assertEquals("PRICE_UPDATED_SUCCESS", DataBaseQuery.selectLog(LOGIN_SUPER_ADMIN).getCode());
    }

    @Feature("Документация")
    @Story("Успешное обновление прайса с даты в следующем месяце")
    @DisplayName("Успешное обновление прайса с даты в следующем месяце")
    @Test
    void clickButtonUpdatePriceFutureMonth() {
        UpdatePriceWindow updatePriceWindow = adminPage.clickButtonUpdatePrice();
        updatePriceWindow.verifyUpdatePriceWindow();
        Calendar calendar = updatePriceWindow.openCalendarUpdatePrice();
        calendar.verifyCalendar()
                .switchFutureMonth();
        assertEquals(getDate("future",1,0,"LLLL yyyy"), calendar.getNameCurrentMonth());
        calendar.clickDateActivation(getDay("future",null,1,2));
        assertFalse(calendar.isCalendarAppear());
        updatePriceWindow.uploadPrice("src/test/resources/files/Прайс.xlsx");
        assertEquals("Прайс успешно обновлен", adminPage.getTextNotification());
        assertEquals("PRICE_UPDATED_SUCCESS", DataBaseQuery.selectLog(LOGIN_SUPER_ADMIN).getCode());
    }

    @Feature("Документация")
    @Story("Успешное обновление прайса с даты в прошлом месяце")
    @DisplayName("Успешное обновление прайса с даты в прошлом месяце")
    @Test
    void clickButtonUpdatePricePreviousMonth() {
        UpdatePriceWindow updatePriceWindow = adminPage.clickButtonUpdatePrice();
        updatePriceWindow.verifyUpdatePriceWindow();
        Calendar calendar = updatePriceWindow.openCalendarUpdatePrice();
        calendar.verifyCalendar()
                .switchPreviousMonth();
        assertEquals(getDate("previous",1,0,"LLLL yyyy"), calendar.getNameCurrentMonth());
        calendar.clickDateActivation(getDay("previous",null,1,2));
        assertFalse(calendar.isCalendarAppear());
        updatePriceWindow.uploadPrice("src/test/resources/files/Прайс.xlsx");
        assertEquals("Прайс успешно обновлен", adminPage.getTextNotification());
        assertEquals("PRICE_UPDATED_SUCCESS", DataBaseQuery.selectLog(LOGIN_SUPER_ADMIN).getCode());
    }

    @Feature("Документация")
    @Story("Успешное обновление прайса с текущей даты")
    @DisplayName("Успешное обновление прайса с текущей даты")
    @Test
    void clickButtonUpdatePriceTodayNotUseCalendar() {
        UpdatePriceWindow updatePriceWindow = adminPage.clickButtonUpdatePrice();
        updatePriceWindow.verifyUpdatePriceWindow();
        assertEquals(getDate("current",0,0,"dd.MM.yyyy"), updatePriceWindow.getValuesButtonToday());
        updatePriceWindow.uploadPrice("src/test/resources/files/Прайс.xlsx");
        assertEquals("Прайс успешно обновлен", adminPage.getTextNotification());
        assertEquals("PRICE_UPDATED_SUCCESS", DataBaseQuery.selectLog(LOGIN_SUPER_ADMIN).getCode());
    }

    @Feature("Документация")
    @Story("Закрытие окна обновления прайса")
    @DisplayName("Закрытие окна обновления прайса")
    @Test
    void closeWindowClickButtonUpdatePrice() {
        UpdatePriceWindow updatePriceWindow = adminPage.clickButtonUpdatePrice();
        updatePriceWindow.verifyUpdatePriceWindow()
                .closeWindowUpdatePrice();
        assertFalse(updatePriceWindow.isWindowAppear());
    }

    @Feature("Документация")
    @Story("Обновление оферты с не валидным файлом")
    @DisplayName("Обновление оферты с не валидным файлом")
    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/files/Оферта,Политика обработки docx.docx", "src/test/resources/files/Оферта, Политика обработки .xlsx.xlsx", "src/test/resources/files/Оферта, Политика обработки jpeg.jpg",})
    void updateOfferInvalidFormat(String path) {
        UpdateLegalDocWindow updateLegalDocWindow = adminPage.clickButtonUpdateOffer();
        updateLegalDocWindow.verifyUploadDocWindow()
                .uploadDoc(path);
        assertEquals("Допускаются файлы с расширением PDF", adminPage.getTextNotification());
    }

    @Feature("Документация")
    @Story("Обновление политики обработки с не валидным файлом")
    @DisplayName("Обновление политики обработки с не валидным файлом")
    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/files/Оферта,Политика обработки docx.docx", "src/test/resources/files/Оферта, Политика обработки .xlsx.xlsx", "src/test/resources/files/Оферта, Политика обработки jpeg.jpg"})
    void updateProcessingPolicyInvalidFormat(String path) {
        UpdateLegalDocWindow updateLegalDocWindow = adminPage.clickButtonUpdateProcessingPolicy();
        updateLegalDocWindow.verifyUploadDocWindow()
                .uploadDoc(path);
        assertEquals("Допускаются файлы с расширением PDF", adminPage.getTextNotification());
    }

    @Feature("Документация")
    @Story("Обновление приказа с ошибкой в строке")
    @DisplayName("Обновление приказа с ошибкой в строке")
    @Test
    void updateOrderWithStringError() {
        UpdateOrderWindow updateOrderWindow = adminPage.clickButtonUpdateOrder();
        updateOrderWindow.verifyUpdateOrderWindow()
                .uploadOrder("src/test/resources/files/Приказ с ошибкой в строке 10858.xlsx");
        assertEquals("Ошибка в 10858 строке", adminPage.getTextNotification());
    }

    @Feature("Документация")
    @Story("Обновление приказа с не валидным файлом")
    @DisplayName("Обновление приказа с не валидным файлом")
    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/files/Оферта,Политика обработки docx.docx", "src/test/resources/files/Оферта, Политика обработки jpeg.jpg", "src/test/resources/files/Оферта.pdf"})
    void updateOrderInvalidFormat(String path) {
        UpdateOrderWindow updateOrderWindow = adminPage.clickButtonUpdateOrder();
        updateOrderWindow.verifyUpdateOrderWindow()
                .uploadOrder(path);
        assertEquals("Допускаются файлы с расширением xlsx", adminPage.getTextNotification());
    }

    @Feature("Документация")
    @Story("Обновление прайса с ошибкой в строке")
    @DisplayName("Обновление прайса с ошибкой в строке")
    @Test
    void clickButtonUpdatePriceWithStringError() {
        UpdatePriceWindow updatePriceWindow = adminPage.clickButtonUpdatePrice();
        updatePriceWindow.verifyUpdatePriceWindow()
                .uploadPrice("src/test/resources/files/Прайс с ошибкой в строке 1398.xlsx");
        assertEquals("Ошибка в 1398 строке", adminPage.getTextNotification());
    }

    @Feature("Документация")
    @Story("Обновление прайса с ошибкой формата стоимости услуги")
    @DisplayName("Обновление прайса с ошибкой формата стоимости услуги")
    @Test
    void clickButtonUpdatePriceFormatPriceServiceError() {
        UpdatePriceWindow updatePriceWindow = adminPage.clickButtonUpdatePrice();
        updatePriceWindow.verifyUpdatePriceWindow()
                .uploadPrice("src/test/resources/files/Прайс с ошибкой формата в услуге А26.30.004.002.xlsx");
        PriceErrorsWindow priceErrorsWindow = updatePriceWindow.priceErrorsWindow();
        priceErrorsWindow.verifyPriceErrorsWindow();
        assertEquals("Ошибка формата стоимости услуги, код: 'А26.30.004.002', стоимость: 'Ы'.", priceErrorsWindow.getErrorInfo());
    }

    @Feature("Документация")
    @Story("Переключение на вкладку правил корректирования файла прайса")
    @DisplayName("Переключение на вкладку правил корректирования файла прайса")
    @Test
    void openAdjustmentRulesPrice() {
        UpdatePriceWindow updatePriceWindow = adminPage.clickButtonUpdatePrice();
        updatePriceWindow.verifyUpdatePriceWindow()
                .uploadPrice("src/test/resources/files/Прайс с ошибкой формата в услуге А26.30.004.002.xlsx");
        PriceErrorsWindow priceErrorsWindow = updatePriceWindow.priceErrorsWindow();
        priceErrorsWindow.verifyPriceErrorsWindow()
                .clickAdjustmentRulesTab();
        assertTrue(priceErrorsWindow.isAdjustmentRulesAppear());
    }

    @Feature("Документация")
    @Story("Переключение на вкладку ошибок файла прайса")
    @DisplayName("Переключение на вкладку ошибок файла прайса")
    @Test
    void openErrorsPrice() {
        UpdatePriceWindow updatePriceWindow = adminPage.clickButtonUpdatePrice();
        updatePriceWindow.verifyUpdatePriceWindow()
                .uploadPrice("src/test/resources/files/Прайс с ошибкой формата в услуге А26.30.004.002.xlsx");
        PriceErrorsWindow priceErrorsWindow = updatePriceWindow.priceErrorsWindow();
        priceErrorsWindow.verifyPriceErrorsWindow()
                .clickAdjustmentRulesTab()
                .clickErrorPriceTab();
        assertTrue(priceErrorsWindow.isErrorInfoAppear());
    }

    @Feature("Документация")
    @Story("Закрытие окна ошибок файла прайса")
    @DisplayName("Закрытие окна ошибок файла прайса")
    @Test
    void closeWindowErrorsPrice() {
        UpdatePriceWindow updatePriceWindow = adminPage.clickButtonUpdatePrice();
        updatePriceWindow.verifyUpdatePriceWindow()
                .uploadPrice("src/test/resources/files/Прайс с ошибкой формата в услуге А26.30.004.002.xlsx");
        PriceErrorsWindow priceErrorsWindow = updatePriceWindow.priceErrorsWindow();
        priceErrorsWindow.verifyPriceErrorsWindow()
                .closeWindowPriceErrors();
        assertFalse(priceErrorsWindow.isWindowAppear());
    }

    @Feature("Документация")
    @Story("Обновление прайса с не валидным файлом")
    @DisplayName("Обновление прайса с не валидным файлом")
    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/files/Оферта,Политика обработки docx.docx", "src/test/resources/files/Оферта, Политика обработки jpeg.jpg", "src/test/resources/files/Оферта.pdf"})
    void clickButtonUpdatePriceInvalidFormat(String path) {
        UpdatePriceWindow updatePriceWindow = adminPage.clickButtonUpdatePrice();
        updatePriceWindow.verifyUpdatePriceWindow()
                .uploadPrice(path);
        assertEquals("Допускаются файлы с расширением xlsx", adminPage.getTextNotification());

    }
}


