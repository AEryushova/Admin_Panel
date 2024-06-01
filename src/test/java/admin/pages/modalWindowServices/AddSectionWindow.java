package admin.pages.modalWindowServices;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class AddSectionWindow {

    public final SelenideElement WINDOW = $x("//span[text()='Новый Вопрос']//parent::div//parent::div//parent::div[@class='eV2Y']");
    private final SelenideElement HEADER_WINDOW = $x("//span[text()='Новый Вопрос']");
    private final SelenideElement NAME_SECTION = $x("");
    private final SelenideElement ADD_BUTTON = $x("//button[text()='Добавить']");
    private final SelenideElement CANCEL_BUTTON = $x("//button[text()='Отменить']");
    private final SelenideElement CLOSE_WINDOW_BUTTON = $x("//span[text()='Новый Вопрос']//parent::div//parent::div/parent::*/div[@class='UnAf Ee5G']");


    public void addSectionWindow() {
        WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        HEADER_WINDOW.shouldBe(Condition.visible, Duration.ofSeconds(5));
        NAME_SECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        CANCEL_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
        ADD_BUTTON.shouldBe(Condition.disabled);
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public void fillNameSectionField(String name) {
        NAME_SECTION.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .setValue(name);
    }

    public void addSection() {
        ADD_BUTTON.shouldBe(Condition.enabled);
        ADD_BUTTON.click();
        WINDOW.shouldBe(Condition.hidden, Duration.ofSeconds(5));
    }

    public void cancellationAddSection() {
        CANCEL_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }


    public void closeWindowAddSection() {
        CLOSE_WINDOW_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }

    public boolean isWindowAppear() {
        return WINDOW.exists();
    }
}
