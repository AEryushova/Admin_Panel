package admin.pages.modalWindowAdministration;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class PriceErrorsWindow {

    private final SelenideElement errorInfoWindow = $x("//div[text()='Ошибки в прайсе']//parent::div//parent::div//parent::div[@class='eV2Y']");
    private final SelenideElement adjustmentRulesTab = $x("//div[text()='Правила корректирования']");
    private final SelenideElement errorPriceTab = $x("");
    private final SelenideElement errorInfo = $x("//div[@class='FeiP']/span");
    private final SelenideElement adjustmentRulesText = $x("//span[contains(text(), 'Код услуги предполагает следующий формат')]");
    private final SelenideElement closeWindowButton = $x("//div[text()='Ошибки в прайсе']/parent::div/parent::div/parent::*/div[@class='UnAf Ee5G']");

    public void priceErrorsWindow() {
        errorInfoWindow.shouldBe(Condition.visible, Duration.ofSeconds(5));
        adjustmentRulesTab.shouldBe(Condition.visible, Duration.ofSeconds(5));
        errorPriceTab.shouldBe(Condition.visible, Duration.ofSeconds(5));
        closeWindowButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void clickAdjustmentRulesTab() {
        adjustmentRulesTab.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void clickErrorPrice() {
        errorPriceTab.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public String getErrorInfo() {
        errorInfo.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);
        return errorInfo.getText();
    }


    public void closeWindowPriceErrors() {
        closeWindowButton.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }


    public boolean isWindowAppear() {
        return errorInfoWindow.exists();
    }

}
