package admin.test;

import admin.data.DataConfig;
import admin.pages.AdministrationPage.AdministrationPage;
import admin.pages.DoctorsPage.DoctorsPage;
import admin.pages.FaqPage.FaqPage;
import admin.pages.HeaderMenu.HeaderMenu;
import admin.pages.ServicesPage.ServicesPage;
import admin.pages.SettingPage.SettingPage;
import admin.utils.preparationDataTests.general.AllureDecorator;
import admin.utils.testUtils.BrowserManager;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

public class NavigationTest {

    private HeaderMenu headerMenu;

    @ExtendWith(AllureDecorator.class)

    @BeforeAll
    static void setUpAuth() {
        BrowserManager.openBrowser(DataConfig.UserData.getLOGIN_SUPER_ADMIN(), DataConfig.UserData.getPASSWORD_SUPER_ADMIN());
    }

    @BeforeEach
    void setUp(){
        Selenide.refresh();
        headerMenu = new HeaderMenu();
    }

    @AfterAll
    static void closeWebDriver() {
        Selenide.closeWebDriver();
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
    @Story("Сохранение вкладки докторов")
    @Test
    void saveDoctorsPage() {
        headerMenu.headerBarSuperAdmin();
        DoctorsPage doctorsPage = headerMenu.doctorsTabOpen();
        doctorsPage.doctorsPage();
        headerMenu.doctorsTabOpen();
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
    @Story("Сохранение вкладки услуг")
    @Test
    void saveServicesPage() {
        headerMenu.headerBarSuperAdmin();
        ServicesPage servicesPage = headerMenu.servicesTabOpen();
        servicesPage.servicesPage();
        headerMenu.servicesTabOpen();
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
    @Story("Сохранение вкладки faq")
    @Test
    void saveFaqPage() {
        headerMenu.headerBarSuperAdmin();
        FaqPage faqPage = headerMenu.faqTabOpen();
        faqPage.faqPage();
        headerMenu.faqTabOpen();
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
    @Story("Сохранение вклакди настроек")
    @Test
    void saveSettingPage() {
        headerMenu.headerBarSuperAdmin();
        SettingPage settingPage = headerMenu.settingTabOpen();
        settingPage.settingPage();
        headerMenu.settingTabOpen();
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

    @Feature("Навигация")
    @Story("Сохранение вкладки администрирования")
    @Test
    void saveAdministrationPage() {
        headerMenu.headerBarSuperAdmin();
        AdministrationPage adminPage = headerMenu.administrationTabOpen();
        adminPage.adminPage();
        headerMenu.administrationTabOpen();
        adminPage.adminPage();
    }
}
