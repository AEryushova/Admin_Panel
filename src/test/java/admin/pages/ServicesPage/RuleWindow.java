package admin.pages.ServicesPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class RuleWindow {

    public final SelenideElement WINDOW = $x("//div[@class='TW3C']");
    public final SelenideElement HEADER_FIELD = $x("//input[@placeholder='Укажите заголовок правила']");
    public final SelenideElement DESCRIPTION_FIELD = $x("//textarea[@placeholder='Укажите описание правила']");
    public final SelenideElement EDIT_BUTTON = $x("//button[text()='Изменить']");
    private final SelenideElement DELETE_BUTTON = $x("//button[text()='Удалить']");
    private final SelenideElement CLOSE_WINDOW_BUTTON = $x("//div[@class='TW3C']/preceding-sibling::div[@class='UnAf Ee5G']");


    public void ruleWindow() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        HEADER_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DESCRIPTION_FIELD.shouldBe(Condition.visible, Duration.ofSeconds(5));
        EDIT_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DELETE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void fillFieldHeader(String header) {
        HEADER_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(header);
    }

    public void fillFieldDescription(String description) {
        DESCRIPTION_FIELD.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(description);
    }


    public void changeRules() {
        EDIT_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void deleteRules() {
        DELETE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public void closeWindowRule() {
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public boolean isWindowAppear() {
        return WINDOW.exists();
    }

}
