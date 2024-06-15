package admin.pages.DoctorsPage.CardDoctorPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class Section {
    private final SelenideElement SECTION = $x("//div[@class='aksW']");
    private final SelenideElement NAME_SECTION = $x("//div[@class='aksW']/span");
    private final SelenideElement EDIT_SAVE_BUTTON = $x("//div[@class='wmqb']");
    private final SelenideElement FIELD_TITLE=$x("//input[@class='yxN5']");
    private final SelenideElement DELETE_BUTTON = $x("//div[@class='UQ5Z']");
    private final SelenideElement ADD_DESCRIPTION_BUTTON = $x("//div[@class='EUkX']");


    public void section() {
        SECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        NAME_SECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        EDIT_SAVE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DELETE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ADD_DESCRIPTION_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }


    public void fillTitleField(String title){
        FIELD_TITLE.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(title);
    }

    public void editSaveTitle() {
        EDIT_SAVE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void clearTitleField(){
        FIELD_TITLE.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .sendKeys(Keys.CONTROL, "a");
        FIELD_TITLE.sendKeys(Keys.BACK_SPACE);
    }

    public String getSection() {
        NAME_SECTION.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return NAME_SECTION.getText();
    }

    public void deleteTitle() {
        DELETE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public AddIntelligenceWindow openWindowAddDescription() {
        ADD_DESCRIPTION_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new AddIntelligenceWindow();
    }

}
