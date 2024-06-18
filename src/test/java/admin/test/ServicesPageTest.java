package admin.test;

import admin.data.DataConfig;
import admin.pages.BasePage.BasePage;
import admin.pages.HeaderMenu.HeaderMenu;
import admin.pages.ServicesPage.*;
import admin.utils.dbUtils.DataBaseQuery;
import admin.utils.dbUtils.dbaseData.ServiceCategories;
import admin.utils.preparationDataTests.general.NotificationDecorator;
import admin.utils.preparationDataTests.services.AddDeleteRuleDecorator;
import admin.utils.preparationDataTests.services.AddRuleDecorator;
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
    @Story("Успешное добавление правила подоготовки к категории")
    @ExtendWith(DeleteRuleDecorator.class)
    @Test
    void addRulePreparingCategory() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(DataConfig.DataTest.getCategoryName());
        rulePreparingWindow.rulesPreparingWindow();
        AddRuleWindow addRuleWindow = rulePreparingWindow.openAddRulesWindow();
        addRuleWindow.addRuleWindow();
        addRuleWindow.fillFieldTitle(DataConfig.DataTest.getRuleTitle());
        addRuleWindow.fillFieldDescription(DataConfig.DataTest.getRuleDescription());
        addRuleWindow.clickSaveButton();
        Selenide.sleep(3000);
        assertTrue(rulePreparingWindow.isExistRule());
        Rule rule = rulePreparingWindow.getRule();
        rule.rule();
        EditRuleWindow editRuleWindow=rule.openEditRuleWindow();
        ServiceCategories preparingDescription = DataBaseQuery.selectRulesPreparing();
        assertEquals(DataConfig.DataTest.getRuleTitle(), editRuleWindow.getTitleRule());
        assertEquals(DataConfig.DataTest.getRuleDescription(), editRuleWindow.getDescriptionRule());
        assertEquals(DataConfig.DataTest.getRuleTitle(), preparingDescription.getTitle());
        assertEquals(DataConfig.DataTest.getRuleDescription(), preparingDescription.getDescription());
        assertFalse(addRuleWindow.isWindowAppear());
    }

    @Feature("Управление правилами подготовки")
    @Story("Добавление правила подоготовки к категории с пустым полем заголовка")
    @ExtendWith({DeleteRuleDecorator.class, NotificationDecorator.class})
    @Test
    void addRulePreparingCategoryEmptyFieldTitle() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(DataConfig.DataTest.getCategoryName());
        AddRuleWindow addRuleWindow = rulePreparingWindow.openAddRulesWindow();
        addRuleWindow.fillFieldDescription(DataConfig.DataTest.getRuleDescription());
        addRuleWindow.clickSaveButton();
        assertEquals("Неверный запрос (400)", servicesPage.getNotification());
        assertFalse(rulePreparingWindow.isExistRule());
    }

    @Feature("Управление правилами подготовки")
    @Story("Добавление правила подоготовки к категории с пустым полем описания")
    @ExtendWith({DeleteRuleDecorator.class, NotificationDecorator.class})
    @Test
    void addRulePreparingCategoryEmptyFieldDescription() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(DataConfig.DataTest.getCategoryName());
        AddRuleWindow addRuleWindow = rulePreparingWindow.openAddRulesWindow();
        addRuleWindow.fillFieldTitle(DataConfig.DataTest.getRuleTitle());
        addRuleWindow.clickSaveButton();
        assertEquals("Неверный запрос (400)", servicesPage.getNotification());
        assertFalse(rulePreparingWindow.isExistRule());
    }

    @Feature("Управление правилами подготовки")
    @Story("Добавление правила подоготовки к категории с пустыми полями заголовка и описания")
    @ExtendWith({DeleteRuleDecorator.class, NotificationDecorator.class})
    @Test
    void addRulePreparingCategoryEmptyFieldTitleDescription() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(DataConfig.DataTest.getCategoryName());
        AddRuleWindow addRuleWindow = rulePreparingWindow.openAddRulesWindow();
        addRuleWindow.clickSaveButton();
        assertEquals("Неверный запрос (400)", servicesPage.getNotification());
        assertFalse(rulePreparingWindow.isExistRule());
    }

    @Feature("Управление правилами подготовки")
    @Story("Зануление полей в окне добавления правила подоготовки после закрытия окна")
    @ExtendWith(DeleteRuleDecorator.class)
    @Test
    void closeWindowAddRulePreparingCategory() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(DataConfig.DataTest.getCategoryName());
        AddRuleWindow addRuleWindow = rulePreparingWindow.openAddRulesWindow();
        addRuleWindow.fillFieldTitle(DataConfig.DataTest.getRuleTitle());
        addRuleWindow.fillFieldDescription(DataConfig.DataTest.getRuleDescription());
        addRuleWindow.closeWindowAddRule();
        assertFalse(addRuleWindow.isWindowAppear());
        assertFalse(rulePreparingWindow.isWindowAppear());
        servicesPage.openRulesPreparingCategory(DataConfig.DataTest.getCategoryName());
        rulePreparingWindow.openAddRulesWindow();
        assertEquals("", addRuleWindow.getValueTitleField());
        assertEquals("", addRuleWindow.getValueDescriptionField());
    }

    @Feature("Управление правилами подготовки")
    @Story("Отмена добавления правила подоготовки после возврата к списку правил")
    @ExtendWith(DeleteRuleDecorator.class)
    @Test
    void comebackRulesListFromWindowAddRulePreparingCategory() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(DataConfig.DataTest.getCategoryName());
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
    @Story("Успешное редактирование правила подоготовки к категории")
    @ExtendWith(AddDeleteRuleDecorator.class)
    @Test
    void editRulePreparingCategory() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(DataConfig.DataTest.getCategoryName());
        Rule rule=rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.openEditRuleWindow();
        editRuleWindow.editRuleWindow();
        editRuleWindow.fillFieldTitle(DataConfig.DataTest.getNewRuleTitle());
        editRuleWindow.fillFieldDescription(DataConfig.DataTest.getNewRuleDescription());
        editRuleWindow.changeRules();
        Selenide.sleep(3000);
        assertFalse(editRuleWindow.isWindowAppear());
        assertTrue(rulePreparingWindow.isWindowAppear());
        rule.openEditRuleWindow();
        ServiceCategories preparingDescription = DataBaseQuery.selectRulesPreparing();
        assertEquals(DataConfig.DataTest.getNewRuleTitle(), editRuleWindow.getTitleRule());
        assertEquals(DataConfig.DataTest.getNewRuleDescription(), editRuleWindow.getDescriptionRule());
        assertEquals(DataConfig.DataTest.getNewRuleTitle(), preparingDescription.getTitle());
        assertEquals(DataConfig.DataTest.getNewRuleDescription(), preparingDescription.getDescription());
    }

    @Feature("Управление правилами подготовки")
    @Story("Редактирование правила подоготовки к категории с пустым полем заголовка")
    @ExtendWith({AddDeleteRuleDecorator.class,NotificationDecorator.class})
    @Test
    void editRulePreparingCategoryEmptyFieldTitle() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(DataConfig.DataTest.getCategoryName());
        Rule rule=rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.openEditRuleWindow();
        editRuleWindow.clearTitleField();
        editRuleWindow.fillFieldDescription(DataConfig.DataTest.getNewRuleDescription());
        editRuleWindow.changeRules();
        assertEquals("Неверный запрос (400)", servicesPage.getNotification());
        assertTrue(editRuleWindow.isWindowAppear());
    }

    @Feature("Управление правилами подготовки")
    @Story("Редактирование правила подоготовки к категории с пустым полем описания")
    @ExtendWith({AddDeleteRuleDecorator.class,NotificationDecorator.class})
    @Test
    void editRulePreparingCategoryEmptyFieldDescription() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(DataConfig.DataTest.getCategoryName());
        Rule rule=rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.openEditRuleWindow();
        editRuleWindow.fillFieldTitle(DataConfig.DataTest.getNewRuleTitle());
        editRuleWindow.clearDescriptionField();
        editRuleWindow.changeRules();
        assertEquals("Неверный запрос (400)", servicesPage.getNotification());
        assertTrue(editRuleWindow.isWindowAppear());
    }

    @Feature("Управление правилами подготовки")
    @Story("Редактирование правила подоготовки к категории с пустыми полями")
    @ExtendWith({AddDeleteRuleDecorator.class,NotificationDecorator.class})
    @Test
    void editRulePreparingCategoryEmptyFields() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(DataConfig.DataTest.getCategoryName());
        Rule rule=rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.openEditRuleWindow();
        editRuleWindow.clearTitleField();
        editRuleWindow.clearDescriptionField();
        editRuleWindow.changeRules();
        assertEquals("Неверный запрос (400)", servicesPage.getNotification());
        assertTrue(editRuleWindow.isWindowAppear());
    }

    @Feature("Управление правилами подготовки")
    @Story("Сохранение правила подоготовки к категории без изменений")
    @ExtendWith(AddDeleteRuleDecorator.class)
    @Test
    void editRulePreparingCategoryNotChangeSave() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(DataConfig.DataTest.getCategoryName());
        Rule rule=rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.openEditRuleWindow();
        editRuleWindow.changeRules();
        rule.openEditRuleWindow();
        ServiceCategories preparingDescription = DataBaseQuery.selectRulesPreparing();
        assertEquals(DataConfig.DataTest.getRuleTitle(), editRuleWindow.getTitleRule());
        assertEquals(DataConfig.DataTest.getRuleDescription(), editRuleWindow.getDescriptionRule());
        assertEquals(DataConfig.DataTest.getRuleTitle(), preparingDescription.getTitle());
        assertEquals(DataConfig.DataTest.getRuleDescription(), preparingDescription.getDescription());
    }

    @Feature("Управление правилами подготовки")
    @Story("Сохранение значений в окне редактирования правила подоготовки к категории после закрытия окна")
    @ExtendWith(AddDeleteRuleDecorator.class)
    @Test
    void closeWindowEditRulePreparingCategory() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(DataConfig.DataTest.getCategoryName());
        Rule rule=rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.openEditRuleWindow();
        editRuleWindow.fillFieldTitle(DataConfig.DataTest.getNewRuleTitle());
        editRuleWindow.fillFieldDescription(DataConfig.DataTest.getNewRuleDescription());
        editRuleWindow.closeWindowRule();
        Selenide.sleep(1000);
        assertFalse(editRuleWindow.isWindowAppear());
        assertTrue(rulePreparingWindow.isWindowAppear());
        rule.openEditRuleWindow();
        ServiceCategories preparingDescription = DataBaseQuery.selectRulesPreparing();
        assertEquals(DataConfig.DataTest.getRuleTitle(), editRuleWindow.getTitleRule());
        assertEquals(DataConfig.DataTest.getRuleDescription(), editRuleWindow.getDescriptionRule());
        assertEquals(DataConfig.DataTest.getRuleTitle(), preparingDescription.getTitle());
        assertEquals(DataConfig.DataTest.getRuleDescription(), preparingDescription.getDescription());
    }

    @Feature("Управление правилами подготовки")
    @Story("Успешное удаление правила подоготовки к категории")
    @ExtendWith(AddRuleDecorator.class)
    @Test
    void deleteRulePreparingCategory() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(DataConfig.DataTest.getCategoryName());
        Rule rule=rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.openEditRuleWindow();
        editRuleWindow.deleteRules();
        Selenide.sleep(2000);
        assertFalse(editRuleWindow.isWindowAppear());
        assertTrue(rulePreparingWindow.isWindowAppear());
        assertFalse(rulePreparingWindow.isExistRule());
        assertTrue(rulePreparingWindow.isExistsEmptyListRules());
        assertEquals("[]", DataBaseQuery.selectRulesPreparing().getPreparing_description());
    }

    @Feature("Управление правилами подготовки")
    @Story("Успешное удаление всех правил подготовки к категории")
    @ExtendWith(AddRuleDecorator.class)
    @Test
    void deleteAllRulePreparingCategory() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(DataConfig.DataTest.getCategoryName());
        rulePreparingWindow.deleteAllRules();
        Selenide.sleep(2000);
        assertFalse(rulePreparingWindow.isExistRule());
        assertTrue(rulePreparingWindow.isExistsEmptyListRules());
        assertEquals("[]", DataBaseQuery.selectRulesPreparing().getPreparing_description());
    }

    @Feature("Управление правилами подготовки")
    @Story("Закрытие окна правил подготовки к категории")
    @Test
    void closeWindowRulePreparingCategory() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(DataConfig.DataTest.getCategoryName());
        rulePreparingWindow.closeWindowRulesPreparing();
        Selenide.sleep(2000);
        assertFalse(rulePreparingWindow.isWindowAppear());
    }

    @Feature("Управление правилами подготовки")
    @Story("Открытие правил подготовки к категории Иные услуги")
    @ExtendWith(NotificationDecorator.class)
    @Test
    void openRulePreparingCategoryOtherServices() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory("Иные услуги");
        assertFalse(rulePreparingWindow.isWindowAppear());
        assertEquals("Категория не найдена", servicesPage.getNotification());
    }

}

/*
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

 */