package test;

import pages.AdministrationPage.AdministrationPage;
import pages.DoctorsPage.DoctorsPage;
import pages.FaqPage.FaqPage;
import pages.HeaderMenu.HeaderMenu;
import pages.ServicesPage.ServicesPage;
import pages.SettingPage.SettingPage;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import static data.TestData.UserData.*;

@Epic("Навигационное меню")
@DisplayName("Навигационное меню")
public class NavigationTest extends BaseTest{

    private HeaderMenu headerMenu;

    @BeforeAll
    static void setUpAuth() {
        BaseTest.openAdminPanel(LOGIN_SUPER_ADMIN, PASSWORD_SUPER_ADMIN);
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
    @DisplayName("Переход на вкладку докторов")
    @Test
    void openDoctorsPage() {
        headerMenu.verifyHeaderBarSuperAdmin();
        DoctorsPage doctorsPage = headerMenu.clickDoctorsTab();
        doctorsPage.verifyDoctorsPage();
    }

    @Feature("Навигация")
    @Story("Сохранение вкладки докторов")
    @DisplayName("Сохранение вкладки докторов")
    @Test
    void saveDoctorsPage() {
        headerMenu.verifyHeaderBarSuperAdmin();
        DoctorsPage doctorsPage = headerMenu.clickDoctorsTab();
        doctorsPage.verifyDoctorsPage();
        headerMenu.clickDoctorsTab();
        doctorsPage.verifyDoctorsPage();
    }

    @Feature("Навигация")
    @Story("Переход на вкладку услуг")
    @DisplayName("Переход на вкладку услуг")
    @Test
    void openServicesPage() {
        headerMenu.verifyHeaderBarSuperAdmin();
        ServicesPage servicesPage = headerMenu.clickServicesTab();
        servicesPage.verifyServicesPage();
    }

    @Feature("Навигация")
    @Story("Сохранение вкладки услуг")
    @DisplayName("Сохранение вкладки услуг")
    @Test
    void saveServicesPage() {
        headerMenu.verifyHeaderBarSuperAdmin();
        ServicesPage servicesPage = headerMenu.clickServicesTab();
        servicesPage.verifyServicesPage();
        headerMenu.clickServicesTab();
        servicesPage.verifyServicesPage();
    }

    @Feature("Навигация")
    @Story("Переход на вкладку faq")
    @DisplayName("Переход на вкладку faq")
    @Test
    void openFaqPage() {
        headerMenu.verifyHeaderBarSuperAdmin();
        FaqPage faqPage = headerMenu.clickFaqTab();
        faqPage.verifyFaqPage();
    }

    @Feature("Навигация")
    @Story("Сохранение вкладки faq")
    @DisplayName("Сохранение вкладки faq")
    @Test
    void saveFaqPage() {
        headerMenu.verifyHeaderBarSuperAdmin();
        FaqPage faqPage = headerMenu.clickFaqTab();
        faqPage.verifyFaqPage();
        headerMenu.clickFaqTab();
        faqPage.verifyFaqPage();
    }

    @Feature("Навигация")
    @Story("Переход на вкладку настроек")
    @DisplayName("Переход на вкладку настроек")
    @Test
    void openSettingPage() {
        headerMenu.verifyHeaderBarSuperAdmin();
        SettingPage settingPage = headerMenu.clickSettingTab();
        settingPage.verifySettingPage();
    }

    @Feature("Навигация")
    @Story("Сохранение вклакди настроек")
    @DisplayName("Сохранение вклакди настроек")
    @Test
    void saveSettingPage() {
        headerMenu.verifyHeaderBarSuperAdmin();
        SettingPage settingPage = headerMenu.clickSettingTab();
        settingPage.verifySettingPage();
        headerMenu.clickSettingTab();
        settingPage.verifySettingPage();
    }

    @Feature("Навигация")
    @Story("Переход на вкладку администрирования")
    @DisplayName("Переход на вкладку администрирования")
    @Test
    void openAdministrationPage() {
        headerMenu.verifyHeaderBarSuperAdmin();
        AdministrationPage adminPage = headerMenu.clickAdministrationTab();
        adminPage.verifyAdminPage();
    }

    @Feature("Навигация")
    @Story("Сохранение вкладки администрирования")
    @DisplayName("Сохранение вкладки администрирования")
    @Test
    void saveAdministrationPage() {
        headerMenu.verifyHeaderBarSuperAdmin();
        AdministrationPage adminPage = headerMenu.clickAdministrationTab();
        adminPage.verifyAdminPage();
        headerMenu.clickAdministrationTab();
        adminPage.verifyAdminPage();
    }
}
