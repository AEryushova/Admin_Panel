package admin.pages.modalWindowAdministration;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class PriceErrorsWindow {

    private final SelenideElement errorInfoWindow = $x("//div[text()='Ошибки в прайсе']//parent::div//parent::div//parent::div[@class='eV2Y']");
    private final SelenideElement adjustmentRulesButton = $x("//div[text()='Правила корректирования']");
    private final SelenideElement adjustmentRulesText = $x("//span[contains(text(), 'Код услуги предполагает следующий формат')]");
    private final SelenideElement closeInfoErrorWindow = $x("//div[text()='Ошибки в прайсе']/parent::div/parent::div/parent::*/div[@class='UnAf Ee5G']");

    public void adjustmentRulesWindow () {
        errorInfoWindow.shouldBe(Condition.visible, Duration.ofSeconds(5));
        adjustmentRulesButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void clickAdjustmentRules(){
        adjustmentRulesButton.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void clickErrorPrice(){
        .shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void closeInfoErrorWindow() {
        errorInfoWindow.shouldBe(Condition.visible, Duration.ofSeconds(5));
        errorInPriceButton.click();
        closeInfoErrorWindow.click();
        errorInfoWindow.shouldBe(Condition.hidden, Duration.ofSeconds(5));
    }

    public String getErrorInfo() {
        errorInfoWindow.shouldBe(Condition.visible, Duration.ofSeconds(5));
        errorInfo.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return errorInfo.getText();
    }

    public boolean isWindowAppear() {
        return windowUpdatePrice.exists();
    }



}
