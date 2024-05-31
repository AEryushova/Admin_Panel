package admin.pages.modalWindowDoctors;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class Section {
    private final SelenideElement section = $x("//div[@class='aksW']");
    private final SelenideElement sectionName = $x("//div[@class='aksW']");
    private final SelenideElement emptyListElement = $x("//div[text()='Пустой список']");
    private SelenideElement editTitle = $x("//div[@class='wmqb']");
    private SelenideElement fieldTitle;
    private final SelenideElement deleteTitle = $x("//div[@class='UQ5Z']");
    private final SelenideElement addDescription = $x("//div[@class='EUkX']");


    public void sectionEmpty() {
        section.shouldBe(Condition.visible, Duration.ofSeconds(5));
        sectionName.shouldBe(Condition.visible, Duration.ofSeconds(5));
        emptyListElement.shouldBe(Condition.visible, Duration.ofSeconds(5));
        editTitle.shouldBe(Condition.visible, Duration.ofSeconds(5));
        deleteTitle.shouldBe(Condition.visible, Duration.ofSeconds(5));
        addDescription.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void sectionNotEmpty() {
        section.shouldBe(Condition.visible, Duration.ofSeconds(5));
        sectionName.shouldBe(Condition.visible, Duration.ofSeconds(5));
        editTitle.shouldBe(Condition.visible, Duration.ofSeconds(5));
        deleteTitle.shouldBe(Condition.visible, Duration.ofSeconds(5));
        addDescription.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void editTitle(String title) {
        editTitle.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        fieldTitle = $x("//div[@class='aksW']/input");
        fieldTitle.setValue(title);
        editTitle.click();
        fieldTitle.shouldBe(Condition.hidden, Duration.ofSeconds(10));
    }

    public String getSectionName() {
        sectionName.exists();
        return sectionName.getText();
    }

    public void deleteTitle() {
        deleteTitle.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public AddIntelligenceWindow openWindowAddDescription() {
        addDescription.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new AddIntelligenceWindow();
    }

    public boolean isSectionAppear() {
        return section.exists();
    }


}
