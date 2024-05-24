package admin.pages.modalWindowServices;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class EditSectionWindow {

    public final SelenideElement windowEditSection = $x("//input[@name='edit-category-name']/parent::div/parent::div");
    public final SelenideElement nameField = $x("//input[@name='edit-category-name']");
    public final SelenideElement saveChangeButton = $x("//input[@name='edit-category-name']/parent::div//following-sibling::div[@class='n6DU']");
    public final SelenideElement closeWindowButton = $x("//input[@name='edit-category-name']/parent::div//following-sibling::div[@class='V5So']");


    public void editSectionWindow() {
        windowEditSection.shouldBe(Condition.visible, Duration.ofSeconds(5));
        nameField.shouldBe(Condition.visible, Duration.ofSeconds(5));
        saveChangeButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        closeWindowButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void fillingNameField(String name){
        nameField.setValue(name);
    }

    public void saveChange(){
        saveChangeButton.click();
    }

    public void clearNameField(){
        nameField.clear();
    }

    public void closeWindow(){
        closeWindowButton.click();
        windowEditSection.shouldBe(Condition.hidden, Duration.ofSeconds(5));
    }
}
