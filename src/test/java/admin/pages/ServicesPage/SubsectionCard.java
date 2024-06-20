package admin.pages.ServicesPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class SubsectionCard {
    private final SelenideElement EDIT_SUBSECTION_BUTTON = $x("//span[text()='Анализы крови']/parent::div/following-sibling::div[@class='V5So']");
    private final SelenideElement DELETE_SUBSECTION_BUTTON = $x("//span[text()='Анализы крови']/parent::div/following-sibling::div[@class='mJna']");
    private final SelenideElement RULES_PREPARING_SUBSECTION = $x("//span[text()='Анализы крови']/parent::div/following-sibling::div[@class='tSFL']");
    private final SelenideElement EXPAND_SUBSECTION = $x("//span[text()='Анализы крови']/parent::div/following-sibling::div[@class='xrjl']");

    public void subsection() {
        EDIT_SUBSECTION_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DELETE_SUBSECTION_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        RULES_PREPARING_SUBSECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        EXPAND_SUBSECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }


    public EditSectionWindow editSubsection() {
        EDIT_SUBSECTION_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new EditSectionWindow();
    }

    public DeleteSectionWindow deleteSubsection() {
        DELETE_SUBSECTION_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new DeleteSectionWindow();
    }

    public RulesPreparingWindow rulesPreparingSubsection() {
        RULES_PREPARING_SUBSECTION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new RulesPreparingWindow();
    }

    public void expandSubsection() {
        EXPAND_SUBSECTION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }
}
