package admin.pages.ServicesPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class AddRuleWindow {
    public final SelenideElement WINDOW = $x("//span[text()='вернуться назад']/parent::div/parent::div/parent::div/parent::div");
    public final SelenideElement RETURN_BUTTON = $x("//span[text()='вернуться назад']");
    public final SelenideElement TITLE_FIELD = $x("//input[@placeholder='Укажите заголовок правила']");
    public final SelenideElement DESCRIPTION_FIELD = $x("//textarea[@placeholder='Укажите описание правила']");
    public final SelenideElement SAVE_BUTTON = $x("//button[text()='Сохранить']");
    private final SelenideElement CLOSE_WINDOW_BUTTON = $x("//span[text()='вернуться назад']/parent::div/parent::div/parent::div/parent::div/preceding-sibling::div[@class='UnAf Ee5G']");

    public void addRuleWindow() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        RETURN_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        TITLE_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DESCRIPTION_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        SAVE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void fillFieldTitle(String header) {
        TITLE_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(header);
    }

    public void fillFieldDescription(String description) {
        DESCRIPTION_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(description);
    }

    public void clickSaveButton() {
        SAVE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public String getValueTitleField() {
        TITLE_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return TITLE_FIELD.getValue();
    }

    public String getValueDescriptionField() {
        DESCRIPTION_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return DESCRIPTION_FIELD.getValue();
    }

    public void closeWindowAddRule() {
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void returnRulesList() {
        RETURN_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public boolean isWindowAppear() {
        return WINDOW.exists();
    }

}
