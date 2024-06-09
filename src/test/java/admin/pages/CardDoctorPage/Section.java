package admin.pages.CardDoctorPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class Section {
    private final SelenideElement SECTION = $x("//div[@class='aksW']");
    private final SelenideElement NAME_SECTION = $x("//div[@class='aksW']/span");
    private final SelenideElement EDIT_BUTTON = $x("//div[@class='wmqb']");
    private final SelenideElement FIELD_TITLE=$x("//div[contains(@class, 'aksW')]/input");
    private final SelenideElement DELETE_BUTTON = $x("//div[@class='UQ5Z']");
    private final SelenideElement ADD_DESCRIPTION_BUTTON = $x("//div[@class='EUkX']");
    private final SelenideElement EMPTY_LIST_DESCRIPTION= $x("//div[text()='Пустой список']");


    public void section() {
        SECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        NAME_SECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        EDIT_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DELETE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ADD_DESCRIPTION_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void editTitle() {
        NAME_SECTION.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        EDIT_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        NAME_SECTION.shouldBe(Condition.hidden)
                .shouldNot(Condition.exist);
        FIELD_TITLE.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled);

    }


    public void fillTitleField(String title){
        NAME_SECTION.shouldBe(Condition.hidden)
                .shouldNot(Condition.exist);
        FIELD_TITLE.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(title);
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

    public boolean isExistsEmpty(){
        return EMPTY_LIST_DESCRIPTION.exists();
    }

}
