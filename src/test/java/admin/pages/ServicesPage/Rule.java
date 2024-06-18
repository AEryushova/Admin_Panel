package admin.pages.ServicesPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class Rule {

    private final SelenideElement OPEN_RULE = $x("//div[@class='ei9k']");
    private final SelenideElement TITLE_RULE=$x("//div[@class='A7Gv']/span");


    public void rule() {
        OPEN_RULE.shouldBe(Condition.visible, Duration.ofSeconds(5));
        TITLE_RULE.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public EditRuleWindow openEditRuleWindow() {
        OPEN_RULE.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new EditRuleWindow();
    }
}
