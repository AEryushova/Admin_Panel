package admin.pages.modalWindowDoctors;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class AddIntelligenceWindow {

    private final SelenideElement fieldText = $x("//input[@placeholder='Укажите название пункта']");
    private final SelenideElement saveButton = $x("//button[text()='Сохранить']");
    private final SelenideElement cancellationButton = $x("//button[text()='Отмена']");
    private final SelenideElement textWindow = $x("//input[@placeholder='Укажите название пункта']//parent::div//parent::div[@class='ijoD']");


    public void addIntelligenceWindow() {
        fieldText.shouldBe(Condition.visible, Duration.ofSeconds(5));
        saveButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        cancellationButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public Section addSection(String title) {
        fieldText.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(title);
        saveButton.click();
        return new Section();
    }

    public Description addDescription(String description) {
        fieldText.setValue(description);
        saveButton.click();
        return new Description();
    }

    public void fillFieldSectionDescription(String title) {
        fieldText.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(title);
    }

    public String getValueSectionDescription() {
        fieldText.exists();
        return fieldText.getValue();
    }

    public void saveValueSectionDescription() {
        saveButton.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void cancellationAddSectionDescription() {
        cancellationButton.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public boolean isWindowAppear() {
        return textWindow.exists();
    }

}
