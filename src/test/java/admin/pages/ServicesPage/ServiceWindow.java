package admin.pages.ServicesPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class ServiceWindow {

    public final SelenideElement WINDOW = $x("//div[@class='rVVQ']");
    private final SelenideElement GENERAL_INFO = $x("//div[text()='Общая информация']");
    private final SelenideElement SERVICE_TRANSFER = $x("//div[text()='Перенос услуги']");
    private final SelenideElement RULES_PREPARING = $x("//div[text()='Правила подготовки']");
    private final SelenideElement HEADER_SERVICE = $x("//div[@class='fbzq']/span");
    private final SelenideElement DELETE_BUTTON = $x("//div[@class='wxIJ']");
    private final SelenideElement PATH_TO_SERVICE = $x("//span[text()='Путь к услуге']/following-sibling::span");
    private final SelenideElement NAME_SERVICE = $x("//span[text()='Наименование']/following-sibling::span");
    private final SelenideElement CODE_SERVICE = $x("//span[text()='Код услуги']/following-sibling::span");
    private final SelenideElement NAME_CATEGORY = $x("//div[@class='ZbgC']");
    private final SelenideElement NAME_SECTION = $x("//div[@class='l89u']");
    private final SelenideElement NO_SECTIONS = $x("//span[contains(text(), 'Разделы отсутствуют')]");
    private final SelenideElement TRANSFER_BUTTON = $x("//button[contains(text(), 'Перенести услугу')]");
    private final SelenideElement TITLE_FIELD = $x("//input[@placeholder='Укажите заголовок правила']");
    private final SelenideElement DESCRIPTION_FIELD = $x("//textarea[@placeholder='Укажите описание правила']");
    private final SelenideElement ADD__RULE_BUTTON = $x("//button[text()='Добавить']");
    private final SelenideElement EDIT_RULE_BUTTON = $x("//button[text()='Изменить']");
    private final SelenideElement DELETE_RULE_BUTTON = $x("//button[text()='Удалить']");
    private final SelenideElement EMPTY_LIST_RULES_PREPARING = $x("//div[@class='HMP4']/div/span");
    private final SelenideElement CLOSE_WINDOW_BUTTON = $x("//div[text()='Общая информация']/parent::div/parent::div/preceding-sibling::div[@class='UnAf Ee5G']");

    @Step("Верифицировать вкладку общей информации окна услуги")
    public void serviceWindowGeneralInfo() {
        GENERAL_INFO.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SERVICE_TRANSFER.shouldBe(Condition.visible, Duration.ofSeconds(5));
        RULES_PREPARING.shouldBe(Condition.visible, Duration.ofSeconds(5));
        HEADER_SERVICE.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DELETE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        PATH_TO_SERVICE.shouldBe(Condition.visible, Duration.ofSeconds(5));
        NAME_SERVICE.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CODE_SERVICE.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Step("Верифицировать вкладку переноса услуг окна услуги")
    public void serviceWindowServiceTransfer() {
        GENERAL_INFO.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SERVICE_TRANSFER.shouldBe(Condition.visible, Duration.ofSeconds(5));
        RULES_PREPARING.shouldBe(Condition.visible, Duration.ofSeconds(5));
        HEADER_SERVICE.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DELETE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        NAME_CATEGORY.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Step("Верифицировать вкладку правил подготовки окна услуги")
    public void serviceWindowRulesPreparing() {
        GENERAL_INFO.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SERVICE_TRANSFER.shouldBe(Condition.visible, Duration.ofSeconds(5));
        RULES_PREPARING.shouldBe(Condition.visible, Duration.ofSeconds(5));
        HEADER_SERVICE.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DELETE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        TITLE_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DESCRIPTION_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ADD__RULE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5)).shouldBe(Condition.disabled);
    }

    @Step("Нажать вкладку переноса услуг")
    public void switchToServiceTransfer() {
        SERVICE_TRANSFER.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Нажать вкладку правил подготовки")
    public void switchToRulesPreparing() {
        RULES_PREPARING.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Нажать вкладку общей информации")
    public void switchToGeneralInfo() {
        GENERAL_INFO.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Получить заголовок окна")
    public String getHeaderServiceWindow() {
        HEADER_SERVICE.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return HEADER_SERVICE.getText();
    }

    @Step("Получить путь расположения услуги")
    public String getPathService() {
        PATH_TO_SERVICE.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return PATH_TO_SERVICE.getText();
    }

    @Step("Получить название услуги")
    public String getNameService() {
        NAME_SERVICE.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return NAME_SERVICE.getText();
    }

    @Step("Получить код услуги")
    public String getCodeService() {
        CODE_SERVICE.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return CODE_SERVICE.getText();
    }

    @Step("Нажать кнопку удаления услуги")
    public void deleteService(){
        DELETE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
            .click();
    }

    @Step("Выбрать категорию для переноса")
    public void openCategoryForTransfer(String nameCategory) {
        SelenideElement CATEGORY = $x("//span[text()='" + nameCategory + "']//parent::div[@class='ZbgC']");
        CATEGORY.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Нажать кнопку переноса услуги в категорию Иные услуги")
    public void transferServiceToOtherServices() {
        NO_SECTIONS.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        NAME_SECTION.shouldNotBe(Condition.visible)
                .shouldNotBe(Condition.exist);
        TRANSFER_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
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

    @Step("Закрыть окно информации об услуге")
    public void closeWindowInfoService() {
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        WINDOW.shouldBe(Condition.hidden, Duration.ofSeconds(5));
    }

    @Step("Проверить отображение окна информации об услуге")
    public boolean isWindowAppear() {
        return WINDOW.isDisplayed();
    }

}
