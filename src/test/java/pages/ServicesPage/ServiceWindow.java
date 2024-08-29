package pages.ServicesPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class ServiceWindow {

    public final SelenideElement
            WINDOW = $x("//div[@class='rVVQ']"),
            GENERAL_INFO = $x("//div[text()='Общая информация']"),
            SERVICE_TRANSFER = $x("//div[text()='Перенос услуги']"),
            RULES_PREPARING = $x("//div[text()='Правила подготовки']"),
            HEADER_SERVICE = $x("//div[@class='fbzq']/span"),
            DELETE_BUTTON = $x("//div[@class='wxIJ']"),
            PATH_TO_SERVICE = $x("//span[text()='Путь к услуге']/following-sibling::span"),
            NAME_SERVICE = $x("//span[text()='Наименование']/following-sibling::span"),
            CODE_SERVICE = $x("//span[text()='Код услуги']/following-sibling::span"),
            NAME_CATEGORY = $x("//div[@class='ZbgC']"),
            NAME_SECTION = $x("//div[@class='l89u']"),
            NO_SECTIONS = $x("//span[contains(text(), 'Разделы отсутствуют')]"),
            TRANSFER_BUTTON = $x("//button[contains(text(), 'Перенести услугу')]"),
            TITLE_FIELD = $x("//input[@placeholder='Укажите заголовок правила']"),
            DESCRIPTION_FIELD = $x("//textarea[@placeholder='Укажите описание правила']"),
            ADD_RULE_BUTTON = $x("//button[text()='Добавить']"),
            EDIT_RULE_BUTTON = $x("//button[text()='Изменить']"),
            DELETE_RULE_BUTTON = $x("//button[text()='Удалить']"),
            EMPTY_LIST_RULES_PREPARING = $x("//div[@class='HMP4']/div/span"),
            CLOSE_WINDOW_BUTTON = $x("//div[text()='Общая информация']/parent::div/parent::div/preceding-sibling::div[@class='UnAf Ee5G']");

    @Step("Верифицировать вкладку общей информации окна услуги")
    public ServiceWindow verifyServiceWindowGeneralInfo() {
        GENERAL_INFO.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SERVICE_TRANSFER.shouldBe(Condition.visible, Duration.ofSeconds(5));
        RULES_PREPARING.shouldBe(Condition.visible, Duration.ofSeconds(5));
        HEADER_SERVICE.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DELETE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        PATH_TO_SERVICE.shouldBe(Condition.visible, Duration.ofSeconds(5));
        NAME_SERVICE.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CODE_SERVICE.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return this;
    }

    @Step("Верифицировать вкладку переноса услуг окна услуги")
    public ServiceWindow verifyServiceWindowServiceTransfer() {
        GENERAL_INFO.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SERVICE_TRANSFER.shouldBe(Condition.visible, Duration.ofSeconds(5));
        RULES_PREPARING.shouldBe(Condition.visible, Duration.ofSeconds(5));
        HEADER_SERVICE.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DELETE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        NAME_CATEGORY.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return this;
    }

    @Step("Верифицировать вкладку правил подготовки окна услуги")
    public ServiceWindow verifyServiceWindowRulesPreparing() {
        GENERAL_INFO.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SERVICE_TRANSFER.shouldBe(Condition.visible, Duration.ofSeconds(5));
        RULES_PREPARING.shouldBe(Condition.visible, Duration.ofSeconds(5));
        HEADER_SERVICE.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DELETE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        TITLE_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DESCRIPTION_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ADD_RULE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5)).shouldBe(Condition.disabled);
        return this;
    }

    @Step("Нажать вкладку переноса услуг")
    public ServiceWindow clickServiceTransferTab() {
        SERVICE_TRANSFER.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return this;
    }

    @Step("Нажать вкладку правил подготовки")
    public ServiceWindow clickRulesPreparingTab() {
        RULES_PREPARING.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return this;
    }

    @Step("Нажать вкладку общей информации")
    public ServiceWindow clickGeneralInfoTab() {
        GENERAL_INFO.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return this;
    }

    @Step("Получить заголовок окна")
    public String getHeaderServiceWindow() {
        HEADER_SERVICE.shouldBe(Condition.visible);
        return HEADER_SERVICE.getText();
    }

    @Step("Получить путь расположения услуги")
    public String getPathService() {
        PATH_TO_SERVICE.shouldBe(Condition.visible);
        return PATH_TO_SERVICE.getText();
    }

    @Step("Получить название услуги")
    public String getNameService() {
        NAME_SERVICE.shouldBe(Condition.visible);
        return NAME_SERVICE.getText();
    }

    @Step("Получить код услуги")
    public String getCodeService() {
        CODE_SERVICE.shouldBe(Condition.visible);
        return CODE_SERVICE.getText();
    }

    @Step("Нажать кнопку удаления услуги")
    public void clickButtonDeleteService() {
        DELETE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Выбрать категорию для переноса")
    public ServiceWindow clickCategoryForTransfer(String nameCategory) {
        SelenideElement CATEGORY = $x("//span[text()='" + nameCategory + "']//parent::div[@class='ZbgC']");
        CATEGORY.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return this;
    }

    @Step("Нажать кнопку переноса услуги в категорию Иные услуги")
    public void clickButtonTransferServiceToOtherServices() {
        NO_SECTIONS.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        NAME_SECTION.shouldNotBe(Condition.visible)
                .shouldNotBe(Condition.exist);
        TRANSFER_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Ввести в поле заголовка '{0}'")
    public ServiceWindow fillFieldTitle(String header) {
        TITLE_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(header);
        return this;
    }

    @Step("Ввести в поле описания '{0}'")
    public ServiceWindow fillFieldDescription(String description) {
        DESCRIPTION_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(description);
        return this;
    }

    @Step("Нажать кнопку добавления")
    public void clickAddButton() {
        ADD_RULE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Проверить доступность для нажатия кнопки добавления правила подготовки")
    public boolean isEnabledAddButton() {
        return ADD_RULE_BUTTON.isEnabled();
    }

    @Step("Получить значение поля заголовка")
    public String getValueTitleField() {
        TITLE_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return TITLE_FIELD.getValue();
    }

    @Step("Получить значение поля описания")
    public String getValueDescriptionField() {
        DESCRIPTION_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return DESCRIPTION_FIELD.getValue();
    }

    @Step("Проверить отображение информации о пустом списке правил подготовки")
    public boolean isExistEmptyList() {
        return EMPTY_LIST_RULES_PREPARING.isDisplayed();
    }

    @Step("Проверить отображение правила подготовки")
    public boolean isExistRulePreparing() {
        try {
            EDIT_RULE_BUTTON.shouldBe(Condition.visible)
                    .shouldBe(Condition.enabled);
            DELETE_RULE_BUTTON.shouldBe(Condition.visible)
                    .shouldBe(Condition.enabled);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Нажать кнопку изменения")
    public void clickChangeButton() {
        EDIT_RULE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Получить поле заголовка")
    public SelenideElement getFieldTitle() {
        return TITLE_FIELD;
    }

    @Step("Получить поле описания")
    public SelenideElement getFieldDescription() {
        return DESCRIPTION_FIELD;
    }

    @Step("Нажать кнопку удаления")
    public void clickDeleteButton() {
        DELETE_RULE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Закрыть окно информации об услуге")
    public void closeWindowInfoService() {
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        WINDOW.shouldNotBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Step("Получить кнопку добавления правила подготовки")
    public SelenideElement getAddRuleButton() {
        return ADD_RULE_BUTTON;
    }

    @Step("Получить кнопку изменения правила подготовки")
    public SelenideElement getEditRuleButton() {
        return EDIT_RULE_BUTTON;
    }

    @Step("Получить кнопку удаления правила подготовки")
    public SelenideElement getDeleteRuleButton() {
        return DELETE_RULE_BUTTON;
    }

    @Step("Получить окно информации об услуге")
    public SelenideElement getServiceWindow() {
        return WINDOW;
    }

    @Step("Проверить отображение окна информации об услуге")
    public boolean isWindowAppear() {
        return WINDOW.isDisplayed();
    }

}
