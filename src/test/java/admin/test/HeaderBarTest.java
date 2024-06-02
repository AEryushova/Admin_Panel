package admin.test;

import admin.pages.*;
import admin.utils.testUtils.AdminAddDeleteDecorator;
import admin.utils.testUtils.CookieUtils;
import admin.utils.testUtils.TestSetupAuth;
import admin.utils.testUtils.*;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@Epic("Хэдер")
public class HeaderBarTest {

    private AuthorizationPage authPage;
    private HeaderBar headerBar;

    @ExtendWith(AllureDecorator.class)

    @BeforeAll
    static void setupAdminPanelWithCookies() {
        TestSetupAuth.
    }

    @BeforeEach
    void loadCookies() {
        TestSetupAuth.openAdministrationPage();
        CookieUtils.loadCookies();
    }

    @ExtendWith(AdminAddDeleteDecorator.class)
    @Feature("Выход из админ-панели")
    @Story("Успешный выход из админ-панели")
    @Test
    void exitAdminPanel() {
        headerBar.headerBarAdmin();
        headerBar.openAndCloseProfileAdmin();
        headerBar.exitAdminPanel();
        authPage.authPage();
    }

    @Feature("Смена своего пароля админом")
    @Story("Успешная мена своего пароля")
    @Test
    void changePasswordAdmin() {
        headerBar.headerBarSuperAdmin();
        DoctorsPage doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
    }

    @Feature("Навигация")
    @Story("Переход на вкладку докторов")
    @Test
    void openDoctorsPage() {
        headerBar.headerBarSuperAdmin();
        DoctorsPage doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
    }

    @Feature("Навигация")
    @Story("Переход на вкладку услуг")
    @Test
    void openServicesPage() {
        headerBar.headerBarSuperAdmin();
        ServicesPage servicesPage = headerBar.servicesTabOpen();
        servicesPage.servicesPage();
    }

    @Feature("Навигация")
    @Story("Переход на вкладку faq")
    @Test
    void openFaqPage() {
        headerBar.headerBarSuperAdmin();
        FaqPage faqPage = headerBar.faqTabOpen();
        faqPage.faqPage();
    }

    @Feature("Навигация")
    @Story("Переход на вкладку настроек")
    @Test
    void openSettingPage() {
        headerBar.headerBarSuperAdmin();
        SettingPage settingPage = headerBar.settingTabOpen();
        settingPage.settingPage();
    }

    @Feature("Навигация")
    @Story("Переход на вкладку администрирования")
    @Test
    void openAdministrationPage() {
        headerBar.headerBarSuperAdmin();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.adminPage();
    }

    @Feature("Навигация")
    @Story("Сохранение состояния страницы при клике по вкладке администрирования")
    @Test
    void clickAdministrationPage() {
        headerBar.headerBarSuperAdmin();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.adminPage();
    }
}
