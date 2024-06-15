package admin.test;

import admin.data.DataConfig;
import admin.pages.BasePage.BasePage;
import admin.pages.HeaderMenu.HeaderMenu;
import admin.pages.ServicesPage.AddRuleWindow;
import admin.pages.ServicesPage.EditRuleWindow;
import admin.pages.ServicesPage.RulesPreparingWindow;
import admin.pages.ServicesPage.ServicesPage;
import admin.utils.dbUtils.DataBaseQuery;
import admin.utils.dbUtils.dbaseData.ServiceCategories;
import admin.utils.preparationDataTests.general.NotificationDecorator;
import admin.utils.preparationDataTests.services.AddDeleteRuleDecorator;
import admin.utils.preparationDataTests.services.DeleteRuleDecorator;
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
public class ServicesPageTest extends BaseTest {

    private ServicesPage servicesPage;
    private BasePage basePage;

    @ExtendWith(AllureDecorator.class)

    @BeforeAll
    static void setUpAuth() {
        BrowserManager.authGetCookie(DataConfig.UserData.getLoginAdmin(), DataConfig.UserData.getPasswordAdmin());
    }

    @BeforeEach
    void setUp() {
        BrowserManager.openPagesAfterAuth();
        servicesPage = new ServicesPage();
        basePage = new BasePage();
        HeaderMenu headerMenu = new HeaderMenu();
        headerMenu.servicesTabOpen();
    }

    @Feature("Управление правилами подготовки")
    @Story("Добавление правила подоготовки к категории")
    @ExtendWith(DeleteRuleDecorator.class)
    @Test
    void addRulePreparingCategory() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory();
        rulePreparingWindow.rulesPreparingWindow();
        AddRuleWindow addRuleWindow = rulePreparingWindow.openAddRulesWindow();
        addRuleWindow.addRuleWindow();
        addRuleWindow.fillFieldTitle(DataConfig.DataTest.getRuleTitle());
        addRuleWindow.fillFieldDescription(DataConfig.DataTest.getRuleDescription());
        addRuleWindow.clickSaveButton();
        Selenide.sleep(3000);
        assertTrue(rulePreparingWindow.isExistRule());
        EditRuleWindow rule = rulePreparingWindow.openEditRuleWindow();
        ServiceCategories preparingDescription = DataBaseQuery.selectRulesPreparing();
        assertEquals(DataConfig.DataTest.getRuleTitle(), rule.getTitleRule());
        assertEquals(DataConfig.DataTest.getRuleDescription(), rule.getDescriptionRule());
        assertEquals(DataConfig.DataTest.getRuleTitle(), preparingDescription.getTitle());
        assertEquals(DataConfig.DataTest.getRuleDescription(), preparingDescription.getDescription());
        assertFalse(addRuleWindow.isWindowAppear());
        assertTrue(rulePreparingWindow.isWindowAppear());
    }

    @Feature("Управление правилами подготовки")
    @Story("Добавление правила подоготовки к категории с пустым полем заголовка")
    @ExtendWith({DeleteRuleDecorator.class, NotificationDecorator.class})
    @Test
    void addRulePreparingCategoryEmptyFieldTitle() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory();
        AddRuleWindow addRuleWindow = rulePreparingWindow.openAddRulesWindow();
        addRuleWindow.fillFieldDescription(DataConfig.DataTest.getRuleDescription());
        addRuleWindow.clickSaveButton();
        assertEquals("Неверный запрос (400)", servicesPage.getNotification());
        assertEquals("[]", DataBaseQuery.selectRulesPreparing().getPreparing_description());
        assertFalse(rulePreparingWindow.isExistRule());
    }

    @Feature("Управление правилами подготовки")
    @Story("Добавление правила подоготовки к категории с пустым полем описания")
    @ExtendWith({DeleteRuleDecorator.class, NotificationDecorator.class})
    @Test
    void addRulePreparingCategoryEmptyFieldDescription() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory();
        AddRuleWindow addRuleWindow = rulePreparingWindow.openAddRulesWindow();
        addRuleWindow.fillFieldTitle(DataConfig.DataTest.getRuleTitle());
        addRuleWindow.clickSaveButton();
        assertEquals("Неверный запрос (400)", servicesPage.getNotification());
        assertEquals("[]", DataBaseQuery.selectRulesPreparing().getPreparing_description());
        assertFalse(rulePreparingWindow.isExistRule());
    }

    @Feature("Управление правилами подготовки")
    @Story("Добавление правила подоготовки к категории с пустыми полями заголовка и описания")
    @ExtendWith({DeleteRuleDecorator.class, NotificationDecorator.class})
    @Test
    void addRulePreparingCategoryEmptyFieldTitleDescription() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory();
        AddRuleWindow addRuleWindow = rulePreparingWindow.openAddRulesWindow();
        addRuleWindow.clickSaveButton();
        assertEquals("Неверный запрос (400)", servicesPage.getNotification());
        assertEquals("[]", DataBaseQuery.selectRulesPreparing().getPreparing_description());
        assertFalse(rulePreparingWindow.isExistRule());
    }

    @Feature("Управление правилами подготовки")
    @Story("Зануление полей в окне добавления правила подоготовки после закрытия окна")
    @ExtendWith(DeleteRuleDecorator.class)
    @Test
    void closeWindowAddRulePreparingCategory() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory();
        AddRuleWindow addRuleWindow = rulePreparingWindow.openAddRulesWindow();
        addRuleWindow.fillFieldTitle(DataConfig.DataTest.getRuleTitle());
        addRuleWindow.fillFieldDescription(DataConfig.DataTest.getRuleDescription());
        addRuleWindow.closeWindowAddRule();
        assertFalse(addRuleWindow.isWindowAppear());
        assertFalse(rulePreparingWindow.isWindowAppear());
        servicesPage.openRulesPreparingCategory();
        rulePreparingWindow.openAddRulesWindow();
        assertEquals("", addRuleWindow.getValueTitleField());
        assertEquals("", addRuleWindow.getValueDescriptionField());
    }

    @Feature("Управление правилами подготовки")
    @Story("Отмена добавления правила подоготовки после возврата к списку правил")
    @ExtendWith(DeleteRuleDecorator.class)
    @Test
    void comebackRulesListFromWindowAddRulePreparingCategory() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory();
        AddRuleWindow addRuleWindow = rulePreparingWindow.openAddRulesWindow();
        addRuleWindow.fillFieldTitle(DataConfig.DataTest.getRuleTitle());
        addRuleWindow.fillFieldDescription(DataConfig.DataTest.getRuleDescription());
        addRuleWindow.returnRulesList();
        assertFalse(addRuleWindow.isWindowAppear());
        assertTrue(rulePreparingWindow.isWindowAppear());
        rulePreparingWindow.openAddRulesWindow();
        assertEquals("", addRuleWindow.getValueTitleField());
        assertEquals("", addRuleWindow.getValueDescriptionField());
    }

    @Feature("Управление правилами подготовки")
    @Story("Редактирование правила подоготовки к категории")
    @ExtendWith(AddDeleteRuleDecorator.class)
    @Test
    void editRulePreparingCategory() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory();
        EditRuleWindow editRuleWindow = rulePreparingWindow.openEditRuleWindow();
        editRuleWindow.editRuleWindow();
        editRuleWindow.fillFieldTitle(DataConfig.DataTest.getNewRuleTitle());
        editRuleWindow.fillFieldDescription(DataConfig.DataTest.getNewRuleDescription());
        editRuleWindow.changeRules();
        Selenide.sleep(3000);
        assertFalse(editRuleWindow.isWindowAppear());
        assertTrue(rulePreparingWindow.isWindowAppear());
        rulePreparingWindow.openEditRuleWindow();
        ServiceCategories preparingDescription = DataBaseQuery.selectRulesPreparing();
        assertEquals(DataConfig.DataTest.getNewRuleTitle(), editRuleWindow.getTitleRule());
        assertEquals(DataConfig.DataTest.getNewRuleDescription(), editRuleWindow.getDescriptionRule());
        assertEquals(DataConfig.DataTest.getNewRuleTitle(), preparingDescription.getTitle());
        assertEquals(DataConfig.DataTest.getNewRuleDescription(), preparingDescription.getDescription());
    }


        @Story("Смена последовательности категорий")
        @Test
        void sequenceChangeQuestion () {

            int sequenceFirstCategory = servicesPage.getCategoryIndexByName("Телемедицина");
            int sequenceSecondCategory = servicesPage.getCategoryIndexByName("Стоматология");
            servicesPage.sequenceChangeCategory("Телемедицина", "Стоматология");
            int sequenceFirstCategory1 = servicesPage.getCategoryIndexByName("Стоматология");
            int sequenceSecondCategory1 = servicesPage.getCategoryIndexByName("Телемедицина");
            Assertions.assertEquals(sequenceFirstCategory, sequenceFirstCategory1);
            Assertions.assertEquals(sequenceSecondCategory, sequenceSecondCategory1);
        }

    }