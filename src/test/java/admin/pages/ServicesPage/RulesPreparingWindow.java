package admin.pages.ServicesPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class RulesPreparingWindow {

    public final SelenideElement WINDOW = $x("//button[text()='Добавить правило']//parent::div//parent::div//parent::div//parent::div//parent::div[@class='TW3C']");
    private final SelenideElement ADD_BUTTON = $x("//button[text()='Добавить правило']");
    private final SelenideElement DELETE_ALL_RULES_BUTTON = $x("//button[text()='Удалить все правила']");
    private final SelenideElement RULE = $x("//div[@class='A7Gv']");
    private final SelenideElement CLOSE_WINDOW_BUTTON = $x("//div[@class='TW3C']/preceding-sibling::div[@class='UnAf Ee5G']");
    private final SelenideElement EMPTY_LIST_RULE =$x("//span[text()='Список пуст']");


    public void rulesPreparingWindow() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ADD_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DELETE_ALL_RULES_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public AddRuleWindow openAddRulesWindow() {
        ADD_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new AddRuleWindow();
    }


    public Rule getRule(){
        RULE.shouldBe(Condition.visible, Duration.ofSeconds(5))
                .shouldBe(Condition.exist, Duration.ofSeconds(5));
        return new Rule();
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
        WINDOW.shouldBe(Condition.hidden, Duration.ofSeconds(5));
    }

    public boolean isExistRule() {
        return RULE.exists();
    }

    public boolean isExistsEmptyListRules(){
        return EMPTY_LIST_RULE.exists();
    }

    public boolean isWindowAppear() {
        return WINDOW.exists();
    }

}
