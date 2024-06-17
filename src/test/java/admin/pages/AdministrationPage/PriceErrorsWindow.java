package admin.pages.AdministrationPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class PriceErrorsWindow {

    private final SelenideElement WINDOW = $x("//div[text()='Ошибки в прайсе']//parent::div//parent::div//parent::div[@class='eV2Y']");
    private final SelenideElement ADJUSTMENT_RULES_TAB = $x("//div[text()='Правила корректирования']");
    private final SelenideElement ERROR_PRICE_TAB = $x("//div[text()='Ошибки в прайсе']");
    private final SelenideElement ERROR_INFO = $x("//div[@class='FeiP']/span");
    private final SelenideElement ADJUSTMENT_RULES_INFO = $x("//span[contains(text(), 'Код услуги предполагает следующий формат')]");
    private final SelenideElement CLOSE_WINDOW_BUTTON = $x("//div[text()='Ошибки в прайсе']/parent::div/parent::div/preceding-sibling::div[@class='UnAf Ee5G']");

    public void priceErrorsWindow() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ADJUSTMENT_RULES_TAB.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ERROR_PRICE_TAB.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void clickAdjustmentRulesTab() {
        ADJUSTMENT_RULES_TAB.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void clickErrorPrice() {
        ERROR_PRICE_TAB.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public String getErrorInfo() {
        ERROR_INFO.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return ERROR_INFO.getText();
    }

    public boolean isErrorInfoAppear(){
        return ERROR_INFO.isDisplayed();
    }

    public boolean isAdjustmentRulesAppear(){
        return ADJUSTMENT_RULES_INFO.isDisplayed();
    }


    public void closeWindowPriceErrors() {
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.hidden,Duration.ofSeconds(5));
    }

    public boolean isWindowAppear() {
        return WINDOW.exists();
    }

}
