package admin.pages.ServicesPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class EditSectionWindow {

    public final SelenideElement WINDOW = $x("//input[@name='edit-category-name']/parent::div/parent::div");
    public final SelenideElement NAME_FIELD = $x("//input[@name='edit-category-name']");
    public final SelenideElement SAVE_BUTTON = $x("//input[@name='edit-category-name']/parent::div//following-sibling::div[@class='n6DU']");
    public final SelenideElement CLOSE_WINDOW_BUTTON = $x("//input[@name='edit-category-name']/parent::div//following-sibling::div[@class='V5So']");


    public void editSectionWindow() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        NAME_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SAVE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void fillNameField(String name) {
        NAME_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(name);
    }

    public void saveChange() {
        SAVE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void closeWindow() {
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();

    }

    public boolean isWindowAppear() {
        return WINDOW.isDisplayed();
    }
}
