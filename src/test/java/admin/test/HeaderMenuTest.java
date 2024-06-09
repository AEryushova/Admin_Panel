package admin.test;

import admin.data.DataInfo;
import admin.pages.AdministrationPage.AdministrationPage;
import admin.pages.AuthorizationPage.AuthorizationPage;
import admin.pages.BasePage.BasePage;
import admin.pages.DoctorsPage.DoctorsPage;
import admin.pages.FaqPage.FaqPage;
import admin.pages.HeaderMenu.ChangeMinePasswordWindow;
import admin.pages.HeaderMenu.HeaderMenu;
import admin.pages.HeaderMenu.UserPanel;
import admin.pages.ServicesPage.ServicesPage;
import admin.pages.SettingPage.SettingPage;
import admin.utils.decoratorsTest.general.AllureDecorator;
import admin.utils.decoratorsTest.general.NotificationDecorator;
import admin.utils.decoratorsTest.headerMenu.NewAuthDecorator;
import admin.utils.decoratorsTest.headerMenu.NewAuthSuperAdminDecorator;
import admin.utils.decoratorsTest.headerMenu.ReturnPasswordAdmin;
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
        BrowserManager.authGetCookie(DataInfo.UserData.getLoginAdmin(), DataInfo.UserData.getPasswordAdmin());
    }

    @BeforeEach
    void setUp(){
        BrowserManager.openPagesAfterAuth();
        headerMenu = new HeaderMenu();
        basePage = new BasePage();
    }


    @Feature("Смена своего пароля админом")
    @Story("Успешная замена своего пароля")
    @ExtendWith({ReturnPasswordAdmin.class, NotificationDecorator.class})
    @Test
    @Order(24)
    void changeMainPassword() {
        UserPanel userPanel=headerMenu.openAndCloseProfile();
        userPanel.userPanelAdmin();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.changePassword();
        changeMinePassWindow.changeMinePasswordWindow();
        changeMinePassWindow.fillFieldOldPassword(DataInfo.UserData.getPasswordAdmin());
        changeMinePassWindow.fillFieldNewPassword(DataInfo.UserData.getNewPasswordAdmin());
        changeMinePassWindow.clickChangeButton();
        assertEquals("Пароль успешно обновлен",basePage.getNotification());
        assertFalse(changeMinePassWindow.isWindowAppear());
        assertFalse(userPanel.isWindowAppear());
    }


    @Feature("Смена своего пароля админом")
    @Story("Успешная замена своего пароля при совпадающем старом и новом пароле")
    @ExtendWith(NotificationDecorator.class)
    @Test
    @Order(23)
    void changeMainPasswordEqualsPassword() {
        UserPanel userPanel=headerMenu.openAndCloseProfile();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.changePassword();
        changeMinePassWindow.fillFieldOldPassword(DataInfo.UserData.getPasswordAdmin());
        changeMinePassWindow.fillFieldNewPassword(DataInfo.UserData.getPasswordAdmin());
        changeMinePassWindow.clickChangeButton();
        assertEquals("Пароль успешно обновлен",basePage.getNotification());
        assertFalse(changeMinePassWindow.isWindowAppear());
        assertFalse(userPanel.isWindowAppear());
    }

    @Feature("Смена своего пароля админом")
    @Story("Замена своего пароля с пустым полем старого пароля")
    @Test
    @Order(1)
    void changeMainPasswordEmptyFieldsOldPassword() {
        UserPanel userPanel=headerMenu.openAndCloseProfile();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.changePassword();
        changeMinePassWindow.fillFieldNewPassword(DataInfo.UserData.getPasswordAdmin());
        assertFalse(changeMinePassWindow.isEnabledChangeButton());
    }

    @Feature("Смена своего пароля админом")
    @Story("Замена своего пароля с пустым полем нового пароля")
    @Test
    @Order(2)
    void changeMainPasswordEmptyFieldsNewPassword() {
        UserPanel userPanel=headerMenu.openAndCloseProfile();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.changePassword();
        changeMinePassWindow.fillFieldOldPassword(DataInfo.UserData.getPasswordAdmin());
        assertFalse(changeMinePassWindow.isEnabledChangeButton());
    }


    @Feature("Смена своего пароля админом")
    @Story("Отображение уведомления об обязательности полей")
    @Test
    @Order(3)
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
    @Order(4)
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
    @Order(5)
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
    @Order(6)
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
    @Order(7)
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
    @Order(8)
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
    @Order(9)
    void changeMainPasswordNewPasswordNotLatinValue(String password) {
        UserPanel userPanel=headerMenu.openAndCloseProfile();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.changePassword();
        changeMinePassWindow.fillFieldNewPassword(password);
        assertEquals("Пароль не валиден", changeMinePassWindow.getErrorFieldNewPassword());
    }


    @Feature("Смена своего пароля админом")
    @Story("Очистка поля старого пароля через кнопку в окне изменения своего пароля")
    @Test
    @Order(10)
    void clearFieldOldPasswordThroughButtonClear(){
        UserPanel userPanel=headerMenu.openAndCloseProfile();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.changePassword();
        changeMinePassWindow.fillFieldOldPassword(DataInfo.UserData.getPasswordAdmin());
        changeMinePassWindow.clickClearButtonOldPasswordField();
        assertEquals("", changeMinePassWindow.getValueOldPasswordField());
        assertEquals("Обязательное поле", changeMinePassWindow.getErrorFieldOldPassword());
    }

    @Feature("Смена своего пароля админом")
    @Story("Очистка поля нового пароля через кнопку в окне изменения своего пароля")
    @Test
    @Order(11)
    void clearFieldNewPasswordThroughButtonClear(){
        UserPanel userPanel=headerMenu.openAndCloseProfile();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.changePassword();
        changeMinePassWindow.fillFieldNewPassword(DataInfo.UserData.getPasswordAdmin());
        changeMinePassWindow.clickClearButtonNewPasswordField();
        assertEquals("", changeMinePassWindow.getValueNewPasswordField());
        assertEquals("Обязательное поле", changeMinePassWindow.getErrorFieldNewPassword());
    }


    @Feature("Смена своего пароля админом")
    @Story("Зануление полей в окне изменения своего пароля и закрытие окна")
    @Test
    @Order(12)
    void closeWindowChangeMainPassword() {
        UserPanel userPanel=headerMenu.openAndCloseProfile();
        ChangeMinePasswordWindow changeMinePassWindow=userPanel.changePassword();
        changeMinePassWindow.fillFieldOldPassword(DataInfo.UserData.getPasswordAdmin());
        changeMinePassWindow.fillFieldNewPassword(DataInfo.UserData.getPasswordAdmin());
        changeMinePassWindow.clickCancelButton();
        assertFalse(changeMinePassWindow.isWindowAppear());
        assertFalse(userPanel.isWindowAppear());
        headerMenu.openAndCloseProfile();
        userPanel.changePassword();
        assertEquals("", changeMinePassWindow.getValueOldPasswordField());
        assertEquals("", changeMinePassWindow.getValueNewPasswordField());
    }

    @Feature("Навигация")
    @Story("Переход на вкладку докторов")
    @Test
    @Order(13)
    void openDoctorsPage() {
        headerMenu.headerBarAdmin();
        DoctorsPage doctorsPage = headerMenu.doctorsTabOpen();
        doctorsPage.doctorsPage();
    }

    @Feature("Навигация")
    @Story("Сохранение вкладки докторов")
    @Test
    @Order(14)
    void saveDoctorsPage() {
        headerMenu.headerBarAdmin();
        DoctorsPage doctorsPage = headerMenu.doctorsTabOpen();
        doctorsPage.doctorsPage();
        headerMenu.doctorsTabOpen();
        doctorsPage.doctorsPage();
    }

    @Feature("Навигация")
    @Story("Переход на вкладку услуг")
    @Test
    @Order(15)
    void openServicesPage() {
        headerMenu.headerBarAdmin();
        ServicesPage servicesPage = headerMenu.servicesTabOpen();
        servicesPage.servicesPage();
    }

    @Feature("Навигация")
    @Story("Сохранение вкладки услуг")
    @Test
    @Order(16)
    void saveServicesPage() {
        headerMenu.headerBarAdmin();
        ServicesPage servicesPage = headerMenu.servicesTabOpen();
        servicesPage.servicesPage();
        headerMenu.servicesTabOpen();
        servicesPage.servicesPage();
    }

    @Feature("Навигация")
    @Story("Переход на вкладку faq")
    @Test
    @Order(17)
    void openFaqPage() {
        headerMenu.headerBarAdmin();
        FaqPage faqPage = headerMenu.faqTabOpen();
        faqPage.faqPage();
    }

    @Feature("Навигация")
    @Story("Сохранение вкладки faq")
    @Test
    @Order(18)
    void saveFaqPage() {
        headerMenu.headerBarAdmin();
        FaqPage faqPage = headerMenu.faqTabOpen();
        faqPage.faqPage();
        headerMenu.faqTabOpen();
        faqPage.faqPage();
    }

    @Feature("Навигация")
    @Story("Переход на вкладку настроек")
    @Test
    @Order(19)
    void openSettingPage() {
        headerMenu.headerBarAdmin();
        SettingPage settingPage = headerMenu.settingTabOpen();
        settingPage.settingPage();
    }

    @Feature("Навигация")
    @Story("Сохранение вклакди настроек")
    @Test
    @Order(20)
    void saveSettingPage() {
        headerMenu.headerBarAdmin();
        SettingPage settingPage = headerMenu.settingTabOpen();
        settingPage.settingPage();
        headerMenu.settingTabOpen();
        settingPage.settingPage();
    }

    @Feature("Навигация")
    @Story("Переход на вкладку администрирования")
    @ExtendWith(NewAuthSuperAdminDecorator.class)
    @Test
    @Order(25)
    void openAdministrationPage() {
        headerMenu.headerBarSuperAdmin();
        AdministrationPage adminPage = headerMenu.administrationTabOpen();
        adminPage.adminPage();
    }

    @Feature("Навигация")
    @Story("Сохранение вкладки администрирования")
    @ExtendWith(NewAuthSuperAdminDecorator.class)
    @Test
    @Order(26)
    void saveAdministrationPage() {
        headerMenu.headerBarSuperAdmin();
        AdministrationPage adminPage = headerMenu.administrationTabOpen();
        adminPage.adminPage();
        headerMenu.administrationTabOpen();
        adminPage.adminPage();
    }

    @Feature("Панель пользователя")
    @Story("Закрытие панели пользователя")
    @Test
    @Order(21)
    void closeUserPanel() {
        UserPanel userPanel=headerMenu.openAndCloseProfile();
        headerMenu.openAndCloseProfile();
        assertFalse(userPanel.isWindowAppear());
    }

    @Feature("Панель пользователя")
    @Story("Успешный выход из админ-панели")
    @ExtendWith(NewAuthDecorator.class)
    @Test
    @Order(22)
    void exitAdminPanel() {
        UserPanel userPanel=headerMenu.openAndCloseProfile();
        AuthorizationPage authPage=userPanel.exitAdminPanel();
        authPage.authPage();
    }


}
