package admin.test;

import admin.data.DataInfo;
import admin.pages.HeaderMenu.HeaderMenu;
import admin.pages.ServicesPage.ServicesPage;
import admin.pages.ServicesPage.AddRuleWindow;
import admin.pages.ServicesPage.RulesPreparingWindow;
import admin.utils.decoratorsTest.*;
import admin.utils.testUtils.*;
import com.codeborne.selenide.Configuration;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
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

    private ServicesPage servicesPage;
    private HeaderMenu headerMenu;

    @ExtendWith(AllureDecorator.class)

    @BeforeAll
    static void setUpAuth() {
        BrowserManager.authGetCookie(DataInfo.UserData.getLoginAdmin(), DataInfo.UserData.getPasswordAdmin());
    }

    @BeforeEach
    void setUp(){
        BrowserManager.openPagesAfterAuth();
        servicesPage=new ServicesPage();
        headerMenu= new HeaderMenu();
        headerMenu.servicesTabOpen();
    }


    @Story("Смена последовательности категорий")
    @Test
    void sequenceChangeQuestion() throws InterruptedException {
        servicesPage = headerMenu.servicesTabOpen();
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
        servicesPage = headerMenu.servicesTabOpen();
        servicesPage.servicesPage();
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparing();
        rulePreparingWindow.rulesPreparingWindow();
        AddRuleWindow addRuleWindow = rulePreparingWindow.addRulesWindow();
        addRuleWindow.addRuleWindow();
        addRuleWindow.fillFieldHeader("Правило подготовки 1");
        addRuleWindow.fillFieldDescription("Текст правила подготовки к категории");
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