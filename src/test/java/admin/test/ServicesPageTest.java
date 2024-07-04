package admin.test;

import admin.pages.BasePage.BasePage;
import admin.pages.HeaderMenu.HeaderMenu;
import admin.pages.ServicesPage.*;
import admin.utils.dbUtils.DataBaseQuery;
import admin.utils.dbUtils.dbaseData.PreparingDescriptions;
import admin.utils.dbUtils.dbaseData.ServiceCategories;
import admin.utils.preparationDataTests.services.*;
import admin.utils.testUtils.*;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import admin.utils.preparationDataTests.general.AllureDecorator;


import static admin.data.TestData.DataTest.*;
import static admin.data.TestData.UserData.*;
import static org.junit.jupiter.api.Assertions.*;


@Epic("Услуги")
@DisplayName("Страница Услуги")
public class ServicesPageTest extends BaseTest {

    private ServicesPage servicesPage;
    private BasePage basePage;

    @ExtendWith(AllureDecorator.class)

    @BeforeAll
    static void setUpAuth() {
        BrowserManager.openAdminPanel(LOGIN_ADMIN, PASSWORD_ADMIN);
        HeaderMenu headerMenu = new HeaderMenu();
        headerMenu.servicesTabOpen();
    }

    @BeforeEach
    void setUp() {
        Selenide.refresh();
        servicesPage = new ServicesPage();
        basePage = new BasePage();
    }

    @AfterAll
    static void closeWebDriver() {
        Selenide.closeWebDriver();
    }

    @Feature("Управление правилами подготовки")
    @Story("Успешное добавление правила подготовки к категории")
    @DisplayName("Успешное добавление правила подготовки к категории")
    @ExtendWith(DeleteRuleCategoryDecorator.class)
    @Test
    void addRulePreparingCategory() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(CATEGORY_RULES);
        rulePreparingWindow.rulesPreparingWindow();
        AddRuleWindow addRuleWindow = rulePreparingWindow.openAddRulesWindow();
        addRuleWindow.addRuleWindow();
        addRuleWindow.fillFieldTitle(RULE_TITLE);
        addRuleWindow.fillFieldDescription(RULE_DESCRIPTION);
        addRuleWindow.clickSaveButton();
        Selenide.sleep(2000);
        assertTrue(rulePreparingWindow.isExistRule());
        Rule rule = rulePreparingWindow.getRule();
        rule.rule();
        EditRuleWindow editRuleWindow = rule.openEditRuleWindow();
        ServiceCategories preparingDescription = DataBaseQuery.selectServicesCategories(CATEGORY_RULES);
        assertEquals(RULE_TITLE, editRuleWindow.getTitleRule());
        assertEquals(RULE_DESCRIPTION, editRuleWindow.getDescriptionRule());
        assertEquals(RULE_TITLE, preparingDescription.getTitle());
        assertEquals(RULE_DESCRIPTION, preparingDescription.getDescription());
        assertFalse(addRuleWindow.isWindowAppear());
    }

    @Feature("Управление правилами подготовки")
    @Story("Добавление правила подготовки к категории с пустым полем заголовка")
    @DisplayName("Добавление правила подготовки к категории с пустым полем заголовка")
    @ExtendWith(DeleteRuleCategoryDecorator.class)
    @Test
    void addRulePreparingCategoryEmptyFieldTitle() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(CATEGORY_RULES);
        AddRuleWindow addRuleWindow = rulePreparingWindow.openAddRulesWindow();
        addRuleWindow.fillFieldDescription(RULE_DESCRIPTION);
        addRuleWindow.clickSaveButton();
        assertEquals("Неверный запрос (400)", servicesPage.getNotification());
        assertFalse(rulePreparingWindow.isExistRule());
    }

    @Feature("Управление правилами подготовки")
    @Story("Добавление правила подготовки к категории с пустым полем описания")
    @DisplayName("Добавление правила подготовки к категории с пустым полем описания")
    @ExtendWith(DeleteRuleCategoryDecorator.class)
    @Test
    void addRulePreparingCategoryEmptyFieldDescription() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(CATEGORY_RULES);
        AddRuleWindow addRuleWindow = rulePreparingWindow.openAddRulesWindow();
        addRuleWindow.fillFieldTitle(RULE_TITLE);
        addRuleWindow.clickSaveButton();
        assertEquals("Неверный запрос (400)", servicesPage.getNotification());
        assertFalse(rulePreparingWindow.isExistRule());
    }

    @Feature("Управление правилами подготовки")
    @Story("Добавление правила подготовки к категории с пустыми полями заголовка и описания")
    @DisplayName("Добавление правила подготовки к категории с пустыми полями заголовка и описания")
    @ExtendWith(DeleteRuleCategoryDecorator.class)
    @Test
    void addRulePreparingCategoryEmptyFieldTitleDescription() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(CATEGORY_RULES);
        AddRuleWindow addRuleWindow = rulePreparingWindow.openAddRulesWindow();
        addRuleWindow.clickSaveButton();
        assertEquals("Неверный запрос (400)", servicesPage.getNotification());
        assertFalse(rulePreparingWindow.isExistRule());
    }

    @Feature("Управление правилами подготовки")
    @Story("Сброс значений полей в окне добавления правила подготовки к категории при закрытии окна")
    @DisplayName("Сброс значений полей в окне добавления правила подготовки к категории при закрытии окна")
    @ExtendWith(DeleteRuleCategoryDecorator.class)
    @Test
    void closeWindowAddRulePreparingCategory() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(CATEGORY_RULES);
        AddRuleWindow addRuleWindow = rulePreparingWindow.openAddRulesWindow();
        addRuleWindow.fillFieldTitle(RULE_TITLE);
        addRuleWindow.fillFieldDescription(RULE_DESCRIPTION);
        addRuleWindow.closeWindowAddRule();
        assertFalse(addRuleWindow.isWindowAppear());
        assertFalse(rulePreparingWindow.isWindowAppear());
        servicesPage.openRulesPreparingCategory(CATEGORY_RULES);
        rulePreparingWindow.openAddRulesWindow();
        assertEquals("", addRuleWindow.getValueTitleField());
        assertEquals("", addRuleWindow.getValueDescriptionField());
    }

    @Feature("Управление правилами подготовки")
    @Story("Отмена добавления правила подготовки после возврата к списку правил")
    @DisplayName("Отмена добавления правила подготовки после возврата к списку правил")
    @ExtendWith(DeleteRuleCategoryDecorator.class)
    @Test
    void comebackRulesListFromWindowAddRulePreparingCategory() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(CATEGORY_RULES);
        AddRuleWindow addRuleWindow = rulePreparingWindow.openAddRulesWindow();
        addRuleWindow.fillFieldTitle(RULE_TITLE);
        addRuleWindow.fillFieldDescription(RULE_DESCRIPTION);
        addRuleWindow.returnRulesList();
        assertFalse(addRuleWindow.isWindowAppear());
        assertTrue(rulePreparingWindow.isWindowAppear());
        rulePreparingWindow.openAddRulesWindow();
        assertEquals("", addRuleWindow.getValueTitleField());
        assertEquals("", addRuleWindow.getValueDescriptionField());
    }

    @Feature("Управление правилами подготовки")
    @Story("Успешное редактирование правила подготовки к категории")
    @DisplayName("Успешное редактирование правила подготовки к категории")
    @ExtendWith(AddDeleteCategoryRuleDecorator.class)
    @Test
    void editRulePreparingCategory() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(CATEGORY_RULES);
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.openEditRuleWindow();
        editRuleWindow.editRuleWindow();
        editRuleWindow.fillFieldTitle(NEW_RULE_TITLE);
        editRuleWindow.fillFieldDescription(NEW_RULE_DESCRIPTION);
        editRuleWindow.changeRules();
        Selenide.sleep(2000);
        assertFalse(editRuleWindow.isWindowAppear());
        assertTrue(rulePreparingWindow.isWindowAppear());
        rule.openEditRuleWindow();
        ServiceCategories preparingDescription = DataBaseQuery.selectServicesCategories(CATEGORY_RULES);
        assertEquals(NEW_RULE_TITLE, editRuleWindow.getTitleRule());
        assertEquals(NEW_RULE_DESCRIPTION, editRuleWindow.getDescriptionRule());
        assertEquals(NEW_RULE_TITLE, preparingDescription.getTitle());
        assertEquals(NEW_RULE_DESCRIPTION, preparingDescription.getDescription());
    }

    @Feature("Управление правилами подготовки")
    @Story("Редактирование правила подготовки к категории с пустым полем заголовка")
    @DisplayName("Редактирование правила подготовки к категории с пустым полем заголовка")
    @ExtendWith(AddDeleteCategoryRuleDecorator.class)
    @Test
    void editRulePreparingCategoryEmptyFieldTitle() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(CATEGORY_RULES);
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.openEditRuleWindow();
        editRuleWindow.clearTitleField();
        editRuleWindow.fillFieldDescription(NEW_RULE_DESCRIPTION);
        editRuleWindow.changeRules();
        assertEquals("Неверный запрос (400)", servicesPage.getNotification());
        assertTrue(editRuleWindow.isWindowAppear());
    }

    @Feature("Управление правилами подготовки")
    @Story("Редактирование правила подготовки к категории с пустым полем описания")
    @DisplayName("Редактирование правила подготовки к категории с пустым полем описания")
    @ExtendWith(AddDeleteCategoryRuleDecorator.class)
    @Test
    void editRulePreparingCategoryEmptyFieldDescription() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(CATEGORY_RULES);
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.openEditRuleWindow();
        editRuleWindow.fillFieldTitle(NEW_RULE_TITLE);
        editRuleWindow.clearDescriptionField();
        editRuleWindow.changeRules();
        assertEquals("Неверный запрос (400)", servicesPage.getNotification());
        assertTrue(editRuleWindow.isWindowAppear());
    }

    @Feature("Управление правилами подготовки")
    @Story("Редактирование правила подготовки к категории с пустыми полями")
    @DisplayName("Редактирование правила подготовки к категории с пустыми полями")
    @ExtendWith(AddDeleteCategoryRuleDecorator.class)
    @Test
    void editRulePreparingCategoryEmptyFields() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(CATEGORY_RULES);
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.openEditRuleWindow();
        editRuleWindow.clearTitleField();
        editRuleWindow.clearDescriptionField();
        editRuleWindow.changeRules();
        assertEquals("Неверный запрос (400)", servicesPage.getNotification());
        assertTrue(editRuleWindow.isWindowAppear());
    }

    @Feature("Управление правилами подготовки")
    @Story("Сохранение правила подготовки к категории без изменений")
    @DisplayName("Сохранение правила подготовки к категории без изменений")
    @ExtendWith(AddDeleteCategoryRuleDecorator.class)
    @Test
    void editRulePreparingCategoryNotChangeSave() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(CATEGORY_RULES);
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.openEditRuleWindow();
        editRuleWindow.changeRules();
        rule.openEditRuleWindow();
        ServiceCategories preparingDescription = DataBaseQuery.selectServicesCategories(CATEGORY_RULES);
        assertEquals(RULE_TITLE, editRuleWindow.getTitleRule());
        assertEquals(RULE_DESCRIPTION, editRuleWindow.getDescriptionRule());
        assertEquals(RULE_TITLE, preparingDescription.getTitle());
        assertEquals(RULE_DESCRIPTION, preparingDescription.getDescription());
    }

    @Feature("Управление правилами подготовки")
    @Story("Сохранение значений в окне редактирования правила подготовки к категории после закрытия окна")
    @DisplayName("Сохранение значений в окне редактирования правила подготовки к категории после закрытия окна")
    @ExtendWith(AddDeleteCategoryRuleDecorator.class)
    @Test
    void closeWindowEditRulePreparingCategory() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(CATEGORY_RULES);
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.openEditRuleWindow();
        editRuleWindow.fillFieldTitle(NEW_RULE_TITLE);
        editRuleWindow.fillFieldDescription(NEW_RULE_DESCRIPTION);
        editRuleWindow.closeWindowRule();
        Selenide.sleep(2000);
        assertFalse(editRuleWindow.isWindowAppear());
        assertTrue(rulePreparingWindow.isWindowAppear());
        rule.openEditRuleWindow();
        ServiceCategories preparingDescription = DataBaseQuery.selectServicesCategories(CATEGORY_RULES);
        assertEquals(RULE_TITLE, editRuleWindow.getTitleRule());
        assertEquals(RULE_DESCRIPTION, editRuleWindow.getDescriptionRule());
        assertEquals(RULE_TITLE, preparingDescription.getTitle());
        assertEquals(RULE_DESCRIPTION, preparingDescription.getDescription());
    }

    @Feature("Управление правилами подготовки")
    @Story("Успешное удаление правила подготовки к категории")
    @DisplayName("Успешное удаление правила подготовки к категории")
    @ExtendWith(AddRuleCategoryDecorator.class)
    @Test
    void deleteRulePreparingCategory() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(CATEGORY_RULES);
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.openEditRuleWindow();
        editRuleWindow.deleteRules();
        Selenide.sleep(2000);
        assertFalse(editRuleWindow.isWindowAppear());
        assertTrue(rulePreparingWindow.isWindowAppear());
        assertFalse(rulePreparingWindow.isExistRule());
        assertTrue(rulePreparingWindow.isExistsEmptyListRules());
        assertEquals("[]", DataBaseQuery.selectServicesCategories(CATEGORY_RULES).getPreparing_description());
    }

    @Feature("Управление правилами подготовки")
    @Story("Успешное удаление всех правил подготовки к категории")
    @DisplayName("Успешное удаление всех правил подготовки к категории")
    @ExtendWith(AddRuleCategoryDecorator.class)
    @Test
    void deleteAllRulePreparingCategory() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(CATEGORY_RULES);
        rulePreparingWindow.deleteAllRules();
        Selenide.sleep(2000);
        assertFalse(rulePreparingWindow.isExistRule());
        assertTrue(rulePreparingWindow.isExistsEmptyListRules());
        assertEquals("[]", DataBaseQuery.selectServicesCategories(CATEGORY_RULES).getPreparing_description());
    }

    @Feature("Управление правилами подготовки")
    @Story("Успешное добавление правила подготовки к разделу")
    @DisplayName("Успешное добавление правила подготовки к разделу")
    @ExtendWith(AddDeleteSectionDecorator.class)
    @Test
    void addRulePreparingSection() {
        CategoryCard categoryCard = servicesPage.openCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        RulesPreparingWindow rulePreparingWindow = sectionCard.openRulesPreparingSection();
        AddRuleWindow addRuleWindow = rulePreparingWindow.openAddRulesWindow();
        addRuleWindow.fillFieldTitle(RULE_TITLE);
        addRuleWindow.fillFieldDescription(RULE_DESCRIPTION);
        addRuleWindow.clickSaveButton();
        Selenide.sleep(2000);
        assertTrue(rulePreparingWindow.isExistRule());
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.openEditRuleWindow();
        ServiceCategories preparingDescription = DataBaseQuery.selectServicesCategories(NAME_SECTION);
        assertEquals(RULE_TITLE, editRuleWindow.getTitleRule());
        assertEquals(RULE_DESCRIPTION, editRuleWindow.getDescriptionRule());
        assertEquals(RULE_TITLE, preparingDescription.getTitle());
        assertEquals(RULE_DESCRIPTION, preparingDescription.getDescription());
        assertFalse(addRuleWindow.isWindowAppear());
    }

    @Feature("Управление правилами подготовки")
    @Story("Успешное редактирование правила подготовки к разделу")
    @DisplayName("Успешное редактирование правила подготовки к разделу")
    @ExtendWith(AddDeleteSectionRuleDecorator.class)
    @Test
    void editRulePreparingSection() {
        CategoryCard categoryCard = servicesPage.openCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        RulesPreparingWindow rulePreparingWindow = sectionCard.openRulesPreparingSection();
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.openEditRuleWindow();
        editRuleWindow.editRuleWindow();
        editRuleWindow.fillFieldTitle(NEW_RULE_TITLE);
        editRuleWindow.fillFieldDescription(NEW_RULE_DESCRIPTION);
        editRuleWindow.changeRules();
        Selenide.sleep(2000);
        assertFalse(editRuleWindow.isWindowAppear());
        assertTrue(rulePreparingWindow.isWindowAppear());
        rule.openEditRuleWindow();
        ServiceCategories preparingDescription = DataBaseQuery.selectServicesCategories(NAME_SECTION);
        assertEquals(NEW_RULE_TITLE, editRuleWindow.getTitleRule());
        assertEquals(NEW_RULE_DESCRIPTION, editRuleWindow.getDescriptionRule());
        assertEquals(NEW_RULE_TITLE, preparingDescription.getTitle());
        assertEquals(NEW_RULE_DESCRIPTION, preparingDescription.getDescription());
    }

    @Feature("Управление правилами подготовки")
    @Story("Успешное удаление правила подготовки к разделу")
    @DisplayName("Успешное удаление правила подготовки к разделу")
    @ExtendWith(AddDeleteSectionRuleDecorator.class)
    @Test
    void deleteRulePreparingSection() {
        CategoryCard categoryCard = servicesPage.openCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        RulesPreparingWindow rulePreparingWindow = sectionCard.openRulesPreparingSection();
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.openEditRuleWindow();
        editRuleWindow.deleteRules();
        Selenide.sleep(2000);
        assertFalse(editRuleWindow.isWindowAppear());
        assertTrue(rulePreparingWindow.isWindowAppear());
        assertFalse(rulePreparingWindow.isExistRule());
        assertTrue(rulePreparingWindow.isExistsEmptyListRules());
        assertEquals("[]", DataBaseQuery.selectServicesCategories(NAME_SECTION).getPreparing_description());
    }

    @Feature("Управление правилами подготовки")
    @Story("Успешное удаление всех правил подготовки к разделу")
    @DisplayName("Успешное удаление всех правил подготовки к разделу")
    @ExtendWith(AddDeleteSectionRuleDecorator.class)
    @Test
    void deleteAllRulePreparingSection() {
        CategoryCard categoryCard = servicesPage.openCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        RulesPreparingWindow rulePreparingWindow = sectionCard.openRulesPreparingSection();
        rulePreparingWindow.deleteAllRules();
        Selenide.sleep(2000);
        assertFalse(rulePreparingWindow.isExistRule());
        assertTrue(rulePreparingWindow.isExistsEmptyListRules());
        assertEquals("[]", DataBaseQuery.selectServicesCategories(NAME_SECTION).getPreparing_description());
    }

    @Feature("Управление правилами подготовки")
    @Story("Успешное добавление правила подготовки к подразделу")
    @DisplayName("Успешное добавление правила подготовки к подразделу")
    @ExtendWith(AddDeleteSubsectionDecorator.class)
    @Test
    void addRulePreparingSubsection() {
        CategoryCard categoryCard = servicesPage.openCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.openSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        RulesPreparingWindow rulePreparingWindow = subsectionCard.openRulesPreparingSubsection();
        AddRuleWindow addRuleWindow = rulePreparingWindow.openAddRulesWindow();
        addRuleWindow.fillFieldTitle(RULE_TITLE);
        addRuleWindow.fillFieldDescription(RULE_DESCRIPTION);
        addRuleWindow.clickSaveButton();
        Selenide.sleep(2000);
        assertTrue(rulePreparingWindow.isExistRule());
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.openEditRuleWindow();
        ServiceCategories preparingDescription = DataBaseQuery.selectServicesCategories(NAME_SUBSECTION);
        assertEquals(RULE_TITLE, editRuleWindow.getTitleRule());
        assertEquals(RULE_DESCRIPTION, editRuleWindow.getDescriptionRule());
        assertEquals(RULE_TITLE, preparingDescription.getTitle());
        assertEquals(RULE_DESCRIPTION, preparingDescription.getDescription());
        assertFalse(addRuleWindow.isWindowAppear());
    }

    @Feature("Управление правилами подготовки")
    @Story("Успешное редактирование правила подготовки к подразделу")
    @DisplayName("Успешное редактирование правила подготовки к подразделу")
    @ExtendWith(AddDeleteSubsectionRuleDecorator.class)
    @Test
    void editRulePreparingSubsection() {
        CategoryCard categoryCard = servicesPage.openCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.openSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        RulesPreparingWindow rulePreparingWindow = subsectionCard.openRulesPreparingSubsection();
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.openEditRuleWindow();
        editRuleWindow.editRuleWindow();
        editRuleWindow.fillFieldTitle(NEW_RULE_TITLE);
        editRuleWindow.fillFieldDescription(NEW_RULE_DESCRIPTION);
        editRuleWindow.changeRules();
        Selenide.sleep(2000);
        assertFalse(editRuleWindow.isWindowAppear());
        assertTrue(rulePreparingWindow.isWindowAppear());
        rule.openEditRuleWindow();
        ServiceCategories preparingDescription = DataBaseQuery.selectServicesCategories(NAME_SUBSECTION);
        assertEquals(NEW_RULE_TITLE, editRuleWindow.getTitleRule());
        assertEquals(NEW_RULE_DESCRIPTION, editRuleWindow.getDescriptionRule());
        assertEquals(NEW_RULE_TITLE, preparingDescription.getTitle());
        assertEquals(NEW_RULE_DESCRIPTION, preparingDescription.getDescription());
    }

    @Feature("Управление правилами подготовки")
    @Story("Успешное удаление правила подготовки к подразделу")
    @DisplayName("Успешное удаление правила подготовки к подразделу")
    @ExtendWith(AddDeleteSubsectionRuleDecorator.class)
    @Test
    void deleteRulePreparingSubsection() {
        CategoryCard categoryCard = servicesPage.openCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.openSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        RulesPreparingWindow rulePreparingWindow = subsectionCard.openRulesPreparingSubsection();
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.openEditRuleWindow();
        editRuleWindow.deleteRules();
        Selenide.sleep(2000);
        assertFalse(editRuleWindow.isWindowAppear());
        assertTrue(rulePreparingWindow.isWindowAppear());
        assertFalse(rulePreparingWindow.isExistRule());
        assertTrue(rulePreparingWindow.isExistsEmptyListRules());
        assertEquals("[]", DataBaseQuery.selectServicesCategories(NAME_SUBSECTION).getPreparing_description());
    }

    @Feature("Управление правилами подготовки")
    @Story("Успешное удаление всех правил подготовки к подразделу")
    @DisplayName("Успешное удаление всех правил подготовки к подразделу")
    @ExtendWith(AddDeleteSubsectionRuleDecorator.class)
    @Test
    void deleteAllRulePreparingSubsection() {
        CategoryCard categoryCard = servicesPage.openCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.openSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        RulesPreparingWindow rulePreparingWindow = subsectionCard.openRulesPreparingSubsection();
        rulePreparingWindow.deleteAllRules();
        Selenide.sleep(2000);
        assertFalse(rulePreparingWindow.isExistRule());
        assertTrue(rulePreparingWindow.isExistsEmptyListRules());
        assertEquals("[]", DataBaseQuery.selectServicesCategories(NAME_SUBSECTION).getPreparing_description());
    }

    @Feature("Управление правилами подготовки")
    @Story("Закрытие окна правил подготовки к категории")
    @DisplayName("Закрытие окна правил подготовки к категории")
    @Test
    void closeWindowRulePreparingCategory() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(CATEGORY_RULES);
        rulePreparingWindow.closeWindowRulesPreparing();
        Selenide.sleep(2000);
        assertFalse(rulePreparingWindow.isWindowAppear());
    }

    @Feature("Управление правилами подготовки")
    @Story("Открытие правил подготовки к категории Иные услуги")
    @DisplayName("Открытие правил подготовки к категории Иные услуги")
    @Test
    void openRulePreparingCategoryOtherServices() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(NAME_OTHER_SERVICE_CATEGORY);
        assertFalse(rulePreparingWindow.isWindowAppear());
        assertEquals("Категория не найдена", servicesPage.getNotification());
    }


    @Feature("Управление правилами подготовки")
    @Story("Успешное добавление правила подготовки к услуге")
    @DisplayName("Успешное добавление правила подготовки к услуге")
    @ExtendWith(AddServiceInNewCategoryDecorator.class)
    @Test
    void addRulePreparingService() {
        CategoryCard categoryCard = servicesPage.openCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.openSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        subsectionCard.openSubsection();
        ServiceCard serviceCard = subsectionCard.getService();
        String codeService = serviceCard.getCodeService();
        ServiceWindow serviceWindow = serviceCard.openServiceInfo();
        serviceWindow.switchToRulesPreparing();
        serviceWindow.serviceWindowRulesPreparing();
        serviceWindow.fillFieldTitle(RULE_TITLE);
        serviceWindow.fillFieldDescription(RULE_DESCRIPTION);
        serviceWindow.clickAddButton();
        PreparingDescriptions preparingDescription = DataBaseQuery.selectDescriptionService(codeService);
        assertEquals(RULE_TITLE, serviceWindow.getValueTitleField());
        assertEquals(RULE_DESCRIPTION, serviceWindow.getValueDescriptionField());
        assertEquals(RULE_TITLE, preparingDescription.getTitle());
        assertEquals(RULE_DESCRIPTION, preparingDescription.getDescriptions());
        assertTrue(serviceWindow.isExistRulePreparing());
        assertFalse(serviceWindow.isExistEmptyList());
        assertTrue(serviceWindow.isWindowAppear());
    }

    @Feature("Управление правилами подготовки")
    @Story("Добавление правила подготовки к услуге с пустым полем заголовка")
    @DisplayName("Добавление правила подготовки к услуге с пустым полем заголовка")
    @ExtendWith(AddServiceInNewCategoryDecorator.class)
    @Test
    void addRulePreparingServiceEmptyFieldTitle() {
        CategoryCard categoryCard = servicesPage.openCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.openSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        subsectionCard.openSubsection();
        ServiceCard serviceCard = subsectionCard.getService();
        ServiceWindow serviceWindow = serviceCard.openServiceInfo();
        serviceWindow.switchToRulesPreparing();
        serviceWindow.fillFieldDescription(RULE_DESCRIPTION);
        serviceWindow.clickAddButton();
        assertEquals("Неверный запрос (400)", servicesPage.getNotification());
        assertTrue(serviceWindow.isEnabledAddButton());
    }

    @Feature("Управление правилами подготовки")
    @Story("Добавление правила подготовки к услуге с пустым полем описания")
    @DisplayName("Добавление правила подготовки к услуге с пустым полем описания")
    @ExtendWith(AddServiceInNewCategoryDecorator.class)
    @Test
    void addRulePreparingServiceEmptyFieldDescription() {
        CategoryCard categoryCard = servicesPage.openCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.openSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        subsectionCard.openSubsection();
        ServiceCard serviceCard = subsectionCard.getService();
        ServiceWindow serviceWindow = serviceCard.openServiceInfo();
        serviceWindow.switchToRulesPreparing();
        serviceWindow.fillFieldTitle(RULE_TITLE);
        assertFalse(serviceWindow.isEnabledAddButton());
    }

    @Feature("Управление правилами подготовки")
    @Story("Сброс значений полей в окне добавления правила подготовки к услуге при переключении окна")
    @DisplayName("Сброс значений полей в окне добавления правила подготовки к услуге при переключении окна")
    @ExtendWith(AddServiceInNewCategoryDecorator.class)
    @Test
    void closeSectionPreparingDescriptionsService() {
        CategoryCard categoryCard = servicesPage.openCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.openSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        subsectionCard.openSubsection();
        ServiceCard serviceCard = subsectionCard.getService();
        ServiceWindow serviceWindow = serviceCard.openServiceInfo();
        serviceWindow.switchToRulesPreparing();
        serviceWindow.fillFieldTitle(RULE_TITLE);
        serviceWindow.fillFieldDescription(RULE_DESCRIPTION);
        serviceWindow.switchToGeneralInfo();
        serviceWindow.switchToRulesPreparing();
        assertEquals("", serviceWindow.getValueTitleField());
        assertEquals("", serviceWindow.getValueDescriptionField());
    }

    @Feature("Управление правилами подготовки")
    @Story("Успешное редактирование правила подготовки к услуге")
    @DisplayName("Успешное редактирование правила подготовки к услуге")
    @ExtendWith(AddDeleteRuleServiceDecorator.class)
    @Test
    void editRulePreparingService() {
        CategoryCard categoryCard = servicesPage.openCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.openSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        subsectionCard.openSubsection();
        ServiceCard serviceCard = subsectionCard.getService();
        String codeService = serviceCard.getCodeService();
        ServiceWindow serviceWindow = serviceCard.openServiceInfo();
        serviceWindow.switchToRulesPreparing();
        serviceWindow.fillFieldTitle(NEW_RULE_TITLE);
        serviceWindow.fillFieldDescription(NEW_RULE_DESCRIPTION);
        serviceWindow.clickChangeButton();
        PreparingDescriptions preparingDescription = DataBaseQuery.selectDescriptionService(codeService);
        assertEquals(NEW_RULE_TITLE, serviceWindow.getValueTitleField());
        assertEquals(NEW_RULE_DESCRIPTION, serviceWindow.getValueDescriptionField());
        assertEquals(NEW_RULE_TITLE, preparingDescription.getTitle());
        assertEquals(NEW_RULE_DESCRIPTION, preparingDescription.getDescriptions());
        assertTrue(serviceWindow.isExistRulePreparing());
        assertTrue(serviceWindow.isWindowAppear());
    }

    @Feature("Управление правилами подготовки")
    @Story("Сохранение значений полей заголовка и описания правила подготовки без изменений данных")
    @DisplayName("Сохранение значений полей заголовка и описания правила подготовки без изменений данных")
    @ExtendWith(AddDeleteRuleServiceDecorator.class)
    @Test
    void editRulePreparingServiceNotChangeSave() {
        CategoryCard categoryCard = servicesPage.openCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.openSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        subsectionCard.openSubsection();
        ServiceCard serviceCard = subsectionCard.getService();
        String codeService = serviceCard.getCodeService();
        ServiceWindow serviceWindow = serviceCard.openServiceInfo();
        serviceWindow.switchToRulesPreparing();
        serviceWindow.clickChangeButton();
        PreparingDescriptions preparingDescription = DataBaseQuery.selectDescriptionService(codeService);
        assertEquals(RULE_TITLE, serviceWindow.getValueTitleField());
        assertEquals(RULE_DESCRIPTION, serviceWindow.getValueDescriptionField());
        assertEquals(RULE_TITLE, preparingDescription.getTitle());
        assertEquals(RULE_DESCRIPTION, preparingDescription.getDescriptions());
        assertTrue(serviceWindow.isExistRulePreparing());
    }

    @Feature("Управление правилами подготовки")
    @Story("Удаление правила подготовки к услуге")
    @DisplayName("Удаление правила подготовки к услуге")
    @ExtendWith(AddRuleServiceDecorator.class)
    @Test
    void deleteRulePreparingService() {
        CategoryCard categoryCard = servicesPage.openCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.openSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        subsectionCard.openSubsection();
        ServiceCard serviceCard = subsectionCard.getService();
        String codeService = serviceCard.getCodeService();
        ServiceWindow serviceWindow = serviceCard.openServiceInfo();
        serviceWindow.switchToRulesPreparing();
        serviceWindow.clickDeleteButton();
        assertTrue(serviceWindow.isWindowAppear());
        assertFalse(serviceWindow.isEnabledAddButton());
        assertTrue(serviceWindow.isExistEmptyList());
        assertEquals("[]", DataBaseQuery.selectDescriptionService(codeService).getDescription());
    }


    @Feature("Управление категориями")
    @Story("Успешное добавление раздела в категорию")
    @DisplayName("Успешное добавление раздела в категорию")
    @ExtendWith(AddDeleteCategorySectionDecorator.class)
    @Test
    void addSectionInCategory() {
        CategoryCard categoryCard = servicesPage.openCategory(NAME_CATEGORY);
        categoryCard.categoryCard();
        AddSectionWindow addSectionWindow = categoryCard.addSection();
        addSectionWindow.addSectionWindow();
        addSectionWindow.fillNameSectionField(NAME_SECTION);
        assertTrue(addSectionWindow.isEnabledAddButton());
        addSectionWindow.clickAddSection();
        Selenide.sleep(2000);
        assertFalse(addSectionWindow.isWindowAppear());
        servicesPage.openCategory(NAME_CATEGORY);
        assertTrue(categoryCard.isExistSectionCard());
        SectionCard sectionCard = categoryCard.getSection();
        assertEquals(NAME_SECTION, sectionCard.getNameSection());
        assertNotNull(DataBaseQuery.selectServicesCategories(NAME_SECTION));
        assertEquals(AddDeleteCategorySectionDecorator.getCategoryId(), DataBaseQuery.selectServicesCategories(NAME_SECTION).getParent_id());
    }

    @Feature("Управление категориями")
    @Story("Сброс значений полей в окне добавления раздела в категорию при закрытии окна")
    @DisplayName("Сброс значений полей в окне добавления раздела в категорию при закрытии окна")
    @ExtendWith(AddDeleteCategoryDecorator.class)
    @Test
    void closeWindowAddSection() {
        CategoryCard categoryCard = servicesPage.openCategory(NAME_CATEGORY);
        AddSectionWindow addSectionWindow = categoryCard.addSection();
        addSectionWindow.fillNameSectionField(NAME_SECTION);
        addSectionWindow.closeWindowAddSection();
        assertFalse(addSectionWindow.isWindowAppear());
        categoryCard.addSection();
        assertEquals("", addSectionWindow.getValueNameField());
    }

    @Feature("Управление категориями")
    @Story("Отображение уведомления об обязательности поля")
    @DisplayName("Отображение уведомления об обязательности поля")
    @ExtendWith(AddDeleteCategoryDecorator.class)
    @Test
    void addedNewSectionObligatoryFields() {
        CategoryCard categoryCard = servicesPage.openCategory(NAME_CATEGORY);
        AddSectionWindow addSectionWindow = categoryCard.addSection();
        addSectionWindow.clickFieldName();
        assertFalse(addSectionWindow.isEnabledAddButton());
        assertEquals("Обязательное поле", addSectionWindow.getErrorFieldName());
    }

    @Feature("Управление категориями")
    @Story("Очистка поля имени раздела через кнопку в окне добавления раздела")
    @DisplayName("Очистка поля имени раздела через кнопку в окне добавления раздела")
    @ExtendWith(AddDeleteCategoryDecorator.class)
    @Test
    void clearFieldNameThroughButtonClear() {
        CategoryCard categoryCard = servicesPage.openCategory(NAME_CATEGORY);
        AddSectionWindow addSectionWindow = categoryCard.addSection();
        addSectionWindow.fillNameSectionField(NAME_SECTION);
        addSectionWindow.clearButtonNameField();
        assertEquals("", addSectionWindow.getValueNameField());
        assertEquals("Обязательное поле", addSectionWindow.getErrorFieldName());
    }

    @Feature("Управление категориями")
    @Story("Отмена добавления раздела в категорию")
    @DisplayName("Отмена добавления раздела в категорию")
    @ExtendWith(AddDeleteCategoryDecorator.class)
    @Test
    void cancelAddSectionInCategory() {
        CategoryCard categoryCard = servicesPage.openCategory(NAME_CATEGORY);
        AddSectionWindow addSectionWindow = categoryCard.addSection();
        addSectionWindow.cancelAddSection();
        assertFalse(addSectionWindow.isWindowAppear());
        assertFalse(categoryCard.isExistSectionCard());
    }

    @Feature("Управление категориями")
    @Story("Успешное редактирование раздела в категории")
    @DisplayName("Успешное редактирование раздела в категории")
    @ExtendWith(AddDeleteSectionDecorator.class)
    @Test
    void editSectionInCategory() {
        CategoryCard categoryCard = servicesPage.openCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.sectionCard();
        EditSectionWindow editSectionWindow = sectionCard.editSection();
        editSectionWindow.editSectionWindow();
        editSectionWindow.fillNameField(NEW_NAME_SECTION);
        editSectionWindow.saveChange();
        Selenide.sleep(2000);
        assertFalse(editSectionWindow.isWindowAppear());
        servicesPage.openCategory(NAME_CATEGORY);
        assertEquals(NEW_NAME_SECTION, sectionCard.getNameSection());
        assertNotNull(DataBaseQuery.selectServicesCategories(NEW_NAME_SECTION));
        assertEquals(AddDeleteSectionDecorator.getCategoryId(), DataBaseQuery.selectServicesCategories(NEW_NAME_SECTION).getParent_id());
    }

    @Feature("Управление категориями")
    @Story("Сохранение значений поля в окне редактирования раздела после закрытия окна")
    @DisplayName("Сохранение значений поля в окне редактирования раздела после закрытия окна")
    @ExtendWith(AddDeleteSectionDecorator.class)
    @Test
    void closeWindowEditSectionInCategory() {
        CategoryCard categoryCard = servicesPage.openCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        EditSectionWindow editSectionWindow = sectionCard.editSection();
        editSectionWindow.fillNameField(NEW_NAME_SECTION);
        editSectionWindow.closeWindow();
        assertFalse(editSectionWindow.isWindowAppear());
        assertEquals(NAME_SECTION, sectionCard.getNameSection());
        assertNotNull(DataBaseQuery.selectServicesCategories(NAME_SECTION));
    }

    @Feature("Управление категориями")
    @Story("Сохранение значений поля названия раздела без изменений данных")
    @DisplayName("Сохранение значений поля названия раздела без изменений данных")
    @ExtendWith(AddDeleteSectionDecorator.class)
    @Test
    void editSectionInCategoryNotChangeSave() {
        CategoryCard categoryCard = servicesPage.openCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        EditSectionWindow editSectionWindow = sectionCard.editSection();
        editSectionWindow.saveChange();
        Selenide.sleep(2000);
        assertFalse(editSectionWindow.isWindowAppear());
        servicesPage.openCategory(NAME_CATEGORY);
        assertEquals(NAME_SECTION, sectionCard.getNameSection());
        assertNotNull(DataBaseQuery.selectServicesCategories(NAME_SECTION));
    }

    @Feature("Управление категориями")
    @Story("Редактирование названия раздела с пустым полем")
    @DisplayName("Редактирование названия раздела с пустым полем")
    @ExtendWith(AddDeleteSectionDecorator.class)
    @Test
    void editSectionInCategoryEmptyFieldName() {
        CategoryCard categoryCard = servicesPage.openCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        EditSectionWindow editSectionWindow = sectionCard.editSection();
        editSectionWindow.clearNameField();
        editSectionWindow.saveChange();
        assertTrue(editSectionWindow.isWindowAppear());
        assertNotNull(DataBaseQuery.selectServicesCategories(NAME_SECTION));
    }

    @Feature("Управление категориями")
    @Story("Успешное удаление раздела из категории")
    @DisplayName("Успешное удаление раздела из категории")
    @ExtendWith(AddSectionDecorator.class)
    @Test
    void deleteSectionInCategory() {
        CategoryCard categoryCard = servicesPage.openCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        DeleteSectionWindow deleteSectionWindow = sectionCard.deleteSection();
        deleteSectionWindow.deleteSectionWindow();
        assertTrue(deleteSectionWindow.verifyNameSection(NAME_SECTION));
        deleteSectionWindow.deleteSection();
        Selenide.sleep(2000);
        assertFalse(deleteSectionWindow.isWindowAppear());
        servicesPage.openCategory(NAME_CATEGORY);
        assertFalse(categoryCard.isExistSectionCard());
        assertTrue(categoryCard.isExistEmptyList());
        assertNull(DataBaseQuery.selectServicesCategories(NAME_SECTION));
    }

    @Feature("Управление категориями")
    @Story("Отмена удаления раздела из категории")
    @DisplayName("Отмена удаления раздела из категории")
    @ExtendWith(AddDeleteSectionDecorator.class)
    @Test
    void cancelDeleteSectionInCategory() {
        CategoryCard categoryCard = servicesPage.openCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        DeleteSectionWindow deleteSectionWindow = sectionCard.deleteSection();
        deleteSectionWindow.cancelDeleteSection();
        assertFalse(deleteSectionWindow.isWindowAppear());
        assertTrue(categoryCard.isExistSectionCard());
        assertNotNull(DataBaseQuery.selectServicesCategories(NAME_SECTION));
    }

    @Feature("Управление категориями")
    @Story("Закрытие окна удаления раздела из категории")
    @DisplayName("Закрытие окна удаления раздела из категории")
    @ExtendWith(AddDeleteSectionDecorator.class)
    @Test
    void closeWindowDeleteSectionInCategory() {
        CategoryCard categoryCard = servicesPage.openCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        DeleteSectionWindow deleteSectionWindow = sectionCard.deleteSection();
        deleteSectionWindow.closeWindowDeleteSection();
        assertFalse(deleteSectionWindow.isWindowAppear());
        assertTrue(categoryCard.isExistSectionCard());
        assertNotNull(DataBaseQuery.selectServicesCategories(NAME_SECTION));
    }

    @Feature("Управление категориями")
    @Story("Успешное добавление подраздела в раздел")
    @DisplayName("Успешное добавление подраздела в раздел")
    @ExtendWith(AddDeleteSectionSubsectionDecorator.class)
    @Test
    void addSubsectionInCategory() {
        CategoryCard categoryCard = servicesPage.openCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.openSection();
        AddSectionWindow addSectionWindow = sectionCard.addSubsection();
        addSectionWindow.fillNameSectionField(NAME_SUBSECTION);
        assertTrue(addSectionWindow.isEnabledAddButton());
        addSectionWindow.clickAddSection();
        Selenide.sleep(2000);
        assertFalse(addSectionWindow.isWindowAppear());
        servicesPage.openCategory(NAME_CATEGORY);
        sectionCard.openSection();
        assertTrue(sectionCard.isExistSubsectionCard());
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        assertEquals(NAME_SUBSECTION, subsectionCard.getNameSubsection());
        assertNotNull(DataBaseQuery.selectServicesCategories(NAME_SUBSECTION));
        assertEquals(AddDeleteSectionSubsectionDecorator.getSectionId(), DataBaseQuery.selectServicesCategories(NAME_SUBSECTION).getParent_id());
    }

    @Feature("Управление категориями")
    @Story("Успешное редактирование подраздела в разделе")
    @DisplayName("Успешное редактирование подраздела в разделе")
    @ExtendWith(AddDeleteSubsectionDecorator.class)
    @Test
    void editSubsectionInCategory() {
        CategoryCard categoryCard = servicesPage.openCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.openSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        subsectionCard.subsectionCard();
        EditSectionWindow editSectionWindow = subsectionCard.editSubsection();
        editSectionWindow.fillNameField(NEW_NAME_SUBSECTION);
        editSectionWindow.saveChange();
        Selenide.sleep(2000);
        assertFalse(editSectionWindow.isWindowAppear());
        servicesPage.openCategory(NAME_CATEGORY);
        sectionCard.openSection();
        assertEquals(NEW_NAME_SUBSECTION, subsectionCard.getNameSubsection());
        assertNotNull(DataBaseQuery.selectServicesCategories(NEW_NAME_SUBSECTION));
        assertEquals(AddDeleteSubsectionDecorator.getSectionId(), DataBaseQuery.selectServicesCategories(NEW_NAME_SUBSECTION).getParent_id());
    }

    @Feature("Управление категориями")
    @Story("Успешное удаление подраздела из раздела")
    @DisplayName("Успешное удаление подраздела из раздела")
    @ExtendWith(AddSubsectionDecorator.class)
    @Test
    void deleteSubsectionInCategory() {
        CategoryCard categoryCard = servicesPage.openCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.openSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        DeleteSectionWindow deleteSectionWindow = subsectionCard.deleteSubsection();
        assertTrue(deleteSectionWindow.verifyNameSection(NAME_SUBSECTION));
        deleteSectionWindow.deleteSection();
        Selenide.sleep(2000);
        assertFalse(deleteSectionWindow.isWindowAppear());
        servicesPage.openCategory(NAME_CATEGORY);
        sectionCard.openSection();
        assertFalse(sectionCard.isExistSubsectionCard());
        assertTrue(sectionCard.isExistEmptyList());
        assertNull(DataBaseQuery.selectServicesCategories(NAME_SUBSECTION));
    }

    @Feature("Управление категориями")
    @Story("Смена последовательности отображения категории Иные услуги")
    @DisplayName("Смена последовательности отображения категории Иные услуги")
    @Test
    void changeDisplaySequenceCategoriesOtherServices() {
        servicesPage.servicesPage();
        int sequenceFirstCategory = servicesPage.getCategoryIndexByName(CATEGORY_RULES);
        int sequenceSecondCategory = servicesPage.getCategoryIndexByName(NAME_OTHER_SERVICE_CATEGORY);
        int sequenceFirstCategoryDB = DataBaseQuery.selectServicesCategories(CATEGORY_RULES).getSequence();
        servicesPage.changeDisplaySequence(CATEGORY_RULES, NAME_OTHER_SERVICE_CATEGORY);
        assertEquals("Нельзя перемещать раздел \"Иные услуги\"", servicesPage.getNotification());
        assertEquals(sequenceFirstCategory, servicesPage.getCategoryIndexByName(CATEGORY_RULES));
        assertEquals(sequenceSecondCategory, servicesPage.getCategoryIndexByName(NAME_OTHER_SERVICE_CATEGORY));
        assertEquals(sequenceFirstCategoryDB, DataBaseQuery.selectServicesCategories(CATEGORY_RULES).getSequence());
        assertNull(DataBaseQuery.selectServicesCategories(NAME_OTHER_SERVICE_CATEGORY));
    }

    @Feature("Управление категориями")
    @Story("Удаление раздела, имеющего подраздел")
    @DisplayName("Удаление раздела, имеющего подраздел")
    @ExtendWith(AddDeleteSubsectionDecorator.class)
    @Test
    void deleteSectionThatHasSubsectionInCategory() {
        CategoryCard categoryCard = servicesPage.openCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        DeleteSectionWindow deleteSectionWindow = sectionCard.deleteSection();
        deleteSectionWindow.deleteSection();
        assertEquals("Нельзя удалить категорию, т.к. имеются дочерние объекты", servicesPage.getNotification());
        assertFalse(deleteSectionWindow.isWindowAppear());
        assertTrue(sectionCard.isExistSubsectionCard());
        assertFalse(sectionCard.isExistEmptyList());
        assertNotNull(DataBaseQuery.selectServicesCategories(NAME_SUBSECTION));
    }

    @Feature("Управление услугами")
    @Story("Открытие карточки услуги")
    @DisplayName("Открытие карточки услуги")
    @ExtendWith(AddServiceInNewCategoryDecorator.class)
    @Test
    void openCardService() {
        CategoryCard categoryCard = servicesPage.openCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.openSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        subsectionCard.openSubsection();
        ServiceCard serviceCard = subsectionCard.getService();
        serviceCard.serviceCard();
        String nameService = serviceCard.getNameService();
        String codeService = serviceCard.getCodeService();
        ServiceWindow serviceWindow = serviceCard.openServiceInfo();
        serviceWindow.serviceWindowGeneralInfo();
        assertEquals(nameService, serviceWindow.getHeaderServiceWindow());
        assertEquals(nameService, serviceWindow.getNameService());
        assertEquals(NAME_CATEGORY + " / " + NAME_SECTION + " / " + NAME_SUBSECTION + " / " + nameService, serviceWindow.getPathService());
        assertEquals(codeService, serviceWindow.getCodeService());
    }

    @Feature("Управление услугами")
    @Story("Успешный перенос услуги в категорию Иные услуги")
    @DisplayName("Успешный перенос услуги в категорию Иные услуги")
    @ExtendWith(AddServiceInNewCategoryForTransferDecorator.class)
    @Test
    void transferServiceToOtherServices() {
        CategoryCard categoryCard = servicesPage.openCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.openSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        subsectionCard.openSubsection();
        ServiceCard serviceCard = subsectionCard.getService();
        String codeService = serviceCard.getCodeService();
        ServiceWindow serviceWindow = serviceCard.openServiceInfo();
        serviceWindow.switchToServiceTransfer();
        serviceWindow.serviceWindowServiceTransfer();
        serviceWindow.openCategoryForTransfer(NAME_OTHER_SERVICE_CATEGORY);
        serviceWindow.transferServiceToOtherServices();
        Selenide.sleep(2000);
        assertFalse(serviceWindow.isWindowAppear());
        servicesPage.openCategory(NAME_OTHER_SERVICE_CATEGORY);
        assertTrue(categoryCard.isExistService(codeService));
        servicesPage.openCategory(NAME_CATEGORY);
        sectionCard.openSection();
        subsectionCard.openSubsection();
        assertTrue(subsectionCard.isExistEmptyList());
    }

    @Feature("Управление услугами")
    @Story("Успешное удаление услуги из категории")
    @DisplayName("Успешное удаление услуги из категории")
    @ExtendWith(AddServiceInNewCategoryForTransferDecorator.class)
    @Test
    void deleteServiceFromCategory() {
        CategoryCard categoryCard = servicesPage.openCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.openSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        subsectionCard.openSubsection();
        ServiceCard serviceCard = subsectionCard.getService();
        String codeService = serviceCard.getCodeService();
        ServiceWindow serviceWindow = serviceCard.openServiceInfo();
        serviceWindow.deleteService();
        Selenide.sleep(2000);
        assertFalse(serviceWindow.isWindowAppear());
        servicesPage.openCategory(NAME_OTHER_SERVICE_CATEGORY);
        assertTrue(categoryCard.isExistService(codeService));
        servicesPage.openCategory(NAME_CATEGORY);
        sectionCard.openSection();
        subsectionCard.openSubsection();
        assertTrue(subsectionCard.isExistEmptyList());

    }

    @Feature("Управление услугами")
    @Story("Успешный перенос услуги в категорию Иные услуги при удалении раздела")
    @DisplayName("Успешный перенос услуги в категорию Иные услуги при удалении раздела")
    @ExtendWith(AddServiceInNewCategoryForDeleteSection.class)
    @Test
    void transferServiceToOtherServicesIfDeleteSection() {
        CategoryCard categoryCard = servicesPage.openCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.openSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        subsectionCard.openSubsection();
        ServiceCard serviceCard = subsectionCard.getService();
        String codeService = serviceCard.getCodeService();
        subsectionCard.closeSubsection(NAME_SUBSECTION);
        DeleteSectionWindow deleteSectionWindow = subsectionCard.deleteSubsection();
        deleteSectionWindow.deleteSection();
        Selenide.sleep(2000);
        servicesPage.openCategory(NAME_OTHER_SERVICE_CATEGORY);
        assertTrue(categoryCard.isExistService(codeService));
        servicesPage.openCategory(NAME_CATEGORY);
        sectionCard.openSection();
        assertTrue(sectionCard.isExistEmptyList());
    }

    @Feature("Управление услугами")
    @Story("Перенос услуги при совпадении категории-источника и категории-приемника")
    @Story("Перенос услуги при совпадении категории-источника и категории-приемника")
    @Test
    void transferServiceEqualsSourceTargetCategory() {
        CategoryCard categoryCard = servicesPage.openCategory(NAME_OTHER_SERVICE_CATEGORY);
        ServiceCard serviceCard = categoryCard.getService();
        String codeService = serviceCard.getCodeService();
        ServiceWindow serviceWindow = serviceCard.openServiceInfo();
        serviceWindow.switchToServiceTransfer();
        serviceWindow.openCategoryForTransfer(NAME_OTHER_SERVICE_CATEGORY);
        serviceWindow.transferServiceToOtherServices();
        assertEquals("Категории источника и приёмника для переноса не должны совпадать", servicesPage.getNotification());
        serviceWindow.closeWindowInfoService();
        assertFalse(serviceWindow.isWindowAppear());
        assertTrue(categoryCard.isExistService(codeService));
    }

    @Feature("Управление категориями")
    @Story("Сворачивание дерева иерархии")
    @DisplayName("Сворачивание дерева иерархии")
    @ExtendWith(AddServiceInNewCategoryDecorator.class)
    @Test
    void closeCategory() {
        CategoryCard categoryCard = servicesPage.openCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.openSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        subsectionCard.openSubsection();
        subsectionCard.closeSubsection(NAME_SUBSECTION);
        assertFalse(subsectionCard.isExistService());
        sectionCard.closeSection(NAME_SECTION);
        servicesPage.closeCategory(NAME_CATEGORY);
        assertFalse(categoryCard.isExistSectionCard());
    }

    @Feature("Управление категориями")
    @Story("Смена последовательности отображения категорий")
    @DisplayName("Смена последовательности отображения категорий")
    @ExtendWith(AddDeleteCategoryDecorator.class)
    @Test
    void changeDisplaySequenceCategories() {
        servicesPage.servicesPage();
        int sequenceFirstCategory = servicesPage.getCategoryIndexByName(CATEGORY_RULES);
        int sequenceSecondCategory = servicesPage.getCategoryIndexByName(NAME_CATEGORY);
        int sequenceFirstCategoryDB = DataBaseQuery.selectServicesCategories(CATEGORY_RULES).getSequence();
        int sequenceSecondCategoryDB = DataBaseQuery.selectServicesCategories(NAME_CATEGORY).getSequence();
        servicesPage.changeDisplaySequence(CATEGORY_RULES, NAME_CATEGORY);
        Selenide.sleep(5000);
        assertEquals(sequenceFirstCategory, servicesPage.getCategoryIndexByName(NAME_CATEGORY));
        assertEquals(sequenceSecondCategory, servicesPage.getCategoryIndexByName(CATEGORY_RULES));
        assertEquals(sequenceFirstCategoryDB, DataBaseQuery.selectServicesCategories(NAME_CATEGORY).getSequence());
        assertEquals(sequenceSecondCategoryDB, DataBaseQuery.selectServicesCategories(CATEGORY_RULES).getSequence());
    }

    @Feature("Управление категориями")
    @Story("Смена последовательности отображения разделов")
    @DisplayName("Смена последовательности отображения разделов")
    @ExtendWith(AddTwoSections.class)
    @Test
    void changeDisplaySequenceSections() {
        CategoryCard categoryCard = servicesPage.openCategory(NAME_CATEGORY);
        int sequenceFirstSection = categoryCard.getSectionByName(NAME_SECTION);
        int sequenceSecondSection = categoryCard.getSectionByName(NEW_NAME_SECTION);
        int sequenceFirstSectionDB = DataBaseQuery.selectServicesCategories(NAME_SECTION).getSequence();
        int sequenceSecondSectionDB = DataBaseQuery.selectServicesCategories(NEW_NAME_SECTION).getSequence();
        categoryCard.changeDisplaySequence(NAME_SECTION, NEW_NAME_SECTION);
        Selenide.sleep(3000);
        servicesPage.openCategory(NAME_CATEGORY);
        assertEquals(sequenceFirstSection, categoryCard.getSectionByName(NEW_NAME_SECTION));
        assertEquals(sequenceSecondSection, categoryCard.getSectionByName(NAME_SECTION));
        assertEquals(sequenceFirstSectionDB, DataBaseQuery.selectServicesCategories(NEW_NAME_SECTION).getSequence());
        assertEquals(sequenceSecondSectionDB, DataBaseQuery.selectServicesCategories(NAME_SECTION).getSequence());
    }

    @Feature("Управление категориями")
    @Story("Смена последовательности отображения подразделов")
    @DisplayName("Смена последовательности отображения подразделов")
    @ExtendWith(AddTwoSubsections.class)
    @Test
    void changeDisplaySequenceSubsections() {
        CategoryCard categoryCard = servicesPage.openCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.openSection();
        int sequenceFirstSubsection = sectionCard.getSubsectionIndexByName(NAME_SUBSECTION);
        int sequenceSecondSubsection = sectionCard.getSubsectionIndexByName(NEW_NAME_SUBSECTION);
        int sequenceFirstSubsectionDB = DataBaseQuery.selectServicesCategories(NAME_SUBSECTION).getSequence();
        int sequenceSecondSubsectionDB = DataBaseQuery.selectServicesCategories(NEW_NAME_SUBSECTION).getSequence();
        sectionCard.changeDisplaySequence(NAME_SUBSECTION, NEW_NAME_SUBSECTION);
        Selenide.sleep(9000);
        servicesPage.openCategory(NAME_CATEGORY);
        sectionCard.openSection();
        assertEquals(sequenceFirstSubsection, categoryCard.getSectionByName(NEW_NAME_SUBSECTION));
        assertEquals(sequenceSecondSubsection, categoryCard.getSectionByName(NAME_SUBSECTION));
        assertEquals(sequenceFirstSubsectionDB, DataBaseQuery.selectServicesCategories(NEW_NAME_SUBSECTION).getSequence());
        assertEquals(sequenceSecondSubsectionDB, DataBaseQuery.selectServicesCategories(NAME_SUBSECTION).getSequence());
    }

    @Feature("Управление категориями")
    @Story("Смена последовательности отображения услуг")
    @DisplayName("Смена последовательности отображения услуг")
    @ExtendWith(AddTwoServices.class)
    @Test
    void changeDisplaySequenceServices() {
        CategoryCard categoryCard = servicesPage.openCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.openSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        subsectionCard.openSubsection();
        String codeFirst = AddTwoServices.getServiceCodeFirst();
        String codeSecond = AddTwoServices.getServiceCodeSecond();
        int sequenceFirstService = subsectionCard.getServiceByCode(codeFirst);
        int sequenceSecondService = subsectionCard.getServiceByCode(codeSecond);
        int sequenceFirstServiceDB = DataBaseQuery.selectAllService2(codeFirst).getSequence();
        int sequenceSecondServiceDB = DataBaseQuery.selectAllService2(codeSecond).getSequence();
        subsectionCard.changeDisplaySequence(codeFirst, codeSecond);
        Selenide.sleep(5000);
        servicesPage.openCategory(NAME_CATEGORY);
        sectionCard.openSection();
        subsectionCard.openSubsection();
        assertEquals(sequenceFirstService, subsectionCard.getServiceByCode(codeSecond));
        assertEquals(sequenceSecondService, subsectionCard.getServiceByCode(codeFirst));
        assertEquals(sequenceFirstServiceDB, DataBaseQuery.selectAllService2(codeSecond).getSequence());
        assertEquals(sequenceSecondServiceDB, DataBaseQuery.selectAllService2(codeSecond).getSequence());
    }

    @Story("Закрытие уведомления на странице услуг по таймауту")
    @DisplayName("Закрытие уведомления на странице услуг по таймауту")
    @Test
    void closeNotificationTimeout() {
        servicesPage.openRulesPreparingCategory(NAME_OTHER_SERVICE_CATEGORY);
        checkCloseNotificationTimeout(basePage);
    }

    @Story("Закрытие уведомления на странице услуг")
    @DisplayName("Закрытие уведомления на странице услуг")
    @Test
    void closeNotification() {
        servicesPage.openRulesPreparingCategory(NAME_OTHER_SERVICE_CATEGORY);
        checkCloseNotification(basePage);
    }
}

