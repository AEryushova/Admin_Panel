package admin.test;

import admin.pages.HeaderMenu.HeaderMenu;
import admin.pages.ServicesPage.*;
import admin.utils.dbUtils.DataBaseQuery;
import admin.utils.dbUtils.dbaseData.PreparingDescriptions;
import admin.utils.dbUtils.dbaseData.ServiceCategories;
import admin.utils.preparationDataTests.services.*;
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

    @ExtendWith(AllureDecorator.class)

    @BeforeAll
    static void setUpAuth() {
        BaseTest.openAdminPanel(LOGIN_ADMIN, PASSWORD_ADMIN);
        HeaderMenu headerMenu = new HeaderMenu();
        headerMenu.clickServicesTab();
    }

    @BeforeEach
    void setUp() {
        Selenide.refresh();
        servicesPage = new ServicesPage();
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
        RulesPreparingWindow rulePreparingWindow = servicesPage.clickButtonOpenRulesPreparingCategory(CATEGORY_RULES);
        rulePreparingWindow.verifyRulesPreparingWindow();
        AddRuleWindow addRuleWindow = rulePreparingWindow.clickButtonAddRules();
        addRuleWindow.verifyAddRuleWindow();
        addRuleWindow.fillFieldTitle(RULE_TITLE);
        addRuleWindow.fillFieldDescription(RULE_DESCRIPTION);
        addRuleWindow.clickSaveButton();
        Selenide.sleep(2000);
        assertTrue(rulePreparingWindow.isExistRule());
        Rule rule = rulePreparingWindow.getRule();
        rule.verifyRule();
        EditRuleWindow editRuleWindow = rule.clickButtonEditRule();
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
        RulesPreparingWindow rulePreparingWindow = servicesPage.clickButtonOpenRulesPreparingCategory(CATEGORY_RULES);
        AddRuleWindow addRuleWindow = rulePreparingWindow.clickButtonAddRules();
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
        RulesPreparingWindow rulePreparingWindow = servicesPage.clickButtonOpenRulesPreparingCategory(CATEGORY_RULES);
        AddRuleWindow addRuleWindow = rulePreparingWindow.clickButtonAddRules();
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
    void addRulePreparingCategoryEmptyFields() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.clickButtonOpenRulesPreparingCategory(CATEGORY_RULES);
        AddRuleWindow addRuleWindow = rulePreparingWindow.clickButtonAddRules();
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
        RulesPreparingWindow rulePreparingWindow = servicesPage.clickButtonOpenRulesPreparingCategory(CATEGORY_RULES);
        AddRuleWindow addRuleWindow = rulePreparingWindow.clickButtonAddRules();
        addRuleWindow.fillFieldTitle(RULE_TITLE);
        addRuleWindow.fillFieldDescription(RULE_DESCRIPTION);
        addRuleWindow.closeWindowAddRule();
        assertFalse(addRuleWindow.isWindowAppear());
        assertFalse(rulePreparingWindow.isWindowAppear());
        servicesPage.clickButtonOpenRulesPreparingCategory(CATEGORY_RULES);
        rulePreparingWindow.clickButtonAddRules();
        assertEquals("", addRuleWindow.getValueTitleField());
        assertEquals("", addRuleWindow.getValueDescriptionField());
    }

    @Feature("Управление правилами подготовки")
    @Story("Отмена добавления правила подготовки после возврата к списку правил")
    @DisplayName("Отмена добавления правила подготовки после возврата к списку правил")
    @ExtendWith(DeleteRuleCategoryDecorator.class)
    @Test
    void comebackRulesListFromWindowAddRulePreparingCategory() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.clickButtonOpenRulesPreparingCategory(CATEGORY_RULES);
        AddRuleWindow addRuleWindow = rulePreparingWindow.clickButtonAddRules();
        addRuleWindow.fillFieldTitle(RULE_TITLE);
        addRuleWindow.fillFieldDescription(RULE_DESCRIPTION);
        addRuleWindow.clickButtonReturnRulesList();
        assertFalse(addRuleWindow.isWindowAppear());
        assertTrue(rulePreparingWindow.isWindowAppear());
        rulePreparingWindow.clickButtonAddRules();
        assertEquals("", addRuleWindow.getValueTitleField());
        assertEquals("", addRuleWindow.getValueDescriptionField());
    }

    @Feature("Управление правилами подготовки")
    @Story("Успешное редактирование правила подготовки к категории")
    @DisplayName("Успешное редактирование правила подготовки к категории")
    @ExtendWith(AddDeleteCategoryRuleDecorator.class)
    @Test
    void editRulePreparingCategory() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.clickButtonOpenRulesPreparingCategory(CATEGORY_RULES);
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.clickButtonEditRule();
        editRuleWindow.verifyEditRuleWindow();
        editRuleWindow.fillFieldTitle(NEW_RULE_TITLE);
        editRuleWindow.fillFieldDescription(NEW_RULE_DESCRIPTION);
        editRuleWindow.clickButtonChangeRules();
        Selenide.sleep(2000);
        assertFalse(editRuleWindow.isWindowAppear());
        assertTrue(rulePreparingWindow.isWindowAppear());
        rule.clickButtonEditRule();
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
        RulesPreparingWindow rulePreparingWindow = servicesPage.clickButtonOpenRulesPreparingCategory(CATEGORY_RULES);
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.clickButtonEditRule();
        editRuleWindow.clearTitleField();
        editRuleWindow.fillFieldDescription(NEW_RULE_DESCRIPTION);
        editRuleWindow.clickButtonChangeRules();
        assertEquals("Неверный запрос (400)", servicesPage.getNotification());
        assertTrue(editRuleWindow.isWindowAppear());
    }

    @Feature("Управление правилами подготовки")
    @Story("Редактирование правила подготовки к категории с пустым полем описания")
    @DisplayName("Редактирование правила подготовки к категории с пустым полем описания")
    @ExtendWith(AddDeleteCategoryRuleDecorator.class)
    @Test
    void editRulePreparingCategoryEmptyFieldDescription() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.clickButtonOpenRulesPreparingCategory(CATEGORY_RULES);
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.clickButtonEditRule();
        editRuleWindow.fillFieldTitle(NEW_RULE_TITLE);
        editRuleWindow.clearDescriptionField();
        editRuleWindow.clickButtonChangeRules();
        assertEquals("Неверный запрос (400)", servicesPage.getNotification());
        assertTrue(editRuleWindow.isWindowAppear());
    }

    @Feature("Управление правилами подготовки")
    @Story("Редактирование правила подготовки к категории с пустыми полями")
    @DisplayName("Редактирование правила подготовки к категории с пустыми полями")
    @ExtendWith(AddDeleteCategoryRuleDecorator.class)
    @Test
    void editRulePreparingCategoryEmptyFields() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.clickButtonOpenRulesPreparingCategory(CATEGORY_RULES);
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.clickButtonEditRule();
        editRuleWindow.clearTitleField();
        editRuleWindow.clearDescriptionField();
        editRuleWindow.clickButtonChangeRules();
        assertEquals("Неверный запрос (400)", servicesPage.getNotification());
        assertTrue(editRuleWindow.isWindowAppear());
    }

    @Feature("Управление правилами подготовки")
    @Story("Сохранение правила подготовки к категории без изменений")
    @DisplayName("Сохранение правила подготовки к категории без изменений")
    @ExtendWith(AddDeleteCategoryRuleDecorator.class)
    @Test
    void saveRulePreparingCategoryNotChange() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.clickButtonOpenRulesPreparingCategory(CATEGORY_RULES);
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.clickButtonEditRule();
        editRuleWindow.clickButtonChangeRules();
        rule.clickButtonEditRule();
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
        RulesPreparingWindow rulePreparingWindow = servicesPage.clickButtonOpenRulesPreparingCategory(CATEGORY_RULES);
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.clickButtonEditRule();
        editRuleWindow.fillFieldTitle(NEW_RULE_TITLE);
        editRuleWindow.fillFieldDescription(NEW_RULE_DESCRIPTION);
        editRuleWindow.closeWindowEditRule();
        Selenide.sleep(2000);
        assertFalse(editRuleWindow.isWindowAppear());
        assertTrue(rulePreparingWindow.isWindowAppear());
        rule.clickButtonEditRule();
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
        RulesPreparingWindow rulePreparingWindow = servicesPage.clickButtonOpenRulesPreparingCategory(CATEGORY_RULES);
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.clickButtonEditRule();
        editRuleWindow.clickButtonDeleteRules();
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
        RulesPreparingWindow rulePreparingWindow = servicesPage.clickButtonOpenRulesPreparingCategory(CATEGORY_RULES);
        rulePreparingWindow.clickButtonDeleteAllRules();
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
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        RulesPreparingWindow rulePreparingWindow = sectionCard.clickButtonOpenRulesPreparingSection();
        AddRuleWindow addRuleWindow = rulePreparingWindow.clickButtonAddRules();
        addRuleWindow.fillFieldTitle(RULE_TITLE);
        addRuleWindow.fillFieldDescription(RULE_DESCRIPTION);
        addRuleWindow.clickSaveButton();
        Selenide.sleep(2000);
        assertTrue(rulePreparingWindow.isExistRule());
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.clickButtonEditRule();
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
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        RulesPreparingWindow rulePreparingWindow = sectionCard.clickButtonOpenRulesPreparingSection();
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.clickButtonEditRule();
        editRuleWindow.verifyEditRuleWindow();
        editRuleWindow.fillFieldTitle(NEW_RULE_TITLE);
        editRuleWindow.fillFieldDescription(NEW_RULE_DESCRIPTION);
        editRuleWindow.clickButtonChangeRules();
        Selenide.sleep(2000);
        assertFalse(editRuleWindow.isWindowAppear());
        assertTrue(rulePreparingWindow.isWindowAppear());
        rule.clickButtonEditRule();
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
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        RulesPreparingWindow rulePreparingWindow = sectionCard.clickButtonOpenRulesPreparingSection();
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.clickButtonEditRule();
        editRuleWindow.clickButtonDeleteRules();
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
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        RulesPreparingWindow rulePreparingWindow = sectionCard.clickButtonOpenRulesPreparingSection();
        rulePreparingWindow.clickButtonDeleteAllRules();
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
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.clickButtonOpenSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        RulesPreparingWindow rulePreparingWindow = subsectionCard.clickButtonOpenRulesPreparingSubsection();
        AddRuleWindow addRuleWindow = rulePreparingWindow.clickButtonAddRules();
        addRuleWindow.fillFieldTitle(RULE_TITLE);
        addRuleWindow.fillFieldDescription(RULE_DESCRIPTION);
        addRuleWindow.clickSaveButton();
        Selenide.sleep(2000);
        assertTrue(rulePreparingWindow.isExistRule());
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.clickButtonEditRule();
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
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.clickButtonOpenSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        RulesPreparingWindow rulePreparingWindow = subsectionCard.clickButtonOpenRulesPreparingSubsection();
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.clickButtonEditRule();
        editRuleWindow.verifyEditRuleWindow();
        editRuleWindow.fillFieldTitle(NEW_RULE_TITLE);
        editRuleWindow.fillFieldDescription(NEW_RULE_DESCRIPTION);
        editRuleWindow.clickButtonChangeRules();
        Selenide.sleep(2000);
        assertFalse(editRuleWindow.isWindowAppear());
        assertTrue(rulePreparingWindow.isWindowAppear());
        rule.clickButtonEditRule();
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
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.clickButtonOpenSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        RulesPreparingWindow rulePreparingWindow = subsectionCard.clickButtonOpenRulesPreparingSubsection();
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.clickButtonEditRule();
        editRuleWindow.clickButtonDeleteRules();
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
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.clickButtonOpenSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        RulesPreparingWindow rulePreparingWindow = subsectionCard.clickButtonOpenRulesPreparingSubsection();
        rulePreparingWindow.clickButtonDeleteAllRules();
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
        RulesPreparingWindow rulePreparingWindow = servicesPage.clickButtonOpenRulesPreparingCategory(CATEGORY_RULES);
        rulePreparingWindow.closeWindowRulesPreparing();
        Selenide.sleep(2000);
        assertFalse(rulePreparingWindow.isWindowAppear());
    }

    @Feature("Управление правилами подготовки")
    @Story("Открытие правил подготовки к категории Иные услуги")
    @DisplayName("Открытие правил подготовки к категории Иные услуги")
    @Test
    void openRulePreparingCategoryOtherServices() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.clickButtonOpenRulesPreparingCategory(NAME_OTHER_SERVICE_CATEGORY);
        assertFalse(rulePreparingWindow.isWindowAppear());
        assertEquals("Категория не найдена", servicesPage.getNotification());
    }


    @Feature("Управление правилами подготовки")
    @Story("Успешное добавление правила подготовки к услуге")
    @DisplayName("Успешное добавление правила подготовки к услуге")
    @ExtendWith(AddServiceInNewCategoryDecorator.class)
    @Test
    void addRulePreparingService() {
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.clickButtonOpenSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        subsectionCard.clickButtonOpenSubsection();
        ServiceCard serviceCard = subsectionCard.getService();
        String codeService = serviceCard.getCodeService();
        ServiceWindow serviceWindow = serviceCard.clickButtonOpenServiceInfo();
        serviceWindow.clickRulesPreparingTab();
        serviceWindow.verifyServiceWindowRulesPreparing();
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
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.clickButtonOpenSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        subsectionCard.clickButtonOpenSubsection();
        ServiceCard serviceCard = subsectionCard.getService();
        ServiceWindow serviceWindow = serviceCard.clickButtonOpenServiceInfo();
        serviceWindow.clickRulesPreparingTab();
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
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.clickButtonOpenSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        subsectionCard.clickButtonOpenSubsection();
        ServiceCard serviceCard = subsectionCard.getService();
        ServiceWindow serviceWindow = serviceCard.clickButtonOpenServiceInfo();
        serviceWindow.clickRulesPreparingTab();
        serviceWindow.fillFieldTitle(RULE_TITLE);
        assertFalse(serviceWindow.isEnabledAddButton());
    }

    @Feature("Управление правилами подготовки")
    @Story("Сброс значений полей в окне добавления правила подготовки к услуге при переключении окна")
    @DisplayName("Сброс значений полей в окне добавления правила подготовки к услуге при переключении окна")
    @ExtendWith(AddServiceInNewCategoryDecorator.class)
    @Test
    void closePreparingDescriptionsService() {
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.clickButtonOpenSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        subsectionCard.clickButtonOpenSubsection();
        ServiceCard serviceCard = subsectionCard.getService();
        ServiceWindow serviceWindow = serviceCard.clickButtonOpenServiceInfo();
        serviceWindow.clickRulesPreparingTab();
        serviceWindow.fillFieldTitle(RULE_TITLE);
        serviceWindow.fillFieldDescription(RULE_DESCRIPTION);
        serviceWindow.clickGeneralInfoTab();
        serviceWindow.clickRulesPreparingTab();
        assertEquals("", serviceWindow.getValueTitleField());
        assertEquals("", serviceWindow.getValueDescriptionField());
    }

    @Feature("Управление правилами подготовки")
    @Story("Успешное редактирование правила подготовки к услуге")
    @DisplayName("Успешное редактирование правила подготовки к услуге")
    @ExtendWith(AddDeleteRuleServiceDecorator.class)
    @Test
    void editRulePreparingService() {
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.clickButtonOpenSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        subsectionCard.clickButtonOpenSubsection();
        ServiceCard serviceCard = subsectionCard.getService();
        String codeService = serviceCard.getCodeService();
        ServiceWindow serviceWindow = serviceCard.clickButtonOpenServiceInfo();
        serviceWindow.clickRulesPreparingTab();
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
    void saveRulePreparingServiceNotChange() {
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.clickButtonOpenSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        subsectionCard.clickButtonOpenSubsection();
        ServiceCard serviceCard = subsectionCard.getService();
        String codeService = serviceCard.getCodeService();
        ServiceWindow serviceWindow = serviceCard.clickButtonOpenServiceInfo();
        serviceWindow.clickRulesPreparingTab();
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
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.clickButtonOpenSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        subsectionCard.clickButtonOpenSubsection();
        ServiceCard serviceCard = subsectionCard.getService();
        String codeService = serviceCard.getCodeService();
        ServiceWindow serviceWindow = serviceCard.clickButtonOpenServiceInfo();
        serviceWindow.clickRulesPreparingTab();
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
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        categoryCard.verifyCategoryCard();
        AddSectionWindow addSectionWindow = categoryCard.clickButtonAddSection();
        addSectionWindow.verifyAddSectionWindow();
        addSectionWindow.fillNameSectionField(NAME_SECTION);
        assertTrue(addSectionWindow.isEnabledAddButton());
        addSectionWindow.clickButtonAddSection();
        Selenide.sleep(2000);
        assertFalse(addSectionWindow.isWindowAppear());
        servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
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
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        AddSectionWindow addSectionWindow = categoryCard.clickButtonAddSection();
        addSectionWindow.fillNameSectionField(NAME_SECTION);
        addSectionWindow.closeWindowAddSection();
        assertFalse(addSectionWindow.isWindowAppear());
        categoryCard.clickButtonAddSection();
        assertEquals("", addSectionWindow.getValueNameField());
    }

    @Feature("Управление категориями")
    @Story("Отображение уведомления об обязательности поля")
    @DisplayName("Отображение уведомления об обязательности поля")
    @ExtendWith(AddDeleteCategoryDecorator.class)
    @Test
    void displayNotificationAboutRequiredFieldsWindowAddSection() {
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        AddSectionWindow addSectionWindow = categoryCard.clickButtonAddSection();
        addSectionWindow.clickFieldName();
        assertFalse(addSectionWindow.isEnabledAddButton());
        assertEquals("Обязательное поле", addSectionWindow.getErrorFieldName());
    }

    @Feature("Управление категориями")
    @Story("Очистка поля имени раздела через кнопку в окне добавления раздела")
    @DisplayName("Очистка поля имени раздела через кнопку в окне добавления раздела")
    @ExtendWith(AddDeleteCategoryDecorator.class)
    @Test
    void clearFieldWindowAddSectionThroughButtonClear() {
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        AddSectionWindow addSectionWindow = categoryCard.clickButtonAddSection();
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
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        AddSectionWindow addSectionWindow = categoryCard.clickButtonAddSection();
        addSectionWindow.clickCancelButtonAddSection();
        assertFalse(addSectionWindow.isWindowAppear());
        assertFalse(categoryCard.isExistSectionCard());
    }

    @Feature("Управление категориями")
    @Story("Успешное редактирование раздела в категории")
    @DisplayName("Успешное редактирование раздела в категории")
    @ExtendWith(AddDeleteSectionDecorator.class)
    @Test
    void editSectionInCategory() {
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.verifySectionCard();
        EditSectionWindow editSectionWindow = sectionCard.clickButtonEditSection();
        editSectionWindow.verifyEditSectionWindow();
        editSectionWindow.fillNameField(NEW_NAME_SECTION);
        editSectionWindow.clickButtonSaveChange();
        Selenide.sleep(2000);
        assertFalse(editSectionWindow.isWindowAppear());
        servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        assertEquals(NEW_NAME_SECTION, sectionCard.getNameSection());
        assertNotNull(DataBaseQuery.selectServicesCategories(NEW_NAME_SECTION));
        assertEquals(AddDeleteSectionDecorator.getCategoryId(), DataBaseQuery.selectServicesCategories(NEW_NAME_SECTION).getParent_id());
    }

    @Feature("Управление категориями")
    @Story("Сохранение значений поля в окне редактирования раздела после закрытия окна")
    @DisplayName("Сохранение значений поля в окне редактирования раздела после закрытия окна")
    @ExtendWith(AddDeleteSectionDecorator.class)
    @Test
    void closeWindowEditSectionInCategory () {
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        EditSectionWindow editSectionWindow = sectionCard.clickButtonEditSection();
        editSectionWindow.fillNameField(NEW_NAME_SECTION);
        editSectionWindow.closeWindowEditSection();
        assertFalse(editSectionWindow.isWindowAppear());
        assertEquals(NAME_SECTION, sectionCard.getNameSection());
        assertNotNull(DataBaseQuery.selectServicesCategories(NAME_SECTION));
    }

    @Feature("Управление категориями")
    @Story("Сохранение значений поля названия раздела без изменений данных")
    @DisplayName("Сохранение значений поля названия раздела без изменений данных")
    @ExtendWith(AddDeleteSectionDecorator.class)
    @Test
    void saveSectionInCategoryNotChange() {
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        EditSectionWindow editSectionWindow = sectionCard.clickButtonEditSection();
        editSectionWindow.clickButtonSaveChange();
        Selenide.sleep(2000);
        assertFalse(editSectionWindow.isWindowAppear());
        servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        assertEquals(NAME_SECTION, sectionCard.getNameSection());
        assertNotNull(DataBaseQuery.selectServicesCategories(NAME_SECTION));
    }

    @Feature("Управление категориями")
    @Story("Редактирование названия раздела с пустым полем")
    @DisplayName("Редактирование названия раздела с пустым полем")
    @ExtendWith(AddDeleteSectionDecorator.class)
    @Test
    void editSectionInCategoryEmptyFieldName() {
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        EditSectionWindow editSectionWindow = sectionCard.clickButtonEditSection();
        editSectionWindow.clearNameField();
        editSectionWindow.clickButtonSaveChange();
        assertTrue(editSectionWindow.isWindowAppear());
        assertNotNull(DataBaseQuery.selectServicesCategories(NAME_SECTION));
    }

    @Feature("Управление категориями")
    @Story("Успешное удаление раздела из категории")
    @DisplayName("Успешное удаление раздела из категории")
    @ExtendWith(AddSectionDecorator.class)
    @Test
    void deleteSectionInCategory() {
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        DeleteSectionWindow deleteSectionWindow = sectionCard.clickButtonDeleteSection();
        deleteSectionWindow.verifyDeleteSectionWindow();
        assertTrue(deleteSectionWindow.verifyNameSection(NAME_SECTION));
        deleteSectionWindow.clickButtonDeleteSection();
        Selenide.sleep(2000);
        assertFalse(deleteSectionWindow.isWindowAppear());
        servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
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
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        DeleteSectionWindow deleteSectionWindow = sectionCard.clickButtonDeleteSection();
        deleteSectionWindow.clickCancelButtonDeleteSection();
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
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        DeleteSectionWindow deleteSectionWindow = sectionCard.clickButtonDeleteSection();
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
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.clickButtonOpenSection();
        AddSectionWindow addSectionWindow = sectionCard.clickButtonAddSubsection();
        addSectionWindow.fillNameSectionField(NAME_SUBSECTION);
        assertTrue(addSectionWindow.isEnabledAddButton());
        addSectionWindow.clickButtonAddSection();
        Selenide.sleep(2000);
        assertFalse(addSectionWindow.isWindowAppear());
        servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        sectionCard.clickButtonOpenSection();
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
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.clickButtonOpenSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        subsectionCard.verifySubsectionCard();
        EditSectionWindow editSectionWindow = subsectionCard.clickButtonEditSubsection();
        editSectionWindow.fillNameField(NEW_NAME_SUBSECTION);
        editSectionWindow.clickButtonSaveChange();
        Selenide.sleep(2000);
        assertFalse(editSectionWindow.isWindowAppear());
        servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        sectionCard.clickButtonOpenSection();
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
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.clickButtonOpenSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        DeleteSectionWindow deleteSectionWindow = subsectionCard.clickButtonDeleteSubsection();
        assertTrue(deleteSectionWindow.verifyNameSection(NAME_SUBSECTION));
        deleteSectionWindow.clickButtonDeleteSection();
        Selenide.sleep(2000);
        assertFalse(deleteSectionWindow.isWindowAppear());
        servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        sectionCard.clickButtonOpenSection();
        assertFalse(sectionCard.isExistSubsectionCard());
        assertTrue(sectionCard.isExistEmptyList());
        assertNull(DataBaseQuery.selectServicesCategories(NAME_SUBSECTION));
    }

    @Feature("Управление категориями")
    @Story("Смена последовательности отображения категории Иные услуги")
    @DisplayName("Смена последовательности отображения категории Иные услуги")
    @Test
    void changeSequenceDisplayCategoriesCategoryOtherServices() {
        servicesPage.verifyServicesPage();
        int sequenceFirstCategory = servicesPage.getCategoryIndexByName(CATEGORY_RULES);
        int sequenceSecondCategory = servicesPage.getCategoryIndexByName(NAME_OTHER_SERVICE_CATEGORY);
        int sequenceFirstCategoryDB = DataBaseQuery.selectServicesCategories(CATEGORY_RULES).getSequence();
        servicesPage.changeSequenceDisplayCategories(CATEGORY_RULES, NAME_OTHER_SERVICE_CATEGORY);
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
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        DeleteSectionWindow deleteSectionWindow = sectionCard.clickButtonDeleteSection();
        deleteSectionWindow.clickButtonDeleteSection();
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
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.clickButtonOpenSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        subsectionCard.clickButtonOpenSubsection();
        ServiceCard serviceCard = subsectionCard.getService();
        serviceCard.verifyServiceCard();
        String nameService = serviceCard.getNameService();
        String codeService = serviceCard.getCodeService();
        ServiceWindow serviceWindow = serviceCard.clickButtonOpenServiceInfo();
        serviceWindow.verifyServiceWindowGeneralInfo();
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
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.clickButtonOpenSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        subsectionCard.clickButtonOpenSubsection();
        ServiceCard serviceCard = subsectionCard.getService();
        String codeService = serviceCard.getCodeService();
        ServiceWindow serviceWindow = serviceCard.clickButtonOpenServiceInfo();
        serviceWindow.clickServiceTransferTab();
        serviceWindow.verifyServiceWindowServiceTransfer();
        serviceWindow.clickCategoryForTransfer(NAME_OTHER_SERVICE_CATEGORY);
        serviceWindow.clickButtonTransferServiceToOtherServices();
        Selenide.sleep(2000);
        assertFalse(serviceWindow.isWindowAppear());
        servicesPage.clickButtonOpenCategory(NAME_OTHER_SERVICE_CATEGORY);
        assertTrue(categoryCard.isExistService(codeService));
        servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        sectionCard.clickButtonOpenSection();
        subsectionCard.clickButtonOpenSubsection();
        assertTrue(subsectionCard.isExistEmptyList());
    }

    @Feature("Управление услугами")
    @Story("Успешное удаление услуги из категории")
    @DisplayName("Успешное удаление услуги из категории")
    @ExtendWith(AddServiceInNewCategoryForTransferDecorator.class)
    @Test
    void deleteServiceFromCategory() {
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.clickButtonOpenSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        subsectionCard.clickButtonOpenSubsection();
        ServiceCard serviceCard = subsectionCard.getService();
        String codeService = serviceCard.getCodeService();
        ServiceWindow serviceWindow = serviceCard.clickButtonOpenServiceInfo();
        serviceWindow.clickButtonDeleteService();
        Selenide.sleep(2000);
        assertFalse(serviceWindow.isWindowAppear());
        servicesPage.clickButtonOpenCategory(NAME_OTHER_SERVICE_CATEGORY);
        assertTrue(categoryCard.isExistService(codeService));
        servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        sectionCard.clickButtonOpenSection();
        subsectionCard.clickButtonOpenSubsection();
        assertTrue(subsectionCard.isExistEmptyList());

    }

    @Feature("Управление услугами")
    @Story("Успешный перенос услуги в категорию Иные услуги при удалении раздела")
    @DisplayName("Успешный перенос услуги в категорию Иные услуги при удалении раздела")
    @ExtendWith(AddServiceInNewCategoryForDeleteSection.class)
    @Test
    void transferServiceToOtherServicesIfDeleteSection() {
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.clickButtonOpenSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        subsectionCard.clickButtonOpenSubsection();
        ServiceCard serviceCard = subsectionCard.getService();
        String codeService = serviceCard.getCodeService();
        subsectionCard.clickButtonCloseSubsection(NAME_SUBSECTION);
        DeleteSectionWindow deleteSectionWindow = subsectionCard.clickButtonDeleteSubsection();
        deleteSectionWindow.clickButtonDeleteSection();
        Selenide.sleep(2000);
        servicesPage.clickButtonOpenCategory(NAME_OTHER_SERVICE_CATEGORY);
        assertTrue(categoryCard.isExistService(codeService));
        servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        sectionCard.clickButtonOpenSection();
        assertTrue(sectionCard.isExistEmptyList());
    }

    @Feature("Управление услугами")
    @Story("Перенос услуги при совпадении категории-источника и категории-приемника")
    @Story("Перенос услуги при совпадении категории-источника и категории-приемника")
    @Test
    void transferServiceEqualsSourceCategoryTargetCategory() {
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(NAME_OTHER_SERVICE_CATEGORY);
        ServiceCard serviceCard = categoryCard.getService();
        String codeService = serviceCard.getCodeService();
        ServiceWindow serviceWindow = serviceCard.clickButtonOpenServiceInfo();
        serviceWindow.clickServiceTransferTab();
        serviceWindow.clickCategoryForTransfer(NAME_OTHER_SERVICE_CATEGORY);
        serviceWindow.clickButtonTransferServiceToOtherServices();
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
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.clickButtonOpenSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        subsectionCard.clickButtonOpenSubsection();
        subsectionCard.clickButtonCloseSubsection(NAME_SUBSECTION);
        assertFalse(subsectionCard.isExistService());
        sectionCard.clickButtonCloseSection(NAME_SECTION);
        servicesPage.clickButtonCloseCategory(NAME_CATEGORY);
        assertFalse(categoryCard.isExistSectionCard());
    }

    @Feature("Управление категориями")
    @Story("Смена последовательности отображения категорий")
    @DisplayName("Смена последовательности отображения категорий")
    @ExtendWith(AddDeleteCategoryDecorator.class)
    @Test
    void changeSequenceDisplayCategoriesCategories() {
        servicesPage.verifyServicesPage();
        int sequenceFirstCategory = servicesPage.getCategoryIndexByName(CATEGORY_RULES);
        int sequenceSecondCategory = servicesPage.getCategoryIndexByName(NAME_CATEGORY);
        int sequenceFirstCategoryDB = DataBaseQuery.selectServicesCategories(CATEGORY_RULES).getSequence();
        int sequenceSecondCategoryDB = DataBaseQuery.selectServicesCategories(NAME_CATEGORY).getSequence();
        servicesPage.changeSequenceDisplayCategories(CATEGORY_RULES, NAME_CATEGORY);
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
    void changeSequenceDisplayCategoriesSections() {
        servicesPage.verifyServicesPage();
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        servicesPage.scrollPage("500");
        int sequenceFirstSection = categoryCard.getSectionIndexByName(NAME_SECTION);
        int sequenceSecondSection = categoryCard.getSectionIndexByName(NEW_NAME_SECTION);
        int sequenceFirstSectionDB = DataBaseQuery.selectServicesCategories(NAME_SECTION).getSequence();
        int sequenceSecondSectionDB = DataBaseQuery.selectServicesCategories(NEW_NAME_SECTION).getSequence();
        Selenide.sleep(3000);
        categoryCard.changeSequenceDisplaySections(NAME_SECTION, NEW_NAME_SECTION);
        Selenide.sleep(3000);
        servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        servicesPage.scrollPage("500");
        assertEquals(sequenceFirstSection, categoryCard.getSectionIndexByName(NEW_NAME_SECTION));
        assertEquals(sequenceSecondSection, categoryCard.getSectionIndexByName(NAME_SECTION));
        assertEquals(sequenceFirstSectionDB, DataBaseQuery.selectServicesCategories(NEW_NAME_SECTION).getSequence());
        assertEquals(sequenceSecondSectionDB, DataBaseQuery.selectServicesCategories(NAME_SECTION).getSequence());
    }


    @Feature("Управление категориями")
    @Story("Смена последовательности отображения подразделов")
    @DisplayName("Смена последовательности отображения подразделов")
    @ExtendWith(AddTwoSubsections.class)
    @Test
    void changeSequenceDisplayCategoriesSubsections() {
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.clickButtonOpenSection();
        servicesPage.scrollPage("500");
        int sequenceFirstSubsection = sectionCard.getSubsectionIndexByName(NAME_SUBSECTION);
        int sequenceSecondSubsection = sectionCard.getSubsectionIndexByName(NEW_NAME_SUBSECTION);
        int sequenceFirstSubsectionDB = DataBaseQuery.selectServicesCategories(NAME_SUBSECTION).getSequence();
        int sequenceSecondSubsectionDB = DataBaseQuery.selectServicesCategories(NEW_NAME_SUBSECTION).getSequence();
        Selenide.sleep(3000);
        sectionCard.changeSequenceDisplaySubsections(NAME_SUBSECTION, NEW_NAME_SUBSECTION);
        Selenide.sleep(3000);
        servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        sectionCard.clickButtonOpenSection();
        servicesPage.scrollPage("500");
        assertEquals(sequenceFirstSubsection, categoryCard.getSectionIndexByName(NEW_NAME_SUBSECTION));
        assertEquals(sequenceSecondSubsection, categoryCard.getSectionIndexByName(NAME_SUBSECTION));
        assertEquals(sequenceFirstSubsectionDB, DataBaseQuery.selectServicesCategories(NEW_NAME_SUBSECTION).getSequence());
        assertEquals(sequenceSecondSubsectionDB, DataBaseQuery.selectServicesCategories(NAME_SUBSECTION).getSequence());
    }

    @Feature("Управление категориями")
    @Story("Смена последовательности отображения услуг")
    @DisplayName("Смена последовательности отображения услуг")
    @ExtendWith(AddTwoServices.class)
    @Test
    void changeSequenceDisplayCategoriesServices() {
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.clickButtonOpenSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        subsectionCard.clickButtonOpenSubsection();
        servicesPage.scrollPage("500");
        String codeFirst = AddTwoServices.getServiceCodeFirst();
        String codeSecond = AddTwoServices.getServiceCodeSecond();
        int sequenceFirstService = subsectionCard.getServiceByCode(codeFirst);
        int sequenceSecondService = subsectionCard.getServiceByCode(codeSecond);
        int sequenceFirstServiceDB = DataBaseQuery.selectAllService(codeFirst).getSequence();
        int sequenceSecondServiceDB = DataBaseQuery.selectAllService(codeSecond).getSequence();
        Selenide.sleep(3000);
        subsectionCard.changeSequenceDisplayServices(codeFirst, codeSecond);
        Selenide.sleep(3000);
        servicesPage.clickButtonOpenCategory(NAME_CATEGORY);
        sectionCard.clickButtonOpenSection();
        subsectionCard.clickButtonOpenSubsection();
        servicesPage.scrollPage("500");
        assertEquals(sequenceFirstService, subsectionCard.getServiceByCode(codeSecond));
        assertEquals(sequenceSecondService, subsectionCard.getServiceByCode(codeFirst));
        assertEquals(sequenceFirstServiceDB, DataBaseQuery.selectAllService(codeSecond).getSequence());
        assertEquals(sequenceSecondServiceDB, DataBaseQuery.selectAllService(codeFirst).getSequence());
    }

}

