package admin.test;

import admin.data.DataTest;
import admin.pages.HeaderBar;
import admin.pages.ServicesPage;
import admin.pages.modalWindowServices.AddRuleWindow;
import admin.pages.modalWindowServices.RulesPreparingWindow;
import admin.utils.testUtils.TestSetupAuth;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

import org.junit.jupiter.api.Assertions;
import user.pages.AuthPage;
import user.pages.HeaderBarLK;
import user.pages.HomePage;
import user.pages.ServicesPageLK;
import user.pages.modalWindowServices.Rule;
import user.pages.modalWindowServices.RulesPreparingWindowLK;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Услуги")
@Feature("Управление услугами")
public class ServicesPageTest {

    private HeaderBar headerBar;
    private ServicesPage servicesPage;


    @BeforeAll
    static void setUpAllAllure() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }


    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setUp() {
        TestSetupAuth.authAdminPanel(DataTest.getLoginAdmin(),DataTest.getPasswordAdmin());
    }

    @Story("Смена последовательности категорий")
    @Test
    void sequenceChangeQuestion() throws InterruptedException {
        servicesPage = headerBar.servicesTabOpen();
        servicesPage.servicesPage();
        int sequenceFirstCategory = servicesPage.getCategoryIndexByName("Телемедицина");
        int sequenceSecondCategory = servicesPage.getCategoryIndexByName("Стоматология");
        servicesPage.sequenceChangeCategory("Телемедицина", "Стоматология");
        Thread.sleep(5000);
        int sequenceFirstCategory1 = servicesPage.getCategoryIndexByName("Стоматология");
        int sequenceSecondCategory1 = servicesPage.getCategoryIndexByName("Телемедицина");
        Assertions.assertEquals(sequenceFirstCategory, sequenceFirstCategory1);
        Assertions.assertEquals(sequenceSecondCategory, sequenceSecondCategory1);
    }


    @Story("Добавление правила подоготовки к категории")
    @Test
    void addRulePreparingCategory() {
        servicesPage = headerBar.servicesTabOpen();
        servicesPage.servicesPage();
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparing();
        rulePreparingWindow.rulesPreparingWindow();
        AddRuleWindow addRuleWindow = rulePreparingWindow.addRulesWindow();
        addRuleWindow.addRuleWindow();
        addRuleWindow.fillingFieldHeader("Правило подготовки 1");
        addRuleWindow.fillingFieldDescription("Текст правила подготовки к категории");
        addRuleWindow.clickSaveButton();
        assertEquals("Правило подготовки 1", rulePreparingWindow.getHeaderRule());
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1920x1080";
        open("https://lk.mdapp.online/auth");
        localStorage().setItem("Environment", "freeze");
        clearBrowserCookies();
        AuthPage authPage = new AuthPage();
        HomePage homePage = authPage.authorizationLK();
        homePage.homePage();
        HeaderBarLK headerBarLK = new HeaderBarLK();
        headerBarLK.headerBarLK();
        ServicesPageLK servicesPageLK = headerBarLK.servicesTabOpen();
        servicesPageLK.servicesPageLK();
        servicesPageLK.openSectionListLaboratories();
        RulesPreparingWindowLK rulesPreparingWindow = servicesPageLK.openRulesPreparingCategory();
        rulesPreparingWindow.rulesPreparingWindow();
        Rule rule = rulesPreparingWindow.openRule();
        rule.rule();
        assertEquals("Правило подготовки 1", rule.getHeaderRule());
        assertEquals("Текст правила подготовки к категории", rule.getDescriptionRule());
    }
}