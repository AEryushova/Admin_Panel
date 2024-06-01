package admin.pages.modalWindowServices;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class RulesPreparingWindow {

    public final SelenideElement WINDOW = $x("//div[@class='TW3C']");
    private final SelenideElement ADD_BUTTON = $x("//button[text()='Добавить правило']");
    private final SelenideElement DELETE_ALL_RULES_BUTTON = $x("//button[text()='Удалить все правила']");
    private final SelenideElement HEADER_RULE = $x("//div[@class='A7Gv']/span");
    private final SelenideElement OPEN_RULE = $x("//div[@class='ei9k']");
    private final SelenideElement CLOSE_WINDOW_BUTTON = $x("//div[@class='TW3C']/preceding-sibling::div[@class='UnAf Ee5G']");

    public void rulesPreparingWindow() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ADD_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DELETE_ALL_RULES_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public AddRuleWindow addRulesWindow() {
        ADD_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new AddRuleWindow();
    }

    public RuleWindow openRule() {
        OPEN_RULE.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new RuleWindow();
    }

    public void deleteAllRules() {
        DELETE_ALL_RULES_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void closeWindowRulesPreparing() {
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public String getHeaderRule() {
        HEADER_RULE.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return HEADER_RULE.getText();
    }

    public boolean isWindowAppear() {
        return WINDOW.exists();
    }

}
