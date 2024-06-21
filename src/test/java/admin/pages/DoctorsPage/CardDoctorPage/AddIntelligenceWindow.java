package admin.pages.DoctorsPage.CardDoctorPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class AddIntelligenceWindow {

    private final SelenideElement TEXT_FIELD = $x("//input[@placeholder='Укажите название пункта']");
    private final SelenideElement SAVE_BUTTON = $x("//button[text()='Сохранить']");
    private final SelenideElement CANCEL_BUTTON = $x("//button[text()='Отмена']");
    private final SelenideElement WINDOW_SECTION = $x("//input[@placeholder='Укажите название пункта']//parent::div//parent::div[@class='ijoD']");
    private final SelenideElement WINDOW_DESCRIPTION = $x("//input[@placeholder='Укажите название пункта']//parent::div//parent::div[@class='S8Lv']");

    public void addIntelligenceSectionWindow() {
        WINDOW_SECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        TEXT_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SAVE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CANCEL_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void addIntelligenceDescriptionWindow() {
        WINDOW_DESCRIPTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        TEXT_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SAVE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CANCEL_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }


    public void fillFieldText(String title) {
        TEXT_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(title);
    }

    public String getValueField() {
        TEXT_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return TEXT_FIELD.getValue();
    }

    public void saveValue() {
        SAVE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void cancelAdd() {
        CANCEL_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public boolean isWindowSectionAppear() {
        return WINDOW_SECTION.isDisplayed();
    }

    public boolean isWindowDescriptionAppear() {
        return WINDOW_DESCRIPTION .isDisplayed();
    }

}
