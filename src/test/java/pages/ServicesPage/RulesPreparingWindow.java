package pages.ServicesPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class RulesPreparingWindow {

    public final SelenideElement WINDOW = $x("//button[text()='Добавить правило']//parent::div//parent::div//parent::div//parent::div//parent::div[@class='TW3C']");
    private final SelenideElement ADD_BUTTON = $x("//button[text()='Добавить правило']");
    private final SelenideElement DELETE_ALL_RULES_BUTTON = $x("//button[text()='Удалить все правила']");
    private final SelenideElement RULE = $x("//div[@class='A7Gv']");
    private final SelenideElement CLOSE_WINDOW_BUTTON = $x("//div[@class='TW3C']/preceding-sibling::div[@class='UnAf Ee5G']");
    private final SelenideElement EMPTY_LIST_RULE =$x("//span[text()='Список пуст']");
    private final ElementsCollection RULES = $$x("//div[@class='A7Gv']/span");

    @Step("Верифицировать окно правил подготовки")
    public void verifyRulesPreparingWindow() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ADD_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DELETE_ALL_RULES_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Step("Нажать кнопку добавления нового правила")
    public AddRuleWindow clickButtonAddRules() {
        ADD_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new AddRuleWindow();
    }

    @Step("Получить правило")
    public Rule getRule(){
        RULE.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return new Rule();
    }

    @Step("Получить заголовок правила")
    public SelenideElement getRuleByTitle(String title) {
        return RULES.findBy(Condition.text(title));
    }

    @Step("Получить информацию о пустом списке правил")
    public SelenideElement getEmptyList() {
        return EMPTY_LIST_RULE;
    }

    @Step("Нажать кнопку удаления всех правил")
    public void clickButtonDeleteAllRules() {
        DELETE_ALL_RULES_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    @Step("Закрыть окно правил подготовки")
    public void closeWindowRulesPreparing() {
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        WINDOW.shouldNotBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Step("Проверить отображение правила")
    public boolean isExistRule() {
        return RULE.isDisplayed();
    }

    @Step("Проверить отображение информации о пустом списке правил")
    public boolean isExistsEmptyListRules(){
        return EMPTY_LIST_RULE.isDisplayed();
    }

    @Step("Проверить отображение окна правил подготовки")
    public boolean isWindowAppear() {
        return WINDOW.isDisplayed();
    }

}
