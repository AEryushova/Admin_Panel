package ru.adminlk.clinica.test;

import ru.adminlk.clinica.pages.HeaderMenu.HeaderMenu;
import ru.adminlk.clinica.pages.ServicesPage.*;
import ru.adminlk.clinica.utils.dbUtils.dbUtils.DataBaseQuery;
import ru.adminlk.clinica.utils.dbUtils.dbData.PreparingDescriptions;
import ru.adminlk.clinica.utils.dbUtils.dbData.ServiceCategories;
import ru.adminlk.clinica.utils.preparationData.services.*;
import com.codeborne.selenide.Condition;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;


import static com.codeborne.selenide.Condition.value;
import static ru.adminlk.clinica.data.FinalTestData.TestData.*;
import static ru.adminlk.clinica.data.FinalTestData.UserData.*;
import static org.junit.jupiter.api.Assertions.*;
import static ru.adminlk.clinica.utils.testsUtils.DataGenerator.*;
import static ru.adminlk.clinica.data.GeneratedTestData.*;


@Epic("Услуги")
@DisplayName("Страница Услуги")
public class ServicesPageTest extends BaseTest {

    @BeforeAll
    static void setUpAuth() {
        getAuthToken(LOGIN_ADMIN, PASSWORD_ADMIN);
    }

    @BeforeEach
    void setUp() {
        openAuthAdminPanel();
        HeaderMenu headerMenu = new HeaderMenu();
        headerMenu.clickServicesTab();
        servicesPage.verifyServicesPage();
    }

    @AfterEach()
    void closeWebDriver() {
        closeDriver();
    }


    @Feature("Управление правилами подготовки")
    @Story("Успешное добавление правила подготовки к категории")
    @DisplayName("Успешное добавление правила подготовки к категории")
    @ExtendWith(DeleteRuleCategory.class)
    @Test
    void addRulePreparingCategory() {
        servicesPage.scrollPageDown("500");
        RulesPreparingWindow rulePreparingWindow = servicesPage.clickButtonOpenRulesPreparingCategory(categoryName);
        rulePreparingWindow.verifyRulesPreparingWindow();
        AddRuleWindow addRuleWindow = rulePreparingWindow.clickButtonAddRules();
        addRuleWindow.verifyAddRuleWindow()
                .fillFieldTitle(generateWord())
                .fillFieldDescription(generateText())
                .clickSaveButton();
        rulePreparingWindow.getRuleByTitle(word).shouldBe(Condition.visible);
        assertTrue(rulePreparingWindow.isExistRule());
        Rule rule = rulePreparingWindow.getRule();
        rule.verifyRule();
        EditRuleWindow editRuleWindow = rule.clickButtonEditRule();
        ServiceCategories preparingDescription = DataBaseQuery.selectServicesCategories(categoryName);
        assertEquals(word, editRuleWindow.getTitleRule());
        assertEquals(text, editRuleWindow.getDescriptionRule());
        assertEquals(word, preparingDescription.getTitle());
        assertEquals(text, preparingDescription.getDescription());
        assertEquals("CATEGORY_PREPARING_DESCRIPTION_CHANGED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Управление правилами подготовки")
    @Story("Добавление правила подготовки к категории с пустым полем заголовка")
    @DisplayName("Добавление правила подготовки к категории с пустым полем заголовка")
    @ExtendWith(DeleteRuleCategory.class)
    @Test
    void addRulePreparingCategoryEmptyFieldTitle() {
        servicesPage.scrollPageDown("500");
        RulesPreparingWindow rulePreparingWindow = servicesPage.clickButtonOpenRulesPreparingCategory(categoryName);
        rulePreparingWindow.verifyRulesPreparingWindow();
        AddRuleWindow addRuleWindow = rulePreparingWindow.clickButtonAddRules();
        addRuleWindow.verifyAddRuleWindow()
                .fillFieldDescription(generateText())
                .clickSaveButton()
                .clickButtonReturnRulesList();
        assertEquals("Неверный запрос (400)", servicesPage.getTextNotification());
        assertFalse(rulePreparingWindow.isExistRule());
        assertTrue(rulePreparingWindow.isExistsEmptyListRules());
        rulePreparingWindow.closeWindowRulesPreparing();
        assertFalse(rulePreparingWindow.isWindowAppear());
    }

    @Feature("Управление правилами подготовки")
    @Story("Добавление правила подготовки к категории с пустым полем описания")
    @DisplayName("Добавление правила подготовки к категории с пустым полем описания")
    @ExtendWith(DeleteRuleCategory.class)
    @Test
    void addRulePreparingCategoryEmptyFieldDescription() {
        servicesPage.scrollPageDown("500");
        RulesPreparingWindow rulePreparingWindow = servicesPage.clickButtonOpenRulesPreparingCategory(categoryName);
        rulePreparingWindow.verifyRulesPreparingWindow();
        AddRuleWindow addRuleWindow = rulePreparingWindow.clickButtonAddRules();
        addRuleWindow.verifyAddRuleWindow()
                .fillFieldTitle(generateWord())
                .clickSaveButton()
                .clickButtonReturnRulesList();
        assertEquals("Неверный запрос (400)", servicesPage.getTextNotification());
        assertFalse(rulePreparingWindow.isExistRule());
        assertTrue(rulePreparingWindow.isExistsEmptyListRules());
    }

    @Feature("Управление правилами подготовки")
    @Story("Добавление правила подготовки к категории с пустыми полями заголовка и описания")
    @DisplayName("Добавление правила подготовки к категории с пустыми полями заголовка и описания")
    @ExtendWith(DeleteRuleCategory.class)
    @Test
    void addRulePreparingCategoryEmptyFields() {
        servicesPage.scrollPageDown("500");
        RulesPreparingWindow rulePreparingWindow = servicesPage.clickButtonOpenRulesPreparingCategory(categoryName);
        rulePreparingWindow.verifyRulesPreparingWindow();
        AddRuleWindow addRuleWindow = rulePreparingWindow.clickButtonAddRules();
        addRuleWindow.verifyAddRuleWindow()
                .clickSaveButton()
                .clickButtonReturnRulesList();
        assertEquals("Неверный запрос (400)", servicesPage.getTextNotification());
        assertFalse(rulePreparingWindow.isExistRule());
        assertTrue(rulePreparingWindow.isExistsEmptyListRules());
    }

    @Feature("Управление правилами подготовки")
    @Story("Сброс значений полей в окне добавления правила подготовки к категории после закрытия окна")
    @DisplayName("Сброс значений полей в окне добавления правила подготовки к категории после закрытия окна")
    @ExtendWith(DeleteRuleCategory.class)
    @Test
    void closeWindowAddRulePreparingCategory() {
        servicesPage.scrollPageDown("500");
        RulesPreparingWindow rulePreparingWindow = servicesPage.clickButtonOpenRulesPreparingCategory(categoryName);
        rulePreparingWindow.verifyRulesPreparingWindow();
        AddRuleWindow addRuleWindow = rulePreparingWindow.clickButtonAddRules();
        addRuleWindow.verifyAddRuleWindow()
                .fillFieldTitle(generateWord())
                .fillFieldDescription(generateText())
                .closeWindowAddRule();
        assertFalse(addRuleWindow.isWindowAppear());
        assertFalse(rulePreparingWindow.isWindowAppear());
        servicesPage.clickButtonOpenRulesPreparingCategory(categoryName);
        rulePreparingWindow.clickButtonAddRules();
        assertEquals("", addRuleWindow.getValueTitleField());
        assertEquals("", addRuleWindow.getValueDescriptionField());
    }

    @Feature("Управление правилами подготовки")
    @Story("Отмена добавления правила подготовки после возврата к списку правил")
    @DisplayName("Отмена добавления правила подготовки после возврата к списку правил")
    @ExtendWith(DeleteRuleCategory.class)
    @Test
    void comebackRulesListFromWindowAddRulePreparingCategory() {
        servicesPage.scrollPageDown("500");
        RulesPreparingWindow rulePreparingWindow = servicesPage.clickButtonOpenRulesPreparingCategory(categoryName);
        rulePreparingWindow.verifyRulesPreparingWindow();
        AddRuleWindow addRuleWindow = rulePreparingWindow.clickButtonAddRules();
        addRuleWindow.verifyAddRuleWindow()
                .fillFieldTitle(generateWord())
                .fillFieldDescription(generateText())
                .clickButtonReturnRulesList();
        assertFalse(addRuleWindow.isWindowAppear());
        assertTrue(rulePreparingWindow.isWindowAppear());
        rulePreparingWindow.clickButtonAddRules();
        assertEquals("", addRuleWindow.getValueTitleField());
        assertEquals("", addRuleWindow.getValueDescriptionField());
    }

    @Feature("Управление правилами подготовки")
    @Story("Успешное редактирование правила подготовки к категории")
    @DisplayName("Успешное редактирование правила подготовки к категории")
    @ExtendWith(AddDeleteCategoryRule.class)
    @Test
    void editRulePreparingCategory() {
        servicesPage.scrollPageDown("500");
        RulesPreparingWindow rulePreparingWindow = servicesPage.clickButtonOpenRulesPreparingCategory(categoryName);
        rulePreparingWindow.verifyRulesPreparingWindow();
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.clickButtonEditRule();
        editRuleWindow.verifyEditRuleWindow()
                .fillFieldTitle(generateWord())
                .fillFieldDescription(generateText())
                .clickButtonChangeRules();
        rulePreparingWindow.getRuleByTitle(word).shouldBe(Condition.visible);
        rule.clickButtonEditRule();
        ServiceCategories preparingDescription = DataBaseQuery.selectServicesCategories(categoryName);
        assertEquals(word, editRuleWindow.getTitleRule());
        assertEquals(text, editRuleWindow.getDescriptionRule());
        assertEquals(word, preparingDescription.getTitle());
        assertEquals(text, preparingDescription.getDescription());
        assertEquals("CATEGORY_PREPARING_DESCRIPTION_CHANGED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Управление правилами подготовки")
    @Story("Редактирование правила подготовки к категории с пустым полем заголовка")
    @DisplayName("Редактирование правила подготовки к категории с пустым полем заголовка")
    @ExtendWith(AddDeleteCategoryRule.class)
    @Test
    void editRulePreparingCategoryEmptyFieldTitle() {
        servicesPage.scrollPageDown("500");
        RulesPreparingWindow rulePreparingWindow = servicesPage.clickButtonOpenRulesPreparingCategory(categoryName);
        rulePreparingWindow.verifyRulesPreparingWindow();
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.clickButtonEditRule();
        editRuleWindow.verifyEditRuleWindow()
                .clearTitleField()
                .fillFieldDescription(generateNamePatient())
                .clickButtonChangeRules()
                .closeWindowEditRule();
        rule.clickButtonEditRule();
        ServiceCategories preparingDescription = DataBaseQuery.selectServicesCategories(categoryName);
        assertEquals("Неверный запрос (400)", servicesPage.getTextNotification());
        assertEquals(word, editRuleWindow.getTitleRule());
        assertEquals(text, editRuleWindow.getDescriptionRule());
        assertEquals(word, preparingDescription.getTitle());
        assertEquals(text, preparingDescription.getDescription());
    }

    @Feature("Управление правилами подготовки")
    @Story("Редактирование правила подготовки к категории с пустым полем описания")
    @DisplayName("Редактирование правила подготовки к категории с пустым полем описания")
    @ExtendWith(AddDeleteCategoryRule.class)
    @Test
    void editRulePreparingCategoryEmptyFieldDescription() {
        servicesPage.scrollPageDown("500");
        RulesPreparingWindow rulePreparingWindow = servicesPage.clickButtonOpenRulesPreparingCategory(categoryName);
        rulePreparingWindow.verifyRulesPreparingWindow();
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.clickButtonEditRule();
        editRuleWindow.verifyEditRuleWindow()
                .fillFieldTitle(generateNamePatient())
                .clearDescriptionField()
                .clickButtonChangeRules()
                .closeWindowEditRule();
        rule.clickButtonEditRule();
        ServiceCategories preparingDescription = DataBaseQuery.selectServicesCategories(categoryName);
        assertEquals("Неверный запрос (400)", servicesPage.getTextNotification());
        assertEquals(word, editRuleWindow.getTitleRule());
        assertEquals(text, editRuleWindow.getDescriptionRule());
        assertEquals(word, preparingDescription.getTitle());
        assertEquals(text, preparingDescription.getDescription());
    }

    @Feature("Управление правилами подготовки")
    @Story("Редактирование правила подготовки к категории с пустыми полями")
    @DisplayName("Редактирование правила подготовки к категории с пустыми полями")
    @ExtendWith(AddDeleteCategoryRule.class)
    @Test
    void editRulePreparingCategoryEmptyFields() {
        servicesPage.scrollPageDown("500");
        RulesPreparingWindow rulePreparingWindow = servicesPage.clickButtonOpenRulesPreparingCategory(categoryName);
        rulePreparingWindow.verifyRulesPreparingWindow();
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.clickButtonEditRule();
        editRuleWindow.verifyEditRuleWindow()
                .clearTitleField()
                .clearDescriptionField()
                .clickButtonChangeRules()
                .closeWindowEditRule();
        rule.clickButtonEditRule();
        ServiceCategories preparingDescription = DataBaseQuery.selectServicesCategories(categoryName);
        assertEquals("Неверный запрос (400)", servicesPage.getTextNotification());
        assertEquals(word, editRuleWindow.getTitleRule());
        assertEquals(text, editRuleWindow.getDescriptionRule());
        assertEquals(word, preparingDescription.getTitle());
        assertEquals(text, preparingDescription.getDescription());
    }

    @Feature("Управление правилами подготовки")
    @Story("Сохранение правила подготовки к категории без изменений")
    @DisplayName("Сохранение правила подготовки к категории без изменений")
    @ExtendWith(AddDeleteCategoryRule.class)
    @Test
    void saveRulePreparingCategoryNotChange() {
        servicesPage.scrollPageDown("500");
        RulesPreparingWindow rulePreparingWindow = servicesPage.clickButtonOpenRulesPreparingCategory(categoryName);
        rulePreparingWindow.verifyRulesPreparingWindow();
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.clickButtonEditRule();
        editRuleWindow.verifyEditRuleWindow()
                .clickButtonChangeRules();
        rule.clickButtonEditRule();
        ServiceCategories preparingDescription = DataBaseQuery.selectServicesCategories(categoryName);
        assertEquals(word, editRuleWindow.getTitleRule());
        assertEquals(text, editRuleWindow.getDescriptionRule());
        assertEquals(word, preparingDescription.getTitle());
        assertEquals(text, preparingDescription.getDescription());
    }

    @Feature("Управление правилами подготовки")
    @Story("Сохранение значений в окне редактирования правила подготовки к категории после закрытия окна")
    @DisplayName("Сохранение значений в окне редактирования правила подготовки к категории после закрытия окна")
    @ExtendWith(AddDeleteCategoryRule.class)
    @Test
    void closeWindowEditRulePreparingCategory() {
        servicesPage.scrollPageDown("500");
        RulesPreparingWindow rulePreparingWindow = servicesPage.clickButtonOpenRulesPreparingCategory(categoryName);
        rulePreparingWindow.verifyRulesPreparingWindow();
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.clickButtonEditRule();
        editRuleWindow.verifyEditRuleWindow()
                .fillFieldTitle(generateNamePatient())
                .fillFieldDescription(generateQuestion())
                .closeWindowEditRule();
        rulePreparingWindow.getRuleByTitle(word).shouldBe(Condition.visible);
        rule.clickButtonEditRule();
        ServiceCategories preparingDescription = DataBaseQuery.selectServicesCategories(categoryName);
        assertEquals(word, editRuleWindow.getTitleRule());
        assertEquals(text, editRuleWindow.getDescriptionRule());
        assertEquals(word, preparingDescription.getTitle());
        assertEquals(text, preparingDescription.getDescription());
    }

    @Feature("Управление правилами подготовки")
    @Story("Успешное удаление правила подготовки к категории")
    @DisplayName("Успешное удаление правила подготовки к категории")
    @ExtendWith(AddRuleCategory.class)
    @Test
    void deleteRulePreparingCategory() {
        servicesPage.scrollPageDown("500");
        RulesPreparingWindow rulePreparingWindow = servicesPage.clickButtonOpenRulesPreparingCategory(categoryName);
        rulePreparingWindow.verifyRulesPreparingWindow();
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.clickButtonEditRule();
        editRuleWindow.verifyEditRuleWindow()
                .clickButtonDeleteRules();
        rulePreparingWindow.getEmptyList().shouldBe(Condition.visible);
        assertFalse(rulePreparingWindow.isExistRule());
        assertTrue(rulePreparingWindow.isExistsEmptyListRules());
        assertEquals("[]", DataBaseQuery.selectServicesCategories(categoryName).getPreparing_description());
        assertEquals("CATEGORY_PREPARING_DESCRIPTION_CHANGED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Управление правилами подготовки")
    @Story("Успешное удаление всех правил подготовки к категории")
    @DisplayName("Успешное удаление всех правил подготовки к категории")
    @ExtendWith(AddRuleCategory.class)
    @Test
    void deleteAllRulePreparingCategory() {
        servicesPage.scrollPageDown("500");
        RulesPreparingWindow rulePreparingWindow = servicesPage.clickButtonOpenRulesPreparingCategory(categoryName);
        rulePreparingWindow.verifyRulesPreparingWindow()
                .clickButtonDeleteAllRules()
                .getEmptyList().shouldBe(Condition.visible);
        assertFalse(rulePreparingWindow.isExistRule());
        assertTrue(rulePreparingWindow.isExistsEmptyListRules());
        assertEquals("[]", DataBaseQuery.selectServicesCategories(categoryName).getPreparing_description());
        assertEquals("CATEGORY_PREPARING_DESCRIPTION_CHANGED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Управление правилами подготовки")
    @Story("Успешное добавление правила подготовки к разделу")
    @DisplayName("Успешное добавление правила подготовки к разделу")
    @ExtendWith(AddDeleteSection.class)
    @Test
    void addRulePreparingSection() {
        servicesPage.scrollPageDown("500");
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(categoryName);
        SectionCard sectionCard = categoryCard.getSection();
        servicesPage.scrollPageDown("500");
        RulesPreparingWindow rulePreparingWindow = sectionCard.clickButtonOpenRulesPreparingSection();
        rulePreparingWindow.verifyRulesPreparingWindow();
        AddRuleWindow addRuleWindow = rulePreparingWindow.clickButtonAddRules();
        addRuleWindow.verifyAddRuleWindow()
                .fillFieldTitle(generateWord())
                .fillFieldDescription(generateText())
                .clickSaveButton();
        rulePreparingWindow.getRuleByTitle(word).shouldBe(Condition.visible);
        assertTrue(rulePreparingWindow.isExistRule());
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.clickButtonEditRule();
        ServiceCategories preparingDescription = DataBaseQuery.selectServicesCategories(sectionName);
        assertEquals(word, editRuleWindow.getTitleRule());
        assertEquals(text, editRuleWindow.getDescriptionRule());
        assertEquals(word, preparingDescription.getTitle());
        assertEquals(text, preparingDescription.getDescription());
        assertEquals("CATEGORY_PREPARING_DESCRIPTION_CHANGED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Управление правилами подготовки")
    @Story("Успешное редактирование правила подготовки к разделу")
    @DisplayName("Успешное редактирование правила подготовки к разделу")
    @ExtendWith(AddDeleteSectionRule.class)
    @Test
    void editRulePreparingSection() {
        servicesPage.scrollPageDown("500");
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(categoryName);
        SectionCard sectionCard = categoryCard.getSection();
        servicesPage.scrollPageDown("500");
        RulesPreparingWindow rulePreparingWindow = sectionCard.clickButtonOpenRulesPreparingSection();
        rulePreparingWindow.verifyRulesPreparingWindow();
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.clickButtonEditRule();
        editRuleWindow.verifyEditRuleWindow()
                .fillFieldTitle(generateWord())
                .fillFieldDescription(generateText())
                .clickButtonChangeRules();
        rulePreparingWindow.getRuleByTitle(word).shouldBe(Condition.visible);
        rule.clickButtonEditRule();
        ServiceCategories preparingDescription = DataBaseQuery.selectServicesCategories(sectionName);
        assertEquals(word, editRuleWindow.getTitleRule());
        assertEquals(text, editRuleWindow.getDescriptionRule());
        assertEquals(word, preparingDescription.getTitle());
        assertEquals(text, preparingDescription.getDescription());
        assertEquals("CATEGORY_PREPARING_DESCRIPTION_CHANGED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Управление правилами подготовки")
    @Story("Успешное удаление правила подготовки к разделу")
    @DisplayName("Успешное удаление правила подготовки к разделу")
    @ExtendWith(AddDeleteSectionRule.class)
    @Test
    void deleteRulePreparingSection() {
        servicesPage.scrollPageDown("500");
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(categoryName);
        SectionCard sectionCard = categoryCard.getSection();
        servicesPage.scrollPageDown("500");
        RulesPreparingWindow rulePreparingWindow = sectionCard.clickButtonOpenRulesPreparingSection();
        rulePreparingWindow.verifyRulesPreparingWindow();
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.clickButtonEditRule();
        editRuleWindow.verifyEditRuleWindow()
                .clickButtonDeleteRules();
        rulePreparingWindow.getEmptyList().shouldBe(Condition.visible);
        assertFalse(rulePreparingWindow.isExistRule());
        assertTrue(rulePreparingWindow.isExistsEmptyListRules());
        assertEquals("[]", DataBaseQuery.selectServicesCategories(sectionName).getPreparing_description());
        assertEquals("CATEGORY_PREPARING_DESCRIPTION_CHANGED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Управление правилами подготовки")
    @Story("Успешное удаление всех правил подготовки к разделу")
    @DisplayName("Успешное удаление всех правил подготовки к разделу")
    @ExtendWith(AddDeleteSectionRule.class)
    @Test
    void deleteAllRulePreparingSection() {
        servicesPage.scrollPageDown("500");
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(categoryName);
        SectionCard sectionCard = categoryCard.getSection();
        servicesPage.scrollPageDown("500");
        RulesPreparingWindow rulePreparingWindow = sectionCard.clickButtonOpenRulesPreparingSection();
        rulePreparingWindow.verifyRulesPreparingWindow()
                .clickButtonDeleteAllRules()
                .getEmptyList().shouldBe(Condition.visible);
        assertFalse(rulePreparingWindow.isExistRule());
        assertTrue(rulePreparingWindow.isExistsEmptyListRules());
        assertEquals("[]", DataBaseQuery.selectServicesCategories(sectionName).getPreparing_description());
        assertEquals("CATEGORY_PREPARING_DESCRIPTION_CHANGED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Управление правилами подготовки")
    @Story("Успешное добавление правила подготовки к подразделу")
    @DisplayName("Успешное добавление правила подготовки к подразделу")
    @ExtendWith(AddDeleteSubsection.class)
    @Test
    void addRulePreparingSubsection() {
        servicesPage.scrollPageDown("500");
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(categoryName);
        SectionCard sectionCard = categoryCard.getSection();
        servicesPage.scrollPageDown("500");
        sectionCard.clickButtonOpenSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        servicesPage.scrollPageDown("500");
        RulesPreparingWindow rulePreparingWindow = subsectionCard.clickButtonOpenRulesPreparingSubsection();
        rulePreparingWindow.verifyRulesPreparingWindow();
        AddRuleWindow addRuleWindow = rulePreparingWindow.clickButtonAddRules();
        addRuleWindow.verifyAddRuleWindow()
                .fillFieldTitle(generateWord())
                .fillFieldDescription(generateText())
                .clickSaveButton();
        rulePreparingWindow.getRuleByTitle(word).shouldBe(Condition.visible);
        assertTrue(rulePreparingWindow.isExistRule());
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.clickButtonEditRule();
        ServiceCategories preparingDescription = DataBaseQuery.selectServicesCategories(subSectionName);
        assertEquals(word, editRuleWindow.getTitleRule());
        assertEquals(text, editRuleWindow.getDescriptionRule());
        assertEquals(word, preparingDescription.getTitle());
        assertEquals(text, preparingDescription.getDescription());
        assertEquals("CATEGORY_PREPARING_DESCRIPTION_CHANGED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Управление правилами подготовки")
    @Story("Успешное редактирование правила подготовки к подразделу")
    @DisplayName("Успешное редактирование правила подготовки к подразделу")
    @ExtendWith(AddDeleteSubsectionRule.class)
    @Test
    void editRulePreparingSubsection() {
        servicesPage.scrollPageDown("500");
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(categoryName);
        SectionCard sectionCard = categoryCard.getSection();
        servicesPage.scrollPageDown("500");
        sectionCard.clickButtonOpenSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        servicesPage.scrollPageDown("500");
        RulesPreparingWindow rulePreparingWindow = subsectionCard.clickButtonOpenRulesPreparingSubsection();
        rulePreparingWindow.verifyRulesPreparingWindow();
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.clickButtonEditRule();
        editRuleWindow.verifyEditRuleWindow()
                .fillFieldTitle(generateWord())
                .fillFieldDescription(generateText())
                .clickButtonChangeRules();
        rulePreparingWindow.getRuleByTitle(word).shouldBe(Condition.visible);
        rule.clickButtonEditRule();
        ServiceCategories preparingDescription = DataBaseQuery.selectServicesCategories(subSectionName);
        assertEquals(word, editRuleWindow.getTitleRule());
        assertEquals(text, editRuleWindow.getDescriptionRule());
        assertEquals(word, preparingDescription.getTitle());
        assertEquals(text, preparingDescription.getDescription());
        assertEquals("CATEGORY_PREPARING_DESCRIPTION_CHANGED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Управление правилами подготовки")
    @Story("Успешное удаление правила подготовки к подразделу")
    @DisplayName("Успешное удаление правила подготовки к подразделу")
    @ExtendWith(AddDeleteSubsectionRule.class)
    @Test
    void deleteRulePreparingSubsection() {
        servicesPage.scrollPageDown("500");
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(categoryName);
        SectionCard sectionCard = categoryCard.getSection();
        servicesPage.scrollPageDown("500");
        sectionCard.clickButtonOpenSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        servicesPage.scrollPageDown("500");
        RulesPreparingWindow rulePreparingWindow = subsectionCard.clickButtonOpenRulesPreparingSubsection();
        rulePreparingWindow.verifyRulesPreparingWindow();
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.clickButtonEditRule();
        editRuleWindow.verifyEditRuleWindow()
                .clickButtonDeleteRules();
        rulePreparingWindow.getEmptyList().shouldBe(Condition.visible);
        assertFalse(rulePreparingWindow.isExistRule());
        assertTrue(rulePreparingWindow.isExistsEmptyListRules());
        assertEquals("[]", DataBaseQuery.selectServicesCategories(subSectionName).getPreparing_description());
        assertEquals("CATEGORY_PREPARING_DESCRIPTION_CHANGED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Управление правилами подготовки")
    @Story("Успешное удаление всех правил подготовки к подразделу")
    @DisplayName("Успешное удаление всех правил подготовки к подразделу")
    @ExtendWith(AddDeleteSubsectionRule.class)
    @Test
    void deleteAllRulePreparingSubsection() {
        servicesPage.scrollPageDown("500");
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(categoryName);
        SectionCard sectionCard = categoryCard.getSection();
        servicesPage.scrollPageDown("500");
        sectionCard.clickButtonOpenSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        servicesPage.scrollPageDown("500");
        RulesPreparingWindow rulePreparingWindow = subsectionCard.clickButtonOpenRulesPreparingSubsection();
        rulePreparingWindow.verifyRulesPreparingWindow()
                .clickButtonDeleteAllRules()
                .getEmptyList().shouldBe(Condition.visible);
        assertFalse(rulePreparingWindow.isExistRule());
        assertTrue(rulePreparingWindow.isExistsEmptyListRules());
        assertEquals("[]", DataBaseQuery.selectServicesCategories(subSectionName).getPreparing_description());
        assertEquals("CATEGORY_PREPARING_DESCRIPTION_CHANGED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }


    @Feature("Управление правилами подготовки")
    @Story("Открытие правил подготовки к категории Иные услуги")
    @DisplayName("Открытие правил подготовки к категории Иные услуги")
    @Test
    void openRulePreparingCategoryOtherServices() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.clickButtonOpenRulesPreparingCategory(NAME_OTHER_SERVICE_CATEGORY);
        assertFalse(rulePreparingWindow.isWindowAppear());
        assertEquals("Категория не найдена", servicesPage.getTextNotification());
    }

    @Feature("Управление правилами подготовки")
    @Story("Успешное добавление правила подготовки к услуге")
    @DisplayName("Успешное добавление правила подготовки к услуге")
    @ExtendWith(AddServiceInNewCategory.class)
    @Test
    void addRulePreparingService() {
        servicesPage.scrollPageDown("500");
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(categoryName);
        SectionCard sectionCard = categoryCard.getSection();
        servicesPage.scrollPageDown("500");
        sectionCard.clickButtonOpenSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        servicesPage.scrollPageDown("500");
        subsectionCard.clickButtonOpenSubsection();
        ServiceCard serviceCard = subsectionCard.getService();
        servicesPage.scrollPageDown("500");
        String codeService = serviceCard.getCodeService();
        ServiceWindow serviceWindow = serviceCard.clickButtonOpenServiceInfo();
        serviceWindow.verifyServiceWindowGeneralInfo()
                .clickRulesPreparingTab()
                .verifyServiceWindowRulesPreparing()
                .fillFieldTitle(generateWord())
                .fillFieldDescription(generateText())
                .clickAddButton();
        serviceWindow.getFieldTitle().shouldHave(value(word));
        serviceWindow.getFieldDescription().shouldHave(value(text));
        serviceWindow.getEditRuleButton().shouldBe(Condition.visible).shouldBe(Condition.enabled);
        serviceWindow.getDeleteRuleButton().shouldBe(Condition.visible).shouldBe(Condition.enabled);
        PreparingDescriptions preparingDescription = DataBaseQuery.selectDescriptionService(codeService);
        assertTrue(serviceWindow.isExistRulePreparing());
        assertEquals(word, serviceWindow.getValueTitleField());
        assertEquals(text, serviceWindow.getValueDescriptionField());
        assertEquals(word, preparingDescription.getTitle());
        assertEquals(text, preparingDescription.getDescriptions());
        assertEquals("SERVICE_PREPARING_DESCRIPTION_CHANGED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Управление правилами подготовки")
    @Story("Добавление правила подготовки к услуге с пустым полем заголовка")
    @DisplayName("Добавление правила подготовки к услуге с пустым полем заголовка")
    @ExtendWith(AddServiceInNewCategory.class)
    @Test
    void addRulePreparingServiceEmptyFieldTitle() {
        servicesPage.scrollPageDown("500");
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(categoryName);
        SectionCard sectionCard = categoryCard.getSection();
        servicesPage.scrollPageDown("500");
        sectionCard.clickButtonOpenSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        servicesPage.scrollPageDown("500");
        subsectionCard.clickButtonOpenSubsection();
        ServiceCard serviceCard = subsectionCard.getService();
        servicesPage.scrollPageDown("500");
        ServiceWindow serviceWindow = serviceCard.clickButtonOpenServiceInfo();
        serviceWindow.verifyServiceWindowGeneralInfo()
                .clickRulesPreparingTab()
                .fillFieldDescription(generateText())
                .clickAddButton();
        assertEquals("Неверный запрос (400)", servicesPage.getTextNotification());
        assertTrue(serviceWindow.isEnabledAddButton());
    }

    @Feature("Управление правилами подготовки")
    @Story("Добавление правила подготовки к услуге с пустым полем описания")
    @DisplayName("Добавление правила подготовки к услуге с пустым полем описания")
    @ExtendWith(AddServiceInNewCategory.class)
    @Test
    void addRulePreparingServiceEmptyFieldDescription() {
        servicesPage.scrollPageDown("500");
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(categoryName);
        SectionCard sectionCard = categoryCard.getSection();
        servicesPage.scrollPageDown("500");
        sectionCard.clickButtonOpenSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        servicesPage.scrollPageDown("500");
        subsectionCard.clickButtonOpenSubsection();
        ServiceCard serviceCard = subsectionCard.getService();
        servicesPage.scrollPageDown("500");
        ServiceWindow serviceWindow = serviceCard.clickButtonOpenServiceInfo();
        serviceWindow.verifyServiceWindowGeneralInfo()
                .clickRulesPreparingTab()
                .fillFieldTitle(generateWord());
        assertFalse(serviceWindow.isEnabledAddButton());
    }

    @Feature("Управление правилами подготовки")
    @Story("Сброс значений полей в окне добавления правила подготовки к услуге при переключении окна")
    @DisplayName("Сброс значений полей в окне добавления правила подготовки к услуге при переключении окна")
    @ExtendWith(AddServiceInNewCategory.class)
    @Test
    void closePreparingDescriptionsService() {
        servicesPage.scrollPageDown("500");
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(categoryName);
        SectionCard sectionCard = categoryCard.getSection();
        servicesPage.scrollPageDown("500");
        sectionCard.clickButtonOpenSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        servicesPage.scrollPageDown("500");
        subsectionCard.clickButtonOpenSubsection();
        ServiceCard serviceCard = subsectionCard.getService();
        servicesPage.scrollPageDown("500");
        ServiceWindow serviceWindow = serviceCard.clickButtonOpenServiceInfo();
        serviceWindow.verifyServiceWindowGeneralInfo()
                .clickRulesPreparingTab()
                .fillFieldTitle(generateWord())
                .fillFieldDescription(generateText())
                .clickGeneralInfoTab()
                .clickRulesPreparingTab();
        assertEquals("", serviceWindow.getValueTitleField());
        assertEquals("", serviceWindow.getValueDescriptionField());
    }

    @Feature("Управление правилами подготовки")
    @Story("Успешное редактирование правила подготовки к услуге")
    @DisplayName("Успешное редактирование правила подготовки к услуге")
    @ExtendWith(AddDeleteRuleService.class)
    @Test
    void editRulePreparingService() {
        servicesPage.scrollPageDown("500");
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(categoryName);
        SectionCard sectionCard = categoryCard.getSection();
        servicesPage.scrollPageDown("500");
        sectionCard.clickButtonOpenSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        servicesPage.scrollPageDown("500");
        subsectionCard.clickButtonOpenSubsection();
        ServiceCard serviceCard = subsectionCard.getService();
        servicesPage.scrollPageDown("500");
        String codeService = serviceCard.getCodeService();
        ServiceWindow serviceWindow = serviceCard.clickButtonOpenServiceInfo();
        serviceWindow.verifyServiceWindowGeneralInfo()
                .clickRulesPreparingTab()
                .fillFieldTitle(generateWord())
                .fillFieldDescription(generateText())
                .clickChangeButton();
        serviceWindow.getFieldTitle().shouldHave(value(word));
        serviceWindow.getFieldDescription().shouldHave(value(text));
        PreparingDescriptions preparingDescription = DataBaseQuery.selectDescriptionService(codeService);
        assertEquals(word, serviceWindow.getValueTitleField());
        assertEquals(text, serviceWindow.getValueDescriptionField());
        assertEquals(word, preparingDescription.getTitle());
        assertEquals(text, preparingDescription.getDescriptions());
        assertEquals("SERVICE_PREPARING_DESCRIPTION_CHANGED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Управление правилами подготовки")
    @Story("Сохранение значений полей заголовка и описания правила подготовки без изменений данных")
    @DisplayName("Сохранение значений полей заголовка и описания правила подготовки без изменений данных")
    @ExtendWith(AddDeleteRuleService.class)
    @Test
    void saveRulePreparingServiceNotChange() {
        servicesPage.scrollPageDown("500");
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(categoryName);
        SectionCard sectionCard = categoryCard.getSection();
        servicesPage.scrollPageDown("500");
        sectionCard.clickButtonOpenSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        servicesPage.scrollPageDown("500");
        subsectionCard.clickButtonOpenSubsection();
        ServiceCard serviceCard = subsectionCard.getService();
        servicesPage.scrollPageDown("500");
        String codeService = serviceCard.getCodeService();
        ServiceWindow serviceWindow = serviceCard.clickButtonOpenServiceInfo();
        serviceWindow.verifyServiceWindowGeneralInfo()
                .clickRulesPreparingTab()
                .clickChangeButton();
        PreparingDescriptions preparingDescription = DataBaseQuery.selectDescriptionService(codeService);
        assertEquals(word, serviceWindow.getValueTitleField());
        assertEquals(text, serviceWindow.getValueDescriptionField());
        assertEquals(word, preparingDescription.getTitle());
        assertEquals(text, preparingDescription.getDescriptions());
    }

    @Feature("Управление правилами подготовки")
    @Story("Удаление правила подготовки к услуге")
    @DisplayName("Удаление правила подготовки к услуге")
    @ExtendWith(AddRuleService.class)
    @Test
    void deleteRulePreparingService() {
        servicesPage.scrollPageDown("500");
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(categoryName);
        SectionCard sectionCard = categoryCard.getSection();
        servicesPage.scrollPageDown("500");
        sectionCard.clickButtonOpenSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        servicesPage.scrollPageDown("500");
        subsectionCard.clickButtonOpenSubsection();
        ServiceCard serviceCard = subsectionCard.getService();
        servicesPage.scrollPageDown("500");
        String codeService = serviceCard.getCodeService();
        ServiceWindow serviceWindow = serviceCard.clickButtonOpenServiceInfo();
        serviceWindow.verifyServiceWindowGeneralInfo()
                .clickRulesPreparingTab()
                .clickDeleteButton();
        serviceWindow.getAddRuleButton().shouldBe(Condition.visible).shouldNotBe(Condition.enabled);
        assertTrue(serviceWindow.isExistEmptyList());
        assertFalse(serviceWindow.isEnabledAddButton());
        assertEquals("[]", DataBaseQuery.selectDescriptionService(codeService).getDescription());
        assertEquals("SERVICE_PREPARING_DESCRIPTION_CHANGED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Управление категориями")
    @Story("Успешное добавление раздела в категорию")
    @DisplayName("Успешное добавление раздела в категорию")
    @ExtendWith(AddDeleteCategorySection.class)
    @Test
    void addSectionInCategory() {
        servicesPage.scrollPageDown("500");
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(categoryName);
        categoryCard.verifyCategoryCard();
        AddSectionWindow addSectionWindow = categoryCard.clickButtonAddSection();
        addSectionWindow.verifyAddSectionWindow()
                .fillNameSectionField(generateSectionName())
                .clickButtonAddSection();
        addSectionWindow.getAddSectionWindow().shouldNotBe(Condition.visible);
        servicesPage.getCategoryByName(categoryName).shouldBe(Condition.visible);
        servicesPage.scrollPageDown("500");
        servicesPage.clickButtonOpenCategory(categoryName);
        SectionCard sectionCard = categoryCard.getSection();
        assertTrue(categoryCard.isExistSectionCard());
        assertEquals(sectionName, sectionCard.getNameSection());
        assertNotNull(DataBaseQuery.selectServicesCategories(sectionName));
        assertEquals(AddDeleteCategorySection.getCategoryId(), DataBaseQuery.selectServicesCategories(sectionName).getParent_id());
        assertEquals("CATEGORY_CREATED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Управление категориями")
    @Story("Сброс значений полей в окне добавления раздела в категорию при закрытии окна")
    @DisplayName("Сброс значений полей в окне добавления раздела в категорию при закрытии окна")
    @ExtendWith(AddDeleteCategory.class)
    @Test
    void closeWindowAddSection() {
        servicesPage.scrollPageDown("500");
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(categoryName);
        AddSectionWindow addSectionWindow = categoryCard.clickButtonAddSection();
        addSectionWindow.verifyAddSectionWindow()
                .fillNameSectionField(generateSectionName())
                .closeWindowAddSection();
        assertFalse(addSectionWindow.isWindowAppear());
        categoryCard.clickButtonAddSection();
        assertEquals("", addSectionWindow.getValueNameField());
    }

    @Feature("Управление категориями")
    @Story("Отображение уведомления об обязательности поля")
    @DisplayName("Отображение уведомления об обязательности поля")
    @ExtendWith(AddDeleteCategory.class)
    @Test
    void displayNotificationAboutRequiredFieldsWindowAddSection() {
        servicesPage.scrollPageDown("500");
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(categoryName);
        AddSectionWindow addSectionWindow = categoryCard.clickButtonAddSection();
        addSectionWindow.verifyAddSectionWindow()
                .clickFieldName();
        assertFalse(addSectionWindow.isEnabledAddButton());
        assertEquals("Обязательное поле", addSectionWindow.getErrorFieldName());
    }

    @Feature("Управление категориями")
    @Story("Очистка поля имени раздела через кнопку в окне добавления раздела")
    @DisplayName("Очистка поля имени раздела через кнопку в окне добавления раздела")
    @ExtendWith(AddDeleteCategory.class)
    @Test
    void clearFieldWindowAddSectionThroughButtonClear() {
        servicesPage.scrollPageDown("500");
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(categoryName);
        AddSectionWindow addSectionWindow = categoryCard.clickButtonAddSection();
        addSectionWindow.verifyAddSectionWindow()
                .fillNameSectionField(generateSectionName())
                .clearButtonNameField();
        assertEquals("", addSectionWindow.getValueNameField());
        assertEquals("Обязательное поле", addSectionWindow.getErrorFieldName());
    }

    @Feature("Управление категориями")
    @Story("Отмена добавления раздела в категорию")
    @DisplayName("Отмена добавления раздела в категорию")
    @ExtendWith(AddDeleteCategory.class)
    @Test
    void cancelAddSectionInCategory() {
        servicesPage.scrollPageDown("500");
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(categoryName);
        AddSectionWindow addSectionWindow = categoryCard.clickButtonAddSection();
        addSectionWindow.verifyAddSectionWindow()
                .clickCancelButtonAddSection();
        assertFalse(addSectionWindow.isWindowAppear());
        assertFalse(categoryCard.isExistSectionCard());
    }

    @Feature("Управление категориями")
    @Story("Успешное редактирование раздела в категории")
    @DisplayName("Успешное редактирование раздела в категории")
    @ExtendWith(AddDeleteSection.class)
    @Test
    void editSectionInCategory() {
        servicesPage.scrollPageDown("500");
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(categoryName);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.verifySectionCard();
        EditSectionWindow editSectionWindow = sectionCard.clickButtonEditSection();
        editSectionWindow.verifyEditSectionWindow()
                .fillNameField(generateSectionName())
                .clickButtonSaveChange();
        editSectionWindow.getEditSectionWindow().shouldNotBe(Condition.visible);
        servicesPage.getCategoryByName(categoryName).shouldBe(Condition.visible);
        servicesPage.scrollPageDown("500");
        servicesPage.clickButtonOpenCategory(categoryName);
        assertEquals(sectionName, sectionCard.getNameSection());
        assertNotNull(DataBaseQuery.selectServicesCategories(sectionName));
        assertEquals(AddDeleteSection.getCategoryId(), DataBaseQuery.selectServicesCategories(sectionName).getParent_id());
        assertEquals("CATEGORY_CHANGED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Управление категориями")
    @Story("Сохранение значений поля в окне редактирования раздела после закрытия окна")
    @DisplayName("Сохранение значений поля в окне редактирования раздела после закрытия окна")
    @ExtendWith(AddDeleteSection.class)
    @Test
    void closeWindowEditSectionInCategory() {
        servicesPage.scrollPageDown("500");
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(categoryName);
        SectionCard sectionCard = categoryCard.getSection();
        EditSectionWindow editSectionWindow = sectionCard.clickButtonEditSection();
        editSectionWindow.verifyEditSectionWindow()
                .fillNameField(generateWord())
                .closeWindowEditSection();
        assertFalse(editSectionWindow.isWindowAppear());
        assertEquals(sectionName, sectionCard.getNameSection());
        assertNotNull(DataBaseQuery.selectServicesCategories(sectionName));
    }

    @Feature("Управление категориями")
    @Story("Сохранение значений поля названия раздела без изменений данных")
    @DisplayName("Сохранение значений поля названия раздела без изменений данных")
    @ExtendWith(AddDeleteSection.class)
    @Test
    void saveSectionInCategoryNotChange() {
        servicesPage.scrollPageDown("500");
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(categoryName);
        SectionCard sectionCard = categoryCard.getSection();
        EditSectionWindow editSectionWindow = sectionCard.clickButtonEditSection();
        editSectionWindow.verifyEditSectionWindow()
                .clickButtonSaveChange();
        editSectionWindow.getEditSectionWindow().shouldNotBe(Condition.visible);
        servicesPage.getCategoryByName(categoryName).shouldBe(Condition.visible);
        servicesPage.scrollPageDown("500");
        servicesPage.clickButtonOpenCategory(categoryName);
        assertEquals(sectionName, sectionCard.getNameSection());
        assertNotNull(DataBaseQuery.selectServicesCategories(sectionName));
    }

    @Feature("Управление категориями")
    @Story("Редактирование названия раздела с пустым полем")
    @DisplayName("Редактирование названия раздела с пустым полем")
    @ExtendWith(AddDeleteSection.class)
    @Test
    void editSectionInCategoryEmptyFieldName() {
        servicesPage.scrollPageDown("500");
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(categoryName);
        SectionCard sectionCard = categoryCard.getSection();
        EditSectionWindow editSectionWindow = sectionCard.clickButtonEditSection();
        editSectionWindow.verifyEditSectionWindow()
                .clearNameField()
                .clickButtonSaveChange();
        assertTrue(editSectionWindow.isWindowAppear());
        assertNotNull(DataBaseQuery.selectServicesCategories(sectionName));
    }

    @Feature("Управление категориями")
    @Story("Успешное удаление раздела из категории")
    @DisplayName("Успешное удаление раздела из категории")
    @ExtendWith(AddSection.class)
    @Test
    void deleteSectionInCategory() {
        servicesPage.scrollPageDown("500");
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(categoryName);
        SectionCard sectionCard = categoryCard.getSection();
        DeleteSectionWindow deleteSectionWindow = sectionCard.clickButtonDeleteSection();
        deleteSectionWindow.verifyDeleteSectionWindow();
        assertTrue(deleteSectionWindow.verifyNameSection(sectionName));
        deleteSectionWindow.clickButtonDeleteSection();
        deleteSectionWindow.getDeleteSectionWindow().shouldNotBe(Condition.visible);
        servicesPage.getCategoryByName(categoryName).shouldBe(Condition.visible);
        servicesPage.scrollPageDown("500");
        servicesPage.clickButtonOpenCategory(categoryName);
        assertFalse(categoryCard.isExistSectionCard());
        assertTrue(categoryCard.isExistEmptyList());
        assertNull(DataBaseQuery.selectServicesCategories(sectionName));
        assertEquals("CATEGORY_DELETED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Управление категориями")
    @Story("Отмена удаления раздела из категории")
    @DisplayName("Отмена удаления раздела из категории")
    @ExtendWith(AddDeleteSection.class)
    @Test
    void cancelDeleteSectionInCategory() {
        servicesPage.scrollPageDown("500");
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(categoryName);
        SectionCard sectionCard = categoryCard.getSection();
        DeleteSectionWindow deleteSectionWindow = sectionCard.clickButtonDeleteSection();
        deleteSectionWindow.verifyDeleteSectionWindow()
                .clickCancelButtonDeleteSection();
        assertFalse(deleteSectionWindow.isWindowAppear());
        assertTrue(categoryCard.isExistSectionCard());
        assertNotNull(DataBaseQuery.selectServicesCategories(sectionName));
        sectionCard.clickButtonDeleteSection();
        deleteSectionWindow.closeWindowDeleteSection();
        assertFalse(deleteSectionWindow.isWindowAppear());
    }


    @Feature("Управление категориями")
    @Story("Успешное добавление подраздела в раздел")
    @DisplayName("Успешное добавление подраздела в раздел")
    @ExtendWith(AddDeleteSectionSubsection.class)
    @Test
    void addSubsectionInCategory() {
        servicesPage.scrollPageDown("500");
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(categoryName);
        SectionCard sectionCard = categoryCard.getSection();
        servicesPage.scrollPageDown("500");
        sectionCard.clickButtonOpenSection();
        AddSectionWindow addSectionWindow = sectionCard.clickButtonAddSubsection();
        addSectionWindow.verifyAddSectionWindow()
                .fillNameSectionField(generateSubSectionName())
                .clickButtonAddSection();
        addSectionWindow.getAddSectionWindow().shouldNotBe(Condition.visible);
        servicesPage.getCategoryByName(categoryName).shouldBe(Condition.visible);
        servicesPage.scrollPageDown("500");
        servicesPage.clickButtonOpenCategory(categoryName);
        servicesPage.scrollPageDown("500");
        sectionCard.clickButtonOpenSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        assertTrue(sectionCard.isExistSubsectionCard());
        assertEquals(subSectionName, subsectionCard.getNameSubsection());
        assertNotNull(DataBaseQuery.selectServicesCategories(subSectionName));
        assertEquals(AddDeleteSectionSubsection.getSectionId(), DataBaseQuery.selectServicesCategories(subSectionName).getParent_id());
        assertEquals("CATEGORY_CREATED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Управление категориями")
    @Story("Успешное редактирование подраздела в разделе")
    @DisplayName("Успешное редактирование подраздела в разделе")
    @ExtendWith(AddDeleteSubsection.class)
    @Test
    void editSubsectionInCategory() {
        servicesPage.scrollPageDown("500");
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(categoryName);
        SectionCard sectionCard = categoryCard.getSection();
        servicesPage.scrollPageDown("500");
        sectionCard.clickButtonOpenSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        subsectionCard.verifySubsectionCard();
        EditSectionWindow editSectionWindow = subsectionCard.clickButtonEditSubsection();
        editSectionWindow.verifyEditSectionWindow()
                .fillNameField(generateSubSectionName())
                .clickButtonSaveChange();
        editSectionWindow.getEditSectionWindow().shouldNotBe(Condition.visible);
        servicesPage.getCategoryByName(categoryName).shouldBe(Condition.visible);
        servicesPage.scrollPageDown("500");
        servicesPage.clickButtonOpenCategory(categoryName);
        servicesPage.scrollPageDown("500");
        sectionCard.clickButtonOpenSection();
        assertEquals(subSectionName, subsectionCard.getNameSubsection());
        assertNotNull(DataBaseQuery.selectServicesCategories(subSectionName));
        assertEquals(AddDeleteSubsection.getSectionId(), DataBaseQuery.selectServicesCategories(subSectionName).getParent_id());
        assertEquals("CATEGORY_CHANGED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Управление категориями")
    @Story("Успешное удаление подраздела из раздела")
    @DisplayName("Успешное удаление подраздела из раздела")
    @ExtendWith(AddSubsection.class)
    @Test
    void deleteSubsectionInCategory() {
        servicesPage.scrollPageDown("500");
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(categoryName);
        SectionCard sectionCard = categoryCard.getSection();
        servicesPage.scrollPageDown("500");
        sectionCard.clickButtonOpenSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        DeleteSectionWindow deleteSectionWindow = subsectionCard.clickButtonDeleteSubsection();
        deleteSectionWindow.verifyDeleteSectionWindow();
        assertTrue(deleteSectionWindow.verifyNameSection(subSectionName));
        deleteSectionWindow.clickButtonDeleteSection();
        deleteSectionWindow.getDeleteSectionWindow().shouldNotBe(Condition.visible);
        servicesPage.getCategoryByName(categoryName).shouldBe(Condition.visible);
        servicesPage.scrollPageDown("500");
        servicesPage.clickButtonOpenCategory(categoryName);
        servicesPage.scrollPageDown("500");
        sectionCard.clickButtonOpenSection();
        servicesPage.scrollPageDown("500");
        assertFalse(sectionCard.isExistSubsectionCard());
        assertTrue(sectionCard.isExistEmptyList());
        assertNull(DataBaseQuery.selectServicesCategories(subSectionName));
        assertEquals("CATEGORY_DELETED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Управление категориями")
    @Story("Смена последовательности отображения категории Иные услуги")
    @DisplayName("Смена последовательности отображения категории Иные услуги")
    @ExtendWith(AddDeleteCategory.class)
    @Test
    void changeSequenceDisplayCategoriesCategoryOtherServices() {
        servicesPage.scrollPageDown("500");
        int sequenceFirstCategory = servicesPage.getCategoryIndexByName(categoryName);
        int sequenceSecondCategory = servicesPage.getCategoryIndexByName(NAME_OTHER_SERVICE_CATEGORY);
        int sequenceFirstCategoryDB = DataBaseQuery.selectServicesCategories(categoryName).getSequence();
        servicesPage.changeSequenceDisplayCategories(categoryName, NAME_OTHER_SERVICE_CATEGORY);
        assertEquals("Нельзя перемещать раздел \"Иные услуги\"", servicesPage.getTextNotification());
        assertEquals(sequenceFirstCategory, servicesPage.getCategoryIndexByName(categoryName));
        assertEquals(sequenceSecondCategory, servicesPage.getCategoryIndexByName(NAME_OTHER_SERVICE_CATEGORY));
        assertEquals(sequenceFirstCategoryDB, DataBaseQuery.selectServicesCategories(categoryName).getSequence());
        assertNull(DataBaseQuery.selectServicesCategories(NAME_OTHER_SERVICE_CATEGORY));
    }

    @Feature("Управление категориями")
    @Story("Удаление раздела, имеющего подраздел")
    @DisplayName("Удаление раздела, имеющего подраздел")
    @ExtendWith(AddDeleteSubsection.class)
    @Test
    void deleteSectionThatHasSubsectionInCategory() {
        servicesPage.scrollPageDown("500");
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(categoryName);
        SectionCard sectionCard = categoryCard.getSection();
        servicesPage.scrollPageDown("500");
        DeleteSectionWindow deleteSectionWindow = sectionCard.clickButtonDeleteSection();
        deleteSectionWindow.verifyDeleteSectionWindow()
                .clickButtonDeleteSection();
        assertEquals("Нельзя удалить категорию, т.к. имеются дочерние объекты", servicesPage.getTextNotification());
        assertTrue(sectionCard.isExistSubsectionCard());
        assertFalse(sectionCard.isExistEmptyList());
        assertNotNull(DataBaseQuery.selectServicesCategories(subSectionName));
    }

    @Feature("Управление услугами")
    @Story("Открытие карточки услуги")
    @DisplayName("Открытие карточки услуги")
    @ExtendWith(AddServiceInNewCategory.class)
    @Test
    void openCardService() {
        servicesPage.scrollPageDown("500");
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(categoryName);
        SectionCard sectionCard = categoryCard.getSection();
        servicesPage.scrollPageDown("500");
        sectionCard.clickButtonOpenSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        servicesPage.scrollPageDown("500");
        subsectionCard.clickButtonOpenSubsection();
        ServiceCard serviceCard = subsectionCard.getService();
        servicesPage.scrollPageDown("500");
        serviceCard.verifyServiceCard();
        String nameService = serviceCard.getNameService();
        String codeService = serviceCard.getCodeService();
        ServiceWindow serviceWindow = serviceCard.clickButtonOpenServiceInfo();
        serviceWindow.verifyServiceWindowGeneralInfo();
        assertEquals(nameService, serviceWindow.getHeaderServiceWindow());
        assertEquals(nameService, serviceWindow.getNameService());
        assertEquals(categoryName + " / " + sectionName + " / " + subSectionName + " / " + nameService, serviceWindow.getPathService());
        assertEquals(codeService, serviceWindow.getCodeService());
    }

    @Feature("Управление услугами")
    @Story("Успешный перенос услуги в категорию Иные услуги")
    @DisplayName("Успешный перенос услуги в категорию Иные услуги")
    @ExtendWith(AddServiceInNewCategoryForTransfer.class)
    @Test
    void transferServiceToOtherServices() {
        servicesPage.scrollPageDown("500");
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(categoryName);
        SectionCard sectionCard = categoryCard.getSection();
        servicesPage.scrollPageDown("500");
        sectionCard.clickButtonOpenSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        servicesPage.scrollPageDown("500");
        subsectionCard.clickButtonOpenSubsection();
        ServiceCard serviceCard = subsectionCard.getService();
        servicesPage.scrollPageDown("500");
        String codeService = serviceCard.getCodeService();
        ServiceWindow serviceWindow = serviceCard.clickButtonOpenServiceInfo();
        serviceWindow.verifyServiceWindowGeneralInfo()
                .clickServiceTransferTab()
                .verifyServiceWindowServiceTransfer()
                .clickCategoryForTransfer(NAME_OTHER_SERVICE_CATEGORY)
                .clickButtonTransferServiceToOtherServices();
        serviceWindow.getServiceWindow().shouldNotBe(Condition.visible);
        servicesPage.getCategoryByName(categoryName).shouldBe(Condition.visible);
        servicesPage.scrollPageDown("500");
        servicesPage.clickButtonOpenCategory(categoryName);
        servicesPage.scrollPageDown("500");
        sectionCard.clickButtonOpenSection();
        servicesPage.scrollPageDown("500");
        subsectionCard.clickButtonOpenSubsection();
        servicesPage.scrollPageDown("500");
        assertTrue(subsectionCard.isExistEmptyList());
        servicesPage.clickButtonCloseCategory(categoryName);
        servicesPage.clickButtonOpenCategory(NAME_OTHER_SERVICE_CATEGORY);
        assertTrue(categoryCard.isExistService(codeService));
        assertEquals("SERVICE_TRANSFER_LOCATION_CHANGED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Управление услугами")
    @Story("Успешное удаление услуги из категории")
    @DisplayName("Успешное удаление услуги из категории")
    @ExtendWith(AddServiceInNewCategoryForTransfer.class)
    @Test
    void deleteServiceFromCategory() {
        servicesPage.scrollPageDown("500");
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(categoryName);
        SectionCard sectionCard = categoryCard.getSection();
        servicesPage.scrollPageDown("500");
        sectionCard.clickButtonOpenSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        servicesPage.scrollPageDown("500");
        subsectionCard.clickButtonOpenSubsection();
        ServiceCard serviceCard = subsectionCard.getService();
        servicesPage.scrollPageDown("500");
        String codeService = serviceCard.getCodeService();
        ServiceWindow serviceWindow = serviceCard.clickButtonOpenServiceInfo();
        serviceWindow.verifyServiceWindowGeneralInfo()
                .clickButtonDeleteService();
        serviceWindow.getServiceWindow().shouldNotBe(Condition.visible);
        servicesPage.getCategoryByName(categoryName).shouldBe(Condition.visible);
        servicesPage.scrollPageDown("500");
        servicesPage.clickButtonOpenCategory(categoryName);
        servicesPage.scrollPageDown("500");
        sectionCard.clickButtonOpenSection();
        servicesPage.scrollPageDown("500");
        subsectionCard.clickButtonOpenSubsection();
        servicesPage.scrollPageDown("500");
        assertTrue(subsectionCard.isExistEmptyList());
        servicesPage.clickButtonCloseCategory(categoryName);
        servicesPage.clickButtonOpenCategory(NAME_OTHER_SERVICE_CATEGORY);
        assertTrue(categoryCard.isExistService(codeService));
        assertEquals("SERVICE_TRANSFER_LOCATION_CHANGED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Управление услугами")
    @Story("Успешный перенос услуги в категорию Иные услуги при удалении раздела")
    @DisplayName("Успешный перенос услуги в категорию Иные услуги при удалении раздела")
    @ExtendWith(AddServiceInNewCategoryForDeleteSection.class)
    @Test
    void transferServiceToOtherServicesIfDeleteSection() {
        servicesPage.scrollPageDown("500");
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(categoryName);
        SectionCard sectionCard = categoryCard.getSection();
        servicesPage.scrollPageDown("500");
        sectionCard.clickButtonOpenSection();
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        servicesPage.scrollPageDown("500");
        subsectionCard.clickButtonOpenSubsection();
        ServiceCard serviceCard = subsectionCard.getService();
        servicesPage.scrollPageDown("500");
        String codeService = serviceCard.getCodeService();
        subsectionCard.clickButtonCloseSubsection(subSectionName);
        DeleteSectionWindow deleteSectionWindow = subsectionCard.clickButtonDeleteSubsection();
        deleteSectionWindow.verifyDeleteSectionWindow()
                .clickButtonDeleteSection();
        deleteSectionWindow.getDeleteSectionWindow().shouldNotBe(Condition.visible);
        servicesPage.getCategoryByName(categoryName).shouldBe(Condition.visible);
        servicesPage.scrollPageDown("500");
        servicesPage.clickButtonOpenCategory(categoryName);
        servicesPage.scrollPageDown("500");
        sectionCard.clickButtonOpenSection();
        servicesPage.scrollPageDown("500");
        assertTrue(sectionCard.isExistEmptyList());
        servicesPage.clickButtonCloseCategory(categoryName);
        servicesPage.clickButtonOpenCategory(NAME_OTHER_SERVICE_CATEGORY);
        assertTrue(categoryCard.isExistService(codeService));
        assertEquals("CATEGORY_DELETED_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
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
        serviceWindow.verifyServiceWindowGeneralInfo()
                .clickServiceTransferTab()
                .clickCategoryForTransfer(NAME_OTHER_SERVICE_CATEGORY)
                .clickButtonTransferServiceToOtherServices();
        assertEquals("Категории источника и приёмника для переноса не должны совпадать", servicesPage.getTextNotification());
        serviceWindow.closeWindowInfoService();
        assertFalse(serviceWindow.isWindowAppear());
        assertTrue(categoryCard.isExistService(codeService));
    }

    @Feature("Управление категориями")
    @Story("Сворачивание дерева иерархии")
    @DisplayName("Сворачивание дерева иерархии")
    @ExtendWith(AddServiceInNewCategory.class)
    @Test
    void closeCategory() {
        servicesPage.scrollPageDown("500");
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(categoryName);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.clickButtonOpenSection();
        servicesPage.scrollPageDown("500");
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        subsectionCard.clickButtonOpenSubsection();
        servicesPage.scrollPageDown("500");
        subsectionCard.clickButtonCloseSubsection(subSectionName);
        servicesPage.scrollPageDown("500");
        assertFalse(subsectionCard.isExistService());
        sectionCard.clickButtonCloseSection(sectionName);
        servicesPage.clickButtonCloseCategory(categoryName);
        assertFalse(categoryCard.isExistSectionCard());
    }

    @Feature("Управление категориями")
    @Story("Смена последовательности отображения категорий")
    @DisplayName("Смена последовательности отображения категорий")
    @ExtendWith(AddDeleteCategory.class)
    @Test
    void changeSequenceDisplayCategoriesCategories() {
        servicesPage.scrollPageDown("500");
        int sequenceFirstCategory = servicesPage.getCategoryIndexByName("Диагностика");
        int sequenceSecondCategory = servicesPage.getCategoryIndexByName(categoryName);
        int sequenceFirstCategoryDB = DataBaseQuery.selectServicesCategories("Диагностика").getSequence();
        int sequenceSecondCategoryDB = DataBaseQuery.selectServicesCategories(categoryName).getSequence();
        servicesPage.changeSequenceDisplayCategories("Диагностика", categoryName);
        servicesPage.scrollPageDown("500");
        servicesPage.getCategoryByName(categoryName).shouldBe(Condition.visible);
        assertEquals(sequenceFirstCategory, servicesPage.getCategoryIndexByName(categoryName));
        assertEquals(sequenceSecondCategory, servicesPage.getCategoryIndexByName("Диагностика"));
        assertEquals(sequenceFirstCategoryDB, DataBaseQuery.selectServicesCategories(categoryName).getSequence());
        assertEquals(sequenceSecondCategoryDB, DataBaseQuery.selectServicesCategories("Диагностика").getSequence());
        assertEquals("CATEGORY_SWAP_LOCATION_IN_LIST_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Управление категориями")
    @Story("Смена последовательности отображения разделов")
    @DisplayName("Смена последовательности отображения разделов")
    @ExtendWith(AddTwoSections.class)
    @Test
    void changeSequenceDisplayCategoriesSections() {
        servicesPage.scrollPageDown("500");
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(categoryName);
        servicesPage.scrollPageDown("500");
        int sequenceFirstSection = categoryCard.getSectionIndexByName(sectionName);
        int sequenceSecondSection = categoryCard.getSectionIndexByName(subSectionName);
        int sequenceFirstSectionDB = DataBaseQuery.selectServicesCategories(sectionName).getSequence();
        int sequenceSecondSectionDB = DataBaseQuery.selectServicesCategories(subSectionName).getSequence();
        categoryCard.changeSequenceDisplaySections(sectionName, subSectionName);
        servicesPage.getCategoryByName(categoryName).shouldBe(Condition.visible);
        servicesPage.scrollPageDown("500");
        servicesPage.clickButtonOpenCategory(categoryName);
        servicesPage.scrollPageDown("500");
        assertEquals(sequenceFirstSection, categoryCard.getSectionIndexByName(subSectionName));
        assertEquals(sequenceSecondSection, categoryCard.getSectionIndexByName(sectionName));
        assertEquals(sequenceFirstSectionDB, DataBaseQuery.selectServicesCategories(subSectionName).getSequence());
        assertEquals(sequenceSecondSectionDB, DataBaseQuery.selectServicesCategories(sectionName).getSequence());
        assertEquals("CATEGORY_SWAP_LOCATION_IN_LIST_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Управление категориями")
    @Story("Смена последовательности отображения подразделов")
    @DisplayName("Смена последовательности отображения подразделов")
    @ExtendWith(AddTwoSubsections.class)
    @Test
    void changeSequenceDisplayCategoriesSubsections() {
        servicesPage.scrollPageDown("500");
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(categoryName);
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.clickButtonOpenSection();
        servicesPage.scrollPageDown("500");
        int sequenceFirstSubsection = sectionCard.getSubsectionIndexByName(subSectionName);
        int sequenceSecondSubsection = sectionCard.getSubsectionIndexByName(text);
        int sequenceFirstSubsectionDB = DataBaseQuery.selectServicesCategories(subSectionName).getSequence();
        int sequenceSecondSubsectionDB = DataBaseQuery.selectServicesCategories(text).getSequence();
        sectionCard.changeSequenceDisplaySubsections(subSectionName, text);
        servicesPage.getCategoryByName(categoryName).shouldBe(Condition.visible);
        servicesPage.scrollPageDown("500");
        servicesPage.clickButtonOpenCategory(categoryName);
        servicesPage.scrollPageDown("500");
        sectionCard.clickButtonOpenSection();
        servicesPage.scrollPageDown("500");
        assertEquals(sequenceFirstSubsection, categoryCard.getSectionIndexByName(text));
        assertEquals(sequenceSecondSubsection, categoryCard.getSectionIndexByName(subSectionName));
        assertEquals(sequenceFirstSubsectionDB, DataBaseQuery.selectServicesCategories(text).getSequence());
        assertEquals(sequenceSecondSubsectionDB, DataBaseQuery.selectServicesCategories(subSectionName).getSequence());
        assertEquals("CATEGORY_SWAP_LOCATION_IN_LIST_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());
    }

    @Feature("Управление категориями")
    @Story("Смена последовательности отображения услуг")
    @DisplayName("Смена последовательности отображения услуг")
    @ExtendWith(AddTwoServices.class)
    @Test
    void changeSequenceDisplayCategoriesServices() {
        servicesPage.scrollPageDown("500");
        CategoryCard categoryCard = servicesPage.clickButtonOpenCategory(categoryName);
        servicesPage.scrollPageDown("500");
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.clickButtonOpenSection();
        servicesPage.scrollPageDown("500");
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        subsectionCard.clickButtonOpenSubsection();
        servicesPage.scrollPageDown("500");
        String codeFirst = AddTwoServices.getServiceCodeFirst();
        String codeSecond = AddTwoServices.getServiceCodeSecond();
        int sequenceFirstService = subsectionCard.getServiceByCode(codeFirst);
        int sequenceSecondService = subsectionCard.getServiceByCode(codeSecond);
        int sequenceFirstServiceDB = DataBaseQuery.selectAllService(codeFirst).getSequence();
        int sequenceSecondServiceDB = DataBaseQuery.selectAllService(codeSecond).getSequence();
        subsectionCard.changeSequenceDisplayServices(codeFirst, codeSecond);
        servicesPage.getCategoryByName(categoryName).shouldBe(Condition.visible);
        servicesPage.scrollPageDown("500");
        servicesPage.clickButtonOpenCategory(categoryName);
        servicesPage.scrollPageDown("500");
        sectionCard.clickButtonOpenSection();
        servicesPage.scrollPageDown("500");
        subsectionCard.clickButtonOpenSubsection();
        servicesPage.scrollPageDown("500");
        assertEquals(sequenceFirstService, subsectionCard.getServiceByCode(codeSecond));
        assertEquals(sequenceSecondService, subsectionCard.getServiceByCode(codeFirst));
        assertEquals(sequenceFirstServiceDB, DataBaseQuery.selectAllService(codeSecond).getSequence());
        assertEquals(sequenceSecondServiceDB, DataBaseQuery.selectAllService(codeFirst).getSequence());
        assertEquals("SERVICE_SWAP_LOCATION_IN_LIST_SUCCESS", DataBaseQuery.selectLog(LOGIN_ADMIN).getCode());

    }
}

