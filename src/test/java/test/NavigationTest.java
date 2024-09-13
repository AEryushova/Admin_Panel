package test;

import com.codeborne.selenide.WebDriverRunner;
import pages.AdministrationPage.AdministrationPage;
import pages.DoctorsPage.DoctorsPage;
import pages.FaqPage.FaqPage;
import pages.ServicesPage.ServicesPage;
import pages.SettingPage.SettingPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;

import static appData.AppData.URI_ADMIN_PANEL;
import static data.TestData.UserData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Навигационное меню")
@DisplayName("Навигационное меню")
public class NavigationTest extends BaseTest {

    @BeforeAll
    static void setUpAuth() {
        getAuthToken(LOGIN_SUPER_ADMIN, PASSWORD_SUPER_ADMIN);
    }

    @BeforeEach
    void setUp() {
        openAuthAdminPanel();
        headerMenu.verifyHeaderBarSuperAdmin();
    }

    @AfterEach()
    void closeWebDriver() {
        closeDriver();
    }


    @Feature("Навигация")
    @Story("Переход на вкладку докторов")
    @DisplayName("Переход на вкладку докторов")
    @Test
    void openDoctorsPage() {
        headerMenu.clickServicesTab();
        DoctorsPage doctorsPage = headerMenu.clickDoctorsTab();
        doctorsPage.verifyDoctorsPage();
        assertEquals(URI_ADMIN_PANEL + "/doctors", WebDriverRunner.url());
    }

    @Feature("Навигация")
    @Story("Сохранение вкладки докторов")
    @DisplayName("Сохранение вкладки докторов")
    @Test
    void saveDoctorsPage() {
        headerMenu.clickServicesTab();
        DoctorsPage doctorsPage = headerMenu.clickDoctorsTab();
        headerMenu.clickDoctorsTab();
        doctorsPage.verifyDoctorsPage();
        assertEquals(URI_ADMIN_PANEL + "/doctors", WebDriverRunner.url());
    }

    @Feature("Навигация")
    @Story("Переход на вкладку услуг")
    @DisplayName("Переход на вкладку услуг")
    @Test
    void openServicesPage() {
        ServicesPage servicesPage = headerMenu.clickServicesTab();
        servicesPage.verifyServicesPage();
        assertEquals(URI_ADMIN_PANEL + "/services", WebDriverRunner.url());
    }

    @Feature("Навигация")
    @Story("Сохранение вкладки услуг")
    @DisplayName("Сохранение вкладки услуг")
    @Test
    void saveServicesPage() {
        ServicesPage servicesPage = headerMenu.clickServicesTab();
        headerMenu.clickServicesTab();
        servicesPage.verifyServicesPage();
        assertEquals(URI_ADMIN_PANEL + "/services", WebDriverRunner.url());
    }

    @Feature("Навигация")
    @Story("Переход на вкладку faq")
    @DisplayName("Переход на вкладку faq")
    @Test
    void openFaqPage() {
        FaqPage faqPage = headerMenu.clickFaqTab();
        faqPage.verifyFaqPage();
        assertEquals(URI_ADMIN_PANEL + "/faq", WebDriverRunner.url());
    }

    @Feature("Навигация")
    @Story("Сохранение вкладки faq")
    @DisplayName("Сохранение вкладки faq")
    @Test
    void saveFaqPage() {
        FaqPage faqPage = headerMenu.clickFaqTab();
        headerMenu.clickFaqTab();
        faqPage.verifyFaqPage();
        assertEquals(URI_ADMIN_PANEL + "/faq", WebDriverRunner.url());
    }

    @Feature("Навигация")
    @Story("Переход на вкладку настроек")
    @DisplayName("Переход на вкладку настроек")
    @Test
    void openSettingPage() {
        SettingPage settingPage = headerMenu.clickSettingTab();
        settingPage.verifySettingPage();
        assertEquals(URI_ADMIN_PANEL + "/settings", WebDriverRunner.url());
    }

    @Feature("Навигация")
    @Story("Сохранение вклакди настроек")
    @DisplayName("Сохранение вклакди настроек")
    @Test
    void saveSettingPage() {
        SettingPage settingPage = headerMenu.clickSettingTab();
        headerMenu.clickSettingTab();
        settingPage.verifySettingPage();
        assertEquals(URI_ADMIN_PANEL + "/settings", WebDriverRunner.url());
    }

    @Feature("Навигация")
    @Story("Переход на вкладку администрирования")
    @DisplayName("Переход на вкладку администрирования")
    @Test
    void openAdministrationPage() {
        AdministrationPage adminPage = headerMenu.clickAdministrationTab();
        adminPage.verifyAdminPage();
        assertEquals(URI_ADMIN_PANEL + "/admins", WebDriverRunner.url());
    }

    @Feature("Навигация")
    @Story("Сохранение вкладки администрирования")
    @DisplayName("Сохранение вкладки администрирования")
    @Test
    void saveAdministrationPage() {
        AdministrationPage adminPage = headerMenu.clickAdministrationTab();
        headerMenu.clickAdministrationTab();
        adminPage.verifyAdminPage();
        assertEquals(URI_ADMIN_PANEL + "/admins", WebDriverRunner.url());
    }
}
