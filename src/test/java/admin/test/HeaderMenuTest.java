package admin.test;

import admin.data.DataInfo;
import admin.pages.AdministrationPage.AdministrationPage;
import admin.pages.AuthorizationPage.AuthorizationPage;
import admin.pages.DoctorsPage.DoctorsPage;
import admin.pages.FaqPage.FaqPage;
import admin.pages.HeaderMenu.HeaderMenu;
import admin.pages.ServicesPage.ServicesPage;
import admin.pages.SettingPage.SettingPage;
import admin.utils.decoratorsTest.AdminAddDeleteDecorator;
import admin.utils.decoratorsTest.AllureDecorator;
import admin.utils.testUtils.*;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@Epic("Хэдер")
public class HeaderMenuTest {

    private AuthorizationPage authPage;
    private HeaderMenu headerMenu;

    @ExtendWith(AllureDecorator.class)

    @BeforeAll
    static void setUpAuth() {
        BrowserManager.authGetCookie(DataInfo.UserData.getLoginAdmin(), DataInfo.UserData.getPasswordAdmin());
    }

    @BeforeEach
    void setUp(){
        BrowserManager.openPagesAfterAuth();
        headerMenu = new HeaderMenu();
    }


    @ExtendWith(AdminAddDeleteDecorator.class)
    @Feature("Выход из админ-панели")
    @Story("Успешный выход из админ-панели")
    @Test
    void exitAdminPanel() {
        headerMenu.headerBarAdmin();
        headerMenu.openAndCloseProfileAdmin();
        headerMenu.exitAdminPanel();
        authPage.authPage();
    }

    @Feature("Смена своего пароля админом")
    @Story("Успешная мена своего пароля")
    @Test
    void changePasswordAdmin() {
        headerMenu.headerBarSuperAdmin();
        DoctorsPage doctorsPage = headerMenu.doctorsTabOpen();
        doctorsPage.doctorsPage();
    }

    @Feature("Навигация")
    @Story("Переход на вкладку докторов")
    @Test
    void openDoctorsPage() {
        headerMenu.headerBarSuperAdmin();
        DoctorsPage doctorsPage = headerMenu.doctorsTabOpen();
        doctorsPage.doctorsPage();
    }

    @Feature("Навигация")
    @Story("Переход на вкладку услуг")
    @Test
    void openServicesPage() {
        headerMenu.headerBarSuperAdmin();
        ServicesPage servicesPage = headerMenu.servicesTabOpen();
        servicesPage.servicesPage();
    }

    @Feature("Навигация")
    @Story("Переход на вкладку faq")
    @Test
    void openFaqPage() {
        headerMenu.headerBarSuperAdmin();
        FaqPage faqPage = headerMenu.faqTabOpen();
        faqPage.faqPage();
    }

    @Feature("Навигация")
    @Story("Переход на вкладку настроек")
    @Test
    void openSettingPage() {
        headerMenu.headerBarSuperAdmin();
        SettingPage settingPage = headerMenu.settingTabOpen();
        settingPage.settingPage();
    }

    @Feature("Навигация")
    @Story("Переход на вкладку администрирования")
    @Test
    void openAdministrationPage() {
        headerMenu.headerBarSuperAdmin();
        AdministrationPage adminPage = headerMenu.administrationTabOpen();
        adminPage.adminPage();
    }

}
