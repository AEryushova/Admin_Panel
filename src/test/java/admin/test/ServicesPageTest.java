package admin.test;

import admin.data.DataConfig;
import admin.pages.BasePage.BasePage;
import admin.pages.HeaderMenu.HeaderMenu;
import admin.pages.ServicesPage.AddRuleWindow;
import admin.pages.ServicesPage.Rule;
import admin.pages.ServicesPage.RulesPreparingWindow;
import admin.pages.ServicesPage.ServicesPage;
import admin.utils.dbUtils.DataBaseQuery;
import admin.utils.dbUtils.dbaseData.ServiceCategories;
import admin.utils.testUtils.*;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import admin.utils.preparationDataTests.general.AllureDecorator;

import static org.junit.jupiter.api.Assertions.*;


@Epic("Услуги")
@Feature("Управление услугами")
public class ServicesPageTest extends BaseTest {

    private ServicesPage servicesPage;
    private HeaderMenu headerMenu;
    private BasePage basePage;

    @ExtendWith(AllureDecorator.class)

    @BeforeAll
    static void setUpAuth() {
        BrowserManager.authGetCookie(DataConfig.UserData.getLoginAdmin(), DataConfig.UserData.getPasswordAdmin());
    }

    @BeforeEach
    void setUp(){
        BrowserManager.openPagesAfterAuth();
        servicesPage=new ServicesPage();
        headerMenu= new HeaderMenu();
        basePage = new BasePage();
        headerMenu.servicesTabOpen();
    }


    @Story("Добавление правила подоготовки к категории")
    @Test
    void addRulePreparingCategory() {
        servicesPage.servicesPage();
        RulesPreparingWindow rulePreparingWindow=servicesPage.openRulesPreparingCategory();
        rulePreparingWindow.rulesPreparingWindow();
        AddRuleWindow addRuleWindow = rulePreparingWindow.addRulesWindow();
        addRuleWindow.addRuleWindow();
        addRuleWindow.fillFieldHeader(DataConfig.DataTest.getRuleHeader());
        addRuleWindow.fillFieldDescription(DataConfig.DataTest.getRuleDescription());
        addRuleWindow.clickSaveButton();
        Selenide.sleep(3000);
        assertTrue(rulePreparingWindow.isExistRule());
        Rule rule = rulePreparingWindow.openRule();
        ServiceCategories preparingDescription = DataBaseQuery.selectRulesPreparing();
        assertEquals(DataConfig.DataTest.getRuleHeader(), rule.getHeaderRule());
        assertEquals(DataConfig.DataTest.getRuleDescription(), rule.getDescriptionRule());
        assertEquals(DataConfig.DataTest.getRuleHeader(), preparingDescription.getTitle());
        assertEquals(DataConfig.DataTest.getRuleDescription(), preparingDescription.getDescription());
        assertFalse(addRuleWindow.isWindowAppear());
    }


    @Story("Смена последовательности категорий")
    @Test
    void sequenceChangeQuestion() {

        int sequenceFirstCategory = servicesPage.getCategoryIndexByName("Телемедицина");
        int sequenceSecondCategory = servicesPage.getCategoryIndexByName("Стоматология");
        servicesPage.sequenceChangeCategory("Телемедицина", "Стоматология");
        int sequenceFirstCategory1 = servicesPage.getCategoryIndexByName("Стоматология");
        int sequenceSecondCategory1 = servicesPage.getCategoryIndexByName("Телемедицина");
        Assertions.assertEquals(sequenceFirstCategory, sequenceFirstCategory1);
        Assertions.assertEquals(sequenceSecondCategory, sequenceSecondCategory1);
    }

}