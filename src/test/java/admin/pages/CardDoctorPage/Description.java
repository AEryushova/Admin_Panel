package admin.pages.CardDoctorPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class Description {
    private final SelenideElement DESCRIPTION = $x("//div[@class='IrCo']");
    private final SelenideElement NAME_DESCRIPTION = $x("//div[@class='IrCo']");
    private final SelenideElement EDIT_BUTTON = $x("//div[@class='J9zY']");
    private final SelenideElement FIELD_DESCRIPTION = $x("//div[@class='aksW']/input");
    private final SelenideElement DELETE_BUTTON = $x("//div[@class='TRfT']");


    public void description() {
        DESCRIPTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        NAME_DESCRIPTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        EDIT_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DELETE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void editDescription() {
        NAME_DESCRIPTION.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        EDIT_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        NAME_DESCRIPTION.shouldBe(Condition.hidden);
    }

    public void fillDescriptionField(String title){
        FIELD_DESCRIPTION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(title);
    }

    public String getDescription() {
        NAME_DESCRIPTION.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return NAME_DESCRIPTION.getText();
    }

    public void deleteDescription() {
        DELETE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

}
