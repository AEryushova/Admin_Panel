package user.pages.modalWindowServices;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class Rule {
    public final SelenideElement windowRules = $x("//div[@class='jNNrqi4Q']");
    private final SelenideElement ruleHeader =$x("//div[@class='Q5qCq2Sz']/span");
    private final SelenideElement ruleDescription =$x("//div[@class='obvJl1wh']/span");
    private final SelenideElement closeWindowButton = $x("//div[@data-locator='close-popup-btn']");

    public void rule() {
        windowRules.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ruleHeader.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ruleDescription.shouldBe(Condition.visible, Duration.ofSeconds(5));
        closeWindowButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public String getHeaderRule(){
        return ruleHeader.getText();
    }

    public String getDescriptionRule(){
        return ruleDescription.getText();
    }
}
