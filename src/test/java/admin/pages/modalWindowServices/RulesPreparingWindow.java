package admin.pages.modalWindowServices;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class RulesPreparingWindow {

    public final SelenideElement windowRules = $x("//div[@class='TW3C']");
    private final SelenideElement addRules = $x("//button[text()='Добавить правило']");
    private final SelenideElement deleteAllRules = $x("//button[text()='Удалить все правила']");
    private final SelenideElement headerRule = $x("//div[@class='A7Gv']/span");
    private final SelenideElement openRule = $x("//div[@class='ei9k']");
    private final SelenideElement closeWindowButton = $x("//div[@class='TW3C']/preceding-sibling::div[@class='UnAf Ee5G']");

    public void rulesPreparingWindow() {
        windowRules.shouldBe(Condition.visible, Duration.ofSeconds(5));
        addRules.shouldBe(Condition.visible, Duration.ofSeconds(5));
        deleteAllRules.shouldBe(Condition.visible, Duration.ofSeconds(5));
        closeWindowButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public AddRuleWindow addRulesWindow() {
        addRules.click();
        return new AddRuleWindow();
    }

    public RuleWindow openRule(){
        openRule.shouldBe(Condition.visible, Duration.ofSeconds(5));
        openRule.click();
        return new RuleWindow();
    }

    public void deleteAllRules(){
        deleteAllRules.click();
    }

    public void closeWindowRulesPreparing(){
        closeWindowButton.click();
        windowRules.shouldBe(Condition.hidden, Duration.ofSeconds(5));
    }

    public String getHeaderRule() {
        return headerRule.getText();
    }

}
