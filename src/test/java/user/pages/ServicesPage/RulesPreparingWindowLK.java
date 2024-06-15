package user.pages.ServicesPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class RulesPreparingWindowLK {

    public final SelenideElement windowRules = $x("//div[@class='jNNrqi4Q']");
    private final SelenideElement closeWindowButton = $x("//div[@data-locator='close-popup-btn']");
    private final SelenideElement ruleOpenButton=$x("//div[@class='grc1akYl']");

    public void rulesPreparingWindow() {
        windowRules.shouldBe(Condition.visible, Duration.ofSeconds(5));
        closeWindowButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public Rule openRule(){
        ruleOpenButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ruleOpenButton.click();
        return new Rule();
    }


}
