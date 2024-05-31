package admin.test;

import admin.data.DataTest;
import admin.pages.*;
import admin.utils.testUtils.CookieUtils;
import admin.utils.testUtils.TestSetupAuth;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Epic("Хэдер")
public class HeaderBarTest {

    @BeforeAll
    static void setUpAllAllure() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }


    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeAll
    static void setupAdminPanelWithCookies() {
        TestSetupAuth.authAdminPanel(DataTest.getLoginAdmin(), DataTest.getPasswordAdmin());
    }

    @BeforeEach
    void loadCookies() {
        TestSetupAuth.openAdministrationPage();
        CookieUtils.loadCookies();
    }

    @Feature("Смена своего пароля админом")
    @Story("Успешная мена своего пароля")
    @Test
    void changePasswordAdmin() {
        HeaderBar headerBar = new HeaderBar();
        headerBar.headerBarSuperAdmin();
        DoctorsPage doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
    }

    @Feature("Навигация")
    @Story("Переход на вкладку докторов")
    @Test
    void openDoctorsPage() {
        HeaderBar headerBar = new HeaderBar();
        headerBar.headerBarSuperAdmin();
        DoctorsPage doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
    }

    @Feature("Навигация")
    @Story("Переход на вкладку услуг")
    @Test
    void openServicesPage() {
        HeaderBar headerBar = new HeaderBar();
        headerBar.headerBarSuperAdmin();
        ServicesPage servicesPage = headerBar.servicesTabOpen();
        servicesPage.servicesPage();
    }

    @Feature("Навигация")
    @Story("Переход на вкладку faq")
    @Test
    void openFaqPage() {
        HeaderBar headerBar = new HeaderBar();
        headerBar.headerBarSuperAdmin();
        FaqPage faqPage = headerBar.faqTabOpen();
        faqPage.faqPage();
    }

    @Feature("Навигация")
    @Story("Переход на вкладку настроек")
    @Test
    void openSettingPage() {
        HeaderBar headerBar = new HeaderBar();
        headerBar.headerBarSuperAdmin();
        SettingPage settingPage = headerBar.settingTabOpen();
        settingPage.settingPage();
    }

    @Feature("Навигация")
    @Story("Переход на вкладку администрирования")
    @Test
    void openAdministrationPage() {
        HeaderBar headerBar = new HeaderBar();
        headerBar.headerBarSuperAdmin();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.adminPage();
    }

    @Feature("Навигация")
    @Story("Сохранение состояния страницы при клике по вкладке администрирования")
    @Test
    void clickAdministrationPage() {
        HeaderBar headerBar = new HeaderBar();
        headerBar.headerBarSuperAdmin();
        AdministrationPage adminPage = headerBar.administrationTabOpen();
        adminPage.adminPage();
    }
}
