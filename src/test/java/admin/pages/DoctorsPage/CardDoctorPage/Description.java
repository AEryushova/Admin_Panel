package admin.pages.DoctorsPage.CardDoctorPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class Description {
    private final SelenideElement DESCRIPTION = $x("//div[@class='IrCo']");
    private final SelenideElement NAME_DESCRIPTION = $x("//div[@class='IrCo']");
    private final SelenideElement EDIT_SAVE_BUTTON = $x("//div[@class='J9zY']");
    private final SelenideElement FIELD_DESCRIPTION = $x("//input[@class='d7ZA']");
    private final SelenideElement DELETE_BUTTON = $x("//div[@class='TRfT']");


    public void description() {
        DESCRIPTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        NAME_DESCRIPTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        EDIT_SAVE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DELETE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void fillDescriptionField(String description){
        FIELD_DESCRIPTION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(description);
    }

    public void editSaveDescription() {
        EDIT_SAVE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void clearDescriptionField(){
        FIELD_DESCRIPTION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .sendKeys(Keys.CONTROL, "a");
        FIELD_DESCRIPTION.sendKeys(Keys.BACK_SPACE);
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
