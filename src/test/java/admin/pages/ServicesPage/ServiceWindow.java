package admin.pages.ServicesPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class ServiceWindow {

    private final SelenideElement GENERAL_INFO =$x("//div[text()='Общая информация']");
    private final SelenideElement SERVICE_TRANSFER = $x("//div[text()='Перенос услуги']");
    private final SelenideElement RULES_PREPARING = $x("//div[text()='Правила подготовки']");
    private final SelenideElement HEADER_SERVICE =$x("//div[@class='fbzq']");
    private final SelenideElement DELETE_BUTTON =$x("//div[@class='wxIJ']");
    private final SelenideElement PATH_TO_SERVICE = $x("//span[text()='Путь к услуге']/following-sibling::span");
    private final SelenideElement NAME_SERVICE = $x("//span[text()='Наименование']/following-sibling::span");
    private final SelenideElement CODE_SERVICE = $x("//span[text()='Код услуги']/following-sibling::span");
    private final SelenideElement NAME_CATEGORY =$x("//div[@class='ZbgC']");
    private final SelenideElement TITLE_FIELD =$x("//input[@placeholder='Укажите заголовок правила']");
    private final SelenideElement DESCRIPTION_FIELD =$x("//textarea[@placeholder='Укажите описание правила']");
    private final SelenideElement ADD__RULE_BUTTON =$x("//button[text()='Добавить']");
    private final SelenideElement EDIT_RULE_BUTTON =$x("//button[text()='Изменить']");
    private final SelenideElement DELETE_RULE_BUTTON =$x("//button[text()='Удалить']");
    private final SelenideElement EMPTY_LIST_RULES_PREPARING = $x("//div[@class='HMP4']/div/span");

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

    public void serviceWindowServiceTransfer() {
        GENERAL_INFO.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SERVICE_TRANSFER.shouldBe(Condition.visible, Duration.ofSeconds(5));
        RULES_PREPARING.shouldBe(Condition.visible, Duration.ofSeconds(5));
        HEADER_SERVICE.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DELETE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        NAME_CATEGORY.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void serviceWindowRulesPreparing() {
        GENERAL_INFO.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SERVICE_TRANSFER.shouldBe(Condition.visible, Duration.ofSeconds(5));
        RULES_PREPARING.shouldBe(Condition.visible, Duration.ofSeconds(5));
        HEADER_SERVICE.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DELETE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        TITLE_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DESCRIPTION_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ADD__RULE_BUTTON.shouldBe(Condition.visible,Duration.ofSeconds(5)).shouldBe(Condition.disabled);
    }


    public void switchToServiceTransfer(){
        SERVICE_TRANSFER.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void switchToRulesPreparing(){
        RULES_PREPARING.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void switchToGeneralInfo(){
        GENERAL_INFO.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public String getPathService(){
        PATH_TO_SERVICE.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return PATH_TO_SERVICE.getText();
    }

    public String getNameService(){
        NAME_SERVICE.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return NAME_SERVICE.getText();
    }

    public String getCodeService(){
        CODE_SERVICE.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return CODE_SERVICE.getText();
    }

    public boolean isExistEmptyList() {
        return EMPTY_LIST_RULES_PREPARING.isDisplayed();
    }

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

}
