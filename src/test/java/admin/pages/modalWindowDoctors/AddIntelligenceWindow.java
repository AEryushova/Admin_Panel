package admin.pages.modalWindowDoctors;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class AddIntelligenceWindow {

    private final SelenideElement TEXT_FIELD = $x("//input[@placeholder='Укажите название пункта']");
    private final SelenideElement SAVE_BUTTON = $x("//button[text()='Сохранить']");
    private final SelenideElement CANCEL_BUTTON = $x("//button[text()='Отмена']");
    private final SelenideElement WINDOW = $x("//input[@placeholder='Укажите название пункта']//parent::div//parent::div[@class='ijoD']");


    public void addIntelligenceWindow() {
        TEXT_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SAVE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CANCEL_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public Section addSection(String title) {
        TEXT_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(title);
        SAVE_BUTTON.click();
        return new Section();
    }

    public Description addDescription(String description) {
        TEXT_FIELD.setValue(description);
        SAVE_BUTTON.click();
        return new Description();
    }

    public void fillFieldSectionDescription(String title) {
        TEXT_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(title);
    }

    public String getValueSectionDescription() {
        TEXT_FIELD.exists();
        return TEXT_FIELD.getValue();
    }

    public void saveValueSectionDescription() {
        SAVE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void cancellationAddSectionDescription() {
        CANCEL_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public boolean isWindowAppear() {
        return WINDOW.exists();
    }

}
