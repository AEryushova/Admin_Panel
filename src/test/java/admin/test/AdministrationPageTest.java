package admin.test;

import admin.data.DataConfig;
import admin.pages.AdministrationPage.*;
import admin.pages.BasePage.BasePage;
import admin.pages.HeaderMenu.HeaderMenu;
import admin.pages.Сalendar.Calendar;
import admin.utils.dbUtils.DataBaseQuery;
import admin.utils.preparationDataTests.administration.AdminAddDecorator;
import admin.utils.preparationDataTests.administration.AdminAddDeleteDecorator;
import admin.utils.preparationDataTests.administration.AdminDeleteDecorator;
import admin.utils.preparationDataTests.general.AllureDecorator;
import admin.utils.preparationDataTests.general.NotificationDecorator;
import admin.utils.testUtils.*;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Администрирование")
public class AdministrationPageTest extends BaseTest {

    private AdministrationPage adminPage;
    private HeaderMenu headerMenu;
    private BasePage basePage;

    @ExtendWith(AllureDecorator.class)

    @BeforeAll
    static void setUpAuth() {
        BrowserManager.authGetCookie(DataConfig.UserData.getLoginSuperAdmin(), DataConfig.UserData.getPasswordSuperAdmin());
    }

    @BeforeEach
    void setUp() {
        BrowserManager.openPagesAfterAuth();
        adminPage = new AdministrationPage();
        headerMenu = new HeaderMenu();
        basePage = new BasePage();
        headerMenu.administrationTabOpen();
    }

    @Feature("Добавление нового админа")
    @Story("Успешное добавление нового админа")
    @ExtendWith({AdminDeleteDecorator.class, NotificationDecorator.class})
    @Test
    void addedNewAdmin() {
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.newAdminWindow();
        newAdminWindow.fillFieldNewAdminLogin(DataConfig.DataTest.getLoginAdminTest());
        newAdminWindow.fillFieldNewAdminPassword(DataConfig.DataTest.getPasswordAdminTest());
        newAdminWindow.fillFieldNewAdminConfirmPassword(DataConfig.DataTest.getPasswordAdminTest());
        newAdminWindow.clickAddButton();
        assertEquals("Новый администратор " + DataConfig.DataTest.getLoginAdminTest() + " успешно создан", adminPage.getNotification());
        assertEquals(1, DataBaseQuery.selectAdmin(DataConfig.DataTest.getLoginAdminTest()).getRole_id());
        assertFalse(DataBaseQuery.selectAdmin(DataConfig.DataTest.getLoginAdminTest()).getIs_blocked());
        assertFalse(DataBaseQuery.selectAdmin(DataConfig.DataTest.getLoginAdminTest()).getIs_deleted());
        assertTrue(adminPage.isExistAdminCard());
        assertFalse(newAdminWindow.isWindowAppear());
    }


    @Feature("Добавление нового админа")
    @Story("Добавление нового админа с уже существующим логином")
    @ExtendWith({AdminAddDeleteDecorator.class, NotificationDecorator.class})
    @Test
    void addedNewAdminAlreadyExisting() {
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.fillFieldNewAdminLogin(DataConfig.DataTest.getLoginAdminTest());
        newAdminWindow.fillFieldNewAdminPassword(DataConfig.DataTest.getPasswordAdminTest());
        newAdminWindow.fillFieldNewAdminConfirmPassword(DataConfig.DataTest.getPasswordAdminTest());
        newAdminWindow.clickAddButton();
        assertEquals("{\"error\":\"Пользователь уже существует, логин: " + DataConfig.DataTest.getLoginAdminTest() + "\",\"innerError\":null,\"exception\":\"AlreadyExistException\"}", adminPage.getNotification());
        assertTrue(adminPage.isExistAdminCard());
    }

    @Feature("Добавление нового админа")
    @Story("Добавление нового администратора с пустым полем логина")
    @Test
    void addedNewAdminEmptyFieldLogin() {
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.fillFieldNewAdminPassword(DataConfig.DataTest.getPasswordAdminTest());
        newAdminWindow.fillFieldNewAdminConfirmPassword(DataConfig.DataTest.getPasswordAdminTest());
        assertFalse(newAdminWindow.isEnabledAddButton());
    }

    @Feature("Добавление нового админа")
    @Story("Добавление нового администратора с пустым полем пароля")
    @Test
    void addedNewAdminEmptyFieldPassword() {
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.fillFieldNewAdminLogin(DataConfig.DataTest.getLoginAdminTest());
        newAdminWindow.fillFieldNewAdminConfirmPassword(DataConfig.DataTest.getPasswordAdminTest());
        assertFalse(newAdminWindow.isEnabledAddButton());
        assertEquals("Не соответствует паролю", newAdminWindow.getErrorFieldConfirmPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Добавление нового администратора с пустым полем подтверждения пароля")
    @Test
    void addedNewAdminEmptyFieldConfirmPassword() {
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.fillFieldNewAdminLogin(DataConfig.DataTest.getLoginAdminTest());
        newAdminWindow.fillFieldNewAdminPassword(DataConfig.DataTest.getPasswordAdminTest());
        assertFalse(newAdminWindow.isEnabledAddButton());
    }


    @Feature("Добавление нового админа")
    @Story("Добавление нового администратора с пустым полем логина и пароля")
    @Test
    void addedNewAdminEmptyFieldsLoginPassword() {
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.fillFieldNewAdminConfirmPassword(DataConfig.DataTest.getPasswordAdminTest());
        assertFalse(newAdminWindow.isEnabledAddButton());
        assertEquals("Не соответствует паролю", newAdminWindow.getErrorFieldConfirmPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Добавление нового администратора с пустым полем логина и подтверждения пароля")
    @Test
    void addedNewAdminEmptyFieldsLoginConfirmPassword() {
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.fillFieldNewAdminPassword(DataConfig.DataTest.getPasswordAdminTest());
        assertFalse(newAdminWindow.isEnabledAddButton());
    }

    @Feature("Добавление нового админа")
    @Story("Добавление нового администратора с пустым полем пароля и подтверждения пароля")
    @Test
    void addedNewAdminEmptyFieldsPasswordConfirmPassword() {
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.fillFieldNewAdminLogin(DataConfig.DataTest.getLoginAdminTest());
        assertFalse(newAdminWindow.isEnabledAddButton());
    }

    @Feature("Добавление нового админа")
    @Story("Отображение уведомления об обязательности полей")
    @Test
    void addedNewAdminObligatoryFields() {
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.clickFieldNewAdminLogin();
        newAdminWindow.clickFieldNewAdminPassword();
        newAdminWindow.clickFieldNewAdminConfirmPassword();
        assertFalse(newAdminWindow.isEnabledAddButton());
        assertEquals("Обязательное поле", newAdminWindow.getErrorFieldLogin());
        assertEquals("Обязательное поле", newAdminWindow.getErrorFieldPassword());
        assertEquals("Обязательное поле", newAdminWindow.getErrorFieldConfirmPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод валидного логина из 31 и 32 символов")
    @ParameterizedTest
    @ValueSource(strings = {"ANNA_TEST_ADMIN123456789_ANNA_1", "ANNA_TEST_ADMIN123456789_ANNA_12"})
    void addedNewAdminLogin_31_32_Symbol(String login) {
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.fillFieldNewAdminLogin(login);
        assertFalse(newAdminWindow.isErrorLoginAppear());
    }


    @Feature("Добавление нового админа")
    @Story("Ввод не валидного логина из 33 символов")
    @Test
    void addedNewAdminLogin_33_Symbol() {
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.fillFieldNewAdminLogin("ANNA_TEST_ADMIN123456789_ANNA_123");
        assertEquals("Максимальная длина 32 символа", newAdminWindow.getErrorFieldLogin());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не валидного логина на кириллице и логина, начинающегося с цифры")
    @ParameterizedTest
    @ValueSource(strings = {"АННА_ТЕСТ", "1ANNA_TEST"})
    void addedNewAdminLoginCyrillicValueBeginNumber(String login) {
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.fillFieldNewAdminLogin(login);
        assertEquals("Первый символ должен быть латинской буквой или \"_\"", newAdminWindow.getErrorFieldLogin());
    }


    @Feature("Добавление нового админа")
    @Story("Ввод не валидного логина, начинающегося с латиницы, далее на кириллице и логина с пробелом")
    @ParameterizedTest
    @ValueSource(strings = {"AННА_ТЕСТ", "ANNA TEST"})
    void addedNewAdminLoginLatinBeginCyrillicValueWithSpace(String login) {
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.fillFieldNewAdminLogin(login);
        assertEquals("Доступны только числа, латиница и \"_\"", newAdminWindow.getErrorFieldLogin());
    }


    @Feature("Добавление нового админа")
    @Story("Ввод не валидного пароля из 7 и 26 символов")
    @ParameterizedTest
    @ValueSource(strings = {"Wwqq12#", "Wwqq123456789#QQgg12345678"})
    void addedNewAdminPassword_7_26_Symbol(String password) {
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.fillFieldNewAdminPassword(password);
        assertEquals("Пароль не валиден", newAdminWindow.getErrorFieldPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод валидного пароля из 8,9,24 и 25 символов")
    @ParameterizedTest
    @ValueSource(strings = {"Wwqq123#", "Wwqq1234#", "Wwqq123456789#QQgg123456", "Wwqq123456789#QQgg1234567"})
    void addedNewAdminPassword_8_9_24_25_Symbol(String password) {
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.fillFieldNewAdminPassword(password);
        assertFalse(newAdminWindow.isErrorPasswordAppear());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не валидного пароля без латинской буквы, без спецсимвола, без латинской буквы в верхнем регистре,без латинской буквы в нижнем регистре, без цифр, с пробелом ")
    @ParameterizedTest
    @ValueSource(strings = {"123456789!", "123456789Ss", "123456789!ss", "123456789!SS", "WwqqLLpp!!", "Wwqq 123456 #"})
    void addedNewAdminPasswordInvalidValue(String password) {
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.fillFieldNewAdminPassword(password);
        assertEquals("Пароль не валиден", newAdminWindow.getErrorFieldPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Ввод не соответствующего пароля при подтверждении")
    @Test
    void addedNewAdminMismatchedPasswords() {
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.fillFieldNewAdminLogin(DataConfig.DataTest.getLoginAdminTest());
        newAdminWindow.fillFieldNewAdminPassword(DataConfig.DataTest.getPasswordAdminTest());
        newAdminWindow.fillFieldNewAdminConfirmPassword(DataConfig.UserData.getPasswordSuperAdmin());
        assertEquals("Не соответствует паролю", newAdminWindow.getErrorFieldConfirmPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Очистка поля логина через кнопку в окне добавления админа")
    @Test
    void clearFieldLoginThroughButtonClear() {
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.fillFieldNewAdminLogin(DataConfig.DataTest.getLoginAdminTest());
        newAdminWindow.clearButtonLoginField();
        assertEquals("", newAdminWindow.getValueLoginField());
        assertEquals("Обязательное поле", newAdminWindow.getErrorFieldLogin());
    }

    @Feature("Добавление нового админа")
    @Story("Очистка поля пароля через кнопку в окне добавления админа")
    @Test
    void clearFieldPasswordThroughButtonClear() {
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.fillFieldNewAdminPassword(DataConfig.DataTest.getPasswordAdminTest());
        newAdminWindow.clickClearButtonPasswordField();
        assertEquals("", newAdminWindow.getValuePasswordField());
        assertEquals("Обязательное поле", newAdminWindow.getErrorFieldPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Очистка поля подтверждения пароля через кнопку в окне добавления админа")
    @Test
    void clearFieldConfirmPasswordThroughButtonClear() {
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.fillFieldNewAdminConfirmPassword(DataConfig.DataTest.getPasswordAdminTest());
        newAdminWindow.clickClearButtonConfirmPasswordField();
        assertEquals("", newAdminWindow.getValueConfirmPasswordField());
        assertEquals("Обязательное поле", newAdminWindow.getErrorFieldConfirmPassword());
    }

    @Feature("Добавление нового админа")
    @Story("Зануление полей в окне добавления админа и закрытие окна")
    @Test
    void closeWindowAddedNewAdmin() {
        NewAdminWindow newAdminWindow = adminPage.openWindowAddedNewAdmin();
        newAdminWindow.fillFieldNewAdminLogin(DataConfig.DataTest.getLoginAdminTest());
        newAdminWindow.fillFieldNewAdminPassword(DataConfig.DataTest.getPasswordAdminTest());
        newAdminWindow.fillFieldNewAdminConfirmPassword(DataConfig.DataTest.getPasswordAdminTest());
        newAdminWindow.closeWindowAddedAdmin();
        assertFalse(newAdminWindow.isWindowAppear());
        adminPage.openWindowAddedNewAdmin();
        assertEquals("", newAdminWindow.getValueLoginField());
        assertEquals("", newAdminWindow.getValuePasswordField());
        assertEquals("", newAdminWindow.getValueConfirmPasswordField());
    }


    @Feature("Смена пароля админу")
    @Story("Успешная смена пароля админу")
    @ExtendWith({AdminAddDeleteDecorator.class, NotificationDecorator.class})
    @Test
    void changePasswordAdmin() {
        adminPage.adminCard();
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.changePasswordAdminWindow();
        changePasswordAdminWindow.fillFieldNewPassword(DataConfig.DataTest.getNewPasswordAdminTest());
        changePasswordAdminWindow.fillFieldConfirmPassword(DataConfig.DataTest.getNewPasswordAdminTest());
        changePasswordAdminWindow.clickSaveNewPasswordButton();
        assertEquals("Админ " + DataConfig.DataTest.getLoginAdminTest() + " успешно изменен", adminPage.getNotification());
        assertFalse(changePasswordAdminWindow.isWindowAppear());
    }

    @Feature("Смена пароля админу")
    @Story("Смена пароля админу с пустым полем пароля")
    @ExtendWith(AdminAddDeleteDecorator.class)
    @Test
    void changePasswordAdminEmptyFieldsPassword() {
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.fillFieldConfirmPassword(DataConfig.DataTest.getNewPasswordAdminTest());
        assertFalse(changePasswordAdminWindow.isEnabledSaveButton());
        assertEquals("Не соответствует паролю", changePasswordAdminWindow.getErrorFieldConfirmPassword());
    }

    @Feature("Смена пароля админу")
    @Story("Смена пароля админу с пустым полем подтверждения пароля")
    @ExtendWith(AdminAddDeleteDecorator.class)
    @Test
    void changePasswordAdminEmptyFieldsConfirmPassword() {
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.fillFieldNewPassword(DataConfig.DataTest.getNewPasswordAdminTest());
        assertFalse(changePasswordAdminWindow.isEnabledSaveButton());
    }

    @Feature("Смена пароля админу")
    @Story("Отображение уведомления об обязательности полей")
    @ExtendWith(AdminAddDeleteDecorator.class)
    @Test
    void changePasswordAdminObligatoryFields() {
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.clickFieldNewPassword();
        changePasswordAdminWindow.clickFieldConfirmPassword();
        assertEquals("Обязательное поле", changePasswordAdminWindow.getErrorFieldPassword());
        assertEquals("Обязательное поле", changePasswordAdminWindow.getErrorFieldConfirmPassword());
    }

    @Feature("Смена пароля админу")
    @Story("Ввод не соответствующего пароля при подтверждении")
    @ExtendWith(AdminAddDeleteDecorator.class)
    @Test
    void changePasswordAdminNotEqualsPasswords() {
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.clickFieldNewPassword();
        changePasswordAdminWindow.fillFieldNewPassword(DataConfig.DataTest.getNewPasswordAdminTest());
        changePasswordAdminWindow.fillFieldConfirmPassword(DataConfig.DataTest.getPasswordAdminTest());
        changePasswordAdminWindow.clickFieldNewPassword();
        assertFalse(changePasswordAdminWindow.isEnabledSaveButton());
        assertEquals("Не соответствует паролю", changePasswordAdminWindow.getErrorFieldConfirmPassword());
    }

    @Feature("Смена пароля админу")
    @Story("Ввод не валидного пароля из 7 символов")
    @ExtendWith(AdminAddDeleteDecorator.class)
    @Test
    void changePasswordAdmin_7_Symbol() {
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.fillFieldNewPassword("Wwqq12#");
        assertEquals("Пароль не валиден", changePasswordAdminWindow.getErrorFieldPassword());
    }

    @Feature("Смена пароля админу")
    @Story("Ввод валидного пароля из 8,9,24 и 25 символов")
    @ExtendWith(AdminAddDeleteDecorator.class)
    @ParameterizedTest
    @ValueSource(strings = {"Wwqq123#", "Wwqq1234#", "Wwqq123456789#QQgg123456", "Wwqq123456789#QQgg1234567"})
    void changePasswordAdmin_8_Symbol(String login) {
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.fillFieldNewPassword(login);
        assertFalse(changePasswordAdminWindow.isErrorPasswordAppear());
    }


    @Feature("Смена пароля админу")
    @Story("Ввод не валидного пароля из 26 символов")
    @ExtendWith(AdminAddDeleteDecorator.class)
    @Test
    void changePasswordAdmin_26_Symbol() {
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.fillFieldNewPassword("Wwqq123456789#QQgg12345678");
        assertEquals("Пароль не валиден", changePasswordAdminWindow.getErrorFieldPassword());
    }

    @Feature("Смена пароля админу")
    @Story("Ввод не валидного пароля без латинской буквы, без спецсимвола, без латинской буквы в верхнем регистре,без латинской буквы в нижнем регистре, без цифр, с пробелом ")
    @ExtendWith(AdminAddDeleteDecorator.class)
    @ParameterizedTest
    @ValueSource(strings = {"123456789!", "123456789Ss", "123456789!ss", "123456789!SS", "WwqqLLpp!!", "Wwqq 123456 #"})
    void changePasswordAdminNotToUpperCase() {
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.fillFieldNewPassword("wwqq123456#");
        assertEquals("Пароль не валиден", changePasswordAdminWindow.getErrorFieldPassword());
    }

    @Feature("Смена пароля админу")
    @Story("Зануление полей в окне смены пароля админу и закрытие окна")
    @ExtendWith(AdminAddDeleteDecorator.class)
    @Test
    void closeWindowChangePasswordAdmin() {
        ChangePasswordAdminWindow changePasswordAdminWindow = adminPage.openWindowChangedPasswordAdmin();
        changePasswordAdminWindow.fillFieldNewPassword(DataConfig.DataTest.getNewPasswordAdminTest());
        changePasswordAdminWindow.fillFieldConfirmPassword(DataConfig.DataTest.getNewPasswordAdminTest());
        changePasswordAdminWindow.closeWindowChangePasswordAdmin();
        assertFalse(changePasswordAdminWindow.isWindowAppear());
        adminPage.openWindowChangedPasswordAdmin();
        assertEquals("", changePasswordAdminWindow.getValuePasswordField());
        assertEquals("", changePasswordAdminWindow.getValueConfirmPasswordField());
    }

    @Feature("Удаление админа")
    @Story("Отмена удаления админа")
    @ExtendWith(AdminAddDeleteDecorator.class)
    @Test
    void cancelDeleteAdmin() {
        adminPage.adminCard();
        DeleteAdminWindow deleteAdminWindow = adminPage.openWindowDeleteAdmin();
        deleteAdminWindow.cancelDeleteAdmin();
        assertTrue(adminPage.isExistAdminCard());
    }

    @Feature("Удаление админа")
    @Story("Успешное удаление админа")
    @ExtendWith({AdminAddDecorator.class, NotificationDecorator.class})
    @Test
    void deleteAdmin() {
        adminPage.adminCard();
        DeleteAdminWindow deleteAdminWindow = adminPage.openWindowDeleteAdmin();
        deleteAdminWindow.deleteAdminWindow();
        assertTrue(deleteAdminWindow.verifyLoginAdmin(DataConfig.DataTest.getLoginAdminTest()));
        deleteAdminWindow.deleteAdmin();
        assertEquals("Админ " + DataConfig.DataTest.getLoginAdminTest() + " успешно удален", adminPage.getNotification());
        assertFalse(adminPage.isExistAdminCard());
        assertNull(DataBaseQuery.selectAdmin(DataConfig.DataTest.getLoginAdminTest()));
    }

    @Feature("Документация")
    @Story("Успешное обновление оферты")
    @Test
    void updateOffer() {
        UpdateLegalDocWindow updateLegalDocWindow = adminPage.updateOffer();
        updateLegalDocWindow.uploadDocWindow();
        String srcDoc = updateLegalDocWindow.getSrcDoc();
        updateLegalDocWindow.uploadValidDoc("src/test/resources/Оферта.pdf");
        adminPage.updateOffer();
        assertEquals(srcDoc, updateLegalDocWindow.getSrcDoc());
    }

    @Feature("Документация")
    @Story("Успешное обновление политики обработки")
    @Test
    void updateProcessingPolicy() {
        UpdateLegalDocWindow updateLegalDocWindow = adminPage.updateProcessingPolicy();
        updateLegalDocWindow.uploadDocWindow();
        String srcDoc = updateLegalDocWindow.getSrcDoc();
        updateLegalDocWindow.uploadValidDoc("src/test/resources/Политика обработки персональных данных.pdf");
        adminPage.updateProcessingPolicy();
        assertEquals(srcDoc, updateLegalDocWindow.getSrcDoc());
    }

    @Feature("Документация")
    @Story("Закрытие окна обновления оферты")
    @Test
    void closeWindowUpdateOffer() {
        UpdateLegalDocWindow updateLegalDocWindow = adminPage.updateOffer();
        updateLegalDocWindow.closeWindowUpdateLegalDoc();
        assertFalse(updateLegalDocWindow.isWindowAppear());
    }

    @Feature("Документация")
    @Story("Закрытие окна обновления политики обработки")
    @Test
    void closeWindowUpdateProcessingPolicy() {
        UpdateLegalDocWindow updateLegalDocWindow = adminPage.updateProcessingPolicy();
        updateLegalDocWindow.closeWindowUpdateLegalDoc();
        assertFalse(updateLegalDocWindow.isWindowAppear());
    }


    @Feature("Документация")
    @Story("Успешное обновление приказа с даты в текущем месяце")
    @ExtendWith(NotificationDecorator.class)
    @Test
    void updateOrderCurrentMonth() {
        UpdateOrderWindow updateOrderWindow = adminPage.updateOrder();
        updateOrderWindow.updateOrderWindow();
        assertEquals(DataHelper.getCurrentDate(), updateOrderWindow.getValuesButtonToday());
        Calendar calendar = updateOrderWindow.openCalendarUpdateOrder();
        calendar.calendar();
        assertEquals(DataHelper.getCurrentMonthYear(), calendar.getCurrentMonthCalendar());
        calendar.selectDateActivation();
        updateOrderWindow.uploadOrder("src/test/resources/Приказ.xlsx");
        assertEquals("Федеральный приказ успешно обновлен", adminPage.getNotification());
        assertFalse(updateOrderWindow.isWindowAppear());
    }

    @Feature("Документация")
    @Story("Успешное обновление приказа с даты в следующем месяце")
    @ExtendWith(NotificationDecorator.class)
    @Test
    void updateOrderFutureMonth() {
        UpdateOrderWindow updateOrderWindow = adminPage.updateOrder();
        Calendar calendar = updateOrderWindow.openCalendarUpdateOrder();
        calendar.switchFutureMonth();
        assertEquals(DataHelper.getFutureMonthYear(), calendar.getCurrentMonthCalendar());
        calendar.selectDateActivation();
        updateOrderWindow.uploadOrder("src/test/resources/Приказ.xlsx");
        assertEquals("Федеральный приказ успешно обновлен", adminPage.getNotification());
        assertFalse(updateOrderWindow.isWindowAppear());
    }

    @Feature("Документация")
    @Story("Успешное обновление приказа с даты в предыдущем месяце")
    @ExtendWith(NotificationDecorator.class)
    @Test
    void updateOrderPreviousMonth() {
        UpdateOrderWindow updateOrderWindow = adminPage.updateOrder();
        Calendar calendar = updateOrderWindow.openCalendarUpdateOrder();
        calendar.switchPreviousMonth();
        assertEquals(DataHelper.getPreviousMonthYear(), calendar.getCurrentMonthCalendar());
        calendar.selectDateActivation();
        updateOrderWindow.uploadOrder("src/test/resources/Приказ.xlsx");
        assertEquals("Федеральный приказ успешно обновлен", adminPage.getNotification());
        assertFalse(updateOrderWindow.isWindowAppear());
    }

    @Feature("Документация")
    @Story("Успешное обновление приказа с текущей даты")
    @ExtendWith(NotificationDecorator.class)
    @Test
    void updateOrderToday() {
        UpdateOrderWindow updateOrderWindow = adminPage.updateOrder();
        Calendar calendar = updateOrderWindow.openCalendarUpdateOrder();
        calendar.selectDateActivationToday();
        updateOrderWindow.uploadOrder("src/test/resources/Приказ.xlsx");
        assertEquals("Федеральный приказ успешно обновлен", adminPage.getNotification());
        assertFalse(updateOrderWindow.isWindowAppear());
    }

    @Feature("Документация")
    @Story("Успешное обновление приказа с текущей даты без использования календаря")
    @ExtendWith(NotificationDecorator.class)
    @Test
    void updateOrderTodayNotUseCalendar() {
        UpdateOrderWindow updateOrderWindow = adminPage.updateOrder();
        updateOrderWindow.uploadOrder("src/test/resources/Приказ.xlsx");
        assertEquals("Федеральный приказ успешно обновлен", adminPage.getNotification());
        assertFalse(updateOrderWindow.isWindowAppear());
    }

    @Feature("Документация")
    @Story("Закрытие окна обновления приказа")
    @Test
    void closeWindowUpdateOrder() {
        UpdateOrderWindow updateOrderWindow = adminPage.updateOrder();
        updateOrderWindow.closeWindowUpdateOrder();
        assertFalse(updateOrderWindow.isWindowAppear());
    }

    @Feature("Документация")
    @Story("Успешное обновление прайса с даты в текущем месяце ")
    @ExtendWith(NotificationDecorator.class)
    @Test
    void updatePriceCurrentMonth() {
        UpdatePriceWindow updatePriceWindow = adminPage.updatePrice();
        updatePriceWindow.updatePriceWindow();
        assertEquals(DataHelper.getCurrentDate(), updatePriceWindow.getValuesButtonToday());
        Calendar calendar = updatePriceWindow.openCalendarUpdatePrice();
        calendar.calendar();
        assertEquals(DataHelper.getCurrentMonthYear(), calendar.getCurrentMonthCalendar());
        calendar.selectDateActivation();
        updatePriceWindow.uploadPrice("src/test/resources/Прайс.xlsx");
        assertEquals("Прайс успешно обновлен", adminPage.getNotification());
        assertFalse(updatePriceWindow.isWindowAppear());
    }

    @Feature("Документация")
    @Story("Успешное обновление прайса с даты в следующем месяце")
    @ExtendWith(NotificationDecorator.class)
    @Test
    void updatePriceFutureMonth() {
        UpdatePriceWindow updatePriceWindow = adminPage.updatePrice();
        Calendar calendar = updatePriceWindow.openCalendarUpdatePrice();
        calendar.calendar();
        calendar.switchFutureMonth();
        assertEquals(DataHelper.getFutureMonthYear(), calendar.getCurrentMonthCalendar());
        calendar.selectDateActivation();
        updatePriceWindow.uploadPrice("src/test/resources/Прайс.xlsx");
        assertEquals("Прайс успешно обновлен", adminPage.getNotification());
        assertFalse(updatePriceWindow.isWindowAppear());
    }

    @Feature("Документация")
    @Story("Успешное обновление прайса с даты в прошлом месяце")
    @ExtendWith(NotificationDecorator.class)
    @Test
    void updatePricePreviousMonth() {
        UpdatePriceWindow updatePriceWindow = adminPage.updatePrice();
        Calendar calendar = updatePriceWindow.openCalendarUpdatePrice();
        calendar.switchPreviousMonth();
        assertEquals(DataHelper.getPreviousMonthYear(), calendar.getCurrentMonthCalendar());
        calendar.selectDateActivation();
        updatePriceWindow.uploadPrice("src/test/resources/Прайс.xlsx");
        assertEquals("Прайс успешно обновлен", adminPage.getNotification());
        assertFalse(updatePriceWindow.isWindowAppear());
    }

    @Feature("Документация")
    @Story("Успешное обновление прайса с текущей даты")
    @ExtendWith(NotificationDecorator.class)
    @Test
    void updatePriceToday() {
        UpdatePriceWindow updatePriceWindow = adminPage.updatePrice();
        Calendar calendar = updatePriceWindow.openCalendarUpdatePrice();
        calendar.selectDateActivationToday();
        updatePriceWindow.uploadPrice("src/test/resources/Прайс.xlsx");
        assertEquals("Прайс успешно обновлен", adminPage.getNotification());
        assertFalse(updatePriceWindow.isWindowAppear());
    }

    @Feature("Документация")
    @Story("Успешное обновление прайса с текущей даты без использования календаря")
    @ExtendWith(NotificationDecorator.class)
    @Test
    void updatePriceTodayNotUseCalendar() {
        UpdatePriceWindow updatePriceWindow = adminPage.updatePrice();
        updatePriceWindow.uploadPrice("src/test/resources/Прайс.xlsx");
        assertEquals("Прайс успешно обновлен", adminPage.getNotification());
        assertFalse(updatePriceWindow.isWindowAppear());
    }

    @Feature("Документация")
    @Story("Закрытие окна обновления прайса")
    @Test
    void closeWindowUpdatePrice() {
        UpdatePriceWindow updatePriceWindow = adminPage.updatePrice();
        updatePriceWindow.closeWindowUpdatePrice();
        assertFalse(updatePriceWindow.isWindowAppear());
    }

    @Feature("Документация")
    @Story("Обновление оферты с файлом в невалидном формате")
    @ExtendWith(NotificationDecorator.class)
    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/Оферта,Политика обработки docx.docx", "src/test/resources/Оферта, Политика обработки .xlsx.xlsx", "src/test/resources/Оферта, Политика обработки jpeg.jpg",})
    void updateOfferInvalidFormat(String path) {
        UpdateLegalDocWindow updateLegalDocWindow = adminPage.updateOffer();
        updateLegalDocWindow.uploadInvalidDoc(path);
        assertEquals("Допускаются файлы с расширением PDF", adminPage.getNotification());
        assertTrue(updateLegalDocWindow.isWindowAppear());
    }


    @Feature("Документация")
    @Story("Обновление политики обработки с файлом в невалидном формате")
    @ExtendWith(NotificationDecorator.class)
    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/Оферта,Политика обработки docx.docx", "src/test/resources/Оферта, Политика обработки .xlsx.xlsx", "src/test/resources/Оферта, Политика обработки jpeg.jpg"})
    void updateProcessingPolicyInvalidFormat(String path) {
        UpdateLegalDocWindow updateLegalDocWindow = adminPage.updateProcessingPolicy();
        updateLegalDocWindow.uploadInvalidDoc(path);
        assertEquals("Допускаются файлы с расширением PDF", adminPage.getNotification());
        assertTrue(updateLegalDocWindow.isWindowAppear());
    }


    @Feature("Документация")
    @Story("Обновление приказа с ошибкой в строке")
    @ExtendWith(NotificationDecorator.class)
    @Test
    void updateOrderStringError() {
        UpdateOrderWindow updateOrderWindow = adminPage.updateOrder();
        updateOrderWindow.uploadOrder("src/test/resources/Приказ с ошибкой в строке 10858.xlsx");
        assertEquals("Ошибка в 10858 строке", adminPage.getNotification());
        assertTrue(updateOrderWindow.isWindowAppear());
    }

    @Feature("Документация")
    @Story("Обновление приказа с файлом в невалидном формате")
    @ExtendWith(NotificationDecorator.class)
    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/Оферта,Политика обработки docx.docx", "src/test/resources/Оферта, Политика обработки jpeg.jpg", "src/test/resources/Оферта.pdf"})
    void updateOrderInvalidFormat(String path) {
        UpdateOrderWindow updateOrderWindow = adminPage.updateOrder();
        updateOrderWindow.uploadOrder(path);
        assertEquals("Допускаются файлы с расширением xlsx", adminPage.getNotification());
        assertTrue(updateOrderWindow.isWindowAppear());
    }


    @Feature("Документация")
    @Story("Обновление прайса с ошибкой в строке")
    @ExtendWith(NotificationDecorator.class)
    @Test
    void updatePriceCurrentMonthStringError() {
        UpdatePriceWindow updatePriceWindow = adminPage.updatePrice();
        updatePriceWindow.uploadPrice("src/test/resources/Прайс с ошибкой в строке 1398.xlsx");
        assertEquals("Ошибка в 1398 строке", adminPage.getNotification());
        assertTrue(updatePriceWindow.isWindowAppear());
    }

    @Feature("Документация")
    @Story("Обновление прайса с ошибкой формата стоимости услуги")
    @Test
    void updatePriceCurrentMonthFormatError() {
        UpdatePriceWindow updatePriceWindow = adminPage.updatePrice();
        updatePriceWindow.uploadPrice("src/test/resources/Прайс с ошибкой формата в услуге А26.30.004.002.xlsx");
        PriceErrorsWindow priceErrorsWindow = updatePriceWindow.priceErrorsWindow();
        priceErrorsWindow.priceErrorsWindow();
        assertEquals("Ошибка формата стоимости услуги, код: 'А26.30.004.002', стоимость: '1000'.", priceErrorsWindow.getErrorInfo());
    }

    @Feature("Документация")
    @Story("Переключение на вкладку правил корректирования файла прайса")
    @Test
    void openAdjustmentRulesPrice() {
        UpdatePriceWindow updatePriceWindow = adminPage.updatePrice();
        updatePriceWindow.uploadPrice("src/test/resources/Прайс с ошибкой формата в услуге А26.30.004.002.xlsx");
        PriceErrorsWindow priceErrorsWindow = updatePriceWindow.priceErrorsWindow();
        priceErrorsWindow.clickAdjustmentRulesTab();
        assertTrue(priceErrorsWindow.isAdjustmentRulesAppear());
    }

    @Feature("Документация")
    @Story("Переключение на вкладку ошибок файла прайса")
    @Test
    void openErrorsPrice() {
        UpdatePriceWindow updatePriceWindow = adminPage.updatePrice();
        updatePriceWindow.uploadPrice("src/test/resources/Прайс с ошибкой формата в услуге А26.30.004.002.xlsx");
        PriceErrorsWindow priceErrorsWindow = updatePriceWindow.priceErrorsWindow();
        priceErrorsWindow.clickAdjustmentRulesTab();
        priceErrorsWindow.clickErrorPrice();
        assertTrue(priceErrorsWindow.isErrorInfoAppear());
    }

    @Feature("Документация")
    @Story("Закрытие окна ошибок файла прайса")
    @Test
    void closeWindowErrorsPrice() {
        UpdatePriceWindow updatePriceWindow = adminPage.updatePrice();
        updatePriceWindow.uploadPrice("src/test/resources/Прайс с ошибкой формата в услуге А26.30.004.002.xlsx");
        PriceErrorsWindow priceErrorsWindow = updatePriceWindow.priceErrorsWindow();
        priceErrorsWindow.closeWindowPriceErrors();
        assertFalse(priceErrorsWindow.isWindowAppear());
    }


    @Feature("Документация")
    @Story("Обновление прайса с файлом в невалидном формате")
    @ExtendWith(NotificationDecorator.class)
    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/Оферта,Политика обработки docx.docx", "src/test/resources/Оферта, Политика обработки jpeg.jpg", "src/test/resources/Оферта.pdf"})
    void updatePriceInvalidFormat(String path) {
        UpdatePriceWindow updatePriceWindow = adminPage.updatePrice();
        updatePriceWindow.uploadPrice(path);
        assertEquals("Допускаются файлы с расширением xlsx", adminPage.getNotification());
        assertTrue(updatePriceWindow.isWindowAppear());

    }

    @Story("Закрытие уведомления на странице администрирования по таймауту")
    @Test
    void closeNotificationTimeout() {
        UpdateLegalDocWindow updateLegalDocWindow = adminPage.updateOffer();
        updateLegalDocWindow.uploadValidDoc("src/test/resources/Оферта,Политика обработки docx.docx");
        checkCloseNotificationTimeout(basePage);
    }

    @Story("Закрытие уведомления на странице администрирования")
    @Test
    void closeNotification() {
        UpdateLegalDocWindow updateLegalDocWindow = adminPage.updateOffer();
        updateLegalDocWindow.uploadValidDoc("src/test/resources/Оферта,Политика обработки docx.docx");
        checkCloseNotification(basePage);
    }
}
/*
    @Feature("Документация")
    @Story("Загрузка файла с прайсом")
    @Test
    void downloadPrice() {
        UpdatePriceWindow updatePriceWindow = adminPage.updatePrice();
        updatePriceWindow.updatePriceWindow();
        File downloadedFile = updatePriceWindow.downloadPriceDateActivation();
    }
*/
