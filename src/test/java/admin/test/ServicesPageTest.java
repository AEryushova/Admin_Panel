package admin.test;

import admin.config.DataConfig;
import admin.pages.BasePage.BasePage;
import admin.pages.HeaderMenu.HeaderMenu;
import admin.pages.ServicesPage.*;
import admin.utils.dbUtils.DataBaseQuery;
import admin.utils.dbUtils.dbaseData.ServiceCategories;
import admin.utils.preparationDataTests.general.NotificationDecorator;
import admin.utils.preparationDataTests.services.*;
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
        BrowserManager.openBrowser(DataConfig.UserData.getLOGIN_ADMIN(), DataConfig.UserData.getPASSWORD_ADMIN());
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
    @Story("Успешное добавление правила подоготовки к категории")
    @ExtendWith(DeleteRuleDecorator.class)
    @Test
    void addRulePreparingCategory() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(DataConfig.DataTest.getCATEGORY_RULES());
        rulePreparingWindow.rulesPreparingWindow();
        AddRuleWindow addRuleWindow = rulePreparingWindow.openAddRulesWindow();
        addRuleWindow.addRuleWindow();
        addRuleWindow.fillFieldTitle(DataConfig.DataTest.getRULE_TITLE());
        addRuleWindow.fillFieldDescription(DataConfig.DataTest.getRULE_DESCRIPTION());
        addRuleWindow.clickSaveButton();
        Selenide.sleep(3000);
        assertTrue(rulePreparingWindow.isExistRule());
        Rule rule = rulePreparingWindow.getRule();
        rule.rule();
        EditRuleWindow editRuleWindow = rule.openEditRuleWindow();
        ServiceCategories preparingDescription = DataBaseQuery.selectServicesInfo(DataConfig.DataTest.getCATEGORY_RULES());
        assertEquals(DataConfig.DataTest.getRULE_TITLE(), editRuleWindow.getTitleRule());
        assertEquals(DataConfig.DataTest.getRULE_DESCRIPTION(), editRuleWindow.getDescriptionRule());
        assertEquals(DataConfig.DataTest.getRULE_TITLE(), preparingDescription.getTitle());
        assertEquals(DataConfig.DataTest.getRULE_DESCRIPTION(), preparingDescription.getDescription());
        assertFalse(addRuleWindow.isWindowAppear());
    }

    @Feature("Управление правилами подготовки")
    @Story("Добавление правила подоготовки к категории с пустым полем заголовка")
    @ExtendWith({DeleteRuleDecorator.class, NotificationDecorator.class})
    @Test
    void addRulePreparingCategoryEmptyFieldTitle() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(DataConfig.DataTest.getCATEGORY_RULES());
        AddRuleWindow addRuleWindow = rulePreparingWindow.openAddRulesWindow();
        addRuleWindow.fillFieldDescription(DataConfig.DataTest.getRULE_DESCRIPTION());
        addRuleWindow.clickSaveButton();
        assertEquals("Неверный запрос (400)", servicesPage.getNotification());
        assertFalse(rulePreparingWindow.isExistRule());
    }

    @Feature("Управление правилами подготовки")
    @Story("Добавление правила подоготовки к категории с пустым полем описания")
    @ExtendWith({DeleteRuleDecorator.class, NotificationDecorator.class})
    @Test
    void addRulePreparingCategoryEmptyFieldDescription() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(DataConfig.DataTest.getCATEGORY_RULES());
        AddRuleWindow addRuleWindow = rulePreparingWindow.openAddRulesWindow();
        addRuleWindow.fillFieldTitle(DataConfig.DataTest.getRULE_TITLE());
        addRuleWindow.clickSaveButton();
        assertEquals("Неверный запрос (400)", servicesPage.getNotification());
        assertFalse(rulePreparingWindow.isExistRule());
    }

    @Feature("Управление правилами подготовки")
    @Story("Добавление правила подоготовки к категории с пустыми полями заголовка и описания")
    @ExtendWith({DeleteRuleDecorator.class, NotificationDecorator.class})
    @Test
    void addRulePreparingCategoryEmptyFieldTitleDescription() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(DataConfig.DataTest.getCATEGORY_RULES());
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
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(DataConfig.DataTest.getCATEGORY_RULES());
        AddRuleWindow addRuleWindow = rulePreparingWindow.openAddRulesWindow();
        addRuleWindow.fillFieldTitle(DataConfig.DataTest.getRULE_TITLE());
        addRuleWindow.fillFieldDescription(DataConfig.DataTest.getRULE_DESCRIPTION());
        addRuleWindow.closeWindowAddRule();
        assertFalse(addRuleWindow.isWindowAppear());
        assertFalse(rulePreparingWindow.isWindowAppear());
        servicesPage.openRulesPreparingCategory(DataConfig.DataTest.getCATEGORY_RULES());
        rulePreparingWindow.openAddRulesWindow();
        assertEquals("", addRuleWindow.getValueTitleField());
        assertEquals("", addRuleWindow.getValueDescriptionField());
    }

    @Feature("Управление правилами подготовки")
    @Story("Отмена добавления правила подоготовки после возврата к списку правил")
    @ExtendWith(DeleteRuleDecorator.class)
    @Test
    void comebackRulesListFromWindowAddRulePreparingCategory() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(DataConfig.DataTest.getCATEGORY_RULES());
        AddRuleWindow addRuleWindow = rulePreparingWindow.openAddRulesWindow();
        addRuleWindow.fillFieldTitle(DataConfig.DataTest.getRULE_TITLE());
        addRuleWindow.fillFieldDescription(DataConfig.DataTest.getRULE_DESCRIPTION());
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
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(DataConfig.DataTest.getCATEGORY_RULES());
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.openEditRuleWindow();
        editRuleWindow.editRuleWindow();
        editRuleWindow.fillFieldTitle(DataConfig.DataTest.getNEW_RULE_TITLE());
        editRuleWindow.fillFieldDescription(DataConfig.DataTest.getNEW_RULE_DESCRIPTION());
        editRuleWindow.changeRules();
        Selenide.sleep(3000);
        assertFalse(editRuleWindow.isWindowAppear());
        assertTrue(rulePreparingWindow.isWindowAppear());
        rule.openEditRuleWindow();
        ServiceCategories preparingDescription = DataBaseQuery.selectServicesInfo(DataConfig.DataTest.getCATEGORY_RULES());
        assertEquals(DataConfig.DataTest.getNEW_RULE_TITLE(), editRuleWindow.getTitleRule());
        assertEquals(DataConfig.DataTest.getNEW_RULE_DESCRIPTION(), editRuleWindow.getDescriptionRule());
        assertEquals(DataConfig.DataTest.getNEW_RULE_TITLE(), preparingDescription.getTitle());
        assertEquals(DataConfig.DataTest.getNEW_RULE_DESCRIPTION(), preparingDescription.getDescription());
    }

    @Feature("Управление правилами подготовки")
    @Story("Редактирование правила подоготовки к категории с пустым полем заголовка")
    @ExtendWith({AddDeleteRuleDecorator.class, NotificationDecorator.class})
    @Test
    void editRulePreparingCategoryEmptyFieldTitle() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(DataConfig.DataTest.getCATEGORY_RULES());
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.openEditRuleWindow();
        editRuleWindow.clearTitleField();
        editRuleWindow.fillFieldDescription(DataConfig.DataTest.getNEW_RULE_DESCRIPTION());
        editRuleWindow.changeRules();
        assertEquals("Неверный запрос (400)", servicesPage.getNotification());
        assertTrue(editRuleWindow.isWindowAppear());
    }

    @Feature("Управление правилами подготовки")
    @Story("Редактирование правила подоготовки к категории с пустым полем описания")
    @ExtendWith({AddDeleteRuleDecorator.class, NotificationDecorator.class})
    @Test
    void editRulePreparingCategoryEmptyFieldDescription() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(DataConfig.DataTest.getCATEGORY_RULES());
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.openEditRuleWindow();
        editRuleWindow.fillFieldTitle(DataConfig.DataTest.getNEW_RULE_TITLE());
        editRuleWindow.clearDescriptionField();
        editRuleWindow.changeRules();
        assertEquals("Неверный запрос (400)", servicesPage.getNotification());
        assertTrue(editRuleWindow.isWindowAppear());
    }

    @Feature("Управление правилами подготовки")
    @Story("Редактирование правила подоготовки к категории с пустыми полями")
    @ExtendWith({AddDeleteRuleDecorator.class, NotificationDecorator.class})
    @Test
    void editRulePreparingCategoryEmptyFields() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(DataConfig.DataTest.getCATEGORY_RULES());
        Rule rule = rulePreparingWindow.getRule();
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
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(DataConfig.DataTest.getCATEGORY_RULES());
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.openEditRuleWindow();
        editRuleWindow.changeRules();
        rule.openEditRuleWindow();
        ServiceCategories preparingDescription = DataBaseQuery.selectServicesInfo(DataConfig.DataTest.getCATEGORY_RULES());
        assertEquals(DataConfig.DataTest.getRULE_TITLE(), editRuleWindow.getTitleRule());
        assertEquals(DataConfig.DataTest.getRULE_DESCRIPTION(), editRuleWindow.getDescriptionRule());
        assertEquals(DataConfig.DataTest.getRULE_TITLE(), preparingDescription.getTitle());
        assertEquals(DataConfig.DataTest.getRULE_DESCRIPTION(), preparingDescription.getDescription());
    }

    @Feature("Управление правилами подготовки")
    @Story("Сохранение значений в окне редактирования правила подоготовки к категории после закрытия окна")
    @ExtendWith(AddDeleteRuleDecorator.class)
    @Test
    void closeWindowEditRulePreparingCategory() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(DataConfig.DataTest.getCATEGORY_RULES());
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.openEditRuleWindow();
        editRuleWindow.fillFieldTitle(DataConfig.DataTest.getNEW_RULE_TITLE());
        editRuleWindow.fillFieldDescription(DataConfig.DataTest.getNEW_RULE_DESCRIPTION());
        editRuleWindow.closeWindowRule();
        Selenide.sleep(1000);
        assertFalse(editRuleWindow.isWindowAppear());
        assertTrue(rulePreparingWindow.isWindowAppear());
        rule.openEditRuleWindow();
        ServiceCategories preparingDescription = DataBaseQuery.selectServicesInfo(DataConfig.DataTest.getCATEGORY_RULES());
        assertEquals(DataConfig.DataTest.getRULE_TITLE(), editRuleWindow.getTitleRule());
        assertEquals(DataConfig.DataTest.getRULE_DESCRIPTION(), editRuleWindow.getDescriptionRule());
        assertEquals(DataConfig.DataTest.getRULE_TITLE(), preparingDescription.getTitle());
        assertEquals(DataConfig.DataTest.getRULE_DESCRIPTION(), preparingDescription.getDescription());
    }

    @Feature("Управление правилами подготовки")
    @Story("Успешное удаление правила подоготовки к категории")
    @ExtendWith(AddRuleDecorator.class)
    @Test
    void deleteRulePreparingCategory() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(DataConfig.DataTest.getCATEGORY_RULES());
        Rule rule = rulePreparingWindow.getRule();
        EditRuleWindow editRuleWindow = rule.openEditRuleWindow();
        editRuleWindow.deleteRules();
        Selenide.sleep(2000);
        assertFalse(editRuleWindow.isWindowAppear());
        assertTrue(rulePreparingWindow.isWindowAppear());
        assertFalse(rulePreparingWindow.isExistRule());
        assertTrue(rulePreparingWindow.isExistsEmptyListRules());
        assertEquals("[]", DataBaseQuery.selectServicesInfo(DataConfig.DataTest.getCATEGORY_RULES()).getPreparing_description());
    }

    @Feature("Управление правилами подготовки")
    @Story("Успешное удаление всех правил подготовки к категории")
    @ExtendWith(AddRuleDecorator.class)
    @Test
    void deleteAllRulePreparingCategory() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(DataConfig.DataTest.getCATEGORY_RULES());
        rulePreparingWindow.deleteAllRules();
        Selenide.sleep(2000);
        assertFalse(rulePreparingWindow.isExistRule());
        assertTrue(rulePreparingWindow.isExistsEmptyListRules());
        assertEquals("[]", DataBaseQuery.selectServicesInfo(DataConfig.DataTest.getCATEGORY_RULES()).getPreparing_description());
    }

    @Feature("Управление правилами подготовки")
    @Story("Закрытие окна правил подготовки к категории")
    @Test
    void closeWindowRulePreparingCategory() {
        RulesPreparingWindow rulePreparingWindow = servicesPage.openRulesPreparingCategory(DataConfig.DataTest.getCATEGORY_RULES());
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

    @Feature("Управление категориями")
    @Story("Успешное добавление раздела в категорию")
    @ExtendWith(AddDeleteCategorySectionDecorator.class)
    @Test
    void addSectionInCategory() {
        CategoryCard categoryCard = servicesPage.openCategory(DataConfig.DataTest.getNAME_CATEGORY());
        categoryCard.categoryWindow();
        AddSectionWindow addSectionWindow = categoryCard.addSection();
        addSectionWindow.addSectionWindow();
        addSectionWindow.fillNameSectionField(DataConfig.DataTest.getNAME_SECTION());
        assertTrue(addSectionWindow.isEnabledAddButton());
        addSectionWindow.clickAddSection();
        Selenide.sleep(2000);
        assertFalse(addSectionWindow.isWindowAppear());
        servicesPage.openCategory(DataConfig.DataTest.getNAME_CATEGORY());
        assertTrue(categoryCard.isExistSectionCard());
        SectionCard sectionCard = categoryCard.getSection();
        assertEquals(DataConfig.DataTest.getNAME_SECTION(), sectionCard.getNameSection());
        assertNotNull(DataBaseQuery.selectServicesInfo(DataConfig.DataTest.getNAME_SECTION()));
        assertEquals(AddDeleteCategorySectionDecorator.getCategoryId(),DataBaseQuery.selectServicesInfo(DataConfig.DataTest.getNAME_SECTION()).getParent_id());
    }

    @Feature("Управление категориями")
    @Story("Зануление полей в окне добавления раздела в категорию и закрытие окна")
    @ExtendWith(AddDeleteCategoryDecorator.class)
    @Test
    void closeWindowAddSection() {
        CategoryCard categoryCard = servicesPage.openCategory(DataConfig.DataTest.getNAME_CATEGORY());
        AddSectionWindow addSectionWindow = categoryCard.addSection();
        addSectionWindow.fillNameSectionField(DataConfig.DataTest.getNAME_SECTION());
        addSectionWindow.closeWindowAddSection();
        assertFalse(addSectionWindow.isWindowAppear());
        categoryCard.addSection();
        assertEquals("", addSectionWindow.getValueNameField());
    }

    @Feature("Управление категориями")
    @Story("Отображение уведомления об обязательности поля")
    @ExtendWith(AddDeleteCategoryDecorator.class)
    @Test
    void addedNewSectionObligatoryFields() {
        CategoryCard categoryCard = servicesPage.openCategory(DataConfig.DataTest.getNAME_CATEGORY());
        AddSectionWindow addSectionWindow = categoryCard.addSection();
        addSectionWindow.clickFieldName();
        assertFalse(addSectionWindow.isEnabledAddButton());
        assertEquals("Обязательное поле",addSectionWindow.getErrorFieldName());
    }

    @Feature("Управление категориями")
    @Story("Очистка поля имени раздела через кнопку в окне добавления раздела")
    @ExtendWith(AddDeleteCategoryDecorator.class)
    @Test
    void clearFieldNameThroughButtonClear() {
        CategoryCard categoryCard = servicesPage.openCategory(DataConfig.DataTest.getNAME_CATEGORY());
        AddSectionWindow addSectionWindow = categoryCard.addSection();
        addSectionWindow.fillNameSectionField(DataConfig.DataTest.getNAME_SECTION());
        addSectionWindow.clearButtonNameField();
        assertEquals("",addSectionWindow.getValueNameField());
        assertEquals("Обязательное поле",addSectionWindow.getErrorFieldName());
    }

    @Feature("Управление категориями")
    @Story("Отмена добавления раздела в категорию")
    @ExtendWith(AddDeleteCategoryDecorator.class)
    @Test
    void cancelAddSectionInCategory() {
        CategoryCard categoryCard = servicesPage.openCategory(DataConfig.DataTest.getNAME_CATEGORY());
        AddSectionWindow addSectionWindow = categoryCard.addSection();
        addSectionWindow.cancelAddSection();
        assertFalse(addSectionWindow.isWindowAppear());
        assertFalse(categoryCard.isExistSectionCard());
    }

    @Feature("Управление категориями")
    @Story("Успешное редактирование раздела в категории")
    @ExtendWith(AddDeleteSectionDecorator.class)
    @Test
    void editSectionInCategory() {
        CategoryCard categoryCard = servicesPage.openCategory(DataConfig.DataTest.getNAME_CATEGORY());
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.sectionCard();
        EditSectionWindow editSectionWindow=sectionCard.editSection();
        editSectionWindow.editSectionWindow();
        editSectionWindow.fillNameField(DataConfig.DataTest.getNEW_NAME_SECTION());
        editSectionWindow.saveChange();
        Selenide.sleep(2000);
        assertFalse(editSectionWindow.isWindowAppear());
        servicesPage.openCategory(DataConfig.DataTest.getNAME_CATEGORY());
        assertEquals(DataConfig.DataTest.getNEW_NAME_SECTION(), sectionCard.getNameSection());
        assertNotNull(DataBaseQuery.selectServicesInfo(DataConfig.DataTest.getNEW_NAME_SECTION()));
        assertEquals(AddDeleteSectionDecorator.getCategoryId(),DataBaseQuery.selectServicesInfo(DataConfig.DataTest.getNEW_NAME_SECTION()).getParent_id());
    }

    @Feature("Управление категориями")
    @Story("Сохранение значений поля в окне редактирования раздела после закрытия окна")
    @ExtendWith(AddDeleteSectionDecorator.class)
    @Test
    void closeWindowEditSectionInCategory() {
        CategoryCard categoryCard = servicesPage.openCategory(DataConfig.DataTest.getNAME_CATEGORY());
        SectionCard sectionCard = categoryCard.getSection();
        EditSectionWindow editSectionWindow=sectionCard.editSection();
        editSectionWindow.fillNameField(DataConfig.DataTest.getNEW_NAME_SECTION());
        editSectionWindow.closeWindow();
        assertFalse(editSectionWindow.isWindowAppear());
        assertEquals(DataConfig.DataTest.getNAME_SECTION(), sectionCard.getNameSection());
        assertNotNull(DataBaseQuery.selectServicesInfo(DataConfig.DataTest.getNAME_SECTION()));
    }

    @Feature("Управление категориями")
    @Story("Сохранение значений поля без изменений данных")
    @ExtendWith(AddDeleteSectionDecorator.class)
    @Test
    void editSectionInCategoryNotChangeSave() {
        CategoryCard categoryCard = servicesPage.openCategory(DataConfig.DataTest.getNAME_CATEGORY());
        SectionCard sectionCard = categoryCard.getSection();
        EditSectionWindow editSectionWindow=sectionCard.editSection();
        editSectionWindow.saveChange();
        Selenide.sleep(3000);
        assertFalse(editSectionWindow.isWindowAppear());
        servicesPage.openCategory(DataConfig.DataTest.getNAME_CATEGORY());
        assertEquals(DataConfig.DataTest.getNAME_SECTION(), sectionCard.getNameSection());
        assertNotNull(DataBaseQuery.selectServicesInfo(DataConfig.DataTest.getNAME_SECTION()));
    }

    @Feature("Управление категориями")
    @Story("Редактирование названия раздела с пустым полем")
    @ExtendWith(AddDeleteSectionDecorator.class)
    @Test
    void editSectionInCategoryEmptyFieldName() {
        CategoryCard categoryCard = servicesPage.openCategory(DataConfig.DataTest.getNAME_CATEGORY());
        SectionCard sectionCard = categoryCard.getSection();
        EditSectionWindow editSectionWindow=sectionCard.editSection();
        editSectionWindow.clearNameField();
        editSectionWindow.saveChange();
        assertTrue(editSectionWindow.isWindowAppear());
        assertNotNull(DataBaseQuery.selectServicesInfo(DataConfig.DataTest.getNAME_SECTION()));
    }

    @Feature("Управление категориями")
    @Story("Успешное удаление раздела из категории")
    @ExtendWith(AddSectionDecorator.class)
    @Test
    void deleteSectionInCategory() {
        CategoryCard categoryCard = servicesPage.openCategory(DataConfig.DataTest.getNAME_CATEGORY());
        SectionCard sectionCard = categoryCard.getSection();
        DeleteSectionWindow deleteSectionWindow=sectionCard.deleteSection();
        deleteSectionWindow.deleteSectionWindow();
        assertTrue(deleteSectionWindow.verifyNameSection(DataConfig.DataTest.getNAME_SECTION()));
        deleteSectionWindow.deleteSection();
        Selenide.sleep(2000);
        assertFalse(deleteSectionWindow.isWindowAppear());
        servicesPage.openCategory(DataConfig.DataTest.getNAME_CATEGORY());
        assertFalse(categoryCard.isExistSectionCard());
        assertTrue(categoryCard.isExistEmptyList());
        assertNull(DataBaseQuery.selectServicesInfo(DataConfig.DataTest.getNAME_SECTION()));
    }

    @Feature("Управление категориями")
    @Story("Отмена удаления раздела из категории")
    @ExtendWith(AddDeleteSectionDecorator.class)
    @Test
    void cancelDeleteSectionInCategory() {
        CategoryCard categoryCard = servicesPage.openCategory(DataConfig.DataTest.getNAME_CATEGORY());
        SectionCard sectionCard = categoryCard.getSection();
        DeleteSectionWindow deleteSectionWindow=sectionCard.deleteSection();
        deleteSectionWindow.cancelDeleteSection();
        assertFalse(deleteSectionWindow.isWindowAppear());
        assertTrue(categoryCard.isExistSectionCard());
        assertNotNull(DataBaseQuery.selectServicesInfo(DataConfig.DataTest.getNAME_SECTION()));
    }

    @Feature("Управление категориями")
    @Story("Закрытие окна удаления раздела из категории")
    @ExtendWith(AddDeleteSectionDecorator.class)
    @Test
    void closeWindowDeleteSectionInCategory() {
        CategoryCard categoryCard = servicesPage.openCategory(DataConfig.DataTest.getNAME_CATEGORY());
        SectionCard sectionCard = categoryCard.getSection();
        DeleteSectionWindow deleteSectionWindow=sectionCard.deleteSection();
        deleteSectionWindow.closeWindowDeleteSection();
        assertFalse(deleteSectionWindow.isWindowAppear());
        assertTrue(categoryCard.isExistSectionCard());
        assertNotNull(DataBaseQuery.selectServicesInfo(DataConfig.DataTest.getNAME_SECTION()));
    }

    @Feature("Управление категориями")
    @Story("Успешное добавление подраздела в категорию")
    @ExtendWith(AddDeleteSectionSubsectionDecorator.class)
    @Test
    void addSubsectionInCategory() {
        CategoryCard categoryCard = servicesPage.openCategory(DataConfig.DataTest.getNAME_CATEGORY());
        SectionCard sectionCard = categoryCard.getSection();
        sectionCard.openSection();
        AddSectionWindow addSectionWindow=sectionCard.addSubsection();
        addSectionWindow.fillNameSectionField(DataConfig.DataTest.getNAME_SUBSECTION());
        assertTrue(addSectionWindow.isEnabledAddButton());
        addSectionWindow.clickAddSection();
        Selenide.sleep(2000);
        assertFalse(addSectionWindow.isWindowAppear());
        servicesPage.openCategory(DataConfig.DataTest.getNAME_CATEGORY());
        sectionCard.openSection();
        assertTrue(sectionCard.isExistSubsectionCard());
        SubsectionCard subsectionCard = sectionCard.getSubsection();
        assertEquals(DataConfig.DataTest.getNAME_SUBSECTION(), subsectionCard.getNameSubsection());
        assertNotNull(DataBaseQuery.selectServicesInfo(DataConfig.DataTest.getNAME_SUBSECTION()));
        assertEquals(AddDeleteSectionSubsectionDecorator.getSectionId(),DataBaseQuery.selectServicesInfo(DataConfig.DataTest.getNAME_SUBSECTION()).getParent_id());
    }
}



    /*
}
        @Story("Смена последовательности категорий")
        @Test
        void sequenceChangeQuestion () {

            int sequenceFirstCategory = servicesPage.getCategoryIndexByName("Телемедицина");
            int sequenceSecondCategory = servicesPage.getCategoryIndexByName("Стоматология");
            servicesPag.sequenceChangeCategory("Телемедицина", "Стоматология");
            int sequenceFirstCategory1 = servicesPage.getCategoryIndexByName("Стоматология");
            int sequenceSecondCategory1 = servicesPage.getCategoryIndexByName("Телемедицина");
            Assertions.assertEquals(sequenceFirstCategory, sequenceFirstCategory1);
            Assertions.assertEquals(sequenceSecondCategory, sequenceSecondCategory1);
        }

    }

 */