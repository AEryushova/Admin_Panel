package admin.pages.modalWindowDoctors;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class Section {
    private final SelenideElement SECTION = $x("//div[@class='aksW']");
    private final SelenideElement NAME_SECTION = $x("//div[@class='aksW']");
    private final SelenideElement EMPTY_LIST_ELEMENT = $x("//div[text()='Пустой список']");
    private SelenideElement EDIT_BUTTON = $x("//div[@class='wmqb']");
    private SelenideElement FIELD_TITLE;
    private final SelenideElement DELETE_BUTTON = $x("//div[@class='UQ5Z']");
    private final SelenideElement ADD_DESCRIPTION_BUTTON = $x("//div[@class='EUkX']");


    public void section() {
        SECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        NAME_SECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        EDIT_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DELETE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ADD_DESCRIPTION_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void editTitle(String title) {
        EDIT_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        FIELD_TITLE = $x("//div[@class='aksW']/input");
        FIELD_TITLE.setValue(title);
        EDIT_BUTTON.click();
        FIELD_TITLE.shouldBe(Condition.hidden, Duration.ofSeconds(10));
    }

    public String getSectionName() {
        NAME_SECTION.exists();
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

    public boolean isSectionAppear() {
        return SECTION.exists();
    }


}
