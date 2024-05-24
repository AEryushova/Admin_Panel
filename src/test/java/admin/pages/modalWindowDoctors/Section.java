package admin.pages.modalWindowDoctors;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class Section {
    private final SelenideElement section=$x("//div[@class='aksW'][last()]");
    private final SelenideElement sectionName=$x("//div[@class='aksW'][last()]/span");
    private final SelenideElement emptyListElement=$x("//div[text()='Пустой список']");
    private SelenideElement editTitle=$x("//div[@class='wmqb'][last()]");
    private SelenideElement fieldTitle;
    private final SelenideElement  deleteTitle=$x("//div[@class='UQ5Z'][last()]");
    private final SelenideElement addDescription=$x("//div[@class='EUkX'][last()]");



    public void sectionEmpty(){
        section.shouldBe(Condition.visible, Duration.ofSeconds(10));
        sectionName.shouldBe(Condition.visible, Duration.ofSeconds(10));
        emptyListElement.shouldBe(Condition.visible, Duration.ofSeconds(10));
        editTitle.shouldBe(Condition.visible, Duration.ofSeconds(10));
        deleteTitle .shouldBe(Condition.visible, Duration.ofSeconds(10));
        addDescription.shouldBe(Condition.visible, Duration.ofSeconds(10));
    }
    public void sectionNotEmpty(){
        section.shouldBe(Condition.visible, Duration.ofSeconds(10));
        sectionName.shouldBe(Condition.visible, Duration.ofSeconds(10));
        editTitle.shouldBe(Condition.visible, Duration.ofSeconds(10));
        deleteTitle .shouldBe(Condition.visible, Duration.ofSeconds(10));
        addDescription.shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    public void editTitle(String title){
        editTitle.click();
        fieldTitle=$x("//div[@class='aksW']/input");
        fieldTitle.setValue(title);
        editTitle.click();
        fieldTitle.shouldBe(Condition.hidden,Duration.ofSeconds(10));
    }
    public String getSectionName(){
        sectionName.shouldBe(Condition.visible, Duration.ofSeconds(10));
        return sectionName.getText();
    }

    public void deleteTitle(){
        deleteTitle.click();
        section.shouldBe(Condition.hidden, Duration.ofSeconds(10));
    }

    public AddIntelligenceWindow openWindowAddDescription(){
        addDescription.click();
        return new AddIntelligenceWindow();
    }



}
