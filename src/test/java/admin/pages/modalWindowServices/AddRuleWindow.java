package admin.pages.modalWindowServices;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class AddRuleWindow {
    public final SelenideElement windowAddRules = $x("//span[text()='вернуться назад']/parent::div/parent::div/parent::div/parent::div");
    public final SelenideElement comeBackButton = $x("//span[text()='вернуться назад']");
    public final SelenideElement headerField = $x("//input[@placeholder='Укажите заголовок правила']");
    public final SelenideElement descriptionField = $x("//textarea[@placeholder='Укажите описание правила']");
    public final SelenideElement saveButton = $x("//button[text()='Сохранить']");
    private final SelenideElement closeWindowButton = $x("//span[text()='вернуться назад']/parent::div/parent::div/parent::div/parent::div/preceding-sibling::div[@class='UnAf Ee5G']");

    public void addRuleWindow() {
        windowAddRules.shouldBe(Condition.visible, Duration.ofSeconds(5));
        comeBackButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        headerField .shouldBe(Condition.visible, Duration.ofSeconds(5));
        descriptionField .shouldBe(Condition.visible, Duration.ofSeconds(5));
        saveButton .shouldBe(Condition.visible, Duration.ofSeconds(5));
        closeWindowButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void fillingFieldHeader(String header) {
        headerField.setValue(header);
    }

    public void fillingFieldDescription(String description) {
        descriptionField.setValue(description);
    }

    public void clickSaveButton() {
        saveButton.click();
    }

    public void clearFieldHeader() {
        headerField.clear();
    }

    public void clearFieldDescription() {
        descriptionField.clear();
    }
    public void closeWindowAddRule() {
        closeWindowButton.click();
        windowAddRules.shouldBe(Condition.hidden, Duration.ofSeconds(5));
    }


}
